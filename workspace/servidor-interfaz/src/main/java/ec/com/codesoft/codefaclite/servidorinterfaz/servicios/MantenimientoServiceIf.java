/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
//import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public interface MantenimientoServiceIf extends ServiceAbstractIf<Mantenimiento>{    
    public Mantenimiento grabar(Mantenimiento mesa,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public Mantenimiento editar(Mantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;    
    public List<Mantenimiento> obtenerTodosActivos(Empresa empresa)  throws ServicioCodefacException, RemoteException;
}