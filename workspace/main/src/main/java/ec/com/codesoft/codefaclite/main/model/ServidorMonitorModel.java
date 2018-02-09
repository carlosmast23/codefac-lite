/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.ServidorMonitorPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ServidorMonitorModel extends ServidorMonitorPanel{

            
    public ServidorMonitorModel() {
        listenerBotones();
    }

    private void listenerBotones() {
        getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String host=RemoteServer.getClientHost();
                    System.out.println(host);
                } catch (ServerNotActiveException ex) {
                    Logger.getLogger(ServidorMonitorModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }
        });
    }
    
}
