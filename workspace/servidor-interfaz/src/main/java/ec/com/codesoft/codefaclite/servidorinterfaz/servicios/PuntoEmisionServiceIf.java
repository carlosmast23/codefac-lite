/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
 ;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface PuntoEmisionServiceIf extends ServiceAbstractIf<PuntoEmision>{
    public abstract List<PuntoEmision> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException  ;
    public abstract List<PuntoEmision> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException  ;
    public abstract PuntoEmision obtenerPorCodigo(Integer codigo,Sucursal sucursal) throws ServicioCodefacException  ;
    //public abstract Boolean validarPuntoEmisionSinTransaccion(PuntoEmision puntoEmision) throws ServicioCodefacException  ;
}
