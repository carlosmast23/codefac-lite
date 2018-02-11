/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class ClienteImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobante{

    private FacturacionModel facturacionModel;
    
    
    
    public ClienteImplComprobante(FacturacionModel facturacionModel) throws RemoteException {
        this.facturacionModel=facturacionModel;
    }
    
    @Override
    public void termino(String novedad) throws RemoteException {
        facturacionModel.getTxtDescripcion().setText("Terminado:"+novedad);
        JOptionPane.showMessageDialog(null,"Mensaje del servidor"+novedad);
    }
    
}
