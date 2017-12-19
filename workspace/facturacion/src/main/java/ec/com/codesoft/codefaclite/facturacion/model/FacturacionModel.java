/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.mail.CorreoCodefac;
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
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.other.FacturacionElectronica;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
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
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel {

    private Persona persona;
    private Factura factura;
    private DefaultTableModel modeloTablaFormasPago;
    private DefaultTableModel modeloTablaDetallesProductos;
    private DefaultTableModel modeloTablaDatosAdicionales;
    private Producto productoSeleccionado;
    private int fila;
    private boolean bandera;
    private boolean banderaAgregar;
    private BigDecimal subtotalSinImpuestos;
    private BigDecimal subtotal12;
    private BigDecimal subtotal0;
    private BigDecimal iva;
    private BigDecimal valorTotal;
    private BigDecimal descuento;
    //Datos informativos del descuento
    private BigDecimal subTotalDescuentoConImpuesto;
    private BigDecimal subTotalDescuentoSinImpuesto;
    //Da
    private BigDecimal subtotal12Descuento;
    private BigDecimal subtotal0Descuento;
    private BigDecimal subtotalSinImpuestosDescuento;

    /**
     * Mapa de datos adicionales que se almacenan temporalmente y sirven para la
     * facturacion electronica como por ejemplo el correo
     */
    private Map<String, String> datosAdicionales;

    /**
     * Objeto que permite interactuar con los servicio de la facturacion
     * Electronica
     */
    private FacturacionElectronica facturaElectronica;

    public FacturacionModel() {
        addListenerButtons();
        initComponenesGraficos();
        initModelTablaFormaPago();
        initModelTablaDetalleFactura();
        initModelTablaDatoAdicional();
        setearVariablesIniciales();

    }
    
    private void setearVariablesIniciales()
    {
        agregarFechaEmision();
        this.subtotalSinImpuestos = new BigDecimal(0);
        this.subtotal12 = new BigDecimal(0);
        this.subtotal0 = new BigDecimal(0);
        this.iva = new BigDecimal(0);
        this.valorTotal = new BigDecimal(0);
        this.subTotalDescuentoConImpuesto = new BigDecimal(0);
        this.subTotalDescuentoSinImpuesto = new BigDecimal(0);
        this.bandera = false;
        this.banderaAgregar = true;
        subtotal12Descuento = new BigDecimal(0);
        calcularIva12();
        datosAdicionales = new HashMap<String,String>();    
        
    }

    private void addListenerButtons() {

        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                persona = (Persona) buscarDialogoModel.getResultado();
                if (persona != null) {
                    setearValoresCliente();
                };
            }
        });

        getBtnAgregarFormaPago().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormaPagoDialogModel dialog = new FormaPagoDialogModel(null, true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                FormaPago formaPago = dialog.getFormaPago();
                agregarFormaPagoTabla(formaPago);
            }
        });

        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogoModel.getResultado();
                if (productoSeleccionado == null) {
                    return;
                    //throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
                }
                setearValoresProducto();
                banderaAgregar = true;
            }
        });

        getTblDetalleFactura().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fila = getTblDetalleFactura().getSelectedRow();
                System.out.println(fila);
                bandera = true;
            }
        });

        getBtnAgregarDetalleFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (banderaAgregar) {
                    FacturaDetalle facturaDetalle = new FacturaDetalle();
                    facturaDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
                    facturaDetalle.setDescripcion(getTxtDescripcion().getText());
                    facturaDetalle.setPrecioUnitario(new BigDecimal(getTxtValorUnitario().getText()));
                    facturaDetalle.setProducto(productoSeleccionado);
                    System.out.println(productoSeleccionado);
                    BigDecimal setTotal = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario());
                    facturaDetalle.setTotal(setTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
                    facturaDetalle.setValorIce(BigDecimal.ZERO);
                    BigDecimal descuento;
                    if(!getCheckPorcentaje().isSelected())
                    {
                        if(!getTxtDescuento().getText().equals(""))
                            descuento = new BigDecimal(getTxtDescuento().getText());
                        else
                            descuento = BigDecimal.ZERO;
                                    
                        
                        facturaDetalle.setDescuento(descuento);
                    }
                    else{
                            BigDecimal porcentajeDescuento = new BigDecimal(getTxtDescuento().getText());
                            porcentajeDescuento = porcentajeDescuento.divide(new BigDecimal(100));
                            descuento = facturaDetalle.getTotal().multiply(porcentajeDescuento);
                            facturaDetalle.setDescuento(descuento.setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                    factura.addDetalle(facturaDetalle);
                    cargarDatosDetalles();
                    setearDetalleFactura();
                    cargarTotales();
                    banderaAgregar = false;

                    /**
                     * Revisar este calculo del iva para no calcular 2 veces al
                     * mostrar
                     */

                                       
                    facturaDetalle.setIva(iva.setScale(2, BigDecimal.ROUND_HALF_UP));

                }
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
                }
            }
        });

        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bandera) {
                    BigDecimal valorUnitario = new BigDecimal(String.valueOf(modeloTablaDetallesProductos.getValueAt(getTblDetalleFactura().getSelectedRow(), 1)));
                    BigDecimal cantidad = new BigDecimal(String.valueOf(modeloTablaDetallesProductos.getValueAt(getTblDetalleFactura().getSelectedRow(), 2)));
                    factura.getDetalles().get(fila).setPrecioUnitario(valorUnitario);
                    factura.getDetalles().get(fila).setCantidad(cantidad);
                    factura.getDetalles().get(fila).setTotal(valorUnitario.multiply(cantidad));
                    cargarDatosDetalles();
                    cargarTotales();
                    bandera = false;
                }
            }

        });

        getBtnAgregarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                    @Override
                    public void updateInterface(Persona entity) {
                        persona = entity;
                        if (persona != null) {
                            setearValoresCliente();
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
                            setearValoresProducto();
                            banderaAgregar = true;
                        }
                    }
                };

                panelPadre.crearDialogoCodefac(observer, DialogInterfacePanel.PRODUCTO_PANEL, false);

            }
        });

    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
        Boolean respuesta=DialogoCodefac.dialogoPregunta("Alerta","Estas seguro que desea facturar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
        if(!respuesta)
        {
            throw  new ExcepcionCodefacLite("Cancelacion usuario");
        }
        
        Factura facturaProcesando; //referencia que va a tener la factura procesada para que los listener no pierdan la referencia a la variable del metodo. 

        FacturacionService servicio = new FacturacionService();
        setearValoresDefaultFactura();
        servicio.grabar(factura);
        facturaProcesando = factura;

        DialogoCodefac.mensaje("Correcto", "La factura se grabo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        //Despues de implemetar todo el metodo de grabar
        FacturacionElectronica facturaElectronica = new FacturacionElectronica(factura, session, this.panelPadre);
        facturaElectronica.setFactura(factura);
        facturaElectronica.setMapInfoAdicional(datosAdicionales);
        /*
        ListenerComprobanteElectronico listener=new ListenerComprobanteElectronico() {
            @Override
            public void termino() {
                MonitorComprobanteModel.getInstance().agregarComprobante(new MonitorComprobanteInterface() {
                    @Override
                    public void eventBtnAbrir() {
                        //String path = facturaElectronica.getServicio().getPathRide();
                        //JasperPrint print = facturaElectronica.getServicio().getPrintJasper();
                        //panelPadre.crearReportePantalla(print, factura.getPreimpreso());
                    }

                    @Override
                    public void eventBtnInforme() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            }
        };*/

        ComprobanteElectronicoService servicioElectronico = facturaElectronica.getServicio();

        servicioElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {

            private MonitorComprobanteData monitorData;

            @Override
            public void termino() {
                monitorData.getBarraProgreso().setForeground(Color.GREEN);
                monitorData.getBtnAbrir().setEnabled(true);
                monitorData.getBtnCerrar().setEnabled(true);
                monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //String path = facturaElectronica.getServicio().getPathRide();
                        JasperPrint print = facturaElectronica.getServicio().getPrintJasper();
                        panelPadre.crearReportePantalla(print, factura.getPreimpreso());
                    }
                });

                /**
                 * Seteando datos adicionales de la factura
                 */
                facturaProcesando.setClaveAcceso(facturaElectronica.getServicio().getClaveAcceso());
                facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                servicio.editar(facturaProcesando);

            }

            @Override
            public void procesando(int etapa) {
                if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
                    monitorData.getBarraProgreso().setValue(20);
                }

                if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
                    monitorData.getBarraProgreso().setValue(30);
                }

                if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
                    monitorData.getBarraProgreso().setValue(50);                   
                }

                if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
                    monitorData.getBarraProgreso().setValue(70);
                }

                if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
                    monitorData.getBarraProgreso().setValue(90);
                    facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                }

                if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
                    monitorData.getBarraProgreso().setValue(95);
                    facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                }

                if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
                    monitorData.getBarraProgreso().setValue(100);
                    facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                }
            }

            @Override
            public void iniciado(ComprobanteElectronico comprobante) {
                monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
                monitorData.getLblPreimpreso().setText(factura.getPreimpreso() + " ");
                monitorData.getBtnAbrir().setEnabled(false);
                monitorData.getBtnReporte().setEnabled(false);
                monitorData.getBtnCerrar().setEnabled(false);
                monitorData.getBarraProgreso().setString(comprobante.getInformacionTributaria().getPreimpreso());
                monitorData.getBarraProgreso().setStringPainted(true);
                MonitorComprobanteModel.getInstance().mostrar();

                facturaProcesando.setEstado(FacturaEnumEstado.SIN_AUTORIZAR.getEstado());

            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                monitorData.getBtnReporte().setEnabled(true);
                monitorData.getBtnCerrar().setEnabled(true);
                monitorData.getBtnReporte().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Etapa: " + cee.getEtapa() + "\n" + cee.getMessage());
                        monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JasperPrint print = facturaElectronica.getServicio().getPrintJasper();
                                panelPadre.crearReportePantalla(print, factura.getPreimpreso());
                            }
                        });
                    }
                });

                if (cee.getTipoError().equals(ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE)) {
                    monitorData.getBtnAbrir().setEnabled(true);
                    monitorData.getBarraProgreso().setForeground(Color.YELLOW);
                } else {
                    monitorData.getBarraProgreso().setForeground(Color.ORANGE);
                }

                
                servicio.editar(facturaProcesando);

            }
        });

        facturaElectronica.procesarComprobante();//listo se encarga de procesar el comprobante
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        if (this.factura != null) {
            String claveAceeso = this.factura.getClaveAcceso();
            facturaElectronica.setClaveAcceso(claveAceeso);
            facturaElectronica.setFactura(factura);
            //facturaElectronica.procesarComprobanteEtapa(ComprobanteElectronicoService.ETAPA_RIDE,false);
            //facturaElectronica.procesarComprobante();
            //JasperPrint print = facturaElectronica.getServicio().getPrintJasper();
            panelPadre.crearReportePantalla(facturaElectronica.obtenerRide(), factura.getPreimpreso());
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
        Factura factura = (Factura) buscarDialogoModel.getResultado();
        if (factura != null) {
            this.factura = factura;
        }
    }

    @Override
    public void limpiar() {
        this.factura = new Factura();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());

        //Setear los valores de la empresa 
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getRazonSocial());
        FacturacionService servicio = new FacturacionService();
        getLblSecuencial().setText(servicio.getPreimpresoSiguiente());

        datosAdicionales = new HashMap<String, String>();
        facturaElectronica = new FacturacionElectronica(session, this.panelPadre);
        
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
       
       //Limpiar las variables de la facturacion
       setearVariablesIniciales();
       
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void agregarFormaPagoTabla(FormaPago formaPago) {
        Vector<String> fila = new Vector<>();
        fila.add(formaPago.getSriFormaPago().getNombre());
        fila.add(formaPago.getTotal().toString());
        fila.add(formaPago.getUnidadTiempo());
        fila.add(formaPago.getPlazo() + "");
        System.out.println(formaPago);
        this.modeloTablaFormasPago.addRow(fila);
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
        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo,0);
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
    private void cargarDatosAdicionales() {
        initModelTablaDatoAdicional();
        for (Map.Entry<String, String> entry : datosAdicionales.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Vector dato = new Vector();
            dato.add(key);
            dato.add(value);
            this.modeloTablaDatosAdicionales.addRow(dato);
        }
    }

    private void cargarDatosDetalles() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("ValorUni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Descuento");
        titulo.add("Valor Total");        
       
        List<FacturaDetalle> detalles= factura.getDetalles();

        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        for (FacturaDetalle detalle : detalles) {
            Vector<String> fila = new Vector<String>();
            fila.add(detalle.getProducto().getCodigoPrincipal());
            fila.add(detalle.getProducto().getValorUnitario().toString());
            fila.add(detalle.getCantidad().toString());
            fila.add(detalle.getDescripcion());
            fila.add(detalle.getDescuento().toString());
            fila.add(detalle.getTotal().toString());
            modeloTablaDetallesProductos.addRow(fila);
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
        this.subtotalSinImpuestos = new BigDecimal(0);
        this.subtotalSinImpuestosDescuento = new BigDecimal(0);
        facturaDetalles.forEach((facturaDetalle) -> 
        {   
                this.subtotalSinImpuestos = this.subtotalSinImpuestos.add(facturaDetalle.getTotal());
                this.subtotalSinImpuestosDescuento = this.subtotalSinImpuestos;
                this.subtotalSinImpuestosDescuento = this.subtotalSinImpuestosDescuento.subtract(facturaDetalle.getDescuento());

        });
        this.subtotalSinImpuestos = this.subtotalSinImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.subtotalSinImpuestosDescuento = this.subtotalSinImpuestosDescuento.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public void calcularTotalDescuento(List<FacturaDetalle> facturaDetalles)
    {
        this.descuento = new BigDecimal(0);
        this.subTotalDescuentoConImpuesto = new BigDecimal(0);
        this.subTotalDescuentoSinImpuesto = new BigDecimal(0);       
        //this.subtotalSinImpuestosDescuento = new BigDecimal(0);
        facturaDetalles.forEach((facturaDetalle) ->
        {           
                    this.descuento = this.descuento.add(facturaDetalle.getDescuento());
                    if(facturaDetalle.getProducto().getIva().getTarifa() == 12)
                    {
                        this.subTotalDescuentoConImpuesto = this.subTotalDescuentoConImpuesto.add(facturaDetalle.getDescuento());
                    }    
                    else
                    {
                        this.subTotalDescuentoSinImpuesto = this.subTotalDescuentoSinImpuesto.add(facturaDetalle.getDescuento());
                    }    
        });
        this.descuento = this.descuento.setScale(2,BigDecimal.ROUND_HALF_UP);
        this.subTotalDescuentoConImpuesto = this.subTotalDescuentoConImpuesto.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.subTotalDescuentoSinImpuesto = this.subTotalDescuentoSinImpuesto.setScale(2, BigDecimal.ROUND_HALF_UP);        
    }

    public void calcularSubtotal12(List<FacturaDetalle> facturaDetalles) {
        this.subtotal12 = new BigDecimal(0);
        this.subtotal12Descuento = new BigDecimal(0);
        facturaDetalles.forEach((facturaDetalle) -> {
            if(facturaDetalle.getProducto().getIva().getTarifa() == 12)
            {
                this.subtotal12 = this.subtotal12.add(facturaDetalle.getTotal());
                this.subtotal12Descuento = this.subtotal12Descuento.add(facturaDetalle.getTotal());
                this.subtotal12Descuento = this.subtotal12Descuento.subtract(facturaDetalle.getDescuento());
            }
        });
        this.subtotal12 = this.subtotal12.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.subtotal12Descuento = this.subtotal12Descuento.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void calcularSubtotal0() {
        this.subtotal0 = this.subtotalSinImpuestos.subtract(this.subtotal12);
        this.subtotal0Descuento = this.subtotalSinImpuestosDescuento.subtract(this.subtotal12Descuento);
    }
    
    public void calcularIva12()
    {
        //this.iva = this.subtotal12.multiply(obtenerValorIva());
        this.iva = this.subtotal12Descuento.multiply(obtenerValorIva());
        //System.out.println("Valor de iva: "+ this.iva);
        //System.out.println("Valor de subtotal12DEscuento"+ this.subtotal12Descuento);
        this.iva = iva.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public void calcularValorTotal()
    {
        //this.valorTotal = this.subtotalSinImpuestos.add(this.iva);
        this.valorTotal = this.subtotalSinImpuestosDescuento.add(this.iva);
    }
    
    public BigDecimal obtenerValorIva() 
    {
        Map<String, Object> map = new HashMap<>();
        ImpuestoDetalleService impuestoDetalleService = new ImpuestoDetalleService();
        map.put("tarifa", 12);
        List<ImpuestoDetalle> listaImpuestoDetalles = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
        listaImpuestoDetalles.forEach((iD) -> {
            BigDecimal iva = iD.getPorcentaje();
        });
        return new BigDecimal(0.120);
    }

    public void cargarTotales() {
        calcularSubtotalSinImpuestos(factura.getDetalles());
        calcularSubtotal12(factura.getDetalles());
        calcularTotalDescuento(factura.getDetalles());
        calcularSubtotal0();
        calcularIva12();
        calcularValorTotal();
        getLblSubtotalSinImpuesto().setText(""+this.subtotalSinImpuestos);
        getLblSubtotal12().setText(""+this.subtotal12);
        getLblSubtotal0().setText(""+this.subtotal0);
        getLblIva12().setText(""+this.iva);
        getTxtValorTotal().setText( "" + this.valorTotal);
        getLblSubTotalDescuentoConImpuesto().setText("" + this.subTotalDescuentoConImpuesto);
        getLblSubTotalDescuentoSinImpuesto().setText("" + this.subTotalDescuentoSinImpuesto);
        getLblTotalDescuento().setText("" + this.descuento);            
    }

    private void setearValoresCliente() {
        getTxtCliente().setText(persona.getIdentificacion());
        getLblNombreCliente().setText(persona.getRazonSocial());
        getLblDireccionCliente().setText(persona.getDireccion());
        getLblTelefonoCliente().setText(persona.getTelefonoConvencional());
        datosAdicionales.put("email", persona.getCorreoElectronico());
        factura.setCliente(persona);
        //Actualiza la tabla de los datos adicionales
        cargarDatosAdicionales();
    }

    private void setearValoresProducto() {
        getTxtValorUnitario().setText(productoSeleccionado.getValorUnitario().toString());
        getTxtDescripcion().setText(productoSeleccionado.getNombre());
        //Dar foco a la cantidad a ingresar
        getTxtCantidad().requestFocus();
    }

    private void setearValoresDefaultFactura() {
        factura.setCliente(persona);
        factura.setEmpresaId(0l);
        factura.setEstado(Factura.ESTADO_FACTURADO);
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        factura.setFechaFactura(new Date(getjDateFechaEmision().getDate().getTime()));
        //factura.setIvaSriId(iva);
        factura.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        factura.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        factura.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA).valor)+1);
        factura.setSubtotalCero(BigDecimal.ZERO);

        /**
         * Seteado los valores temporales pero toca cambiar esta parte y setear
         * los valores directamente en la factura
         */
        factura.setTotal(new BigDecimal(getTxtValorTotal().getText()));
        factura.setSubtotalCero(new BigDecimal(getLblSubtotal0().getText()));
        factura.setSubtotalDoce(new BigDecimal(getLblSubtotal12().getText()));
        factura.setValorIvaDoce(new BigDecimal(getLblIva12().getText()));

    }

    private void initComponenesGraficos() {
        String path=RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/mas-ico.png");
        getBtnAgregarDetalleFactura().setIcon(new ImageIcon(path));
        getBtnAgregarDetalleFactura().setText("");
        getBtnAgregarDetalleFactura().setToolTipText("Agregar detalle factura");
        
        getBtnEditarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/edit_icon.png")));
        getBtnEditarDetalle().setText("");
        getBtnEditarDetalle().setToolTipText("Editar detalle factura");
        
        getBtnQuitarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/cerrar-ico.png")));
        getBtnQuitarDetalle().setText("");
        getBtnQuitarDetalle().setToolTipText("Eliminar detalle factura");
        
        getBtnAgregarCliente().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/usuario.png")));
        getBtnAgregarCliente().setText("");
        getBtnAgregarCliente().setToolTipText("Crear nuevo cliente");
        
        getBtnAgregarProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/list.png")));
        getBtnAgregarProducto().setText("");
        getBtnAgregarProducto().setToolTipText("Agregar producto a la factura");
        
        getBtnCrearProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/add2.png")));
        getBtnCrearProducto().setText("");
        getBtnCrearProducto().setToolTipText("Crear nuevo producto");
        
        
        
        
    }

    @Override
    public void iniciar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        Boolean respuesta=DialogoCodefac.dialogoPregunta("Alerta","Si desea continuar se perderan los datos sin guardar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
        if(!respuesta)
        {
            throw  new ExcepcionCodefacLite("Cancelacion usuario");
        }
    }
    
}
