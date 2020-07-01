/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
 
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface CategoriaProductoServiceIf extends ServiceAbstractIf<CategoriaProducto>
{
    public CategoriaProducto grabar(CategoriaProducto c) throws ServicioCodefacException  ;
    public void editar(CategoriaProducto c)   ;
    public void eliminar(CategoriaProducto c)   ;
    
    public List<CategoriaProducto> obtenerTodosPorEmpresa(Empresa empresa)   ;
    public CategoriaProducto buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException   ;
    
}
