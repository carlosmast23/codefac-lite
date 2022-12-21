/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoQueryEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class QueryDialog {
    public String query;
    public TipoQueryEnum tipoQuery;
    private Map<Integer,Object> parametros;

    public QueryDialog() {
        query="";
        parametros=new HashMap<>();
    }

    public QueryDialog(String query) {
        this.query = query;
        parametros=new HashMap<>();
    }
    
    
    
    public void agregarParametro(Integer alias,Object dato)
    {   
        parametros.put(alias, dato);
    }

    public Map<Integer, Object> getParametros() {
        return parametros;
    }
    

    
}
