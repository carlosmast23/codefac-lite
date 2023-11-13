/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CajaFacade extends AbstractFacade<Caja>{

    public CajaFacade() 
    {
        super(Caja.class);
    }
    
    public List<Caja> buscarCajasAutorizadasParaUsuario(Usuario usuario)
    {
        try
        {
            //CajaPermiso cp;
            //cp.getCaja().setEstadoEnum(CajaEnum.ACTIVO);
            String queryString = "Select distinct(cp.caja) from CajaPermiso cp where cp.usuario = ?1 and cp.caja.estado=?2 and cp.estado=?2 ";

            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, usuario);
            query.setParameter(2, CajaEnum.ACTIVO.getEstado());
            List resultado=query.getResultList();
            return resultado;
           
        } 
        catch (NoResultException e) 
        {
            return null;
        }
        
    }
    
}
