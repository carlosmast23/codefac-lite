/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
 
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ParametroCodefacServiceIf extends ServiceAbstractIf<ParametroCodefac>
{
       
    public Map<String ,ParametroCodefac> getParametrosMap(Empresa empresaIf)   ;
    
    public ParametroCodefac getParametroByNombre(String nombre,Empresa empresa)   ;
    
    /**
     * Edita todos los parametros 
     * @param parametro 
     */
    public void editarParametros(Map<String ,ParametroCodefac> parametro)   ;
    
    public void editarParametros(List<ParametroCodefac> parametro)   ;
    
    //public ParametroCodefac grabar(ParametroCodefac parametro)   ;
    
    public List<ParametroCodefac> buscarParametrosPorMap(Map<String,Object> map)   ;
    
    public void grabarOEditar(ParametroCodefac parametro)   throws ServicioCodefacException;
    
    public void grabarOEditar(Empresa empresa,String parametroNombre,String valor)    throws ServicioCodefacException;
}
