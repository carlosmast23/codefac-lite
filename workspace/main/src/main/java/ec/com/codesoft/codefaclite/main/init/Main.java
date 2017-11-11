/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;


import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.crm.model.ProductoModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.model.LoginModel;
import ec.com.codesoft.codefaclite.main.model.MenuControlador;
import ec.com.codesoft.codefaclite.main.panel.LoginForm;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Main {
    
    
    public static void main(String[] args) {
        componentesIniciales();   
        
        LoginModel loginModel=new LoginModel();
        loginModel.setVisible(true);
        
        /**
         * Si el usuario devuuelto es incorrecto terminar el aplicativo
         */
        Usuario usuarioLogin=loginModel.getUsuarioLogin();
        if(usuarioLogin==null)
        {
            System.out.println("aplicacion terminada");
            return ;
        }
        
        /**
         * Crear la session y cargar otro datos de la empresa
         */
        SessionCodefac session=new SessionCodefac();
        Empresa empresa=new Empresa();
        empresa.setDireccion("Quito, Av amazonas y America 214");
        empresa.setTelefonos("2671232/0918123213");
        empresa.setIdentificacion("17782823123");
        empresa.setNombre("CORECOMPU");
        session.setEmpresa(empresa);
        
        session.setUsuario(usuarioLogin);
        
        /**
         * Seteando la session de los datos a utilizar en el aplicativo
         */
        GeneralPanelModel panel=new GeneralPanelModel();
        panel.setSessionCodefac(session);
        
        
        /**
         * Añadir menus y ventanas a la aplicacion principal
         */        
        panel.setVentanasMenuList(agregarMenuVentana(panel));
        /**
         * Establecer propiedades del formulario principal
         */
        panel.setIconImage(new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("logoCodefac-ico.png")).getImage()); // NOI18N        
        panel.setVisible(true);
        panel.setExtendedState(MAXIMIZED_BOTH);


    }
    
    /**
     * Metodo donde se van a ligar las ventanas con los menus correspondientes
     * @param panel
     * @return 
     */
    public static List<MenuControlador> agregarMenuVentana(GeneralPanelModel panel)
    {
        List<MenuControlador> ventanas=new ArrayList<MenuControlador>();
        ventanas.add(new MenuControlador(panel.getjMenuCliente(),new ClienteModel()));
        ventanas.add(new MenuControlador(panel.getjMenuProducto(),new ProductoModel()));
        ventanas.add(new MenuControlador(panel.getjMenuFactura(),new FacturacionModel()));
        //ventanas.add(new MenuControlador(panel.getj(), new EmisorModel()));
        return ventanas;
    
    }
    
    public static void componentesIniciales()
    {
        try {
            AbstractFacade.cargarEntityManager();
        } catch (Exception e) {
            //e.p
            UtilidadesServidor.crearBaseDatos();
            JOptionPane.showMessageDialog(null,"Creada base de datos");
            AbstractFacade.cargarEntityManager();
            
        }
        
    }
}
