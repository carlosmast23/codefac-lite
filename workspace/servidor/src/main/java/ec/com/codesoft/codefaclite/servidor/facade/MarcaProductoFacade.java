/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MarcaProductoFacade extends AbstractFacade<MarcaProducto>{

    public MarcaProductoFacade() 
    {
        super(MarcaProducto.class);
    }
    
    public List<MarcaProducto> obtenerActivosPorEmpresa(Empresa empresa)
    {
        
        String queryStr=" SELECT m FROM MarcaProducto m WHERE m.estado=?1 and m.empresa=?2 ORDER BY m.nombre";
        Query query=getEntityManager().createQuery(queryStr);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, empresa);
        return query.getResultList();
    }
}
