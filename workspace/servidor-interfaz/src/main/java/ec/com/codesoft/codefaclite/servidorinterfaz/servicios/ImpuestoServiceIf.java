/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
// 
import java.util.List;
/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ImpuestoServiceIf extends ServiceAbstractIf<Impuesto>
{
    public Impuesto grabar(Impuesto i) throws ServicioCodefacException;
    public void editar(Impuesto i) ;
    public void eliminar(Impuesto i) ;
    public Impuesto obtenerImpuestoPorCodigo(String nombre) ;
    public Impuesto obtenerImpuestoPorVigencia(String nombre) ;
    public List<Impuesto> obtenerTodos() ;
    public List<ImpuestoDetalle> obtenerDetalle() ;
}
