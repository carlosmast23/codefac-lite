/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.ProcesoSegundoPlano;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.controlador.dialog.AvisoDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
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
import java.util.Date;
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
                Empresa empresaSeleccionada= (Empresa) getCmbEmpresa().getSelectedItem();
                if(empresaSeleccionada!=null)
                {                    
                    try {
                        DialogoCodefac.mostrarDialogoCargando(new ProcesoSegundoPlano() {
                            @Override
                            public void procesar() {
                                GeneralPanelModel.terminarAutorizarComprobantesPendientes(empresaSeleccionada);
                                GeneralPanelModel.generarRespaldoBaseDatosPorCorreo(empresaSeleccionada);
                            }
                            
                            @Override
                            public String getMensaje() {
                                return "Cerrando el Sistema ...";
                            }                    
                        });
                    } catch (ExcepcionCodefacLite ex) {
                        Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                GeneralPanelModel.salirSistema(panelPrincipal, usuario);
                
                //salirSistema();
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
    
    @Deprecated
    //TODO: Unificar metodo con el de GeneralPanelModel
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
                        DatosLogin datosLogin=getDatosLogin();
                        panelPrincipal.setSessionCodefac(datosLogin);
                        panelPrincipal.cambiarAspectoDependiendoUsuario(usuario);
                        //Grabar los datos de comprobacion de licencia para saber que se esta accediendo
                        //TODO: Hago esto de forma temporal porque aveces en el proceso interno de comprobar no se graba en la base de datos la fecha
                        UtilidadesServiceIf utilidadesService=ServiceFactory.getFactory().getUtilidadesServiceIf();
                        utilidadesService.grabarFechaRevisionLicencia(null, empresaSeleccionada);
                        advertirCajaSessionAbiertaDiaAnterior(datosLogin.usuario, datosLogin.sucursal);
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
                if(manejarErrorConexionFatal(ex))
                {
                    return;
                }
                DialogoCodefac.mensaje("Error Login", "Datos Incorrectos", MENSAJE_INCORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                if(manejarErrorConexionFatal(ex))
                {
                    return;
                }
                DialogoCodefac.mensaje("Error Login", ex.getMessage(), MENSAJE_INCORRECTO);
            }
        }
    
    }

    /**
     * Advierte (sin bloquear el ingreso) si el usuario tiene una sesion de caja activa cuya apertura
     * quedo en un dia anterior -sintoma de que nadie hizo el cierre de caja al final del turno
     * pasado-. Si no se cierra, las ventas de HOY se siguen sumando sobre esa misma sesion vieja, y
     * el "valor teorico" del proximo cierre termina reflejando varios dias de ventas juntos en vez de
     * solo el dia actual, lo que hace parecer un faltante de caja que en realidad no es tal (ver
     * incidente de auditoria 2026-11-26: sesion de caja abierta desde el 24/11 que siguio acumulando
     * ventas el 25/11, dando un "valor teorico" que sumaba los 2 dias).
     */
    private void advertirCajaSessionAbiertaDiaAnterior(Usuario usuarioLogueado, Sucursal sucursal)
    {
        if (usuarioLogueado == null || sucursal == null)
        {
            return;
        }
        try
        {
            List<CajaSession> sesionesActivas = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuarioYSucursal(usuarioLogueado, sucursal);
            if (sesionesActivas == null)
            {
                return;
            }
            //Se calcula una sola vez, no por cada sesion encontrada (el permiso no cambia entre una
            //sesion vieja y otra del mismo usuario).
            Boolean tienePermisoAbrirCaja = tienePermisoVentana(usuarioLogueado, VentanaEnum.ABRIR_CAJA);
            for (CajaSession sesion : sesionesActivas)
            {
                if (sesion.getFechaHoraApertura() == null)
                {
                    continue;
                }
                if (!UtilidadesFecha.verificarFechaHoy(sesion.getFechaHoraApertura()))
                {
                    //Piloto 2026-11-26 del componente AvisoDialog reutilizable (ver controlador.dialog.
                    //AvisoDialog): un dialogo por cada sesion de caja vieja encontrada, en vez del
                    //DialogoCodefac.mensaje(...) generico (JOptionPane) que se usaba antes. El criterio
                    //de deteccion (fecha de apertura distinta a hoy) no cambio, solo la presentacion y
                    //la navegacion directa a "Abrir Caja".
                    mostrarAvisoCajaPendienteCierre(sesion, tienePermisoAbrirCaja);
                }
            }
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarAvisoCajaPendienteCierre(CajaSession sesion, Boolean tienePermisoAbrirCaja)
    {
        String nombreCaja = (sesion.getCaja() != null) ? sesion.getCaja().getNombre() : "sin nombre";
        String fechaApertura = ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(sesion.getFechaHoraApertura());

        //null (no se pudo determinar, ej. perfiles no cargados en esta respuesta de login) se trata
        //igual que "si tiene permiso": es preferible ofrecer de mas la accion de ir a revisar la caja
        //a negarsela de forma incorrecta por un dato que no se pudo confirmar. Solo un "false" firme
        //(se encontraron los perfiles del usuario y ninguno incluye Abrir Caja) oculta la accion.
        boolean ofrecerRevisarCierre = !Boolean.FALSE.equals(tienePermisoAbrirCaja);

        AvisoDialog.Builder constructor = new AvisoDialog.Builder(panelPrincipal)
                .tipo(AvisoDialog.TipoAviso.ADVERTENCIA)
                .titulo("Caja pendiente de cierre")
                .dato(nombreCaja)
                .dato("Abierta desde el " + fechaApertura)
                .accionSecundaria("Más tarde", null);

        if (ofrecerRevisarCierre)
        {
            constructor.descripcion("Las nuevas ventas se sumarán a esta sesión. Revisa el cierre antes de iniciar una nueva jornada.")
                    .accionPrincipal("Revisar cierre", new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //No cierra la caja automaticamente: solo abre la pantalla existente de
                            //Abrir/Cerrar Caja (VentanaEnum.ABRIR_CAJA) para que el usuario decida y
                            //confirme el cierre el mismo, con los datos reales de esa sesion.
                            panelPrincipal.crearVentanaCodefac(VentanaEnum.ABRIR_CAJA, false, null);
                        }
                    });
        }
        else
        {
            constructor.descripcion("Las nuevas ventas se sumarán a esta sesión. No tiene permiso para revisar el cierre de caja: pida a un responsable que lo revise.");
        }

        constructor.build().mostrar();
    }

    /**
     * Verifica si el usuario tiene permiso para abrir una pantalla (VentanaEnum), replicando la
     * misma fuente de datos que ya usa el menu principal (Perfil.getVentanasPermisos()), pero sin
     * depender del arbol de menu ya construido (este chequeo corre antes de que la ventana principal
     * termine de armar su menu). Devuelve null (no "false") cuando no se pudo determinar con certeza
     * -ej. si la respuesta de login no trajo cargados los perfiles del usuario-, para que quien
     * llama pueda tratar ese caso como "se asume que si tiene permiso" en vez de bloquear de mas.
     */
    private Boolean tienePermisoVentana(Usuario usuario, VentanaEnum ventanaEnum)
    {
        try
        {
            if (usuario == null || usuario.getPerfilesUsuario() == null || usuario.getPerfilesUsuario().isEmpty())
            {
                return null;
            }
            for (PerfilUsuario perfilUsuario : usuario.getPerfilesUsuario())
            {
                Perfil perfil = perfilUsuario.getPerfil();
                if (perfil == null || perfil.getVentanasPermisos() == null)
                {
                    continue;
                }
                for (PermisoVentana permiso : perfil.getVentanasPermisos())
                {
                    if (ventanaEnum.equals(permiso.getVentanaEnum()))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (Exception ex)
        {
            Logger.getLogger(LoginModel.class.getName()).log(Level.WARNING, "No se pudo verificar permiso de ventana: " + ventanaEnum, ex);
            return null;
        }
    }

    private boolean manejarErrorConexionFatal(Exception ex)
    {
        String detalle=(ex!=null)?ex.getMessage():null;
        if(detalle==null)
        {
            return false;
        }
        
        String detalleMayuscula=detalle.toUpperCase();
        boolean errorDerbyBloqueado=detalleMayuscula.contains("ERROR XSDB6")
                || detalleMayuscula.contains("ANOTHER INSTANCE OF DERBY")
                || detalleMayuscula.contains("FAILED TO START DATABASE");
        boolean errorBaseDatos=detalleMayuscula.contains("SE PRODUJO UN ERROR EN LA BASE DE DATOS")
                || detalleMayuscula.contains("ERROR DE PERSISTENCIA")
                || detalleMayuscula.contains("PERSISTENCEEXCEPTION");
        
        if(!errorDerbyBloqueado && !errorBaseDatos)
        {
            return false;
        }
        
        String mensaje="Existe un problema de conexión con la base de datos.";
        if(errorDerbyBloqueado)
        {
            mensaje+="\nOtra instancia de Derby ya tiene abierta la base embebida.";
            mensaje+="\nCierre la otra instancia del sistema o libere la base y vuelva a intentar.";
        }
        else
        {
            mensaje+="\nNo es posible iniciar el sistema mientras no se pueda abrir la base de datos.";
        }
        
        DialogoCodefac.mensaje("Error base de datos", mensaje, MENSAJE_ADVERTENCIA);
        dispose();
        System.exit(0);
        return true;
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
            manejarErrorConexionFatal(ex);
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
            manejarErrorConexionFatal(ex);
        }catch (ServicioCodefacException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            manejarErrorConexionFatal(ex);
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
