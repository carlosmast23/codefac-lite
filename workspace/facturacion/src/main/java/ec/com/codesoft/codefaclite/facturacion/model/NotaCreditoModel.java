/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.NotaCreditoBusqueda;
import ec.com.codesoft.codefaclite.facturacion.other.NotaCreditoElectronico;
import ec.com.codesoft.codefaclite.facturacion.panel.NotaCreditoPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.NotaCreditoService;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class NotaCreditoModel extends NotaCreditoPanel {

    private DefaultTableModel modeloTablaDetalle = new DefaultTableModel();
    private NotaCredito notaCredito;

    public NotaCreditoModel() {
        addListenerButtons();
    }
    
    private void setearValoresNotaCredito()
    {
         notaCredito.setEmpresaId(0l);
        notaCredito.setEstado(Factura.ESTADO_FACTURADO);
        notaCredito.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        notaCredito.setRazonModificado(getTxtMotivoAnulacion().getText());
        notaCredito.setFechaNotaCredito(new Date(getjDateFechaEmision().getDate().getTime()));
        
        notaCredito.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        notaCredito.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        notaCredito.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).valor));
        notaCredito.setSubtotalCero(BigDecimal.ZERO);
        
        //Verificacion para cambiar el estado de la factura
        if(notaCredito.getTotal().compareTo(notaCredito.getFactura().getTotal())<0)
        {
            notaCredito.getFactura().setEstado(FacturaEnumEstado.ANULADO_PARCIAL.getEstado());
        }
        else
        {
            notaCredito.getFactura().setEstado(FacturaEnumEstado.ANULADO_TOTAL.getEstado());
        }
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        NotaCredito notaCreditoGrabada;
        NotaCreditoService servicio=new NotaCreditoService();
        setearValoresNotaCredito();
        servicio.grabar(notaCredito);
        notaCreditoGrabada=notaCredito;//graba una referencia con ambiento del metodo para los listener
        
        NotaCreditoElectronico notaCreditoElectronico=new NotaCreditoElectronico(session, panelPadre);
        notaCreditoElectronico.setNotaCredito(notaCredito);
        Map<String,String> datosAdicionales=new HashMap<String,String>();
        datosAdicionales.put("correo",notaCredito.getCliente().getCorreoElectronico());
        notaCreditoElectronico.setMapInfoAdicional(datosAdicionales);
        notaCreditoElectronico.setCorreosAdicionales(new ArrayList<String>());
        
        notaCreditoElectronico.getServicio().addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
           
            private MonitorComprobanteData monitorData;
            
            @Override
            public void termino() 
            {
                
                monitorData.getBarraProgreso().setForeground(Color.GREEN);
                monitorData.getBtnAbrir().setEnabled(true);
                monitorData.getBtnCerrar().setEnabled(true);
                monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //String path = facturaElectronica.getServicio().getPathRide();
                        notaCreditoElectronico.cargarDatosRecursos();
                        JasperPrint print = notaCreditoElectronico.getServicio().getPrintJasper();
                        panelPadre.crearReportePantalla(print, notaCredito.getPreimpreso());
                    }
                });
                
                /**
                 * Seteando datos adicionales de la factura
                 */
                notaCreditoGrabada.setClaveAcceso(notaCreditoElectronico.getServicio().getClaveAcceso());
                notaCreditoGrabada.setEstado(Factura.ESTADO_FACTURADO);
                servicio.editar(notaCreditoGrabada);
                

            }

            @Override
            public void procesando(int etapa,ClaveAcceso clave) {
                if(etapa==ComprobanteElectronicoService.ETAPA_GENERAR)
                    monitorData.getBarraProgreso().setValue(20);
                
                if(etapa==ComprobanteElectronicoService.ETAPA_PRE_VALIDAR)
                    monitorData.getBarraProgreso().setValue(30);
                
                if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
                    monitorData.getBarraProgreso().setValue(50);
                }
                
                if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
                    monitorData.getBarraProgreso().setValue(70);
                }
                
                if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
                    monitorData.getBarraProgreso().setValue(90);
                }
                
                if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
                    monitorData.getBarraProgreso().setValue(95);
                }
                
                if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
                    monitorData.getBarraProgreso().setValue(100);
                }
            }

            @Override
            public void iniciado(ComprobanteElectronico comprobante) {
                monitorData=MonitorComprobanteModel.getInstance().agregarComprobante();
                monitorData.getLblPreimpreso().setText(notaCredito.getPreimpreso()+" ");
                monitorData.getBtnAbrir().setEnabled(false);
                monitorData.getBtnReporte().setEnabled(false);
                monitorData.getBtnCerrar().setEnabled(false);
                monitorData.getBarraProgreso().setString(comprobante.getInformacionTributaria().getPreimpreso());
                monitorData.getBarraProgreso().setStringPainted(true);
                MonitorComprobanteModel.getInstance().mostrar();
                
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                monitorData.getBtnReporte().setEnabled(true);
                monitorData.getBtnCerrar().setEnabled(true);
                monitorData.getBtnReporte().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,"Etapa: "+ cee.getEtapa()+"\n"+cee.getMessage());
                         monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JasperPrint print = notaCreditoElectronico.getServicio().getPrintJasper();
                                panelPadre.crearReportePantalla(print, notaCredito.getPreimpreso());
                            }
                        });
                    }
                });
                
                if(cee.getTipoError().equals(ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE))
                {
                    monitorData.getBtnAbrir().setEnabled(true);
                    monitorData.getBarraProgreso().setForeground(Color.YELLOW);
                }
                else
                {
                    monitorData.getBarraProgreso().setForeground(Color.ORANGE);
                }
                
            }
        });
        
        notaCreditoElectronico.procesarComprobante();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        NotaCreditoBusqueda notaCreditoBusqueda = new NotaCreditoBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(notaCreditoBusqueda);
        buscarDialogoModel.setVisible(true);
        NotaCredito notaCredito = (NotaCredito) buscarDialogoModel.getResultado();
        if(notaCredito != null)
        {
            this.notaCredito = notaCredito;
            mostrarDatosNotaCredito();
            setearDatosEmpresa();

        }
        
        
        
    }

    @Override
    public void limpiar() {
        /**
         * Cargar Datos de la empresa
         */
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getRazonSocial());

        notaCredito = new NotaCredito();
        crearDetalleTabla();
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void addListenerButtons() {
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusqueda facturaBusqueda = new FacturaBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);
                Factura factura = (Factura) buscarDialogoModel.getResultado();
                if (factura != null) {
                    notaCredito.setFactura(factura);
                    cargarDatosNotaCredito();
                }
            }
        });
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
 
        
        /**
         * CargarDetallesNotaCredito
         */
        List<FacturaDetalle> detallesFactura = notaCredito.getFactura().getDetalles();
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            NotaCreditoDetalle notaDetalle = new NotaCreditoDetalle();
            notaDetalle.setCantidad(facturaDetalle.getCantidad());
            notaDetalle.setDescripcion(facturaDetalle.getDescripcion());
            notaDetalle.setDescuento(facturaDetalle.getDescuento());
            notaDetalle.setIva(facturaDetalle.getIva());
            notaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            notaDetalle.setProducto(facturaDetalle.getProducto());
            notaDetalle.setTotal(facturaDetalle.getTotal());
            notaDetalle.setValorIce(facturaDetalle.getValorIce());
            notaCredito.addDetalle(notaDetalle);
        }
        
        actualizarDatosTablaDetalle();
        mostrarDatosNotaCredito();

    }

    private void mostrarDatosNotaCredito() {
        getTxtReferenciaFactura().setText(notaCredito.getFactura().getPreimpreso());
        getLblNombreCliente().setText(notaCredito.getFactura().getCliente().getRazonSocial());
        getLblDireccionCliente().setText(notaCredito.getFactura().getCliente().getDireccion());
        getLblTelefonoCliente().setText(notaCredito.getFactura().getCliente().getTelefonoCelular());

        /**
         * Cargar Los calculos de los totales
         */
        getTxtValorTotal().setText(notaCredito.getTotal() + "");
        getLblIva12().setText(notaCredito.getSubtotalDoce() + "");
        getLblSubtotal0().setText(notaCredito.getSubtotalCero() + "");
        getLblSubtotal12().setText(notaCredito.getSubtotalDoce() + "");
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

    private void actualizarDatosTablaDetalle() {
        crearDetalleTabla();
        List<NotaCreditoDetalle> detalles = notaCredito.getDetalles();
        for (NotaCreditoDetalle detalle : detalles) {
            Vector<String> fila = new Vector<String>();
            fila.add(detalle.getProducto().getCodigoPrincipal());
            fila.add(detalle.getProducto().getValorUnitario() + "");
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
        if(session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor().equals(""))
        { 
            mensajeValidacion+=" - Archivo Firma\n";
            validado= false;
        }
        
        if(session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor().equals(""))
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
        
        if(!validado)
        {
            //mensajeValidacion=mensajeValidacion.substring(0,mensajeValidacion.length()-2);
            DialogoCodefac.mensaje("Acceso no permitido", mensajeValidacion+"\nPofavor complete estos datos en configuración para usar esta pantalla",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        
        
        
        
        return validado;
        
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

}
