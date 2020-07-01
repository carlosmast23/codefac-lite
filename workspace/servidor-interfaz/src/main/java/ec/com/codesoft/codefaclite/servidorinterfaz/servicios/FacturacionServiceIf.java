/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import java.math.BigDecimal;
 
 ;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface FacturacionServiceIf extends ServiceAbstractIf<Factura>
{
    public void editar(Factura factura) throws ServicioCodefacException  ;
    public Factura grabar(Factura factura) throws ServicioCodefacException   ,ServicioCodefacException; 
    public Factura grabar(Factura factura,Prestamo prestamo,CarteraParametro carteraParametro) throws    ServicioCodefacException;
    public Factura grabar(Factura factura,Empleado empleado) throws ServicioCodefacException   ,ServicioCodefacException; 
    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion,Empresa empresa)   ;
    public List<Factura> consultaDialogo(String param,int limiteMinimo,int limiteMaximo)   ;
    //public void editar(Factura factura)   ;
    public void editarFactura(Factura factura) throws ServicioCodefacException   ,ServicioCodefacException; 
    public List<Factura> obtenerTodos()  ;
    //public List<Factura> obtenerFacturasReporte(Persona persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa)   ;
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal)   ;
    public List<Factura> obtenerFacturasActivas()   ;
    //public String getPreimpresoSiguiente()   ;
    public void eliminarFactura(Factura factura)   throws ServicioCodefacException;
    public Long obtenerSecuencialProformas(Empresa empresa);    
    public Factura grabarProforma(Factura proforma) throws   ServicioCodefacException;
    public void eliminarProforma(Factura factura)   throws ServicioCodefacException;
    public List<Factura> consultarProformasReporte(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,GeneralEnumEstado estado) throws ServicioCodefacException;
    public Factura buscarPorPremimpresoYEstado(Integer secuencial,BigDecimal puntoEstablecimiento,Integer puntoEmision,ComprobanteEntity.ComprobanteEnumEstado estadoEnum);      
    public void grabarCartera(Factura factura) throws    ServicioCodefacException;
    
    public Factura grabarLiquidacionCompra(Factura liquidacionCompra) throws   ServicioCodefacException;
    public ReferenciaDetalleFacturaRespuesta obtenerReferenciaDetalleFactura(TipoDocumentoEnum tipoDocumentoEnum,Long referenciaId)   throws ServicioCodefacException;
    public Map<Factura,BigDecimal> obtenerCostoFacturas(List<Factura> facturas) throws    ServicioCodefacException;
    public Factura editarProforma(Factura proforma) throws   ServicioCodefacException;
    
}
