/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface CarteraCruceServiceIf extends ServiceAbstractIf<CarteraCruce>{
    public List<CarteraCruce>  buscarPorCarteraDetalle(CarteraDetalle carteraDetalle) throws ServicioCodefacException, java.rmi.RemoteException;
}
