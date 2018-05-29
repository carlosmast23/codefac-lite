/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface OrdenTrabajoServiceIf extends ServiceAbstractIf<OrdenTrabajo>
{
    public void grabarOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException,java.rmi.RemoteException; 
    public List<OrdenTrabajo> consultaDialogo(String param,int limiteMinimo,int limiteMaximo) throws java.rmi.RemoteException;
    public void editar(OrdenTrabajo ordenTrabajo) throws java.rmi.RemoteException;
    public List<OrdenTrabajo> obtenerTodos()throws java.rmi.RemoteException;
    public void eliminar(OrdenTrabajo ordenTrabajo) throws java.rmi.RemoteException;
}