/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.crm.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.crm.test.EjemploCrm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.SriIdentificacionService;
import ec.com.codesoft.codefaclite.servidor.service.SriService;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
public class ClienteModel extends ClienteForm implements DialogInterfacePanel<Persona> {

    /**
     * Modelo para manejar las identificaciones del sri
     */
    private DefaultComboBoxModel<SriIdentificacion> modelComboIdentificacion;
    List<SriIdentificacion> identificaciones;

    private PersonaServiceIf personaService;
    private Persona persona;
    private String razonSocial;
    private String comboIdentificacion [] = {"CEDULA","RUC","PASAPORTE","IDENTIFICACION DEL EXTERIOR","PLACA"};
    private String comboTipoCliente [] = {"CLIENTE","SUJETO RETENIDO","DESTINATARIO"};
   
    private int opcionIdentificacion = 4;

    public ClienteModel() {
        this.personaService = ServiceController.getController().getPersonaServiceIf();
        getjTextExtension().setText("0");
        this.razonSocial = "";
        cargarClientes();
        cargarDatosIniciales();        
        addListenerTexts();
        addListenerCombos();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            if (!prevalidar()) {
                //Cancela el evento guardar porque no prevalido
                throw new ExcepcionCodefacLite("Error al prevalidar");
            }
            
            persona = new Persona();
            persona.setNombres(getjTextNombres().getText());
            persona.setApellidos(getjTextApellidos().getText());
            persona.setRazonSocial(getjTextNombreSocial().getText());
            persona.setTipoIdentificacion(((SriIdentificacion) getjComboIdentificacion().getSelectedItem()).getCodigo());
            persona.setIdentificacion(getjTextIdentificacion().getText());
            persona.setTipCliente((String) getjComboTipoCliente().getSelectedItem());
            persona.setDireccion(getjTextAreaDireccion().getText());
            persona.setTelefonoConvencional(getjTextTelefono().getText());
            persona.setExtensionTelefono(getjTextExtension().getText());
            persona.setTelefonoCelular(getjTextCelular().getText());
            persona.setCorreoElectronico(getjTextCorreo().getText());
            persona.setEstado(((ClienteEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
            persona.setTipo(((OperadorNegocioEnum)getCmbTipoOperador().getSelectedItem()).getLetra());
            personaService.grabar(persona);
            DialogoCodefac.mensaje("Datos correctos", "El cliente se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            System.err.println("Se grabo correctamente");
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al prevalidar");
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            persona.setNombres(getjTextNombres().getText());
            persona.setApellidos(getjTextApellidos().getText());
            persona.setRazonSocial(getjTextNombreSocial().getText());
            persona.setTipoIdentificacion(((SriIdentificacion) getjComboIdentificacion().getSelectedItem()).getCodigo());
            persona.setIdentificacion(getjTextIdentificacion().getText());
            persona.setTipCliente((String) getjComboTipoCliente().getSelectedItem());
            persona.setDireccion(getjTextAreaDireccion().getText());
            persona.setTelefonoConvencional(getjTextTelefono().getText());
            persona.setExtensionTelefono(getjTextExtension().getText());
            persona.setTelefonoCelular(getjTextCelular().getText());
            persona.setCorreoElectronico(getjTextCorreo().getText());
            persona.setTipo(((OperadorNegocioEnum)getCmbTipoOperador().getSelectedItem()).getLetra());
            
            personaService.editar(persona);
            
            System.out.println("Se edito correctamente");
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() {
        Boolean confirmacion=DialogoCodefac.dialogoPregunta("Alerta","Está seguro que desea eliminar el cliente?",DialogoCodefac.MENSAJE_ADVERTENCIA);
        if(confirmacion)
        {
            try {
                personaService.eliminar(persona);
                System.out.println("Se elimino correctamente");
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        cargarClientes();
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        //this.panelPadre.crearVentanaCodefac(new ClienteModel(),true);
        ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        persona = (Persona) buscarDialogoModel.getResultado();

        if (persona == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        getjTextNombres().setText(persona.getNombres());
        getjTextApellidos().setText(persona.getApellidos());
        getjTextNombreSocial().setText(persona.getRazonSocial());
        getjTextIdentificacion().setText("" + persona.getIdentificacion());
        persona.setNombreLegal(getjTextNombreSocial().getText());
        getjComboIdentificacion().setSelectedIndex(comboIdentificacion(persona.getIdentificacion()));
        getjTextIdentificacion().setText(persona.getIdentificacion());
        getjComboTipoCliente().setSelectedItem(persona.getTipCliente());
        getjTextAreaDireccion().setText(persona.getDireccion());
        getjTextTelefono().setText(persona.getTelefonoConvencional());
        getjTextExtension().setText(persona.getExtensionTelefono());
        getjTextCelular().setText(persona.getTelefonoCelular());
        getjTextCorreo().setText(persona.getCorreoElectronico());
        getCmbEstado().setSelectedItem(ClienteEnumEstado.getEnum(persona.getEstado()));
        getCmbTipoOperador().setSelectedItem(persona.getTipoEnum());
        

        System.out.println("Datos cargados ");
    }

    @validacionPersonalizadaAnotacion(errorTitulo = "Formato de identificacion")
    public boolean validarIdentificacionSegunOpcionEstablecida()
    {
        boolean verificador = false;
        switch(this.opcionIdentificacion)
        {
            case 4:
                verificador = validarRUC(getjTextIdentificacion().getText());
            break;
            case 5:
                verificador = validarCedula(getjTextIdentificacion().getText());
            break;
            case 6:
                verificador = true;
            break;
            default:
                 verificador = false;
            break;
        }
        return verificador;
    }        
            
    public boolean validarCedula(String cedula)
    {          
        try {
            int suma=0;
            if(cedula.length()==9)
            {
                return false;
            }
            else
            {
                int a[]=new int [cedula.length()/2];
                int b[]=new int [(cedula.length()/2)];
                int c=0;
                int d=1;
                for (int i = 0; i < cedula.length()/2; i++)
                {
                    a[i]=Integer.parseInt(String.valueOf(cedula.charAt(c)));
                    c=c+2;
                    if (i < (cedula.length()/2)-1) 
                    {
                        b[i]=Integer.parseInt(String.valueOf(cedula.charAt(d)));
                        d=d+2;
                    }
                }

                for (int i = 0; i < a.length; i++) 
                {
                    a[i]=a[i]*2;
                    if (a[i] >9)
                    {
                        a[i]=a[i]-9;
                    }
                    suma=suma+a[i]+b[i];
                } 
                int aux=suma/10;
                int dec=(aux+1)*10;
                if ((dec - suma) == Integer.parseInt(String.valueOf(cedula.charAt(cedula.length()-1))))
                    return true;
                else
                if(suma%10==0 && cedula.charAt(cedula.length()-1)=='0')
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } catch (NumberFormatException nfe) {
            return false;
        } catch (Exception err) {
            return false;
        }
    }

    @validacionPersonalizadaAnotacion(errorTitulo = "formato otra validacion incorrecto")
    public boolean validarOtro() {
        return true;
    }

    public int comboIdentificacion(String op) {
        for (int i = 0; i < comboIdentificacion.length; i++) {
            if (op.equals(comboIdentificacion[i])) {
                return i;
            }
        }
        return 0;
    }

    public int comboTipoCliente(String op) {
        for (int i = 0; i < comboTipoCliente.length; i++) {
            if (op.equals(comboTipoCliente[i])) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void limpiar() {
        /**
         * Seleccionando valores por defecto que se deben seleccionar despues de
         * limpiar
         *
         * @author carlos
         */
               
        SriIdentificacionService service=new SriIdentificacionService();
        SriIdentificacion id;
        Map<String,Object> parametros=new HashMap<String,Object>();
        parametros.put("codigo","05");
        SriIdentificacion identificacion=service.obtenerPorMap(parametros).get(0);
        getjComboIdentificacion().setSelectedItem(identificacion);
        
        
        getjComboTipoCliente().setSelectedIndex(0);
        getCmbTipoOperador().setSelectedIndex(0);
        getjTextExtension().setText("0");

        //Setear el valor por defecto
        getCmbEstado().setSelectedItem(ClienteEnumEstado.ACTIVO);
        this.razonSocial = "";

    }

    /**
     * Cargar los tipos de clientes de la base de datos
     */
    private void cargarClientes() {
        /**
         * Cargar los valores por defecto de las identificaciones
         */
        SriService servicioSri = new SriService();
        identificaciones = servicioSri.obtenerIdentificaciones(SriIdentificacion.CLIENTE);
        getjComboIdentificacion().removeAllItems();
        for (SriIdentificacion identificacion : identificaciones) {
            if(!(identificacion.getCodigo().equals("07") || identificacion.getCodigo().equals("19")))
            {
                getjComboIdentificacion().addItem(identificacion);
            }
        }
        

    }

    @Override
    public Persona getResult() {

        try {
            grabar();
            return persona;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void cargarDatosIniciales() {
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
        /**
         * Cargar los datos por defecto de los tipos de operadores
         */
        getCmbTipoOperador().removeAllItems();
        OperadorNegocioEnum list[]= OperadorNegocioEnum.values();
        for (OperadorNegocioEnum operadorNegocioEnum : list) {
            getCmbTipoOperador().addItem(operadorNegocioEnum);        
        }
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void addListenerCombos()
    {
        getjComboIdentificacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionIdentificacion = Integer.parseInt(((SriIdentificacion)getjComboIdentificacion().getSelectedItem()).getCodigo());
                System.out.println("Info combo: " +((SriIdentificacion)getjComboIdentificacion().getSelectedItem()).getCodigo());
                //Validario comonente cuando sea diferente de vacio dependiendo la opcion de identificacion
                if(!getjTextIdentificacion().getText().equals(""))
                    panelPadre.validarPorGrupo(ValidacionCodefacAnotacion.GRUPO_FORMULARIO,NOMBRE_VALIDADOR_IDENTIFICACION);
            }
        });
        
    }       

    private boolean validarRUC(String RUC) 
    {
        System.out.println("Longitud de RUC" + RUC.length());
        if(RUC.length() == 13)
        {
            String cedula = RUC.substring(0,10);
            System.out.println("CEDULA: " + cedula);
            String ruc = RUC.substring(10,13);
            System.out.println("RUC: " + ruc);
            System.out.println("VALor cedula " +  validarCedula(cedula));
            System.out.println("VALor ruc " +  isNumeric(ruc));
            if(validarCedula(cedula) && isNumeric(ruc))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isNumeric(String cadena) 
    {
        boolean resultado;
        try
        {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }
           
    public void addListenerTexts()
    {      
        getjTextNombres().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                    
            }
            @Override
            public void focusLost(FocusEvent evt)
            {
                getjTextNombreSocial().setText(getjTextNombres().getText() + " " + getjTextApellidos().getText());
            }
        });
        
        getjTextApellidos().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                  
            }
            @Override
            public void focusLost(FocusEvent evt)
            {
                getjTextNombreSocial().setText(getjTextNombres().getText() + " " + getjTextApellidos().getText());
            }
        });
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisos=new ArrayList<String>();
        permisos.add(Perfil.PERFIl_ADMINISTRADOR);
        permisos.add(Perfil.PERFIl_OPERADOR);
        return permisos;
    }
}
