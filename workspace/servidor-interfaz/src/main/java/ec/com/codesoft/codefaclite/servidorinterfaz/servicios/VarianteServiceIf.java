package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Variante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

public interface VarianteServiceIf extends ServiceAbstractIf<Variante> {

    public Variante grabar(Variante variante) throws ServicioCodefacException, RemoteException;

    public void editar(Variante variante) throws ServicioCodefacException, RemoteException;

    public void eliminar(Variante variante) throws ServicioCodefacException, RemoteException;

    public List<Variante> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
}
