/*
 * Created by JFormDesigner on Tue Oct 31 16:23:50 COT 2017
 */

package ec.com.codesoft.codefaclite.main.panel;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import ec.com.codesoft.codefaclite.main.model.GeneralPanelInterface;

/**
 * @author Tatiana Vasconez
 */
public abstract class NewJFormDesignerForm extends GeneralPanelInterface {
    public NewJFormDesignerForm() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        label5 = new JLabel();
        textField5 = new JTextField();
        button1 = new JButton();
        label1 = new JLabel();
        textField1 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        setVisible(true);
        setTitle("Formulario Ejemplo");
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "4*(default, $lcgap), default:grow, $lcgap, default, $lcgap, default:grow, $lcgap, default, $lcgap, default:grow, $lcgap, default",
            "5*(default, $lgap), default:grow, 2*($lgap, default)"));

        //---- label5 ----
        label5.setText("Buscar:");
        contentPane.add(label5, CC.xy(5, 3));
        contentPane.add(textField5, CC.xy(9, 3));

        //---- button1 ----
        button1.setText("Buscar");
        contentPane.add(button1, CC.xy(11, 3));

        //---- label1 ----
        label1.setText("Usuario:");
        contentPane.add(label1, CC.xy(5, 5));
        contentPane.add(textField1, CC.xy(9, 5));

        //---- label3 ----
        label3.setText("Direccion:");
        contentPane.add(label3, CC.xy(15, 5));
        contentPane.add(textField3, CC.xy(17, 5));

        //---- label2 ----
        label2.setText("Clave");
        contentPane.add(label2, CC.xy(5, 7));
        contentPane.add(textField2, CC.xy(9, 7));

        //---- label4 ----
        label4.setText("Ciudad:");
        contentPane.add(label4, CC.xy(15, 7));
        contentPane.add(textField4, CC.xy(17, 7));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, CC.xywh(5, 9, 13, 5));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JLabel label5;
    private JTextField textField5;
    private JButton button1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label4;
    private JTextField textField4;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
