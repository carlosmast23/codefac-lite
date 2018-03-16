/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.RubroPlantillaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class RubroPlantillaModel extends RubroPlantillaPanel{
    
    /**
     * Referencia para trabajar con el objeto rubro plantilla
     */
    private RubroPlantilla rubroPlantilla;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
        iniciarListener();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearVariables();
            ServiceFactory.getFactory().getRubroPlantillaServiceIf().grabar(rubroPlantilla);
            DialogoCodefac.mensaje("Correcto","Los dato fueron grabados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","No se pueden grabar los datos", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","No existe comunicación con el servior",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        limpiarVariables();
        cargarDatosTablas();
    }

    @Override
    public String getNombre() {
        return "Rubros Plantilla";
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

    private void cargarValoresIniciales() {
        try {
            List<Periodo> periodos= ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            getCmbPeriodo().removeAll();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
            
            //Cargar los productos
            List<CatalogoProducto> productos=ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerTodos();
            for (CatalogoProducto producto : productos) {
                getCmbRubro().addItem(producto);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarListener() {
        getBtnAgregarRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface() {
                    @Override
                    public void updateInterface(Object entity) {
                        CatalogoProducto catalogo = (CatalogoProducto) entity;
                        getCmbRubro().addItem(catalogo);
                        getCmbRubro().setSelectedItem(catalogo);

                    }
                }, VentanaEnum.CATALOGO_PRODUCTO, false);
            }
        });
    }

    private void limpiarVariables() {
        this.rubroPlantilla=new RubroPlantilla();
        this.rubroPlantilla.setDetalles(new ArrayList<RubroPlantillaEstudiante>());
    }

    private void setearVariables() {
        Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
        this.rubroPlantilla.setPeriodo(periodo);
        
        this.rubroPlantilla.setNombre(getTxtNombre().getText());
        this.rubroPlantilla.setCatalogoProducto((CatalogoProducto) getCmbRubro().getSelectedItem());
        this.rubroPlantilla.setValor(new BigDecimal(getTxtValor().getText()));
        this.rubroPlantilla.setDiasCredito(new Integer(getTxtDiasCredito().getText()));
        
        this.rubroPlantilla.setEnero(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setFebrero(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setMarzo(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setAbril(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setMayo(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setJunio(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setJulio(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setAgosto(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setSeptiembre(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setOctubre(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setNoviembre(EnumSiNo.NO.getLetra());
        this.rubroPlantilla.setDiciembre(EnumSiNo.NO.getLetra());
        
    }

    private void cargarDatosTablas() {
        List<RubroPlantillaEstudiante> detalles=this.rubroPlantilla.getDetalles();
        for (RubroPlantillaEstudiante detalle : detalles) {
            
        }
    }
    
    private List<EstudianteInscrito> obtenerEstudiantesNoRegistrados()
    {
        //ServiceFactory.getFactory().getEstudianteIn
        return null;
    }
    
}
