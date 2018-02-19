/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface UtilidadesServiceIf extends Remote
{
    public List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo) throws java.rmi.RemoteException;
    public Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map) throws java.rmi.RemoteException;
    public boolean verificarConexionesServidor() throws java.rmi.RemoteException;
    public TipoLicenciaEnum getTipoLicencia() throws java.rmi.RemoteException;
    public Map<ModuloCodefacEnum,Boolean> getModulosSistema()  throws RemoteException;
}
