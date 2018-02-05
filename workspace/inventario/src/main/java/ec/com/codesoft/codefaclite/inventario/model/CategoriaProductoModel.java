/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.panel.CategoriaProductoPanel;
import ec.com.codesoft.codefaclite.servidor.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.CategoriaProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.CategoriaProductoService;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo
 */
public class CategoriaProductoModel extends CategoriaProductoPanel implements DialogInterfacePanel<CategoriaProducto> {

    private CategoriaProducto catProducto;
    private CategoriaProductoService catProductoService;

    public CategoriaProductoModel() {
        catProductoService = new CategoriaProductoService();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            catProducto = new CategoriaProducto();
            setearValoresCatProducto(catProducto);
            catProductoService.grabar(catProducto);
            DialogoCodefac.mensaje("Datos correctos", "La categoria del producto se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        }
    }

    private void setearValoresCatProducto(CategoriaProducto cat) {
        cat.setEstado(CategoriaProductoEnumEstado.ACTIVO.getEstado());
        cat.setNombre(getTxtNombre().getText());
        cat.setDescripcion(getTxtDescripcion().getText());
        //cat.setImagenPath(getTxtFoto().getText());
    }

    public void editar() throws ExcepcionCodefacLite {
        setearValoresCatProducto(catProducto);
        catProductoService.editar(catProducto);
        DialogoCodefac.mensaje("Datos correctos", "La categoria del producto se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
    }

    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar la bodega?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion bodega");
            }
            catProductoService.eliminar(catProducto);
            DialogoCodefac.mensaje("Datos correctos", "La categoria del producto se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        }
    }
    
    @Override
    public CategoriaProducto getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
