/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CrearBaseDatos 
{
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //obtenemos la conexión
            Connection conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby2.DB;create=true");
            
            if (conn!=null)
            {
                JOptionPane.showMessageDialog(null,"OK base de datos listo");
                String creartabla="create table CLIENTE( ID_CLIENTE int not null, NOMBRE_SOCIAL varchar(256),TIPO_IDENTIFICACION varchar(64), CEDULA int, TIPO_CLIENTE varchar(12),DIRECCION varchar(1024), TELEFONO_CONVENCIONAL varchar(12),EXTENSION_TELEFONO varchar(256), TELEFONO_CELULAR varchar(12), CORREO_ELECTRONICO varchar(60),  primary key (ID_CLIENTE))";
              
                //String desc="disconnect;";
                PreparedStatement pstm = conn.prepareStatement(creartabla);
                pstm.execute();
                pstm.close();

                //PreparedStatement pstm2 = conn.prepareStatement(desc);
                //pstm2.execute();
                //pstm2.close();

                JOptionPane.showMessageDialog(null,"BD Creada correctamente");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
