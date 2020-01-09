/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.PrestamoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle.EstadoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.internal.sessions.factories.SessionsFactory;

/**
 *
 * @author Carlos
 */
public class FacturacionService extends ServiceAbstract<Factura, FacturaFacade> implements FacturacionServiceIf
{
    

    FacturaFacade facturaFacade;
    FacturaDetalleFacade facturaDetalleFacade;
    ParametroCodefacService parametroService;
    
    public FacturacionService() throws RemoteException {
        super(FacturaFacade.class);
        this.facturaFacade = new FacturaFacade();
        this.facturaDetalleFacade = new FacturaDetalleFacade();
        this.parametroService = new ParametroCodefacService();

    }
    
    public Factura buscarPorPremimpresoYEstado(Integer secuencial,BigDecimal puntoEstablecimiento,Integer  puntoEmision,ComprobanteEntity.ComprobanteEnumEstado estadoEnum) throws RemoteException
    {
        return getFacade().buscarPorPremimpresoYEstadoFacade(secuencial, puntoEstablecimiento, puntoEmision, estadoEnum);
    }
    
    /**
     * TODO: Este metodo me parece que debe estar en compra y no en servicio de factura
     * @param proforma
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public Factura grabarLiquidacionCompra(Factura liquidacionCompra) throws RemoteException,ServicioCodefacException
    {
        validarLiquidacionCompra(liquidacionCompra);
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException {
                    try
                    {
                        //liquidacionCompra.setSecuencial(obtenerSecuencialProformas(proforma.getEmpresa()).intValue());
                        liquidacionCompra.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                        
                        liquidacionCompra.setCodigoDocumento(DocumentoEnum.LIQUIDACION_COMPRA.getCodigo());
                        
                        ComprobantesService servicioComprobante = new ComprobantesService();
                        servicioComprobante.setearSecuencialComprobanteSinTransaccion(liquidacionCompra);           
                        setearDatosCliente(liquidacionCompra);
                        grabarDetallesFactura(liquidacionCompra); //Todo: Por el momento dejo comentando la proforma que se descuente del inventario
                        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar
                    }
                    catch (RemoteException ex)
                    {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (PersistenceException ex) {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Relanzar la excepcion si sucede algun problema interno
                    
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return liquidacionCompra;
    }
    private void validarLiquidacionCompra(Factura liquidacionCompra) throws RemoteException,ServicioCodefacException
    {
        if(liquidacionCompra.getCliente().getIdentificacion().equals(Persona.IDENTIFICACION_CONSUMIDOR_FINAL))
        {
            throw new ServicioCodefacException("No se puede grabar una Liquidación de Compra a consumidor final");
        }
    }
    
    
    public Factura grabarProforma(Factura proforma) throws RemoteException,ServicioCodefacException
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException {
                    try
                    {
                        proforma.setSecuencial(obtenerSecuencialProformas(proforma.getEmpresa()).intValue());
                        proforma.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                        
                        proforma.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
                        setearDatosCliente(proforma);
                        grabarDetallesFactura(proforma); //Todo: Por el momento dejo comentando la proforma que se descuente del inventario
                        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar
                    }
                    catch (RemoteException ex)
                    {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (PersistenceException ex) {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Relanzar la excepcion si sucede algun problema interno
                    
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return proforma;
    }
    
    /**
     * Metodo que permite setear los datos del cliente en la venta para poder datos estaticos para realizar control de la venta
     * @param venta 
     */
    private void setearDatosCliente(Factura venta)
    {
        venta.setRazonSocial(venta.getCliente().getRazonSocial());
        venta.setIdentificacion(venta.getCliente().getIdentificacion());
        venta.setDireccion(venta.getSucursal().getDireccion());
        venta.setTelefono(venta.getCliente().getTelefonoCelular()); //todo: ver si hago un metodo para obtener los telefonos        
    }
    
    public Factura grabar(Factura factura,Prestamo prestamo) throws RemoteException, ServicioCodefacException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(factura);
                
                /**
                 * ============================================================
                 *          GENERAR UN PRESTAMO SI EXISTE 
                 * ============================================================
                 */
                if(prestamo!=null)
                {
                    PrestamoService prestamoService=new PrestamoService();
                    prestamoService.grabarSinTransaccion(prestamo, factura);
                }
            }
        });
        return factura;
    }

    public Factura grabar(Factura factura) throws ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(factura);
                
            }
        });
        return factura;
        
    }
    
    public void grabarSinTransaccion(Factura factura) throws ServicioCodefacException, RemoteException
    {
        //TODO:Este codigo de doucmento ya no debo setear porque desde la factura ya mando el documento
        //factura.setCodigoDocumento(DocumentoEnum.FACTURA.getCodigo());
        factura.setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.NO);

        //Setear los datos del cliente en la factura para tener un historico y vovler a consultar
        //Todo: Ver si es necesario corregir este problema tambien en la factura cuando edita
        factura.setRazonSocial(factura.getCliente().getRazonSocial());
        factura.setIdentificacion(factura.getCliente().getIdentificacion());
        factura.setDireccion(factura.getSucursal().getDireccion());
        factura.setTelefono(factura.getSucursal().getTelefonoConvencional());

        ComprobantesService servicioComprobante = new ComprobantesService();
        servicioComprobante.setearSecuencialComprobanteSinTransaccion(factura);
        grabarDetallesFactura(factura);
        grabarCarteraSinTransaccion(factura);
    }
    
    public void editarFactura(Factura factura) throws ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(factura);            
            }
        });        
        
    }
    
    
    private void grabarDetallesFactura(Factura factura) throws RemoteException,PersistenceException,ServicioCodefacException
    {
        try
        {
            factura.setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.SIN_ANULAR.getEstado());            
            //facturaFacade.create(factura);
            entityManager.persist(factura);
            entityManager.flush();
            
            /**
             * Actualizar valores del inventario con el kardex
             */
            for (FacturaDetalle detalle : factura.getDetalles()) {
                //Verificar a que modulo debe afectar los detalles
                switch(detalle.getTipoDocumentoEnum())
                {
                    case ACADEMICO:
                        afectarAcademico(detalle);
                        break;
                    case INVENTARIO:
                        //Todo: Mejorar esta parte por el momento cuando es una proforma no proceso el tema del inventario
                        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA))
                        {
                            break;
                        }
                        
                        BodegaService bodegaService=new BodegaService();
                        Bodega bodegaVenta=bodegaService.obtenerBodegaVenta(factura.getSucursalEmpresa());
                        if(bodegaVenta==null)
                        {
                            throw new ServicioCodefacException("No existe un tipo de Bodega de Venta Configurado");
                        }
                        afectarInventario(detalle,bodegaVenta);
                        break;
                    case LIBRE:
                        //NO DEBE AFECTAR A NADA;
                        break;
                    case PRESUPUESTOS:
                        afectarPresupuesto(detalle);
                        break;
                        
                }
                
            }
        }
        catch (PersistenceException ex) {
            throw ex;
        }
            
    }
    
    public void grabarCartera(Factura factura) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarCarteraSinTransaccion(factura);
            }
        });
    }
    
    private void grabarCarteraSinTransaccion(Factura factura) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(factura, Cartera.TipoCarteraEnum.CLIENTE);
    }
    
    private void afectarPresupuesto(FacturaDetalle detalle)
    {
        try {
            PresupuestoService servicio = new PresupuestoService();
            Presupuesto presupuesto = servicio.buscarPorId(detalle.getReferenciaId());
            
            //Cambiar el estado al presupuesto para saber que ya fue facturadp
            presupuesto.setEstado(Presupuesto.EstadoEnum.FACTURADO.getLetra()); 
            
            //Cambiar el estado a la orden de trabajo del detalle para saber que ya no puede usar
            presupuesto.getOrdenTrabajoDetalle().setEstado(OrdenTrabajoDetalle.EstadoEnum.TERMINADO.getLetra());//Cambio el estado a terminado
            
            //Actualiza el estado de la orde de trabajo principal
            OrdenTrabajoService ordenTrabajoService=new OrdenTrabajoService();
            ordenTrabajoService.actualizarEstadoSinTransaccion(presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void afectarAcademico(FacturaDetalle detalle)
    {
        try {
            RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
            //El total es sin impuestos
            BigDecimal saldoPendiente=rubroEstudiante.getSaldo().subtract(detalle.getTotal());
            
            //Cuando el saldo es 0 la factura se factura en su totalidad
            if(saldoPendiente.compareTo(BigDecimal.ZERO)==0)
            {
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.FACTURADO.getLetra());
                rubroEstudiante.setSaldo(BigDecimal.ZERO);
            }
            else
            {
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.FACTURA_PARCIAL.getLetra());
                rubroEstudiante.setSaldo(saldoPendiente);
            }
            
            //Despues de modificar los estados del rubro los modifico segun el caso
            entityManager.merge(rubroEstudiante);
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private Kardex consultarOCrearStock(Producto producto, Bodega bodega) throws RemoteException, ServicioCodefacException
    {
        
        //Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
        //Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("producto", producto);
        KardexService kardexService = new KardexService();
        List<Kardex> kardexs = kardexService.buscarPorProductoYBodega(producto, bodega);

        Kardex kardex = null;
        if (kardexs == null || kardexs.size() == 0) {
            kardex = kardexService.crearObjeto(bodega, producto);
            entityManager.persist(kardex);
        } else {
            kardex = kardexs.get(0);
        }
        return kardex;

    }
    
    private void afectarInventario(FacturaDetalle detalle,Bodega bodega) throws RemoteException, ServicioCodefacException
    {

        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
        //Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("producto", producto);
        /*KardexService kardexService = new KardexService();
        List<Kardex> kardexs = kardexService.buscarPorProductoYBodega(producto, bodega);

        Kardex kardex = null;
        if (kardexs == null || kardexs.size() == 0) {
            kardex = kardexService.crearObjeto(bodega, producto);
            entityManager.persist(kardex);
        } else {
            kardex = kardexs.get(0);
        }*/
        Kardex kardex = consultarOCrearStock(producto, bodega);

        /**
         * Validacion pára verificar que exista un stock superior o igual en el
         * kardex segun lo que quieran facturar
         */
        int cantidadFaltante = detalle.getCantidad().intValue() - kardex.getStock();
        if(ParametroUtilidades.comparar(detalle.getFactura().getEmpresa(), ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO,EnumSiNo.NO))
        {
            //Si el stock que queremos facturar es mayor del existe lanzo una excepcion                
            if (detalle.getCantidad().compareTo(new BigDecimal(kardex.getStock())) > 0) 
            {
                //Solo para ensambles rerifica si tiene que construir el ensamble no importaria si no tiene el stock suficiente y mando a construir
                if (producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE) && ParametroUtilidades.comparar(kardex.getBodega().getEmpresa(), ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI)) 
                {
                    //No valida nada porque si este proceso falla automaticamente debe generar la excepcion interior, por ejemplo cuando no existe la cantidad necesaria de los componentes para construir el ensamble                    
                    verificarConstruirEnsamble(kardex, cantidadFaltante,true);
                } 
                else 
                {
                    //Si es un producto normal sin ensamble mando la excepcion que no tiene stock
                    throw new ServicioCodefacException("No existe el stock sufiente para facturar el producto " + kardex.getProducto().getNombre() + ", faltan " + cantidadFaltante + " productos");
                }
            }
        }
        else //Este caso se lanza cuando por defecto o esta activo que permita facturar negativo
        {
            //Solo para ensambles rerifica si tiene que construir el ensamble no importaria si no tiene el stock suficiente y mando a construir
            if (producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE) && ParametroUtilidades.comparar(kardex.getBodega().getEmpresa(), ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI)) {
                //En este caso si estaba activo construir el ensamble lo realizo pero sin validar el stock de los componentes
                verificarConstruirEnsamble(kardex, cantidadFaltante,false);
            }
        }
        
        /*ParametroCodefacService parametroService = new ParametroCodefacService();
        ParametroCodefac parametroFacturarStockNegativo = parametroService.getParametroByNombre(ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO, detalle.getFactura().getEmpresa());
        if (parametroFacturarStockNegativo != null) {
            EnumSiNo enumFacturarStockNegativo = EnumSiNo.getEnumByLetra(parametroFacturarStockNegativo.valor);
            
            //Cuando no quieren facturar con stock negativo verifico que exista la cantidad necesaria para facturar
            if (enumFacturarStockNegativo!=null && enumFacturarStockNegativo.equals(EnumSiNo.NO)) {
                //Si el stock que queremos facturar es mayor del existe lanzo una excepcion                
                if (detalle.getCantidad().compareTo(new BigDecimal(kardex.getStock())) > 0) {
                    
                    int cantidadFaltante=detalle.getCantidad().intValue()-kardex.getStock();                    
                    
                    //Solo para ensambles rerifica si tiene que construir el ensamble no importaria si no tiene el stock suficiente y mando a construir
                    if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE) && ParametroUtilidades.comparar(kardex.getBodega().getEmpresa(),ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI))
                    {
                        //No valida nada porque si este proceso falla automaticamente debe generar la excepcion interior, por ejemplo cuando no existe la cantidad necesaria de los componentes para construir el ensamble                    
                        verificarConstruirEnsamble(kardex,cantidadFaltante);
                    }
                    else
                    {
                        //Si es un producto normal sin ensamble mando la excepcion que no tiene stock
                        throw new ServicioCodefacException("No existe el stock sufiente para facturar el producto "+kardex.getProducto().getNombre()+", faltan "+cantidadFaltante+" productos");
                    }
                }
            }
        }
        else
        {
            
        }*/

        /**
         * ============================== FIN VALIDACION DE FACTURAR CON STOCK
         * NEGATIVO ======================
         */
        //TODO: Definir especificamente cual es la bodega principal
        //TODO: Analizar caso cuando se resta un producto especifico
        KardexService kardexService = new KardexService();
        KardexDetalle kardexDetalle = kardexService.crearKardexDetalleSinPersistencia(kardex, TipoDocumentoEnum.VENTA_INVENTARIO, detalle.getPrecioUnitario(), detalle.getCantidad().intValue());;
        //Agregando datos adicionales del movimiento en la factura
        kardexDetalle.setReferenciaDocumentoId(detalle.getFactura().getId());
        kardexDetalle.setPuntoEmision(detalle.getFactura().getPuntoEmision().toString());
        kardexDetalle.setPuntoEstablecimiento(detalle.getFactura().getPuntoEstablecimiento().toString());
        kardexDetalle.setSecuencial(detalle.getFactura().getSecuencial());
        kardexDetalle.setFechaDocumento(detalle.getFactura().getFechaEmision());

        //Actualizar los valores del kardex
        kardexService.recalcularValoresKardex(kardex, kardexDetalle);
        //kardex.setStock(kardex.getStock() - kardexDetalle.getCantidad());
        //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        //kardex.setPrecioTotal(kardex.getPrecioTotal().subtract(kardexDetalle.getPrecioTotal()));
        //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
        entityManager.persist(kardexDetalle); //Grabo el kardex detalle
        kardex.addDetalleKardex(kardexDetalle);
        entityManager.merge(kardex); //Actualizo el kardex con la nueva referencia
       
    }
    
    /**
     * Metodo para verificar si tiene la opcion activa de generar ensamble y ver si se puede construir en ese momento
     */
    public void verificarConstruirEnsamble(Kardex kardex,int cantidadFaltante,Boolean validarStockComponentes) throws RemoteException, ServicioCodefacException
    {
        if(ParametroUtilidades.comparar(kardex.getBodega().getEmpresa(),ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI))
        {
            ServiceFactory.getFactory().getKardexServiceIf().ingresoEgresoInventarioEnsambleSinTransaccion(kardex.getBodega(), kardex.getProducto(), cantidadFaltante,ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA,validarStockComponentes);
        }
    }
    
    
    public List<Factura> consultaDialogo(String param,int limiteMinimo,int limiteMaximo)
    {
        return facturaFacade.queryDialog(param,limiteMinimo,limiteMaximo);        
    }

    public void editar(Factura factura) {
        facturaFacade.edit(factura);
    }

    public List<Factura> obtenerTodos() {
        return facturaFacade.findAll();
    }

    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum) throws java.rmi.RemoteException {
        return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum);
    }

    public List<Factura> obtenerFacturasActivas()
    {
        return facturaFacade.getFacturaEnable();
    }
    
    /*
    public String getPreimpresoSiguiente() {
        try {
            Integer secuencialSiguiente=0;
            //Obtener secuencial cuando es modo electronico
            if(parametroService.getParametroByNombre(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
            {
                secuencialSiguiente = Integer.parseInt(parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA).valor);
            }
            else //cuando el modo es normals
            {
                secuencialSiguiente = Integer.parseInt(parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA_FISICA).valor);
            }
            
            String secuencial = UtilidadesTextos.llenarCarateresIzquierda(secuencialSiguiente.toString(), 8, "0");
            String establecimiento = parametroService.getParametroByNombre(ParametroCodefac.ESTABLECIMIENTO).valor;
            String puntoEmision = parametroService.getParametroByNombre(ParametroCodefac.PUNTO_EMISION).valor;
            return puntoEmision + "-" + establecimiento + "-" + secuencial;
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }*/
    
    public void eliminarFactura(Factura factura)
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    try {
                        ComprobantesService comprobanteService=new ComprobantesService();
                        comprobanteService.eliminarComprobanteSinTransaccion(factura);
                        
                        //factura.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado()); //Cambio el estado de las facturas a eliminad
                        entityManager.merge(factura); //actualizar los datos de la factura
                        
                        NotaCreditoService servicioNotaCredito=new NotaCreditoService();
                        for (FacturaDetalle detalle : factura.getDetalles()) {
                            //Anulo los datos segun el tipo de modulo relacionado
                            //servicioNotaCredito.anularProcesoFactura(detalle.getTipoDocumentoEnum(),detalle.getReferenciaId(),detalle.getTotal());
                            servicioNotaCredito.anularProcesoFactura(detalle);
                        }
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*
    public Long obtenerSecuencialProforma() throws java.rmi.RemoteException
    {
        return 0;
    }
*/

    @Override
    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion) throws RemoteException {
        //Factura f;
        //f.getIdentificacion();
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       mapParametros.put("identificacion",identificacion);
       return getFacade().findByMap(mapParametros);
    }
    
    @Override
    public Long obtenerSecuencialProformas(Empresa empresa) throws RemoteException
    {
        Long secuencial=getFacade().getSecuencialProforma(empresa);
        return (secuencial!=null)?(secuencial+1):1; //Si no existe ningun valor por defecto retorna 1
    }
    
    public void eliminarProforma(Factura factura) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                factura.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                entityManager.merge(factura);
            }
        });
    }
    
    public List<Factura> consultarProformasReporte(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,GeneralEnumEstado estado) throws java.rmi.RemoteException,ServicioCodefacException
    {
        return getFacade().consultarProformasReporteFacade(cliente, fechaInicial, fechaFinal, empresa,estado);
    }

    public Factura grabar(Factura factura,Empleado empleado) throws ServicioCodefacException,java.rmi.RemoteException,ServicioCodefacException
    {
        factura.setVendedor(empleado);
        factura=grabar(factura);
        return factura;
    }
}
