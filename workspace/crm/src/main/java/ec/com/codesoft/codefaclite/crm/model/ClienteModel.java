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
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.crm.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.crm.test.EjemploCrm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.SriService;
import java.io.IOException;
import java.io.InputStream;
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

    private PersonaService personaService;
    private Persona persona;
    private String comboIdentificacion[] = {"CEDULA", "RUC", "PASAPORTE", "IDENTIFICACION DEL EXTERIOR", "PLACA"};
    private String comboTipoCliente[] = {"CLIENTE", "SUJETO RETENIDO", "DESTINATARIO"};

    public ClienteModel() {
        this.personaService = new PersonaService();
        getjTextExtension().setText("0");
        cargarClientes();
        cargarDatosIniciales();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
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
        personaService.grabar(persona);
        DialogoCodefac.mensaje("Datos correctos", "El cliente se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        System.err.println("Se grabo correctamente");

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
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

        personaService.editar(persona);

        System.out.println("Se edito correctamente");
    }

    @Override
    public void eliminar() {
        personaService.eliminar(persona);
        System.out.println("Se elimino correctamente");
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
        String path = RecursoCodefac.JASPER_CRM.getResourcePath("reporteEjemplo.jrxml");
        System.out.println(session.getUsuario().getClave());
        Map parameters = new HashMap();
        parameters.put("nombre", "carlos");
        parameters.put("subreporte", "C:\\Users\\Carlos\\Documents\\GitHub\\codefac-lite\\workspace\\recursos\\src\\main\\resources\\reportes\\crm\\");

        List<DataEjemploReporte> data = new ArrayList<DataEjemploReporte>();
        data.add(new DataEjemploReporte("carlos", "1"));
        data.add(new DataEjemploReporte("pedro", "2"));
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Nuevos ");
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

        System.out.println("Datos cargados ");
    }

    @validacionPersonalizadaAnotacion(errorTitulo = "formato cedula incorrecto")
    public boolean validarCedula() {
        String cedula = getjTextIdentificacion().getText();
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;

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
        getjComboIdentificacion().setSelectedIndex(0);
        getjComboTipoCliente().setSelectedIndex(0);
        getjTextExtension().setText("0");

        //Setear el valor por defecto
        getCmbEstado().setSelectedItem(ClienteEnumEstado.ACTIVO);

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
            getjComboIdentificacion().addItem(identificacion);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
