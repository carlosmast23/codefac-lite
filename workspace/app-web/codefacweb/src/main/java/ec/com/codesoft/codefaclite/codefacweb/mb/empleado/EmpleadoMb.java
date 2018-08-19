/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.empleado;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
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
public class EmpleadoMb extends GeneralAbstractMb implements Serializable{
    private Empleado empleado;
    
    @PostConstruct
    public void init()
    {
        empleado=new Empleado();
    }
/*
    @Override
    public void grabar() {
        System.out.println("llamando al metodo grabar");
    }

    @Override
    public void editar() {
        System.out.println("llamando al metodo editar");
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    @Override
    public String linkAyuda() {
        System.out.println("mandando ayuda");
        //return "http://www.cf.codesoft-ec.com/ayuda";
        return "http://www.al-code.com/";
    }
    
    public void ayuda()
    {
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    
    
}
