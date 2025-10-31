/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface FacturaDetalleServiceIf extends ServiceAbstractIf<FacturaDetalle> {
    public Object getReferenciaDetalle(FacturaDetalle facturaDetalle,EntityManager entityManager) throws ServicioCodefacException,java.rmi.RemoteException;
    public List<FacturaDetalle> buscarPorFactura(Factura factura,EntityManager entityManager) throws ServicioCodefacException,java.rmi.RemoteException;
    public Object getReferenciaDetalle(FacturaDetalle facturaDetalle) throws ServicioCodefacException,java.rmi.RemoteException;
}
