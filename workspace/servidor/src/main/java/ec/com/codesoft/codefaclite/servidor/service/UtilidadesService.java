/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class UtilidadesService extends UnicastRemoteObject implements UtilidadesServiceIf{

    public UtilidadesService() throws RemoteException {
    }       
    
    public  List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo) throws java.rmi.RemoteException {
        return AbstractFacade.findStaticDialog(query, map, limiteMinimo, limiteMaximo);
    }

    public  Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map) throws java.rmi.RemoteException {
        return AbstractFacade.findCountStaticDialog(query, map);
    }
}
