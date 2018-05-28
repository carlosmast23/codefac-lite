/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoService extends ServiceAbstract<Presupuesto, PresupuestoFacade> implements PresupuestoServiceIf
{
    private PresupuestoFacade presupuestoFacade;

    public PresupuestoService() throws RemoteException 
    {
        super(PresupuestoFacade.class);
        this.presupuestoFacade = new PresupuestoFacade();
    }
    
    public Presupuesto grabar(Presupuesto p) throws ServicioCodefacException
    {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        try {
            entityManager.persist(p);
            transaccion.commit();
        } catch (PersistenceException ex)
        {
            if(transaccion.isActive())
            {
                transaccion.rollback();
            }
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje());
            }            
            //throw  new ServicioCodefacException("Error sql desconocido");
        }
        return p;
    }
    
    public void editar(Presupuesto p)
    {
        presupuestoFacade.edit(p);
    }
    
    public void eliminar(Presupuesto p)
    {
        p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        editar(p);
    }
    
    public List<Presupuesto> buscar()
    {
        return presupuestoFacade.findAll();
    }
    
    
}
