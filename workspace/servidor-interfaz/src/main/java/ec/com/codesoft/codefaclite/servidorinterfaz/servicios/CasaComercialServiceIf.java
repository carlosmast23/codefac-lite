/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CasaComercial;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface CasaComercialServiceIf extends ServiceAbstractIf<CasaComercial>{
    public List<CasaComercial> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
    public CasaComercial buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException;
    public CasaComercial grabarSinTransaccion(CasaComercial marcaProducto) throws ServicioCodefacException, RemoteException;
}
