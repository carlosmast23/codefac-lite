/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import static ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface.ESTADO_EDITAR;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class ComponenteDatosComprobanteElectronicosPanel extends javax.swing.JPanel {

    private ComponenteDatosComprobanteElectronicosInterface comprobante;
    /**
     * Creates new form ComponenteDatosComprobanteElectronicosPanel
     */
    public ComponenteDatosComprobanteElectronicosPanel() {
        initComponents();
        listenerBotones();
        listenerLabels();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnObtenerClaveAcceso = new javax.swing.JButton();
        btnAbrirXml = new javax.swing.JButton();
        btnReenviarCorreo = new javax.swing.JButton();
        btnAutorizarDocumento = new javax.swing.JButton();
        btnCambiarEstado = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblConsultaSri = new javax.swing.JLabel();
        lblAnulacionSri = new javax.swing.JLabel();

        setLayout(new org.jdesktop.swingx.VerticalLayout());

        btnObtenerClaveAcceso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/datos.png"))); // NOI18N
        btnObtenerClaveAcceso.setToolTipText("Imprimir Clave de Acceso");
        add(btnObtenerClaveAcceso);

        btnAbrirXml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/xml.png"))); // NOI18N
        btnAbrirXml.setToolTipText("obtener xml");
        add(btnAbrirXml);

        btnReenviarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/email.png"))); // NOI18N
        btnReenviarCorreo.setToolTipText("Reenviar Correo");
        add(btnReenviarCorreo);

        btnAutorizarDocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/visto.png"))); // NOI18N
        btnAutorizarDocumento.setToolTipText("Cambiar estado autorizar comprobante");
        add(btnAutorizarDocumento);

        btnCambiarEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/editar2.gif"))); // NOI18N
        btnCambiarEstado.setToolTipText("Cambiar estado comprobante");
        add(btnCambiarEstado);

        jLabel2.setText("  ");
        add(jLabel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Links", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        jPanel1.setLayout(new org.jdesktop.swingx.VerticalLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/SriLogo.png"))); // NOI18N
        jPanel1.add(jLabel1);

        lblConsultaSri.setBackground(new java.awt.Color(153, 0, 0));
        lblConsultaSri.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblConsultaSri.setForeground(new java.awt.Color(0, 51, 153));
        lblConsultaSri.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConsultaSri.setText("Consulta");
        lblConsultaSri.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblConsultaSri.setPreferredSize(new java.awt.Dimension(57, 20));
        jPanel1.add(lblConsultaSri);

        lblAnulacionSri.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAnulacionSri.setForeground(new java.awt.Color(153, 0, 0));
        lblAnulacionSri.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnulacionSri.setText("Anulación");
        lblAnulacionSri.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAnulacionSri.setPreferredSize(new java.awt.Dimension(68, 20));
        jPanel1.add(lblAnulacionSri);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirXml;
    private javax.swing.JButton btnAutorizarDocumento;
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnObtenerClaveAcceso;
    private javax.swing.JButton btnReenviarCorreo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAnulacionSri;
    private javax.swing.JLabel lblConsultaSri;
    // End of variables declaration//GEN-END:variables

    private void listenerBotones() {
        
        btnCambiarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ComprobanteEntity.ComprobanteEnumEstado.values();
                ComprobanteEntity.ComprobanteEnumEstado opcionSeleccionada=(ComprobanteEntity.ComprobanteEnumEstado) JOptionPane.showInputDialog(null,"Seleccione el estado para cambiar:","Cambiar estado", JOptionPane.QUESTION_MESSAGE, null, ComprobanteEntity.ComprobanteEnumEstado.values(), ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
                if(opcionSeleccionada!=null)
                {
                    ComprobanteEntity comprobanteEntidad=comprobante.getComprobante();
                    comprobanteEntidad.setEstado(opcionSeleccionada.getEstado());
                    try {
                        ServiceFactory.getFactory().getComprobanteServiceIf().editar(comprobanteEntidad);
                        DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.AccionesFormulario.PROCESO_CORRECTO);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje("Error",ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                }
            }
        });
        
        btnAutorizarDocumento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comprobante.getComprobante().getEstadoEnum()!=null && comprobante.getComprobante().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR))
                {
                //if(estadoFormulario.equals(ESTADO_EDITAR))
                //{
                    if(DialogoCodefac.dialogoPregunta("Advertencia","Esta opción solo debe ser usada para corregir problemas, Por ejemplo:\n-Si el comprobante está  autorizada en el sri pero no en el sistema.\n-Para corregir cualquier problema con el sistema. \n\n Está  seguro que desea continuar de todos modos ? ",DialogoCodefac.MENSAJE_ADVERTENCIA))
                    {
                        try {
                            ServiceFactory.getFactory().getComprobanteServiceIf().autorizarComprobante(comprobante.getComprobante());
                            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.PROCESO_CORRECTO);
                            btnAutorizarDocumento.setEnabled(false);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                //}
                }
                else
                {
                    DialogoCodefac.mensaje("Advertencia","Esta función esta disponible solo para los comprobantes no autorizados", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
        
        btnObtenerClaveAcceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogMostrarClaveAcceso dialogo=new DialogMostrarClaveAcceso(null,true);
                dialogo.setClaveAcceso(comprobante.getComprobante().getClaveAcceso());      
                
                dialogo.setTipoComprobante(comprobante.getComprobante().getCodigoDocumentoEnum().getNombre());
                if(comprobante.getComprobante().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR))
                {
                    dialogo.setFechaAutorizacion("Comprobante no esta autorizado en el SRI");
                }
                else
                {
                    dialogo.setFechaAutorizacion((comprobante.getComprobante().getFechaAutorizacionSri()!=null)?comprobante.getComprobante().getFechaAutorizacionSri().toString():"revisar en el archivo xml");
                }
                
                //dialogo.setIdentificacionReceptor(comprobante.getComprobante().getCodigoDocumento());
                setearDatosRecepcion(dialogo);
                //dialogo.setCorreoElectronicoReceptor("");
                dialogo.setearDatos();
                dialogo.setVisible(true);
                

            }
        });
        
        btnReenviarCorreo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Boolean reenviarCorreo=DialogoCodefac.dialogoPregunta("Advertencia ","¿Está seguro que desea reenviar la información al correo? ",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(reenviarCorreo)
                {
                    try {
                        int etapaEnviarCorreo=ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE; //Por defecto elijo que los correos se envie solo el xml firmado
                        ParametroCodefac parametroCodefac=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.TIPO_ENVIO_COMPROBANTE);
                        if(parametroCodefac!=null && parametroCodefac.getValor().equals(ParametroCodefac.TipoEnvioComprobanteEnum.ENVIAR_AUTORIZADO.getLetra()))
                        {
                            etapaEnviarCorreo=ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE_AUTORIZADO;
                        }
                                                
                        comprobante.getPanelPadre().cambiarCursorEspera();
                        
                        //TODO: ver si usar el metodo procesarComprobantesPendiente porque este estan mal implementado para otros casos desde otras etapas
                        List<AlertaComprobanteElectronico> alertas=ServiceFactory.getFactory().getComprobanteServiceIf().procesarComprobantesPendienteSinCallBack(
                                etapaEnviarCorreo,
                                etapaEnviarCorreo,
                                comprobante.getComprobante().getClaveAcceso(),
                                obtenerCorreosFactura()); //TOdo:Verificar si puedo hacer alguna manera para solo enviar a los correos que esten escritos
                        
                        if(alertas.size()>0)
                        {
                            String mensajeCompleto = AlertaComprobanteElectronico.unirTodasAlertas(alertas);
                            DialogoCodefac.mensaje("Alertas en el proceso ",mensajeCompleto,DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                        else
                        {                            
                            DialogoCodefac.mensaje(MensajeCodefacSistema.CorreoElectronico.CORREO_ENVIADO);
                        }
                        comprobante.getPanelPadre().cambiarCursorNormal();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje(MensajeCodefacSistema.ErrorComunicacion.ERROR_COMUNICACION_SERVIDOR);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                }
            }
        });
        
        btnAbrirXml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    RemoteInputStream remoteInputStream =ServiceFactory.getFactory().getComprobanteServiceIf().obtenerXmlFirmadoComprobante(comprobante.getEmpresa(),comprobante.getComprobante().getClaveAcceso());
                    
                    String nombreArchivoFinal=UtilidadesArchivos.generarNombreArchivoUnico("firma","xml");
                    File fileDestino = new File(ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES+"/"+nombreArchivoFinal); //Ver si parametrizar el backslash 
                    //crear toda la ruta si no existe
                    if (!fileDestino.exists()) {
                        fileDestino.getParentFile().mkdirs();
                        //file.mkdir();
                    }

                    OutputStream outputStream = new FileOutputStream(fileDestino);

                    InputStream inputStream = RemoteInputStreamClient.wrap(remoteInputStream);

                    UtilidadesArchivos.grabarInputStreamEnArchivo(inputStream, outputStream);
                    UtilidadesSistema.abrirDocumento(fileDestino);
                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ComponenteDatosComprobanteElectronicosPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void setearDatosRecepcion(DialogMostrarClaveAcceso dialogo)
    {
        String identificacionReceptor="";
        String correoReceptor="";
        
        switch (comprobante.getComprobante().getCodigoDocumentoEnum()) {
            case FACTURA:
                Factura factura=(Factura) comprobante.getComprobante();
                identificacionReceptor=factura.getCliente().getIdentificacion();
                correoReceptor=factura.getCliente().getCorreoElectronico();
                break;

            case NOTA_CREDITO:
                NotaCredito notaCredito = (NotaCredito) comprobante.getComprobante();
                identificacionReceptor = notaCredito.getCliente().getIdentificacion();
                correoReceptor = notaCredito.getCliente().getCorreoElectronico();
                break;

                
            case RETENCIONES:
                Retencion retencion = (Retencion) comprobante.getComprobante();
                identificacionReceptor = retencion.getProveedor().getIdentificacion();
                correoReceptor = retencion.getProveedor().getCorreoElectronico();
                break;
                
                
            case GUIA_REMISION:
                GuiaRemision guiaRemision = (GuiaRemision) comprobante.getComprobante();
                identificacionReceptor = guiaRemision.getDestinatarios().get(0).getDestinatorio().getIdentificacion();
                correoReceptor = guiaRemision.getDestinatarios().get(0).getDestinatorio().getCorreoElectronico();
                break;
        }
        
        dialogo.setIdentificacionReceptor(identificacionReceptor);
        dialogo.setCorreoElectronicoReceptor(correoReceptor);
    }
    
    public void habilitar(boolean habilitar)
    {
        btnObtenerClaveAcceso.setEnabled(habilitar);
        btnAbrirXml.setEnabled(habilitar);
        btnReenviarCorreo.setEnabled(habilitar);
        
        btnAutorizarDocumento.setEnabled(false); //Por defecto este boton siempre va a estar desabilitado
        btnCambiarEstado.setEnabled(habilitar);
        /*if(habilitar==true)
        {
            if(comprobante.getComprobante().getEstadoEnum()!=null && comprobante.getComprobante().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR))
            {
                btnAutorizarDocumento.setEnabled(true);
            }
        }*/

        
    }

    public void setComprobante(ComponenteDatosComprobanteElectronicosInterface comprobante) {
        this.comprobante = comprobante;
    }

    private List<String> obtenerCorreosFactura() {
        ArrayList<String> correos = new ArrayList<String>();

        if (comprobante.getDatosAdicionales() != null) {
            for (ComprobanteAdicional datoAdicional : comprobante.getDatosAdicionales()) {
                if (FacturaAdicional.Tipo.TIPO_CORREO.getLetra().equals(datoAdicional.getTipo())) {

                    correos.add(datoAdicional.getValor());

                }
            }
        }
        return correos;
    }
    
    public void habiliarBotonAutorizar()
    {
        if(comprobante.getComprobante().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR) && comprobante.getComprobante().getEstadoEnum()!=null)
        {
            btnAutorizarDocumento.setEnabled(true);
        }
    }

    public JButton getBtnAutorizarDocumento() {
        return btnAutorizarDocumento;
    }

    private void listenerLabels() {
        
        lblConsultaSri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblConsultaSri.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilidadesWeb.abrirPaginaWebNavigador(ParametrosSistemaCodefac.LINK_CONSULTAS_SRI);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        lblAnulacionSri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblAnulacionSri.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilidadesWeb.abrirPaginaWebNavigador(ParametrosSistemaCodefac.LINK_ANULACIONES_SRI);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
    }
 
    
}
