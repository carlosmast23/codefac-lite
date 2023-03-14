/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.Banco;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface BancoServiceIf extends ServiceAbstractIf<Banco>{
    
    public Banco grabar(Banco entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public Banco editar(Banco entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public List<Banco> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
}
