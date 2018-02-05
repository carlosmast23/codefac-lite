/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.CategoriaProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidor.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.CategoriaProductoFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class CategoriaProductoService extends ServiceAbstract<CategoriaProducto, CategoriaProductoFacade> {

    private CategoriaProductoFacade categoriaProductoFacade;

    public CategoriaProductoService() {
        super(CategoriaProductoFacade.class);
        this.categoriaProductoFacade = new CategoriaProductoFacade();
    }
    
    public void grabar(CategoriaProducto c) throws ServicioCodefacException {
        try {
            categoriaProductoFacade.create(c);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos");
        }
    }

    public void editar(CategoriaProducto c) {
        categoriaProductoFacade.edit(c);
    }

    public void eliminar(CategoriaProducto c) {
        c.setEstado(CategoriaProductoEnumEstado.ELIMINADO.getEstado());
        categoriaProductoFacade.edit(c);
    }

}
