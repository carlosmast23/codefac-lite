/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.ArqueoCajaFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.ArqueoCajaServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ArqueoCajaService extends ServiceAbstract<ArqueoCaja, ArqueoCajaFacade> implements ArqueoCajaServiceIf
{
    
    private ArqueoCajaFacade arqueoCajaFacade;
    
    public ArqueoCajaService() throws RemoteException {
        super(ArqueoCajaFacade.class);
        this.arqueoCajaFacade = new ArqueoCajaFacade();
    }

    @Override
    public List<ArqueoCaja> obtenerArqueoCajaPorCaja(Caja caja) throws RemoteException {
        
        try {
            return (List<ArqueoCaja>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> map = new HashMap<>();
                    map.put("cajaSession.caja", caja);
                    
                    List<ArqueoCaja> arqueoCajas = getFacade().findByMap(map,entityManager);
                    
                    if(arqueoCajas.size() > 0)
                    {
                        return arqueoCajas;
                    }
                    
                    return null;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
       
    }
    
}
