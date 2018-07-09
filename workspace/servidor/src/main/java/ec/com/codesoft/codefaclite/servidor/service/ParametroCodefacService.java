/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.ParametroCodefacFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class ParametroCodefacService extends ServiceAbstract<ParametroCodefac,ParametroCodefacFacade> implements ParametroCodefacServiceIf{
    private ParametroCodefacFacade parametroCodefacFacade;

    public ParametroCodefacService() throws RemoteException {
        super(ParametroCodefacFacade.class);
        parametroCodefacFacade=new ParametroCodefacFacade();
    }
    
    public Map<String ,ParametroCodefac> getParametrosMap() throws java.rmi.RemoteException
    {
            Map<String ,ParametroCodefac> parametrosCodefacMap=new HashMap<String,ParametroCodefac>();
        
        List<ParametroCodefac> parametros=parametroCodefacFacade.findAll();
        for (ParametroCodefac parametro : parametros) {
            parametrosCodefacMap.put(parametro.getNombre(),parametro);
        }
        
        return parametrosCodefacMap;
    }
    
    public ParametroCodefac getParametroByNombre(String nombre) throws java.rmi.RemoteException
    {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("nombre",nombre);
        List<ParametroCodefac> parametroCodefacList=parametroCodefacFacade.findByMap(map);
        if(parametroCodefacList!=null && parametroCodefacList.size()>0 )
            return ((List<ParametroCodefac>) (parametroCodefacFacade.findByMap(map))).get(0);
        else
            return null;
    }
    
    /**
     * Edita todos los parametros 
     * @param parametro 
     */
    public void editarParametros(Map<String ,ParametroCodefac> parametro) throws java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                for (Map.Entry<String, ParametroCodefac> entry : parametro.entrySet()) {
                    //String key = entry.getKey();
                    ParametroCodefac value = entry.getValue();
                    
                    if(value.getId()==null) //Si no existe el dato lo grabo
                    {
                        entityManager.persist(value);
                    }
                    else //Si existe el dato solo lo edito
                    {
                        entityManager.merge(value);
                    }
                    
                }
            }
        });
        /*
           for (Map.Entry<String, ParametroCodefac> entry : parametro.entrySet()) {
            String key = entry.getKey();
            ParametroCodefac value = entry.getValue();
            parametroCodefacFacade.edit(value);
        }*/
    }
    
    public ParametroCodefac grabar(ParametroCodefac parametro) throws java.rmi.RemoteException
    {
        try {
            parametroCodefacFacade.create(parametro);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parametro;
    }
    
    public List<ParametroCodefac> buscarParametrosPorMap(Map<String,Object> map) throws java.rmi.RemoteException
    {
        return parametroCodefacFacade.findByMap(map);
    }
    
    
    
}
