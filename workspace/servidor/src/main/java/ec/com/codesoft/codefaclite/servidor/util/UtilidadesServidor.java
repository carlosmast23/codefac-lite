/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.test.CrearBaseDatos;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadesServidor {
    public static InputStream[] querys={
        RecursoCodefac.SQL.getResourceInputStream("create_cliente.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_usuario.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_usuario.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_sri_forma_pago.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_sri_identificacion.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_default.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_impuesto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_impuesto_detalle.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_impuesto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_parametro.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_parametros.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_impuesto_detalle.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_producto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_factura.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_nota_credito.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_cliente.sql"),
    };
    
    public static void crearBaseDatos()
    {
         try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //obtenemos la conexión si no existe crea la base
            Connection conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby2.DB;create=true");
            
            if (conn!=null)
            {
                
                /**
                 * Busca todos los querys disponibles para ejecutar
                 */
                for (InputStream query : querys) {
                    String sql=UtilidadesTextos.getStringURLFile(query);
                    String[] sentencias= sql.split(";");
                    for (String sentencia : sentencias) {
                        PreparedStatement pstm = conn.prepareStatement(sentencia);
                        pstm.execute();
                        pstm.close();
                    }
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
