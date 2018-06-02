/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.PerfilBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.PerfilPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;

/**
 *
 * @author Carlos
 */
public class PerfilModel extends PerfilPanel{
    
    /**
     * Entidad con la voy a trabajar para grabar editar y consultar los perfiles
     */
    private Perfil perfil;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListener();
        //Cargar los modulos disponibles
        ModuloCodefacEnum[] modulos= ModuloCodefacEnum.values();
        getCmbModulo().removeAllItems();
        
        for (ModuloCodefacEnum modulo :modulos) 
        {
            getCmbModulo().addItem(modulo);
        }
        
        //Cargar las categorias disponibles
        CategoriaMenuEnum[] categorias= CategoriaMenuEnum.values();
        getCmbCategoria().removeAllItems();
        
        for (CategoriaMenuEnum categoria : categorias) {
            getCmbCategoria().addItem(categoria);
        }
        
        cargarVentanasSeleccionadas();
        
    }
    
    private void cargarVentanasSeleccionadas()
    {
            //Cargar las ventanas por categoria
        ModuloCodefacEnum modulo=(ModuloCodefacEnum) getCmbModulo().getSelectedItem();
        CategoriaMenuEnum categoria=(CategoriaMenuEnum) getCmbCategoria().getSelectedItem();
        List<VentanaEnum> ventanas= VentanaEnum.getVentanaByModuloAndCategoria(modulo,categoria);
        
        getCmbVentana().removeAllItems();
        for (VentanaEnum ventana : ventanas) {
            getCmbVentana().addItem(ventana);
        }
    
    }
    
    private void eliminarItemsCombo(JComboBox combo)
    {
        for (int i = combo.getItemCount() - 1; i >= 0; i--) {
            combo.removeItemAt(i);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresPantalla();
            PerfilServiceIf perfilServiceIf=ServiceFactory.getFactory().getPerfilServicioIf();
            perfilServiceIf.grabar(perfil);
            DialogoCodefac.mensaje("Correcto","El perfil se grabo correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","No se pueden grabar los datos", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error con el servidor", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresPantalla();
            PerfilServiceIf perfilServiceIf=ServiceFactory.getFactory().getPerfilServicioIf();
            perfilServiceIf.editar(perfil);
            DialogoCodefac.mensaje("Correcto","El perfil se edito correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error con el servidor", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        
        Boolean respuesta=DialogoCodefac.dialogoPregunta("Pregunta","Esta seguro que desea eliminar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
        if(!respuesta)
        {
            throw new ExcepcionCodefacLite("Cancelado eliminar");                        
        }
        
        try {
            Map<String,Object> mapParametros=new HashMap<String,Object>();
            mapParametros.put("perfil",perfil);            
            List<PerfilUsuario> perfilesUsuario=ServiceFactory.getFactory().getPerfilUsuarioServiceIf().obtenerPorMap(mapParametros);
            //Solo eliminar si ningun usuario tiene activo este perfil
            if(perfilesUsuario.size()==0)
            {
                ServiceFactory.getFactory().getPerfilServicioIf().eliminar(perfil);
                DialogoCodefac.mensaje("Correcto","El perfil fue eliminado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            }
            else
            {
                DialogoCodefac.mensaje("Error","Este perfil siendo esta siendo usado por los usuarios\n Para eliminar quite el rol de los usuarios que esten usando este perfil ", DialogoCodefac.MENSAJE_INCORRECTO);
                throw new ExcepcionCodefacLite("Cancelado eliminar");
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PerfilModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void imprimir() {
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        PerfilBusquedaDialogo perfilBusquedaDialogo=new PerfilBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(perfilBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Perfil perfilTemp = (Perfil) buscarDialogoModel.getResultado();

        if (perfilTemp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        else
        {
            this.perfil=perfilTemp;
        }
        
        getTxtNombrePerfil().setText(this.perfil.getNombre());
        construirTabla();
    }

    @Override
    public void limpiar() {
        this.perfil=new Perfil();        
        limpiarPantalla();
    }

//    @Override
    public String getNombre() {
        return "Perfiles";
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

    private void agregarListener() {
        
        getCmbModulo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanasSeleccionadas();
            }
        });
        
        getCmbCategoria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanasSeleccionadas();
            }
        });
        
       getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perfil.addPermisoVentana(getPermisoAgregar());
                construirTabla();
            }
        });
       
       getBtnQuitar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getTblDatos().getSelectedRow();
                perfil.getVentanasPermisos().remove(fila);
                construirTabla();
            }
        });
    }
    
    private void construirTabla()
    {
       String[] titulo={"Nombre","Grabar","Editar","Buscar","Imprimir","Eliminar"};
       DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
       
       //Verificar si no existen datos en el perfil no carga nada
       if (perfil != null && perfil.getVentanasPermisos() != null) {

            for (PermisoVentana permisoVentana : perfil.getVentanasPermisos()) {
                Vector<String> fila = new Vector<String>();
                fila.add(permisoVentana.getVentanaEnum().getNombre());
                fila.add((permisoVentana.getPermisoGrabar().equals("s")) ? "x" : "");
                fila.add((permisoVentana.getPermisoEditar().equals("s")) ? "x" : "");
                fila.add((permisoVentana.getPermisoBuscar().equals("s")) ? "x" : "");
                fila.add((permisoVentana.getPermisoImprimir().equals("s")) ? "x" : "");
                fila.add((permisoVentana.getPermisoEliminar().equals("s")) ? "x" : "");
                modeloTabla.addRow(fila);
            }
        }
        getTblDatos().setModel(modeloTabla);
               
    }
    
    private PermisoVentana getPermisoAgregar()
    {
        VentanaEnum ventanaEnum=(VentanaEnum) getCmbVentana().getSelectedItem();
        
        String opcionBuscar=getChkBuscar().isSelected()?"s":"n";
        String opcionEditar=getChkEditar().isSelected()?"s":"n";
        String opcionEliminar=getChkEliminar().isSelected()?"s":"n";
        String opcionGrabar=getChkGrabar().isSelected()?"s":"n";
        String opcionImprimir=getChkImprimir().isSelected()?"s":"n";
        
        PermisoVentana permisoVentana=new PermisoVentana();
        permisoVentana.setNombreClase(ventanaEnum.getCodigo());
        permisoVentana.setPermisoBuscar(opcionBuscar);
        permisoVentana.setPermisoEditar(opcionEditar);
        permisoVentana.setPermisoEliminar(opcionEliminar);
        permisoVentana.setPermisoGrabar(opcionGrabar);
        permisoVentana.setPermisoImprimir(opcionImprimir);
        
        return permisoVentana;
    }

    private void setearValoresPantalla() {
        perfil.setNombre(getTxtNombrePerfil().getText());
    }

    private void limpiarPantalla() {
        getTxtNombrePerfil().setText("");        
        getChkBuscar().setSelected(false);
        getChkEditar().setSelected(false);
        getChkEliminar().setSelected(false);
        getChkGrabar().setSelected(false);
        getChkImprimir().setSelected(false);
        construirTabla();
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
