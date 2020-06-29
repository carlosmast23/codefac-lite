/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Carlos
 * @param <Entity>
 */
public interface ServiceAbstractIf<Entity> {
        //private T t;
    public List<Entity> obtenerTodos();
    
    public Entity grabar(Entity entity) throws ServicioCodefacException;
    
    public void editar(Entity entity) throws ServicioCodefacException;
    
    public void eliminar(Entity entity) throws ServicioCodefacException;
    
    public Entity buscarPorId(Object primaryKey) ;
    
    //public List<Entity> obtenerPorMap(Map<String,Object> parametros)   ,ServicioCodefacException;
}
