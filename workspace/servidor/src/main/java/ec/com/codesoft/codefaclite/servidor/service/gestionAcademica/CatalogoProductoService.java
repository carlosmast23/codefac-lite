/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.CatalogoProductoFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class CatalogoProductoService extends ServiceAbstract<CatalogoProducto,CatalogoProductoFacade> implements CatalogoProductoServiceIf{
            
    public CatalogoProductoService() throws RemoteException {
        super(CatalogoProductoFacade.class);
    }
    
    public List<CatalogoProducto> obtenerPorModulo(ModuloCodefacEnum modulo) throws RemoteException
    {
        try {
            return (List<CatalogoProducto>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("moduloCod", modulo.getCodigo());
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CatalogoProductoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    public CatalogoProducto obtenerPorNombre(String nombre) throws RemoteException {

        try {
            return (CatalogoProducto) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    CatalogoProducto cp;
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("nombre", nombre);
                    List<CatalogoProducto> catalogos = getFacade().findByMap(mapParametros,entityManager);
                    if (catalogos.size() > 0) {
                        return catalogos.get(0);
                    }
                    return null;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CatalogoProductoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
}
