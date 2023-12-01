/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.main.init.Main;
import static ec.com.codesoft.codefaclite.main.init.Main.modoAplicativo;
import ec.com.codesoft.codefaclite.main.panel.LoginFormDialog;
import ec.com.codesoft.codefaclite.main.utilidades.UtilidadServicioWeb;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_ADVERTENCIA;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_INCORRECTO;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.utilidades.archivos.UtilidadesDirectorios;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class LoginModel extends LoginFormDialog{

    private static final Logger LOG = Logger.getLogger(LoginModel.class.getName());
    //public boolean salirAplicacion=false;
    
    //private UsuarioServicioIf usuarioServicio;
    private Usuario usuario;
    /**
     * Contenedor principal especialmente necesito este dato para poder setear la session despues de cargar el login
     */
    private GeneralPanelModel panelPrincipal;

    public LoginModel(GeneralPanelModel panelPrincipal) {
        super(null,true);
        valoresIniciales();
        initListenerBotones();
        initListenerCombos();
        initListenerPantalla();
        //this.usuarioServicio=ServiceFactory.getFactory().getUsuarioServicioIf();
        this.panelPrincipal=panelPrincipal;
        
        //Image fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoInicial.jpg")).getImage();
        //getPanelPrincipal().setBorder(new Fondo2(fondoImg));
        
        //Setear la versión del sistema
        getLblVersion().setText("Versión:"+ParametrosSistemaCodefac.VERSION+"   ");        
               
    }

    private void initListenerBotones() {
                
        //new CodefacMsj("", CodefacMsj.TipoMensajeEnum.CORRECTO);
        getBtnUrlWeb().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    String urlWeb=ServiceFactory.getFactory().getUtilidadesServiceIf().getUrlServicioWeb();
                    UtilidadesWeb.abrirPaginaWebNavigador(urlWeb);
                    DialogoCodefac.mensaje(new CodefacMsj(urlWeb, CodefacMsj.TipoMensajeEnum.CORRECTO));
                } catch (RemoteException ex) {
                    Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnActualizarSistema().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ACTUALIZAR_SISTEMA)) {
                    ArchivoConfiguracionesCodefac.grabarConfiguracion(ArchivoConfiguracionesCodefac.CAMPO_MODO_ACTUALIZACION, ArchivoConfiguracionesCodefac.ModoActualizacionEnum.DESARROLLO.getNombre());
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.REINICIAR_SISTEMA);
                    System.exit(0);
                }
            }
        });
        
        getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirSistema();
            }
        });
        
        getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema(false);
            }
        });
        
        getTxtClave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema(false);
            }
        });
        
        getTxtUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema(false);
            }
        });
    }
    
    public void salirSistema()
    {
        try {
            //dispose();
            //salirAplicacion=true;
            //setVisible(false);
            UtilidadServicioWeb.apagarServicioWeb();
            if (!modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE)) 
            {
                ServiceFactory.getFactory().getEmpresaServiceIf().cerrarConexionDB();
            }
            dispose();
            System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Usuario getUsuarioLogin()
    {
        return usuario;
    }
    
    public DatosLogin getDatosLogin()
    {
        DatosLogin datosLogin=new DatosLogin();
        datosLogin.empresa=obtenerEmpresaSeleccionada();
        datosLogin.sucursal=obtenerSucursalSeleccionada();
        datosLogin.matriz=obtenerMatriz();
        datosLogin.usuario=getUsuarioLogin();
        return datosLogin;        
    }
    
    private Sucursal obtenerMatriz()
    {
        Sucursal sucursal=(Sucursal) getCmbSucursal().getSelectedItem();
        Sucursal matriz=null;
        if(sucursal!=null)
        {
            try {
                matriz = ServiceFactory.getFactory().getSucursalServiceIf().obtenerMatrizPorSucursal(sucursal.getEmpresa());
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return matriz;
    }
    
    private void ingresarSistema(Boolean modoForzado)
    {
        
        String clave = new String(getTxtClave().getPassword());
        String usuarioTxt = getTxtUsuario().getText();
        Empresa empresaSeleccionada=(Empresa) getCmbEmpresa().getSelectedItem();
        if (!usuarioTxt.equals("") && !clave.equals("")) {
            try {
                //usuario = usuarioServicio.login(usuarioTxt, clave, (Empresa) getCmbEmpresa().getSelectedItem());
                UsuarioServicioIf usuarioServicio=ServiceFactory.getFactory().getUsuarioServicioIf();
                LoginRespuesta loginRespuesta = usuarioServicio.login(usuarioTxt, clave, empresaSeleccionada,modoForzado);
                
                //Mostrar las alertas del sistema 
                if(loginRespuesta.alertas!=null)
                {
                    DialogoCodefac.mensaje("Alertas",loginRespuesta.obtenerAlertasConFormato(),MENSAJE_ADVERTENCIA);
                }
                
                switch(loginRespuesta.estadoEnum)
                {
                    case CORRECTO_USUARIO:
                        LOG.log(Level.INFO, "Ingresando con el usuario: " + usuarioTxt);
                        setVisible(false);
                        usuario=loginRespuesta.usuario;
                        panelPrincipal.setSessionCodefac(getDatosLogin());
                        panelPrincipal.cambiarAspectoDependiendoUsuario(usuario);
                        //Grabar los datos de comprobacion de licencia para saber que se esta accediendo
                        //TODO: Hago esto de forma temporal porque aveces en el proceso interno de comprobar no se graba en la base de datos la fecha
                        UtilidadesServiceIf utilidadesService=ServiceFactory.getFactory().getUtilidadesServiceIf();
                        utilidadesService.grabarFechaRevisionLicencia(null, empresaSeleccionada);
                        break;
                    case INCORRECTO_USUARIO:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje(), MENSAJE_INCORRECTO);
                        break;
                    case NO_EXISTE_DIRECTORIO_LICENCIA:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.NO_EXISTE_DIRECTORIO_LICENCIA.getMensaje(), MENSAJE_INCORRECTO);
                        Main.seleccionarDirectorioRecursos(empresaSeleccionada);
                        //validacionesEmpresa
                        break;
                    
                    case LICENCIA_DESACTUALIZADA:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.LICENCIA_DESACTUALIZADA.getMensaje(), MENSAJE_INCORRECTO);
                        String[] opciones = {"Ingresar Datos licencia","Cambiar directorio licencia", "Cancelar"};
                        int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Alerta", "Seleccione una opción para solucionar el problema", MENSAJE_ADVERTENCIA, opciones);
                        switch(opcionSeleccionada)
                        {
                            case 0:
                                pantallaRegistrarLicencia();
                                break;
                            case 1:
                                Main.actualizarDirectorioLicencia(empresaSeleccionada);
                                break;
                                
                        }
                        //validacionesEmpresa
                        break;
                        
                    case PAGOS_PENDIENTES:
                        LOG.log(Level.WARNING, "Error con las fechas de pago excedidas " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        Map<String,String> mapParametros=new HashMap<String,String>();
                        mapParametros.put("?1", loginRespuesta.usuarioLicencia);
                        mapParametros.put("?2", empresaSeleccionada.obtenerNombreEmpresa());
                        
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.PAGOS_PENDIENTES.getMensajeConParametros(mapParametros), MENSAJE_INCORRECTO);                        
                        break;

                    default:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",loginRespuesta.estadoEnum.getMensaje(), MENSAJE_INCORRECTO);
                        solicitarIngresoForzadoSistema();
                        break;
                }
                
               
            } catch (RemoteException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error Login", "Datos Incorrectos", MENSAJE_INCORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    public void solicitarIngresoForzadoSistema()
    {
        String claveIngresada = DialogoCodefac.mensajeTextoIngreso(new CodefacMsj("Existen problemas al comprobar la licencia, ingrese la clave de soporte para continuar: ", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        if (!UtilidadesSistema.verificarClaveSoporte(claveIngresada)) {
            DialogoCodefac.mensaje(MensajeCodefacSistema.IngresoInformacion.MENSAJE_CLAVE_INCORRECTA);            
        }
        else
        {
            //Si quiere volver a ingresar pero de manera forzada llamo al mismo metodo
            ingresarSistema(true);
        }
    }
    
    public void pantallaRegistrarLicencia()
    {
        //Crear un dialogo si no existe la licencia o esta desactualizada o con alguna incoherencia
        //ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true, existeLicencia);
        Empresa empresa=(Empresa) getCmbEmpresa().getSelectedItem();
        ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true,false,empresa);
        licenciaDialog.setVisible(true);
        //licenciaDialog.setValidacionLicenciaCodefac
        //licenciaDialog.licenciaCreada
        /*if (validacion.verificarConexionInternet()) {
            licenciaDialog.setVisible(true);
            if (licenciaDialog.licenciaCreada) {
                return comprobarLicencia(pathBase); //volver a verificar la licencia
            } else {
                return false;
            }
        } else {
            DialogoCodefac.mensaje("Error", "Para activar su producto conéctese a Internet", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }*/
    }

    private void valoresIniciales() {
        String anioActualStr=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.getFechaHoy());
        getLblPiePagina().setText("Codefac software de facturación electrónica @ Codesoft "+anioActualStr);
        
        try {
            UtilidadesComboBox.llenarComboBox(getCmbEmpresa(),ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodosActivos(OrdenarEnum.ASCEDENTE));
            //Setear valores de los combos
            /*List<Empresa> empresas=ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodos(); //Todo: Cambiar este metodo para solo obtener las empresas activas
            getCmbEmpresa().removeAllItems();
            for (Empresa empresa : empresas) {
                getCmbEmpresa().addItem(empresa);
            }*/
        } catch (RemoteException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cargarSucursalesPorEmpresa();        
        
    }
    
    private void cargarSucursalesPorEmpresa()
    {
        try {
            //Setear valores de los combos
            
            List<Sucursal> sucursales=ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(obtenerEmpresaSeleccionada()); //Todo: Cambiar este metodo para solo obtener las empresas activas
            getCmbSucursal().removeAllItems();
            for (Sucursal sucursal : sucursales) {
                getCmbSucursal().addItem(sucursal);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ServicioCodefacException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initListenerPantalla() {
         addComponentListener(new ComponentListener() { //Artificio para setear en los dialogs el focus
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {
                getTxtUsuario().requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                //salirAplicacion=true;
                /*UtilidadServicioWeb.apagarServicioWeb(); //Apagar el servicio web    
                setVisible(false);
                dispose();
                System.exit(0);*/
                salirSistema();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //salirAplicacion=true;
                //setVisible(false);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }
    
    private Empresa obtenerEmpresaSeleccionada()
    {
        return (Empresa) getCmbEmpresa().getSelectedItem();
    }

    private void initListenerCombos() {
        getCmbEmpresa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(obtenerEmpresaSeleccionada()!=null)
                {
                    cargarSucursalesPorEmpresa();     
                }
            }
        });
    }
    
    //TODO: Metodo Temporal para obtener la sucursal que el usuario accedio
    public Sucursal obtenerSucursalSeleccionada()
    {
        return (Sucursal) getCmbSucursal().getSelectedItem();
    }
    
    public void setearSucursalPorDefecto(Sucursal sucursal)
    {
        if(sucursal!=null)
        {
            getCmbEmpresa().setSelectedItem(sucursal.getEmpresa());
            getCmbSucursal().setSelectedItem(sucursal);
        }
    }
    
    /**
     * Clase de envoltorio solo para agrupar un conjunto de resultados de la pantalla Login
     */
    public class DatosLogin
    {
        public Empresa empresa;
        public Sucursal sucursal;
        public Sucursal matriz;
        public Usuario usuario;
        
    }
    
    
}
