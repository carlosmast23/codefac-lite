/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.service.interfacePanel.ServidorMonitorUpdateInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.test.CrearBaseDatos;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadesServidor {

    //Listado de conexiones en el Servidor
    public static List<String> hostConectados = new ArrayList<String>();

    public static ServidorMonitorUpdateInterface monitorUpdate;

    public static TipoLicenciaEnum tipoLicenciaEnum;

    public static String usuarioLicencia;

    public static String pathRecursos;

    public static Integer cantidadUsuarios;

    public static Map<ModuloCodefacEnum, Boolean> modulosMap;

    public static InputStream[] querys = {
        RecursoCodefac.SQL.getResourceInputStream("create_comprobante_fisico_disenio.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_modulo_academico.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_cliente.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_usuario.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_sri_forma_pago.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_sri_identificacion.sql"),
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
        RecursoCodefac.SQL.getResourceInputStream("create_perfil.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_acceso_directo.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_producto_proveedor.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_kardex.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_compra.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_bodega.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_categoria_producto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_catalogo_producto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_modulo_cartera.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_default.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_empresa.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_usuario.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_default_academico.sql"),};

    public static void crearBaseDatos() {
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

            if (conn != null) {

                /**
                 * Busca todos los querys disponibles para ejecutar
                 */
                for (InputStream query : querys) {
                    try {
                        String sql = UtilidadesTextos.getStringURLFile(query);
                        String[] sentencias = sql.split(";");
                        for (String sentencia : sentencias) {
                            PreparedStatement pstm = conn.prepareStatement(sentencia);
                            pstm.execute();
                            pstm.close();
                        }
                    } catch (NullPointerException cpe) {
                        System.out.println("Alerta al crear el sql, porfavor revise que los sql no tengan espacios en blanco al final, apesar de esta advertencia el proceso puede continuar sin ningun problema");
                    }
                }

            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
