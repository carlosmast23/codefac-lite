/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.EstudiantePanel;
import ec.com.codesoft.codefaclite.inventario.busqueda.RepresentanteBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import static ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha.hoy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class EstudianteModel extends EstudiantePanel {

    private Persona representante;
    private Estudiante estudiante;
    private EstudianteServiceIf estudianteService;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaNacimiento = null;
    String fechanacimiento = "";

    public EstudianteModel() {
        estudianteService = ServiceFactory.getFactory().getEstudianteServiceIf();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        getDateFechaNacimiento().setDate(hoy());

        getCmbEstado().removeAllItems();
        for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
            getCmbEstado().addItem(enumerador);
        }

        getCmbGenero().removeAllItems();
        for (GeneroEnum generoEnum : GeneroEnum.values()) {
            getCmbGenero().addItem(generoEnum);
        }

        getBtnBuscarRepresentante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RepresentanteBusquedaDialogo buscarBusquedaDialogo = new RepresentanteBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                representante = (Persona) buscarDialogo.getResultado();

                if (representante != null) {
                    String identificacion = representante.getIdentificacion();
                    String nombre = representante.getRazonSocial();
                    getTxtRepresentante().setText(identificacion + " - " + nombre);
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
        try {
            setearValoresEstudiante(estudiante);
            estudianteService.grabar(estudiante);
            DialogoCodefac.mensaje("Datos correctos", "El registro de estudiante se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar aula modelo");
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error", "Error de comunicación con el servidor , estudiantes", DialogoCodefac.MENSAJE_ADVERTENCIA);
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresEstudiante(Estudiante estudiante) {
        estudiante.setCodigoSistema(getTxtCodSistema().getText());
        estudiante.setCodigoAuxiliar(getTxtCodAuxiliar().getText());
        estudiante.setCedula(getTxtCedula().getText());
        estudiante.setEmail(getTxtCorreo().getText());
        estudiante.setNombres(getTxtNombres().getText());
        estudiante.setApellidos(getTxtApellidos().getText());
        estudiante.setTelefono(getTxtTelefono().getText());
        estudiante.setCelular(getTxtCelular().getText());
        estudiante.setDireccion(getTxtDireccion().getText());
        estudiante.setDatosAdicionales(getTxtAdicionales().getText());

        estudiante.setGenero(((GeneroEnum) getCmbGenero().getSelectedItem()).getEstado());
        estudiante.setEstado(((GeneralEnumEstado) getCmbEstado().getSelectedItem()).getEstado());

        if (getDateFechaNacimiento().getDate() != null) {
            fechaNacimiento = new Date(getDateFechaNacimiento().getDate().getTime());
            fechanacimiento = dateFormat.format(getDateFechaNacimiento().getDate());
            estudiante.setFechaNacimiento(fechaNacimiento);
        }

        estudiante.setRepresentante(representante);

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresEstudiante(estudiante);
            estudianteService.editar(estudiante);
            DialogoCodefac.mensaje("Datos correctos", "El estudiante se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el estudiante?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion aula");
                }
                estudianteService.eliminar(estudiante);
                DialogoCodefac.mensaje("Datos correctos", "El estudiante se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        EstudianteBusquedaDialogo aulaBusquedaDialogo = new EstudianteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        estudiante = (Estudiante) buscarDialogoModel.getResultado();
        if (estudiante == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar estudiante vacio");
        }
        getTxtCodSistema().setText(estudiante.getCodigoSistema());
        getTxtCodAuxiliar().setText(estudiante.getCodigoAuxiliar());
        getTxtCedula().setText(estudiante.getCedula());
        getTxtCorreo().setText(estudiante.getEmail());
        getTxtNombres().setText(estudiante.getNombres());
        getTxtApellidos().setText(estudiante.getApellidos());
        getTxtTelefono().setText(estudiante.getTelefono());
        getTxtCelular().setText(estudiante.getCelular());
        getTxtDireccion().setText(estudiante.getDireccion());
        getTxtAdicionales().setText(estudiante.getDatosAdicionales());

        getCmbGenero().setSelectedItem(estudiante.getGenero());
        getCmbEstado().setSelectedItem(estudiante.getEstado());
        getDateFechaNacimiento().setDate(estudiante.getFechaNacimiento());

        String identificacion = estudiante.getRepresentante().getIdentificacion();
        String nombre = estudiante.getRepresentante().getRazonSocial();
        getTxtRepresentante().setText(identificacion + " - " + nombre);

    }

    @Override
    public void limpiar() {
        this.estudiante = new Estudiante();

        getDateFechaNacimiento().setDate(hoy());
        getCmbEstado().setSelectedIndex(0);
        getCmbGenero().setSelectedIndex(0);

    }

    @Override
    public String getNombre() {
        return "Estudiantes";
    }

    @Override
    public String getURLAyuda() {
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
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
