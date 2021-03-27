/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AbrirCajaModel extends CajaSessionModel
{

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar();
        getjTextValorCierre().setEnabled(false);
    }
    
}
