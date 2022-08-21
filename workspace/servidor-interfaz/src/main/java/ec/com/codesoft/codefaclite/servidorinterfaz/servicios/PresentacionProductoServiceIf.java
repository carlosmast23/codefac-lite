/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface PresentacionProductoServiceIf extends ServiceAbstractIf<PresentacionProducto>{
    public List<PresentacionProducto> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
    public PresentacionProducto buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException;
    public PresentacionProducto grabarSinTransaccion(PresentacionProducto presentacionProducto) throws ServicioCodefacException, RemoteException;
}
