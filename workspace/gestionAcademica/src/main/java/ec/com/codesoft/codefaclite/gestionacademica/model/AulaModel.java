/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.AulaBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.AulaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.AulaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AulaServiceIf;
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
public class AulaModel extends AulaPanel {

    private Aula aula;
    private AulaServiceIf aulaService;

    public AulaModel() {
        aulaService = ServiceFactory.getFactory().getAulaServiceIf();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        getCmbEstado().removeAllItems();
        for (AulaEnumEstado enumerador : AulaEnumEstado.values()) {
            getCmbEstado().addItem(enumerador);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresAula(aula);
            aula = aulaService.grabar(aula);
            DialogoCodefac.mensaje("Datos correctos", "El aula se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar aula modelo");
        } catch (RemoteException ex) {
            Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresAula(Aula aula) {
        aula.setNombre(getTxtNombre().getText());
        aula.setUbicacion(getTxtUbicacion().getText());
        aula.setCapacidad(Integer.parseInt(getTxtCapacidad().getText()));
        aula.setEstado(((AulaEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresAula(aula);
            aulaService.editar(aula);
            DialogoCodefac.mensaje("Datos correctos", "El aula se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el aula?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion aula");
                }
                aulaService.eliminar(aula);
                DialogoCodefac.mensaje("Datos correctos", "El aula se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
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
        AulaBusquedaDialogo aulaBusquedaDialogo = new AulaBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        aula = (Aula) buscarDialogoModel.getResultado();
        if (aula == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar aula vacio");
        }
        getTxtNombre().setText(aula.getNombre());
        getTxtUbicacion().setText(aula.getUbicacion());
        getTxtCapacidad().setText(aula.getCapacidad().toString());
    }

    @Override
    public void limpiar() {
        aula = new Aula();
    }

    @Override
    public String getNombre() {
        return "Aula";
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

}
