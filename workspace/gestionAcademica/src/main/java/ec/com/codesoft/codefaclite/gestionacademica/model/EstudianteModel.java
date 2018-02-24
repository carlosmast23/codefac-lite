/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.EstudiantePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstudianteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class EstudianteModel extends EstudiantePanel {

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
        getCmbEstado().removeAllItems();
        for (EstudianteEnumEstado enumerador : EstudianteEnumEstado.values()) {
            getCmbEstado().addItem(enumerador);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresEstudiante(estudiante);
            estudiante = estudianteService.grabar(estudiante);
            DialogoCodefac.mensaje("Datos correctos", "El registro de estudiante se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar aula modelo");
        } catch (RemoteException ex) {
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

        estudiante.setEstado(((GeneroEnum) getCmbEstado().getSelectedItem()).getEstado());
        estudiante.setEstado(((EstudianteEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
        
        
           if (getDateFechaNacimiento().getDate() != null) {
            fechaNacimiento = new Date(getDateFechaNacimiento().getDate().getTime());
            fechanacimiento = dateFormat.format(getDateFechaNacimiento().getDate());
            estudiante.setFechaNacimiento(fechaNacimiento);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
