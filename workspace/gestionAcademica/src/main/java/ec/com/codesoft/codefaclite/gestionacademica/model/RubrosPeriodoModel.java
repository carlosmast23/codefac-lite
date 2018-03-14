/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.RubrosPeriodoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoRubroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
public class RubrosPeriodoModel extends RubrosPeriodoPanel{

    /**
     * Nivel por defecto que especifica que se debe aplicar a todos los niveles
     */
    private Nivel nivelDefecto;
    
    private RubrosNivel rubrosNivel;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarVariables();
        iniciarLister();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearVariablesPantalla();
            ServiceFactory.getFactory().getRubrosNivelServiceIf().grabar(rubrosNivel);
            DialogoCodefac.mensaje("Correcto","El rubro se grabo correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubrosPeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RubrosPeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }

    @Override
    public void limpiar() {
        
    }

    @Override
    public String getNombre() {
        return "Rubros Periodo";
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

    private void iniciarLister() {
        listenerCombos();
        listenerBotones();
    }

    private void listenerCombos() {
        try {
            //TODO: Deberia solo poder cargar los periodos activos
            PeriodoServiceIf servicio= ServiceFactory.getFactory().getPeriodoServiceIf();
            List<Periodo> periodos=servicio.obtenerPeriodoActivo();            
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
            
            //Cargar los combos de los niveles
            List<Nivel> niveles=ServiceFactory.getFactory().getNivelServiceIf().obtenerTodos();
            //Ingresa un valor por defecto  para todos los rubros para referirse que va a estar disponibles para todos lo nivelss
            getCmbNivel().addItem(nivelDefecto);
            
            for (Nivel nivel : niveles) {
                getCmbNivel().addItem(nivel);
            }
            
            //Cargar los productos
            List<CatalogoProducto> productos=ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerTodos();
            for (CatalogoProducto producto : productos) {
                getCmbRubro().addItem(producto);
            }
            
            //Cargar lso tipos de rubros
            TipoRubroEnum[] tiposRubro= TipoRubroEnum.values();
            for (TipoRubroEnum tipoRubroEnum : tiposRubro) {
                getCmbTipoRubro().addItem(tipoRubroEnum);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(RubrosPeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarVariables() {
        rubrosNivel=new RubrosNivel();
        nivelDefecto=new Nivel();
        nivelDefecto.setNombre("Todos");
    }

    private void setearVariablesPantalla() {
        rubrosNivel.setNombre(getTxtNombre().getText());
        
        //Verificar si el nivel seleccionado es uno por defecto
        Nivel nivelSeleccionado=(Nivel) getCmbNivel().getSelectedItem();
        if(nivelSeleccionado.equals(nivelDefecto))
            nivelSeleccionado=null;
                
        rubrosNivel.setNivel(nivelSeleccionado);
        
        rubrosNivel.setPeriodo((Periodo) getCmbPeriodo().getSelectedItem());
        rubrosNivel.setCatalogoProducto((CatalogoProducto) getCmbRubro().getSelectedItem());
        TipoRubroEnum tipoRubroEnum= (TipoRubroEnum) getCmbTipoRubro().getSelectedItem();
        rubrosNivel.setTipoRubro(tipoRubroEnum.getLetra());
        rubrosNivel.setValor(new BigDecimal(getTxtValor().getText()));
        //rubrosNivel.setProducto(GETCMB);
    }

    private void listenerBotones() {
        getBtnAgregarRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               panelPadre.crearDialogoCodefac(new ObserverUpdateInterface() {
                   @Override
                   public void updateInterface(Object entity) {
                       CatalogoProducto catalogo=(CatalogoProducto) entity;                       
                       getCmbRubro().addItem(catalogo);
                       getCmbRubro().setSelectedItem(catalogo);
                       
                   }
               }, VentanaEnum.CATALOGO_PRODUCTO,false);
            }
        });
    }
    
}
