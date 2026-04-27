package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Variante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.VarianteFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.VarianteServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VarianteService extends ServiceAbstract<Variante, VarianteFacade> implements VarianteServiceIf {

    private VarianteFacade varianteFacade;

    public VarianteService() throws RemoteException {
        super(VarianteFacade.class);
        this.varianteFacade = new VarianteFacade();
    }

    @Override
    public Variante grabar(Variante variante) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                variante.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                variante.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                entityManager.persist(variante);
            }
        });
        return variante;
    }

    @Override
    public void editar(Variante variante) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                entityManager.merge(variante);
            }
        });
    }

    @Override
    public void eliminar(Variante variante) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                variante.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(variante);
            }
        });
    }

    @Override
    public List<Variante> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException {
        return (List<Variante>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta(EntityManager em) throws ServicioCodefacException, RemoteException {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                parametros.put("empresa", empresa);
                return getFacade().findByMap(parametros, em);
            }
        });
    }
}
