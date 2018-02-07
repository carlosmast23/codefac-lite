/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.BodegaBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.CategoriaProductoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.CategoriaProductoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceController;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo
 */
public class CategoriaProductoModel extends CategoriaProductoPanel implements DialogInterfacePanel<CategoriaProducto> {

    private CategoriaProducto catProducto;
    private CategoriaProductoServiceIf catProductoService;

    public CategoriaProductoModel() {
        catProductoService = ServiceController.getController().getCategoriaProductoServiceIf();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            catProducto = new CategoriaProducto();
            setearValoresCatProducto(catProducto);
            try {
                catProductoService.grabar(catProducto);
            } catch (RemoteException ex) {
                Logger.getLogger(CategoriaProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        try {
            setearValoresCatProducto(catProducto);
            catProductoService.editar(catProducto);
            DialogoCodefac.mensaje("Datos correctos", "La categoria del producto se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(CategoriaProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar la categoria del producto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion bodega");
                }
                catProductoService.eliminar(catProducto);
                DialogoCodefac.mensaje("Datos correctos", "La categoria del producto se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(CategoriaProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        CategoriaProductoBusquedaDialogo catProdBusquedaDialogo = new CategoriaProductoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(catProdBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        catProducto = (CategoriaProducto) buscarDialogoModel.getResultado();
        if (catProducto == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar bodega vacio");
        }
        getTxtNombre().setText(catProducto.getNombre());
        getTxtDescripcion().setText(catProducto.getDescripcion());
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
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        //permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
