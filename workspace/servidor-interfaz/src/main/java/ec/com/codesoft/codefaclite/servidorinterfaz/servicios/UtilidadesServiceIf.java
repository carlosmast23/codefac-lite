/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
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
    /**
     * Metodo que me permite sincronizar con la persistencia el objecto actual
     * @param entity
     * @return
     * @throws java.rmi.RemoteException 
     */
    public Object mergeEntity(Object entity) throws java.rmi.RemoteException;
    public List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo) throws java.rmi.RemoteException;
    public Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map) throws java.rmi.RemoteException;
    public boolean verificarConexionesServidor() throws java.rmi.RemoteException;
    public TipoLicenciaEnum getTipoLicencia() throws java.rmi.RemoteException;
    public List<ModuloCodefacEnum> getModulosSistema()  throws RemoteException;
    /**
     * Me devuelve un objeto session con algunos datos preconstruidos
     * @return
     * @throws RemoteException 
     */
    public SessionCodefac getSessionPreConstruido()  throws RemoteException;
    
}
