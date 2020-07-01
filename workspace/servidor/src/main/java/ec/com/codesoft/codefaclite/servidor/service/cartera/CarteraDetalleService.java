/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraDetalleServiceIf;
 ;

/**
 *
 * @author Carlos
 */
public class CarteraDetalleService extends ServiceAbstract<CarteraDetalle,CarteraDetalleFacade> implements CarteraDetalleServiceIf{

    public CarteraDetalleService()   {
        super(CarteraDetalleFacade.class);
    }
    
}
