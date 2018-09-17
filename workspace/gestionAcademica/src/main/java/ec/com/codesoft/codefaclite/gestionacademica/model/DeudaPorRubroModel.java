/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EstudianteInscritoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.RubroPeriodoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.DeudaPorRubroPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class DeudaPorRubroModel extends DeudaPorRubroPanel{
    
    /**
     * Lista de los estuditantes que tienen ese rubro pendiente
     * TODO: Cambiar a map , porque se pueden hacer cambios entre varios cursos y seria bueno tener separado por cursos
     */
    private List<RubroEstudiante> rubrosEstudiante;
    
    /**
     * Lista que contiene datos de rubros que quieren eliminar
     */
    private List<RubroEstudiante> rubrosEstudianteEliminar;
    
    /**
     * Rubro del nivel que seleccione el usuario
     */
    private RubrosNivel rubroNivelSeleccionado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        valoresInicialesPantalla();
        listenerTabla();
        listenerBotones();
        listenerCheckBox();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        ServiceFactory.getFactory().getRubroEstudianteServiceIf().crearRubrosEstudiantes(obtenerRubrosCrear());
        ServiceFactory.getFactory().getRubroEstudianteServiceIf().eliminarRubrosEstudiantes(rubrosEstudianteEliminar);
        DialogoCodefac.mensaje("Correcto","Los datos fueron actualizados correctamente",DialogoCodefac.MENSAJE_CORRECTO);
    }
    
    private List<RubroEstudiante> obtenerRubrosCrear()
    {
        List<RubroEstudiante> rubrosCrear=new ArrayList<RubroEstudiante>();
        for (RubroEstudiante rubroEstudiante: rubrosEstudiante) {
            if(rubroEstudiante.getId()==null)
            {
                rubrosCrear.add(rubroEstudiante);
            }
        }
        return rubrosCrear;
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        rubrosEstudiante=new ArrayList<RubroEstudiante>();
        rubrosEstudianteEliminar=new ArrayList<RubroEstudiante>();
        rubroNivelSeleccionado=null;
        getTxtRubro().setText("");
        getTblDetalles().setModel(new DefaultTableModel());
        getChkSeleccionarTodos().setSelected(false);
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
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

    private void valoresInicialesPantalla() {
        try {
            List<Periodo> periodos= ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean verificarExisteRubroEstudiante(EstudianteInscrito estudianteInscrito)
    {
        for (RubroEstudiante rubroEstudiante : rubrosEstudiante) 
        {
            if(rubroEstudiante.getEstudianteInscrito().equals(estudianteInscrito))
            {
                return true;
            }
        }
        return false;
    }

    private void listenerBotones() {
        
        getBtnEliminarRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < getTblDetalles().getRowCount(); i++) {
                    boolean seleccionado=(boolean) getTblDetalles().getValueAt(i,1);
                    
                    if(seleccionado)
                    {
                        RubroEstudiante rubroEstudiante=(RubroEstudiante) getTblDetalles().getValueAt(i, 0);
                        if(rubroEstudiante.getId()==null)
                        {
                            rubrosEstudiante.remove(rubroEstudiante); //Si no tiene id elimino directamente de la lista
                        }else
                        {
                            //Caso contrario agrego a la lista de eliminar
                            rubrosEstudianteEliminar.add(rubroEstudiante); //Agrego a la lista que objetos quieren eliminar
                        } 
                        
                    }
                }
                
                //Validar que solo reconstruya la tabla solo si existieron datos eliminados
                construirTabla();
            }
        });
        
        getBtnAgregarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
                
                if(periodoSeleccionado==null)
                {
                    DialogoCodefac.mensaje("Error","Seleccione un nivel primero",DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                
                if(rubroNivelSeleccionado==null)
                {
                    DialogoCodefac.mensaje("Error","Seleccione nu rubro primero",DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                
                EstudianteInscritoBusquedaDialogo busquedaDialogo = new EstudianteInscritoBusquedaDialogo(periodoSeleccionado);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                buscarDialogoModel.setVisible(true);
                EstudianteInscrito estudianteInscrito = (EstudianteInscrito) buscarDialogoModel.getResultado();
                
                if (estudianteInscrito != null) {
                    boolean existeEstudiante=verificarExisteRubroEstudiante(estudianteInscrito);
                    
                    //Si ya existe el estudiante inscrito preguntar si quiere agregar nuevamente
                    if(existeEstudiante)
                    {
                        if(!DialogoCodefac.dialogoPregunta("Pregunta","El estudiante ya tiene asignada una deuda con el rubro ,\n Esta seguro que quiere asignar nuevamente la deuda?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                        {
                            return ;//Si el usuario selecciona no , termino el metodo
                        }
                    }
                    
                    //Agregar la nueva deuda , no seteo la fecha porque eso se debe guardar cuando crea el servidor
                    RubroEstudiante rubroEstudiante=new RubroEstudiante();
                    rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                    rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                    rubroEstudiante.setEstudianteInscrito(estudianteInscrito);
                    rubroEstudiante.setRubroNivel(rubroNivelSeleccionado);
                    rubroEstudiante.setSaldo(rubroNivelSeleccionado.getValor());
                    rubroEstudiante.setValor(rubroNivelSeleccionado.getValor());
                    rubrosEstudiante.add(rubroEstudiante);
                    construirTabla();
                    
                }
            }
        });
        
        getBtnBuscarRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
                RubroPeriodoBusquedaDialogo busquedaDialogo = new RubroPeriodoBusquedaDialogo(periodoSeleccionado);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                buscarDialogoModel.setVisible(true);
                
                if(buscarDialogoModel.getResultado()!=null)
                {
                    try {
                        limpiar();
                        rubroNivelSeleccionado=(RubrosNivel) buscarDialogoModel.getResultado();
                        getTxtRubro().setText(rubroNivelSeleccionado.toString());                        
                        List<RubrosNivel> rubrosNivel=new ArrayList<RubrosNivel>();
                        rubrosNivel.add(rubroNivelSeleccionado);
                        rubrosEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosEstudiantesPorRubros(rubrosNivel);
                        construirTabla();
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(DeudaPorRubroModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
     
            }
        });
    }
    
    private void construirTabla()
    {
        DefaultTableModel modeloTabla = UtilidadesTablas.crearModeloTabla(new String[]{"", "Opción", "Nombres", "Curso"}, new Class[]{Object.class, Boolean.class, String.class, String.class});

        List<RubrosNivel> rubrosNivel = new ArrayList<RubrosNivel>();
        rubrosNivel.add(rubroNivelSeleccionado);

        //Consulta todos los rubros de los estudiantes creados de ese nivel
        for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {

            if (!rubrosEstudianteEliminar.contains(rubroEstudiante)) {
                Object[] fila = new Object[]{
                    rubroEstudiante,
                    false,
                    rubroEstudiante.getEstudianteInscrito().getEstudiante().getNombreCompleto(),
                    rubroEstudiante.getEstudianteInscrito().getNivelAcademico().getNombre()};

                modeloTabla.addRow(fila);
            }

        }

        getTblDetalles().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblDetalles(), 0);      

    }

    private void listenerCheckBox() {
        
        getChkSeleccionarTodos().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        getChkSeleccionarTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //Verificar o activar solo si no esta seleccionados

                DefaultTableModel modeloTabla=(DefaultTableModel) getTblDetalles().getModel();
                
                for (int i = 0; i < modeloTabla.getRowCount(); i++) 
                {
                    
                    if(getChkSeleccionarTodos().isSelected())
                    {
                        //Caso cuando se va a activar las casillas
                        RubroEstudiante rubroEstudiante=(RubroEstudiante)modeloTabla.getValueAt(i,0);
                        
                        if(validarPuedeEliminarRubro(rubroEstudiante))
                        {
                            modeloTabla.setValueAt(true, i, 1); //Setear con el valor de activo la casilla de opcion                            
                        }
                        else
                        {
                            DialogoCodefac.mensaje("Advertencia","El rubro del estudiante "+rubroEstudiante.getEstudianteInscrito().getEstudiante().getNombreSimple()+" no se puede eliminar porque tiene deudas facturadas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                    }
                    else
                    {
                        //Caso cuando se va a desactivar las casillas
                        modeloTabla.setValueAt(false, i, 1); //Setear con el valor de activo la casilla de opcion
                    }
                }
                
            }
        });
    }
    
    private boolean validarPuedeEliminarRubro(RubroEstudiante rubroEstudiante)
    {
        //Solo se puede dar de baja la deuda si esta en el estado sin facturar
        if(rubroEstudiante.getEstadoFacturaEnum().equals(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR))
        {
            return true;
        }
        return false;
    }
    
  
    
    //private void 

    private void listenerTabla() {
        getTblDetalles().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada=getTblDetalles().getSelectedRow();
                DefaultTableModel modeloTabla = (DefaultTableModel) getTblDetalles().getModel();

                //TODO: Ver si se puede crear un solo metodo para homologar con la funcion de seleccionar todos que es similar
                if (filaSeleccionada >= 0) {

                    //Caso cuando se va a activar las casillas
                    RubroEstudiante rubroEstudiante = (RubroEstudiante) modeloTabla.getValueAt(filaSeleccionada, 0);

                    if (validarPuedeEliminarRubro(rubroEstudiante)) {
                        modeloTabla.setValueAt(true, filaSeleccionada, 1); //Setear con el valor de activo la casilla de opcion                            
                    } else {
                        DialogoCodefac.mensaje("Advertencia", "El rubro del estudiante " + rubroEstudiante.getEstudianteInscrito().getEstudiante().getNombreSimple() + " no se puede eliminar porque tiene deudas facturadas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }

                }

                
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
}
