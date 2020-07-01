/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class EjemploCallBack implements Runnable{
    
    public FacturacionModel facturacionModel;

    public EjemploCallBack(FacturacionModel facturacionModel) {
        this.facturacionModel = facturacionModel;
    }
    
    public void iniciar()
    {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(1000);
                System.out.println(facturacionModel.getClass().getName());
            } catch (InterruptedException ex) {
                Logger.getLogger(EjemploCallBack.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
