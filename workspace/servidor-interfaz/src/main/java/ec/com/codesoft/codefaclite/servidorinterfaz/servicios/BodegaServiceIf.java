/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
 
 ;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface BodegaServiceIf extends ServiceAbstractIf<Bodega>
{
    public Bodega grabar(Bodega b) throws ServicioCodefacException   ;
    public void editar(Bodega b) throws ServicioCodefacException   ;
    
    
    public Bodega buscarPorNombre(String nombre) throws ServicioCodefacException   ;
    
    public List<Bodega> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException   ;
    public Bodega obtenerBodegaVenta(Sucursal sucursal) throws ServicioCodefacException   ;
}
