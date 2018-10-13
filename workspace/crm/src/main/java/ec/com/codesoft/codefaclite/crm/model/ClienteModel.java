/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes.ComponenteEnvioSmsData;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteEnvioSmsInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.sms.ControladorPlantillaSms;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.crm.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.crm.test.EjemploCrm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesJuridicas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author PC
 */
public class ClienteModel extends ClienteForm implements DialogInterfacePanel<Persona>, InterfazPostConstructPanel ,ComponenteEnvioSmsInterface{

    private static final Logger LOG = Logger.getLogger(ClienteModel.class.getName());

    
    /**
     * Modelo para manejar las identificaciones del sri
     */
    private DefaultComboBoxModel<SriIdentificacion> modelComboIdentificacion;
    //List<SriIdentificacion> identificaciones;

    private PersonaServiceIf personaService;
    private Persona persona;
    private String razonSocial;
    //private String comboTipoCliente[] = {"CLIENTE", "SUJETO RETENIDO", "DESTINATARIO"};

    //private int opcionIdentificacion = 4;
    
    protected OperadorNegocioEnum operadorNegocioDefault;

    public ClienteModel() {
        this.personaService = ServiceFactory.getFactory().getPersonaServiceIf();
        getjTextExtension().setText("0");
        this.razonSocial = "";
        excluirValidaciones();
        cargarTipoClientes();
        cargarDatosIniciales();
        addListenerBotones();
        addListenerTexts();
        addListenerCombos();
        super.mapDatosIngresadosDefault.put(getjTextExtension(),"0");
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            if (!prevalidar()) {
                //Cancela el evento guardar porque no prevalido
                throw new ExcepcionCodefacLite("Error al prevalidar");
            }
            setearDatos();
            persona = personaService.grabar(persona);
            DialogoCodefac.mensaje("Datos correctos", "El cliente se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al prevalidar");
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            personaService.editar(persona);
            
            DialogoCodefac.mensaje("Correcto","La persona fue editada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","Error de comunicacion con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }
    
    private void setearDatos() {        
        persona.setNombres(getjTextNombres().getText());
        persona.setApellidos(getjTextApellidos().getText());
        persona.setRazonSocial(getjTextNombreSocial().getText());
        persona.setNombreLegal(getTxtNombreLegal().getText());
        
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=(Persona.TipoIdentificacionEnum) getjComboIdentificacion().getSelectedItem();
        persona.setTipoIdentificacion(tipoIdentificacionEnum.getLetra());
        persona.setIdentificacion(getjTextIdentificacion().getText());
        persona.setTipCliente((String) getjComboTipoCliente().getSelectedItem());
        persona.setDireccion(getjTextAreaDireccion().getText());
        persona.setTelefonoConvencional(getjTextTelefono().getText());
        persona.setExtensionTelefono(getjTextExtension().getText());
        persona.setTelefonoCelular(getjTextCelular().getText());
        persona.setCorreoElectronico(getjTextCorreo().getText());
        persona.setEstado(((ClienteEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
        persona.setTipo(((OperadorNegocioEnum) getCmbTipoOperador().getSelectedItem()).getLetra());
        persona.setNacionalidad(((Nacionalidad) getCmbNacionalidad().getSelectedItem()));
        persona.setSriFormaPago((SriFormaPago) getCmbFormaPagoDefecto().getSelectedItem());
        
        //Grabar la variable de obligado a llevar contabilidad
        if(getChkObligadoLlevarContabilidad().isSelected())
            persona.setObligadoLlevarContabilidad(EnumSiNo.SI.getLetra());
        else
            persona.setObligadoLlevarContabilidad(EnumSiNo.NO.getLetra());
        
        //Grabar si el contacto tiene un check box
        if(getChkContacto().isSelected())
            persona.setContactoCliente(EnumSiNo.SI.getLetra());
        else
            persona.setContactoCliente(EnumSiNo.NO.getLetra());
        
        if(!getTxtPorcentajeComision().getText().isEmpty())
        {
            persona.setContactoClientePorcentaje(new BigDecimal(getTxtPorcentajeComision().getText()));
        }
        
        
    }

    @Override
    public void eliminar() {
        Boolean confirmacion = DialogoCodefac.dialogoPregunta("Alerta", "Está seguro que desea eliminar el cliente?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (confirmacion) {
            try {
                personaService.eliminar(persona);
                System.out.println("Se elimino correctamente");
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //@Override
    public String getNombre() {
        return "Cliente";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public void imprimir() {
        /*
        URL path = RecursoCodefac.JASPER_CRM.getResourceURL("reporteEjemplo.jrxml");
        System.out.println(session.getUsuario().getClave());
        Map parameters = new HashMap();
        parameters.put("nombre", "carlos");
        parameters.put("subreporte", "C:\\Users\\Carlos\\Documents\\GitHub\\codefac-lite\\workspace\\recursos\\src\\main\\resources\\reportes\\crm\\");

        List<DataEjemploReporte> data = new ArrayList<DataEjemploReporte>();
        data.add(new DataEjemploReporte("carlos", "1"));
        data.add(new DataEjemploReporte("pedro", "2"));
        ReporteCodefac.generarReporteInternalFramePlantilla(path.getPath(), parameters, data, panelPadre, "Reporte Nuevos ");*/
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#eclientes";
    }

    @Override
    public void actualizar() {
        cargarTipoClientes();
    }
/*
    @Override
    public void buscar() throws ExcepcionCodefacLite {
        //this.panelPadre.crearVentanaCodefac(new ClienteModel(),true);
        ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);        
        buscarDialogoModel.setVisible(true);        
        Persona personaTmp = (Persona) buscarDialogoModel.getResultado();

        if (personaTmp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        
        persona=personaTmp;
        cargarDatos();
    
    }
    */

    @validacionPersonalizadaAnotacion(errorTitulo = "Formato de identificacion")
    public boolean validarIdentificacionSegunOpcionEstablecida() {
        boolean verificador = false;
        Persona.TipoIdentificacionEnum tipoIdentificacion=(Persona.TipoIdentificacionEnum) getjComboIdentificacion().getSelectedItem();
        
        if(tipoIdentificacion==null)
            return false;
        
        switch (tipoIdentificacion) {
            case RUC:
                verificador = UtilidadesJuridicas.validarTodosRuc(getjTextIdentificacion().getText());
                break;
            case CEDULA:
                verificador = UtilidadesJuridicas.validarCedula(getjTextIdentificacion().getText());
                break;
            case PASAPORTE:
                verificador = true;
                break;
            case CLIENTE_FINAL:
                verificador=(getjTextIdentificacion().getText().equals("9999999999999"))?true:false;
                break;
            default :
                verificador = false;
                break;
        }
        return verificador;
    }
    
    private void cargarDatos() {
        getjTextNombres().setText(persona.getNombres());
        getjTextApellidos().setText(persona.getApellidos());
        getjTextNombreSocial().setText(persona.getRazonSocial());
        getjTextIdentificacion().setText("" + persona.getIdentificacion());
        getTxtNombreLegal().setText(persona.getNombreLegal());
        //persona.setNombreLegal(getjTextNombreSocial().getText());
        
        
        //getjComboIdentificacion().setSelectedIndex(comboIdentificacion(persona.getIdentificacion()));
        getjComboIdentificacion().setSelectedItem(persona.getTipoIdentificacionEnum());
        //getjComboIdentificacion().setSelectedItem(persona.getSriTipoIdentificacion());
        getjTextIdentificacion().setText(persona.getIdentificacion());
        getjComboTipoCliente().setSelectedItem(persona.getTipCliente());
        getjTextAreaDireccion().setText(persona.getDireccion());
        getjTextTelefono().setText(persona.getTelefonoConvencional());
        getjTextExtension().setText(persona.getExtensionTelefono());
        getjTextCelular().setText(persona.getTelefonoCelular());
        getjTextCorreo().setText(persona.getCorreoElectronico());
        getCmbEstado().setSelectedItem(ClienteEnumEstado.getEnum(persona.getEstado()));
        getCmbTipoOperador().setSelectedItem(persona.getTipoEnum());
        getCmbNacionalidad().setSelectedItem(persona.getNacionalidad());
        getCmbFormaPagoDefecto().setSelectedItem(persona.getSriFormaPago());
        
        //Seleccionar si es obligado a llevar contabilidad
        if(persona.getObligadoLlevarContabilidadEnum()!=null)
        {
            if(persona.getObligadoLlevarContabilidadEnum().equals(EnumSiNo.SI))
            {
                getChkObligadoLlevarContabilidad().setSelected(true);
            }
            else
            {
                getChkObligadoLlevarContabilidad().setSelected(false);
            }
        }
        
        
        if(persona.getContactoCliente()!=null)
        {
            if(persona.getContactoClientesEnum().equals(EnumSiNo.SI))
            {
                getChkContacto().setSelected(true);
            }
            else
            {
                getChkContacto().setSelected(false);
            }
        }
        else
        {
            getChkContacto().setSelected(false);
        }
        
        getTxtPorcentajeComision().setText((persona.getContactoClientePorcentaje()!=null)?persona.getContactoClientePorcentaje().toString():"");
        

        System.out.println("Datos cargados ");
    }

    @validacionPersonalizadaAnotacion(errorTitulo = "formato otra validacion incorrecto")
    public boolean validarOtro() {
        return true;
    }


    @Override
    public void limpiar() {
        try {
            persona = new Persona();
            /**
             * Seleccionando valores por defecto que se deben seleccionar
             * despues de limpiar
             *
             * @author carlos
             */

            SriIdentificacionServiceIf service = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
            SriIdentificacion id;
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo", "05");
            SriIdentificacion identificacion = service.obtenerPorMap(parametros).get(0);
            getjComboIdentificacion().setSelectedItem(identificacion);

            getjComboTipoCliente().setSelectedIndex(0);
            getCmbTipoOperador().setSelectedItem(operadorNegocioDefault);
            getjTextExtension().setText("0");
            
            getCmbFormaPagoDefecto().setSelectedIndex(0);

            //Setear el valor por defecto
            getCmbEstado().setSelectedItem(ClienteEnumEstado.ACTIVO);
            
            getTxtPorcentajeComision().setText("0");
            getChkContacto().setSelected(false);
            this.razonSocial = "";
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        getCmbNacionalidad().setSelectedIndex(52);

    }

    /**
     * Cargar los tipos de clientes de la base de datos
     */
    private void cargarTipoClientes() {
        /**
         * Cargar los valores por defecto de las identificaciones
         */
        //SriServiceIf servicioSri = ServiceFactory.getFactory().getSriServiceIf();
        //identificaciones = servicioSri.obtenerIdentificaciones(SriIdentificacion.CLIENTE);
        getjComboIdentificacion().removeAllItems();
        for (Persona.TipoIdentificacionEnum tipoIdentificacion : Persona.TipoIdentificacionEnum.values()) {
            getjComboIdentificacion().addItem(tipoIdentificacion);
        }
    }

    @Override
    public Persona getResult() throws ExcepcionCodefacLite {

        try {
            grabar();
            return persona;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void cargarDatosIniciales() {
        getPnlSms().setControlador(this);
        operadorNegocioDefault=OperadorNegocioEnum.CLIENTE;
        /**
         * Cargando los estados por defecto
         */
        getCmbEstado().removeAllItems();
        for (ClienteEnumEstado enumerador : ClienteEnumEstado.values()) {
            getCmbEstado().addItem(enumerador);
        }
        
        
    }

    private boolean prevalidar() {
        if (getCmbEstado().getSelectedItem().equals(ClienteEnumEstado.ELIMINADO)) {
            DialogoCodefac.mensaje("Advertencia", "Si desea eliminar el cliente seleccione el boton de eliminar,seleccione otro estado", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }

        return true;
    }

    @Override
    public void iniciar() {
        try {
            /**
             * Cargar los datos por defecto de los tipos de operadores
             */
            getCmbTipoOperador().removeAllItems();
            OperadorNegocioEnum list[] = OperadorNegocioEnum.values();
            for (OperadorNegocioEnum operadorNegocioEnum : list) {
                getCmbTipoOperador().addItem(operadorNegocioEnum);
            }
            
            try {
                List<Nacionalidad> nacion = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
                getCmbNacionalidad().removeAllItems();
                for (Nacionalidad n : nacion) {
                    getCmbNacionalidad().addItem(n);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Cargar las formas de pago vigentes en el SRI
            List<SriFormaPago> formasPago=ServiceFactory.getFactory().getSriServiceIf().obtenerFormasPagoActivo();
            getCmbFormaPagoDefecto().removeAllItems();
            for (SriFormaPago formaPago : formasPago)
            {
                getCmbFormaPagoDefecto().addItem(formaPago);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addListenerCombos() {
        
         
        getjComboIdentificacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Persona.TipoIdentificacionEnum tipoIdentificacionEnum=(Persona.TipoIdentificacionEnum) getjComboIdentificacion().getSelectedItem();
                //opcionIdentificacion = Integer.parseInt(((SriIdentificacion) getjComboIdentificacion().getSelectedItem()).getCodigo());
                
                //Validario comonente cuando sea diferente de vacio dependiendo la opcion de identificacion
                if (!getjTextIdentificacion().getText().equals("")) {
                    panelPadre.validarPorGrupo(ValidacionCodefacAnotacion.GRUPO_FORMULARIO, NOMBRE_VALIDADOR_IDENTIFICACION);
                }
                
                //Habilitar o deshabilitar razon social y nombre legal dependiendo si elige cedula o ruc
                //SriIdentificacion sriIdentificacion=(SriIdentificacion) getjComboIdentificacion().getSelectedItem();
                
                // Verifico que si el codigo es igual a la cedula desahabilito esos 2 datos
                if(tipoIdentificacionEnum!=null && tipoIdentificacionEnum.equals(tipoIdentificacionEnum.CEDULA))
                {
                    getTxtNombreLegal().setText("");
                    getTxtNombreLegal().setEnabled(false);
                    getjTextNombreSocial().setEnabled(false);
                }
                else //Verifico si es cualquier otro dato solicito los 2 campos de nombre legal y razon social
                {
                    getTxtNombreLegal().setEnabled(true);
                    getjTextNombreSocial().setEnabled(true);
                }
            }
        });
        
        getCmbTipoOperador().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperadorNegocioEnum enumerador=(OperadorNegocioEnum) getCmbTipoOperador().getSelectedItem();
                
                if(enumerador!=null)
                {
                    if(enumerador.equals(OperadorNegocioEnum.CLIENTE))
                    {
                        getLblOligadoLlevarContabilidad().setVisible(false);
                        getChkObligadoLlevarContabilidad().setVisible(false);
                    }
                    else
                    {
                        getLblOligadoLlevarContabilidad().setVisible(true);
                        getChkObligadoLlevarContabilidad().setVisible(true);                    
                    }
                }
            }
        });

    }
    
    public boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    public void addListenerTexts() {
        
                
        getjTextNombres().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {

            }

            @Override
            public void focusLost(FocusEvent evt) {
                construirNombreSocial();
            }
        });

        getjTextApellidos().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {

            }

            @Override
            public void focusLost(FocusEvent evt) {
                construirNombreSocial();
            }
        });
    }
    
    private void construirNombreSocial()
    {
        getjTextNombreSocial().setText(getjTextApellidos().getText() + " " + getjTextNombres().getText());
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisos = new ArrayList<String>();
        permisos.add(Perfil.PERFIl_ADMINISTRADOR);
        permisos.add(Perfil.PERFIl_OPERADOR);
        return permisos;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {        
        
        //Cargar el tipo de operador de negocio : cliente , proveedor, ambos          
        OperadorNegocioEnum operadorNegocioEnum=(OperadorNegocioEnum) parametros[0];
        operadorNegocioDefault=operadorNegocioEnum;
        getCmbTipoOperador().setSelectedItem(operadorNegocioEnum);
        
        String identificacion=(String) parametros[1];
        getjTextIdentificacion().setText(identificacion);
        
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        persona=(Persona) entidad;
        cargarDatos();
    }

    private void addListenerBotones() {
        
    }

    //Lista de componentes que se deben excluir de las validaciona automaticas
    private void excluirValidaciones() {
       listaExclusionComponentes.add(getPnlSms().getTxtMensajeTexto());
    }
    
    @Override
    public boolean getValidacionEnvioSms() {
        if (!estadoFormulario.equals(ESTADO_EDITAR)) {
            DialogoCodefac.mensaje("Error", "Porfavor seleccione un cliente para enviar el mensaje", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        return true;
    }

    public Map<PlantillaSmsEnum.EtiquetaEnum, String> getPlantillaTags() {
        Map<PlantillaSmsEnum.EtiquetaEnum, String> mapParametros = new HashMap<PlantillaSmsEnum.EtiquetaEnum, String>();
        mapParametros.put(PlantillaSmsEnum.EtiquetaEnum.EMPRESA, session.getEmpresa().getNombreLegal());
        return mapParametros;
    }
    
    @Override
    public List<ComponenteEnvioSmsData> getDataSms() {                
        ComponenteEnvioSmsData componenteEnvioSmsData=new ComponenteEnvioSmsData(persona.getTelefonoCelular(),getPlantillaTags());
        List<ComponenteEnvioSmsData> dataList=new ArrayList<ComponenteEnvioSmsData>();
        dataList.add(componenteEnvioSmsData);
        return dataList;
    }

    @Override
    public VentanaEnum getVentanaEnum() {
        return VentanaEnum.CLIENTE;
    }

    @Override
    public EnvioMensajesCallBackInterface getInterfaceCallback() {
        return null;
    }
    
}
