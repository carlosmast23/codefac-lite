/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialog;

import ec.com.codesoft.codefaclite.controlador.panel.DialogoCargando;

/**
 *
 * @author Carlos
 */
public class HiloSegundoPlano extends Thread{
    private ProcesoSegundoPlano proceso;
    private DialogoCargando dialogo;

    public HiloSegundoPlano(ProcesoSegundoPlano proceso, DialogoCargando dialogo) {
        this.proceso = proceso;
        this.dialogo = dialogo;
    }

    
    
    @Override
    public void run() {
         proceso.procesar();
         dialogo.setVisible(false);
    }
    
}
