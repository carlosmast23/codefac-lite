/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadesService extends UnicastRemoteObject implements UtilidadesServiceIf {

    public UtilidadesService() throws RemoteException {
    }

    public List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo) throws java.rmi.RemoteException {
        return AbstractFacade.findStaticDialog(query, map, limiteMinimo, limiteMaximo);
    }

    public Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map) throws java.rmi.RemoteException {
        return AbstractFacade.findCountStaticDialog(query, map);
    }

    @Override
    public boolean verificarConexionesServidor() throws RemoteException {
        int numeroConexionesPermitidas = UtilidadesServidor.cantidadUsuarios;
        try {
            String hostCliente = RemoteServer.getClientHost();

            if (UtilidadesServidor.hostConectados.contains(hostCliente)) {
                return true; //Existe en la lista de usuarios permitidos                
            } else if (UtilidadesServidor.hostConectados.size() < numeroConexionesPermitidas) {
                UtilidadesServidor.hostConectados.add(hostCliente);
                if (UtilidadesServidor.monitorUpdate != null) {
                    
                    String[] stockArr = new String[UtilidadesServidor.hostConectados.size()];
                    stockArr = UtilidadesServidor.hostConectados.toArray(stockArr);
                    UtilidadesServidor.monitorUpdate.actualizarNumeroConexiones(stockArr);

                }
                return true;
            }

            //Si ya supera el numero de conexiones permitidas retorno falso
            return false;
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public TipoLicenciaEnum getTipoLicencia() throws RemoteException {
        return UtilidadesServidor.tipoLicenciaEnum;
    }
    
    @Override
    public List<ModuloCodefacEnum> getModulosSistema()  throws RemoteException
    {
        return UtilidadesServidor.modulosMap;
    }
    

}
