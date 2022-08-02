/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.utilidades.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DellWin10
 */
public class UtilidadSql {
    
    public static Connection conectarBaseDatos(String usuario,String clave)
    {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //TODO: Parametrizar el nombre de la base de datos
            Connection conn = DriverManager.getConnection("jdbc:derby:Derby2.DB;databaseName=codefac;user=" + usuario + ";password=" + clave);
            //Statement s = conn.createStatement();
            return conn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilidadSql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UtilidadSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static String ejecutarConsultaSqlPrimerResultado(String usuario,String clave,String sql,String nombreColumna)
    {    
        String resultado=null;
        try {
            Connection conn=conectarBaseDatos(usuario, clave);
            if(conn==null)
            {
                return null ;
            }
            
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet registro =pstm.executeQuery();
            
            if (registro.next() == true) {
                resultado= registro.getString("valor");
            } 
            
            pstm.close();
            conn.close();            
        } catch (SQLException ex) {
            Logger.getLogger(UtilidadSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public static int ejecutarProcesoSql(String usuario,String clave,String sqlProceso)
    {
        int filasAfectadas=0;
        try {
            Connection conn=conectarBaseDatos(usuario, clave);
            
            if (conn == null) 
            {
                return 0;
            }

            PreparedStatement pstm = conn.prepareStatement(sqlProceso);
            filasAfectadas=pstm.executeUpdate();
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UtilidadSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }
    
    public static String convertirConsultaEnConsultaTamanio(String query)
    {
        //por el momento ocupo esta cadena temporal para luego poder encontrar las posiciones de corte con minusculas
        String queryTmp=query.toLowerCase();
        int primerCorte=queryTmp.indexOf("select")+"select".length();
        int segundoCorte=queryTmp.indexOf("from");
        String queryModificado=query.substring(0,primerCorte)+" count(1) "+query.substring(segundoCorte);
        
        //Eliminar las columnas de ordenar porque no se pueden ejecutar en conjunto con el comando count(1)
        if(queryModificado.toLowerCase().indexOf("order by")>=0)
        {
            queryModificado = queryModificado.substring(0, queryModificado.toLowerCase().indexOf("order by")); //TODO: Analizar como verificar para otros casos que tengan mas espacios entre order y by
        }
    
        return queryModificado;
    }
    
}
