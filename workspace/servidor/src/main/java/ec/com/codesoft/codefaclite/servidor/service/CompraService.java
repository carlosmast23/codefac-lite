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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.sri.ComprobantesElectronicosParametros;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
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
    
    private void validarDatosCompra(Compra compra) throws RemoteException, ServicioCodefacException
    {
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

            List<Compra> resultadoCompra= getFacade().findByMap(mapParametros);
            if(resultadoCompra.size()>0)
            {
                throw new ServicioCodefacException("No se puede ingresar compras repetidas del mismo proveedor");
            }
        }
        
        /**
         * Validar que este ingresando una compra repetida
         */
        if(getFacade().verificarCompraRepetida(compra))
        {
            throw new ServicioCodefacException("No se puede ingresar compras repetidas");
        }
        
        
    }
    
    private void grabarCartera(Compra compra,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(compra, Cartera.TipoCarteraEnum.PROVEEDORES,carteraParametro);
    }

    public void eliminarCompra(Compra compra) throws ServicioCodefacException,RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                RetencionService retencionService=new RetencionService();
                List<Retencion> retencionesAsociadas= retencionService.obtenerRetencionesPorCompra(compra);
                
                if(retencionesAsociadas.size()==0)
                {
                    compra.setEstado(GeneralEnumEstado.ELIMINADO.getEstado()); //Cambiar el estado de la compra
                    entityManager.merge(compra);                    
                }
                else
                {
                    //Obtener las retenciones asociadas
                    String retencionesStr="";
                    for (Retencion retencionesAsociada : retencionesAsociadas) {
                        retencionesStr+=retencionesAsociada.getPreimpreso()+"  ";                        
                    }
                    //retencionesStr=UtilidadesTextos.quitarUltimaLetra(retencionesStr);
                    
                    throw new ServicioCodefacException("No se puede eliminar porque existe retenciones asociadas "+retencionesStr);
                }
                
            }
        });
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
