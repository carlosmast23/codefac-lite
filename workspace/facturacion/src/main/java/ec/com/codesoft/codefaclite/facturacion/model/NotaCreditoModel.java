/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.model.DatoAdicionalModel;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import static ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface.ESTADO_EDITAR;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteDatosComprobanteElectronicosInterface;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusquedaNotaCredito;
import ec.com.codesoft.codefaclite.facturacion.busqueda.NotaCreditoBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteNotaCreditoImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.panel.NotaCreditoPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataNotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class NotaCreditoModel extends NotaCreditoPanel implements ComponenteDatosComprobanteElectronicosInterface{

    private DefaultTableModel modeloTablaDetalle = new DefaultTableModel();
    private NotaCredito notaCredito;
    private Producto productoSeleccionado;
    
    private static final int TAB_INDEX_VENTA=0;
    private static final int TAB_INDEX_LIBRE=1;
    

    public NotaCreditoModel() {
        
        valoresIniciales();
        initComponenesGraficos();
        listenerComponentes();
        addListenerButtons();
        addListenerTablas();
        addListenerCombos();
        addPopUpListener();
        super.validacionDatosIngresados=false;
    }
    
    private PuntoEmision obtenerPuntoEmisionSeleccionado()
    {
        return (PuntoEmision)getCmbPuntoEmision().getSelectedItem();
    }
    
    private void setearValoresNotaCredito()
    {
        notaCredito.setEmpresa(session.getEmpresa());
        //notaCredito.setEstado(Factura.ESTADO_FACTURADO);
        notaCredito.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        notaCredito.setRazonModificado(getTxtMotivoAnulacion().getText());
        notaCredito.setFechaEmision(new Date(getjDateFechaEmision().getDate().getTime()));
        
        PuntoEmision puntoEmisionSeleccionado= obtenerPuntoEmisionSeleccionado();
        //notaCredito.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        //notaCredito.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        notaCredito.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision().toString());
        notaCredito.setPuntoEstablecimiento(puntoEmisionSeleccionado.getSucursal().getCodigoSucursal().toString());
        
        //notaCredito.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).valor));
        notaCredito.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        //notaCredito.setSubtotalCero(BigDecimal.ZERO);
        
        
        //Actualizar en el documento de nota_credito el nuevo tipo de documento
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        notaCredito.setTipoDocumento(tipoDocumentoEnum.getCodigo());
        
        notaCredito.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
        notaCredito.setDireccionMatriz(session.getMatriz().getDirecccion());
        
        switch(tipoDocumentoEnum)
        {
            case LIBRE:
                //TODO: Validar que estos campos en la nota de credito esten ingresados correctamente
                notaCredito.setNumDocModificado(getTxtPreimpresoProveedor().getText());
                if(getCmbFechaCompra().getDate()!=null)
                {
                    notaCredito.setFechaEmisionDocSustento(new java.sql.Date(getCmbFechaCompra().getDate().getTime()));
                }
                break;
                
            case VENTA:
                if(notaCredito.getFactura()!=null)
                {
                    notaCredito.setNumDocModificado(notaCredito.getFactura().getPreimpreso());
                    notaCredito.setFechaEmisionDocSustento(new java.sql.Date(notaCredito.getFactura().getFechaEmision().getTime()));
                }
                break;
        
        }
        //Verificacion para cambiar el estado de la factura
        /*if(notaCredito.getTotal().compareTo(notaCredito.getFactura().getTotal())<0)
        {
            Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado();
            notaCredito.getFactura().setEstado(FacturaEnumEstado.ANULADO_PARCIAL.getEstado());
        }
        else
        {
            notaCredito.getFactura().setEstado(FacturaEnumEstado.ANULADO_TOTAL.getEstado());
        }*/
    }
    
    private boolean validarDatosNotaCredito()
    {
        if(notaCredito.getCliente()==null)
        {
            DialogoCodefac.mensaje("Error Validación","Porfavor seleccione un cliente",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if(notaCredito.getDetalles()==null || notaCredito.getDetalles().size()==0)
        {
            DialogoCodefac.mensaje("Error Validación","Porfavor ingrese detalles al comprobante",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        
        //Actualizar en el documento de nota_credito el nuevo tipo de documento
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        if(tipoDocumentoEnum.equals(TipoDocumentoEnum.LIBRE))
        {
            if(notaCredito.getFechaEmisionDocSustento()==null)
            {
                DialogoCodefac.mensaje("Error Validación","Porfavor seleccione la fecha de emisión",DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
            
            if(notaCredito.getNumDocModificado().replaceAll("-","").replaceAll(" ","").isEmpty())
            {
                DialogoCodefac.mensaje("Error Validación","Porfavor ingrese el preimpreso de la factura",DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
        }
        
        
        return true;    
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
        Boolean pregunta=DialogoCodefac.dialogoPregunta("Alerta","Esta seguro que desea grabar la Nota de Crédito",DialogoCodefac.MENSAJE_ADVERTENCIA);
        if(!pregunta)
        {
            throw new ExcepcionCodefacLite("cancelar el metodo grabar ...");
        }
        
        try {
            NotaCredito notaCreditoGrabada;
            NotaCreditoServiceIf servicio=ServiceFactory.getFactory().getNotaCreditoServiceIf();
            setearValoresNotaCredito();
            
            if(!validarDatosNotaCredito())
            {
                throw new ExcepcionCodefacLite("Error Validación");
            }
            
            notaCredito=servicio.grabar(notaCredito);
            notaCreditoGrabada=notaCredito;//graba una referencia con ambiento del metodo para los listener
            
            ComprobanteDataNotaCredito comprobanteData=new ComprobanteDataNotaCredito(notaCredito);

            comprobanteData.setMapInfoAdicional(getMapAdicional(notaCredito));
             
            ClienteInterfaceComprobante cic=new ClienteNotaCreditoImplComprobante(this, notaCreditoGrabada);
            ComprobanteServiceIf comprobanteServiceIf=ServiceFactory.getFactory().getComprobanteServiceIf();
            comprobanteServiceIf.procesarComprobante(comprobanteData,notaCredito,session.getUsuario(),cic);        
            

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Map<String,String> getMapAdicional(NotaCredito notaCredito)
    {
        Map<String,String> parametroMap=new HashMap<String ,String>();
        if(notaCredito.getDatosAdicionales()!=null)
        {
            for (NotaCreditoAdicional datoAdicional : notaCredito.getDatosAdicionales()) 
            {
                parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
            }
        }
        return parametroMap;
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        ComprobanteElectronicoComponente.eliminarComprobante(this,notaCredito,null);
    }

    @Override
    public void imprimir() {
                
        //Solo imprimir en el modo de editar
        if(estadoFormulario==ESTADO_EDITAR)
        {
            try {
                String claveAceeso = this.notaCredito.getClaveAcceso();
                byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAceeso);
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint,notaCredito.getPreimpreso());
            } catch (RemoteException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        NotaCreditoBusqueda notaCreditoBusqueda = new NotaCreditoBusqueda(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(notaCreditoBusqueda);
        buscarDialogoModel.setVisible(true);
        NotaCredito notaCreditoTmp = (NotaCredito) buscarDialogoModel.getResultado();
        if(notaCreditoTmp != null)
        {
            crearDetalleTabla();
            this.notaCredito = notaCreditoTmp;
            setearDatosProveedor(this.notaCredito.getCliente());
            mostrarDatosNotaCredito(); 
            cargarDatosDetalles();
            cargarTablaDatosAdicionales();
            getPnlDatosAdicionales().habiliarBotonAutorizar();
        }
        else
        {
            throw new ExcepcionCodefacLite("Cancelado metodo buscar");
        }
        
    }

    @Override
    public void limpiar() {
        //try {
            /**
             * Cargar Datos de la empresa
             */
            getLblRuc().setText(session.getEmpresa().getIdentificacion());
            getLblDireccion().setText(session.getSucursal().getDirecccion());
            getLblTelefonos().setText(session.getMatriz().getTelefono());
            getLblNombreComercial().setText(session.getEmpresa().getRazonSocial());
            
            getTxtProveedor().setText("");
            getTxtPreimpresoProveedor().setText("");
            getCmbFechaCompra().setDate(UtilidadesFecha.getFechaHoy());
                        
            
            //Cargar el secuncial correspondiente
            //NotaCreditoServiceIf servicio=ServiceFactory.getFactory().getNotaCreditoServiceIf();
            //getLblSecuencial().setText(servicio.getPreimpresoSiguiente());
            ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),ComprobanteEnum.NOTA_CREDITO,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
            
            getCmbTipoDocumento().setSelectedItem(TipoDocumentoEnum.VENTA);
            
            getTblDatosAdicionales().setModel(new DefaultTableModel());
            
            notaCredito = new NotaCredito();
            crearDetalleTabla();
            limpiarCampos();
        //} catch (RemoteException ex) {
        //    Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
    
    
    
    private void limpiarCampos()
    {
        getLblNombreCliente().setText("");
        getLblDireccionCliente().setText("");
        getLblTelefonoCliente().setText("");
        getTxtMotivoAnulacion().setText("");
        
        BigDecimal valorCero=new BigDecimal("0.00");
        getLblSubtotalSinImpuesto().setText(valorCero.toString());
        getLblSubtotal12().setText(valorCero.toString());
        getLblSubtotal0().setText(valorCero.toString());
        getLblSubtotalNoObjetoDeIva().setText(valorCero.toString());
        getLblSubtotalExentoDeIva().setText(valorCero.toString());
        getLblTotalDescuento().setText(valorCero.toString());
        getLblValorIce().setText(valorCero.toString());
        getLblIva12().setText(valorCero.toString());
        getLblValorIRBPNR().setText(valorCero.toString());
        getLblPropina10().setText(valorCero.toString());
        getTxtValorTotal().setText(valorCero.toString());
        getTxtReferenciaFactura().setText("");
        
        //getTblDetalleNotaCredito().removeAll();
        
    }

//    @Override
    public String getNombre() {
        return "Nota de Credito";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#enotacredito";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    private void agregarProducto() {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        productoSeleccionado = (Producto) buscarDialogoModel.getResultado();
        agregarProductoVista(productoSeleccionado);
    }
    
    private void agregarProductoVista(Producto productoSeleccionado) {
        if (productoSeleccionado == null) {
            return;
        }
        
        this.productoSeleccionado=productoSeleccionado;
        setearValoresProducto(productoSeleccionado.getValorUnitario(), productoSeleccionado.getNombre(),productoSeleccionado.getCodigoPersonalizado());
    }
    
    private void setearValoresProducto(BigDecimal valorUnitario,String descripcion,String codigo) {
        getTxtValorUnitario().setText(valorUnitario+"");
        getTxtDescripcion().setText(descripcion);
        //getTxtValorUnitario().setText(productoSeleccionado.getValorUnitario().toString());
        //getTxtDescripcion().setText(productoSeleccionado.getNombre());
        //Dar foco a la cantidad a ingresar
        getTxtCantidad().setText("1");
        getTxtCodigoDetalle().setText(codigo);
        getTxtCantidad().requestFocus();
        getTxtCantidad().selectAll();
    }
    
    private void btnListenerCrearProducto() {
        TipoDocumentoEnum tipoDocumento=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        
        EnumSiNo manejoInventario=EnumSiNo.NO;
        switch (tipoDocumento) {
            case INVENTARIO:
                manejoInventario=EnumSiNo.SI;
                break;

        }
                
        ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
            @Override
            public void updateInterface(Producto entity) {
                if (entity != null) {
                    productoSeleccionado = entity;
                    setearValoresProducto(productoSeleccionado.getValorUnitario(), productoSeleccionado.getNombre(),productoSeleccionado.getCodigoPersonalizado());
                    //Establecer puntero en la cantidad para agregar
                    getTxtCantidad().requestFocus();
                    getTxtCantidad().selectAll();
                }
            }
        };
                
        panelPadre.crearDialogoCodefac(observer, VentanaEnum.PRODUCTO, false, new Object[]{manejoInventario,getTxtCodigoDetalle().getText()},this);
        
    }
    
    private boolean verificarCamposValidados() {
        //bandera para comprobar que todos los campos esten validados
        boolean b = true;
        List<JTextField> camposValidar = new ArrayList<JTextField>();
        //Ingresar los campos para validar 
        camposValidar.add(getTxtValorUnitario());
        camposValidar.add(getTxtCantidad());
        camposValidar.add(getTxtDescripcion());
        camposValidar.add(getTxtDescuento());
        //Obtener el estado de validacion de los campos
        for (JTextField campo : camposValidar) {
            Color color=campo.getBackground();
            //System.out.println(color.getRed()+"-"+color.getGreen()+"-"+color.getBlue());
            //SeaGlassLookAndFeel.
            if (!compararColores(color,Color.white)) {
                b = false;
            }
        }
        return b;
    }
    
    private boolean compararColores(Color color1,Color color2) //TODO: Artificio para comparar colores cuando se manejan templates no compara directo los objetos
    {
        if(color1.getRed()==color2.getRed() && color1.getGreen()==color2.getGreen() && color1.getBlue()==color2.getBlue())
        {
            return true;
        }
        return false;
    }
    
    private boolean validacionPersonalizadaPorModulos() {
        TipoDocumentoEnum tipoDocEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        BigDecimal cantidad = new BigDecimal(getTxtCantidad().getText());
        BigDecimal valorUnitario = new BigDecimal(getTxtValorUnitario().getText());

        switch(tipoDocEnum)
        {
            /*
            case ACADEMICO:
                //TODO: Analizar para el caso que tenga descuento
                if (rubroSeleccionado.getSaldo().compareTo(cantidad.multiply(valorUnitario)) == -1) {
                    DialogoCodefac.mensaje("Validación", "El Total no puede exceder del valor " + rubroSeleccionado.getSaldo() + " del rubro", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
                break;
                
            case PRESUPUESTOS:
                if (presupuestoSeleccionado.getTotalVenta().compareTo(cantidad.multiply(valorUnitario)) == -1) {
                    DialogoCodefac.mensaje("Validación", "El Total no puede exceder del valor " + rubroSeleccionado.getSaldo() + " del rubro", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
                break;
            */
        }
        
        return true;
    }
    
    public BigDecimal obtenerValorIva() {
        try {
            Map<String, Object> map = new HashMap<>();
            ImpuestoDetalleServiceIf impuestoDetalleService =ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
            map.put("tarifa", 12); //TODO Parametrizar el iva con la variable del sistema
            List<ImpuestoDetalle> listaImpuestoDetalles = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
            listaImpuestoDetalles.forEach((iD) -> {
                BigDecimal iva = iD.getPorcentaje();
            });
            return new BigDecimal(0.120);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean agregarDetallesFactura(NotaCreditoDetalle facturaDetalle) {
        boolean agregar = true;

        //Verifica si manda un detalle existe solo se modifica
        if (facturaDetalle != null) {
            agregar = false;
        } else {
            facturaDetalle = new NotaCreditoDetalle();
        }

        if (!panelPadre.validarPorGrupo("detalles")) {
            int filaSeleccionada=getTblDetalleNotaCredito().getSelectedRow();
            cargarDatosDetalles(); //Si no se pudo editar vuelvo a cargar los detalles si se modifico desde la tabla para que quede la forma original
            //actualizarDatosTablaDetalle();
            getTblDetalleNotaCredito().setRowSelectionInterval(filaSeleccionada,filaSeleccionada);
            return false;
        }
        

        if (verificarCamposValidados()) {
            
            //Validacion dependiendo de la logica de cada tipo de documento
            if (!validacionPersonalizadaPorModulos()) {
                return false;
            }
            
            
            //Variable del producto para verificar otros datos como el iva
            CatalogoProducto catalogoProducto=null;
            //Seleccionar la referencia dependiendo del tipo de documento
            //TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
            
            facturaDetalle.setTipoReferencia(TipoDocumentoEnum.LIBRE.getCodigo());
            facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
            try {
                catalogoProducto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
            } catch (RemoteException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*
            facturaDetalle.setTipoDocumento(tipoDocumentoEnum.getCodigo());
            switch (tipoDocumentoEnum)
            {
            case ACADEMICO:
            facturaDetalle.setReferenciaId(rubroSeleccionado.getId());
            catalogoProducto = rubroSeleccionado.getRubroNivel().getCatalogoProducto();
            break;
            
            case PRESUPUESTOS:
            facturaDetalle.setReferenciaId(presupuestoSeleccionado.getId());
            catalogoProducto=presupuestoSeleccionado.getCatalogoProducto();
            break;
            
            //Para invetario o para libre es la misma logica
            case INVENTARIO: case LIBRE:
            facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
            catalogoProducto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
            break;
            }*/
            //Advertecia catalogo producto
            if(catalogoProducto==null)
            {
                DialogoCodefac.mensaje("Advertencia","No esta definido el Catalogo Producto ,donde se especifica los impuestos para facturar ",DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
            //FacturaDetalle facturaDetalle = new FacturaDetalle();
            facturaDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
            facturaDetalle.setDescripcion(getTxtDescripcion().getText());
            BigDecimal valorTotalUnitario = new BigDecimal(getTxtValorUnitario().getText());
            facturaDetalle.setPrecioUnitario(valorTotalUnitario);
            facturaDetalle.setValorIce(BigDecimal.ZERO);
            facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
            
            BigDecimal descuento;
            if (!getCheckPorcentaje().isSelected()) { //Cuando no es porcentaje el valor se setea directo
                if (!getTxtDescuento().getText().equals("")) {
                    descuento = new BigDecimal(getTxtDescuento().getText());
                } else {
                    descuento = BigDecimal.ZERO;
                }
                
                facturaDetalle.setDescuento(descuento);
            } else { //Cuando es porcentaje se calcula el valor directo
                if (!getTxtDescuento().getText().equals("")) {
                    BigDecimal porcentajeDescuento = new BigDecimal(getTxtDescuento().getText());
                    porcentajeDescuento = porcentajeDescuento.divide(new BigDecimal(100));
                    BigDecimal total = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario().setScale(2,BigDecimal.ROUND_HALF_UP)); //Escala a 2 decimales el valor del valor unitario porque algunos proveedores tienen 3 decimales
                    descuento = total.multiply(porcentajeDescuento);
                    facturaDetalle.setDescuento(descuento.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
            //Calular el total despues del descuento porque necesito esa valor para grabar
            BigDecimal setTotal = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).subtract(facturaDetalle.getDescuento());
            facturaDetalle.setTotal(setTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
            /**
             * Revisar este calculo del iva para no calcular 2 veces al mostrar
             */
            //facturaDetalle.set
            //facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
            if (catalogoProducto.getIva().getTarifa().equals(0)) {
                facturaDetalle.setIva(BigDecimal.ZERO);
            } else {
                BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP);
                facturaDetalle.setIva(iva);
            }
            if (facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).compareTo(facturaDetalle.getDescuento()) > 0) {
                
                //Solo agregar si se enviar un dato vacio
                if (agregar) {
                    notaCredito.addDetalle(facturaDetalle);
                }
                
                cargarDatosDetalles();
                limpiarDetalleFactura();
                cargarTotales();
            } else {
                DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                limpiarDetalleFactura();
                return false;
            }
            
            return true; //si pasa todas las validaciones asumo que se edito correctamente

        }
        else
        {
            return false;
        }
        
    }
    
    public void cargarTotales() {
        notaCredito.calcularTotalesDesdeDetalles();
        /**
         * Setear los componentes graficos despues de los calculos
         */
        getLblSubtotalSinImpuesto().setText("" + notaCredito.getSubtotalSinImpuestos().add(notaCredito.getSubtotalImpuestos()));
        getLblSubtotal12().setText("" + notaCredito.getSubtotalImpuestos());
        getLblSubtotal0().setText("" + notaCredito.getSubtotalSinImpuestos());
        getLblIva12().setText("" + notaCredito.getIva());
        getTxtValorTotal().setText("" + this.notaCredito.getTotal());
        //getLblSubTotalDescuentoConImpuesto().setText("" + notaCredito.getDescuentoImpuestos());
        //getLblSubTotalDescuentoSinImpuesto().setText("" + notaCredito.getDescuentoSinImpuestos());
        getLblTotalDescuento().setText("" + notaCredito.getDescuentoImpuestos().add(notaCredito.getDescuentoSinImpuestos()));
        

    }
    
    private void limpiarDetalleFactura() {
        /*
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        
        //TODO: REVISAR PORQUE ME TOCA HACER ESTA VALIDACION
        if(tipoDocumentoEnum==null)
        {
            tipoDocumentoEnum=tipoDocumentoEnum.LIBRE;
        }
        
        //Limpio las variables
        switch(tipoDocumentoEnum)
        {
            case LIBRE:
            case INVENTARIO:
                productoSeleccionado=null;
                break;
                
            case PRESUPUESTOS:
                presupuestoSeleccionado=null;
                break;
                
            case ACADEMICO:
                rubroSeleccionado=null;
                break;
        
        }
        */    
        
        //Limpio los datos en la pantalla
        getTxtCantidad().setText("");
        getTxtDescripcion().setText("");
        getTxtValorUnitario().setText("");
        getTxtDescuento().setText("0");
        getTxtCodigoDetalle().setText("");
        getTxtCodigoDetalle().requestFocus();
        getTxtCodigoDetalle().selectAll();
    }
    
    private void agregarCorreo(String correo)
    {
        if(notaCredito.getDatosAdicionales()!=null)
        {
            notaCredito.getDatosAdicionales().clear();            
        }
        
        if(correo!=null)
        {
            notaCredito.addDatosAdicionalCorreo(correo);
        }
        //Cargar los datos en la tabla
        cargarTablaDatosAdicionales();
        
    }

    private void addListenerButtons() {
                
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo clienteBusquedaDialogo = new ProveedorBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Persona proveedorTmp=((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                
                if(proveedorTmp!=null)
                {
                    notaCredito.setCliente(proveedorTmp);
                    agregarCorreo(proveedorTmp.getCorreoElectronico());
                    setearDatosProveedor(proveedorTmp);                    
                    getTxtProveedor().setText(proveedorTmp.getIdentificacion()+" - "+proveedorTmp.getRazonSocial());
                    mostrarDatosNotaCredito();
                }
            }
        });
        
        getBtnQuitarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerEliminar();
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerEditar();
            }
        });
        
        getBtnAgregarDetalleFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(panelPadre.validarPorGrupo("detalles"));
                agregarDetallesFactura(null);
                
            }
        });
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerCrearProducto();
            }
        });
        
        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                agregarProducto();
                
            }
        });
        
        getBtnAgregarDatosAdicionales().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatoAdicionalModel datoAdicional=new DatoAdicionalModel();
                datoAdicional.setVisible(true);
                
                String valor=datoAdicional.valor;
                String campo=datoAdicional.campo;
                
                FacturaAdicional.Tipo tipoEnum=datoAdicional.tipo;
                
                if(notaCredito!=null && valor!=null && tipoEnum!=null)
                {
                    if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_CORREO))
                    {
                        notaCredito.addDatosAdicionalCorreo(valor);
                    }
                    else
                    {
                        NotaCreditoAdicional dato=new NotaCreditoAdicional();
                        dato.setCampo(campo);
                        dato.setTipo(tipoEnum.getLetra());
                        dato.setNumero(0);
                        dato.setValor(valor);
                        notaCredito.addDatoAdicional(dato);
                        //factura.addDatoAdicional(dato);
                    }
                    cargarTablaDatosAdicionales();
                }
            }
        });
        
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusquedaNotaCredito  facturaBusqueda = new FacturaBusquedaNotaCredito(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);
                Factura factura = (Factura) buscarDialogoModel.getResultado();
                if (factura != null) {
                    notaCredito.setFactura(factura);
                    notaCredito.setNumDocModificado(factura.getPreimpreso());
                    setearDatosProveedor(factura.getCliente());
                    cargarDatosNotaCredito();
                    cargarDatosAdicionales();
                    cargarTablaDatosAdicionales();
                    mostrarDatosNotaCredito();
                }
            }
        });
    }
    
    private void setearDatosProveedor(Persona proveedor)
    {
        notaCredito.setCliente(proveedor);
        notaCredito.setTelefono(proveedor.getEstablecimientos().get(0).getTelefonoConvencional());
        notaCredito.setDireccion(proveedor.getEstablecimientos().get(0).getDireccion());
        notaCredito.setRazonSocial(proveedor.getRazonSocial());
    }
    
    private void cargarDatosAdicionales()
    {
        //Si vuelve a escoger otra factura se borran los datos adicionales
         if(notaCredito.getDatosAdicionales()!=null)
            notaCredito.getDatosAdicionales().clear();
        
         List<FacturaAdicional> datosAdicional=notaCredito.getFactura().getDatosAdicionales();
         if(datosAdicional!=null)
         {
             List<NotaCreditoAdicional> datosAdicionalNotaCredito=new ArrayList<NotaCreditoAdicional>();
             for (FacturaAdicional facturaDetalle : datosAdicional) {
                 NotaCreditoAdicional notaCreditoAdicional=new NotaCreditoAdicional();
                 notaCreditoAdicional.setCampo(facturaDetalle.getCampo());
                 notaCreditoAdicional.setNotaCredito(notaCredito);
                 notaCreditoAdicional.setNumero(facturaDetalle.getNumero());
                 notaCreditoAdicional.setTipo(facturaDetalle.getTipo());
                 notaCreditoAdicional.setValor(facturaDetalle.getValor());
                 datosAdicionalNotaCredito.add(notaCreditoAdicional);
             }
             notaCredito.setDatosAdicionales(datosAdicionalNotaCredito);
         }

    }

    private void cargarDatosNotaCredito() {
        
        /**
         * Setear datos de la factura a la nota de credito
         */
        notaCredito.setTotal(notaCredito.getFactura().getTotal());
        notaCredito.setValorIvaDoce(notaCredito.getFactura().getIva());
        notaCredito.setSubtotalCero(notaCredito.getFactura().getSubtotalSinImpuestos());
        notaCredito.setSubtotalDoce(notaCredito.getFactura().getSubtotalImpuestos());
        notaCredito.setCliente(notaCredito.getFactura().getCliente());
        notaCredito.setDescuentoImpuestos(notaCredito.getFactura().getDescuentoImpuestos());
        notaCredito.setDescuentoSinImpuestos(notaCredito.getFactura().getDescuentoSinImpuestos());
        
        /**
         * CargarDetallesNotaCredito
         */
        List<FacturaDetalle> detallesFactura = notaCredito.getFactura().getDetalles();
        if(notaCredito.getDetalles()!=null)
            notaCredito.getDetalles().clear();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            NotaCreditoDetalle notaDetalle = new NotaCreditoDetalle();
            notaDetalle.setCantidad(facturaDetalle.getCantidad());
            notaDetalle.setDescripcion(facturaDetalle.getDescripcion());
            //System.out.println(facturaDetalle.getDescuento());
            notaDetalle.setDescuento(facturaDetalle.getDescuento());
            notaDetalle.setIva(facturaDetalle.getIva());
            notaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            notaDetalle.setReferenciaId(facturaDetalle.getReferenciaId());
            notaDetalle.setTipoReferencia(facturaDetalle.getTipoDocumento());
            notaDetalle.setTotal(facturaDetalle.getTotal());
            notaDetalle.setValorIce(facturaDetalle.getValorIce());
            notaDetalle.setIvaPorcentaje(facturaDetalle.getIvaPorcentaje());

            notaCredito.addDetalle(notaDetalle);
        }
        
        /**
         * Cargar los datos Adicionales
         */
        /*
         List<FacturaAdicional> datosAdicional=notaCredito.getFactura().getDatosAdicionales();
         if(datosAdicional!=null)
         {
             List<NotaCreditoAdicional> datosAdicionalNotaCredito=new ArrayList<NotaCreditoAdicional>();
             for (FacturaAdicional facturaDetalle : datosAdicional) {
                 NotaCreditoAdicional notaCreditoAdicional=new NotaCreditoAdicional();
                 notaCreditoAdicional.setCampo(facturaDetalle.getCampo());
                 notaCreditoAdicional.setNotaCredito(notaCredito);
                 notaCreditoAdicional.setNumero(facturaDetalle.getNumero());
                 notaCreditoAdicional.setTipo(facturaDetalle.getTipo());
                 notaCreditoAdicional.setValor(facturaDetalle.getValor());
                 datosAdicionalNotaCredito.add(notaCreditoAdicional);
             }
             notaCredito.setDatosAdicionales(datosAdicionalNotaCredito);
         }
        */
        
        cargarDatosDetalles();
        mostrarDatosNotaCredito();

    }

    private void mostrarDatosNotaCredito() {
        getTxtReferenciaFactura().setText(notaCredito.getNumDocModificado());
        
        getLblNombreCliente().setText(notaCredito.getRazonSocial());
        getLblDireccionCliente().setText(notaCredito.getDireccion());
        getLblTelefonoCliente().setText(notaCredito.getTelefono());
        getTxtMotivoAnulacion().setText(notaCredito.getRazonModificado());
        
        
        //Cargar los datos de la seccion de libre
        getTxtProveedor().setText(notaCredito.getCliente().toString());
        getCmbFechaCompra().setDate(notaCredito.getFechaEmisionDocSustento());
        getTxtPreimpresoProveedor().setText(notaCredito.getNumDocModificado());
        
        //Cargar laseccion segun el tipo de documento guardado en la nota de credito
        if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            TipoDocumentoEnum tipoDocumento=notaCredito.getTipoDocumentoEnum();
            if(tipoDocumento!=null)
            {
                getCmbTipoDocumento().setSelectedItem(tipoDocumento);
                /*switch(tipoDocumento)
                {
                    case LIBRE:
                        getCmbTipoDocumento().setSelectedItem(t);
                        //habilitarTab(TAB_INDEX_LIBRE);
                        break;

                    case VENTA:
                        getCmbTipoDocumento().setSelectedIndex(TAB_INDEX_VENTA);
                        //habilitarTab(TAB_INDEX_VENTA);
                        break;
                }*/
            }
            else
            {
                //TODO: Asumo que los que tienen null ese campo mandaron como venta porque antes no existia ese campo
                habilitarTab(TAB_INDEX_VENTA);
            }
            
        }
        
        
        //Cargar el preimpreso solo cuando el estado sea editar
        if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            ComprobanteElectronicoComponente.cargarSecuencialConsulta(notaCredito,getCmbPuntoEmision(),getLblEstablecimiento(),getLblSecuencial());
        }

        /**
         * Cargar Los calculos de los totales
         */
        getTxtValorTotal().setText(notaCredito.getTotal() + "");
        getLblIva12().setText(notaCredito.getIva() + "");
        getLblSubtotal0().setText(notaCredito.getSubtotalCero() + "");
        getLblSubtotal12().setText(notaCredito.getSubtotalDoce() + "");
        getLblTotalDescuento().setText(notaCredito.getDescuentoImpuestos().add(notaCredito.getDescuentoSinImpuestos()).toString());
    }
    
    

    private void crearDetalleTabla() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("Valor Uni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Valor Total");

        this.modeloTablaDetalle = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleNotaCredito().setModel(this.modeloTablaDetalle);
    }

    private void cargarDatosDetalles() {
        crearDetalleTabla();
        List<NotaCreditoDetalle> detalles = notaCredito.getDetalles();
        for (NotaCreditoDetalle detalle : detalles) {
            
            Vector<String> fila = new Vector<String>();
            
            //TODO: Revisar este codigo porque esta de optimizar la carga del precio unitario
            try {
                switch (detalle.getTipoDocumentoEnum()) {
                    case ACADEMICO:
                        RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add(rubroEstudiante.getId().toString());
                        //fila.add(rubroEstudiante.getValor().toString());
                        break;

                    case PRESUPUESTOS:
                        //Presupuesto presupuesto = ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add(detalle.getReferenciaId()+"");
                        //fila.add(detalle.getPrecioUnitario().toString());
                        break;

                    case INVENTARIO:
                    case LIBRE:
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add((producto.getCodigoPersonalizado() != null) ? producto.getCodigoPersonalizado() : "");
                        //fila.add(producto.getValorUnitario() + "");
                        break;

                }
            } catch (RemoteException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Setear los valores finales
            fila.add(detalle.getPrecioUnitario().toString());
            fila.add(detalle.getCantidad() + "");
            fila.add(detalle.getDescripcion());
            fila.add(detalle.getTotal() + "");
            this.modeloTablaDetalle.addRow(fila);                
        }

    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        if(!validacionParametrosCodefac())
        {
            dispose();
            throw new ExcepcionCodefacLite("No cumple validacion inicial");
        }
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean validacionParametrosCodefac() {
        String mensajeValidacion="Esta pantalla requiere : \n";
        boolean validado=true;
        
               //Validacion cuando solo sea facturacion manual
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()))
        {
            DialogoCodefac.mensaje("Advertencia","Pantalla solo dispinible para facturación electronica",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        else
        {
        
            try {
                if(session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor().equals(""))
                {
                    mensajeValidacion+=" - Archivo Firma\n";
                    validado= false;
                }
                
                String claveFirmaElectronica=UtilidadesEncriptar.desencriptar(session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
                if(claveFirmaElectronica.equals(""))
                {
                    mensajeValidacion+=" - Clave Firma\n";
                    validado= false;
                }
                
                if(session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals(""))
                {
                    mensajeValidacion+=" - Correo\n";
                    validado= false;
                }
                
                if(session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals(""))
                {
                    mensajeValidacion+=" - Clave Correo \n";
                    validado= false;
                }
                
                if(session.getEmpresa() == null)
                {
                    mensajeValidacion+=" - Información de Empresa \n";
                    validado= false;
                }
            } catch (Exception ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(!validado)
        {
            //mensajeValidacion=mensajeValidacion.substring(0,mensajeValidacion.length()-2);
            DialogoCodefac.mensaje("Acceso no permitido", mensajeValidacion+"\nPofavor complete estos datos en configuración para usar esta pantalla",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        
        
        
        
        return validado;
        
    }
    
     /**
     * Metodo que actualiza los valores en la tabla
     */    
    private void cargarTablaDatosAdicionales() 
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("Valor");
        
        DefaultTableModel modeloTablaDatosAdicionales=UtilidadesTablas.crearModeloTabla(new String[]{"","Nombre","Valor"},new Class[]{FacturaAdicional.class,String.class,String.class});
        //this.modeloTablaDatosAdicionales = new DefaultTableModel(titulo, 0);
       
        //DefaultTableModel modeloTablaDatosAdicionales = new DefaultTableModel(titulo, 0);
       
        for (NotaCreditoAdicional datoAdicional : notaCredito.getDatosAdicionales()) {
            Vector dato = new Vector();
            dato.add(datoAdicional);
            dato.add(datoAdicional.getCampo());
            dato.add(datoAdicional.getValor());
            
            modeloTablaDatosAdicionales.addRow(dato);
        }
        
        getTblDatosAdicionales().setModel(modeloTablaDatosAdicionales);
        
        getTblDatosAdicionales().setModel(modeloTablaDatosAdicionales);
        UtilidadesTablas.ocultarColumna(getTblDatosAdicionales(), 0); //Ocultar la fila del objeto para poder volver a modificar

    }

    private void setearDatosEmpresa()
    {
        getLblRuc().setText(notaCredito.getIdentificacion());
        getLblDireccion().setText(notaCredito.getDireccion());
        getLblNombreComercial().setText(notaCredito.getRazonSocial());
        getLblTelefonos().setText(notaCredito.getTelefono());
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void initComponenesGraficos() {
        URL path = RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/mas-ico.png");
        getBtnAgregarDetalleFactura().setIcon(new ImageIcon(path));
        getBtnAgregarDetalleFactura().setText("");
        getBtnAgregarDetalleFactura().setToolTipText("Agregar detalle Nota Crédito");

        getBtnEditarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/edit_icon.png")));
        getBtnEditarDetalle().setText("");
        getBtnEditarDetalle().setToolTipText("Editar detalle Nota Crédito");

        getBtnQuitarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/cerrar-ico.png")));
        getBtnQuitarDetalle().setText("");
        getBtnQuitarDetalle().setToolTipText("Eliminar detalle Nota Crédito");

        getBtnAgregarProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/list.png")));
        getBtnAgregarProducto().setText("");
        getBtnAgregarProducto().setToolTipText("Agregar producto a la Nota Crédito");

        getBtnCrearProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/add2.png")));
        getBtnCrearProducto().setText("");
        getBtnCrearProducto().setToolTipText("Crear nuevo producto");

    }

    private void valoresIniciales() {
        getCmbTipoDocumento().removeAllItems();
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.LIBRE);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.VENTA);
        
    }
    
    private void btnListenerEditar()
    {
        int fila = getTblDetalleNotaCredito().getSelectedRow();
        if(fila>=0)
        {
            try {
                NotaCreditoDetalle facturaDetalle = notaCredito.getDetalles().get(fila);
                //Buscar la referencia de las variables depedendiendo del modulo seleccionado
                TipoDocumentoEnum tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                
                Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                productoSeleccionado = producto;
                /*
                switch (tipoDocumentoEnum) {
                    case LIBRE:
                    case INVENTARIO:
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                        productoSeleccionado = producto;
                        break;
                        
                    case PRESUPUESTOS:
                        Presupuesto presupuesto=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                        presupuestoSeleccionado = presupuesto;
                        break;
                        
                    case ACADEMICO:
                        RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                        rubroSeleccionado = rubroEstudiante;
                        break;
                        
                }*/
                if(agregarDetallesFactura(facturaDetalle))
                {
                    habilitarModoIngresoDatos();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void habilitarModoIngresoDatos() 
    {
        getBtnEditarDetalle().setEnabled(false);
        getBtnQuitarDetalle().setEnabled(false);
        getBtnAgregarDetalleFactura().setEnabled(true);
        getBtnAgregarProducto().setEnabled(true);
        getBtnCrearProducto().setEnabled(true);
    }
    
    private void btnListenerEliminar() {
        int fila = getTblDetalleNotaCredito().getSelectedRow();
        DefaultTableModel modeloTablaDetallesProductos=(DefaultTableModel) getTblDetalleNotaCredito().getModel();
        if(fila>=0)
        {
            modeloTablaDetallesProductos.removeRow(fila);
            notaCredito.getDetalles().remove(fila);
            cargarTotales();
            getBtnEditarDetalle().setEnabled(false);
            getBtnQuitarDetalle().setEnabled(false);
            getBtnAgregarDetalleFactura().setEnabled(true);
            getBtnAgregarProducto().setEnabled(true);
            getBtnCrearProducto().setEnabled(true);
        }
    }
    
    private void addListenerTablas() {
        
        getTblDetalleNotaCredito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTblDetalleNotaCredito().getSelectedRow();
                if(fila>=0)
                {
                    //setear valores para cargar de nuevo en los campos de la factura
                    NotaCreditoDetalle facturaDetalle = notaCredito.getDetalles().get(fila);
                    getTxtValorUnitario().setText(facturaDetalle.getPrecioUnitario() + "");
                    getTxtCantidad().setText(facturaDetalle.getCantidad() + "");
                    getTxtDescripcion().setText(facturaDetalle.getDescripcion());
                    getTxtDescuento().setText(facturaDetalle.getDescuento() + "");
                    getCheckPorcentaje().setSelected(false);
                    getBtnEditarDetalle().setEnabled(true);
                    getBtnQuitarDetalle().setEnabled(true);
                    getBtnAgregarDetalleFactura().setEnabled(false);
                    getBtnAgregarProducto().setEnabled(false);
                    getBtnCrearProducto().setEnabled(false);
                }
            }
        });
       
          
        getTblDetalleNotaCredito().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                getTblDetalleNotaCredito().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        //Evento cuando se desea eliminar un dato de los detalles
                        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            btnListenerEliminar();
                        }      
                        
                        //Permite salir del modo edicion y regresa al modo ingreso
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            btnListenerEditar();
                        }

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                });
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void addListenerCombos() {
        getCmbPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),ComprobanteEnum.NOTA_CREDITO,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
            }
        });
        
        
        getCmbTipoDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                TipoDocumentoEnum tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                //tipoDocumentoEnum=getTi
                switch(tipoDocumentoEnum)
                {
                    case VENTA:
                        getTabTipoDocumentos().setSelectedIndex(0);
                        habilitarEdicionRetencion(false);                        
                        habilitarTab(0);
                        break;
                    
                    case LIBRE:
                        getTabTipoDocumentos().setSelectedIndex(1);
                        habilitarEdicionRetencion(true);
                        habilitarTab(1);
                        break;
                
                }
                
                
            }
        });
    }
    
    private void habilitarTab(int numeroTab)
    {
        getTabTipoDocumentos().setEnabledAt(0, false);
        getTabTipoDocumentos().setEnabledAt(1, false);
        
        getTabTipoDocumentos().setEnabledAt(numeroTab,true);
        getTabTipoDocumentos().setSelectedIndex(numeroTab);
        
    }
    
    private void habilitarEdicionRetencion(Boolean opcion)
    {
        /*
        getTxtBaseImponible().setEnabled(opcion);
        getCmbRetencionIva().setEnabled(opcion);
        getCmbRetencionRenta().setEnabled(opcion);
        getBtnAgregar().setEnabled(opcion);
        ge/tBtnEditar().setEnabled(opcion);*/
    }
    
      private void addPopUpListener() {
        JPopupMenu jPopupMenu=new JPopupMenu();
        JMenuItem jMenuItemDatoAdicional=new JMenuItem("Eliminar");
        jMenuItemDatoAdicional.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblDatosAdicionales().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    NotaCreditoAdicional notaCreditoAdicional=(NotaCreditoAdicional)getTblDatosAdicionales().getValueAt(filaSeleccionada,0); //Obtener el objeto de la columna
                    notaCredito.getDatosAdicionales().remove(notaCreditoAdicional);
                    cargarTablaDatosAdicionales();//Volver a cargar los datos adicionales en la tabla de la vista
                }
            }
        });
        
        jPopupMenu.add(jMenuItemDatoAdicional);
        getTblDatosAdicionales().setComponentPopupMenu(jPopupMenu);
      }

    @Override
    public ComprobanteEntity getComprobante() {
        return notaCredito;
    }    

    private void listenerComponentes() {
        getPnlDatosAdicionales().setComprobante(this);
    }

    @Override
    public void eventoCambiarEstado() {
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            getPnlDatosAdicionales().habilitar(false);
        }
        else if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            getPnlDatosAdicionales().habilitar(true);
        }
    }
    
    @Override
    public Empresa getEmpresa() {
        return session.getEmpresa();
    }

    @Override
    public InterfazComunicacionPanel getPanelPadre() {
        return panelPadre;
    }

    @Override
    public List<ComprobanteAdicional> getDatosAdicionales() {
        return (List<ComprobanteAdicional>)(Object)notaCredito.getDatosAdicionales();
    }

    

}
