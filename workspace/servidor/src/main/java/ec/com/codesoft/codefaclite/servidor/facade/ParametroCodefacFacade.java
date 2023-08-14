/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class ParametroCodefacFacade extends AbstractFacade<ParametroCodefac>
{
    
    public ParametroCodefacFacade() 
    {
        super(ParametroCodefac.class);
    }
    
    public List<ParametroCodefac> getParametrosMapByEmpresa(Empresa empresa) 
    {
        //ParametroCodefac parametroCodefac;
        //parametroCodefac.getEmpresa()
        String queryString = "SELECT p FROM ParametroCodefac p WHERE p.empresa=?1 ";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,empresa); 
        query.setFlushMode(FlushModeType.COMMIT);
        return query.getResultList();
    }
    
    public ParametroCodefac getParametroByNombreFacade(String nombre,Empresa empresa) throws java.rmi.RemoteException
    {
        try
        {
            //Este Order o hago por si existen datos repetidos seleccionar el último guardado        
            String queryString = "SELECT p FROM ParametroCodefac p WHERE p.empresa=?2 AND p.nombre=?1 ORDER BY p.id desc ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,nombre); 
            query.setParameter(2,empresa); 

            List<ParametroCodefac> resultadoList=query.getResultList();
            if(resultadoList.size()>0)
            {
                return resultadoList.get(0);
            }
        } 
        catch (PersistenceException e) {
            Throwable rootCause = e.getCause();
            if (rootCause instanceof NullPointerException) {
                // Manejar la NullPointerException aquí
                // Puedes imprimir un mensaje de error, registrar la excepción, etc.
                //System.err.println();
                Logger.getLogger(ParametroCodefacFacade.class.getName()).log(Level.INFO,"Se ha generado una NullPointerException: " + rootCause.getMessage());
                Logger.getLogger(ParametroCodefacFacade.class.getName()).log(Level.INFO,"Al Realizar la consulta getParametroByNombreFacade con el siguiente parametro " + nombre);
            } else {
                // Manejar otras causas de la PersistenceException aquí
                // Puedes imprimir un mensaje de error, registrar la excepción, etc.                
                Logger.getLogger(ParametroCodefacFacade.class.getName()).log(Level.INFO,"Se ha generado una PersistenceException: " + e.getMessage());
            }
        }
        
        return null;
    }
    
}

