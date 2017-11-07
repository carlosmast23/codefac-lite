/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.test.CrearBaseDatos;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
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
public class UtilidadesServidor {
    public static void crearBaseDatos()
    {
         try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //obtenemos la conexión
            Connection conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby2.DB;create=true");
            
            if (conn!=null)
            {
                //JOptionPane.showMessageDialog(null,"OK base de datos listo");
                //RecursoCodefac.SQL.getResourcePath("codefacLite");
                String script=UtilidadesTextos.getStringURLFile(RecursoCodefac.SQL.getResourceInputStream("codefacLite.sql"));
                //String creartabla="DROP TABLE CLIENTE";
                //String desc="disconnect;";
                PreparedStatement pstm = conn.prepareStatement(script);
                pstm.execute();
                pstm.close();

                //PreparedStatement pstm2 = conn.prepareStatement(desc);
                //pstm2.execute();
                //pstm2.close();

                //JOptionPane.showMessageDialog(null,"BD Creada correctamente");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
