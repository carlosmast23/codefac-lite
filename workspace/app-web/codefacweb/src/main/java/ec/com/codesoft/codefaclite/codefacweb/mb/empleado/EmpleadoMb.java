/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.empleado;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class EmpleadoMb extends GeneralAbstractMb implements Serializable {

    private Empleado empleado;

    @PostConstruct
    public void init() {
        empleado = new Empleado();
    }

    @Override
    public String linkAyuda() {
        System.out.println("mandando ayuda");
        //return "http://www.cf.codesoft-ec.com/ayuda";
        return "http://www.al-code.com/";
    }

    public void ayuda() {
        System.out.println("Si funciona esto si veo la luz");
    }

    @Override
    public void grabar() {
        try {
            ServiceFactory.getFactory().getEmpleadoServiceIf().grabar(empleado);
            System.out.println("Empleado guardado");
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EmpleadoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EmpleadoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buscar() {

    }

    @Override
    public void cargarBusqueda(Object obj) {
        this.empleado = (Empleado) obj;
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new EmpleadoBusquedaDialogo();
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * METODOS GET AND SET
     * @return 
     */

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
