/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.DescuentoAcademicoBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.DescuentoAcademicoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.math.BigDecimal;
 ;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DescuentoAcademicoModel extends DescuentoAcademicoPanel {

    private DescuentoAcademico descuentoAcademico;

    @Override
    public void iniciar() throws ExcepcionCodefacLite   {
        descuentoAcademico = new DescuentoAcademico();
        UtilidadesComboBox.llenarComboBox(getCmbTipo(), DescuentoAcademico.TipoEnum.values());

        getCmbTipo().setSelectedItem(DescuentoAcademico.TipoEnum.INDIVIDUAL);

    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite   {

    }

    @Override
    public void grabar() throws ExcepcionCodefacLite   {
        try {
            setearDatos();
            ServiceFactory.getFactory().getDescuentoAcademicoServiceIf().grabar(descuentoAcademico);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }

    }

    @Override
    public void editar() throws ExcepcionCodefacLite   {
        try {
            setearDatos();
            ServiceFactory.getFactory().getDescuentoAcademicoServiceIf().editar(descuentoAcademico);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }

    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite   {

        if (!DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO)) 
        {
            return ;
        }

        try {
            ServiceFactory.getFactory().getDescuentoAcademicoServiceIf().eliminar(descuentoAcademico);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite   {

    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite   {

    }

    @Override
    public void limpiar() {

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
    public InterfaceModelFind obtenerDialogoBusqueda() {
        try {
            Periodo periodo = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerUnicoPeriodoActivo();
            return new DescuentoAcademicoBusqueda(periodo);
        } catch (Exception ex) {
            Logger.getLogger(DescuentoAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        descuentoAcademico = (DescuentoAcademico) entidad;
        getTxtNombre().setText(descuentoAcademico.getNombre());
        getTxtPorcentaje().setText(descuentoAcademico.getPorcentaje().toString());
        getCmbTipo().setSelectedItem(descuentoAcademico.getTipoEnum());

    }

    private void setearDatos() {
        descuentoAcademico.setNombre(getTxtNombre().getText());
        descuentoAcademico.setPorcentaje(new BigDecimal(getTxtPorcentaje().getText()));
        descuentoAcademico.setTipoEnum((DescuentoAcademico.TipoEnum) getCmbTipo().getSelectedItem());
    }

}
