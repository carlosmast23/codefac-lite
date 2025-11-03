/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.NivelFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class NivelService extends ServiceAbstract<Nivel, NivelFacade> implements NivelServiceIf {

    private NivelFacade nivelFacade;

    public NivelService() throws RemoteException {
        super(NivelFacade.class);
        this.nivelFacade = new NivelFacade();
    }

    public Nivel obtenerNivelPorNombreYEstado(String nombre, GeneralEnumEstado estado) throws RemoteException {
        try {
            return (Nivel) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> parametros = new HashMap<String, Object>();
                    parametros.put("nombre", nombre);
                    parametros.put("estado", estado.getEstado());
                    List<Nivel> niveles = getFacade().findByMap(parametros, entityManager);
                    if (niveles.size() > 0) {
                        return niveles.get(0);
                    }
                    return null;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NivelService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    /*
    public Nivel grabar(Nivel n) throws ServicioCodefacException {
        try {
            nivelFacade.create(n);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos aulas");
        }
        return n;
    }*/


    public void eliminar(Nivel n) {
        
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    n.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    nivelFacade.editData(n,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NivelService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Nivel> obtenerNivelesActivos() throws RemoteException {

        try {
            return (List<Nivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NivelService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

}
