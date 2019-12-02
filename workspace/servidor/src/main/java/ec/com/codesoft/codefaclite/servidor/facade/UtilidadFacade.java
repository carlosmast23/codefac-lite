/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadFacade extends AbstractFacade<Object>
{

    public UtilidadFacade() {
        super(Object.class);
    }
    
    public Integer obtenerCodigoMaximo(String prefijo,String nombreTabla)
    {
        //String query=" Select ";
        String queryString = "SELECT MAX(u.codigo) FROM :nombreTabla u WHERE u.codigo LIKE ?1 ";
        queryString=queryString.replace(":nombreTabla",nombreTabla);
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,prefijo+"%");
        
        String codigo=null;
        try
        {
            codigo=query.getSingleResult().toString();
        }catch(NoResultException nre)
        {
            nre.printStackTrace();
        }
        
        Integer resultado=1;
        if(codigo!=null)
        {
            resultado=Integer.parseInt(codigo.replace(prefijo,""));
            resultado++;
        }
        
        return resultado;
    }
}
