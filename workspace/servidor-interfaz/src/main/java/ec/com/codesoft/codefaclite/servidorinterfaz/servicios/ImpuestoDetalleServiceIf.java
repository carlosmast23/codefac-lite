/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
// 
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ImpuestoDetalleServiceIf extends ServiceAbstractIf<ImpuestoDetalle>
{
    //public void grabar(ImpuestoDetalle i) throws ServicioCodefacException   ;
    //public void editar(ImpuestoDetalle i)   ;
    public void eliminar(ImpuestoDetalle i) ;
    public List<ImpuestoDetalle> buscarImpuestoDetallePorMap(Map<String,Object> map) ;
    public List<ImpuestoDetalle> obtenerIvaVigente();
    public ImpuestoDetalle buscarPorTarifa(Integer tarifa) throws ServicioCodefacException;
}
