/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.RespaldarInformacionPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class RespaldarInformacionModel extends RespaldarInformacionPanel
{

    @Override
    public void iniciar() throws ExcepcionCodefacLite 
    {
        getBtnRespaldar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                 try {
                    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                    //obtenemos la conexión si no existe crea la base
                    Connection conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby2.DB;databaseName=codefac;create=true;user=root");
                    //Establecer autentificacion en derby 
                    Statement s = conn.createStatement();
                    s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'true')");
                    s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.authentication.provider', 'BUILTIN')");
                    s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.root', 'Code17bwbtj')");
                    s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.propertiesOnly', 'true')");
                    s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.language.sequence.preallocator', '1')");

                    java.text.SimpleDateFormat todaysDate =  new java.text.SimpleDateFormat("yyyy-MM-dd");
                    String backupdirectory = "c:/mybacksup/" + todaysDate.format((java.util.Calendar.getInstance()).getTime());
                    CallableStatement cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)"); 
                    cs.setString(1, backupdirectory);
                    cs.execute(); 
                    cs.close();
                    System.out.println("backed up database to "+backupdirectory);
                }
                catch(ClassNotFoundException cfe)
                {
                    System.out.println("1.-" + cfe);
                } catch (SQLException ex) {
                     System.out.println("2.- " + ex);
                }
            }    
        });
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        return "Respaldar Información";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
