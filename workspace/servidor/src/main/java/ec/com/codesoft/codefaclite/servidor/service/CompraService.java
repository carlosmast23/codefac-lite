/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.CompraDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.CompraFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.sri.ComprobantesElectronicosParametros;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class CompraService extends ServiceAbstract<Compra,CompraFacade> implements CompraServiceIf{
    
    CompraFacade compraFacade;
    CompraDetalleFacade compraDetalleFacade;
    
    
    public CompraService() throws RemoteException {
        super(CompraFacade.class);
        this.compraFacade = new CompraFacade();
        this.compraDetalleFacade = new CompraDetalleFacade();
    }
    
    @Override
    public void editarCompra(Compra compra) throws ServicioCodefacException
    {
        //TODO: Editar este metodo porque el de grabar es muy similar
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
                    for (CompraDetalle compraDetalle : compra.getDetalles()) {
                        if (compraDetalle.getProductoProveedor().getId() == null) {
                            entityManager.persist(compraDetalle.getProductoProveedor());
                        } else {
                            entityManager.merge(compraDetalle.getProductoProveedor());
                        }
                    }

                    entityManager.merge(compra);
            }
        });
    }
    
    
    @Override
    public void grabarCompra(Compra compra,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        llenarDatosPorDefecto(compra);
        validarDatosCompra(compra);
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
               
                compra.setInventarioIngreso(EnumSiNo.NO.getLetra()); //La primera vez que grabo por defecto grabo para poder ingresar al inventario
                //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
                for (CompraDetalle compraDetalle : compra.getDetalles()) {
                    if (compraDetalle.getProductoProveedor().getId() == null) {
                        entityManager.persist(compraDetalle.getProductoProveedor());
                    } else {
                        entityManager.merge(compraDetalle.getProductoProveedor());
                    }
                }
                entityManager.persist(compra);
                grabarCartera(compra,carteraParametro); //Grabo la cartera desde de grabar la compra para tener el id de referencia que necesito en cartera
                
            }
        });
        
        //TODO: Falta retornar el tipo de dato por ejemplo en los dialogos necesita obtener el nuevo dato modificado.
    }
    
    private void llenarDatosPorDefecto(Compra compra) throws RemoteException, ServicioCodefacException
    {
        //Para documento de nota de venta interna ingreso unos datos adicionales automaticos
        DocumentoEnum documentoEnum=compra.getCodigoDocumentoEnum();
        if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            if(compra.getAutorizacion()==null)
            {
                compra.setAutorizacion("000000");
            }
            
            if(compra.getPuntoEstablecimiento()==null)
            {
                compra.setPuntoEstablecimiento(new BigDecimal("999"));
            }
            
            if(compra.getPuntoEmision()==null)
            {
                compra.setPuntoEmision(999);
            }
            
            if(compra.getSecuencial()==null)
            {
                Integer secuencial=getFacade().obtenerMaximoCodigoNotaVentaInterna(compra.getPuntoEmision(),compra.getPuntoEstablecimiento(),compra.getEmpresa());
                compra.setSecuencial(secuencial);
            }
            
        }
    }
    
    private void validarDatosCompra(Compra compra) throws RemoteException, ServicioCodefacException
    {
        if (compra.getPuntoEstablecimiento() == null) 
        {
            throw new ServicioCodefacException("El establecimiento no puede ser vacio");
        }
        
        if (compra.getPuntoEmision() == null) 
        {
            throw new ServicioCodefacException("EL punto de emisión no puede ser vacio");
        }
        
        if (compra.getSecuencial() == null) 
        {
            throw new ServicioCodefacException("El secuencial no puede ser vacio");
        }
        
                
        //La validacion de los secuenciales solo debe funcionar cuando no es un documento interno por que puede ser que no tenga datos de secuenciales
        DocumentoEnum documentoEnum=compra.getCodigoDocumentoEnum();
        if(!documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
                        
            if (compra.getPuntoEstablecimiento().compareTo(BigDecimal.ZERO) == 0) 
            {
                throw new ServicioCodefacException("El establecimiento no puede ser 0");
            }
            if (compra.getPuntoEmision() == 0) 
            {
                throw new ServicioCodefacException("EL punto de emisión no puede ser 0");
            }
            if (compra.getSecuencial() == 0) 
            {
                throw new ServicioCodefacException("El secuencial no puede ser 0");
            }
            
            if (compra.getAutorizacion()== null) {
                throw new ServicioCodefacException("La autorización no puede ser vacio");
            }
            
            if (compra.getAutorizacion().toString().trim().isEmpty()) {
                throw new ServicioCodefacException("La autorización no puede estar vacia");
            }
            
            if(compra.getPuntoEstablecimiento().toString().length()>3 && compra.getPuntoEmision().toString().length()>3 && compra.getPuntoEmision().toString().length()>9)
            {
                throw new ServicioCodefacException("Revise el formato del secuencial de la compra que es incorrecto");
            }
        }
        
        /**
         * =====================================================================
         *          VALIDAR EL TAMANIO DE LA AUTORIZACION
         * =====================================================================
         */
        if(compra.getAutorizacion()!=null)
        {
            if(compra.getAutorizacion().length()>ComprobantesElectronicosParametros.TAMANIO_MAXIMO_AUTORIZACION)
            {
                throw new ServicioCodefacException("El tamanio de la autorización no puede ser superior a "+ComprobantesElectronicosParametros.TAMANIO_MAXIMO_AUTORIZACION);
            }
        }
        
        /**
         * =====================================================================
         *          VALIDAR EL INGRESO DE DATOS NO REPETIDOS
         * =====================================================================
         * TODO: Poner en metodo facade porque seguro esta funcion utilice algunas veces mas para las vistas
         * Solo hacer esta validacion cuando no sea consumidor final
         */
        if(!compra.getIdentificacion().equals(Persona.IDENTIFICACION_CONSUMIDOR_FINAL))
        {
            Map<String,Object> mapParametros=new HashMap<String,Object>();
            mapParametros.put("puntoEstablecimiento",compra.getPuntoEstablecimiento());
            mapParametros.put("puntoEmision",compra.getPuntoEmision());
            mapParametros.put("secuencial",compra.getSecuencial());
            mapParametros.put("empresa",compra.getEmpresa());
            mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
            mapParametros.put("codigoDocumento", compra.getCodigoDocumentoEnum().getCodigo());
            mapParametros.put("proveedor", compra.getProveedor());
            
            //compra.get

            List<Compra> resultadoCompra= getFacade().findByMap(mapParametros);
            if(resultadoCompra.size()>0)
            {
                throw new ServicioCodefacException("No se puede ingresar compras repetidas del mismo proveedor");
            }
        }
        
        /**
         * Validar que este ingresando una compra repetida
         */
        //if(getFacade().verificarCompraRepetida(compra))
        //{
        //    throw new ServicioCodefacException("No se puede ingresar compras repetidas");
        //}
        
        
    }
    
    private void grabarCartera(Compra compra,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(compra, Cartera.TipoCarteraEnum.PROVEEDORES,carteraParametro,CrudEnum.CREAR);
    }

    public void eliminarCompra(Compra compra) throws ServicioCodefacException,RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                
                eliminarCompraSinTransaccion(compra);
            }
        });
    }
    
    public void eliminarCompraSinTransaccion(Compra compra) throws ServicioCodefacException, RemoteException {
        eliminarRetencionCompraSinTransaccion(compra);

        //Solo crear movimiento de egreso de mercaderia cuando ya fueron ingresados al inventario
        if (compra.getInventarioIngresoEnum().equals(EnumSiNo.SI)) {
            for (CompraDetalle detalle : compra.getDetalles()) {
                eliminarDetalleCompra(detalle);
            }
        }

        /**
         * ==================================================================================
         * ELIMINACION EN CARTERA DE LAS COMPRAS
         * ==================================================================================
         */
        eliminarCarteraCompra(compra);
    }
    
    private void eliminarCarteraCompra(Compra entity) throws RemoteException, ServicioCodefacException
    {        
        //Cartera cartera;
        //cartera.
        CarteraService carteraService=new CarteraService();
        Cartera carteraCompra=carteraService.buscarCarteraPorReferencia(entity.getId(),entity.getCodigoDocumentoEnum(), GeneralEnumEstado.ACTIVO, Cartera.TipoCarteraEnum.PROVEEDORES,entity.getSucursalEmpresa());                
        if(carteraCompra!=null)
        {
            carteraService.eliminarCarteraSinTransaccion(carteraCompra,ModoProcesarEnum.NORMAL);
            
        }
    }
    

    private void eliminarDetalleCompra(CompraDetalle compraDetalle) throws RemoteException, ServicioCodefacException
    {   
        
        Bodega bodega = obtenerBodegaDevolucion(compraDetalle);
        KardexService kardexService=new KardexService();
        kardexService.afectarInventario(
                bodega,
                compraDetalle.getCantidad(),
                compraDetalle.getPrecioUnitario(),
                compraDetalle.getTotal(),
                compraDetalle.getId(),
                compraDetalle.getProductoProveedor().getProducto().getIdProducto(),
                TipoDocumentoEnum.ELIMINADO_COMPRA,
                compraDetalle.getCompra().getPuntoEmision().toString(),
                compraDetalle.getCompra().getPuntoEstablecimiento().toString(),
                compraDetalle.getCompra().getSecuencial(),
                compraDetalle.getCompra().getFechaEmision()
        );

        
    }
    
    private Bodega obtenerBodegaDevolucion(CompraDetalle compraDetalle) throws RemoteException, ServicioCodefacException
    {
        KardexDetalleService kardexDetalleService=new KardexDetalleService();
        KardexDetalle kardexDetalle=kardexDetalleService.consultarPorReferencia(compraDetalle.getCompra().getCodigoTipoDocumentoEnum(),compraDetalle.getCompra().getId(),compraDetalle.getProductoProveedor().getProducto());
        if(kardexDetalle!=null)
        {
            return kardexDetalle.getKardex().getBodega();
        }
        return null;
    }
    
    private void eliminarRetencionCompraSinTransaccion(Compra compra) throws ServicioCodefacException, RemoteException
    {
        RetencionService retencionService = new RetencionService();
        List<Retencion> retencionesAsociadas = retencionService.obtenerRetencionesPorCompra(compra);

        if (retencionesAsociadas.size() == 0) {
            compra.setEstado(GeneralEnumEstado.ELIMINADO.getEstado()); //Cambiar el estado de la compra
            entityManager.merge(compra);
        } else {
            //Obtener las retenciones asociadas
            String retencionesStr = "";
            for (Retencion retencionesAsociada : retencionesAsociadas) {
                retencionesStr += retencionesAsociada.getPreimpreso() + "  ";
            }
            //retencionesStr=UtilidadesTextos.quitarUltimaLetra(retencionesStr);

            throw new ServicioCodefacException("No se puede eliminar porque existe retenciones asociadas " + retencionesStr);
        }
    }
    
    
    

    @Override
    public List<Compra> obtenerTodos()
    {
        return compraFacade.findAll();
    }
    
    @Override
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum de, TipoDocumentoEnum tde,GeneralEnumEstado estadoEnum,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        return compraFacade.obtenerCompraReporte(proveedor, fechaInicial, fechaFin, de, tde,estadoEnum,empresa);
    }    
    
    @Override
    public List<Compra> obtenerCompraDisenable()
    {
        return compraFacade.getCompraRetencionDisenable();
    }
}
