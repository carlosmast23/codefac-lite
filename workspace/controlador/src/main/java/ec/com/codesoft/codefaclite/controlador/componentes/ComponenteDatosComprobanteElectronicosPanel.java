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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirXml;
    private javax.swing.JButton btnAutorizarDocumento;
    private javax.swing.JButton btnObtenerClaveAcceso;
    private javax.swing.JButton btnReenviarCorreo;
    // End of variables declaration//GEN-END:variables

    private void listenerBotones() {
        
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
                        comprobante.getPanelPadre().cambiarCursorEspera();
                        List<AlertaComprobanteElectronico> alertas=ServiceFactory.getFactory().getComprobanteServiceIf().procesarComprobantesPendienteSinCallBack(
                                ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE,
                                ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE,
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
    
    public void habilitar(boolean habilitar)
    {
        btnObtenerClaveAcceso.setEnabled(habilitar);
        btnAbrirXml.setEnabled(habilitar);
        btnReenviarCorreo.setEnabled(habilitar);
        
        btnAutorizarDocumento.setEnabled(false); //Por defecto este boton siempre va a estar desabilitado
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

    

    
    
}
