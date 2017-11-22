/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.facade.ParametroCodefacFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ParametroCodefacService {
    private ParametroCodefacFacade parametroCodefacFacade;

    public ParametroCodefacService() {
        parametroCodefacFacade=new ParametroCodefacFacade();
    }
    
    public Map<String ,ParametroCodefac> getParametrosMap()
    {
        Map<String ,ParametroCodefac> parametrosCodefacMap=new HashMap<String,ParametroCodefac>();
        
        List<ParametroCodefac> parametros=parametroCodefacFacade.findAll();
        for (ParametroCodefac parametro : parametros) {
            parametrosCodefacMap.put(parametro.getNombre(),parametro);
        }
        
        return parametrosCodefacMap;
    }
    
    /**
     * Edita todos los parametros 
     * @param parametro 
     */
    public void editarParametros(Map<String ,ParametroCodefac> parametro)
    {
           for (Map.Entry<String, ParametroCodefac> entry : parametro.entrySet()) {
            String key = entry.getKey();
            ParametroCodefac value = entry.getValue();
            parametroCodefacFacade.edit(value);
        }
    }
    
    public void grabar(ParametroCodefac parametro)
    {
        parametroCodefacFacade.create(parametro);
    }
    
    public List<ParametroCodefac> buscarParametrosPorMap(Map<String,Object> map)
    {
        return parametroCodefacFacade.findByMap(map);
    }
    
}
