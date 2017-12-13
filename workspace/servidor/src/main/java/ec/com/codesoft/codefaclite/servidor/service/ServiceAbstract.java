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
public abstract class ServiceAbstract<T>
{
    private AbstractFacade<T> facade;
    private Class<T> clase;

    public ServiceAbstract(Class<T> clase) {
        try {
            this.facade = (AbstractFacade<T>) clase.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //private T t;
    public List<T> obtenerTodos()
    {
        return this.facade.findAll();
    }
    
    public List<T> obtenerPorMap(Map<String,Object> parametros)
    {
        return this.facade.findByMap(parametros);
    }
}
