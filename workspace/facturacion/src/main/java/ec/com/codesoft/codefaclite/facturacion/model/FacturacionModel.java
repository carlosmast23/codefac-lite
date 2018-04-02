/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusqueda;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DatosAdicionalesComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoReferenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.ejemplo.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel implements InterfazPostConstructPanel{

    //private Persona persona;
    private Factura factura;
    private Estudiante estudiante;
    private DefaultTableModel modeloTablaFormasPago;
    private DefaultTableModel modeloTablaDetallesProductos;
    private DefaultTableModel modeloTablaDatosAdicionales;
    private Producto productoSeleccionado;
    private RubroEstudiante rubroSeleccionado;
    private int fila;
    private int filaFP;
    private boolean bandera;
    private boolean banderaFP;
    private boolean banderaAgregar;
    private boolean banderaFormaPago;
    private BigDecimal valorTotalFormaDePago;
    private java.util.Date fechaMax;
    private java.util.Date fechaMin;

    /**
     * Mapa de datos adicionales que se almacenan temporalmente y sirven para la
     * facturacion electronica como por ejemplo el correo
     */
    //private Map<String, String> datosAdicionales;


    public FacturacionModel() {
        setearFechas();
        addListenerButtons();
        addListenerCombos();
        initComponenesGraficos();
        initModelTablaFormaPago();
        initModelTablaDetalleFactura();
        initModelTablaDatoAdicional();
        //setearVariablesIniciales();

    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) throws ExcepcionCodefacLite {
        this.factura = factura;
        setearValoresFactura();
    }
    
    /*setearVariablesIniciales()
    private void asds()
    {
                getLblSubtotalSinImpuesto().setText("" + this.subtotalSinImpuestos);
        getLblSubtotal12().setText("" + this.subtotal12);
        getLblSubtotal0().setText("" + this.subtotal0);
        getLblIva12().setText("" + this.iva);
        getTxtValorTotal().setText("" + this.valorTotal);
        getLblSubTotalDescuentoConImpuesto().setText("" + this.subTotalDescuentoConImpuesto);
        getLblSubTotalDescuentoSinImpuesto().setText("" + this.subTotalDescuentoSinImpuesto);
        getLblTotalDescuento().setText("" + this.descuento);
    }*/
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
        this.bandera = false;
        this.banderaAgregar = true;
        this.banderaFormaPago = true;
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        calcularIva12();
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
            if (!campo.getBackground().equals(Color.white)) {
                b = false;
            }
        }
        return b;
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
                        factura.addDatosAdicionalCorreo(valor);
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
        
        getBtnBuscarRepresentante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                
                Persona represetanteTmp=(Persona) buscarDialogoModel.getResultado();
                
                if(represetanteTmp!=null)
                {
                    //Agregar solo si no existe el dato en el combo box
                    if(!verificaDatoComboRepresentante(represetanteTmp))
                    {
                        getCmbRepresentante().addItem(represetanteTmp);
                        getCmbRepresentante().setSelectedItem(represetanteTmp);
                    }
                    else
                    {
                        getCmbRepresentante().setSelectedItem(represetanteTmp);
                    }
                    
                    /*
                    try {
                        Map<String,Object> parametrosMap=new HashMap<String,Object>();
                        parametrosMap.put("representante",represetanteTmp);
                        
                        List<Estudiante> estudiantes=ServiceFactory.getFactory().getEstudianteServiceIf().obtenerPorMap(parametrosMap);
                        if(estudiantes.size()>0)
                        {
                            factura.setCliente(represetanteTmp);
                            estudiante=estudiantes.get(0);      
                            setearValoresAcademicos(estudiante);
                            cargarDatosAdicionalesAcademicos();
                            cargarTablaDatosAdicionales();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                }

            }
        });
        
        getBtnBuscarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    factura.setCliente((Persona) getCmbRepresentante().getSelectedItem());
                    cargarFormaPago();
                }
                
                
            }
        });

        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                factura.setCliente((Persona) buscarDialogoModel.getResultado());
                if (factura.getCliente() != null) 
                {
                    cargarFormaPago();
                    setearValoresCliente();
                    cargarDatosAdicionales();
                    cargarTablaDatosAdicionales();
                };
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
                    valorTotalFormaDePago = formaPago.getTotal();
                    try {

                        verificarSumaFormaPago();
                        if (banderaFormaPago) {
                            factura.addFormaPago(formaPago);
                            valorTotalFormaDePago = new BigDecimal("0");
                        }
                        cargarFormasPagoTabla();
                    } catch (Exception ex) {
                        System.out.println("No existe forma de pago");
                    }

                }
            }
        });

        getBtnQuitarDetalleFormaPago().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (banderaFP) {
                    modeloTablaFormasPago.removeRow(filaFP);
                    factura.getFormaPagos().remove(filaFP);
                    verificarSumaFormaPago();
                    cargarFormasPagoTabla();
                    banderaFP = false;
                }
            }
        });

        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                //Si el tipo de documento es academico , agrega rubros
                if(tipoDocumentoEnum.equals(TipoDocumentoEnum.ACADEMICO))
                {
                    agregarRubroAcademico();
                }
                else //Si el tipo de documento es productos , agrega productos
                {
                    agregarProducto();
                }
                
            }
        });

        getTblDetalleFactura().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fila = getTblDetalleFactura().getSelectedRow();
                //setear valores para cargar de nuevo en los campos de la factura
                FacturaDetalle facturaDetalle = factura.getDetalles().get(fila);
                getTxtValorUnitario().setText(facturaDetalle.getPrecioUnitario() + "");
                getTxtCantidad().setText(facturaDetalle.getCantidad() + "");
                getTxtDescripcion().setText(facturaDetalle.getDescripcion());
                getTxtDescuento().setText(facturaDetalle.getDescuento() + "");
                getCheckPorcentaje().setSelected(false);
                bandera = true;
                getBtnEditarDetalle().setEnabled(true);
                getBtnQuitarDetalle().setEnabled(true);
                getBtnAgregarDetalleFactura().setEnabled(false);
                getBtnAgregarProducto().setEnabled(false);
                getBtnCrearProducto().setEnabled(false);
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
                agregarDetallesFactura(null);
            }
        });

        getBtnQuitarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bandera) {
                    bandera = false;
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
        });

        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bandera) {
                    fila = getTblDetalleFactura().getSelectedRow();
                    FacturaDetalle facturaDetalle = factura.getDetalles().get(fila);
                    agregarDetallesFactura(facturaDetalle);
                    getBtnEditarDetalle().setEnabled(false);
                    getBtnQuitarDetalle().setEnabled(false);
                    getBtnAgregarDetalleFactura().setEnabled(true);
                    getBtnAgregarProducto().setEnabled(true);
                    getBtnCrearProducto().setEnabled(true);
                }
            }
        });

        getBtnAgregarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                    @Override
                    public void updateInterface(Persona entity) {
                        factura.setCliente(entity);
                        if (factura.getCliente() != null) {
                            cargarFormaPago();
                            setearValoresCliente();
                            cargarDatosAdicionales();
                            cargarTablaDatosAdicionales();;
                        }
                    }
                }, DialogInterfacePanel.CLIENTE_PANEL, false);
            }
        });

        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
                    @Override
                    public void updateInterface(Producto entity) {
                        if (entity != null) {
                            productoSeleccionado = entity;
                            setearValoresProducto(productoSeleccionado.getValorUnitario(),productoSeleccionado.getNombre());
                            banderaAgregar = true;
                        }
                    }
                };

                panelPadre.crearDialogoCodefac(observer, DialogInterfacePanel.PRODUCTO_PANEL, false);

            }
        });

        getjDateFechaEmision().addPropertyChangeListener("date", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println("Fecha : ------->" + e.getNewValue());
                java.util.Date fecha = getjDateFechaEmision().getDate();
                if (!ComprobarRangoDeFechaPermitido(fecha)) {
                    DialogoCodefac.mensaje("Advertencia fecha", "La fecha seleccionada esta fuera del rango de autorizaciòn del SRI", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });

        getTblFormasPago().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filaFP = getTblFormasPago().getSelectedRow();
                banderaFP = true;
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
    
    private void cargarFormaPago()
    {
        if(factura.getFormaPagos()==null || factura.getFormaPagos().size()==0)
        {
            if(factura.getCliente()!=null)
            {
                FormaPago formaPago=new FormaPago();
                formaPago.setPlazo(0);
                formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                formaPago.setTotal(BigDecimal.ZERO);
                formaPago.setUnidadTiempo(FormaPago.UnidadTiempoEnum.NINGUNO.getNombre());

                factura.addFormaPago(formaPago);
            }
        }        
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
    
    private void agregarRubroAcademico()
    {
        RubroEstudianteBusqueda rubroBusquedaDialogo = new RubroEstudianteBusqueda(estudiante);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(rubroBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        RubroEstudiante rubroEstudianteTmp = (RubroEstudiante) buscarDialogoModel.getResultado();

        if (rubroEstudianteTmp != null) {
            rubroSeleccionado=rubroEstudianteTmp;
            setearValoresProducto(rubroEstudianteTmp.getSaldo(),rubroEstudianteTmp.getRubroNivel().getNombre());
            banderaAgregar = true;
        }

        
    }
    
    private void agregarProducto() {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        productoSeleccionado = (Producto) buscarDialogoModel.getResultado();
        if (productoSeleccionado == null) {
            return;
            //throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
        }
        setearValoresProducto(productoSeleccionado.getValorUnitario(),productoSeleccionado.getNombre());
        banderaAgregar = true;
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {

        try {
            if (!banderaFormaPago) {
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
            if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(TipoFacturacionEnumEstado.NORMAL.getLetra()))
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
                Boolean repuestaFacturaElectronica = DialogoCodefac.dialogoPregunta("Correcto", "La factura se grabo correctamente,Desea autorizar en el SRI ahora?", DialogoCodefac.MENSAJE_CORRECTO);
                
                //Si quiere que se procese en ese momento le ejecuto el proceso normal
                if (repuestaFacturaElectronica) {
                    cic = new ClienteFacturaImplComprobante(this,facturaProcesando,false);
                    comprobanteServiceIf.procesarComprobante(comprobanteData, facturaProcesando, session.getUsuario(), cic);
                }
                else
                {
                    //Solo genera el pdf pero no envia al SRI
                    comprobanteServiceIf.procesarComprobanteOffline(comprobanteData, facturaProcesando, session.getUsuario(),cic);
                    DialogoCodefac.mensaje("Correcto","El comprobante esta firmado , no se olvide de enviar al SRI en un periodo maximo de 48 horas", DialogoCodefac.MENSAJE_CORRECTO);
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
        for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) 
        {
            parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
        }
        return parametroMap;
    }
    
    public Map<String, Object> getParametroReporte(DocumentoEnum documento)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("fechaEmision", factura.getFechaFactura().toString());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        //Eliminar solo si ha cargado un dato para editar
        if (estadoFormulario.equals(ESTADO_EDITAR)) {
            if (factura != null) {
                //Eliminar solo si el estado es sin autorizar, o esta en el modo de facturacion normal y esta con estado facturado
                if (factura.getEstado().equals(FacturaEnumEstado.SIN_AUTORIZAR.getEstado()) || 
                        (factura.getTipoFacturacion().equals(TipoFacturacionEnumEstado.NORMAL.getLetra()) && factura.getEstado().equals(FacturaEnumEstado.FACTURADO.getEstado()) )) {
                    
                    boolean respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Esta seguro que desea eliminar la factura? ", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if (respuesta) {
                        try {
                            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
                            servicio.eliminarFactura(factura);
                            DialogoCodefac.mensaje("Exitoso", "La factura se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                            getLblEstadoFactura().setText(FacturaEnumEstado.ELIMINADO.getNombre());
                        } catch (RemoteException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    DialogoCodefac.mensaje("Alerta", "Solo se pueden eliminar facturas no autorizadas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        }
    }

    @Override
    public void imprimir() {
        if (this.factura != null) {
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

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        FacturaBusqueda facturaBusqueda = new FacturaBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
        buscarDialogoModel.setVisible(true);
        Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();
        if (facturaTmp != null) {
            this.factura = facturaTmp;
            ///Cargar los datos de la factura segun el tipo de datos del primer detalle
            TipoDocumentoEnum tipoReferenciaEnum=factura.getDetalles().get(0).getTipoDocumentoEnum();
            if(tipoReferenciaEnum.equals(TipoDocumentoEnum.ACADEMICO))
            {
                try {
                    getCmbTipoDocumento().setSelectedItem(tipoReferenciaEnum);
                    seleccionarPanelTipoDocumento(tipoReferenciaEnum);
                    
                    FacturaAdicional facturaAdicional=buscarCampoAdicionalPorNombre(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre());
                    Long estudianteInscritoId=Long.parseLong(facturaAdicional.getValor());                    
                    EstudianteInscrito estudianteInscrito=ServiceFactory.getFactory().getEstudianteInscritoServiceIf().buscarPorId(estudianteInscritoId);
                    
                    estudiante=estudianteInscrito.getEstudiante();
                    
                    setearValoresAcademicos(estudiante);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else //Si es otro valor que no sea academico
            {
                getCmbTipoDocumento().getSelectedItem().equals(TipoDocumentoEnum.VENTA);
                setearValoresCliente();
            }           
                        
            cargarDatosDetalles();
            setearDetalleFactura();
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
        getTxtValorUnitario().setText("");
        getTxtCantidad().setText("");
        getTxtDescripcion().setText("");
        getTxtDescuento().setText("");
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
        boolean facturacionElectronica=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor.equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra());
        
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

    @Override
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

    private void cargarFormasPagoTabla() {

        //this.modeloTablaFormasPago=new DefaultTableModel(fila,0);
        //this.modeloTablaFormasPago.initModelTablaFormaPago();
        initModelTablaFormaPago();

        List<FormaPago> formasPago = factura.getFormaPagos();
        for (FormaPago formaPago : formasPago) {
            Vector<String> fila = new Vector<>();
            fila.add(formaPago.getSriFormaPago().getNombre());
            fila.add(formaPago.getTotal().toString());
            fila.add(formaPago.getUnidadTiempo());
            fila.add(formaPago.getPlazo() + "");
            this.modeloTablaFormasPago.addRow(fila);
        }

    }

    private void initModelTablaFormaPago() {
        Vector<String> titulo = new Vector<>();
        titulo.add("forma pago");
        titulo.add("Valor");
        titulo.add("Tipo");
        titulo.add("Tiempo");

        this.modeloTablaFormasPago = new DefaultTableModel(titulo, 0);
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
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("Valor");

        this.modeloTablaDatosAdicionales = new DefaultTableModel(titulo, 0);
        getTblDatosAdicionales().setModel(modeloTablaDatosAdicionales);
    }

    /**
     * Metodo que actualiza los valores en la tabla
     */
    private void cargarTablaDatosAdicionales() {
        initModelTablaDatoAdicional();
        
        for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
            Vector dato = new Vector();
            dato.add(datoAdicional.getCampo());
            dato.add(datoAdicional.getValor());
            
            this.modeloTablaDatosAdicionales.addRow(dato);
        }
        
        /*for (Map.Entry<String, String> entry : datosAdicionales.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Vector dato = new Vector();
            dato.add(key);
            dato.add(value);
            this.modeloTablaDatosAdicionales.addRow(dato);
        }*/
    }

    private void cargarDatosDetalles() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("ValorUni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Descuento");
        titulo.add("Valor Total");

        List<FacturaDetalle> detalles = factura.getDetalles();

        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        for (FacturaDetalle detalle : detalles) {
            try {
                
                TipoDocumentoEnum tipoReferenciaEnum=detalle.getTipoDocumentoEnum();
                
                
                Vector<String> fila = new Vector<String>();
                 
                if(tipoReferenciaEnum.equals(TipoDocumentoEnum.ACADEMICO))
                {
                    RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
                    fila.add(rubroEstudiante.getId()+"");
                    //fila.add(rubroEstudiante.getRubroNivel().getValor()+"");
                }
                else
                {
                    if(tipoReferenciaEnum.equals(TipoDocumentoEnum.VENTA)) 
                    {
                        Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
                        fila.add(producto.getCodigoPersonalizado());
                        //fila.add(producto.getValorUnitario().toString());
                    }
                }
                
                fila.add(detalle.getPrecioUnitario().toString());
               
                //Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());

                fila.add(detalle.getCantidad().toString());
                fila.add(detalle.getDescripcion());
                fila.add(detalle.getDescuento().toString());
                fila.add(detalle.getTotal().toString());
                modeloTablaDetallesProductos.addRow(fila);
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getTblDetalleFactura().setModel(this.modeloTablaDetallesProductos);
    }

    private void agregarFechaEmision() {

    }

    private void setearDetalleFactura() {
        getTxtCantidad().setText("");
        getTxtCliente().setText("");
        getTxtDescripcion().setText("");
        getTxtValorUnitario().setText("");
        getTxtDescuento().setText("0");
    }

    public void calcularSubtotalSinImpuestos(List<FacturaDetalle> facturaDetalles) {
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);
        this.factura.setDescuentoSinImpuestos(new BigDecimal(0));

        for (FacturaDetalle facturaDetalle : facturaDetalles) {
            //Solo sumar los subt
            System.out.println(facturaDetalle.getIva());
            if (facturaDetalle.getIva().compareTo(BigDecimal.ZERO) == 0) {
                this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().add(facturaDetalle.getPrecioUnitario().multiply(facturaDetalle.getCantidad())));
                this.factura.setDescuentoSinImpuestos(this.factura.getDescuentoSinImpuestos().add(facturaDetalle.getDescuento()));
            }
        }

        this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
        this.factura.setDescuentoSinImpuestos(this.factura.getDescuentoSinImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
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

    public void calcularSubtotal12(List<FacturaDetalle> facturaDetalles) {
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setDescuentoImpuestos(new BigDecimal(0));

        for (FacturaDetalle facturaDetalle : facturaDetalles) {
            try {
                
                TipoDocumentoEnum tipoReferenciaEnum=facturaDetalle.getTipoDocumentoEnum();
                CatalogoProducto catalogoProducto=null;
                
                if(tipoReferenciaEnum.equals(TipoDocumentoEnum.ACADEMICO))
                {
                    catalogoProducto=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getRubroNivel().getCatalogoProducto();
                }
                else
                {
                    if(tipoReferenciaEnum.equals(TipoDocumentoEnum.VENTA))
                    {
                        catalogoProducto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                    }
                }
                
                //TODO este valor esta quemado toca parametrizar

                if (catalogoProducto.getIva().getTarifa() == 12) { //esta parametro de 12 debe estar parametrizado
                    this.factura.setSubtotalImpuestos(factura.getSubtotalImpuestos().add(facturaDetalle.getPrecioUnitario().multiply(facturaDetalle.getCantidad())));
                    //this.factura.setDescuentoImpuestos(this.factura.getDescuentoImpuestos().add(facturaDetalle.getTotal()));
                    this.factura.setDescuentoImpuestos(this.factura.getDescuentoImpuestos().add(facturaDetalle.getDescuento()));
                    System.out.println(facturaDetalle.getDescuento());
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Escalar valores
        this.factura.setSubtotalImpuestos(factura.getSubtotalImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
        this.factura.setDescuentoImpuestos(this.factura.getDescuentoImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println(this.factura.getDescuentoImpuestos());
    }

    public void calcularSubtotales() {
        this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().subtract(this.factura.getSubtotalImpuestos()));
        this.factura.setDescuentoSinImpuestos(this.factura.getSubtotalImpuestos().add(this.factura.getSubtotalSinImpuestos()).subtract(factura.getSubtotalImpuestos()));
    }

    public void calcularIva12() {
        //this.factura.setIva(this.factura.getDescuentoImpuestos().multiply(obtenerValorIva()));
        //this.factura.setIva(this.factura.getIva().setScale(2, BigDecimal.ROUND_HALF_UP));
        this.factura.setIva(this.factura.getSubtotalImpuestos().subtract(this.factura.getDescuentoImpuestos()).multiply(obtenerValorIva()));
        this.factura.setIva(this.factura.getIva().setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void calcularValorTotal() {
        this.factura.setTotal(
                factura.getSubtotalImpuestos().subtract(this.factura.getDescuentoImpuestos()).
                        add(factura.getSubtotalSinImpuestos().subtract(factura.getDescuentoSinImpuestos())).
                        add(factura.getIva()));
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
        calcularSubtotalSinImpuestos(factura.getDetalles());
        calcularSubtotal12(factura.getDetalles());
        //calcularTotalDescuento(factura.getDetalles()); esta de revisar porque ya calcula en los subtotales
        //calcularSubtotales();
        calcularIva12();
        calcularValorTotal();
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
        if (factura.getFormaPagos().size() == 1) {
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
        factura.addDatosAdicionalCorreo(factura.getCliente().getCorreoElectronico());

        factura.addDatoAdicional(DatosAdicionalesComprobanteEnum.NOMBRE_ESTUDIANTE.getNombre(), estudiante.getNombreCompleto());

        factura.addDatoAdicional(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre(), estudiante.getIdEstudiante() + "");
    }
    
    private void cargarDatosAdicionales()
    {
        //Cargar el correo solo cuando exista 
        if (factura.getCliente().getCorreoElectronico() != null) {
            factura.addDatosAdicionalCorreo(factura.getCliente().getCorreoElectronico());
            //datosAdicionales.put("email", factura.getCliente().getCorreoElectronico());
        }
    }

    private void setearValoresCliente() {
        getTxtCliente().setText(factura.getCliente().getIdentificacion());
        getLblNombreCliente().setText(factura.getCliente().getRazonSocial());
        getLblDireccionCliente().setText(factura.getCliente().getDireccion());
        getLblTelefonoCliente().setText(factura.getCliente().getTelefonoConvencional());

        /**
         * Cargar el estado de la factura
         */
        FacturaEnumEstado estadoEnum = FacturaEnumEstado.getEnum(factura.getEstado());
        
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

    private void setearValoresProducto(BigDecimal valorUnitario,String descripcion) {
        getTxtValorUnitario().setText(valorUnitario+"");
        getTxtDescripcion().setText(descripcion);
        //getTxtValorUnitario().setText(productoSeleccionado.getValorUnitario().toString());
        //getTxtDescripcion().setText(productoSeleccionado.getNombre());
        //Dar foco a la cantidad a ingresar
        getTxtCantidad().requestFocus();
    }

    private void setearValoresDefaultFactura() {
        //factura.setCliente(persona);
        factura.setEmpresaId(0l);
        factura.setEstado(Factura.ESTADO_FACTURADO);
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        factura.setFechaFactura(new Date(getjDateFechaEmision().getDate().getTime()));
        //factura.setIvaSriId(iva);
        factura.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        factura.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        
        //Cuando la facturacion es electronica
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra()))
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
        Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (!respuesta) {
            throw new ExcepcionCodefacLite("Cancelacion usuario");
        }
    }

    public void definirFechaMinFacturacion() {

        getjDateFechaEmision().setDate(new java.util.Date());
        ((JTextField) this.getjDateFechaEmision().getDateEditor()).setEditable(false);

        this.fechaMax = new java.util.Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fechaMax);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        this.fechaMin = calendar.getTime();

    }

    private void cargarValoresAdicionales() {
        getLblSecuencial().setText(factura.getPreimpreso());
        getjDateFechaEmision().setDate(factura.getFechaFactura());
    }

    private boolean validacionParametrosCodefac() {
        System.out.println("Ingreso a la validacion de Paremtros Codefac");
        String mensajeValidacion = "Esta pantalla requiere : \n";
        boolean validado = true;
        
        //Validacion cuando solo sea facturacion manual
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(TipoFacturacionEnumEstado.NORMAL.getLetra()))
        {
            if (session.getEmpresa() == null) 
            {
                mensajeValidacion += " - Información de Empresa \n";
                validado = false;
            }
        }
        else //Validacion cunando es facturacion electronica
        {        
       
            if (session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor().equals("")) {
                mensajeValidacion += " - Archivo Firma\n";
                validado = false;
            }

            if (session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor().equals("")) {
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

    public void verificarSumaFormaPago() {
        BigDecimal totalFormasPago = new BigDecimal("0");
        int res;
        try {
            for (FormaPago fp : factura.getFormaPagos()) {
                totalFormasPago = totalFormasPago.add(fp.getTotal());
            }
            totalFormasPago = totalFormasPago.add(valorTotalFormaDePago);
        } catch (Exception e) {
            System.out.println("Es la primera vez que se utiliza una forma de pago");
        }

        res = factura.getTotal().compareTo(totalFormasPago);
        if (res == -1) {
            DialogoCodefac.mensaje("Advertencia", "La forma de pago sobrepasa el valor a Facturar", DialogoCodefac.MENSAJE_ADVERTENCIA);
            banderaFormaPago = false;
        } else {
            banderaFormaPago = true;
        }
    }

    public void agregarDetallesFactura(FacturaDetalle facturaDetalle) {
        boolean agregar = true;

        //Verifica si manda un detalle existe solo se modifica
        if (facturaDetalle != null) {
            agregar = false;
        } else {
            facturaDetalle = new FacturaDetalle();
        }

        if (!panelPadre.validarPorGrupo("detalles")) {
            return;
        }
        

        if (verificarCamposValidados()) {
            
            //Validacion dependiendo de la logica de cada tipo de documento
            if (!validacionPersonalizadaPorModulos()) {
                return;
            }
            
            
            try {
                //FacturaDetalle facturaDetalle = new FacturaDetalle();
                facturaDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
                facturaDetalle.setDescripcion(getTxtDescripcion().getText());
                BigDecimal valorTotalUnitario = new BigDecimal(getTxtValorUnitario().getText());
                facturaDetalle.setPrecioUnitario(valorTotalUnitario.setScale(2, BigDecimal.ROUND_HALF_UP));
                
                //Variable del producto para verificar otros datos como el iva
                CatalogoProducto catalogoProducto=null;
                //Seleccionar la referencia dependiendo del tipo de documento
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                if(tipoDocumentoEnum.equals(TipoDocumentoEnum.ACADEMICO))
                {
                    facturaDetalle.setReferenciaId(rubroSeleccionado.getId());
                    facturaDetalle.setTipoDocumento(TipoDocumentoEnum.ACADEMICO.getCodigo());
                    catalogoProducto=rubroSeleccionado.getRubroNivel().getCatalogoProducto();
                }
                else
                {
                    facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
                    facturaDetalle.setTipoDocumento(TipoDocumentoEnum.VENTA.getCodigo());
                    catalogoProducto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                }
                
                
                
                facturaDetalle.setValorIce(BigDecimal.ZERO);
                
                BigDecimal descuento;
                if (!getCheckPorcentaje().isSelected()) {
                    if (!getTxtDescuento().getText().equals("")) {
                        descuento = new BigDecimal(getTxtDescuento().getText());
                    } else {
                        descuento = BigDecimal.ZERO;
                    }
                    
                    facturaDetalle.setDescuento(descuento);
                } else {
                    if (!getTxtDescuento().getText().equals("")) {
                        BigDecimal porcentajeDescuento = new BigDecimal(getTxtDescuento().getText());
                        porcentajeDescuento = porcentajeDescuento.divide(new BigDecimal(100));
                        BigDecimal total = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario());
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
                    setearDetalleFactura();
                    cargarTotales();
                    banderaAgregar = false;
                } else {
                    DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    setearDetalleFactura();
                    banderaAgregar = false;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }

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
                if(tipoDocumentoEnum.equals(TipoDocumentoEnum.ACADEMICO))
                {
                    RubroEstudiante rubroSeleccionado=(RubroEstudiante) referencia;
                    
                    facturaDetalle.setReferenciaId(rubroSeleccionado.getId());
                    facturaDetalle.setTipoDocumento(TipoDocumentoEnum.ACADEMICO.getCodigo());
                    catalogoProducto=rubroSeleccionado.getRubroNivel().getCatalogoProducto();
                }
                else
                {
                    Producto productoSeleccionado=(Producto) referencia;
                    
                    facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
                    facturaDetalle.setTipoDocumento(TipoDocumentoEnum.VENTA.getCodigo());
                    catalogoProducto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
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
                    setearDetalleFactura();
                    cargarTotales();
                    banderaAgregar = false;
                } else {
                    DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    setearDetalleFactura();
                    banderaAgregar = false;
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
            setearDetalleFactura();
            cargarTotales();
            cargarValoresAdicionales();
            //cargarFormasPagoTabla();
        }
    }

    public void iniciarValoresIniciales() {
        List<DocumentoEnum> tiposDocumento=null;
        //cuando la factura es electronica
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor.equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra()))
        {
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosElectronicos(ModuloEnum.VENTAS);
        }
        else //Cuando la factura es fisica
        {
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosFisico(ModuloEnum.VENTAS);
        }
        
        getCmbDocumento().removeAllItems();
        for (DocumentoEnum tipoDocumentoEnum : tiposDocumento) {
            getCmbDocumento().addItem(tipoDocumentoEnum);
        }
        
        
        //Agregar los tipos de documentos disponibles

        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloEnum.VENTAS);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
    }

    private void addListenerCombos() {
        getCmbTipoDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();                    
                seleccionarPanelTipoDocumento(tipoDocumentoEnum);                
            }
        });
        
        getCmbRepresentante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(factura!=null)
                {
                    //Si cambie el combo de representante tambien los seteo en la factura
                    Persona persona=(Persona) getCmbRepresentante().getSelectedItem();
                    factura.setCliente(persona);
                    //cargarFormaPago();
                    
                    //Verificar si ya esta cargado una forma de pago del representante anterior y si cambia de representante
                    //Seleccionar la forma de pago de nuevo representante seleccionado                    
                    if(factura.getFormaPagos()!=null && factura.getFormaPagos().size()==1)
                    {
                        FormaPago formaPago=factura.getFormaPagos().get(0);
                        formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                        cargarFormasPagoTabla();
                    }
                    
                }
            }
        });
    }
    
    private void seleccionarPanelTipoDocumento(TipoDocumentoEnum tipoDocumentoEnum)
    {
        if (tipoDocumentoEnum != null) {
            if (tipoDocumentoEnum.equals(tipoDocumentoEnum.ACADEMICO)) {
                activarTabDatos(1);
            } else {
                //Todo: Seguir armando el tab de los otros datos
                activarTabDatos(0);
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
        
        if(tipoDocEnum.equals(TipoDocumentoEnum.ACADEMICO))
        {
            BigDecimal cantidad=new BigDecimal(getTxtCantidad().getText());
            BigDecimal valorUnitario=new BigDecimal(getTxtValorUnitario().getText());
            //TODO: Analizar para el caso que tenga descuento
            
            if(rubroSeleccionado.getSaldo().compareTo(cantidad.multiply(valorUnitario))==-1)
            {
                DialogoCodefac.mensaje("Validación","El Total no puede exceder del valor "+rubroSeleccionado.getSaldo()+" del rubro",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return false;
            }
        }
        else
        {
            //PARA LOS DEMAS CASOS
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

        }
        else
        {
            //TODO: implementar para el otro caso cuando sea de otros modulos
        
        }
        
        
        
    }

}
