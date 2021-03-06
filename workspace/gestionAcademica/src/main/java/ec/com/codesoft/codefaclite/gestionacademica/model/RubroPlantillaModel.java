/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EstudianteInscritoBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.NivelBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.RubroPlantillaBusquedaDialog;
import ec.com.codesoft.codefaclite.gestionacademica.panel.RubroPlantillaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.eclipse.persistence.sessions.factories.SessionFactory;

/**
 *
 * @author Carlos
 */
public class RubroPlantillaModel extends RubroPlantillaPanel{
    
    /**
     * Lista para cargar todos los estudiantes inscritos en cada nivel
     */
    //private Map<NivelAcademico,List<EstudianteInscrito>> estudiantesSinRegistrarMap;
    
    /**
     * Lista para cargar todos los estudiantes registrados en un map
     */
    private Map<NivelAcademico,List<RubroPlantillaEstudiante>> estudiantesRegistradosMap;
    
    /**
     * Lista de estudiantes para eliminar
     */
    private List<RubroPlantillaEstudiante> estudiantesEliminar;
    
    /**
     * Referencia para trabajar con el objeto rubro plantilla
     */
    private RubroPlantilla rubroPlantilla;
    
    private static final int COLUMNA_VALOR_ESTUDIANTE=3; 

    public RubroPlantillaModel() {
        listaExclusionComponentes.add(getTxtValor()); //Agrego a la lista de exluciones para evitar que valide cuando existan datos ingresados
        listaExclusionComponentes.add(getTxtDiasCredito()); //Agrego a la lista de exluciones para evitar que valide cuando existan datos ingresados
    }
    
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
        iniciarListener();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
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
        try {
            
            setearVariables();
            ServiceFactory.getFactory().getRubroPlantillaServiceIf().editarConDetalles(rubroPlantilla,estudiantesEliminar);
            DialogoCodefac.mensaje("Correcto","La plantilla se edito correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Incorrecto","La plantilla se grabo correctamente",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        //Solo me permite eliminar si algun dato esta cargado
        try
        {
            if(estadoFormulario.equals(ESTADO_EDITAR))
            {
                
                if(!DialogoCodefac.dialogoPregunta("Pregunta","Esta seguro que desea borrar el plantilla?",DialogoCodefac.MENSAJE_INCORRECTO))
                {
                    throw new ExcepcionCodefacLite("Cancelado metodo eliminar");
                }
                
                //Verifica que no exista ningun rubro del mes generado
                if (rubroPlantilla.getMesesGenerados() == null || rubroPlantilla.getMesesGenerados().size() == 0) {
                    ServiceFactory.getFactory().getRubroPlantillaServiceIf().eliminar(rubroPlantilla);
                    DialogoCodefac.mensaje("Correcto", "La plantilla fue eliminada correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                } 
                else 
                {
                    // En esta condición se ingresa cuando ya existen rubros generados
                    Boolean isEliminar = DialogoCodefac.dialogoPregunta("Pregunta", "Ya existen rubros generados con esta plantilla, esta seguro que desea eliminar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if (isEliminar) {
                        ServiceFactory.getFactory().getRubroPlantillaServiceIf().eliminar(rubroPlantilla);
                        DialogoCodefac.mensaje("Correcto", "La plantilla fue eliminada correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                    }
                    else
                    {
                        throw new ExcepcionCodefacLite("Cancelado metodo eliminar");
                   }
                }
            }
        }
        catch(RemoteException re)
        {
            re.printStackTrace();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
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
        
        Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
        
        RubroPlantillaBusquedaDialog busquedaDialogo = new RubroPlantillaBusquedaDialog(periodoSeleccionado);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
        buscarDialogoModel.setVisible(true);
        RubroPlantilla rubroPlantillaTmp = (RubroPlantilla) buscarDialogoModel.getResultado();
        
        if (rubroPlantillaTmp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada para cancelar buscar");
        }
        
        rubroPlantilla=rubroPlantillaTmp;
        //Cuando se cargue un dato mandar por defecto el periodo que ya estuvo grabado
        cargarDatos();
        cargarDatosDetallesMap();
        
        getCmbCursoSinRegistrar().setSelectedIndex(0);
        //Habilitar la pestaña de generar los rubros porque ya existe un dato
        this.getjTabPanel().setEnabledAt(2,true);
        //cargarEstudiantesInscritosTabla(rubroPlantilla.get);
        //cargarEstudiantesSinInscribirTabla(nivelAcademico);
    }

    @Override
    public void limpiar() {
        limpiarVariables();        
        getCmbTipoValor().setSelectedItem(RubroPlantilla.TipoValorEnum.VALOR_FIJO);
        ///cargarDatosDetallesMap();
                
    }

//    @Override
    public String getNombre() {
        return "Rubros Plantilla";
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
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
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
            List<CatalogoProducto> productos=ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerPorModulo(ModuloCodefacEnum.GESTIONA_ACADEMICA);
            for (CatalogoProducto producto : productos) {
                getCmbRubro().addItem(producto);
            }
            
            //Cargar los tipos de valor
            RubroPlantilla.TipoValorEnum[] tipos=RubroPlantilla.TipoValorEnum.values();
            
            getCmbTipoValor().removeAllItems();
            for (RubroPlantilla.TipoValorEnum tipo : tipos) {
                getCmbTipoValor().addItem(tipo);
            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarListener() {
        listenerText();
        listenerBotones();
        listenerCombos();
        listenerList();
        listenerCheckBox();
    }
    
    private void listenerBotones()
    {
        
        getBtnAgregarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
                EstudianteInscritoBusquedaDialogo busquedaDialogo = new EstudianteInscritoBusquedaDialogo(periodoSeleccionado);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                buscarDialogoModel.setVisible(true);
                EstudianteInscrito estudianteInscrito = (EstudianteInscrito) buscarDialogoModel.getResultado();
                
                if (estudianteInscrito != null) {
                    //Solo agregar si no encuentra en la lista de estudiantes inscritos
                    if (!verificarExisteEstudianteInscrito(estudianteInscrito)) {
                        Integer filaSeleccionada = getCmbCursosRegistrados().getSelectedIndex();
                        pasarEstudiante(estudianteInscrito);
                        actualizarDatosInscripciones(filaSeleccionada);
                        DialogoCodefac.mensaje("Correcto","El estudiante se agrego correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                        
                    } else {
                        DialogoCodefac.mensaje("Advertencia", "El estudiante ya se encuentra agregado",DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                }

            }
        });
        
        getBtnRegresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer filaSeleccionada = getCmbCursosRegistrados().getSelectedIndex();
                regresarEstudiantes();
                actualizarDatosInscripciones(filaSeleccionada);
                
            }
        });
        
        getBtnPasar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //Guardo la fila seleccionada porque va a recostruir los combos 
                Integer filaSeleccionada=getCmbCursosRegistrados().getSelectedIndex();                
                pasarEstudiantes();
                actualizarDatosInscripciones(filaSeleccionada);
                
            }
        });
        
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
                }, VentanaEnum.CATALOGO_PRODUCTO, false,formularioActual);
            }
        });
        
        getBtnGenerar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               RubroPlantillaMes rubroPlantillaMes=getLstMesesSinGenerar().getSelectedValue();
                
                if(rubroPlantillaMes!=null)
                {
                    String nombreMes = getTxtNombreMes().getText();
                    if (!nombreMes.equals("")) {
                        try {

                            rubroPlantilla = ServiceFactory.getFactory().getRubroEstudianteServiceIf().crearRubroEstudiantesDesdePlantila(rubroPlantilla, rubroPlantillaMes.getMesEnum(), nombreMes, rubroPlantillaMes.getAnio());
                            DialogoCodefac.mensaje("Correcto", "Las deudas para el mes " + rubroPlantillaMes.getMesEnum().getNombre() + " se generaron correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                            cargarDatos();

                        } catch (RemoteException ex) {
                            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Verifica si un estudiante inscrito ya esta en el map de estudiantes inscritos
     * @return 
     */
    private boolean verificarExisteEstudianteInscrito(EstudianteInscrito estudianteInscritos)
    {
        for (Map.Entry<NivelAcademico, List<RubroPlantillaEstudiante>> entry : estudiantesRegistradosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<RubroPlantillaEstudiante> detalles = entry.getValue();
            
            for (RubroPlantillaEstudiante detalle : detalles) {
                if(detalle.getEstudianteInscrito().equals(estudianteInscritos))
                {
                    return true;
                }
            }
            
        }
        return false;
    }
    
    /**
     * Actualiza los combos y tablas utilizadas para la inscripcion de los estudiantes
     * @param filaSeleccionada 
     */
    private void actualizarDatosInscripciones(Integer filaSeleccionada) {
        cargarComboNivelesInscritos();
        //Selecciono el mismo dato para que vuelvan a cargar los datos
        getCmbCursoSinRegistrar().setSelectedIndex(getCmbCursoSinRegistrar().getSelectedIndex());

        //TODO: Ver como se puede mejorar esta parte
        //Vuelvo a seleccionar el combo construido
        int nuevoTamanio = getCmbCursosRegistrados().getModel().getSize();
        if (getCmbCursosRegistrados().getModel().getSize() == 1) {
            //Si solo existe un dato selecciona el primero
            getCmbCursosRegistrados().setSelectedIndex(0);
        } else {
            if(getCmbCursosRegistrados().getModel().getSize()>0)
            {
                //Selecciona el anterior dato 
                getCmbCursosRegistrados().setSelectedIndex(filaSeleccionada);
            }
            else
            {
                //Cuando no existen datos en el combo dejo la tabla vacia
                getTblDatosRegistrados().setModel(new DefaultTableModel());
            }
            
        }
    }
    
    /**
     * Verifica en la lista de estudiantes para ver si existe el estudiante inscrito
     * @return 
     */
    private boolean containListaEliminados(EstudianteInscrito estudianteInscrito)
    {
        for (RubroPlantillaEstudiante plantillaEstudiante : estudiantesEliminar) {
            if(plantillaEstudiante.getEstudianteInscrito().equals(estudianteInscrito))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private void pasarEstudiante(EstudianteInscrito estudianteInscrito)
    {
        //Si ya existe el dato en la lista de eliminar lo elimino antes de agregar
        if (containListaEliminados(estudianteInscrito)) {
            estudiantesEliminar.remove(estudianteInscrito);
        } else //Si no existe agrego normalmente el dato
        {
            inscribirEstudianteMap(estudianteInscrito);
        }   
    }
    
    private void pasarEstudiantes()
    {
        DefaultTableModel modeloTabla=(DefaultTableModel) getTblDatosSinRegistrar().getModel();
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            
            Boolean seleccionado=(Boolean) modeloTabla.getValueAt(i,1);
            
            //Agregar a la otra lista solo estudiantes seleccionados
            if(seleccionado)
            {
                EstudianteInscrito estudianteInscrito=(EstudianteInscrito) modeloTabla.getValueAt(i,0);
                
                pasarEstudiante(estudianteInscrito);        
                
            }
        }        
    }
    
    private void regresarEstudiantes()
    {
        DefaultTableModel modeloTabla=(DefaultTableModel) getTblDatosRegistrados().getModel();
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            
            Boolean seleccionado=(Boolean) modeloTabla.getValueAt(i,1);
            
            //Agregar a la otra lista solo estudiantes seleccionados
            if(seleccionado)
            {
                RubroPlantillaEstudiante inscrito=(RubroPlantillaEstudiante) modeloTabla.getValueAt(i,0);
                
                //Si el id es igual a null significa que aun no esta guardado y solo debo borrar del map temporal de los datos por gragar
                if(inscrito.getId()==null)
                {
                    eliminarEstudianteMap(inscrito.getEstudianteInscrito());
                }
                else //Caso contrario aunmento a la lista de elementos para borrar
                {
                    //Agrego a la lista para eliminar
                    estudiantesEliminar.add(inscrito);
                }
            }
        }
        
    }
    
    private void eliminarEstudianteMap(EstudianteInscrito estudianteInscrito)
    {
    
        for (Map.Entry<NivelAcademico, List<RubroPlantillaEstudiante>> entry : estudiantesRegistradosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<RubroPlantillaEstudiante> detalles = entry.getValue();
            
            for (RubroPlantillaEstudiante detalle : detalles) {
                if(estudianteInscrito.equals(detalle.getEstudianteInscrito()))
                {
                    detalles.remove(detalle);
                    
                    //Si no existen datos para ese nivel lo borro tambien del map
                    if(detalles.size()==0)
                    {
                        estudiantesRegistradosMap.remove(nivelAcademico);
                    }
                    
                    return;
                }
            }
            
        }
    }
    
    private void inscribirEstudianteMap(EstudianteInscrito estudianteInscrito)
    {
        RubroPlantillaEstudiante estudianteRubroPlantilla=new RubroPlantillaEstudiante();
        estudianteRubroPlantilla.setEstudianteInscrito(estudianteInscrito);
        estudianteRubroPlantilla.setRubroPlantilla(rubroPlantilla);
        estudianteRubroPlantilla.setValorPlantilla(new BigDecimal(getTxtValor().getText()));
        
        NivelAcademico nivelEstudiante=estudianteInscrito.getNivelAcademico();
        List<RubroPlantillaEstudiante> estudiantesInscritos= estudiantesRegistradosMap.get(nivelEstudiante);
        
        //Si el estudiante inscrito esta sin crear lo crea
        if(estudiantesInscritos==null)
        {
            estudiantesInscritos=new ArrayList<RubroPlantillaEstudiante>();
            estudiantesInscritos.add(estudianteRubroPlantilla);
            estudiantesRegistradosMap.put(nivelEstudiante,estudiantesInscritos);
        }
        else
        {
            estudiantesInscritos.add(estudianteRubroPlantilla);
        }
        
    }
    
    private void listenerCombos()
    {
        getCmbTipoValor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubroPlantilla.TipoValorEnum tipoValor=(RubroPlantilla.TipoValorEnum) getCmbTipoValor().getSelectedItem();
                if(tipoValor!=null)
                {
                    if(tipoValor.equals(RubroPlantilla.TipoValorEnum.VALOR_VARIABLE))
                    {
                        getTxtValor().setEnabled(false);
                    }
                    else
                    {
                        getTxtValor().setEnabled(true);
                    }
                }
            }
        });
        
        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Solo cargar cursos de otros periodos cuando no se ha grabado la plantilla
                if(rubroPlantilla.getId()==null)
                {
                    Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
                    cargarDatosCombo(periodoSeleccionado);
                }
            }
        });
        
        getCmbCursoSinRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademicoRegistrado=(NivelAcademico) getCmbCursoSinRegistrar().getSelectedItem();
                
                if(nivelAcademicoRegistrado!=null)
                {
                    cargarEstudiantesSinInscribirTabla(nivelAcademicoRegistrado);
                }
                
            }
        });
        
        getCmbCursosRegistrados().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico=(NivelAcademico) getCmbCursosRegistrados().getSelectedItem();
                
                if(nivelAcademico!=null)
                {
                    cargarEstudiantesInscritosTabla(nivelAcademico);
                }
            }
        });
        
    }
    
    private void cargarEstudiantesInscritosTabla(NivelAcademico nivelAcademico)
    {
        String[] titulo={"objeto","Seleccionar","Nombre","Valor"};
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{RubroPlantillaEstudiante.class,Boolean.class,String.class,BigDecimal.class});
        
        List<RubroPlantillaEstudiante> estudiantesInscritos= estudiantesRegistradosMap.get(nivelAcademico);
        
        for (RubroPlantillaEstudiante estudianteInscrito : estudiantesInscritos) {
            
            //Si el rubro esta dentro de la lista de datos a eliminar no lo muestro
            if(!estudiantesEliminar.contains(estudianteInscrito))
            {
                //BigDecimal valorEstudiante=new BigDecimal(getTxtValor().getText());
                modeloTabla.addRow(
                        new Object[]{
                            estudianteInscrito,
                            false,
                            estudianteInscrito.getEstudianteInscrito().getEstudiante().getNombreCompleto(),
                            estudianteInscrito.getValorPlantilla()
                        }
                );
            }
            
        }
        
        getTblDatosRegistrados().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblDatosRegistrados(),0);   
        
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int filaSeleccionada= e.getFirstRow();
                int columnaSeleccionada=e.getColumn();
                
                if(columnaSeleccionada==COLUMNA_VALOR_ESTUDIANTE)
                {
                    DefaultTableModel modeloDatos=(DefaultTableModel) e.getSource();
                    BigDecimal valorModificado=(BigDecimal) modeloDatos.getValueAt(filaSeleccionada,COLUMNA_VALOR_ESTUDIANTE);
                    RubroPlantillaEstudiante estudiantePlantilla=(RubroPlantillaEstudiante) modeloDatos.getValueAt(filaSeleccionada,0);
                    estudiantePlantilla.setValorPlantilla(valorModificado);
                    
                }
            }
        });
        
    }
    
    private void cargarEstudiantesSinInscribirTabla(NivelAcademico nivelAcademico)
    {
        try {
            String[] titulo={"objeto","Seleccionar","Nombre"};
            
            DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{EstudianteInscrito.class,Boolean.class,String.class});
            
            Periodo periodoSeleccionado = (Periodo) getCmbPeriodo().getSelectedItem();
            List<EstudianteInscrito> estudiantesInscritosTodos=ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritos(nivelAcademico,periodoSeleccionado);
            
            //Elimina los datos de los estudiantes que ya esten inscritos
            List<EstudianteInscrito> estudiantesNoInscritos= obtenerSoloEstudianesSinRegistro(estudiantesInscritosTodos);
            
            //Imprimir la tabla de los estudiantes no inscritos
            for (EstudianteInscrito estudianteNoInscrito : estudiantesNoInscritos) {
                modeloTabla.addRow(new Object[]{estudianteNoInscrito,false,estudianteNoInscrito.getEstudiante().getNombreCompleto()});
            }
            
            getTblDatosSinRegistrar().setModel(modeloTabla);
            UtilidadesTablas.ocultarColumna(getTblDatosSinRegistrar(),0);
        } catch (RemoteException ex) {
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private List<EstudianteInscrito> obtenerSoloEstudianesSinRegistro(List<EstudianteInscrito> estudiantesNoInscritos)
    {
        for (Map.Entry<NivelAcademico, List<RubroPlantillaEstudiante>> entry : estudiantesRegistradosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<RubroPlantillaEstudiante> estudiantesInscritos = entry.getValue();
            
            for (RubroPlantillaEstudiante estudianteInscrito : estudiantesInscritos) {
                //Solo hago la verificacion para datos que no esten dentro de la tabla eliminados
                if(!estudiantesEliminar.contains(estudianteInscrito))
                {
                    
                    //Verifica que en la lista de estudiantes no inscritos no esten ya inscritos
                    if (estudiantesNoInscritos.contains(estudianteInscrito.getEstudianteInscrito())) {
                        //Si existe un estudiante ya inscrito lo borro de la lista de no inscritos
                        estudiantesNoInscritos.remove(estudianteInscrito.getEstudianteInscrito());
                    }


                }
                                
            }
            
        }
        return estudiantesNoInscritos;
    }

    private void limpiarVariables() {
        this.rubroPlantilla=new RubroPlantilla();
        this.rubroPlantilla.setDetalles(new ArrayList<RubroPlantillaEstudiante>());
        this.estudiantesRegistradosMap=new HashMap<NivelAcademico,List<RubroPlantillaEstudiante>>();
        this.estudiantesEliminar=new ArrayList<RubroPlantillaEstudiante>();
        
        //Esta opcion de generar rubros dejo por defecto desabilitada , solo se habilita cuando ya esta grabado el dato
        this.getjTabPanel().setEnabledAt(2,false);
        
        this.getTxtDiasCredito().setText("0");
        
        //Limpiar las tablas actuales
        getCmbCursosRegistrados().removeAllItems();
        //Limpiar la tabla de los estudiante registrados
        getTblDatosRegistrados().setModel(new DefaultTableModel());
        
        getCmbPeriodo().setSelectedIndex(0);
        
        getjTabPanel().setSelectedIndex(0);
    }

    private void setearVariables() {
        Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
        this.rubroPlantilla.setPeriodo(periodo);
        
        this.rubroPlantilla.setNombre(getTxtNombre().getText());
        this.rubroPlantilla.setCatalogoProducto((CatalogoProducto) getCmbRubro().getSelectedItem());
        this.rubroPlantilla.setValor(new BigDecimal(getTxtValor().getText()));
        this.rubroPlantilla.setDiasCredito(new Integer(getTxtDiasCredito().getText()));
        
        
        List<RubroPlantillaEstudiante> detalles=obtenerDetallesFromMap();
        this.rubroPlantilla.setDetalles(detalles);
        
    }

    private void cargarDatosDetallesMap() {
        //Solo verificar cuando el rubro plantilla ya se haya guardado
        if(rubroPlantilla.getId()==null)
        {
            estudiantesRegistradosMap=new HashMap<NivelAcademico,List<RubroPlantillaEstudiante>>();
        }
        else
        {
            estudiantesRegistradosMap=construirMapEstudiantesInscritos(rubroPlantilla.getDetallesActivos());
        }
        
        cargarComboNivelesInscritos();
    }
    
    private Map<NivelAcademico,List<RubroPlantillaEstudiante>> construirMapEstudiantesInscritos(List<RubroPlantillaEstudiante> lista)
    {
        Map<NivelAcademico,List<RubroPlantillaEstudiante>> mapEstudianteInscritos=new HashMap<NivelAcademico,List<RubroPlantillaEstudiante>>();
        
        for (RubroPlantillaEstudiante rubroPlantillaEstudiante : lista) {
            
            NivelAcademico nivelAcademicoEstudiante=rubroPlantillaEstudiante.getEstudianteInscrito().getNivelAcademico();
            
            List<RubroPlantillaEstudiante> detalleInscritos=mapEstudianteInscritos.get(nivelAcademicoEstudiante);
            
            //Cuando no existe el nivel lo creamos
            if(detalleInscritos==null)
            {
                detalleInscritos=new ArrayList<RubroPlantillaEstudiante>();
                detalleInscritos.add(rubroPlantillaEstudiante);
                mapEstudianteInscritos.put(nivelAcademicoEstudiante,detalleInscritos);
            }
            else
            {
                detalleInscritos.add(rubroPlantillaEstudiante);
            }            
            
        }
        return mapEstudianteInscritos;
                
    }
    
    private List<EstudianteInscrito> obtenerEstudiantesNoRegistrados()
    {
        //ServiceFactory.getFactory().getEstudianteIn
        return null;
    }

    private void cargarDatosCombo(Periodo periodo) {
        try {
            /**
             * Cargar los datos del nivel academico en el combo
             * TODO: Vericar la modalidad cuando recien se crea y cuando ya existe datos
             */
            //Map<String,Object> mapParametroMap=new HashMap<String,Object>();
            //mapParametroMap.put("periodo",periodo);  
            //mapParametroMap.put("estado",GeneralEnumEstado.ACTIVO.getEstado()); 
            
            List<NivelAcademico> niveles=ServiceFactory.getFactory().getNivelAcademicoServiceIf().buscarPorPeriodo(periodo);            
            getCmbCursoSinRegistrar().removeAllItems();
            for (NivelAcademico nivelAcademico : niveles) {
                getCmbCursoSinRegistrar().addItem(nivelAcademico);
            }
            

        } catch (RemoteException ex) {
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarDatos() {
        getCmbPeriodo().setSelectedItem(rubroPlantilla.getPeriodo());
        getTxtNombre().setText(rubroPlantilla.getNombre());
        getCmbRubro().setSelectedItem(rubroPlantilla.getCatalogoProducto());
        getTxtValor().setText(rubroPlantilla.getValor().toString());
        getTxtDiasCredito().setText(rubroPlantilla.getDiasCredito().toString());
        
        cargarDatosCombo(rubroPlantilla.getPeriodo());
        
        //Cargar los datos de la tabla
        cargarListMesesSinGenerar();
        cargarListMesesGenerados();
   }
    
   private void cargarListMesesSinGenerar()
   {
       DefaultListModel<RubroPlantillaMes> meses=new DefaultListModel<RubroPlantillaMes>();
       
       if(rubroPlantilla.obtenerMesesSinGenerar()!=null)
       {
           for (RubroPlantillaMes mes : rubroPlantilla.obtenerMesesSinGenerar()) 
           {
               meses.addElement(mes);
           }
       }
       getLstMesesSinGenerar().setModel(meses);
   }
   
   private void cargarListMesesGenerados()
   {
       DefaultListModel<RubroPlantillaMes> meses=new DefaultListModel<RubroPlantillaMes>();
       if(rubroPlantilla.getMesesGenerados()!=null)
       {
           for (RubroPlantillaMes mes : rubroPlantilla.getMesesGenerados()) 
           {
               meses.addElement(mes);
           }
       }
       getLstMesesGenerados().setModel(meses);
   }

    /**
     * Cargar los combos disponibles segun el mapa de estudiantes inscritos
     */
    private void cargarComboNivelesInscritos() {
        
        getCmbCursosRegistrados().removeAllItems();
        for (Map.Entry<NivelAcademico, List<RubroPlantillaEstudiante>> entry : estudiantesRegistradosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            //List<RubroPlantillaEstudiante> estudiantesInscritos = entry.getValue();
            
            getCmbCursosRegistrados().addItem(nivelAcademico);
        }
        
    }
    
    private List<RubroPlantillaEstudiante> obtenerDetallesFromMap()
    {
        List<RubroPlantillaEstudiante> detalles=new ArrayList<RubroPlantillaEstudiante>();
        
        for (Map.Entry<NivelAcademico, List<RubroPlantillaEstudiante>> entry : estudiantesRegistradosMap.entrySet()) {
            //NivelAcademico key = entry.getKey();
            List<RubroPlantillaEstudiante> detalleEstudiantesInscritos = entry.getValue();
            
            for (RubroPlantillaEstudiante estudianteInscrito : detalleEstudiantesInscritos) {
                detalles.add(estudianteInscrito);
            }
            
        }
        
        return detalles;
    }

    private void listenerList() {
        getLstMesesSinGenerar().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                RubroPlantillaMes rubroPlantillaMes=getLstMesesSinGenerar().getSelectedValue();
                if(rubroPlantilla!=null && rubroPlantillaMes!=null)
                {
                    getTxtNombreMes().setText(rubroPlantilla.getNombre()+" "+rubroPlantillaMes.toString());
                }
            }
        });
        
        
        //Agregar un menu de opciones a la lista de mese generados
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemAdd = new JMenuItem("Eliminar");
        popupMenu.add(menuItemAdd);        
        getLstMesesGenerados().setComponentPopupMenu(popupMenu);
        menuItemAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubroPlantillaMes rubroPlantillaMes=getLstMesesGenerados().getSelectedValue();
                if(rubroPlantillaMes!=null)
                {
                    try {
                        ServiceFactory.getFactory().getRubroEstudianteServiceIf().eliminarMesRubroPlantilla(rubroPlantillaMes);
                        
                        if(rubroPlantillaMes.getRubroNivel()!=null)
                            DialogoCodefac.mensaje("Correcto","El rubro fue eliminado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                        else
                            DialogoCodefac.mensaje("Advertencia","El rubro del mes fue eliminado,\n pero las deudas de los estudiantes tiene que ser borradas manualmente", DialogoCodefac.MENSAJE_ADVERTENCIA);
                       
                        //Volver a consultar rubro plantilla con los datos modificados de la base
                        //rubroPlantilla=(RubroPlantilla) ServiceFactory.getFactory().getUtilidadesServiceIf().mergeEntity(rubroPlantilla);
                        rubroPlantilla=ServiceFactory.getFactory().getRubroPlantillaServiceIf().buscarPorId(rubroPlantilla.getId());                        
                        cargarDatos();
                        //as
                    } catch (RemoteException ex) {
                        Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje("Incorrecto",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                }
                
            }
        });
        

        
        
    }
    
    private void cambiarSeleccionaTabla(JTable tabla,Boolean seleccion)
    {
        DefaultTableModel tablaModelo=(DefaultTableModel) tabla.getModel();
        
        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            //Boolean opcion=(Boolean) tablaModelo.getValueAt(i,1);
            tablaModelo.setValueAt(seleccion,i,1);           
        }
    }

    private void listenerCheckBox() {
        getChkCursoRegistrado().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cambiarSeleccionaTabla(getTblDatosRegistrados(),getChkCursoRegistrado().isSelected());
            }
        });
        
        getChkCursoSinRegistrar().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Boolean opcion= getChkCursoSinRegistrar().isSelected();
                cambiarSeleccionaTabla(getTblDatosSinRegistrar(),opcion);
            }
        });
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerText() {
        
        getTxtValor().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                listenerTextValor();
            }
        });
        getTxtValor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerTextValor();
            }
        });
    }
    
    private void listenerTextValor() {
        if (estudiantesRegistradosMap.size() > 0) {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Pregunta", "Desea actualizar el valor a todos los estudiantes registrados?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (respuesta) {
                BigDecimal valorEstudiante = new BigDecimal(getTxtValor().getText());
                for (Map.Entry<NivelAcademico, List<RubroPlantillaEstudiante>> entry : estudiantesRegistradosMap.entrySet()) {
                    NivelAcademico nivelAcademico = entry.getKey();
                    List<RubroPlantillaEstudiante> lista = entry.getValue();

                    for (RubroPlantillaEstudiante plantillaEstudiante : lista) {
                        plantillaEstudiante.setValorPlantilla(valorEstudiante);
                    }

                }
                getCmbCursosRegistrados().setSelectedIndex(getCmbCursosRegistrados().getSelectedIndex());
            }
        }
    }
    
    
}
