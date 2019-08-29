/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.BodegaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class BodegaService extends ServiceAbstract<Bodega, BodegaFacade> implements BodegaServiceIf{

    private BodegaFacade bodegaFacade;

    public BodegaService() throws RemoteException {
        super(BodegaFacade.class);
        this.bodegaFacade = new BodegaFacade();
    }

    /*
    public Bodega grabar(Bodega b) throws ServicioCodefacException {
        try {
            bodegaFacade.create(b);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos");
        }
        return b;
    }
*/

    public void editar(Bodega b) {
        bodegaFacade.edit(b);
    }

    public void eliminar(Bodega b) {
        b.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        bodegaFacade.edit(b);
    }
    
    public Bodega buscarPorNombre(String nombre) throws ServicioCodefacException,RemoteException
    {
        Bodega bodega;
        //bodega.getEstado(); //FALTA FILTRAR
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nombre", nombre);
                return getFacade().findByMap(mapParametros);
            }
        });
        
        if(bodegas.size()>0)
        {
            return bodegas.get(0);
        }
        
        return null;
    }
    
    public List<Bodega> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException,RemoteException
    {        
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
               
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParametros);
            }
        });
        return bodegas;
    }

}
