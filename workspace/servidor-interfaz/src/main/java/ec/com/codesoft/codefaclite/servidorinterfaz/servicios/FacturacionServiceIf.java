/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface FacturacionServiceIf extends ServiceAbstractIf<Factura>
{
    public Factura grabar(Factura factura) throws ServicioCodefacException,java.rmi.RemoteException,ServicioCodefacException; 
    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion) throws java.rmi.RemoteException;
    public List<Factura> consultaDialogo(String param,int limiteMinimo,int limiteMaximo) throws java.rmi.RemoteException;
    public void editar(Factura factura) throws java.rmi.RemoteException;
    public List<Factura> obtenerTodos()throws java.rmi.RemoteException;
    public List<Factura> obtenerFacturasReporte(Persona persona,Date fi,Date ff,String estado,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido) throws java.rmi.RemoteException;
    public List<Factura> obtenerFacturasActivas() throws java.rmi.RemoteException;
    public String getPreimpresoSiguiente() throws java.rmi.RemoteException;
    public void eliminarFactura(Factura factura) throws java.rmi.RemoteException;
    public Long obtenerSecuencialProformas() throws RemoteException;
    public Factura grabarProforma(Factura proforma) throws RemoteException;
}
