/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.ejemplo.utilidades.rmi.UtilidadesRmi;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class RecursosService extends UnicastRemoteObject implements RecursosServiceIf
{

    public RecursosService() throws RemoteException {
    }

    @Override
    public RemoteInputStream getResourceInputStream(RecursoCodefac recurso, String file) throws RemoteException {
        try {
            InputStream input= recurso.getResourceInputStream(file);
            RemoteInputStreamServer istream =new SimpleRemoteInputStream(input);
            RemoteInputStream result = istream.export();
            return result;
            //return UtilidadesRmi.serializar(ois);
        } catch (IOException ex) {
            Logger.getLogger(RecursosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; //Si falla el servidor devuelve null
    }


    
}
