/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
 
 ;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface AtsServiceIf    {
    public List<VentaAts> consultarVentasAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal,Empresa empresa) throws    ServicioCodefacException;
    public AtsJaxb consultarAts(Integer anio, MesEnum mes,Empresa empresa,String numeroSucursal,boolean  comprasBool, boolean  ventasBool,boolean anuladosBool) throws    ServicioCodefacException;
    
}
