/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.rmi.Remote;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface CompraServiceIf extends ServiceAbstractIf<Compra>
{
    public void grabarCompra(Compra compra) throws ServicioCodefacException,java.rmi.RemoteException;
    public List<Compra> obtenerTodos() throws java.rmi.RemoteException;
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum de, TipoDocumentoEnum tde) throws java.rmi.RemoteException;
}
