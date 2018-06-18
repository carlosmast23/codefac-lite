/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.GestionEmpleadosPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class GestionEmpleadosModel extends GestionEmpleadosPanel
{
    private Empleado empleado;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        empleado = new Empleado();
        iniciarCombos();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void limpiar() {
        limpiarCampos();
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

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void iniciarCombos()
    {
        try {
            getCmbDepartamento().removeAllItems();
            List<Departamento> departamentos = ServiceFactory.getFactory().getDepartamentoServiceIf().obtenerTodos();
            for (Departamento departamento : departamentos) {
                getCmbDepartamento().addItem(departamento);
            }
            
            getCmbEstado().removeAllItems();
            for(GeneralEnumEstado generalEnumEstado : GeneralEnumEstado.values())
            {
                getCmbEstado().addItem(generalEnumEstado);
            }
            
            getCmbNacionalidad().removeAllItems();
            List<Nacionalidad> nacionalidades = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
            for (Nacionalidad nacionalidad : nacionalidades) {
                getCmbNacionalidad().addItem(nacionalidad);
            }
            
            getCmbSexo().removeAllItems();
            for(GeneroEnum genero : GeneroEnum.values())
            {
                getCmbSexo().addItem(genero);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionEmpleadosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpiarCampos()
    {
        getTxtApellidos().setText("");
        getTxtCargo().setText("");
        getTxtCelular().setText("");
        getTxtCorreoElectronico().setText("");
        getTxtIdentificacion().setText("");
        getTxtNombres().setText("");
        getTxtTelefono().setText("");
        getTxtAreaDireccion().setText("");
    }
}
