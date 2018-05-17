/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.DepartamentoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DepartamentoServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class DepartamentoService extends ServiceAbstract<Departamento, DepartamentoFacade> implements DepartamentoServiceIf
{
    private DepartamentoFacade departamentoFacade;
    
    public DepartamentoService() throws RemoteException
    {
        super(DepartamentoFacade.class);
        this.departamentoFacade = new DepartamentoFacade();
    }
    
    @Override
     public Departamento grabar(Departamento d) throws ServicioCodefacException {
        try {
            departamentoFacade.create(d);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(DepartamentoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    @Override
    public void editar(Departamento d) {
        departamentoFacade.edit(d);
    }

    @Override
    public void eliminar(Departamento d) {
  
        d.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        departamentoFacade.edit(d);
    }

    public List<Departamento> buscar()
    {
        return departamentoFacade.findAll();
    } 
}
