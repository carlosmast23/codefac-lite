/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.DescuentoAcademicoFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.DescuentoAcademicoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DescuentoAcademicoService extends ServiceAbstract<DescuentoAcademico,DescuentoAcademicoFacade> implements DescuentoAcademicoServiceIf{

    public DescuentoAcademicoService() throws RemoteException {
        super(DescuentoAcademicoFacade.class);
    }

    @Override
    public DescuentoAcademico grabar(DescuentoAcademico entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //buscar el periodo actual activo pqara grabar
                PeriodoService periodoService=new PeriodoService();
                Periodo periodoActivo=periodoService.obtenerUnicoPeriodoActivo();
                
                entity.setPeriodo(periodoActivo);
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    @Override
    public void eliminar(DescuentoAcademico entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
    
    
    
}
