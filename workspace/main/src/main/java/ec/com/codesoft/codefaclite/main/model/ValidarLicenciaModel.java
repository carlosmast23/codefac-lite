/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.ValidarLicenciaDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Carlos
 */
public class ValidarLicenciaModel extends ValidarLicenciaDialog{
    
    public ValidarLicenciaModel(Frame parent, boolean modal) {
        super(parent, modal);
        addListener();
    }

    private void addListener() {
        addListenerButtons();
    }

    private void addListenerButtons() {
        getBtnSalirRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        getBtnSalirVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
    }
    
    public static void main(String[] args)
    {
        ValidarLicenciaModel validar=new ValidarLicenciaModel(null,true);
        validar.setVisible(true);
    }
    
    
    
    
}
