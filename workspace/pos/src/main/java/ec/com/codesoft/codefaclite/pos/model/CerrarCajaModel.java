/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CerrarCajaModel extends CajaSessionModel
{

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        super.cicloVida=false;
        super.validacionDatosIngresados=false;
        getjTextValorApertura().setEnabled(false);
    }
    
    
    private void cargarDatos()
    {
        try {
            //TODO: Metodo donde cargar los datos de la session activa para poder cerrar la cja
            // Por el momento solo escojo cualquier dato ingresado
            List<CajaSession> resultado = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerTodos();
            if(resultado.size()>0)
            {
                cargarDatosPantalla(resultado);
            }            
        } catch (RemoteException ex) {
            Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}
