package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.BodegaBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.BodegaPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodegaModel extends BodegaPanel implements DialogInterfacePanel<Bodega> {

    private Bodega bodega;
    private BodegaService bodegaService;

    public BodegaModel() {
        bodegaService = new BodegaService();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            bodega = new Bodega();
            setearValoresBodega(bodega);
            bodegaService.grabar(bodega);
            DialogoCodefac.mensaje("Datos correctos", "El Producto se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        }
    }

    private void setearValoresBodega(Bodega bodega) {
        bodega.setNombre(getTxtNombre().getText());
        bodega.setDescripcion(getTxtDescripcion().getText());
        bodega.setEncargado(getTxtEncargado().getText());
        bodega.setImagenPath(getTxtFoto().getText());
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
    public void editar() throws ExcepcionCodefacLite {
        setearValoresBodega(bodega);
        bodegaService.editar(bodega);
        DialogoCodefac.mensaje("Datos correctos", "La bodega se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar la bodega?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion bodega");
            }
            bodegaService.eliminar(bodega);
            DialogoCodefac.mensaje("Datos correctos", "La bodega se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        }
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
        BodegaBusquedaDialogo bodegaBusquedaDialogo = new BodegaBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(bodegaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        bodega = (Bodega) buscarDialogoModel.getResultado();

        if (bodega == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar bodega vacio");
        }

        getTxtNombre().setText(bodega.getNombre());
        getTxtDescripcion().setText(bodega.getDescripcion());
        getTxtEncargado().setText(bodega.getEncargado());
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
    public Bodega getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
