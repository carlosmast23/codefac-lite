/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ParametroCodefacServiceIf extends ServiceAbstractIf<ParametroCodefac>
{
       
    public Map<String ,ParametroCodefac> getParametrosMap(Empresa empresaIf) throws java.rmi.RemoteException;
    
    public ParametroCodefac getParametroByNombre(String nombre,Empresa empresa) throws java.rmi.RemoteException;
    
    /**
     * Edita todos los parametros 
     * @param parametro 
     */
    public void editarParametros(Map<String ,ParametroCodefac> parametro) throws java.rmi.RemoteException;
    
    public void editarParametros(List<ParametroCodefac> parametro) throws java.rmi.RemoteException;
    
    //public ParametroCodefac grabar(ParametroCodefac parametro) throws java.rmi.RemoteException;
    
    public List<ParametroCodefac> buscarParametrosPorMap(Map<String,Object> map) throws java.rmi.RemoteException;
    
    public void grabarOEditar(ParametroCodefac parametro) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public void grabarOEditar(Empresa empresa,String parametroNombre,String valor) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public void procesoBloqueadoPrueba() throws java.rmi.RemoteException, ServicioCodefacException;
}
