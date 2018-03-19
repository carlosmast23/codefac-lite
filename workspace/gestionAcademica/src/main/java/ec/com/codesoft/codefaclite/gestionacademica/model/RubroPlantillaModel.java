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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
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
     * Lista de estudiantes para eliminar
     */
    private List<RubroPlantillaEstudiante> estudiantesEliminar;
    
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
        
        getCmbCursoSinRegistrar().setSelectedIndex(0);
        //Habilitar la pestaña de generar los rubros porque ya existe un dato
        this.getjTabPanel().setEnabledAt(2,true);
        //cargarEstudiantesInscritosTabla(rubroPlantilla.get);
        //cargarEstudiantesSinInscribirTabla(nivelAcademico);
    }

    @Override
    public void limpiar() {
        limpiarVariables();        
        ///cargarDatosDetallesMap();
                
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
            
            //Cargar los meses de los enum disponibles
            getCmbMesGenerar().removeAllItems();
            MesEnum[] meses= MesEnum.values();
            for (MesEnum mes : meses) {
                getCmbMesGenerar().addItem(mes);
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
        
        getBtnRegresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer filaSeleccionada=getCmbCursosRegistrados().getSelectedIndex();
                regresarEstudiantes();
                cargarComboNivelesInscritos();
                //Selecciono el mismo dato para que vuelvan a cargar los datos
                getCmbCursoSinRegistrar().setSelectedIndex(getCmbCursoSinRegistrar().getSelectedIndex());
                
                int nuevoTamanio=getCmbCursosRegistrados().getModel().getSize();
                if(getCmbCursosRegistrados().getModel().getSize()==1)
                {
                    //Si solo existe un dato selecciona el primero
                    getCmbCursosRegistrados().setSelectedIndex(0);
                }
                else
                {
                    //Selecciona el anterior dato
                    getCmbCursosRegistrados().setSelectedIndex(filaSeleccionada);
                }
                
            }
        });
        
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
                int nuevoTamanio=getCmbCursosRegistrados().getModel().getSize();
                if(getCmbCursosRegistrados().getModel().getSize()==1)
                {
                    //Si solo existe un dato selecciona el primero
                    getCmbCursosRegistrados().setSelectedIndex(0);
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
        
        getBtnGenerar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MesEnum mesEnum=(MesEnum) getCmbMesGenerar().getSelectedItem();
                
                if(mesEnum!=null)
                {
                    String nombreMes=getTxtNombreMes().getText();
                    if(!nombreMes.equals(""))
                    {
                        try {
                            //Solo generar si todavia no se ha generado
                            EnumSiNo enumSiNo=rubroPlantilla.obtenerMesPorEnum(mesEnum);
                            
                            if(enumSiNo.equals(EnumSiNo.NO))
                            {
                                rubroPlantilla = ServiceFactory.getFactory().getRubroEstudianteServiceIf().crearRubroEstudiantesDesdePlantila(rubroPlantilla, mesEnum, nombreMes);
                                DialogoCodefac.mensaje("Correcto", "Las deudas para el mes " + mesEnum.getNombre() + " se generaron correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                                cargarDatos();
                            }
                            else
                            {   
                                DialogoCodefac.mensaje("Error","El mes seleccionado ya fue generado",DialogoCodefac.MENSAJE_INCORRECTO);
                            }
                            
                        } catch (RemoteException ex) {
                            Logger.getLogger(RubroPlantillaModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
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
    
    private void pasarEstudiantes()
    {
        DefaultTableModel modeloTabla=(DefaultTableModel) getTblDatosSinRegistrar().getModel();
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            
            Boolean seleccionado=(Boolean) modeloTabla.getValueAt(i,1);
            
            //Agregar a la otra lista solo estudiantes seleccionados
            if(seleccionado)
            {
                EstudianteInscrito estudianteInscrito=(EstudianteInscrito) modeloTabla.getValueAt(i,0);
                
                //Si ya existe el dato en la lista de eliminar lo elimino antes de agregar
                if(containListaEliminados(estudianteInscrito))
                {
                   estudiantesEliminar.remove(estudianteInscrito);
                }
                else //Si no existe agrego normalmente el dato
                {
                    inscribirEstudianteMap(estudianteInscrito);
                }                
                
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
        
        getCmbMesGenerar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MesEnum mesEnum=(MesEnum) getCmbMesGenerar().getSelectedItem();
                
                getTxtNombreMes().setText(rubroPlantilla.getNombre()+" "+mesEnum.getNombre());
            }
        });
        
    }
    
    private void cargarEstudiantesInscritosTabla(NivelAcademico nivelAcademico)
    {
        String[] titulo={"objeto","Seleccionar","Nombre"};
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{RubroPlantillaEstudiante.class,Boolean.class,String.class});
        
        List<RubroPlantillaEstudiante> estudiantesInscritos= estudiantesRegistradosMap.get(nivelAcademico);
        
        for (RubroPlantillaEstudiante estudianteInscrito : estudiantesInscritos) {
            
            //Si el rubro esta dentro de la lista de datos a eliminar no lo muestro
            if(!estudiantesEliminar.contains(estudianteInscrito))
            {
                modeloTabla.addRow(new Object[]{estudianteInscrito,false,estudianteInscrito.getEstudianteInscrito().getEstudiante().getNombreCompleto()});
            }
            
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
        
        //Limpiar las tablas actuales
        getCmbCursosRegistrados().removeAllItems();
        //Limpiar la tabla de los estudiante registrados
        getTblDatosRegistrados().setModel(new DefaultTableModel());
        
        getCmbPeriodo().setSelectedIndex(0);
    }

    private void setearVariables() {
        Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
        this.rubroPlantilla.setPeriodo(periodo);
        
        this.rubroPlantilla.setNombre(getTxtNombre().getText());
        this.rubroPlantilla.setCatalogoProducto((CatalogoProducto) getCmbRubro().getSelectedItem());
        this.rubroPlantilla.setValor(new BigDecimal(getTxtValor().getText()));
        this.rubroPlantilla.setDiasCredito(new Integer(getTxtDiasCredito().getText()));
        
        this.rubroPlantilla.setEnero(EnumSiNo.getEnumByBoolean(this.getChkEnero().isSelected()).getLetra());
        this.rubroPlantilla.setFebrero(EnumSiNo.getEnumByBoolean(this.getChkFebrero().isSelected()).getLetra());
        this.rubroPlantilla.setMarzo(EnumSiNo.getEnumByBoolean(this.getChkMarzo().isSelected()).getLetra());
        this.rubroPlantilla.setAbril(EnumSiNo.getEnumByBoolean(this.getChkAbril().isSelected()).getLetra());
        this.rubroPlantilla.setMayo(EnumSiNo.getEnumByBoolean(this.getChkMayo().isSelected()).getLetra());
        this.rubroPlantilla.setJunio(EnumSiNo.getEnumByBoolean(this.getChkJunio().isSelected()).getLetra());
        this.rubroPlantilla.setJulio(EnumSiNo.getEnumByBoolean(this.getChkJunio().isSelected()).getLetra());
        this.rubroPlantilla.setAgosto(EnumSiNo.getEnumByBoolean(this.getChkAgosto().isSelected()).getLetra());
        this.rubroPlantilla.setSeptiembre(EnumSiNo.getEnumByBoolean(this.getChkSeptiembre().isSelected()).getLetra());
        this.rubroPlantilla.setOctubre(EnumSiNo.getEnumByBoolean(this.getChkOctubre().isSelected()).getLetra());
        this.rubroPlantilla.setNoviembre(EnumSiNo.getEnumByBoolean(this.getChkNoviembre().isSelected()).getLetra());
        this.rubroPlantilla.setDiciembre(EnumSiNo.getEnumByBoolean(this.getChkDiciembre().isSelected()).getLetra());
        
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
        
        //Cargar los check de los meses
        getChkEnero().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getEnero()).getBool());
        getChkFebrero().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getFebrero()).getBool());
        getChkMarzo().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getMarzo()).getBool());
        getChkAbril().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getAbril()).getBool());
        getChkMayo().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getMayo()).getBool());
        getChkJunio().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getJunio()).getBool());
        getChkJulio().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getJulio()).getBool());
        getChkAgosto().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getAgosto()).getBool());
        getChkSeptiembre().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getSeptiembre()).getBool());
        getChkOctubre().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getOctubre()).getBool());
        getChkNoviembre().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getNoviembre()).getBool());
        getChkDiciembre().setSelected(EnumSiNo.getEnumByLetra(rubroPlantilla.getDiciembre()).getBool());
        
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
