/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.AulaBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.other.RowsRenderer;
import ec.com.codesoft.codefaclite.gestionacademica.panel.MatriculaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.ejemplo.utilidades.list.UtilidadesLista;
import ec.com.codesoft.ejemplo.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class MatriculaModel extends MatriculaPanel {
    
        /**
     * Periodo por defecto para manejar cuando el estudiante no esta inscrito o
     * es nuevo
     */
    private Periodo periodoNuevosEstudiantes;

    private Periodo periodoTodosSinMatricular;
    
    /*
    Map donde se guardan todos los estudiantes inscritos de la base de datos
    */
    private Map<NivelAcademico,List<EstudianteInscrito>> estudiantesInscritosMap;
    
    /**
     * Lista que va a contener a los estudiante para eliminar
     */
    private List<EstudianteInscrito> estudiantesEliminar;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        limpiarVariables();
        listenerBotones();
        listenerCombos();
        listenerCheckBox();
        cargarValoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {

        try {
            List<EstudianteInscrito> estudiantes = obtenerEstudiantesPorMatricular();
            
            if (estudiantes.size() == 0 && estudiantesEliminar.size()==0) {
                DialogoCodefac.mensaje("Advertencia", "No existen datos para matricular", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Cancelado metodo grabar");
            }
            else
            {
                if(estudiantes.size()>0)
                    ServiceFactory.getFactory().getEstudianteInscritoServiceIf().matriculaEstudianteByList(estudiantes);
                
                if(estudiantesEliminar.size()>0)
                    ServiceFactory.getFactory().getEstudianteInscritoServiceIf().eliminarEstudiantesInscrito(estudiantesEliminar);
                
                
                int datosActualizados=estudiantes.size()+estudiantesEliminar.size();
                
                DialogoCodefac.mensaje("Correcto",datosActualizados+" estudiantes fueron actualizados correctamente", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
     
            
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error", "No existe comunicación con el servidor", DialogoCodefac.MENSAJE_ADVERTENCIA);
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private List<EstudianteInscrito> obtenerEstudiantesPorMatricular()
    {
        List<EstudianteInscrito> estudiantesMatriculado=new ArrayList<EstudianteInscrito>();
        
        for (Map.Entry<NivelAcademico, List<EstudianteInscrito>> entry : estudiantesInscritosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<EstudianteInscrito> detalles = entry.getValue();
        
            for (EstudianteInscrito detalle : detalles) {
                if(detalle.getId()==null)
                {
                    estudiantesMatriculado.add(detalle);
                }
            }
        }
        
        return estudiantesMatriculado;
        
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
        getCmbPeriodoSiguiente().setSelectedIndex(0);
        getCmbPeriodoAnterior().setSelectedIndex(0);
    }

    @Override
    public String getNombre() {
        return "Matricula en Grupo";
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
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerBotones() {
        getBtnPasar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //Nivel academico al cual se va a matricular
                NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                if(nivelAcademico!=null)
                {
                    pasarEstudiantesNoMatriculados(nivelAcademico);
                    cargarTablaNoMatriculados();
                    getCmbNivelMatricula().setSelectedIndex(getCmbNivelMatricula().getSelectedIndex());
                    
                }
            }
        });
        
        getBtnAgregarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstudianteBusquedaDialogo aulaBusquedaDialogo = new EstudianteBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Estudiante estudianteTmp = (Estudiante) buscarDialogoModel.getResultado();
                if (estudianteTmp != null) {
                    NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                    matriculaEstudiante(estudianteTmp, nivelAcademico);
                    
                    //Seleccionar el combo principal para volver a cargar los datos
                    cargarTablaNoMatriculados();
                    getCmbNivelMatricula().setSelectedIndex(getCmbNivelMatricula().getSelectedIndex());
                    
                }
            }
        });
        
        getBtnRegresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                regresarEstudiantesMatriculadosSinGrabar(nivelAcademico);
                
                //Vuelve a cargar los datos del nivel seleccionado
                cargarTablaNoMatriculados();
                
                //Vuelve a generar los combos de los niveles matriculados
                getCmbNivelMatricula().setSelectedIndex(getCmbNivelMatricula().getSelectedIndex());
                
            }
        });
    }
    
    private Integer estudiantesPorMatricular()
    {
        Integer estudiantesNoMatriculados=0;
        if(estudiantesInscritosMap!=null)
        {            
            for (Map.Entry<NivelAcademico, List<EstudianteInscrito>> entry : estudiantesInscritosMap.entrySet()) {
                NivelAcademico nivelAcademio = entry.getKey();
                List<EstudianteInscrito> detalles = entry.getValue();

                for (EstudianteInscrito detalle : detalles) {                
                    if(detalle.getId()==null)
                    {
                        estudiantesNoMatriculados++;
                    }                
                }            

            }
        }
        return estudiantesNoMatriculados;
        
    }
    
    private void regresarEstudiantesMatriculadosSinGrabar(NivelAcademico nivelAcademico)
    {
        List<String> nombresEstudiantes=new ArrayList<String>();
        
        DefaultTableModel tablaModelo=(DefaultTableModel) getTblAlumnosConMatricula().getModel();
        
        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            Boolean seleccion=(Boolean) tablaModelo.getValueAt(i,0);
            
            //Agregar solo datos que estan seleccionados
            if(seleccion)
            {
                EstudianteInscrito estudianteInscrito=(EstudianteInscrito) tablaModelo.getValueAt(i, 1);
                
                //Para los estudiantes inscritos que aun no ha sido grabados solo la elimino directo de la lista de matriculados
                if(estudianteInscrito.getId()==null)
                {
                    eliminarMatriculaEstudiante(estudianteInscrito);
                }
                else
                {
                    //Verifica que el estudiante inscrito no tenga relaciones con los rubros para eliminar
                    if(verificarEliminarMatriculaEstudianteGrabado(estudianteInscrito))
                    {
                        eliminarMatriculaEstudiante(estudianteInscrito);
                        estudiantesEliminar.add(estudianteInscrito);
                    }
                    else //Si tiene restricciones los grabo en una lista para mostrar al usuario
                    {
                        nombresEstudiantes.add(estudianteInscrito.getEstudiante().getNombreCompleto());
                    }
                }
            }
            
        }
        
        //Si existen expeciones para eliminar se muestra una alerta
        if(nombresEstudiantes.size()>0)
        {
            String estudianesStr=UtilidadesLista.castListToString(nombresEstudiantes,"\n");
            DialogoCodefac.mensaje("Advertencia","Existen problemas con los siguientes estudiantes: \n"+estudianesStr+"\n Elimine las relaciones para borrar el estudiante (Rubros Asignados)",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        
    }
    
    private Boolean verificarEliminarMatriculaEstudianteGrabado(EstudianteInscrito estudianteInscrito)
    {
        try {
            List<RubroEstudiante> rubros=ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosActivosPorEstudiantesInscrito(estudianteInscrito);
            if(rubros.size()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void pasarEstudiantesNoMatriculados(NivelAcademico nivelAcademico)
    {
        DefaultTableModel tablaModelo=(DefaultTableModel) getTblAlumnosSinMatricula().getModel();
        
        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            Boolean seleccion=(Boolean) tablaModelo.getValueAt(i,0);
            
            //Agregar solo datos que estan seleccionados
            if(seleccion)
            {
                Estudiante estudiante=(Estudiante) tablaModelo.getValueAt(i, 1);
                
                //Si esta dentro de la lista de eliminados solo copio a la otra lista   
                EstudianteInscrito estudianteInscrito=buscarEstudianteEliminado(estudiante);
                if(estudianteInscrito!=null)
                {
                    estudiantesEliminar.remove(estudianteInscrito);
                    //Si ya existe solo copio a la otra lista para que aparezca de nuevo
                    ingresarEstudianteMapMatricula((NivelAcademico) getCmbNivelMatricula().getSelectedItem(),estudianteInscrito);
                }
                else
                {
                    matriculaEstudiante(estudiante,nivelAcademico);
                }
                
            }
            
        }
    }
    
    private EstudianteInscrito buscarEstudianteEliminado(Estudiante estudiante)
    {
        for (EstudianteInscrito estudianteInscrito : estudiantesEliminar) {
            if(estudiante.equals(estudianteInscrito.getEstudiante()))
            {
                return estudianteInscrito;
            }
        }
        
        return null;
    }
    
    /**
     * Metodo que elimina del map de matriculados al estudiante
     * @param estudianteInscrito 
     */
    private void eliminarMatriculaEstudiante(EstudianteInscrito estudianteInscrito)
    {
        for (Map.Entry<NivelAcademico, List<EstudianteInscrito>> entry : estudiantesInscritosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<EstudianteInscrito> lista = entry.getValue();
            
            for (EstudianteInscrito estudiante : lista) {
                if(estudiante.getEstudiante().equals(estudianteInscrito.getEstudiante()))
                {
                    lista.remove(estudiante);
                    
                    //Eliminar del map el nivel si no existen datos
                    if(lista.size()==0)
                    {
                        estudiantesInscritosMap.remove(nivelAcademico);
                    }
                    return;
                }
            }
            
        }
    }
    
    /**
     * Agrega un nuevo estudiante al map de estudiantes matriculados
     * @param estudiante
     * @param nivelAcademico 
     */
    private void matriculaEstudiante(Estudiante estudiante,NivelAcademico nivelAcademico)
    {
        if(!verificarEstudianteExisteMapMatriculados(estudiante))
        {
            EstudianteInscrito estudianteInscrito=new EstudianteInscrito();
            estudianteInscrito.setEstudiante(estudiante);
            estudianteInscrito.setNivelAcademico(nivelAcademico);
            
            ingresarEstudianteMapMatricula(nivelAcademico,estudianteInscrito);
                        
        }
        else
        {
            DialogoCodefac.mensaje("Adevertencia","El estudiante ya se encuentra agregado para la matricula",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
    private void ingresarEstudianteMapMatricula(NivelAcademico nivelAcademico,EstudianteInscrito estudianteInscrito)
    {
        List<EstudianteInscrito> estudiantesInscritos = estudiantesInscritosMap.get(nivelAcademico);

        if (estudiantesInscritos == null) {
            estudiantesInscritos = new ArrayList<EstudianteInscrito>();
            estudiantesInscritos.add(estudianteInscrito);
            estudiantesInscritosMap.put(nivelAcademico,estudiantesInscritos);
        } else {
            estudiantesInscritos.add(estudianteInscrito);
        }
    }
    /**
     * Metodo que se encarga de verificar si existe el estudiante inscrito en el map
     * @param estudiante
     * @return 
     */
    private Boolean verificarEstudianteExisteMapMatriculados(Estudiante estudiante)
    {
        
        if(estudiantesInscritosMap!=null)
        {
            for (Map.Entry<NivelAcademico, List<EstudianteInscrito>> entry : estudiantesInscritosMap.entrySet()) {
                NivelAcademico nivelAcademico = entry.getKey();
                List<EstudianteInscrito> lista = entry.getValue();

                for (EstudianteInscrito estudianteInscrito : lista) {
                    if(estudianteInscrito.getEstudiante().equals(estudiante))
                    {
                        return true;
                    }
                }

            }
        }
        return false;
    }
    
    private void cargarNivelesPeriodo(Periodo periodo, JComboBox<NivelAcademico> comboNivel) {
        try {

            NivelAcademicoServiceIf servicio = ServiceFactory.getFactory().getNivelAcademicoServiceIf();
            Map<String, Object> mapBusqueda = new HashMap<String, Object>();
            mapBusqueda.put("periodo", periodo);
            List<NivelAcademico> resultados = servicio.obtenerPorMap(mapBusqueda);

            comboNivel.removeAllItems();
            for (NivelAcademico resultado : resultados) {
                comboNivel.addItem(resultado);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void listenerCombos() {
        
        getCmbPeriodoAnterior().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo = (Periodo) getCmbPeriodoAnterior().getSelectedItem();
                //Ocultar el contenido de los niveles anteriores si se selecciona alguna de las opciones
                if(periodo.equals(periodoNuevosEstudiantes) || periodo.equals(periodoTodosSinMatricular))
                {
                    getCmbNivel().setEnabled(false);       
                     cargarTablaNoMatriculados();
                }
                else
                {
                    getCmbNivel().setEnabled(true);
                    //Si se selecciona un periodo anterior cargar los niveles
                    cargarNivelesPeriodo(periodo, getCmbNivel());
                }

            }
        });
        
        getCmbPeriodoSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo = (Periodo) getCmbPeriodoSiguiente().getSelectedItem();
                if (periodo != null) {
                    
                    //TODO: Analizar que pasa cuando derepente quieren cambiar de periodo y se borran los datos
                    cargarValoresEstudiantesMatriculados(periodo);
                    cargarComboNivelesMatricula(periodo);
                    getCmbNivelMatricula().setSelectedIndex(getCmbNivelMatricula().getSelectedIndex());
                    //cargarNivelesPeriodo(periodo, getCmbNivelMatricula());
                }
            }

        });
        
        getCmbNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTablaNoMatriculados();          
            }
        });
        
        getCmbNivelMatricula().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                cargarTablaMatriculados(nivelAcademico);
            }
        });
    }
    
    /*
    private void cargarComboNivelesMatriculados() {
        
        //Esta variable selecciona el nivel seleccionado anterior s existe antes de volver a acargar los datos
        Integer nivelSeleccionadoIndice=getCmbNivelMatricula().getSelectedIndex();
        
        getCmbNivelMatricula().removeAllItems();
        for (Map.Entry<NivelAcademico, List<EstudianteInscrito>> entry : estudiantesInscritosMap.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            getCmbNivelMatricula().addItem(nivelAcademico);
        }
        
        
        //Despues de volver a acatualizar los datos si existe de nuevo el nivel seleccionado los selecciono o por defecto cargo el primer dato del combo
         
        if(getCmbNivelMatricula().getItemCount()>0)
        {
            if (nivelSeleccionadoIndice>=0 && nivelSeleccionadoIndice < getCmbNivelMatricula().getItemCount()) {
                getCmbNivelMatricula().setSelectedIndex(nivelSeleccionadoIndice);
            } else {
                getCmbNivelMatricula().setSelectedIndex(0);
            }
        }
        
    }*/
    
    private void cargarComboNivelesMatricula(Periodo periodo)
    {
        try {
            //Cargar los datos de los niveles disponibles
            List<NivelAcademico> niveles = ServiceFactory.getFactory().getNivelAcademicoServiceIf().obtenerTodosActivosPorPeriodo(periodo);
            getCmbNivelMatricula().removeAllItems();
            
            for (NivelAcademico nivel : niveles) {
                getCmbNivelMatricula().addItem(nivel);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    private void cargarTablaNoMatriculados()
    {
        try {
            Periodo periodo=(Periodo) getCmbPeriodoAnterior().getSelectedItem();
            NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivel().getSelectedItem();
            
            String[] titulos = {"Seleccion", "Estudiante"};
            DefaultTableModel modeloTabla = UtilidadesTablas.crearModeloTabla(titulos, new Class[]{Boolean.class, Estudiante.class});

            //Si al principio selecciono los nuevos estudiantes carga la lista solo de nuevos
            List<Estudiante> estudiantesSinMatricular=new ArrayList<Estudiante>();
            if (periodo.equals(periodoTodosSinMatricular)) {
                estudiantesSinMatricular = ServiceFactory.getFactory().getEstudianteServiceIf().estudianteSinMatriculaPorPeriodo(periodo);
            }
            else
            {
                if(periodo.equals(periodoNuevosEstudiantes))
                {
                    estudiantesSinMatricular = ServiceFactory.getFactory().getEstudianteServiceIf().estudianteNuevosSinMatricula();
                }
                else
                {
                    //Si no correponden a la ninguna de las opciones anteriores debe estar buscando por un nivel academico anterior
                    //Map<String,Object> mapParametro=new HashMap<String,Object>();
                    //mapParametro.put("nivelAcademico",nivelAcademico);
                   
                    List<EstudianteInscrito> estudiantesInscrito=ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritos(nivelAcademico);
                    estudiantesSinMatricular.clear();
                    //Cargar la lista solo de los estudiantes pertenencientes a ese nivel
                    for (EstudianteInscrito estudianteInscrito : estudiantesInscrito) {
                        estudiantesSinMatricular.add(estudianteInscrito.getEstudiante());
                    }
                }
            }
            
            //Cargar la lista de los estudiantes obtenidos segun la busqueda
            for (Estudiante estudiante : estudiantesSinMatricular) {
                if(!verificarEstudianteExisteMapMatriculados(estudiante))
                {
                    modeloTabla.addRow(new Object[]{false,estudiante});                
                }
                
            }
            
            getTblAlumnosSinMatricula().setModel(modeloTabla);
            

        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void cargarTablaMatriculados(NivelAcademico nivelAcademico)
    {
        String[] titulos={"Seleccion","Estudiante"};
        DefaultTableModel modeloTabla= UtilidadesTablas.crearModeloTabla(titulos,new Class[]{Boolean.class,EstudianteInscrito.class});
        
        List<EstudianteInscrito> estudiantesInscritosNivel=estudiantesInscritosMap.get(nivelAcademico);
        
        //Agregando los estudiantes que ya estan matriculados
        if(estudiantesInscritosNivel!=null)
        {
            for (EstudianteInscrito estudianteInscrito : estudiantesInscritosNivel) {
                
                //Solo agrego si no esta en la lista para eliminar
                if(!estudiantesEliminar.contains(estudianteInscrito))
                {
                    modeloTabla.addRow(new Object[]{false,estudianteInscrito});
                }
                
            }
        }
        
        getTblAlumnosConMatricula().setModel(modeloTabla);
        
    }

    private void limpiarVariables() {
        estudiantesInscritosMap=new HashMap<NivelAcademico,List<EstudianteInscrito>>();
        estudiantesEliminar=new ArrayList<EstudianteInscrito>();
        
    }
    
    private void cargarValoresIniciales() {
        try {
            periodoNuevosEstudiantes = new Periodo();
            periodoNuevosEstudiantes.setNombre("Nuevos Estudiante");

            periodoTodosSinMatricular = new Periodo();
            periodoTodosSinMatricular.setNombre("Sin Matricular");
            
            //Cargar los combos de los niveles
            List<Periodo> niveles = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbPeriodoAnterior().removeAllItems();
            getCmbPeriodoSiguiente().removeAllItems();

            getCmbPeriodoAnterior().addItem(periodoNuevosEstudiantes);
            getCmbPeriodoAnterior().addItem(periodoTodosSinMatricular);
            for (Periodo nivel : niveles) {
                if (GeneralEnumEstado.getEnum(nivel.getEstado()).equals(GeneralEnumEstado.INACTIVO)) {
                    getCmbPeriodoAnterior().addItem(nivel);
                }

                //Solo cargar los niveles activos en este combo
                if (GeneralEnumEstado.getEnum(nivel.getEstado()).equals(GeneralEnumEstado.ACTIVO)) {
                    getCmbPeriodoSiguiente().addItem(nivel);
                }
            }

        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarValoresEstudiantesMatriculados(Periodo periodoSeleccionado) {
        try {
            List<EstudianteInscrito> estudiantesInscritos=ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritosPorPeriodo(periodoSeleccionado);
            estudiantesInscritosMap=castListToMap(estudiantesInscritos);
            //Crear los combos de los niveles seleccionados segun el nivel
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private Map<NivelAcademico,List<EstudianteInscrito>> castListToMap(List<EstudianteInscrito> estudiantesInscritos)
    {
        Map<NivelAcademico,List<EstudianteInscrito>> estudianteNivelMap=new HashMap <NivelAcademico,List<EstudianteInscrito>>();
        
        for (EstudianteInscrito estudiantesInscrito : estudiantesInscritos) {
            List<EstudianteInscrito> listaMatriculados=estudianteNivelMap.get(estudiantesInscrito.getNivelAcademico());
            
            if(listaMatriculados==null)
            {
                listaMatriculados=new ArrayList<EstudianteInscrito>();
                listaMatriculados.add(estudiantesInscrito);
                estudianteNivelMap.put(estudiantesInscrito.getNivelAcademico(),listaMatriculados);
            }
            else
            {
                listaMatriculados.add(estudiantesInscrito);
            }
        }
        
        return estudianteNivelMap;
    }

    private void listenerCheckBox() {
        getChkSeleccionarTodoTblSinMatricula().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cambiarSeleccionaTabla(getTblAlumnosSinMatricula(),getChkSeleccionarTodoTblSinMatricula().isSelected());
            }
        });
        
        getChkSeleccionarTodoTblConMatricula().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cambiarSeleccionaTabla(getTblAlumnosConMatricula(),getChkSeleccionarTodoTblConMatricula().isSelected());
            }
        });

    }
    
    private void cambiarSeleccionaTabla(JTable tabla,Boolean seleccion)
    {
        DefaultTableModel tablaModelo=(DefaultTableModel) tabla.getModel();
        
        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            Boolean opcion=(Boolean) tablaModelo.getValueAt(i,0);
            tablaModelo.setValueAt(seleccion,i,0);           
        }
    }
   

}
