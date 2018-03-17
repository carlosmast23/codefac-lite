/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroPlantillaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import javax.persistence.EntityTransaction;
import org.apache.derby.iapi.store.raw.Transaction;

/**
 *
 * @author Carlos
 */
public class RubroPlantillaService extends ServiceAbstract<RubroPlantilla,RubroPlantillaFacade> implements RubroPlantillaServiceIf{

    public RubroPlantillaService() throws RemoteException {
        super(RubroPlantillaFacade.class);
    }
    
    public void grabarConDetalles(RubroPlantilla rubroPlantilla) throws RemoteException
    {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        
        List<RubroPlantillaEstudiante> detalles = rubroPlantilla.getDetalles();

        for (RubroPlantillaEstudiante detalle : detalles) {
            //Si no tiene id se debe grabar porque no eixste
            if (detalle.getId() == null) {
                entityManager.persist(detalle);
            }
        }
        
        entityManager.persist(rubroPlantilla);
        
        transaccion.commit();

    }
    
    
    public void editarConDetalles(RubroPlantilla entity) throws java.rmi.RemoteException
    {
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        
        List<RubroPlantillaEstudiante> detalles=entity.getDetalles();
        
        for (RubroPlantillaEstudiante detalle : detalles) {
            //Si no tiene id se debe grabar porque no eixste
            if(detalle.getId()==null)
            {
                entityManager.persist(detalle);
            }
        }
        
        entityManager.merge(entity);
        
        transaccion.commit();
    }
    
}
