/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;


import ec.com.codesoft.codefaclite.configuraciones.model.CalculadoraModel;
import ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.AyudaCodefacModel;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.ValidadorCodefacModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.crm.model.EmisorModel;
import ec.com.codesoft.codefaclite.crm.model.ProductoModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturaReporteModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel;
import ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteModel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.model.LoginModel;
import ec.com.codesoft.codefaclite.main.model.MenuControlador;
import ec.com.codesoft.codefaclite.main.model.SplashScreenModel;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Main {
    
    
    public static void main(String[] args) {
        
        SplashScreenModel splashScren=new SplashScreenModel();
        splashScren.agregarPorcentaje(40,"Cargando base de datos");
        splashScren.agregarPorcentaje(60,"Cargando datos session");
        splashScren.agregarPorcentaje(80,"Creando controlador codefac");
        splashScren.agregarPorcentaje(100,"Cargando ventanas");
        splashScren.setVisible(true);
        splashScren.iniciar();

        //DialogoCodefac.mensaje("uno mas","otro mas",DialogoCodefac.MENSAJE_CORRECTO);
        
        componentesIniciales();
        splashScren.siguiente();
        
       
                /**
         * Crear la session y cargar otro datos de la empresa
         */
        SessionCodefac session=new SessionCodefac();
        Empresa empresa=new Empresa();
        empresa.setDireccion("Sangolqui,Av.Calderon y Espejo");
        empresa.setTelefonos("022333167");
        empresa.setIdentificacion("1724218951001");
        empresa.setRazonSocial("Carlos Alfonso Sanchez Coyago");
        empresa.setNombreLegal("Codesoft Desarrollo");
        empresa.setContribuyenteEspecial("");
        empresa.setObligadoLlevarContabilidad(Empresa.NO_LLEVA_CONTABILIDAD);
        session.setEmpresa(empresa);
        //session.setParametrosCodefac(getParametros());
        splashScren.siguiente();

        
        
                /**
         * Seteando la session de los datos a utilizar en el aplicativo
         */
        
        GeneralPanelModel panel=new GeneralPanelModel();
        panel.setSessionCodefac(session);
        splashScren.siguiente();
        
        /**
         * Añadir menus y ventanas a la aplicacion principal
         */        
        panel.setVentanasMenuList(agregarMenuVentana(panel));
        panel.setPanelesSecundarios(agregarPanelesSecundarios());
        panel.agregarPanelesSecundarios();
        /**
         * Establecer propiedades del formulario principal
         */
        panel.setIconImage(new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("logoCodefac-ico.png")).getImage()); // NOI18N        
        panel.setExtendedState(MAXIMIZED_BOTH);
        splashScren.siguiente();
        splashScren.termino();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /**
         * Si el usuario devuuelto es incorrecto terminar el aplicativo
         */
        LoginModel loginModel=new LoginModel();
        loginModel.setVisible(true);
        Usuario usuarioLogin=loginModel.getUsuarioLogin();
        if(usuarioLogin==null)
        {
            System.out.println("aplicacion terminada");
            return ;
        }
        
        session.setUsuario(usuarioLogin);
        
        panel.setVisible(true);

        
        
    }
    
    public static Map<String,ParametroCodefac> getParametros()
    {
        Map<String,ParametroCodefac> parametros=new HashMap<String,ParametroCodefac>();
        ParametroCodefac param=new ParametroCodefac();
        
        param.id=1;
        param.nombre=ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA;
        param.valor="carlos_alfonso_sanchez_coyago.p12";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=2;
        param.nombre=ParametroCodefac.CLAVE_FIRMA_ELECTRONICA;
        param.valor="Code17bwbtj";        
        parametros.put(param.nombre,param);
        
        
        param=new ParametroCodefac();
        param.id=3;
        param.nombre=ParametroCodefac.DIRECTORIO_RECURSOS;
        param.valor="E:/FacturacionOffline";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=4;
        param.nombre=ParametroCodefac.MODO_FACTURACION;
        param.valor="pruebas";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=5;
        param.nombre=ParametroCodefac.SRI_WS_RECEPCION;
        param.valor="https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);

        param=new ParametroCodefac();
        param.id=6;
        param.nombre=ParametroCodefac.SRI_WS_RECEPCION_PRUEBA;
        param.valor="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);

        param=new ParametroCodefac();
        param.id=7;
        param.nombre=ParametroCodefac.SRI_WS_AUTORIZACION;
        param.valor="https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);

        param=new ParametroCodefac();
        param.id=8;
        param.nombre=ParametroCodefac.SRI_WS_AUTORIZACION_PRUEBA;
        param.valor="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=9;
        param.nombre=ParametroCodefac.SECUENCIAL_FACTURA;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=10;
        param.nombre=ParametroCodefac.SECUENCIAL_GUIA_REMISION;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=11;
        param.nombre=ParametroCodefac.SECUENCIAL_NOTA_CREDITO;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=12;
        param.nombre=ParametroCodefac.SECUENCIAL_NOTA_DEBITO;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=13;
        param.nombre=ParametroCodefac.SECUENCIAL_RETENCION;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        return parametros;
    }
    
    /**
     * Metodo donde se van a ligar las ventanas con los menus correspondientes
     * @param panel
     * @return                                             
     */
    public static List<MenuControlador> agregarMenuVentana(GeneralPanelModel panel)
    {
        List<MenuControlador> ventanas=new ArrayList<MenuControlador>();
        ventanas.add(new MenuControlador(panel.getjMenuCliente(),ClienteModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuProducto(),ProductoModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuFactura(),FacturacionModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuEmisor(),EmisorModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuComprobanteConfig(),ComprobantesConfiguracionModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuCalculadora(),CalculadoraModel.class,false));
        ventanas.add(new MenuControlador(panel.getjMenuItemUtilidades(),UtilidadComprobanteModel.class,false));
        ventanas.add(new MenuControlador(panel.getjMenuItemNotaCredito(),NotaCreditoModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemFacturaReporte(),FacturaReporteModel.class,true));
        return ventanas;
    
    }
    
    public static Map<String,PanelSecundarioAbstract> agregarPanelesSecundarios()
    {
        Map<String,PanelSecundarioAbstract> paneles=new HashMap<String,PanelSecundarioAbstract>();
        paneles.put(PanelSecundarioAbstract.PANEL_AYUDA,new AyudaCodefacModel() );
        paneles.put(PanelSecundarioAbstract.PANEL_MONITOR,MonitorComprobanteModel.getInstance());
        paneles.put(PanelSecundarioAbstract.PANEL_VALIDACION,new ValidadorCodefacModel());
        return paneles;
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

