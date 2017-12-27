/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public abstract class ServiceAbstract<Entity,Facade>
{
    private AbstractFacade<Entity> facade;
 
    public ServiceAbstract(Class<Facade> clase) {
        try {
            this.facade =(AbstractFacade<Entity>) clase.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //private T t;
    public List<Entity> obtenerTodos()
    {
        return this.facade.findAll();
    }
    
    public void grabar(Entity entity)
    {
       this.facade.create(entity);
    }
    
    public List<Entity> obtenerPorMap(Map<String,Object> parametros)
    {
        return this.facade.findByMap(parametros);
    }
    
    public static List<Object> consultaGeneralDialogos(String query,Map<Integer,Object> map,int limiteMinimo,int limiteMaximo)
    {
       return AbstractFacade.findStaticDialog(query,map, limiteMinimo, limiteMaximo);
    }
    
    public static Long consultaTamanioGeneralDialogos(String query,Map<Integer,Object> map)
    {
       return AbstractFacade.findCountStaticDialog(query,map);
    }
    
}
