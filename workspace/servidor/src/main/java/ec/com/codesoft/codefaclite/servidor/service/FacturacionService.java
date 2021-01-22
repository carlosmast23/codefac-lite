/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle.EstadoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.FacturaParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesNumeros;
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
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws RemoteException, ServicioCodefacException {
                    
                        //liquidacionCompra.setSecuencial(obtenerSecuencialProformas(proforma.getEmpresa()).intValue());
                        liquidacionCompra.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                        
                        liquidacionCompra.setCodigoDocumento(DocumentoEnum.LIQUIDACION_COMPRA.getCodigo());
                        
                        ComprobantesService servicioComprobante = new ComprobantesService();
                        servicioComprobante.setearSecuencialComprobanteSinTransaccion(liquidacionCompra);           
                        setearDatosCliente(liquidacionCompra);
                        grabarDetallesFacturaSinTransaccion(liquidacionCompra); //Todo: Por el momento dejo comentando la proforma que se descuente del inventario
                        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar
                    
                    //Relanzar la excepcion si sucede algun problema interno
                    
                }
            });
       
        
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
            validacionInicialFacturar(proforma,CrudEnum.CREAR);
        
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws RemoteException, ServicioCodefacException {
                        //Agregado vendedor de forma automatica si el usuario tiene relacionado un empleado con departamento de ventas
                        asignarVendedorProforma(proforma);
                    
                        proforma.setSecuencial(obtenerSecuencialProformas(proforma.getEmpresa()).intValue());
                        proforma.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                        
                        proforma.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
                        setearDatosCliente(proforma);
                        grabarDetallesFacturaSinTransaccion(proforma); //Todo: Por el momento dejo comentando la proforma que se descuente del inventario
                        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar
                    
                     
               }
            });
        
        
        return proforma;
    }
    
    private void asignarVendedorProforma(Factura proforma)
    {
        Empleado empleado=proforma.getUsuario().getEmpleado();
        if(empleado!=null)
        {
            Departamento departamento=empleado.getDepartamento();            
            if(departamento.getTipoEnum().equals(Departamento.TipoEnum.Ventas))
            {
                proforma.setVendedor(empleado);
            }
        }
        
    }
    
    public Factura editarProforma(Factura proforma) throws RemoteException,ServicioCodefacException
    {
        validacionInicialFacturar(proforma,CrudEnum.EDITAR);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                setearDatosCliente(proforma);
                entityManager.merge(proforma);
            }
        });
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
    
    /**
     * El prestamos sirve para identificar cuando se genera con el modulo de Crédito
     * @param factura
     * @param prestamo
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public Factura grabar(final Factura factura,Prestamo prestamo,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validacionInicialFacturar(factura,CrudEnum.CREAR);
                grabarSinTransaccion(factura,carteraParametro);
                
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
                
                
                //Despues de grabar genero inmediatamente un flush para evitar perder la transacción por causas como perdida de energia
                entityManager.flush();
            }
        });
        return factura;
    }

    public Factura grabar(Factura factura) throws ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validacionInicialFacturar(factura,CrudEnum.CREAR);
                grabarSinTransaccion(factura,null);
                
            }
        });
        return factura;
        
    }
    
    public FacturaLoteRespuesta grabarLote(List<FacturaParametro> facturaList) throws RemoteException,ServicioCodefacException {
        
        FacturaLoteRespuesta respuesta=new FacturaLoteRespuesta();
        
        for (FacturaParametro factura : facturaList) 
        {
            try
            {
                //´TODO: Por el momento todas las facturas en lote se van a facturar con la fecha actual
                factura.factura.setFechaEmision(UtilidadesFecha.getFechaHoy());
                
                Factura facturaGrabada=grabar(factura.factura,factura.prestamo,factura.carteraPrestamo);
                respuesta.agregarFacturaProcesada(factura.factura);
            }
            catch(ServicioCodefacException e)
            {
                //Agrego a la lista las facturas que tienen problemas y no fueron procesadas
                respuesta.agregarFacturaNoProcesada(new FacturaLoteRespuesta.Error(factura.factura, e.getMessage()));
            }
        }
        
        return respuesta;
        
    }
    
    public void validacionInicialFacturar(Factura factura,CrudEnum modo) throws ServicioCodefacException, RemoteException
    {
        if(factura.getCliente()==null)
        {
            throw new ServicioCodefacException("La factura tiene que tener un cliente asignado");
        }
        
        if(factura.getCliente().getRazonSocial()==null || factura.getCliente().getRazonSocial().trim().isEmpty())
        {
            throw new ServicioCodefacException("No se puede emitir una factura sin la razón social del cliente ");
        }
        
        if(!factura.getCliente().validarIdentificacion().equals(Persona.ValidacionCedulaEnum.VALIDACION_CORRECTA))
        {
            //DialogoCodefac.mensaje("Error con el cliente", factura.getCliente().validarIdentificacion().getMensaje(), DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ServicioCodefacException("Error con la identificacion del cliente seleccionado");
        }
        
        //Validacion para los detalles
        for (FacturaDetalle detalle : factura.getDetalles()) 
        {
            if(detalle.getDescuento()==null)
            {
                throw new ServicioCodefacException("Error al grabar un descuento null en el pedido");
            }
            else
            {
                //Verificar que los detalles de los descuentos no tenga más de 2 decimales
                int numeroDecimales=UtilidadesNumeros.numeroDecimales(detalle.getDescuento()+"");
                if(numeroDecimales>2)
                {
                    throw new ServicioCodefacException("Error con el descuento que tiene más de 2 decimales en el producto "+detalle.getDescripcion()+" ");
                }
            }
        }
        
        //validar que los totales coincidan o si tiene inconsistencias no grabar
        for (FacturaDetalle detalle : factura.getDetalles()) 
        {
            BigDecimal totalDetalle=detalle.getTotal();
            BigDecimal totalDetalleCalculado=detalle.getCalcularTotalDetalle();
            BigDecimal diferencia=totalDetalle.subtract(totalDetalleCalculado).abs();
            
            if(diferencia.compareTo(new BigDecimal("0.01"))>0)
            {
                throw new ServicioCodefacException("Error de inconsistencia en los valores de los detalles");
            }
        }
        
        if(!factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA))
        {
            if(modo.equals(CrudEnum.CREAR))
            {
                validacionInicialProforma(factura.getProforma());            
            }
            
        }
    }
    
    public Factura grabarSinTransaccion(Factura factura,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        //TODO:Este codigo de documento ya no debo setear porque desde la factura ya mando el documento
        //factura.setCodigoDocumento(DocumentoEnum.FACTURA.getCodigo());
        factura.setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.NO);

        //Setear los datos del cliente en la factura para tener un historico y vovler a consultar
        //Todo: Ver si es necesario corregir este problema tambien en la factura cuando edita
        factura.setRazonSocial(factura.getCliente().getRazonSocial());
        factura.setIdentificacion(factura.getCliente().getIdentificacion());
        factura.setDireccion(factura.getSucursal().getDireccion());
        factura.setTelefono(factura.getSucursal().getTelefonoConvencional());
        asignarVendedorAutomatico(factura);
        
        //Cambiar el estado si viene de un pedido si fuera el caso
        Factura proforma=factura.getProforma();
        if(proforma!=null)
        {
            proforma.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.FACTURADO_PROFORMA);
            entityManager.merge(proforma);
        }
        
        //Si es nota de venta generar un número de autorización cualquiera
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            factura.setClaveAcceso(factura.getPuntoEstablecimiento()+""+factura.getPuntoEmision()+""+factura.getSecuencial()+"");
        }

        ComprobantesService servicioComprobante = new ComprobantesService();
        servicioComprobante.setearSecuencialComprobanteSinTransaccion(factura);
        grabarDetallesFacturaSinTransaccion(factura);
        grabarCarteraSinTransaccion(factura,carteraParametro);
        return factura;
    }
    
    /**
     * Metodo que me permite asiganar un vendedor de forma automatica si el usuario tiene vinculado el vendedor
     */
    private void asignarVendedorAutomatico(Factura factura) throws ServicioCodefacException, RemoteException
    {
        Empleado vendedor=factura.getUsuario().getEmpleado();
        if(vendedor!=null && vendedor.getDepartamento()!=null && vendedor.getDepartamento().getTipoEnum().equals(Departamento.TipoEnum.Ventas))
        {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.INFO,"Grabado vendedor de forma automatica");
            factura.setVendedor(vendedor);
        }
    }
    
    /**
     * @deprecated Porque existe el metodo editar que hace lo mismo y esto solo usaba para una actuaizacion hace tiempo
     * @param factura
     * @throws ServicioCodefacException 
     */
    public void editarFactura(Factura factura) throws ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(factura);            
            }
        });        
        
    }
    
    
    private void grabarDetallesFacturaSinTransaccion(Factura factura) throws RemoteException,PersistenceException,ServicioCodefacException
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
            switch (detalle.getTipoDocumentoEnum()) {
                case ACADEMICO:
                    afectarAcademico(detalle);
                    break;
                case INVENTARIO:
                    //Todo: Mejorar esta parte por el momento cuando es una proforma no proceso el tema del inventario
                    if (factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA)) {
                        break;
                    }

                    BodegaService bodegaService = new BodegaService();
                    Bodega bodegaVenta = bodegaService.obtenerBodegaVenta(factura.getSucursalEmpresa());
                    if (bodegaVenta == null) {
                        throw new ServicioCodefacException("No existe un tipo de Bodega de Venta Configurado");
                    }
                    afectarInventario(detalle, bodegaVenta);
                    break;
                case LIBRE:
                    //NO DEBE AFECTAR A NADA;
                    break;
                case PRESUPUESTOS:
                    afectarPresupuesto(detalle);
                    break;

            }
            
            //Hacer persistir los detalles porque sucedio un caso que por algun motivo no se grabaron
            entityManager.flush();
            detalle.setFactura(factura); //Hago este seteo por que si viene de una referencia anterior como una proforma puede generar errores
            entityManager.merge(detalle);
            
        }

            
    }
    
    public void grabarCartera(Factura factura) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarCarteraSinTransaccion(factura,null);
            }
        });
    }
    
    private void grabarCarteraSinTransaccion(Factura factura,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(factura, Cartera.TipoCarteraEnum.CLIENTE,carteraParametro);
    }
    
    private void afectarPresupuesto(FacturaDetalle detalle) throws RemoteException
    {
        
            PresupuestoService servicio = new PresupuestoService();
            Presupuesto presupuesto = servicio.buscarPorId(detalle.getReferenciaId());
            
            //Cambiar el estado al presupuesto para saber que ya fue facturadp
            presupuesto.setEstado(Presupuesto.EstadoEnum.FACTURADO.getLetra()); 
            
            //Cambiar el estado a la orden de trabajo del detalle para saber que ya no puede usar
            presupuesto.getOrdenTrabajoDetalle().setEstado(OrdenTrabajoDetalle.EstadoEnum.TERMINADO.getLetra());//Cambio el estado a terminado
            
            //Actualiza el estado de la orde de trabajo principal
            OrdenTrabajoService ordenTrabajoService=new OrdenTrabajoService();
            ordenTrabajoService.actualizarEstadoSinTransaccion(presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            
        

    }
    
    private void afectarAcademico(FacturaDetalle detalle) throws RemoteException
    {
        
            RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
            
            BigDecimal totalBruto=detalle.getSubtotalSinDescuentos();
            //El total es sin impuestos
            BigDecimal saldoPendiente=rubroEstudiante.getSaldo().subtract(totalBruto);
            
            if(detalle.getDescuento()!=null && detalle.getDescuento().compareTo(BigDecimal.ZERO)>0)
            {
                //Seteao los nuevos valores cuando tiene un descuento para no descuadrar reportes
                rubroEstudiante.setProcentajeDescuento(null); //TODO: esto pongo de esta manera por que si estaba grabando un descuento anterior el calculo seria muy confuso
                rubroEstudiante.setValorDescuento(detalle.getDescuento()); 
                rubroEstudiante.setValor(detalle.getSubtotalRestadoDescuentos().add(saldoPendiente));
            }            
            
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
            
        
        
    }
    
    /*private Kardex consultarOCrearStock(Producto producto, Bodega bodega) throws RemoteException, ServicioCodefacException
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

    }*/
    
    private void afectarInventario(FacturaDetalle detalle,Bodega bodega) throws RemoteException, ServicioCodefacException
    {

        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());

        Kardex kardex =ServiceFactory.getFactory().getKardexServiceIf().consultarOCrearStockSinPersistencia(producto, bodega);
        //Kardex kardex = consultarOCrearStock(producto, bodega);

        /**
         * Validacion pára verificar que exista un stock superior o igual en el
         * kardex segun lo que quieran facturar
         */
        BigDecimal cantidadFaltante = detalle.getCantidad().subtract(kardex.getStock());
        if(ParametroUtilidades.comparar(detalle.getFactura().getEmpresa(), ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO,EnumSiNo.NO))
        {
            //Si el stock que queremos facturar es mayor del existe lanzo una excepcion                
            if (detalle.getCantidad().compareTo(kardex.getStock()) > 0) 
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
        

        //detalle.getTipoDocumentoEnum().
        /**
         * ============================== FIN VALIDACION DE FACTURAR CON STOCK
         * NEGATIVO ======================
         */
        //TODO: Definir especificamente cual es la bodega principal
        //TODO: Analizar caso cuando se resta un producto especifico
        KardexService kardexService = new KardexService();
        KardexDetalle kardexDetalle = kardexService.crearKardexDetalleSinPersistencia(kardex, TipoDocumentoEnum.VENTA_INVENTARIO, detalle.getPrecioUnitario(), detalle.getCantidad());;
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
        
        //Solo ejecuto un merge por que anteriormente ya se ejecuto un persistent cuando no existe
        entityManager.merge(kardex); //Actualizo el kardex con la nueva referencia
       
    }
    
    /**
     * Metodo para verificar si tiene la opcion activa de generar ensamble y ver si se puede construir en ese momento
     */
    public void verificarConstruirEnsamble(Kardex kardex,BigDecimal cantidadFaltante,Boolean validarStockComponentes) throws RemoteException, ServicioCodefacException
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

    public void editar(Factura factura) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //facturaFacade.edit(factura);
                entityManager.merge(factura);
            }
        });
        
    }

    public List<Factura> obtenerTodos() {
        return facturaFacade.findAll();
    }

    //public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal) throws java.rmi.RemoteException {
    //    return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal);
    //}
    
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario) throws java.rmi.RemoteException {
        return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal,usuario,null,null);
    }
    
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision) throws java.rmi.RemoteException {
        return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal,usuario,vendedor,enviadoGuiaRemision);
    }
    
    public Long obtenerFacturasReporteTamanio(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision) throws java.rmi.RemoteException {
        return facturaFacade.listaConTamanio(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal,usuario,vendedor,enviadoGuiaRemision);
    }

    /**
     * TODO: Este metodo es temporal hasta poder grabar el costo en la misma factura
     * @deprecated 
     * @param facturas
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public Map<Factura,BigDecimal> obtenerCostoFacturas(List<Factura> facturas) throws RemoteException, ServicioCodefacException
    {
        Map<Factura,BigDecimal> mapCostos=new HashMap<Factura,BigDecimal>();
        
        for (Factura factura : facturas) {
            BigDecimal costoFactura=BigDecimal.ZERO;
            for (FacturaDetalle detalle : factura.getDetalles()) {
                
                KardexService kardexService = new KardexService();
                ReferenciaDetalleFacturaRespuesta referenciaDetalle = obtenerReferenciaDetalleFactura(detalle.getTipoDocumentoEnum(), detalle.getReferenciaId());
                if (referenciaDetalle.objecto != null) {
                    if(referenciaDetalle.tipoDocumentoEnum.equals(TipoDocumentoEnum.INVENTARIO))
                    {
                        Producto producto=(Producto) referenciaDetalle.objecto;
                        Kardex kardexTemp=kardexService.buscarKardexPorProducto(producto);
                        if(kardexTemp!=null)
                        {
                            BigDecimal costoTotalDetalle=kardexTemp.getCostoPromedio().multiply(detalle.getCantidad());
                            costoFactura=costoFactura.add(costoTotalDetalle);
                        }
                    }
                }
                
                /**
                 * Setear el costo obtenido
                 */
                mapCostos.put(factura, costoFactura);
                
            }
        }
        return mapCostos;
        
    }
    
    public List<Factura> obtenerFacturasActivas()
    {
        return facturaFacade.getFacturaEnable();
    }
    
    
    
    public void eliminarFactura(Factura factura) throws java.rmi.RemoteException,ServicioCodefacException
    {
        
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws java.rmi.RemoteException,ServicioCodefacException {
                    
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
                        
                    
                    
                }
            });
        
        
    }
    /*
    public Long obtenerSecuencialProforma() throws java.rmi.RemoteException
    {
        return 0;
    }
*/

    //TODO: Falta programar que se puedan ver solo facturas activas
    @Override
    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion,Empresa empresa) throws RemoteException {
        //Factura f;
        //f.getIdentificacion();
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       mapParametros.put("identificacion",identificacion);
       mapParametros.put("empresa",empresa);
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
    
    public List<Factura> consultarProformasReporte(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,ComprobanteEntity.ComprobanteEnumEstado estado) throws java.rmi.RemoteException,ServicioCodefacException
    {
        return getFacade().consultarProformasReporteFacade(cliente, fechaInicial, fechaFinal, empresa,estado);
    }

    public Factura grabar(Factura factura,Empleado empleado) throws ServicioCodefacException,java.rmi.RemoteException,ServicioCodefacException
    {
        factura.setVendedor(empleado);
        factura=grabar(factura);
        return factura;
    }
    
    public ReferenciaDetalleFacturaRespuesta obtenerReferenciaDetalleFactura(TipoDocumentoEnum tipoDocumentoEnum,Long referenciaId) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ReferenciaDetalleFacturaRespuesta respuesta=null;
        
                CatalogoProducto catalogoProducto=null;
                if (tipoDocumentoEnum != null) {
                    switch (tipoDocumentoEnum) {
                        case ACADEMICO:
                            RubroEstudiante rubroEstudiante;
                            rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(referenciaId);

                            catalogoProducto = rubroEstudiante.getRubroNivel().getCatalogoProducto();
                            respuesta=new ReferenciaDetalleFacturaRespuesta(
                                    catalogoProducto,
                                    rubroEstudiante.getId(), 
                                    tipoDocumentoEnum, 
                                    rubroEstudiante);

                            break;

                        case LIBRE:
                        case INVENTARIO:
                            Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(referenciaId);
                            catalogoProducto = producto.getCatalogoProducto();
                            respuesta=new ReferenciaDetalleFacturaRespuesta(
                                    catalogoProducto,
                                    producto.getIdProducto(), 
                                    tipoDocumentoEnum, 
                                    producto);
                            respuesta.codigoPrincipal=producto.getCodigoPersonalizado();
                            break;

                        case PRESUPUESTOS:
                            Presupuesto presupuesto = ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(referenciaId);
                            catalogoProducto = presupuesto.getCatalogoProducto();
                            respuesta=new ReferenciaDetalleFacturaRespuesta(
                                    catalogoProducto,
                                    presupuesto.getId(), 
                                    tipoDocumentoEnum, 
                                    presupuesto);
                            break;
                    }
                }
            
        return respuesta;
    }
    
    private void validacionInicialProforma(Factura proforma) throws RemoteException,ServicioCodefacException 
    {
        if(proforma==null)
        {
                return;
        }
        
        if(getFacade().verificarFacturaActivaIngresadaConPedido(proforma))
        {
            throw new ServicioCodefacException("No se puede hacer más de 2 facturas con el mismo pedido");
        }
    }
}
