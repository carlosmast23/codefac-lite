package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.panel.BodegaPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
<<<<<<< HEAD
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import java.util.List;
import java.util.Map;
=======
>>>>>>> 474e807c474d32012b9f9bd1e0c30b47b5b4f1ff

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CodesoftDesarrollo
 */
<<<<<<< HEAD
public class BodegaModel extends BodegaPanel implements DialogInterfacePanel<Bodega> {

    private Bodega bodega;
    private BodegaService bodegaService;

    public BodegaModel() {
        bodegaService = new BodegaService();
    }
=======
public class BodegaModel extends BodegaPanel implements DialogInterfacePanel<Bodega>{

    @Override
    public Bodega getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
>>>>>>> 474e807c474d32012b9f9bd1e0c30b47b5b4f1ff
    

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

  /*@Override
    public Bodega getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    private void setearValoresBodega(Bodega bodega) {
        bodega.setNombre(getTxtNombre().getText());
        bodega.setDescripcion(getTxtDescripcion().getText());
        bodega.setEncargado(getTxtEncargado().getText());

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
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

    @Override
    public Bodega getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
