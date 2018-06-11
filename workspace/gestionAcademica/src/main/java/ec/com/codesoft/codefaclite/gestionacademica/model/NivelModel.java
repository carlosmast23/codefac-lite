/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.NivelBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NivelPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class NivelModel extends NivelPanel {

    private Nivel nivel;
    private NivelServiceIf nivelService;

    public NivelModel() {
        nivelService = ServiceFactory.getFactory().getNivelServiceIf();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void iniciarCombos() {
        getCmbEstado().removeAllItems();
        for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
            getCmbEstado().addItem(enumerador);
        }

        //combo para niveles
        try {
            Nivel n1 = new Nivel();
            n1.setNombre("Ninguno");
            getCmbNivelPosterior().removeAllItems();
            List<Nivel> nivelList = nivelService.obtenerTodos();
            getCmbNivelPosterior().addItem(n1);
            for (Nivel niv : nivelList) {
                getCmbNivelPosterior().addItem(niv);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(NivelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresNivel(nivel);
            nivel = nivelService.grabar(nivel);
            DialogoCodefac.mensaje("Datos correctos", "El nivel se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar nivel modelo");
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error", "Error de comunicación con el servidor , nivel", DialogoCodefac.MENSAJE_ADVERTENCIA);
            Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresNivel(Nivel nivel) {
        nivel.setNombre(getTxtNombre().getText());
        nivel.setOrden(Integer.parseInt(getTxtOrden().getText()));
        nivel.setDescripcion(getTxtDescripcion().getText());
        nivel.setEstado(((GeneralEnumEstado) getCmbEstado().getSelectedItem()).getEstado());

        if (getCmbNivelPosterior().getSelectedItem() != null) {
            nivel = (Nivel) getCmbNivelPosterior().getSelectedItem();
            nivel.setNivelPosterior(nivel);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresNivel(nivel);
            nivelService.editar(nivel);
            DialogoCodefac.mensaje("Datos correctos", "El nivel se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(NivelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NivelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el nivel?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion nivel");
                }
                nivelService.eliminar(nivel);
                DialogoCodefac.mensaje("Datos correctos", "El nivel se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        NivelBusquedaDialogo nivelBusquedaDialogo = new NivelBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(nivelBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Nivel nivelTemp = (Nivel) buscarDialogoModel.getResultado();
        if (nivelTemp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar aula vacio");
        } else {
            nivel = nivelTemp;
        }

        getTxtNombre().setText(nivel.getNombre());
        getTxtOrden().setText(nivel.getOrden().toString());
        getTxtDescripcion().setText(nivel.getDescripcion());
        getCmbNivelPosterior().setSelectedItem(nivel.getNivelPosterior());

    }

    @Override
    public void limpiar() {
        nivel = new Nivel();
        //iniciarCombos();
        iniciarCombos();
        getCmbNivelPosterior().setSelectedIndex(0);
    }

//    @Override
    public String getNombre() {
        return "Nivel";
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
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
