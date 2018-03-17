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
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.NivelBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.RubroPlantillaBusquedaDialog;
import ec.com.codesoft.codefaclite.gestionacademica.panel.RubroPlantillaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.ejemplo.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        try {
            DialogoCodefac.mensaje("Correcto","La plantilla se edito correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            setearVariables();
            ServiceFactory.getFactory().getRubroPlantillaServiceIf().editarConDetalles(rubroPlantilla);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Incorrecto","La plantilla se grabo correctamente",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        RubroPlantillaBusquedaDialog busquedaDialogo = new RubroPlantillaBusquedaDialog();
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
        //cargarEstudiantesInscritosTabla(rubroPlantilla.get);
        //cargarEstudiantesSinInscribirTabla(nivelAcademico);
    }

    @Override
    public void limpiar() {
        limpiarVariables();        
        cargarDatosDetallesMap();
                
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
        listenerBotones();
        listenerCombos();
    }
    
    private void listenerBotones()
    {
        getBtnPasar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //Guardo la fila seleccionada porque va a recostruir los combos 
                Integer filaSeleccionada=getCmbCursosRegistrados().getSelectedIndex();
                
                pasarEstudiantes();
                cargarComboNivelesInscritos();
                
                //Selecciono el mismo dato para que vuelvan a cargar los datos
                getCmbCursoSinRegistrar().setSelectedIndex(getCmbCursoSinRegistrar().getSelectedIndex());
                
                //TODO: Ver como se puede mejorar esta parte
                //Vuelvo a seleccionar el combo construido
                if(getCmbCursosRegistrados().getSize().equals(1))
                {
                    //Si solo existe un dato selecciona el primero
                    getCmbCursosRegistrados().setSelectedIndex(1);
                }
                else
                {
                    //Selecciona el anterior dato
                    getCmbCursosRegistrados().setSelectedIndex(filaSeleccionada);
                }
                
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
                }, VentanaEnum.CATALOGO_PRODUCTO, false);
            }
        });
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
                inscribirEstudianteMap(estudianteInscrito);
            }
        }
        
    }
    
    private void inscribirEstudianteMap(EstudianteInscrito estudianteInscrito)
    {
        RubroPlantillaEstudiante estudianteRubroPlantilla=new RubroPlantillaEstudiante();
        estudianteRubroPlantilla.setEstudianteInscrito(estudianteInscrito);
        estudianteRubroPlantilla.setRubroPlantilla(rubroPlantilla);
        
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
        String[] titulo={"objeto","Seleccionar","Nombre"};
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{RubroPlantillaEstudiante.class,Boolean.class,String.class});
        
        List<RubroPlantillaEstudiante> estudiantesInscritos= estudiantesRegistradosMap.get(nivelAcademico);
        
        for (RubroPlantillaEstudiante estudianteInscrito : estudiantesInscritos) {
            modeloTabla.addRow(new Object[]{estudianteInscrito,false,estudianteInscrito.getEstudianteInscrito().getEstudiante().getNombreCompleto()});
        }
        
        getTblDatosRegistrados().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblDatosRegistrados(),0);                
        
    }
    
    private void cargarEstudiantesSinInscribirTabla(NivelAcademico nivelAcademico)
    {
        try {
            String[] titulo={"objeto","Seleccionar","Nombre"};
            
            DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{EstudianteInscrito.class,Boolean.class,String.class});
            
            List<EstudianteInscrito> estudiantesInscritosTodos=ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritos(nivelAcademico);
            
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
                
                //Verifica que en la lista de estudiantes no inscritos no esten ya inscritos
                if(estudiantesNoInscritos.contains(estudianteInscrito.getEstudianteInscrito()))
                {
                    //Si existe un estudiante ya inscrito lo borro de la lista de no inscritos
                    estudiantesNoInscritos.remove(estudianteInscrito.getEstudianteInscrito());
                }
                
            }
            
        }
        return estudiantesNoInscritos;
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
            estudiantesRegistradosMap=construirMapEstudiantesInscritos(rubroPlantilla.getDetalles());
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
            Map<String,Object> mapParametroMap=new HashMap<String,Object>();
            mapParametroMap.put("periodo",periodo);            
            List<NivelAcademico> niveles=ServiceFactory.getFactory().getNivelAcademicoServiceIf().obtenerPorMap(mapParametroMap);            
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
    
}
