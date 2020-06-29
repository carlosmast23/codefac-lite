/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes.ComponenteEnvioSmsData;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.sms.ControladorPlantillaSms;
import static ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface.ESTADO_EDITAR;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 ;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

/**
 *
 * @author Carlos
 */
public class ComponenteEnvioSmsPanel extends javax.swing.JPanel {

    private ComponenteEnvioSmsInterface controlador;
    /**
     * Creates new form ComponenteEnvioSmsPanel
     */
    public ComponenteEnvioSmsPanel() {
        initComponents();
        listener();
    }

    public ComponenteEnvioSmsPanel(ComponenteEnvioSmsInterface controlador) {
        initComponents();
        listener();
        this.controlador=controlador;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnEnviarSms = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMensajeTexto = new javax.swing.JTextArea();
        lblCantidadTextoMensajes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbPlantillasSms = new javax.swing.JComboBox<>();

        setLayout(new java.awt.GridBagLayout());

        btnEnviarSms.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnEnviarSms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/sms.png"))); // NOI18N
        btnEnviarSms.setText("Enviar Mensaje Texto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        add(btnEnviarSms, gridBagConstraints);

        txtMensajeTexto.setColumns(20);
        txtMensajeTexto.setRows(5);
        jScrollPane1.setViewportView(txtMensajeTexto);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jScrollPane1, gridBagConstraints);

        lblCantidadTextoMensajes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCantidadTextoMensajes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidadTextoMensajes.setText("0/0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(lblCantidadTextoMensajes, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Plantilla: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(jLabel1, gridBagConstraints);

        cmbPlantillasSms.setFont(new java.awt.Font("Arial", 0, 12));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(cmbPlantillasSms, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarSms;
    private javax.swing.JComboBox<PlantillaSmsEnum> cmbPlantillasSms;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidadTextoMensajes;
    private javax.swing.JTextArea txtMensajeTexto;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnEnviarSms() {
        return btnEnviarSms;
    }

    public JComboBox<PlantillaSmsEnum> getCmbPlantillasSms() {
        return cmbPlantillasSms;
    }

    public JTextArea getTxtMensajeTexto() {
        return txtMensajeTexto;
    }
    
    

    private void listener() {
        
        getBtnEnviarSms().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    SmsServiceIf servidorSms=ServiceFactory.getFactory().getSmsServiceIf();
                    if(!servidorSms.isServicioDisponible())
                    {
                        DialogoCodefac.mensaje("Error","Problemas con el servicio SMS , no se pueden enviar los mensajes",DialogoCodefac.MENSAJE_INCORRECTO);
                        return;
                    }
                    
                    String mensajeEnviar=txtMensajeTexto.getText();
                    if(mensajeEnviar.length()>=ParametrosSistemaCodefac.LIMITE_CARACTERES_SMS)
                    {
                        DialogoCodefac.mensaje("Error","El mensaje es superior de la cantidad de palabras permitidas", DialogoCodefac.MENSAJE_INCORRECTO);
                        return;
                    }
                    
                    
                    if(!controlador.getValidacionEnvioSms())
                    {
                        return; //Si la validacion es falsa  no continuar
                    }

                    if(txtMensajeTexto.getText().isEmpty())
                    {
                        DialogoCodefac.mensaje("Error","Por favor ingrese un texto para enviar",DialogoCodefac.MENSAJE_INCORRECTO);
                        return;                   
                    }
                    
                    
                   
                    
                    //Validar que exista datos
                    if(controlador.getDataSms().size()==0)
                    {
                        DialogoCodefac.mensaje("Incorrecto","El servicio de Sms no esta diponible",DialogoCodefac.MENSAJE_INCORRECTO);
                        return;
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Correcto","Los mensajes estan siendo procesadas",DialogoCodefac.MENSAJE_CORRECTO);
                    }
                    
                    //Metodo que se encarga de contruir el mensaje y enviar
                    ControladorPlantillaSms.enviarMensajesConPlantilla(controlador.getDataSms(), mensajeEnviar,controlador.getInterfaceCallback());                    
                    
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ComponenteEnvioSmsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                
            }
        });
        
        getCmbPlantillasSms().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlantillaSmsEnum plantilla = (PlantillaSmsEnum) getCmbPlantillasSms().getSelectedItem();
                txtMensajeTexto.setText(plantilla.getMensaje());
            }
        });
        
        
        txtMensajeTexto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Integer cantidadTexto=txtMensajeTexto.getText().toString().length();
                lblCantidadTextoMensajes.setText(cantidadTexto.toString()+"/"+ParametrosSistemaCodefac.LIMITE_CARACTERES_SMS);
                if(cantidadTexto>=ParametrosSistemaCodefac.LIMITE_CARACTERES_SMS)
                {
                    lblCantidadTextoMensajes.setForeground(Color.RED);
                }
                else
                {
                    lblCantidadTextoMensajes.setForeground(Color.BLACK);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
    }

    public void setControlador(ComponenteEnvioSmsInterface controlador) {
        this.controlador = controlador;
        valoresIniciales();
    }

    private void valoresIniciales() {
                /**
         * Cargar plantillas disponibles para enviar por mensajeria
         */
        getCmbPlantillasSms().removeAllItems();
        for (PlantillaSmsEnum plantilla : PlantillaSmsEnum.findListByVentana(controlador.getVentanaEnum())) 
        {
            getCmbPlantillasSms().addItem(plantilla);
        }
    }
    
    
}
