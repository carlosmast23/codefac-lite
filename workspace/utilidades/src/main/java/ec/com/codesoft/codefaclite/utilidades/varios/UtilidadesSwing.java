/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesSwing {
    
    public static JFileChooser getJFileChooserPreBuild(String title,String nombreExtension,String[] filter)
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle(title);
        jFileChooser.setFileFilter(new FileNameExtensionFilter(nombreExtension,filter));  
        return jFileChooser;
    }
    
    public static void agregarLinkLabel(JLabel label,String url)
    {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilidadesSistema.abrirUrlNavegador(url);
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
    
    public static void corregirFuncionamientoSeteoManualJxDate(JXDatePicker datePicker,SetearFechaIf setearDatoIf) {        
        JTextField textField = (JTextField) datePicker.getComponent(0);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDatePicker(datePicker, textField,setearDatoIf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDatePicker(datePicker, textField,setearDatoIf);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDatePicker(datePicker, textField,setearDatoIf);
            }
        });
     
        
    }
    
    private static void updateDatePicker(JXDatePicker datePicker, JTextField textField,SetearFechaIf setearDatoIf) {
        String text = textField.getText();
        DateFormat dateFormat = datePicker.getFormats()[0];
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(text);
            setearDatoIf.setear(date);
            //No puedo establecer porque generar un error al setear y estar manejando el mismo evento del mismo componente
            //datePicker.getEditor().commitEdit();
            //datePicker.setDate(date);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            // Manejar el error aquí si el formato es inválido
        }
    }
    
    /*public static void corregirFuncionamientoSeteoManualJxDate(JXDatePicker cmbDataPicker)    
    {
        JTextField textField= (JTextField) cmbDataPicker.getComponent(0);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDatePicker();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
         
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
         
            }
            
             private void updateDatePicker() {
                String text = textField.getText();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.util.Date date = dateFormat.parse(text);
                    cmbDataPicker.setDate(date);
                } catch (ParseException ex) {
                    // Manejar el error aquí si el formato es inválido
                }
            }
        });
    }*/
    
    public interface SetearFechaIf
    {
        public void setear(java.util.Date fecha);
    }
}
