/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraDetalleServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class CarteraDetalleService extends ServiceAbstract<CarteraDetalle,CarteraDetalleFacade> implements CarteraDetalleServiceIf{

    public CarteraDetalleService() throws RemoteException{
        super(CarteraDetalleFacade.class);
    }
    
    public List<CarteraDetalle> consultarPorcartera(Cartera cartera)
    {
        try {
            return (List<CarteraDetalle>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                          //CarteraDetalle cd;
                    //cd.getCartera()
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("cartera", cartera);
                    List<CarteraDetalle> detalles = getFacade().findByMap(mapParametros,entityManager);
                    return detalles;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CarteraDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
}
