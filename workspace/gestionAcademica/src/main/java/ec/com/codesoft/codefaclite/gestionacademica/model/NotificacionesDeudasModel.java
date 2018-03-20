/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NotificacionesDeudasPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class NotificacionesDeudasModel extends NotificacionesDeudasPanel{

    /**
     * Listado donde van a estar el listado de todos los rubros para enviar al correo
     */
    private List<RubrosNivel> listaRubros;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarCombos();
        listenerCombos();
        listenerBotones();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
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
        this.listaRubros=new ArrayList<RubrosNivel>();
    }

    @Override
    public String getNombre() {
        return "Notificacion Deudas";
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

    private void iniciarCombos() {
        
        try {
            //Cargar los periodos activos
            List<Periodo> periodosActivos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            getCmbPeriodo().removeAllItems();
            for (Periodo periodosActivo : periodosActivos) {
                getCmbPeriodo().addItem(periodosActivo);
            }
            
            //Cargar los catalogos de los combos
            getCmbTipoRubroGrupo().removeAllItems();
            getCmbTipoRubroPorMes().removeAllItems();
            getCmbTipoRubroPorRubro().removeAllItems();

            List<CatalogoProducto> tipoRubros = ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerTodos();

            for (CatalogoProducto catalogo : tipoRubros) {
                getCmbTipoRubroGrupo().addItem(catalogo);
                getCmbTipoRubroPorMes().addItem(catalogo);
                getCmbTipoRubroPorRubro().addItem(catalogo);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerCombos() {
        getCmbTipoRubroPorRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
                    CatalogoProducto catalogoProducto=(CatalogoProducto) getCmbTipoRubroPorRubro().getSelectedItem();
                    
                    Map<String,Object> mapParametros=new HashMap<String,Object>();
                    mapParametros.put("periodo", periodo);
                    mapParametros.put("catalogoProducto", catalogoProducto);
                    
                    List<RubrosNivel> listaRubros=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
                    
                    //Cargar la lista de rubros segun la seleccion del combo
                    getCmbRubro().removeAllItems();
                    for (RubrosNivel rubro : listaRubros) {
                        getCmbRubro().addItem(rubro);
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    /**
     * Construye una tabla con los datos segun los rubros ingresados
     */
    private void construirTablaRubros()
    {
        String[] titulo={"Nombre","Valor"};
        DefaultTableModel modelo=new DefaultTableModel(titulo,0);
        
        for (RubrosNivel rubroNivel : listaRubros) {
            String[] fila={rubroNivel.getNombre(),rubroNivel.getValor().toString()};
            modelo.addRow(fila);
        }
        
        getTblRubros().setModel(modelo);
        
    }

    private void listenerBotones() {
        getBtnAgregarPorRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubrosNivel rubroNivel= (RubrosNivel) getCmbRubro().getSelectedItem();
                
                listaRubros.add(rubroNivel);
                construirTablaRubros();                        
            }
        });
        
        getBtnAgregarPorGrupo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
                    CatalogoProducto tipoRubro = (CatalogoProducto) getCmbTipoRubroGrupo().getSelectedItem();
                    
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("periodo", tipoRubro);
                    mapParametros.put("catalogoProducto", tipoRubro);
                    
                    List<RubrosNivel> rubros=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
                    listaRubros.addAll(rubros);
                    construirTablaRubros();
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    
    
}
