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
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteInscritoBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteMatriculaBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.MatriculaEstudiantePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDescuentoRubroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class MatriculaEstudianteModel extends MatriculaEstudiantePanel{
    
    /**
     * Variable donde se va a almencenar la inscripcion del alumno
     */
    private EstudianteInscrito estudianteInscrito;
    private RubroEstudiante rubroMatricula;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listenerBotones();
        listenerCombos();
        listenerChkBox();
        iniciarValoresComponentes();
        iniciarCombos();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        //Habilito campos especiales cuando se va a crear
        getBtnBuscarEstudiante().setEnabled(true);
        getBtnNuevoEstudiante().setEnabled(true);
        getCmbRubroMatricula().setEnabled(true);
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            
            //Verifica si pasa la validacion de los datos
            if(!validacionDatosGrabar())
            {
                throw  new ExcepcionCodefacLite("Fallo validacion de datos");
            }
            
            setearValores();
            estudianteInscrito=ServiceFactory.getFactory().getEstudianteInscritoServiceIf().matricularEstudiante(estudianteInscrito, rubroMatricula);
            DialogoCodefac.mensaje("Correcto","El estudiante fue matriculado correctamente",DialogoCodefac.MENSAJE_CORRECTO);

            Boolean opcion=DialogoCodefac.dialogoPregunta("Aviso","Desea facturar ahora?",DialogoCodefac.MENSAJE_ADVERTENCIA);
            
            if(opcion)
            {
                /**
                 * Preguntar si desea facturar en ese momento
                 */
                Object[] paramPostConstruct = new Object[5];
                paramPostConstruct[0] = DocumentoEnum.FACTURA;
                paramPostConstruct[1] = TipoDocumentoEnum.ACADEMICO;
                paramPostConstruct[2] = estudianteInscrito.getEstudiante();
                paramPostConstruct[3] = estudianteInscrito.getEstudiante().getRepresentante();

                //Lista del rubro para facturar            
                List<RubroEstudiante> matriculas = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubroMatriculaPorEstudianteInscrito(estudianteInscrito);
                paramPostConstruct[4] = matriculas;

                panelPadre.crearVentanaCodefac(VentanaEnum.FACTURACION, true, paramPostConstruct);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValores();
            ServiceFactory.getFactory().getEstudianteInscritoServiceIf().editar(estudianteInscrito);
            DialogoCodefac.mensaje("Correcto","La matricula fue editada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if(verificarEliminarMatriculaEstudianteGrabado(estudianteInscrito))
        {
            try {
                //Eliminar el estudiantes
                ServiceFactory.getFactory().getEstudianteInscritoServiceIf().eliminar(estudianteInscrito);        
                DialogoCodefac.mensaje("Correcto","El estudiante matriculado fue eliminado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            DialogoCodefac.mensaje("Advertencia","No se puede eliminar la matricula porque tienen deudas de rubros sin anular",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("cancelar grabar");
        }
    }
    
    private Boolean verificarEliminarMatriculaEstudianteGrabado(EstudianteInscrito estudianteInscrito) {
        try {
            List<RubroEstudiante> rubros = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosActivosPorEstudiantesInscrito(estudianteInscrito);
            if (rubros.size() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
        Periodo periodoSeleccionado = (Periodo) getCmbPeriodoActivo().getSelectedItem();
        EstudianteInscritoBusquedaDialogo busquedaDialogo = new EstudianteInscritoBusquedaDialogo(periodoSeleccionado);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
        buscarDialogoModel.setVisible(true);
        EstudianteInscrito estudianteInscritoTmp = (EstudianteInscrito) buscarDialogoModel.getResultado();
        
        if(estudianteInscritoTmp==null)
        {
            throw new ExcepcionCodefacLite("Cancelado buscar");
        }
        else
        {
            try {
                estudianteInscrito=estudianteInscritoTmp;
                //Cargar el rubro de matricula
                List<RubroEstudiante> matriculas=ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubroMatriculaPorEstudianteInscrito(estudianteInscritoTmp);
                
                if(matriculas.size()>0)
                {
                    rubroMatricula=matriculas.get(0);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Cargar los datos en pantalla
            cargarDatos();   
            //Desactivar campos en esta modalidad que no puedo editar
            getBtnBuscarEstudiante().setEnabled(false);
            getBtnNuevoEstudiante().setEnabled(false);
            getCmbRubroMatricula().setEnabled(false);
        }
        
        
        
        
    }

    @Override
    public void limpiar() {
        estudianteInscrito = new EstudianteInscrito();
        rubroMatricula = new RubroEstudiante();

        getCmbTipoMatricula().setSelectedItem(EstudianteInscrito.TipoMatriculaEnum.ORDINARIA);
        getCmbPeriodoActivo().setSelectedIndex(0);
        getCmbEstudianteTieneBeca().setSelectedItem(EnumSiNo.NO);
        getChkNinguno().setSelected(true);
        getTxtNombreDescuento().setEnabled(false);
        getSpnPorcentaje().setEnabled(false);
        
        getChkBeca().setEnabled(true);
        getChkNinguno().setEnabled(true);
        getChkOtro().setEnabled(true);
         
    }

//    @Override
    public String getNombre() {
        return "Matricula Estudiante";
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

    private void iniciarValoresComponentes() {
        try {
            //Cargar los periodos activos
            List<Periodo> periodosActivos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            getCmbPeriodoActivo().removeAllItems();
            for (Periodo periodosActivo : periodosActivos) {
                getCmbPeriodoActivo().addItem(periodosActivo);
            }

            //Iniciar combo del tipo de matricula
            EstudianteInscrito.TipoMatriculaEnum[] listaMatricula = EstudianteInscrito.TipoMatriculaEnum.values();
            getCmbTipoMatricula().removeAllItems();
            for (EstudianteInscrito.TipoMatriculaEnum tipoMatricula : listaMatricula) {
                getCmbTipoMatricula().addItem(tipoMatricula);
            }
            
            //Iniciar valor de si el estudiante va a tener beca si o no
            for (EnumSiNo enumSiNo : EnumSiNo.values()) {
                getCmbEstudianteTieneBeca().addItem(enumSiNo);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarCombos() {
        getCmbPeriodoActivo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo=(Periodo) getCmbPeriodoActivo().getSelectedItem();
                cargarCursosPorPeriodo(periodo);
            }
        });
        
        getCmbCursoAsignar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    NivelAcademico nivelAcademico=(NivelAcademico)getCmbCursoAsignar().getSelectedItem();
                    Periodo periodoSeleccionado=(Periodo) getCmbPeriodoActivo().getSelectedItem();
                    //Cargar los rubros disponibles para asignar para la matricula
                    if(nivelAcademico!=null)
                    {
                        List<RubrosNivel> rubrosNivel = ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorCatalogoCatagoriaYNivelPeriodo(CatalogoProducto.TipoEnum.MATRICULA, nivelAcademico.getNivel(),periodoSeleccionado);

                        getCmbRubroMatricula().removeAllItems();
                        for (RubrosNivel rubroNivel : rubrosNivel) {
                            getCmbRubroMatricula().addItem(rubroNivel);
                        }
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        
    }
    
    private void cargarCursosPorPeriodo(Periodo periodo)
    {
        try {
            List<NivelAcademico> cursos= ServiceFactory.getFactory().getNivelAcademicoServiceIf().obtenerTodosActivosPorPeriodo(periodo);
            getCmbCursoAsignar().removeAllItems();;
            for (NivelAcademico curso : cursos) {
                getCmbCursoAsignar().addItem(curso);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void listenerBotones() {
        
        //Buscar un estudiante nuevo
        getBtnBuscarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodoSeleccionado=(Periodo) getCmbPeriodoActivo().getSelectedItem();
                EstudianteMatriculaBusquedaDialogo busquedaDialog = new EstudianteMatriculaBusquedaDialogo(periodoSeleccionado);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialog);
                buscarDialogoModel.setVisible(true);
                Estudiante estudianteTemp = (Estudiante) buscarDialogoModel.getResultado();
                if (estudianteTemp != null) {
                    
                    //Solo setear si el estudiante aunnno esta inscrito en ese periodo
                    if (verificarEstudianteNoEstaInscrito(estudianteTemp)) 
                    {
                        estudianteInscrito.setEstudiante(estudianteTemp);
                        getTxtEstudiante().setText(estudianteInscrito.getEstudiante().getNombreCompleto());
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Advertencia","El estudiante ya esta matriculado",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }

                    //getCmbCursoAsignar().setSelectedItem(estudianteInscrito.getNivelAcademico());
                }
            }
        });
        
        getBtnNuevoEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Estudiante>() {
                    @Override
                    public void updateInterface(Estudiante entity) {
                        if(entity!=null)
                        {
                            estudianteInscrito.setEstudiante(entity);
                            getTxtEstudiante().setText(entity.getNombreCompleto());
                       }
                    }
                }, VentanaEnum.ESTUDIANTES,false,formularioActual);
            }
        });
    }

    private void listenerChkBox() {
        getChkNinguno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Si esta seleccionado desahabilito las demas opciones
                if(getChkNinguno().isSelected())
                {
                    getTxtNombreDescuento().setText("");
                    getTxtNombreDescuento().setEnabled(false);
                    
                    getSpnPorcentaje().setValue(0);
                    getSpnPorcentaje().setEnabled(false);
                }

            }
        });
        
        getChkBeca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkBeca().isSelected())
                {
                    getTxtNombreDescuento().setEnabled(true);
                    getSpnPorcentaje().setEnabled(true);
                }
            }
        });
        
        getChkOtro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkOtro().isSelected())
                {
                    getTxtNombreDescuento().setEnabled(true);
                    getSpnPorcentaje().setEnabled(true);
                }
            }
        });
    }
    
    private boolean verificarEstudianteNoEstaInscrito(Estudiante estudiante)
    {
        try {
            Periodo periodoSeleccionado=(Periodo) getCmbPeriodoActivo().getSelectedItem();
            //Verificar que no este inscrito , porque solo puede haber un estudiante inscrito por periodo
            List<EstudianteInscrito> lista= ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritosPorPeriodoYEstudiante(periodoSeleccionado, estudiante);
            
            if(lista.size()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return false;
    }

    private void setearValores() {
        estudianteInscrito.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        estudianteInscrito.setNivelAcademico((NivelAcademico) getCmbCursoAsignar().getSelectedItem());
        EstudianteInscrito.TipoMatriculaEnum tipoMatriculaEnum=(EstudianteInscrito.TipoMatriculaEnum) getCmbTipoMatricula().getSelectedItem();
        estudianteInscrito.setTipoMatriculaCod(tipoMatriculaEnum.getLetra());
        EnumSiNo enumSiNo=(EnumSiNo) getCmbEstudianteTieneBeca().getSelectedItem();
        estudianteInscrito.setBeca(enumSiNo.getLetra());
        
        //Setear los valores del rubro de la matricula

        rubroMatricula.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        rubroMatricula.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
        rubroMatricula.setEstudianteInscrito(estudianteInscrito);
        rubroMatricula.setNombreDescuento(getTxtNombreDescuento().getText());
        rubroMatricula.setProcentajeDescuento((Integer) getSpnPorcentaje().getValue());
        rubroMatricula.setFechaGenerado(UtilidadesFecha.getFechaHoy());
        RubrosNivel rubroNivel=(RubrosNivel) getCmbRubroMatricula().getSelectedItem();
        rubroMatricula.setRubroNivel(rubroNivel);
        rubroMatricula.setSaldo(rubroNivel.getValor());
        rubroMatricula.setValor(rubroNivel.getValor());
        rubroMatricula.setTipoDescuento( getTipoDescuentoCheck().getLetra());
        
    }
    
    private void SeleccionaDescuentoRubroEnum(TipoDescuentoRubroEnum tipoDescuentoEnum)
    {
        switch(tipoDescuentoEnum)
        {
            case BECA:getChkBeca().isSelected();break;
            case NINGUNO:getChkNinguno().isSelected();break;
            case OTRO:getChkOtro().isSelected();break;
        }
        
    }
    
    private TipoDescuentoRubroEnum getTipoDescuentoCheck()
    {
        if(getChkBeca().isSelected())
        {
            return TipoDescuentoRubroEnum.BECA;
        }
        else
        {
            if(getChkNinguno().isSelected())
            {
                return TipoDescuentoRubroEnum.NINGUNO;
            }
            else
            {
                if(getChkOtro().isSelected())
                {
                    return TipoDescuentoRubroEnum.OTRO;
                }
            
            }
        }
        
        return null;
    }

    private void listenerCombos() {
        getCmbEstudianteTieneBeca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnumSiNo enumSiNo=(EnumSiNo) getCmbEstudianteTieneBeca().getSelectedItem();
                
                //Funcionalidad disponible solo cuando esta en el modo de grabar
                if(estadoFormulario.equals(ESTADO_GRABAR))
                {
                    if(enumSiNo.equals(EnumSiNo.SI))
                    {
                        getChkBeca().setSelected(true);
                        getTxtNombreDescuento().setEnabled(true);
                        getSpnPorcentaje().setEnabled(true);
                    }
                    else
                    {
                        getChkBeca().setSelected(false);
                        getTxtNombreDescuento().setEnabled(false);
                        getSpnPorcentaje().setEnabled(false);
                    }
                }
            }
        });
    }

    private void cargarDatos() {
        getCmbPeriodoActivo().setSelectedItem(estudianteInscrito.getNivelAcademico().getPeriodo());
        getTxtEstudiante().setText(estudianteInscrito.getEstudiante().getNombreCompleto());
        getCmbCursoAsignar().setSelectedItem(estudianteInscrito.getNivelAcademico());
        getCmbTipoMatricula().setSelectedItem(estudianteInscrito.getTipoMatriculaCodEnum());
        getCmbEstudianteTieneBeca().setSelectedItem(estudianteInscrito.getBecaEnum());
        
        //Cargar solo el rubro de la matricula solo si esta gurdada
        if(rubroMatricula!=null)
        {
            getCmbRubroMatricula().setSelectedItem(rubroMatricula);
            SeleccionaDescuentoRubroEnum(rubroMatricula.getTipoDescuentoEnum());
            getTxtNombreDescuento().setText(rubroMatricula.getNombreDescuento());
            getSpnPorcentaje().setValue(rubroMatricula.getProcentajeDescuento());
        }
        
        //Desahabilitar los componentes que ya no puede editar
        getChkBeca().setEnabled(false);
        getChkNinguno().setEnabled(false);
        getChkOtro().setEnabled(false);
        
        getTxtNombreDescuento().setEnabled(false);
        getSpnPorcentaje().setEnabled(false);
        
    }

    /**
     * Validaciones de los datos de la matricula antes de grabar
     * @return 
     */
    private boolean validacionDatosGrabar() {
        if(estudianteInscrito.getEstudiante()==null)
        {
            DialogoCodefac.mensaje("Advertencia","Seleccione un estudiante para matricular",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        return true;
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
