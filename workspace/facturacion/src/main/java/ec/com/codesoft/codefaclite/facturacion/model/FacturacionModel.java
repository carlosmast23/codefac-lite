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
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusquedaPresupuesto;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.busqueda.RubroEstudianteBusqueda;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.ManagerReporteFacturaFisica;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.facturacion.reportdata.DetalleFacturaFisicaData;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_AUTORIZAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_ENVIAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_FIRMAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_PRE_VALIDAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_RIDE;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodosEnvioInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DatosAdicionalesComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.tabla.ButtonColumn;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.collections4.map.HashedMap;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel implements InterfazPostConstructPanel{

    //private Persona persona;
    protected Factura factura;
    private Estudiante estudiante;
    //private DefaultTableModel modeloTablaFormasPago;
    private DefaultTableModel modeloTablaDetallesProductos;
    private DefaultTableModel modeloTablaDatosAdicionales;
    private Producto productoSeleccionado;
    private RubroEstudiante rubroSeleccionado;
    private Presupuesto presupuestoSeleccionado;
    //private int fila;
    private java.util.Date fechaMax;
    private java.util.Date fechaMin;
    
    /**
     * Variable que almacena la forma de pago por defecto cuando no se selecciona ninguna
     */
    private SriFormaPago formaPagoDefecto;

    /**
     * Mapa de datos adicionales que se almacenan temporalmente y sirven para la
     * facturacion electronica como por ejemplo el correo
     */
    //private Map<String, String> datosAdicionales;


    public FacturacionModel() {
        setearFechas();
        addListenerButtons();
        addListenerCombos();
        addListenerCamposTexto();
        addListenerTablas();
        addPopUpListener();
        initComponenesGraficos();
        initModelTablaFormaPago();
        initModelTablaDetalleFactura();
        initModelTablaDatoAdicional();
        setearValoresVista();
        //setearVariablesIniciales();

    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) throws ExcepcionCodefacLite {
        this.factura = factura;
        setearValoresFactura();
    }

    
    private void setearFechas() {
        //this.fechaMax = new java.util.Date();
        definirFechaMinFacturacion();

    }

    private void setearVariablesIniciales() {
        agregarFechaEmision();
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);
        this.factura.setIva(BigDecimal.ZERO);
        this.factura.setTotal(BigDecimal.ZERO);
        //this.subTotalDescuentoConImpuesto = new BigDecimal(0);
        //this.subTotalDescuentoSinImpuesto = new BigDecimal(0);
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        //this.subtotalSinImpuestosDescuento=BigDecimal.ZERO;
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        //calcularIva12();
        //datosAdicionales = new HashMap<String, String>();
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

    private void addListenerButtons() {        
        
        getBtnAgregarDatosAdicionales().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatoAdicionalModel datoAdicional=new DatoAdicionalModel();
                datoAdicional.setVisible(true);
                
                String valor=datoAdicional.valor;
                String campo=datoAdicional.campo;
                
                FacturaAdicional.Tipo tipoEnum=datoAdicional.tipo;
                
                if(factura!=null && valor!=null && tipoEnum!=null)
                {
                    if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_CORREO))
                    {
                        factura.addDatosAdicionalCorreo(valor,FacturaAdicional.Tipo.TIPO_CORREO,ComprobanteAdicional.CampoDefectoEnum.CORREO);
                    }
                    else
                    {
                        FacturaAdicional dato=new FacturaAdicional();
                        dato.setCampo(campo);
                        dato.setTipo(tipoEnum.getLetra());
                        dato.setNumero(0);
                        dato.setValor(valor);
                                
                        factura.addDatoAdicional(dato);
                    }
                    cargarTablaDatosAdicionales();
                }
                
                //datosAdicionales.put("", title);
            }
        });
        
                
        getBtnBuscarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //Verificar si ya existen detalles cargados se tienen que eliminar se debe preguntar al usuario
                if(factura!=null && factura.getDetalles()!=null && factura.getDetalles().size()>0)
                {
                    Boolean pregunta=DialogoCodefac.dialogoPregunta("Advertencia","Si selecciona otro estudiante se eliminaron los detalles cargados, desea continuar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if(pregunta)
                    {
                        factura.getDetalles().clear();
                        //Actualizar los detalles vacios en la vista
                        cargarDatosDetalles();
                        
                    }
                    else //Si selecciona no termina la ejecucion
                    {
                        return;
                    }
                }                
                
                EstudianteBusquedaDialogo clienteBusquedaDialogo = new EstudianteBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Estudiante estudianteTmp=(Estudiante) buscarDialogoModel.getResultado();
                
                if(estudianteTmp!=null)
                {
                    estudiante=estudianteTmp;     
                    setearValoresAcademicos(estudiante);
                    cargarDatosAdicionalesAcademicos();
                    cargarTablaDatosAdicionales();           
                    if(estudiante.getRepresentante()==null && estudiante.getRepresentante2()==null)
                    {
                        DialogoCodefac.mensaje("Advertencia","El estudiante no tienen ningun representante asignado", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                    else
                    {
                        factura.setCliente((Persona) getCmbRepresentante().getSelectedItem());   
                        getCmbRepresentante().setSelectedIndex(getCmbRepresentante().getSelectedIndex());
                        cargarFormaPago();
                    }
                }
                
                
            }
        });
        
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });

        getBtnAgregarFormaPago().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!factura.getDetalles().isEmpty()) {
                    FormaPagoDialogModel dialog = new FormaPagoDialogModel(null, true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    FormaPago formaPago = dialog.getFormaPago();
                    try {

                        factura.addFormaPago(formaPago);
                        verificarSumaFormaPago();

                        cargarFormasPagoTabla();
                    } catch (Exception ex) {
                        System.out.println("No existe forma de pago");
                    }

                }
            }
        });

        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                
                switch(tipoDocumentoEnum)
                {
                    case ACADEMICO:
                        agregarRubroAcademico();
                        break;
                    case PRESUPUESTOS:
                        agregarPresupuesto();
                        break;
                    case INVENTARIO: 
                        agregarProducto(EnumSiNo.SI);
                        break;
                    case LIBRE:
                        agregarProducto(EnumSiNo.NO);
                        break;
                
                }
                
            }
        });

        getTblDetalleFactura().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTblDetalleFactura().getSelectedRow();
                if(fila>=0)
                {
                    //setear valores para cargar de nuevo en los campos de la factura
                    FacturaDetalle facturaDetalle = factura.getDetalles().get(fila);
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

        getBtnAgregarDetalleFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(panelPadre.validarPorGrupo("detalles"));
                agregarDetallesFactura(null);
                
            }
        });

        getTxtCantidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getBtnAgregarDetalleFactura().isEnabled())
                {   //Si esta habilitado el boton de agregar funciona para agregar
                    agregarDetallesFactura(null);
                }
                else //Si no esta habilitado el boton de editar funciona como para editar
                {
                    btnListenerEditar();
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
        

        getBtnAgregarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerAgregarCliente();
            }
        });

        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerCrearProducto();
            }
        });

        getjDateFechaEmision().addPropertyChangeListener("date", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if(estadoFormulario.equals(ESTADO_GRABAR))
                {
                    java.util.Date fecha = getjDateFechaEmision().getDate();
                    if (!ComprobarRangoDeFechaPermitido(fecha)) {
                        DialogoCodefac.mensaje("Advertencia fecha", "La fecha seleccionada esta fuera del rango de autorizaciòn del SRI", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                }
            }
        });

        
        getCmbDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCmbDocumento().getSelectedItem()!=null)
                {
                    cargarSecuencial();
                }
            }
        });

    }
    
    private void btnListenerEditar()
    {
        int fila = getTblDetalleFactura().getSelectedRow();
        if(fila>=0)
        {
            try {
                FacturaDetalle facturaDetalle = factura.getDetalles().get(fila);
                //Buscar la referencia de las variables depedendiendo del modulo seleccionado
                TipoDocumentoEnum tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
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
                        
                }
                if(agregarDetallesFactura(facturaDetalle))
                {
                    habilitarModoIngresoDatos();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void habilitarModoIngresoDatos() {
        getBtnEditarDetalle().setEnabled(false);
        getBtnQuitarDetalle().setEnabled(false);
        getBtnAgregarDetalleFactura().setEnabled(true);
        getBtnAgregarProducto().setEnabled(true);
        getBtnCrearProducto().setEnabled(true);
    }
    
    private void btnListenerEliminar() {
        int fila = getTblDetalleFactura().getSelectedRow();
        if(fila>=0)
        {
            modeloTablaDetallesProductos.removeRow(fila);
            factura.getDetalles().remove(fila);
            cargarTotales();
            getBtnEditarDetalle().setEnabled(false);
            getBtnQuitarDetalle().setEnabled(false);
            getBtnAgregarDetalleFactura().setEnabled(true);
            getBtnAgregarProducto().setEnabled(true);
            getBtnCrearProducto().setEnabled(true);
        }
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

    private void btnListenerAgregarCliente()
    {
        Object[] parametros={OperadorNegocioEnum.CLIENTE,getTxtCliente().getText()};
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
            @Override
            public void updateInterface(Persona entity) {
                factura.setCliente(entity);
                if (factura.getCliente() != null) {
                    cargarCliente(entity);
                }
            }
        },VentanaEnum.CLIENTE, false,parametros,this);
    }
    
    private void btnListenerBuscarCliente() {
        ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());        
        cargarCliente((Persona) buscarDialogoModel.getResultado());        
    }
    
    private void cargarFormaPago()
    {
        if(factura.getFormaPagos()==null || factura.getFormaPagos().size()==0)
        {
            if(factura.getCliente()!=null)
            {
                FormaPago formaPago=new FormaPago();
                formaPago.setPlazo(0);
                
                if(factura.getCliente().getSriFormaPago()==null)
                    formaPago.setSriFormaPago(formaPagoDefecto);
                else
                    formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                    
                formaPago.setTotal(BigDecimal.ZERO);
                formaPago.setUnidadTiempo(FormaPago.UnidadTiempoEnum.NINGUNO.getNombre());

                factura.addFormaPago(formaPago);
                cargarFormasPagoTabla();
            }
        }
        else
        {
            //Si ya existe un dato ingresado solo edita el dato si se cambia de representante
            if (factura.getFormaPagos().size() == 1)
            {
                FormaPago formaPago = factura.getFormaPagos().get(0);
                //TODO: Optimizar para que se cambie la forma de pago solo si es un cliente distinto
                if (factura.getCliente().getSriFormaPago() != null) 
                {
                    formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                } 
                else //Si no esta grabado una forma de pago en el cliente asigno a forma de pago por defecto de las configuraciones
                {
                    if (formaPagoDefecto != null) 
                    {
                        formaPago.setSriFormaPago(formaPagoDefecto);
                    }
                }
                
                cargarFormasPagoTabla();
            }
        }
    }
    
    private void cargarCliente(Persona cliente)
    {
        if (cliente != null) {
            factura.setCliente(cliente);
            //Elimino datos adicionales del anterior cliente si estaba seleccionado
            //factura.eliminarTodosDatosAdicionales();
            
            cargarFormaPago();
            setearValoresCliente();
            cargarDatosAdicionales();
            cargarTablaDatosAdicionales();
        };
    }
    
    private boolean verificaDatoComboRepresentante(Persona persona)
    {
        DefaultComboBoxModel modelo=(DefaultComboBoxModel) getCmbRepresentante().getModel();
        
        if(modelo.getIndexOf(persona)<0)
        {
            return false;
        }
        return true;
    }
    
    private void agregarPresupuesto()
    {
        FacturaBusquedaPresupuesto presupuestoDialog=null;
        if(getChkFiltroPresupuestoCliente().isSelected())
        {
            presupuestoDialog = new FacturaBusquedaPresupuesto(factura.getCliente());
        }
        else
        {
            presupuestoDialog = new FacturaBusquedaPresupuesto();
        }
        
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(presupuestoDialog);
        buscarDialogoModel.setVisible(true);

        Presupuesto presupuestoTmp = (Presupuesto) buscarDialogoModel.getResultado();

        if (presupuestoTmp != null) {

            if(verificarExistePresupuestoAgregado(presupuestoTmp))
            {
                 DialogoCodefac.mensaje("Advertencia","EL presupuesto ya esta agregado, no se puede agregar nuevamente",DialogoCodefac.MENSAJE_ADVERTENCIA);
                 return;
            }            
            presupuestoSeleccionado=presupuestoTmp;
            
            String descripcion="P"+presupuestoSeleccionado.getId()+" OT"+presupuestoSeleccionado.getOrdenTrabajoDetalle().getOrdenTrabajo().getId()+"  "+presupuestoSeleccionado.getDescripcion();
            setearValoresProducto(presupuestoSeleccionado.getTotalVenta(),descripcion,presupuestoSeleccionado.getId().toString());
        }
    }
    
    private void agregarRubroAcademico()
    {
        RubroEstudianteBusqueda rubroBusquedaDialogo = new RubroEstudianteBusqueda(estudiante);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(rubroBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        RubroEstudiante rubroEstudianteTmp = (RubroEstudiante) buscarDialogoModel.getResultado();

        if (rubroEstudianteTmp != null) {
            //Verificar que no se puedan agregar rubros que ya estan en los detalles para facturar
            if(verificarExisteRubroAgregado(rubroEstudianteTmp))
            {
                DialogoCodefac.mensaje("Advertencia","EL rubro ya esta agregado, no se puede agregar nuevamente",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }            
            
            rubroSeleccionado=rubroEstudianteTmp;
            setearValoresProducto(rubroEstudianteTmp.getSaldo(),rubroEstudianteTmp.getRubroNivel().getNombre(),rubroEstudianteTmp.getId().toString());
        }
        
    }
    
    /**
     * Funcion que verifica si existe un rubro dentro de la lista de la factura
     * @param rubroEstudiante
     * @return 
     */
    private boolean verificarExisteRubroAgregado(RubroEstudiante rubroEstudiante)
    {
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) 
        {
            //Verificar solo los que son de tipo academico
            if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.ACADEMICO))
            {
                try {
                    RubroEstudiante rubroEstudianteTmp=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    
                    if(rubroEstudianteTmp!=null)
                    {
                        if(rubroEstudianteTmp.equals(rubroEstudiante))
                        {
                            return true;
                        }
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    private boolean verificarExistePresupuestoAgregado(Presupuesto presupuesto)
    {
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) 
        {
            //Verificar solo los que son de tipo academico
            if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.PRESUPUESTOS))
            {
                try {
                    Presupuesto presupuestoTmp=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    
                    if(presupuestoTmp!=null)
                    {
                        if(presupuestoTmp.equals(presupuesto))
                        {
                            return true;
                        }
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    private void agregarProducto(EnumSiNo manejaInventario) {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(manejaInventario);
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
    
    public void validacionesGrabar() throws ExcepcionCodefacLite
    {
        if (!verificarSumaFormaPago()) {
            throw new ExcepcionCodefacLite("Formas de pago erroneas");
        }

        if (factura.getCliente() == null) {
            DialogoCodefac.mensaje("Alerta", "Necesita seleccionar un cliente", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
        }

        if (factura.getDetalles().isEmpty()) {
            DialogoCodefac.mensaje("Alerta", "No se puede facturar sin detalles", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar detalles ");
        }

    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {

        try {
            
            validacionesGrabar();
            
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea facturar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
            
            Factura facturaProcesando; //referencia que va a tener la factura procesada para que los listener no pierdan la referencia a la variable del metodo.
            
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            setearValoresDefaultFactura();
            factura=servicio.grabar(factura);
            
            facturaProcesando = factura;
            
            
            //Si la factura en manual no continua el proceso de facturacion electronica
            if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()))
            {
                DialogoCodefac.mensaje("Correcto", "La factura se grabo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                facturaManual();
                
            }
            else
            {

                ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
                comprobanteData.setMapInfoAdicional(getMapAdicional(factura));
                ClienteInterfaceComprobante cic = new ClienteFacturaImplComprobante(this,facturaProcesando,true);
                ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
                
                if (ServiceFactory.getFactory().getComprobanteServiceIf().verificarDisponibilidadSri()) {
                    Boolean repuestaFacturaElectronica = DialogoCodefac.dialogoPregunta("Correcto", "La factura se grabo correctamente,Desea autorizar en el SRI ahora?", DialogoCodefac.MENSAJE_CORRECTO);

                    //Si quiere que se procese en ese momento le ejecuto el proceso normal
                    if (repuestaFacturaElectronica) {
                        //Verificar que existe comunicacion con el Sri
                        cic = new ClienteFacturaImplComprobante(this, facturaProcesando, false);
                        comprobanteServiceIf.procesarComprobante(comprobanteData, facturaProcesando, session.getUsuario(), cic);

                    } else {
                        //Solo genera el pdf pero no envia al SRI
                        comprobanteServiceIf.procesarComprobanteOffline(comprobanteData, facturaProcesando, session.getUsuario(), cic);
                        DialogoCodefac.mensaje("Correcto", "El comprobante esta firmado , no se olvide de enviar al SRI en un periodo maximo de 48 horas", DialogoCodefac.MENSAJE_CORRECTO);
                    }

                } else { //Si el servidor del sri no esta disponible solo existe un camino
                    DialogoCodefac.mensaje("Advertencia", "El servidor del Sri no esta disponible\n El comprobante esta firmado , no se olvide de enviar al SRI en un periodo maximo de 48 horas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    comprobanteServiceIf.procesarComprobanteOffline(comprobanteData, facturaProcesando, session.getUsuario(), cic);
                }

                
            }
            
     
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void facturaManual() throws ServicioCodefacException
    {
    
        try {
            DocumentoEnum documentoEnum = (DocumentoEnum) getCmbDocumento().getSelectedItem();
            
            InputStream reporteOriginal = null;
            if (documentoEnum.NOTA_VENTA.equals(documentoEnum)) {
                reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("nota_venta.jrxml");
            } else {
                reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("factura_fisica.jrxml");
            }
            
            ManagerReporteFacturaFisica manager = new ManagerReporteFacturaFisica(reporteOriginal);
            ComprobanteFisicoDisenioServiceIf servicioComprobanteDisenio = ServiceFactory.getFactory().getComprobanteFisicoDisenioServiceIf();;
            Map<String, Object> parametroComprobanteMap = new HashMap<String, Object>();
            parametroComprobanteMap.put("codigoDocumento", documentoEnum.getCodigo());
            ComprobanteFisicoDisenio documento = servicioComprobanteDisenio.obtenerPorMap(parametroComprobanteMap).get(0);
            manager.setearNuevosValores(documento);
            InputStream reporteNuevo = manager.generarNuevoDocumento();
            
            Map<String, Object> parametros = getParametroReporte(documentoEnum);
            
            //Llenar los datos de los detalles
            List<DetalleFacturaFisicaData> detalles = new ArrayList<DetalleFacturaFisicaData>();
            
            for (FacturaDetalle detalleFactura : factura.getDetalles()) {
                DetalleFacturaFisicaData detalle = new DetalleFacturaFisicaData();
                detalle.setCantidad(detalleFactura.getCantidad() + "");
                detalle.setDescripcion(detalleFactura.getDescripcion());
                detalle.setValorTotal(detalleFactura.getTotal() + "");
                detalle.setValorUnitario(detalleFactura.getPrecioUnitario() + "");
                detalles.add(detalle);
            }
            
            ReporteCodefac.generarReporteInternalFrame(reporteNuevo, parametros, detalles, panelPadre, "Muestra Previa");
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            throw  ex; //Relanza el error al proceso principal
        }
    
    }
    
    private Map<String,String> getMapAdicional(Factura factura)
    {
        Map<String,String> parametroMap=new HashMap<String ,String>();
        if(factura.getDatosAdicionales()!=null)
        {
            for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) 
            {
                parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
            }
        }
        return parametroMap;
    }
    
    public Map<String, Object> getParametroReporte(DocumentoEnum documento)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("fechaEmision", factura.getFechaEmision().toString());
        parametros.put("razonSocial", factura.getCliente().getRazonSocial());
        parametros.put("direccion", factura.getCliente().getDireccion());
        parametros.put("telefono", factura.getCliente().getTelefonoConvencional());
        parametros.put("correoElectronico", (factura.getCliente().getCorreoElectronico() != null) ? factura.getCliente().getCorreoElectronico() : "");
        parametros.put("identificacion", factura.getCliente().getIdentificacion());

        //Datos cuando es una nota de venta
        if(DocumentoEnum.NOTA_VENTA.equals(documento))
        {
            parametros.put("subtotal", factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).toString());
            parametros.put("descuento", factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString());
            parametros.put("total", factura.getTotal() + "");        
        }
        else
        {   //Datos cuando es una factura
            parametros.put("subtotalImpuesto", factura.getSubtotalImpuestos().toString());
            parametros.put("subtotalSinImpuesto", factura.getSubtotalSinImpuestos().toString());
            parametros.put("descuento", factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString());
            parametros.put("subtotalConDescuento", factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).subtract((factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()))).toString());
            parametros.put("valorIva", factura.getIva().toString());
            parametros.put("total", factura.getTotal() + "");
            String ivaStr = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
            parametros.put("iva", ivaStr);
        
        }
        return parametros;
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        DialogoCodefac.mensaje("Advertencia","Las facturas no se pueden modificar",DialogoCodefac.MENSAJE_ADVERTENCIA);
        throw new ExcepcionCodefacLite("cancelar el evento editar");
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite,RemoteException {
        //Varible 
        boolean respuesta =false;
        
        //Eliminar solo si esta en modo editar
        if (estadoFormulario.equals(ESTADO_EDITAR)) {
            if (factura != null) {
                
                //Verificar que la factura no tenga notas de credito aplicando porque no podria eliminar si se da esta condicion
                if(!factura.getEstadoNotaCreditoEnum().equals(Factura.EstadoNotaCreditoEnum.SIN_ANULAR))
                {
                    DialogoCodefac.mensaje(MensajeCodefacSistema.FacturasMensajes.ERROR_ELIMINAR_AFECTA_NOTA_CREDITO);
                    throw new ExcepcionCodefacLite("error");
                }
                
                
                //Eliminar solo si el estado esta en sin autorizar, o esta en el modo de facturacion normal y esta con estado facturado
                if (factura.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado()) || 
                        (factura.getTipoFacturacion().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()) && factura.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado()) )) {
                    
                    respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Esta seguro que desea eliminar la factura? ", DialogoCodefac.MENSAJE_ADVERTENCIA);

                } else {
                    respuesta=DialogoCodefac.dialogoPregunta("Alerta", "La factura se encuentra autorizada en el SRI , \nPorfavor elimine la factura solo si tambien esta anulado en el SRI\nDesea eliminar la factura de todos modos?", DialogoCodefac.MENSAJE_INCORRECTO);
                }
                
                //Eliminar la factura si eligen la respuesta si
                if (respuesta) {
                    try {
                        FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
                        servicio.eliminarFactura(factura);
                        DialogoCodefac.mensaje("Exitoso", "La factura se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                        getLblEstadoFactura().setText(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getNombre());
                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else
        {
            throw new ExcepcionCodefacLite("Cancelar evento eliminar porque no esta en modo editar");
        }
    }

    @Override
    public void imprimir() {
        if (this.factura != null && estadoFormulario.equals(ESTADO_EDITAR)) {
            try {
                String claveAceeso = this.factura.getClaveAcceso();
                byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAceeso);
                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint, factura.getPreimpreso());
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public InterfaceModelFind getBusquedaInterface()
    {
        FacturaBusqueda facturaBusqueda = new FacturaBusqueda();
        return facturaBusqueda;
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        InterfaceModelFind busquedaInterface=getBusquedaInterface();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaInterface);
        buscarDialogoModel.setVisible(true);
        Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();
        if (facturaTmp != null) {
            this.factura = facturaTmp;
            ///Cargar los datos de la factura segun el tipo de datos del primer detalle
            TipoDocumentoEnum tipoReferenciaEnum=factura.getDetalles().get(0).getTipoDocumentoEnum();
            getCmbTipoDocumento().setSelectedItem(tipoReferenciaEnum);
            seleccionarPanelTipoDocumento(tipoReferenciaEnum);
            
            switch(tipoReferenciaEnum)
            {
                case ACADEMICO:
                    try {
                        //getCmbTipoDocumento().setSelectedItem(tipoReferenciaEnum)                        

                        FacturaAdicional facturaAdicional = buscarCampoAdicionalPorNombre(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre());
                        Long estudianteInscritoId = Long.parseLong(facturaAdicional.getValor());
                        estudiante = ServiceFactory.getFactory().getEstudianteServiceIf().buscarPorId(estudianteInscritoId);

                        //setearValoresAcademicos(estudiante);
                        getTxtEstudiante().setText(estudiante.getNombreCompleto());
                        getCmbRepresentante().removeAllItems();
                        getCmbRepresentante().addItem(factura.getCliente());

                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    break;
                    
                case PRESUPUESTOS:
                    //getTxtClientePresupuesto().setText(factura.getCliente().toString());
                    //getLblNombresClientePresupuesto().setText(factura.getCliente().toString());                    
                    break;
                    
                case INVENTARIO: case LIBRE:
                    //getCmbTipoDocumento().getSelectedItem().equals(TipoDocumentoEnum.INVENTARIO);
                    setearValoresCliente();
                    break;
            
            }            
            
                        
            cargarDatosDetalles();
            limpiarDetalleFactura();
            cargarTotales();
            cargarValoresAdicionales();
            cargarFormasPagoTabla();
            cargarTablaDatosAdicionales();
        } else {
            throw new ExcepcionCodefacLite("cancelar ejecucion");
        }
    }
    
    private FacturaAdicional buscarCampoAdicionalPorNombre(String nombre)
    {
        for(FacturaAdicional facturaAdicional : factura.getDatosAdicionales())
        {
            if(facturaAdicional.getCampo().equals(nombre))
            {
                return facturaAdicional;
            }
        }
        return null;
    }

    @Override
    public void limpiar() {
        this.factura = new Factura();
        //this.for=new ArrayList<FormaPago>();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());

        //Setear los valores de la empresa 
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
        FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
        //getLblSecuencial().setText(servicio.getPreimpresoSiguiente());
        getLblEstadoFactura().setText("Procesando");

        //datosAdicionales = new HashMap<String, String>();
        //facturaElectronica = new FacturacionElectronica(session, this.panelPadre);

        //Limpiar los campos del cliente
        getLblNombreCliente().setText("");
        getLblTelefonoCliente().setText("");
        getLblDireccionCliente().setText("");
        getTxtCliente().setText("");

        //Limpiar campos de los detalles de la factura
        getTxtCodigoDetalle().setText("");
        getTxtValorUnitario().setText("");
        getTxtCantidad().setText("");
        getTxtDescripcion().setText("");
        getTxtDescuento().setText("");
        //getTxtClientePresupuesto().setText("");
        getCheckPorcentaje().setSelected(false);

        //Limpiar los datos de la tabla factura
        initModelTablaDetalleFactura();
        //Limpiar los datos forma pago
        initModelTablaFormaPago();
        //Limpiar los datos adicional
        initModelTablaDatoAdicional();

        //Limpiar los labels de los calculos
        getLblSubtotalSinImpuesto().setText("0.00");
        getLblSubtotal12().setText("0.00");
        getLblSubtotal0().setText("0.00");
        getLblSubtotalNoObjetoDeIva().setText("0.00");
        getLblSubTotalDescuentoConImpuesto().setText("0.00");
        getLblSubTotalDescuentoSinImpuesto().setText("0.00");
        getLblSubtotalExentoDeIva().setText("0.00");
        getLblTotalDescuento().setText("0.00");
        getLblValorIce().setText("0.00");
        getLblIva12().setText("0.00");
        getLblValorIRBPNR().setText("0.00");
        getLblPropina10().setText("0.00");
        getTxtValorTotal().setText("0.00");

        getBtnEditarDetalle().setEnabled(false);
        getBtnQuitarDetalle().setEnabled(false);
        getBtnAgregarDetalleFactura().setEnabled(true);
        getBtnAgregarProducto().setEnabled(true);
        getBtnCrearProducto().setEnabled(true);
        
        //Borrar los datos del estudiantes y los representantes
        getCmbRepresentante().removeAllItems();
        getTxtEstudiante().setText("");
        
        //Limpiar las variables de la facturacion
        setearVariablesIniciales();
        cargarSecuencial();
        

    }
    
    public void cargarSecuencial()
    {
        DocumentoEnum tipoDocumentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        String secuencial="";
        boolean facturacionElectronica=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra());
        
        switch(tipoDocumentoEnum)
        {
            case FACTURA:
                if(facturacionElectronica)
                    secuencial=session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA).valor; 
                else
                    secuencial = session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA_FISICA).valor;
                    
                break;
                
            case NOTA_VENTA:
                    secuencial = session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_VENTA_FISICA).valor;                    
                    break;
        }
        
     
        String preimpreso = UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(), 8, "0");
        String establecimiento = session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor;
        String puntoEmision = session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor;
        preimpreso=puntoEmision + "-" + establecimiento + "-" + preimpreso;
        getLblSecuencial().setText(preimpreso);
    }

//    @Override
    public String getNombre() {
        return "Facturacion";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#efactura";
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
    
    private DefaultTableModel initModelTablaFormaPago() {
        Vector<String> titulo = new Vector<>();
        titulo.add("forma pago");
        titulo.add("Valor");
        //titulo.add("Tipo");
        //titulo.add("Tiempo");

        DefaultTableModel modeloTablaFormasPago = new DefaultTableModel(titulo, 0);
        getTblFormasPago().setModel(modeloTablaFormasPago);
        return modeloTablaFormasPago;
    }


    private void cargarFormasPagoTabla() {

        //Crea el modelo con el titulo de las formas de pago
        DefaultTableModel modeloTablaFormasPago=initModelTablaFormaPago();

        List<FormaPago> formasPago = factura.getFormaPagos();
        for (FormaPago formaPago : formasPago) {
            Vector<String> fila = new Vector<>();
            fila.add(formaPago.getSriFormaPago().getAlias());
            fila.add(formaPago.getTotal().toString());
            //fila.add(formaPago.getUnidadTiempo());
            //fila.add(formaPago.getPlazo() + "");
            modeloTablaFormasPago.addRow(fila);
        }
        getTblFormasPago().setModel(modeloTablaFormasPago);
    }

    private void initModelTablaDetalleFactura() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("Valor Uni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Descuento");
        titulo.add("Valor Total");
        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleFactura().setModel(modeloTablaDetallesProductos);
    }

    private void initModelTablaDatoAdicional() {
        //Vector<String> titulo = new Vector<>();
        //titulo.add("Objecto");
        //titulo.add("Nombre");
        //titulo.add("Valor");

        this.modeloTablaDatosAdicionales=UtilidadesTablas.crearModeloTabla(new String[]{"","Nombre","Valor"},new Class[]{FacturaAdicional.class,String.class,String.class});
        //this.modeloTablaDatosAdicionales = new DefaultTableModel(titulo, 0);
        getTblDatosAdicionales().setModel(modeloTablaDatosAdicionales);
        
        UtilidadesTablas.ocultarColumna(getTblDatosAdicionales(),0); //Ocultar la fila del objeto para poder volver a modificar
    }

    /**
     * Metodo que actualiza los valores en la tabla
     */
    private void cargarTablaDatosAdicionales() {
        initModelTablaDatoAdicional();
        
        for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
            Vector dato = new Vector();
            dato.add(datoAdicional);
            dato.add(datoAdicional.getCampo());
            dato.add(datoAdicional.getValor());
            
            this.modeloTablaDatosAdicionales.addRow(dato);
        }

    }

    /**
     * Cargar los detalles de las facturas agregados
     */
    private void cargarDatosDetalles() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("ValorUni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Descuento");
        titulo.add("Valor Total");
        titulo.add(""); //Columna para el boton de eliminar

        List<FacturaDetalle> detalles = factura.getDetalles();

        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        for (FacturaDetalle detalle : detalles) {
            try {
                
                TipoDocumentoEnum tipoReferenciaEnum=detalle.getTipoDocumentoEnum();
                Vector<String> fila = new Vector<String>();
                
                switch(tipoReferenciaEnum)
                {
                    case ACADEMICO:
                        RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add(rubroEstudiante.getId() + "");
                        break;
                        
                    case PRESUPUESTOS:
                        //Presupuesto presupuesto=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add(detalle.getReferenciaId().toString());
                        break;
                    
                    case INVENTARIO: case LIBRE:
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add(producto.getCodigoPersonalizado());
                        break;

                        
                }
                 

                //Cargar los totales
                fila.add(detalle.getPrecioUnitario().toString());               
                //Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());

                fila.add(detalle.getCantidad().toString());
                fila.add(detalle.getDescripcion());
                fila.add(detalle.getDescuento().toString());
                fila.add(detalle.getTotal().toString());
                fila.add("Eliminar"); //Boton de eliminar para la tabla
                modeloTablaDetallesProductos.addRow(fila);
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getTblDetalleFactura().setModel(this.modeloTablaDetallesProductos);
        
        UtilidadesTablas.definirTamanioColumnas(getTblDetalleFactura(),new Integer[]{100,100,80,600,80,100,100}); //Definir los tamanios definidos para la tabla principal
        
        ButtonColumn botonEliminar=new ButtonColumn(getTblDetalleFactura(),new AbstractAction() { //Agregado boton de eliminar a la tabla
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerEliminar();
            }
        }, 6); 
        //botonEliminar.setMnemonic(KeyEvent.VK_D);
        
        modeloTablaDetallesProductos.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int columnaModificada=e.getColumn();
                int filaModificada=e.getFirstRow();
                
                if(filaModificada<0 || columnaModificada<0 || columnaModificada==6) //Si no existe ninguna fila seleccionada no ejecuta ninguna accion o si es lacolumna  6 que es el boton de eliminar
                    return;
                
                Object dato=modeloTablaDetallesProductos.getValueAt(filaModificada, columnaModificada);
                //TableModel modelo = ((TableModel) (e.getSource()));
                //String datoOriginal=modelo.getValueAt(filaModificada,columnaModificada)
                
                switch(columnaModificada)
                {
                    case 2:
                        getTxtCantidad().setText(dato.toString());
                        btnListenerEditar();
                        break;
                        
                }
                
            }
        });
        
        
    }

    private void agregarFechaEmision() {

    }

    /**
     * Limpiar las variables y los campos de de la vista de la parte de detalle
     */
    private void limpiarDetalleFactura() {
        
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
            
        
        //Limpio los datos en la pantalla
        getTxtCantidad().setText("");
        getTxtDescripcion().setText("");
        getTxtValorUnitario().setText("");
        getTxtDescuento().setText("0");
        getTxtCodigoDetalle().setText("");
        getTxtCodigoDetalle().requestFocus();
        getTxtCodigoDetalle().selectAll();
    }

    public void calcularTotalDescuento(List<FacturaDetalle> facturaDetalles) {
        /*
        //this.descuento = new BigDecimal(0);
        this.subTotalDescuentoConImpuesto = new BigDecimal(0);
        this.subTotalDescuentoSinImpuesto = new BigDecimal(0);
        //this.subtotalSinImpuestosDescuento = new BigDecimal(0);
        facturaDetalles.forEach((facturaDetalle)
                -> {
            //this.descuento = this.descuento.add(facturaDetalle.getDescuento());
            if (facturaDetalle.getProducto().getIva().getTarifa() == 12) {
                this.subTotalDescuentoConImpuesto = this.subTotalDescuentoConImpuesto.add(facturaDetalle.getDescuento());
            } else {
                this.subTotalDescuentoSinImpuesto = this.subTotalDescuentoSinImpuesto.add(facturaDetalle.getDescuento());
            }
        });
        //this.descuento = this.descuento.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.subTotalDescuentoConImpuesto = this.subTotalDescuentoConImpuesto.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.subTotalDescuentoSinImpuesto = this.subTotalDescuentoSinImpuesto.setScale(2, BigDecimal.ROUND_HALF_UP);*/
    }

    
    public void calcularSubtotales() {
        this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().subtract(this.factura.getSubtotalImpuestos()));
        this.factura.setDescuentoSinImpuestos(this.factura.getSubtotalImpuestos().add(this.factura.getSubtotalSinImpuestos()).subtract(factura.getSubtotalImpuestos()));
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

    public void cargarTotales() {
        factura.calcularTotalesDesdeDetalles();
        /**
         * Setear los componentes graficos despues de los calculos
         */
        getLblSubtotalSinImpuesto().setText("" + factura.getSubtotalSinImpuestos().add(factura.getSubtotalImpuestos()));
        getLblSubtotal12().setText("" + factura.getSubtotalImpuestos());
        getLblSubtotal0().setText("" + factura.getSubtotalSinImpuestos());
        getLblIva12().setText("" + factura.getIva());
        getTxtValorTotal().setText("" + this.factura.getTotal());
        getLblSubTotalDescuentoConImpuesto().setText("" + factura.getDescuentoImpuestos());
        getLblSubTotalDescuentoSinImpuesto().setText("" + factura.getDescuentoSinImpuestos());
        getLblTotalDescuento().setText("" + factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
        
        //Verifico que solo exista una forma de pago y si cumple ese requesito actualizo el valor de la forma de pago
        if (factura.getFormaPagos()!=null && factura.getFormaPagos().size() == 1) {
            FormaPago formaPago = factura.getFormaPagos().get(0);
            formaPago.setTotal(factura.getTotal());
            cargarFormasPagoTabla();
        }

    }
    
    private void setearValoresAcademicos(Estudiante estudiante)
    {
       
        getTxtEstudiante().setText(estudiante.getNombreCompleto());
        
        //Cargar los representantes por defecto
        getCmbRepresentante().removeAllItems();
        if(estudiante.getRepresentante()!=null)
        {
            getCmbRepresentante().addItem(estudiante.getRepresentante());
        }
        
        if(estudiante.getRepresentante2()!=null)
        {
            getCmbRepresentante().addItem(estudiante.getRepresentante2());
        }

    }
    
    private void cargarDatosAdicionalesAcademicos() {
        //Quita todos los datos anteriores para cargar los datos del estudiante
        if(factura.getDatosAdicionales()!=null)
            factura.getDatosAdicionales().clear();
        
        //Cargar el correo solo cuando exista 
        //factura.addDatosAdicionalCorreo(factura.getCliente().getCorreoElectronico());

        factura.addDatoAdicional(DatosAdicionalesComprobanteEnum.NOMBRE_ESTUDIANTE.getNombre(), estudiante.getNombreCompleto());

        factura.addDatoAdicional(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre(), estudiante.getIdEstudiante() + "");
    }
    
    private void cargarDatosAdicionales()
    {
        //Cargar el correo solo cuando exista 
        if (factura.getCliente().getCorreoElectronico() != null) {
            //Obtiene el campo del correo por defecto sis existe
            FacturaAdicional campoAdicional=factura.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.CORREO);
            //Si no existe el campo del correo del cliente lo creo
            if(campoAdicional==null)
            {
                if(factura.getCliente().getCorreoElectronico()!=null && !factura.getCliente().getCorreoElectronico().toString().isEmpty())
                    factura.addDatosAdicionalCorreo(factura.getCliente().getCorreoElectronico(),FacturaAdicional.Tipo.TIPO_CORREO,ComprobanteAdicional.CampoDefectoEnum.CORREO);
            }
            else //Si existe el campo del correo del cliente lo edito
            {
                campoAdicional.setValor(factura.getCliente().getCorreoElectronico());
            }                
            
            //datosAdicionales.put("email", factura.getCliente().getCorreoElectronico());
        }
        
        //Cargar el numero e celular del cliente
        if (factura.getCliente().getTelefonoCelular() != null) {
            //Obtiene el campo del correo por defecto sis existe
            FacturaAdicional campoAdicional=factura.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.CELULAR);
            //Si no existe el campo del correo del cliente lo creo
            if(campoAdicional==null)
            {
                if(factura.getCliente().getTelefonoCelular()!=null && !factura.getCliente().getTelefonoCelular().toString().isEmpty())
                    factura.addDatosAdicionalCorreo(factura.getCliente().getTelefonoCelular(),FacturaAdicional.Tipo.TIPO_CELULAR,ComprobanteAdicional.CampoDefectoEnum.CELULAR);
            }
            else //Si existe el campo del correo del cliente lo edito
            {
                campoAdicional.setValor(factura.getCliente().getTelefonoCelular());
            }                
            
            //datosAdicionales.put("email", factura.getCliente().getCorreoElectronico());
        }
        
    }

    private void setearValoresCliente() {
        
        
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        
        //Cargar los tipos de documentos segun el tipo de dcumento
        switch(tipoDocumentoEnum)
        {
            case PRESUPUESTOS:            
            case ACADEMICO:
            case INVENTARIO:
            case LIBRE:
                getTxtCliente().setText(factura.getCliente().getIdentificacion());
                getLblNombreCliente().setText(factura.getCliente().getRazonSocial());
                getLblDireccionCliente().setText(factura.getCliente().getDireccion());
                getLblTelefonoCliente().setText(factura.getCliente().getTelefonoConvencional());
                break;
                
                //getTxtClientePresupuesto().setText(factura.getCliente().toString());

        
        }

        /**
         * Cargar el estado de la factura
         */
        ComprobanteEntity.ComprobanteEnumEstado estadoEnum = ComprobanteEntity.ComprobanteEnumEstado.getEnum(factura.getEstado());
        
        if (estadoEnum != null) {
            getLblEstadoFactura().setText(estadoEnum.getNombre());
        }

        factura.setCliente(factura.getCliente());
        factura.setRazonSocial(factura.getCliente().getRazonSocial());
        factura.setIdentificacion(factura.getCliente().getIdentificacion());
        factura.setDireccion(factura.getCliente().getDireccion());
        factura.setTelefono(factura.getCliente().getTelefonoConvencional());
        //Actualiza la tabla de los datos adicionales
        //cargarDatosAdicionales();
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

    protected void setearValoresDefaultFactura() {
        /**
         * Todo: Carlos Pato
         */
        factura.setEmpresaId(session.getEmpresa().getId());
        factura.setEstado(Factura.ESTADO_FACTURADO);
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        factura.setFechaEmision(new Date(getjDateFechaEmision().getDate().getTime()));
        //factura.setIvaSriId(iva);
        factura.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        factura.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        
        //Cuando la facturacion es electronica
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        {
            factura.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA).valor));
        }
        else //cuando la facturacion es normal
        {
            factura.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA_FISICA).valor));
        }
        
        factura.setSubtotalSinImpuestos(BigDecimal.ZERO);

        /**
         * Seteado los valores temporales pero toca cambiar esta parte y setear
         * los valores directamente en la factura
         */
        factura.setTotal(new BigDecimal(getTxtValorTotal().getText()));
        factura.setSubtotalSinImpuestos(new BigDecimal(getLblSubtotal0().getText()));
        factura.setSubtotalImpuestos(new BigDecimal(getLblSubtotal12().getText()));
        factura.setIva(new BigDecimal(getLblIva12().getText()));
        
        DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
        factura.setCodigoDocumento(documentoEnum.getCodigo());
        
        factura.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        
        /**
         * Redondeo los valores de los precios unitario de los detalles de la factura
         * Nota: este proceso lo hago al final porque para los totales necesitaba tener los valores exactos de los precios unitarios, pero como ya va a generar la factura puedo redondeal los valores unitario
         */
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) {
            facturaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario().setScale(2,RoundingMode.HALF_UP));
        }

    }

    private void initComponenesGraficos() {
        URL path = RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/mas-ico.png");
        getBtnAgregarDetalleFactura().setIcon(new ImageIcon(path));
        getBtnAgregarDetalleFactura().setText("");
        getBtnAgregarDetalleFactura().setToolTipText("Agregar detalle factura");

        getBtnEditarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/edit_icon.png")));
        getBtnEditarDetalle().setText("");
        getBtnEditarDetalle().setToolTipText("Editar detalle factura");

        getBtnQuitarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/cerrar-ico.png")));
        getBtnQuitarDetalle().setText("");
        getBtnQuitarDetalle().setToolTipText("Eliminar detalle factura");

        getBtnAgregarCliente().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/usuario.png")));
        getBtnAgregarCliente().setText("");
        getBtnAgregarCliente().setToolTipText("Crear nuevo cliente");

        getBtnAgregarProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/list.png")));
        getBtnAgregarProducto().setText("");
        getBtnAgregarProducto().setToolTipText("Agregar producto a la factura");

        getBtnCrearProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/add2.png")));
        getBtnCrearProducto().setText("");
        getBtnCrearProducto().setToolTipText("Crear nuevo producto");

        getBtnAgregarFormaPago().setText("");
        getBtnAgregarFormaPago().setToolTipText("Agregar formas e pago");

    }

    public boolean ComprobarRangoDeFechaPermitido(java.util.Date fecha) {
        boolean fechaDespues = fecha.after(fechaMin);
        boolean fechaAntes = (fecha.before(fechaMax));

        if (fechaDespues && fechaAntes) {
            return true;
        }
        return false;
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        System.out.println("Ingresando a iniciar");
        if (!validacionParametrosCodefac()) {
            dispose();
            throw new ExcepcionCodefacLite("No cumple validacion inicial");

        }
        
        iniciarValoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        /*
        if(factura.getCliente()!=null || (factura.getDetalles()!=null && factura.getDetalles().size()>0) )
        {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
        }*/
    }

    @Override
    public boolean salirSinGrabar() {
        if(factura.getCliente()!=null || (factura.getDetalles()!=null && factura.getDetalles().size()>0) )
        {
            //Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;            
        }
        return true;
    }
    
    

    public void definirFechaMinFacturacion() {

        getjDateFechaEmision().setDate(new java.util.Date());
        //this.getjDateFechaEmision().setEditable(false);
        //((JTextField) this.getjDateFechaEmision().getDateEditor()).setEditable(false);

        this.fechaMax = new java.util.Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fechaMax);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        this.fechaMin = calendar.getTime();

    }

    private void cargarValoresAdicionales() {
        getLblEstadoFactura().setText((factura.getEstadoEnum()!=null)?factura.getEstadoEnum().getNombre():"Sin estado");
        getLblSecuencial().setText(factura.getPreimpreso());
        getjDateFechaEmision().setDate(factura.getFechaEmision());
    }

    private boolean validacionParametrosCodefac() {
        System.out.println("Ingreso a la validacion de Paremtros Codefac");
        String mensajeValidacion = "Esta pantalla requiere : \n";
        boolean validado = true;
        
        //Validacion cuando solo sea facturacion manual
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()))
        {
            if (session.getEmpresa() == null) 
            {
                mensajeValidacion += " - Información de Empresa \n";
                validado = false;
            }
        }
        else         {        
       
            try //Validacion cunando es facturacion electronica
            {
                if (session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor().equals("")) {
                    mensajeValidacion += " - Archivo Firma\n";
                    validado = false;
                }
                
                
                String claveFirmaElectronica=UtilidadesEncriptar.desencriptar(session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
                
                if (claveFirmaElectronica.equals("")) {
                    mensajeValidacion += " - Clave Firma\n";
                    validado = false;
                }
                
                if (session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals("")) {
                    mensajeValidacion += " - Correo\n";
                    validado = false;
                }
                
                if (session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals("")) {
                    mensajeValidacion += " - Clave Correo \n";
                    validado = false;
                }
                
                if (session.getEmpresa() == null) {
                    mensajeValidacion += " - Información de Empresa \n";
                    validado = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if (!validado) {
            //mensajeValidacion=mensajeValidacion.substring(0,mensajeValidacion.length()-2);
            DialogoCodefac.mensaje("Acceso no permitido", mensajeValidacion + "\nPofavor complete estos datos en configuración para usar esta pantalla", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }

        return validado;

    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean verificarSumaFormaPago() {
        
        //Obtiene el total de las formas de pago
        BigDecimal totalFormasPago = factura.getTotalFormasPago();
        int res = factura.getTotal().compareTo(totalFormasPago);
        if (res == -1) 
        {
            DialogoCodefac.mensaje("Advertencia", "La forma de pago sobrepasa el valor a Facturar", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        } 
        else 
        {
            if(res==1)
            {
                DialogoCodefac.mensaje("Advertencia", "La formas de pago es inferior a el valor a Facturar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return false;
            }
            return true;
        }
    }

    public boolean agregarDetallesFactura(FacturaDetalle facturaDetalle) {
        boolean agregar = true;

        //Verifica si manda un detalle existe solo se modifica
        if (facturaDetalle != null) {
            agregar = false;
        } else {
            facturaDetalle = new FacturaDetalle();
        }

        if (!panelPadre.validarPorGrupo("detalles")) {
            int filaSeleccionada=getTblDetalleFactura().getSelectedRow();
            cargarDatosDetalles(); //Si no se pudo editar vuelvo a cargar los detalles si se modifico desde la tabla para que quede la forma original
            getTblDetalleFactura().setRowSelectionInterval(filaSeleccionada,filaSeleccionada);
            return false;
        }
        

        if (verificarCamposValidados()) {
            
            //Validacion dependiendo de la logica de cada tipo de documento
            if (!validacionPersonalizadaPorModulos()) {
                return false;
            }
            
            
            try {
                
                                //Variable del producto para verificar otros datos como el iva
                CatalogoProducto catalogoProducto=null;
                //Seleccionar la referencia dependiendo del tipo de documento
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
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
                }
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
                facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
                
                if (catalogoProducto.getIva().getTarifa().equals(0)) {
                    facturaDetalle.setIva(BigDecimal.ZERO);
                } else {
                    BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    facturaDetalle.setIva(iva);
                }
                
                if (facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).compareTo(facturaDetalle.getDescuento()) > 0) {
                    
                    //Solo agregar si se enviar un dato vacio
                    if (agregar) {
                        factura.addDetalle(facturaDetalle);
                    }
                    
                    cargarDatosDetalles();
                    limpiarDetalleFactura();
                    cargarTotales();
                } else {
                    DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    limpiarDetalleFactura();
                    return false;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return true; //si pasa todas las validaciones asumo que se edito correctamente

        }
        else
        {
            return false;
        }
        
    }
    
    //TODO: Para optimizar y mejorar el codigo analizar para utilizar una sola funcion con la anterior
    public void agregarDetallesFactura(BigDecimal cantidad,BigDecimal valorUnitario,String descripcion,Boolean descuentoPorcentaje,BigDecimal descuento,Object referencia) {
        FacturaDetalle facturaDetalle = new FacturaDetalle();

            
            try {
                //FacturaDetalle facturaDetalle = new FacturaDetalle();
                facturaDetalle.setCantidad(cantidad);
                facturaDetalle.setDescripcion(descripcion);
                BigDecimal valorTotalUnitario =valorUnitario;
                facturaDetalle.setPrecioUnitario(valorTotalUnitario.setScale(2, BigDecimal.ROUND_HALF_UP));
                
                //Variable del producto para verificar otros datos como el iva
                CatalogoProducto catalogoProducto=null;
                //Seleccionar la referencia dependiendo del tipo de documento
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                
                switch (tipoDocumentoEnum) {
                    case ACADEMICO:
                        RubroEstudiante rubroSeleccionado = (RubroEstudiante) referencia;
                        facturaDetalle.setReferenciaId(rubroSeleccionado.getId());
                        facturaDetalle.setTipoDocumento(TipoDocumentoEnum.ACADEMICO.getCodigo());
                        catalogoProducto = rubroSeleccionado.getRubroNivel().getCatalogoProducto();
                        break;

                    case PRESUPUESTOS:
                        Presupuesto presupuesto=(Presupuesto)referencia;
                        facturaDetalle.setReferenciaId(presupuesto.getId());
                        facturaDetalle.setTipoDocumento(TipoDocumentoEnum.PRESUPUESTOS.getCodigo());
                        catalogoProducto = ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                        break;

                    case INVENTARIO:
                    case LIBRE:
                        Producto productoSeleccionado = (Producto) referencia;
                        facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
                        facturaDetalle.setTipoDocumento(TipoDocumentoEnum.INVENTARIO.getCodigo());
                        catalogoProducto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                        break;
                }
                
                
                facturaDetalle.setValorIce(BigDecimal.ZERO);
                
                if (!descuentoPorcentaje) {
                    facturaDetalle.setDescuento(descuento);
                } else {
                    BigDecimal porcentajeDescuento = descuento;
                    
                    porcentajeDescuento = porcentajeDescuento.divide(new BigDecimal(100));
                    BigDecimal total = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario());
                    descuento = total.multiply(porcentajeDescuento);
                    facturaDetalle.setDescuento(descuento.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                
                //Calular el total despues del descuento porque necesito esa valor para grabar
                BigDecimal setTotal = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).subtract(facturaDetalle.getDescuento());
                facturaDetalle.setTotal(setTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
                facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
                /**
                 * Revisar este calculo del iva para no calcular 2 veces al mostrar
                 */
                
                if (catalogoProducto.getIva().getTarifa().equals(0)) {
                    facturaDetalle.setIva(BigDecimal.ZERO);
                } else {
                    BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    facturaDetalle.setIva(iva);
                }
                
                if (facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).compareTo(facturaDetalle.getDescuento()) > 0) {
                    
                    factura.addDetalle(facturaDetalle);

                    cargarDatosDetalles();
                    limpiarDetalleFactura();
                    cargarTotales();
                } else {
                    DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    limpiarDetalleFactura();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    /**
     * Metodo para setear valores de la factura de manera externa
     */
    public void setearValoresFactura()
    {
        if (factura != null) {
            this.factura = factura;
            ///Cargar los datos de la factura
            setearValoresCliente();
            cargarDatosDetalles();
            limpiarDetalleFactura();
            cargarTotales();
            cargarValoresAdicionales();
            //cargarFormasPagoTabla();
        }
    }

    public void iniciarValoresIniciales() {
        List<DocumentoEnum> tiposDocumento=null;
        
        try {
            //Obtener la forma de pago
            formaPagoDefecto=ServiceFactory.getFactory().getSriServiceIf().obtenerFormarPagoDefecto();
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //cuando la factura es electronica
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        {
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosElectronicos(ModuloCodefacEnum.FACTURACION);
        }
        else //Cuando la factura es fisica
        {
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosFisico(ModuloCodefacEnum.FACTURACION);
        }
        
        getCmbDocumento().removeAllItems();
        for (DocumentoEnum tipoDocumentoEnum : tiposDocumento) {
            getCmbDocumento().addItem(tipoDocumentoEnum);
        }
        
        
        //Agregar los tipos de documentos disponibles
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.FACTURACION,session.getModulos());
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
        
        //Seleccionar el tipo de documento configurado por defecto
        ParametroCodefac parametroCodefac=session.getParametrosCodefac().get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA);
        TipoDocumentoEnum tipoDocumentoEnumDefault=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroCodefac.getValor());
        getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnumDefault);
    }

    private void addListenerCombos() {
        getCmbTipoDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();                    
                seleccionarPanelTipoDocumento(tipoDocumentoEnum);
                limpiarDetalleFactura();
            }
        });
        
        getCmbRepresentante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(factura!=null)
                {
                    //Si cambie el combo de representante tambien los seteo en la factura
                    Persona persona=(Persona) getCmbRepresentante().getSelectedItem();
                    
                    //Solo ejecutar el listener si existe un persona seleccionada
                    if(persona!=null)
                    {
                        factura.setCliente(persona);
                        cargarCliente(persona); //carga los datos del representante en los campos del cliente
                        
                        //Modificar el correo principal de los datos adicionales por el del nuevo cliente
                        FacturaAdicional facturaAdicional=factura.obtenerDatoAdicionalPorCampo(FacturaAdicional.CampoDefectoEnum.CORREO);
                        //Si el campo ya existe solo lo modifico
                        if(facturaAdicional!=null)
                        {   
                            if(persona.getCorreoElectronico()!=null)
                                facturaAdicional.setValor(persona.getCorreoElectronico());
                            else//Si no existe correo lo elimina de la lista
                                factura.getDatosAdicionales().remove(facturaAdicional);
                        }
                        else //Si no existe el campo de correo lo crea
                        {
                            //Solo agregar si el cliente tiene un correo por defecto
                            if(persona.getCorreoElectronico()!=null)
                            {
                                factura.addDatosAdicionalCorreo(persona.getCorreoElectronico(),FacturaAdicional.Tipo.TIPO_CORREO,ComprobanteAdicional.CampoDefectoEnum.CORREO);

                            }
                        }
                        
                        cargarTablaDatosAdicionales();

                        //Cargar las formas de pago por defecto del cliente
                        cargarFormaPago();
                        
                    }
                }
            }
        });
    }
    
    
    private void seleccionarPanelTipoDocumento(TipoDocumentoEnum tipoDocumentoEnum)
    {
        if (tipoDocumentoEnum != null) {
            switch(tipoDocumentoEnum)
            {
                case ACADEMICO:
                    activarTabDatos(1);
                    break;
                case PRESUPUESTOS:
                    activarTabDatos(2);
                    break;
                case INVENTARIO: case LIBRE:
                    activarTabDatos(0);
                    break;
            }
        }        
        
    }
    
    
    private void activarTabDatos(int indice)
    {
        for (int i = 0; i < getPanelTabDatos().getTabCount(); i++) {
            if(i==indice)
            {
                getPanelTabDatos().setEnabledAt(i,true);
                getPanelTabDatos().setSelectedIndex(i);
            }
            else
            {
                getPanelTabDatos().setEnabledAt(i,false);
            }
        }
            
        
    }

    /**
     * @author Carlos
     * Validacion de la la logica dependiendo el modulo
     * @return 
     */
    private boolean validacionPersonalizadaPorModulos() {
        TipoDocumentoEnum tipoDocEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        BigDecimal cantidad = new BigDecimal(getTxtCantidad().getText());
        BigDecimal valorUnitario = new BigDecimal(getTxtValorUnitario().getText());

        switch(tipoDocEnum)
        {
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
            
        }
        
        return true;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        DocumentoEnum documentoEnum=(DocumentoEnum) parametros[0];
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) parametros[1];
        
        getCmbDocumento().setSelectedItem(documentoEnum);
        getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnum);
        
        
        estudiante=(Estudiante) parametros[2];
        factura.setCliente((Persona) parametros[3]);
        
        //Agregar los detalles enviados cuando son enviados desde el modulo academicos
        if(tipoDocumentoEnum.equals(TipoDocumentoEnum.ACADEMICO))
        {
            setearValoresAcademicos(estudiante);
            cargarDatosAdicionalesAcademicos();
            cargarTablaDatosAdicionales();
            
            List<RubroEstudiante> rubrosEstudiantes=(List<RubroEstudiante>) parametros[4];
            for (RubroEstudiante rubro : rubrosEstudiantes) {
                
                String descripcion=rubro.getRubroNivel().getNombre();
                
                if(rubro.getProcentajeDescuento()>0)
                {
                    descripcion+="("+rubro.getNombreDescuento()+"-"+rubro.getProcentajeDescuento()+"%)";                    

                }                
                agregarDetallesFactura(BigDecimal.ONE,rubro.getSaldo(),descripcion,true,new BigDecimal(rubro.getProcentajeDescuento()+""),rubro);
            }
            
            //Cargar los representate seteado en el combo box
            getCmbRepresentante().setSelectedIndex(getCmbRepresentante().getSelectedIndex());

        }
        else
        {
            //TODO: implementar para el otro caso cuando sea de otros modulos
        
        }
        
        cargarFormaPago();
        
        
        
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addListenerCamposTexto() {
        //Evento para buscar y cargar un cliente o abrir una nueva ventana para crear el cliente
        getTxtCliente().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String identificacion=getTxtCliente().getText();
                if(!identificacion.equals(""))
                {
                    //mapParametros.put("tipo",OperadorNegocioEnum.CLIENTE); //TODO: Falta optimizar cuando sean clientes y proveedores o ambos 

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            mapParametros.put("identificacion", identificacion);
                            List<Persona> resultados=ServiceFactory.getFactory().getPersonaServiceIf().obtenerPorMap(mapParametros); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(resultados.size()==0)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Cliente","No existe el Cliente, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    btnListenerAgregarCliente();
                                }
                            }
                            else
                            {
                                cargarCliente(resultados.get(0));
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        
        getTxtCodigoDetalle().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                
                //Solo validar si existe datos ingresados en el combo
                if(getTxtCodigoDetalle().getText().trim().equals(""))
                {
                    return;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    try {
                        TipoDocumentoEnum tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                        
                        switch (tipoDocumentoEnum) {
                            case ACADEMICO:
                                //agregarRubroAcademico();
                                break;
                            case PRESUPUESTOS:
                                //agregarPresupuesto();
                                break;
                            case INVENTARIO:
                            case LIBRE:
                                
                                Map<String,Object> mapParametros=new HashMap<String,Object>();
                                mapParametros.put("codigoPersonalizado", getTxtCodigoDetalle().getText()); //TODO: VER COMO MANEJAR TODOS LOS TIPOS DE CODIGO, VER UNA OPCION DE PARAMETRIZAR POR QUE CODIGO SE QUIERE TRABAJAR
                                List<Producto> productos=ServiceFactory.getFactory().getProductoServiceIf().obtenerPorMap(mapParametros);
                                if(productos.size()==0)
                                {
                                    if (DialogoCodefac.dialogoPregunta("Crear Producto", "No existe el Producto, lo desea crear?", DialogoCodefac.MENSAJE_ADVERTENCIA)) {
                                       btnListenerCrearProducto();
                                    }
                                }
                                else
                                {
                                    agregarProductoVista(productos.get(0));
                                }

                                break;
                                
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void addListenerTablas() {
       
          
        getTblDetalleFactura().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                getTblDetalleFactura().addKeyListener(new KeyListener() {
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

    private void setearValoresVista() {
        UtilidadesSwingX.placeHolder("Identificación", getTxtCliente());
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
                    FacturaAdicional facturaAdicional=(FacturaAdicional)getTblDatosAdicionales().getValueAt(filaSeleccionada,0); //Obtener el objeto de la columna
                    factura.getDatosAdicionales().remove(facturaAdicional);
                    cargarTablaDatosAdicionales();//Volver a cargar los datos adicionales en la tabla de la vista
                }
            }
        });
        
        jPopupMenu.add(jMenuItemDatoAdicional);
        getTblDatosAdicionales().setComponentPopupMenu(jPopupMenu);
        
        
        //Agregar pop up para la tabla de formas de pago
        JPopupMenu jPopupMenu2=new JPopupMenu();
        JMenuItem jMenuItemFormaPago=new JMenuItem("Eliminar");
        jMenuItemFormaPago.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblFormasPago().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    factura.getFormaPagos().remove(filaSeleccionada);
                    cargarFormasPagoTabla();
                }
            }
        });
        
        jPopupMenu2.add(jMenuItemFormaPago);
        getTblFormasPago().setComponentPopupMenu(jPopupMenu2);
        
        

                
    }
    
    
    

}
