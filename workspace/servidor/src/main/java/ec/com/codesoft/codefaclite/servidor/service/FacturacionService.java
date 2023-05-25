/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadReportes;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesImpresora;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import static ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.obtenerComprobanteData;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.PrestamoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroEstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.pos.CajaPermisoService;
import ec.com.codesoft.codefaclite.servidor.service.pos.CajaSesionService;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.TurnoAsignado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DiaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SignoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.FacturaParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.UtilidadReport;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ProductoConversionPresentacionRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ArchivoComprobacionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.hora.UtilidadesHora;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesNumeros;
import ec.com.codesoft.codefaclite.utilidades.xml.UtilidadesXml;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class FacturacionService extends ServiceAbstract<Factura, FacturaFacade> implements FacturacionServiceIf
{
    CajaPermisoService cajaPermisoService=new CajaPermisoService();
    CajaSesionService cajaSesionService=new CajaSesionService();
    KardexService kardexService=new KardexService();
    FacturaDetalleService facturaDetalleService=new FacturaDetalleService();
    ProductoService productoService=new ProductoService();
    LoteService loteService=new LoteService();
    RubroEstudianteService rubroEstudianteService=new RubroEstudianteService();
    PresupuestoService presupuestoService=new PresupuestoService();
    

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
                        setearDatosClienteYDistribuidor(liquidacionCompra);
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
    
    public Factura grabarProformaYComandaSinTransaccion(Factura proforma) throws RemoteException,ServicioCodefacException
    {
        validacionInicialFacturar(proforma, null, CrudEnum.CREAR);
        //Agregado vendedor de forma automatica si el usuario tiene relacionado un empleado con departamento de ventas
        asignarVendedorProforma(proforma);

        proforma.setSecuencial(obtenerSecuencialProformas(proforma.getEmpresa(),proforma.getCodigoDocumentoEnum()).intValue());
        proforma.setEstado(GeneralEnumEstado.ACTIVO.getEstado());

        //proforma.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
        setearDatosClienteYDistribuidor(proforma);
        grabarDetallesFacturaSinTransaccion(proforma); //Todo: Por el momento dejo comentando la proforma que se descuente del inventario
        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar

        /**
         * Gestionar el tema de reservas en el INVENTARIO
         */
        //grabarProductosReservados(proforma);
        kardexService.grabarProductosReservadosSinTransaccion(proforma);
        return proforma;
    }
    
    public Factura grabarProforma(Factura proforma) throws RemoteException,ServicioCodefacException
    {            
            //validacionInicialFacturar(proforma,null,CrudEnum.CREAR);
        
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws RemoteException, ServicioCodefacException {
                       /* validacionInicialFacturar(proforma,null, CrudEnum.CREAR);
                        //Agregado vendedor de forma automatica si el usuario tiene relacionado un empleado con departamento de ventas
                        asignarVendedorProforma(proforma);
                    
                        proforma.setSecuencial(obtenerSecuencialProformas(proforma.getEmpresa()).intValue());
                        proforma.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                        
                        proforma.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
                        setearDatosClienteYDistribuidor(proforma);
                        grabarDetallesFacturaSinTransaccion(proforma); //Todo: Por el momento dejo comentando la proforma que se descuente del inventario
                        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar
                        */
                        /**
                         * Gestionar el tema de reservas en el INVENTARIO
                         */
                        /*grabarProductosReservados(proforma);*/
                    
                        /**
                        * Informar por CORREO que la proforma fue enviada
                        * correctamente
                        */
                        proforma.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
                        grabarProformaYComandaSinTransaccion(proforma);
                        
                        //JasperPrint jasperReporte = FacturaModelControlador.getReporteJasperProforma(proforma,FacturaModelControlador.FormatoReporteEnum.A4);
                        //UtilidadesImpresora.PrintReportToPrinter(jasperReporte);
                        
                        enviarCorreoProforma(proforma);
               }
            });
            
        
        return proforma;
    }
    
    public Factura grabarComanda(Factura proforma,SessionCodefac sessionCodefac) throws RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                proforma.setCodigoDocumento(DocumentoEnum.COMANDA.getCodigo());
                grabarProformaYComandaSinTransaccion(proforma);
                imprimirComanda(proforma, sessionCodefac);
            }
        });
        return proforma;
    }
    
    private void imprimirComanda(Factura proforma,SessionCodefac sessionCodefac)
    {
        String cantidadStr=ParametroUtilidades.obtenerValorParametro(sessionCodefac.getEmpresa(),ParametroCodefac.COPIAS_IMPRESORA_COMANDA);
        if(!UtilidadesTextos.verificarNullOVacio(cantidadStr))
        {
            Integer cantidad=Integer.parseInt(cantidadStr);
            if(cantidad>0)
            {
                //List<String> impresoraList=new ArrayList<String>();
                JasperPrint jasperReporte = FacturaModelControlador.getReporteTicket(proforma, sessionCodefac);
                //Impresora 1
                String nombreImpresoraDefecto=ParametroUtilidades.obtenerValorParametro(sessionCodefac.getEmpresa(),ParametroCodefac.IMPRESORA_DEFECTO_COMANDA);
                if(!UtilidadesTextos.verificarNullOVacio(nombreImpresoraDefecto)&& !nombreImpresoraDefecto.equals("null"))
                {
                    UtilidadesImpresora.printReportToPrinter(jasperReporte, cantidad,nombreImpresoraDefecto);
                }
                
                //Impresora 2
                nombreImpresoraDefecto=ParametroUtilidades.obtenerValorParametro(sessionCodefac.getEmpresa(),ParametroCodefac.IMPRESORA_DEFECTO_COMANDA_2);
                if(!UtilidadesTextos.verificarNullOVacio(nombreImpresoraDefecto)&& !nombreImpresoraDefecto.equals("null"))
                {
                    UtilidadesImpresora.printReportToPrinter(jasperReporte, cantidad,nombreImpresoraDefecto);
                }
                

            }
        }
    }
    
    /**
     * Metodo que permite grabar las cantidades reservadas en los kardex
     * @param factura 
     */
    /*private void grabarProductosReservados(Factura factura)
    {
        List<FacturaDetalle> detalles=factura.getDetalles();
        for (FacturaDetalle detalle : detalles) 
        {
            if(detalle.getReservadoEnum()!=null && detalle.getReservadoEnum().equals(EnumSiNo.SI))
            {
                Kardex kardex=detalle.getKardex();                
                kardex.procesarReserva(detalle.getCantidad(),SignoEnum.POSITIVO);
                //kardex.setStock(kardex.getStock().subtract(detalle.getCantidad()));
                //kardex.setReserva(kardex.getReserva().add(detalle.getCantidad()));
                
                entityManager.merge(kardex);
            }
            
        }
        
    }*/
    
    public void enviarCorreoNVI(Factura notaVentaInterna) throws RemoteException,ServicioCodefacException
    {
        try {
            ComprobanteDataInterface dataFactura= obtenerComprobanteData(notaVentaInterna);
            ComprobanteServiceIf comprobanteService=ServiceFactory.getFactory().getComprobanteServiceIf();
            byte[] byteReporte=comprobanteService.getReporteComprobanteComprobante(dataFactura,notaVentaInterna.getUsuario(),notaVentaInterna.getClaveAcceso());
            JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
            
            String secuencialStr=notaVentaInterna.getSecuencial()+"";
            Map<String, String> mapParametro = new HashMap<String, String>();
            mapParametro.put("nombre_documento","Nota Venta Interna");
            mapParametro.put("numeroProforma",secuencialStr);
            mapParametro.put("nombreCliente", notaVentaInterna.getRazonSocial());
            mapParametro.put("empresa", notaVentaInterna.getEmpresa().obtenerNombreEmpresa());
            //mapParametro
            CodefacMsj mensaje = MensajeCodefacSistema.VentasMensaje.NVI_ENVIADA_CORREO.agregarParametros(mapParametro);
            
            enviarReporteCorreo(jasperPrint, notaVentaInterna.getEmpresa(), "Comprobante", notaVentaInterna.getPreimpreso(), mensaje,Arrays.asList(notaVentaInterna.obtenerCorreosStr()));
        } catch (IOException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void enviarCorreoProforma(Factura proforma) throws RemoteException,ServicioCodefacException
    {
        //TODO: Agregar para poner un validacion previa para evitar construir un reporte cuando no tenga correos a donde enviar
        List<String> destinatarios = Arrays.asList(proforma.obtenerCorreosStr());

        String secuencialStr=proforma.getSecuencial()+"";
        Map<String, String> mapParametro = new HashMap<String, String>();
        mapParametro.put("numeroProforma",secuencialStr);
        mapParametro.put("nombreCliente", proforma.getRazonSocial());
        mapParametro.put("empresa", proforma.getEmpresa().obtenerNombreEmpresa());
        //mapParametro
        CodefacMsj mensaje = MensajeCodefacSistema.ProformasMensajes.PROFORMA_ENVIADA_CORREO.agregarParametros(mapParametro);
        //TODO: Verificar que no exista problema que los correos vienen separados por coma y no por arreglos
        
        JasperPrint jasperReporte = FacturaModelControlador.getReporteJasperProforma(proforma,FacturaModelControlador.FormatoReporteEnum.A4);
        enviarReporteCorreo(jasperReporte, proforma.getEmpresa(), "Proforma", secuencialStr, mensaje, destinatarios);
        //Controlador
        /*JasperPrint jasperReporte = FacturaModelControlador.getReporteJasperProforma(proforma,FacturaModelControlador.FormatoReporteEnum.A4);
        String pathReporte = UtilidadReportes.grabarArchivoJasperTemporal(jasperReporte);
        Map<String, String> mapPathFiles = new HashMap<String, String>();
        mapPathFiles.put("proforma #" + secuencialStr+".pdf", pathReporte);
        
        CorreoCodefac correoCodefac = new CorreoCodefac();
        correoCodefac.enviarCorreo(
        proforma.getEmpresa(),
        mensaje.getMensaje(),
        mensaje.getTitulo(),
        destinatarios,
        mapPathFiles);*/
    }
    
    /**
     * Ver si se puede hacer un metodo muy general para enviar cualquier comprobante
     */
    private void enviarReporteCorreo(JasperPrint jasperReporte,Empresa empresa,String nombreArchivo,String secuencialStr,CodefacMsj mensaje,List<String> destinatarios)
    {
        try {
            //Si no existen destinarios cancelo el envio a los correos
            if (destinatarios.size() == 0 || destinatarios.get(0).trim().isEmpty()) {
                return;
            }
            
            String pathReporte = UtilidadReportes.grabarArchivoJasperTemporal(jasperReporte);
            Map<String, String> mapPathFiles = new HashMap<String, String>();
            mapPathFiles.put( nombreArchivo+ "#"+ secuencialStr + ".pdf", pathReporte);
            
            CorreoCodefac correoCodefac = new CorreoCodefac();
            correoCodefac.enviarCorreo(
                    empresa,
                    mensaje.getMensaje(),
                    mensaje.getTitulo(),
                    destinatarios,
                    mapPathFiles);
        } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Estos datos se graban en la misma factura para poder hacer un reporte mas rapido y evitar hacer tantas consultas
     * @param proforma 
     */
    private void setearDatosDistribuidor(Factura venta) throws RemoteException, ServicioCodefacException
    {
        //Si la proforma tiene una zona la grabo con los datos de la oficina del cliente
        if(venta.getSucursal()!=null && venta.getSucursal().getZona()!=null)
        {
            venta.setZonaId(venta.getSucursal().getZona().getId());
            venta.setZonaNombre(venta.getSucursal().getZona().getNombre());
        }
        
        //Si la venta viene previamente de una proforma y tiene datos de los vendedores entonces selecciono los mismos valores
        Factura proformaTmp=venta.getProforma();
        if(proformaTmp==null)
        {
            //Si el cliente tiene un vendedor consulto a que ruta pertenece el cliente
            if(venta.getVendedor()!=null)
            {
                RutaService rutaService=new RutaService();
                Integer dia=UtilidadesFecha.obtenerDiaSemana(UtilidadesFecha.castDateUtilToSql(venta.getFechaEmision())); //Por el momento busca la ruta segun el día que esta generando la proforma o factura
                DiaEnum diaEnum=DiaEnum.buscarPorNumero(dia);
                Ruta ruta=rutaService.consultarRutaActivaPorVendedorYCliente(venta.getVendedor(),venta.getSucursal(),diaEnum);
                if(ruta!=null)
                {
                    venta.setRutaId(ruta.getId());
                    venta.setRutaNombre(ruta.getNombre());
                }
            }
        }
        
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
        validacionInicialFacturar(proforma,null,CrudEnum.EDITAR);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                setearDatosClienteYDistribuidor(proforma);
                entityManager.merge(proforma);                
                eliminarDetalles(facturaDetalleService.buscarPorFactura(proforma), proforma.getDetalles());                
            }
        });
        imprimirLogFactura(proforma, CrudEnum.EDITAR);
        return proforma;
    }
    
    
    /**
     * Metodo que permite setear los datos del cliente en la venta para poder datos estaticos para realizar control de la venta
     * @param venta 
     */
    private void setearDatosClienteYDistribuidor(Factura venta) throws RemoteException, ServicioCodefacException
    {
        if(venta.getCliente()!=null)
        {
            venta.setRazonSocial(venta.getCliente().getRazonSocial());
            venta.setIdentificacion(venta.getCliente().getIdentificacion());
        }
            
        if(venta.getSucursal()!=null)
        {
            venta.setDireccion(venta.getSucursal().getDireccion());
            venta.setTelefono(venta.getSucursal().getTelefonoCelular()); //todo: ver si hago un metodo para obtener los telefonos 
        }
        
        setearDatosDistribuidor(venta);
    }
    
    private void setearDatosPorDefecto(Factura factura,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException
    {   
        //Fecha de cuando estamos generando el documento
        factura.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        
        //Valor por defecto para indicar que no esta emitida una guia de remision
        factura.setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.NO);
        
        ///Agregar estado de la nota de credito
        //factura.setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.SIN_ANULAR.getEstado());
        factura.setEstadoNotaCreditoEnum(Factura.EstadoNotaCreditoEnum.SIN_ANULAR);
        
        //Agregar por defecto la fecha de vecimiento de la factura cuando tenga habilitado el crédito y los días de crédito
        if(carteraParametro!=null)
        {
            if(carteraParametro.habilitarCredito && carteraParametro.diasCredito>0)
            {
                java.util.Date fechaVencimiento=UtilidadesFecha.sumarDiasFecha(factura.getFechaEmision(),carteraParametro.diasCredito);
                String fechaStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(fechaVencimiento);                
                factura.addDatoAdicional(new FacturaAdicional(ComprobanteAdicional.CampoDefectoEnum.FECHA_VENCIMIENTO.getNombre(), fechaStr,ComprobanteAdicional.Tipo.TIPO_OTRO));
            }
        }
        
        
        //Setear campos adicionales del detalle
        for (FacturaDetalle detalle : factura.getDetalles()) 
        {
            //Por defecto solo genera un unico codigo
            detalle.setCantidadPresentacion(BigDecimal.ONE);
            TipoDocumentoEnum tipoReferenciaEnum=detalle.getTipoDocumentoEnum();
            
            //TODO: @Deprecated, Optimizar como un cache
            ReferenciaDetalleFacturaRespuesta respuesta =obtenerReferenciaDetalleFactura(tipoReferenciaEnum, detalle.getReferenciaId());
            if (respuesta.objecto != null) {
                switch (respuesta.tipoDocumentoEnum) {
                    case LIBRE:
                    case INVENTARIO:
                        Producto producto= (Producto) respuesta.objecto;
                        ProductoPresentacionDetalle presentacionDetalle= producto.buscarPresentacionDetalleProducto();
                        if(presentacionDetalle!=null)
                        {
                            detalle.setCantidadPresentacion(presentacionDetalle.getCantidad());
                        }
                        break;
                    case ACADEMICO:
                        
                        break;
                }

            } 
        }
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
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() 
        {
            
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException 
            {   
                
                setearDatosClienteYDistribuidor(factura);
                
                
                //Validaciones iniciales de la factura
                validacionInicialFacturar(factura,carteraParametro,CrudEnum.CREAR);
                
                //Agrega datos adcional como por ejemplo la fecha de creacion de la factura
                setearDatosPorDefecto(factura,carteraParametro);
                               
                //Metodo que va a grabar la factura
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
                
                //TODO Esta validación la realizo porque no existe una variable global que me permita saber si se realiza POS
                List<CajaPermiso> cajasPermisosList=cajaPermisoService.buscarPermisosCajasActivos(factura.getUsuario());
                //List<CajaPermiso> cajasPermisosList=factura.getUsuario().buscarPermisosCajasActivosService();
                if(cajasPermisosList != null && !cajasPermisosList.isEmpty())
                {
                    agregarDatosParaCajaSession(factura);
                }
                
                
                //Despues de grabar genero inmediatamente un flush para evitar perder la transacción por causas como perdida de energia
                entityManager.flush();
                
                //Si se grabo una nota de venta interna intento enviar al correo
                if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
                {
                    enviarCorreoProforma(factura);
                }
                
                imprimirLogFactura(factura, CrudEnum.CREAR);
                //throw new RollbackException("Forzando un rollback"); // Lanzar una excepción marcada
            }
        });
        
        /**
         * Ejecuto en un proceso independiente para no interferir con la velocidad el proceso actual
         * TODO: Buscar una solucion mas elegante
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArchivoComprobacionCodefac.getInstance().grabarDatosComprobacion();
                Logger.getLogger(FacturacionService.class.getName()).log(Level.INFO,"Grabando datos en el archivo de configuracion ..");
            }
        }).start();
        
        return factura;
    }
    private void imprimirLogFactura(Factura factura,CrudEnum crudEnum)
    {
        String accionFactura="Creando Factura # :";
        if(crudEnum.equals(CrudEnum.EDITAR))
        {
            accionFactura="Editando Factura #: ";
        }
        //Genero un LOG DE LAS VENTAS para tener un registro en los logs por algun caso si la base de datos falla
        Logger.getLogger(FacturacionService.class.getName()).log(Level.INFO, accionFactura + factura.getPreimpreso() + " | fecha de emisión: " + factura.getFechaEmision() + " | cliente: " + factura.getRazonSocial() + " | documento: " + factura.getCodigoDocumentoEnum().getNombre() + " | iva: " + factura.getIva() + " | total: " + factura.getTotal());
        //Genero un Log de los detalles de las ventas
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.INFO, "Código: " + facturaDetalle.getCodigoPrincipal() + " | Nombre: " + facturaDetalle.getDescripcion() + " | ValUnit: " + facturaDetalle.getPrecioUnitario() + " | Cantidad: " + facturaDetalle.getCantidad() + " | Desc: " + facturaDetalle.getDescuento());
        }
    }

    //TODO: Tomar en cuenta que este metodo no toma en cuenta la cartera y luego puede generar problemas
    @Deprecated
    public Factura grabar(Factura factura) throws RemoteException, ServicioCodefacException {
        
        return grabar(factura, null, null);
        /*ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validacionInicialFacturar(factura,CrudEnum.CREAR);
                grabarSinTransaccion(factura,null);
                
            }
        });
        return factura;*/
        
    }
    
    public FacturaLoteRespuesta grabarLote(List<FacturaParametro> facturaList) throws RemoteException,ServicioCodefacException {
        
        //TODO: Evento que luego me permite realizar una auditoria
        Logger.getLogger(FacturacionService.class.getName()).log(Level.INFO,"Procesando en lote :"+ facturaList.size()+" documentos");
        
        FacturaLoteRespuesta respuesta=new FacturaLoteRespuesta();
        
        for (FacturaParametro factura : facturaList) 
        {
            try
            {
                //´TODO: Por el momento todas las facturas en lote se van a facturar con la fecha actual
                //factura.factura.setFechaEmision(UtilidadesFecha.getFechaHoy());                
                Factura facturaGrabada=grabar(factura.factura,factura.prestamo,factura.carteraPrestamo);
                //Si la factura se termina de procesar correctamento agrego a la respuesta
                respuesta.agregarFacturaProcesada(factura.factura);
            }
            catch(ServicioCodefacException e)
            {
                Logger.getLogger(FacturacionService.class.getName()).log(Level.INFO,"Error procesando lote proforma :"+factura.factura.getPreimpreso());
                //Agrego a la lista las facturas que tienen problemas y no fueron procesadas
                respuesta.agregarFacturaNoProcesada(new FacturaLoteRespuesta.Error(factura.factura, e.getMessage()));
            }
        }
        
        return respuesta;
        
    }
    
    public void validacionInicialFacturar(Factura factura,CarteraParametro carteraParametro,CrudEnum modo) throws ServicioCodefacException, RemoteException
    { 
        if(modo.equals(CrudEnum.CREAR))
        {
            //TODO: Temporalmente usa esta solucion por que aveces viene con un secuencial con cero cuando hago una preimpresion pero no se a que momento se setea ese valor
            if(factura.getSecuencial()!=null && factura.getSecuencial()==0)
            {
                factura.setSecuencial(null);
            }
            
            if(factura.getId()!=null ||  factura.getSecuencial()!=null && factura.getSecuencial()==0 || factura.getClaveAcceso()!=null)
            {                
                throw new ServicioCodefacException("Error al grabar el documento, se va a generar un documento repetido ");
            }
            
            //Verificar que no pueda poner una fecha superior a la del día de hoy
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
            {
                if(factura.getFechaEmision().compareTo(UtilidadesFecha.getFechaHoraHoy())>0)
                {
                    throw new ServicioCodefacException("Error al grabar el documento, no se puede grabar con fecha superior a la actual ");
                }
            }
           

        }
       
               
        
        if (factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA)) {
            
            //TODO: Mejorar esta parte, no utilizo el metodo de utilidades parametro porque si utilizo voy a tener problemas de rendimiento
            //ALERTA: Ver una solución alterna
            ParametroCodefacService parametroService = new ParametroCodefacService();
            ParametroCodefac parametroCodefac = parametroService.getParametroByNombre(ParametroCodefac.NOTA_VENTA_INTERNA_IVA, factura.getEmpresa());
            EnumSiNo agregarIvaNVI = EnumSiNo.NO;
            if (parametroCodefac != null) {
                if (!UtilidadesTextos.verificarNullOVacio(parametroCodefac.valor)) {
                    EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra(parametroCodefac.valor);
                    if (enumSiNo != null && enumSiNo.equals(EnumSiNo.SI)) {
                        agregarIvaNVI = EnumSiNo.SI;
                    }
                }
            }
            
            if(agregarIvaNVI.equals(EnumSiNo.NO))
            {
                if(factura.getIva().compareTo(BigDecimal.ZERO)>0)
                {
                    throw new ServicioCodefacException("Error las NOTAS DE VENTA INTERNA no pueden llevar IVA ");
                }
            }
        }
        
        //Solo valido los datos de clientes cuando es un DOCUMENTO diferente de proforma, por que si se puede grabar sin datos para proforma en especial para las comandas
        if(!factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA) && !factura.getCodigoDocumentoEnum().equals(DocumentoEnum.COMANDA))
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
            
            //Validacion de MONTOS SUPERIORES A $50 PARA CONSUMIDORES FINALES EN DOCUMENTOS LEGALES
            if (factura.getCliente().isClienteFinal() && factura.getCodigoDocumentoEnum().getDocumentoLegal()) 
            {
                if (factura.getTotal().compareTo(ParametrosSistemaCodefac.MONTO_MAXIMO_VENTAS_CONSUMIDOR_FINAL) > 0) {
                    throw new ServicioCodefacException("El Monto no puede ser superior a $50 para el CLIENTES FINALES");
                }
            }

            Persona.TipoIdentificacionEnum tipoIdentificacionEnum = factura.getCliente().getTipoIdentificacionEnum();
            if (tipoIdentificacionEnum == null) 
            {
                throw new ServicioCodefacException("Cliente no configurado el tipo de identificación");
            }

            //Fecha:15/11/2021 agregada validacion de la direccion por que el sri no permite
            if (factura.getSucursal().getDireccion() == null || factura.getSucursal().getDireccion().trim().isEmpty()) 
            {
                throw new ServicioCodefacException("No se puede emitir una factura sin dirección");
            }
            
            //

        }
        

        
        //Validar el punto de emision para cualquier tipo de documento que no sea una proforma
        if(!factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA) && !factura.getCodigoDocumentoEnum().equals(DocumentoEnum.COMANDA) && factura.getPuntoEmisionId()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin tener un punto de emisión configurado");
        }
        
        if(modo.equals(CrudEnum.CREAR))
        {
            if(carteraParametro!=null)
            {
                //Si la factura tiene credito verifico que el cliente tenga esa opcion
                if(carteraParametro.habilitarCredito)
                {
                    if(factura.getCliente().getHabilitarCreditoEnum()!=null )
                    {
                        if(factura.getCliente().getHabilitarCreditoEnum().equals(EnumSiNo.NO))
                        {
                            throw new ServicioCodefacException("El cliente no tiene habilitada la opción para generar ventas con Crédito ");
                        }
                    }
                }            
            }
        }
        
        //TODO:Optimizar esta parte para poner en otra parte
        PuntoEmisionService puntoEmisionService = new PuntoEmisionService();
        PuntoEmision puntoEmision = puntoEmisionService.buscarPorId(factura.getPuntoEmisionId());
        
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
                
                //Verificar que los decimales de la cantidades con tengan mas de 6
                int numeroDecimalesCantidades=UtilidadesNumeros.numeroDecimales(detalle.getCantidad()+"");
                if(numeroDecimalesCantidades>6)
                {
                    throw new ServicioCodefacException("Error con las cantidades que tiene más de 6 decimales en el producto "+detalle.getDescripcion()+" ");
                }
                
                                
                
                //Validacion para hacer remplazo de caracteres que son incopatibles con la facturacion electronica
                //detalle.setDescripcion(detalle.getDescripcion().replace("“","\"").replace("”","\""));
                

                //Validacion automatica de los espacios en blanco de los productos
                if (puntoEmision != null) {
                    if (puntoEmision.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA)) {
                        detalle.setDescripcion(UtilidadesXml.normalizarCaracteresNoPermitidosUTF8(detalle.getDescripcion()));
                        detalle.setCodigoPrincipal(UtilidadesXml.normalizarCaracteresNoPermitidosUTF8(detalle.getCodigoPrincipal()));
                        /*detalle.setDescripcion(detalle.getDescripcion().replace("\n", " "));
                        detalle.setDescripcion(detalle.getDescripcion().replace("\r", " "));
                        
                        //TODO: Ver si se puede poner estos codigos en otra seccion 
                        detalle.setCodigoPrincipal(codigoPrincipal);
                        detalle.setDescripcion(detalle.getDescripcion().replace("”","''"));
                        detalle.setDescripcion(detalle.getDescripcion().replace(""," "));
                        detalle.setDescripcion(detalle.getDescripcion().replace("Ð","Ñ"));*/
                        
                        //UtilidadesXml.convertirDocumentToString(path)
                    }
                    
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
                //Verificar cual es el item o los items que tiene problemas 
                
                throw new ServicioCodefacException("Error de inconsistencia en el detalle, existe una diferencia de "+diferencia+" en el producto "+ detalle.getDescripcion()+"\nPosibles Causas:\n - El producto fue editado el precio\n - El producto requiere más decimales para el calculo exacto\n ");
            }
            
            //Validar que los detalles de las facturas no puedan tener más de 300 caracteres
            if(detalle.getDescripcion().length()>300)
            {
                throw new ServicioCodefacException("Los detalles de las facturas no pueden tener más de 300 caracteres");
            }
            
            //Validar que no se pueda grabar los detalles con tipos de datos no permitidos
            if(detalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.VENTA))
            {
                throw new ServicioCodefacException("El producto "+detalle.getDescripcion()+" tiene un tipo de documento invalido");
            }
            
            //validar que si tiene un documento de tipo de IDENTIFICACION SIN DEFINIR solo permita realizar procesos INTERNOS        
            if (factura.getCliente()!=null && factura.getCliente().getTipoIdentificacionEnum()!=null && factura.getCliente().getTipoIdentificacionEnum().equals(Persona.TipoIdentificacionEnum.SIN_DEFINIR)) 
            {
                Boolean isDocumentoLegal = factura.getCodigoDocumentoEnum().getDocumentoLegal();

                if (isDocumentoLegal) {
                    throw new ServicioCodefacException("No se puede emitir DOCUMENTOS LEGALES al cliente con el tipo de IDENTIFICACIÓN SIN DEFINIR");
                }
            }
            
            //Verificar que por algun motivo los detalles del descuento no pueden ser superiores a los valores de los precios unitarios
            if(detalle.getTotal().compareTo(BigDecimal.ZERO)<0)
            {
                throw new ServicioCodefacException("El producto "+detalle.getDescripcion()+" tiene un tipo un descuento superior al permitido");
            }
            
        }   
        
        if(!factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA))
        {
            
            if(modo.equals(CrudEnum.CREAR))
            {
                if(!ParametrosSistemaCodefac.PROFORMA_MODO_PRUEBA)
                {
                    validacionInicialProforma(factura.getProforma());            
                }
            }
            
        }
        
        
        //TODO: Por el momento dejo desctivado por que consume muchos recursos
        //validacion especial cuanod tiene problemas en la base de datos
        //validacionBaseDatosFacturas();
                
    }
    
    /**
     * Metodo que hacer verificaciones de problemas de incoherencias con la base de datos
     */
    public void validacionBaseDatosFacturas() throws ServicioCodefacException, RemoteException
    {
        if(AbstractFacade.buscarClavesRepetidasBaseDatos(Factura.NOMBRE_TABLA, Factura.NOMBRE_PK))
        {
            throw new ServicioCodefacException("Error con Datos repetido de PK "+Factura.NOMBRE_TABLA);
        }
    }
    
    private void asignarProformaAFactura(Factura factura)
    {
        Factura proforma=factura.getProforma();
        if(proforma!=null)
        {
            if(!ParametrosSistemaCodefac.PROFORMA_MODO_PRUEBA)
            {
                //Cambio el estado de la proforma a facturado para saber que ya no puedo volver a utilizar
                proforma.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.FACTURADO_PROFORMA);
                entityManager.merge(proforma);
            }
        }
    }
    
    private void asignarClaveAccesoDocumentosNoElectronicos(Factura factura)
    {
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            factura.setClaveAcceso(factura.getPuntoEstablecimiento()+""+factura.getPuntoEmision()+""+factura.getSecuencial()+"");
        }
    }
    
    public Factura grabarSinTransaccion(Factura factura,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        

        //TODO:Ver si se mueve a la primera parte donde se setean los datos
        //Parece que no debo mover por que otros metodos recien desde este punto empiezan grabar
        setearDatosClienteYDistribuidor(factura);    
        
        //TODO:Analizar mover a fuera , o pasar los metodos setear a este punto
        asignarVendedorAutomatico(factura);
        
        //Cambiar el estado si viene de un pedido si fuera el caso
        asignarProformaAFactura(factura);
        
        //Si es nota de venta generar un número de autorización cualquiera
        asignarClaveAccesoDocumentosNoElectronicos(factura);

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
        //IMPORTANTE: Este flush es importante por que si no esta causa conflicto con otro datos
        entityManager.flush();
        //TODO: Ver si es necesario grabar en esta parte
        List<FacturaDetalle> facturaDetalleList=factura.getDetalles();
        List<FormaPago> facturaFormasPagoList=factura.getFormaPagos();
        List<FacturaAdicional> facturaAdicionalList=factura.getDatosAdicionales();
        factura.setFormaPagos(null);
        factura.setDetalles(null);
        factura.setDatosAdicionales(null);
        //factura.setMesa(null);
        entityManager.persist(factura);
        entityManager.flush();
        
        //Grabar las formas de pago primero este artificio hago por el momento por que estaba causando conflicto al grabar varias facturas en lote
        for (FormaPago formaPago : facturaFormasPagoList) {            
            formaPago.setFactura(factura);
            entityManager.persist(formaPago);       
            entityManager.flush();
        }
        
        for (FacturaAdicional facturaAdicional : facturaAdicionalList) {
            facturaAdicional.setFactura(factura);
            entityManager.persist(facturaAdicional);
            entityManager.flush();
        }

        /**
         * Actualizar valores del inventario con el kardex
         */
        for (FacturaDetalle detalle : facturaDetalleList) {            
            //Verificar a que modulo debe afectar los detalles
            switch (detalle.getTipoDocumentoEnum()) {
                case ACADEMICO:
                    afectarAcademico(detalle);
                    break;
                case INVENTARIO:
                    //Todo: Mejorar esta parte por el momento cuando es una proforma no proceso el tema del inventario
                    //TODO: Parece que la parte de proforma deveria estar en la parte superior por que no debe afectar a ningun otro modulo
                    if (factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA) || factura.getCodigoDocumentoEnum().equals(DocumentoEnum.COMANDA) ) 
                    {
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
            entityManager.persist(detalle);
            entityManager.flush();
            
        }

        //Volver a setear la lista a la factura despues de grabar de forma individual los detalles
        factura.setDetalles(facturaDetalleList);
        factura.setFormaPagos(facturaFormasPagoList);
        factura.setDatosAdicionales(facturaAdicionalList);
            
    }
    
    public void grabarCartera(Factura factura) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                CarteraParametro carteraParametro=new CarteraParametro(true,0);
                grabarCarteraSinTransaccion(factura,carteraParametro);
            }
        });
    }
    
    /**
     * Metodo que permite grabar la cartera y generar el cruce automatico si es el caso para cerrar la cuenta
     * @param factura documento a procesar
     * @param carteraParametro objecto que tiene datos de configuracion para el cruce
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    private void grabarCarteraSinTransaccion(Factura factura,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(factura, Cartera.TipoCarteraEnum.CLIENTE,carteraParametro,CrudEnum.CREAR,ModoProcesarEnum.NORMAL);
    }
    
    private void afectarPresupuesto(FacturaDetalle detalle) throws RemoteException
    {
        
            PresupuestoService servicio = new PresupuestoService();
            Presupuesto presupuesto = servicio.buscarPorId(detalle.getReferenciaId());
            
            presupuesto.setPersona(detalle.getFactura().getCliente());
            
            //Cambiar el estado al presupuesto para saber que ya fue facturadp
            presupuesto.setEstadoEnum(Presupuesto.EstadoEnum.FACTURADO); 
            
            OrdenTrabajoDetalle ordenTrabajoDetalle=presupuesto.getOrdenTrabajoDetalle();
            OrdenTrabajo ordenTrabajo=ordenTrabajoDetalle.getOrdenTrabajo();
            
            //Cambiar el estado a la orden de trabajo del detalle para saber que ya no puede usar
            ordenTrabajoDetalle.setEstado(OrdenTrabajoDetalle.EstadoEnum.TERMINADO.getLetra());//Cambio el estado a terminado
            ordenTrabajo.setCliente(detalle.getFactura().getCliente());
            //ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.FACTURADO);
            
            //Actualiza el estado de la orde de trabajo principal
            OrdenTrabajoService ordenTrabajoService=new OrdenTrabajoService();
            ordenTrabajoService.actualizarEstadoSinTransaccion(ordenTrabajo);
            
            //Agregado una referencia de la venta al presupuesto para luego consultar de una manera más rapida
            presupuesto.setFactura(detalle.getFactura());
            
            //Actualizar los datos de la OT y PRESUPUESTOS
            entityManager.merge(presupuesto);
            //entityManager.merge(ordenTrabajo);
            entityManager.merge(ordenTrabajoDetalle);            
            entityManager.flush();

    }
    
    private void afectarAcademico(FacturaDetalle detalle) throws RemoteException
    {
        
            RubroEstudiante rubroEstudiante=rubroEstudianteService.buscarPorId(detalle.getReferenciaId());
            
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
    
    /***
     * Ver si este metodo se une con el que esta en KardexService
     * @param detalle
     * @param bodega
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    private void afectarInventario(FacturaDetalle detalle,Bodega bodega) throws RemoteException, ServicioCodefacException
    {        
        Producto producto = productoService.buscarPorId(detalle.getReferenciaId());
        BigDecimal cantidad=detalle.getCantidad();
        BigDecimal precioUnitario=detalle.getPrecioUnitario();
        
        //Si el producto es un empaque busco el producto original
        if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE))
        {
            /*ProductoPresentacionDetalle presentacionDetalle=producto.buscarPresentacionDetalleProducto();
            BigDecimal cantidadEquivalencia=presentacionDetalle.getCantidad();
            cantidad=detalle.getCantidad().multiply(cantidadEquivalencia);
            precioUnitario=(detalle.getPrecioUnitario().divide(cantidadEquivalencia,6,BigDecimal.ROUND_HALF_UP));
            //Finalmente dejo seleccionado el producto principal para que continue con el proceso
            producto=presentacionDetalle.getProductoOriginal();*/
            ProductoConversionPresentacionRespuesta respuesta=productoService.convertirProductoEmpaqueSecundarioEnPrincipal(producto, detalle.getCantidad(), detalle.getPrecioUnitario());
            
            producto=respuesta.productoPresentacionPrincipal;
            precioUnitario=respuesta.precioUnitario;
            cantidad=respuesta.cantidad;            
        }
        
        Kardex kardex=null;
        //Si el detalle del producto era con reserva entonces regreso los valores al stock original para que haga el proceso normal
        if(detalle.getReservadoEnum()!=null && detalle.getReservadoEnum().equals(EnumSiNo.SI))
        {
            kardex= detalle.getKardex();
            
            if(kardex==null)
            {
                throw new ServicioCodefacException("No se puede hacer una RESERVA sin un KARDEX");
            }
            
            kardex.procesarReserva(detalle.getCantidad(), SignoEnum.NEGATIVO);
            //kardex.setStock(kardex.getStock().add(detalle.getCantidad()));
            //kardex.setReserva(kardex.getReserva().subtract(detalle.getCantidad()));
            
            
        }
        
        //Buscar el lote si no tiene un datos
        Lote lote=null;
        if(detalle.getLote()!=null)
        {
            lote=loteService.buscarPorId(detalle.getLote().getId());
        }

        if(kardex==null)
        {
            kardex =kardexService.consultarOCrearStockSinPersistencia(producto, bodega,lote);
        }
        //Kardex kardex = consultarOCrearStock(producto, bodega);

        /**
         * Validacion pára verificar que exista un stock superior o igual en el
         * kardex segun lo que quieran facturar
         */
        BigDecimal cantidadFaltante = cantidad.subtract(kardex.getStock());
        if(ParametroUtilidades.comparar(detalle.getFactura().getEmpresa(), ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO,EnumSiNo.NO))
        {
            //Si el stock que queremos facturar es mayor del existe lanzo una excepcion                
            if (cantidad.compareTo(kardex.getStock()) > 0) 
            {
                //Solo para ensambles rerifica si tiene que construir el ensamble no importaria si no tiene el stock suficiente y mando a construir
                if (producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE) && ParametroUtilidades.comparar(kardex.getBodega().getEmpresa(), ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI)) 
                {
                    //No valida nada porque si este proceso falla automaticamente debe generar la excepcion interior, por ejemplo cuando no existe la cantidad necesaria de los componentes para construir el ensamble                    
                    kardex=verificarConstruirEnsamble(kardex, cantidadFaltante,true);
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
                kardex=verificarConstruirEnsamble(kardex, cantidadFaltante,false);
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
        KardexDetalle kardexDetalle = kardexService.crearKardexDetalleSinPersistencia(kardex, TipoDocumentoEnum.VENTA_INVENTARIO, precioUnitario,cantidad);;
        //Agregando datos adicionales del movimiento en la factura
        kardexDetalle.setReferenciaDocumentoId(detalle.getFactura().getId());
        kardexDetalle.setPuntoEmision(detalle.getFactura().getPuntoEmision().toString());
        kardexDetalle.setPuntoEstablecimiento(detalle.getFactura().getPuntoEstablecimiento().toString());
        kardexDetalle.setSecuencial(detalle.getFactura().getSecuencial());
        kardexDetalle.setUsuarioNick(detalle.getFactura().getUsuario().getNick());
        kardexDetalle.setFechaDocumento(UtilidadesFecha.castDateUtilToSql(detalle.getFactura().getFechaEmision()));
        
        kardexDetalle.setRazonSocial(detalle.getFactura().getRazonSocial());
        //kardexDetalle.setNombreLegal(detalle.getFactura().get);
        
        //Si el producto tiene relacionado un item especifico lo agrego para tener la referencia de la venta
        if(detalle.getKardexItemEspecifico()!=null)
        {
            KardexItemEspecifico kardexItemEspecifico=detalle.getKardexItemEspecifico();
            kardexItemEspecifico.setEstadoEnum(GeneralEnumEstado.INACTIVO);
            entityManager.merge(kardexItemEspecifico);
            kardexDetalle.addDetalle(detalle.getKardexItemEspecifico());
        }

        //Actualizar los valores del kardex
        kardexService.recalcularValoresKardex(kardex, kardexDetalle);
        entityManager.persist(kardexDetalle); //Grabo el kardex detalle
        kardex.addDetalleKardex(kardexDetalle);
        
        //Solo ejecuto un merge por que anteriormente ya se ejecuto un persistent cuando no existe
        entityManager.merge(kardex); //Actualizo el kardex con la nueva referencia
        
        //adicional grabar el costo en el detalle de las facturas para luego hacer el reporte
        if(ParametroUtilidades.comparar(bodega.getEmpresa(),ParametroCodefac.CALCULAR_UTILIDAD_ULTIMO_COSTO,EnumSiNo.SI))
        {
            detalle.setCostoPromedio(kardex.getPrecioUltimo());
        }
        else
        {
            detalle.setCostoPromedio(kardex.getCostoPromedio());
        }
       
    }
    
    /**
     * Metodo para verificar si tiene la opcion activa de generar ensamble y ver si se puede construir en ese momento
     */
    public Kardex verificarConstruirEnsamble(Kardex kardex,BigDecimal cantidadFaltante,Boolean validarStockComponentes) throws RemoteException, ServicioCodefacException
    {
        if(ParametroUtilidades.comparar(kardex.getBodega().getEmpresa(),ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI))
        {
            //Cuando intenta construir los ensambles siempre va a coger de la misma bodega
            return kardexService.ingresoEgresoInventarioEnsambleSinTransaccion(kardex.getBodega(),kardex.getBodega(), kardex.getProducto(), cantidadFaltante,ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA,validarStockComponentes);
        }
        //Todo: Verificar que no genere problemas el NULL
        return null;
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
    
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario) throws java.rmi.RemoteException 
    {
        return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal,usuario,null,null,false);
    }
    
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision,Boolean quitarVentasAnuladasNCTotal) throws java.rmi.RemoteException 
    {
        return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal,usuario,vendedor,enviadoGuiaRemision,quitarVentasAnuladasNCTotal);
    }
    
    public BigDecimal obtenerFacturasReporteTotalVenta(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision,Boolean quitarVentasAnuladasNCTotal) throws java.rmi.RemoteException 
    {
        BigDecimal valor=facturaFacade.listaConTotalValor(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido,puntoEmision,empresa,documentoEnum,sucursal,usuario,vendedor,enviadoGuiaRemision);
        if(valor==null)
        {
            valor=BigDecimal.ZERO;
        }
        return valor;
    }
    
    public Long obtenerFacturasReporteTamanio(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision) throws java.rmi.RemoteException 
    {
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
                        
                        factura.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO);
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
                        
                        //ELIMINAR el registro de la CARTERA
                        CarteraService carteraService=new CarteraService();
                        
                        Cartera carteraFactura=carteraService.buscarCarteraPorReferencia(
                        factura.getId(),
                        factura.getCodigoDocumentoEnum(),
                        GeneralEnumEstado.ACTIVO,
                        Cartera.TipoCarteraEnum.CLIENTE,
                        factura.getSucursalEmpresa());
                        
                        if(carteraFactura!=null)
                        {
                            carteraService.eliminarCarteraSinTransaccion(carteraFactura, ModoProcesarEnum.FORZADO);
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
    public Long obtenerSecuencialProformas(Empresa empresa,DocumentoEnum documentoEnum) throws RemoteException
    {
        Long secuencial=getFacade().getSecuencialProforma(empresa,documentoEnum);
        return (secuencial!=null)?(secuencial+1):1; //Si no existe ningun valor por defecto retorna 1
    }
    
    @Override
    public Long obtenerSecuencialComanda(Empresa empresa) throws RemoteException
    {
        Long secuencial=getFacade().getSecuencialProforma(empresa,DocumentoEnum.COMANDA);
        return (secuencial!=null)?(secuencial+1):1; //Si no existe ningun valor por defecto retorna 1
    }
    
    public void eliminarProforma(Factura factura) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                factura.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO);
                entityManager.merge(factura);
            }
        });
    }
    
    public List<Factura> consultarProformasReporte(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,ComprobanteEntity.ComprobanteEnumEstado estado) throws java.rmi.RemoteException,ServicioCodefacException
    {
        return getFacade().consultarProformasReporteFacade(cliente, fechaInicial, fechaFinal, empresa,estado,DocumentoEnum.PROFORMA);
    }
    
    public List<Factura> consultarComandaReporte(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,ComprobanteEntity.ComprobanteEnumEstado estado) throws java.rmi.RemoteException,ServicioCodefacException
    {
        return getFacade().consultarProformasReporteFacade(cliente, fechaInicial, fechaFinal, empresa,estado,DocumentoEnum.COMANDA);
    }

    /**
     * @deprecated Parece que nadie usa este metodo
     */
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
                            rubroEstudiante = rubroEstudianteService.buscarPorId(referenciaId);

                            catalogoProducto = rubroEstudiante.getRubroNivel().getCatalogoProducto();
                            respuesta=new ReferenciaDetalleFacturaRespuesta(
                                    catalogoProducto,
                                    rubroEstudiante.getId(), 
                                    tipoDocumentoEnum, 
                                    rubroEstudiante);

                            break;

                        case LIBRE:
                        case INVENTARIO:
                            Producto producto = productoService.buscarPorId(referenciaId);
                            if(producto!=null)
                            {
                                catalogoProducto = producto.getCatalogoProducto();
                            }
                            
                            respuesta=new ReferenciaDetalleFacturaRespuesta(
                                    catalogoProducto,
                                    producto.getIdProducto(), 
                                    tipoDocumentoEnum, 
                                    producto);
                            respuesta.codigoPrincipal=producto.getCodigoPersonalizado();
                            break;

                        case ORDEN_TRABAJO:    
                        case PRESUPUESTOS:
                            Presupuesto presupuesto = presupuestoService.buscarPorId(referenciaId);
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
            //throw new ServicioCodefacException("No se puede hacer más de 2 facturas con el mismo pedido");
        }
    }
    
    /**
     * @AUTHOR: Robert Tene
     * @param factura
     * @throws ServicioCodefacException
     * @throws RemoteException 
     */
    private void agregarDatosParaCajaSession(Factura factura) throws ServicioCodefacException, RemoteException
    {
        CajaSession cajaSession = null;
        CajaPermiso cajaPermisoParaUsuario = null;
        
        Usuario usuario = factura.getUsuario();
        
        List<CajaPermiso> cajaPermisoList=cajaPermisoService.buscarPermisosCajasActivos(usuario);
        //List<CajaPermiso> cajaPermisoList= usuario.buscarPermisosCajasActivosService();
        
        //Verifico si el usuario tiene cajas con permisos para el método POS
        if(cajaPermisoList.isEmpty())
        {
            throw new ServicioCodefacException("No tiene permisos asignados en cajas");
        }
        
        //Busco la caja, dentro de la cajas que tiene permiso el usuario con el respectivo punto de emision que eligio en la factura       
        //CajaPermisoService cajaPermisoService=new CajaPermisoService();
        //cajaPermisoService.buscarUsuariosPorSucursalYLigadosACaja(session, caja)
        
        for(CajaPermiso cajaPermiso : cajaPermisoList) 
        {
            Caja caja=cajaPermiso.getCaja();
            if(caja.getPuntoEmision().getPuntoEmision().equals(factura.getPuntoEmision()) || ( caja.getPuntoEmision2()!=null && caja.getPuntoEmision2().getPuntoEmision().equals(factura.getPuntoEmision())))
            {
                for(TurnoAsignado turnoAsignado : cajaPermiso.getTurnoAsignadoList())
                {
                    if(turnoAsignado.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                    {
                        if(UtilidadesHora.comprobarHoraEnRangoDeTiempo(turnoAsignado.getTurno().getHoraInicial(), turnoAsignado.getTurno().getHoraFinal(), UtilidadesHora.horaActual()))
                        {
                           cajaPermisoParaUsuario = cajaPermiso;
                           break;
                        }
                    }                    
                }
            }
        }
        
        if(cajaPermisoParaUsuario == null)
        {
            throw new ServicioCodefacException("No tiene permisos asignados en el punto de emisión para este horario.\n Solución: Asigne al usuario un horario");
        }
        
        //TODO: Este artificio solo es temporal por que esta referencia no se esta actualizando de forma automatica y toca cerrar y abrir el sistema para que se actualice
        List<CajaSession> cajasSessionUsuarioList=cajaSesionService.obtenerCajaSessionPorUsuarioYSucursal(usuario,factura.getSucursalEmpresa());
        if(cajasSessionUsuarioList.isEmpty())
        {            
            throw new ServicioCodefacException("No esta activa una CAJA para el usuario: "+usuario.getNick());
        }
        
        cajaSession =cajaSesionService.obtenerCajaSessionPorPuntoEmisionYUsuario(factura.getPuntoEmision(), factura.getUsuario());
        
        if(cajaSession == null)
        {
            throw new ServicioCodefacException("No se encontro ninguna session para el punto de emisión");
        }
        
        //Grabar el valor de la venta para contavilizar cuando se termine la session de la caja
        IngresoCaja ingresoCaja = new IngresoCaja();
        ingresoCaja.setCajaSession(cajaSession);
        ingresoCaja.setValor(factura.getTotal());
        ingresoCaja.setFactura(factura);
        
        entityManager.persist(ingresoCaja);
            
        //cajaSession.addIngresoCaja(ingresoCaja);
        //IngresoCajaService ingresoCajaService=new IngresoCajaService();
        //ingresoCajaService.grabar(ingresoCaja);
  
        entityManager.merge(cajaSession);
    }
    
    @Override
    public Factura obtenerPedidoVentaDiariaActivo(Sucursal sucursal) throws RemoteException,ServicioCodefacException 
    {
        //Factura factura;
        //factura.setCodigoDocumento(codigoDocumento);
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("sucursalEmpresa",sucursal);
        mapParametros.put("codigoOrigenTransaccion", Factura.OrigenTransaccionEnum.WIDGETS_VENTA_DIARIA.getCodigo());
        mapParametros.put("codigoDocumento", DocumentoEnum.PROFORMA.getCodigo());
        mapParametros.put("estado", Factura.ComprobanteEnumEstado.AUTORIZADO.getEstado());
        
        List<Factura> resultado=getFacade().findByMap(mapParametros);
        if(resultado.size()>0)
        {
            return resultado.get(0);
        }
        return null;
    }    
    
   public UtilidadReport consultaUtilidadVentas(Date fechaMenor, Date fechaMayor) throws RemoteException,ServicioCodefacException 
   {
       
       UtilidadReport reporte=new UtilidadReport("Reporte de Utilidades");
       List<UtilidadResult> datosList= getFacade().consultaUtilidadFacade(fechaMenor,fechaMayor);       
       reporte.setDetalleList(datosList);
       
       return reporte;
   }
}
