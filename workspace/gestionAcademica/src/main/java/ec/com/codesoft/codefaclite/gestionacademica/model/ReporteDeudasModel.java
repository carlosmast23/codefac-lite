/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.other.NotificacionDeudaImprimir;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteDeudasPanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteDeudasData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesMap;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteDeudasModel extends ReporteDeudasPanel {

    Map parameters = new HashMap();
    //private boolean banderaNiveles = false;
    //private boolean banderaRubros = false;
    private List<RubrosNivel> listaRubros;
    private Periodo periodoActivo;

    private DefaultListModel<RubroPlantillaMes> modeloLista;
    private List<ReporteDeudasData> data;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        modeloLista = new DefaultListModel();
        
        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                if (periodo != null) {
                    cargarNivelesPeriodo(periodo, getCmbNivelAcademico());
                    cargarMesesPeriodo(periodo);
                }
            }

        });
        listener();
        try {
            periodoActivo = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerUnicoPeriodoActivo();
            Periodo p1 = new Periodo();
            p1.setNombre("Seleccione:");
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodosSinEliminar();
            getCmbPeriodo().removeAllItems();
            getCmbPeriodo().addItem(p1);
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
            getCmbPeriodo().setSelectedItem(periodoActivo);

            getCmbTipoRubroPorMes().removeAllItems();
            List<CatalogoProducto> tipoRubros = ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerPorModulo(ModuloCodefacEnum.GESTIONA_ACADEMICA);
            for (CatalogoProducto catalogo : tipoRubros) {
                getCmbTipoRubroPorMes().addItem(catalogo);
            }
            
            UtilidadesComboBox.llenarComboBox(getCmbTipoReporte(),ReporteDeudasData.TipoReporteEnum.values());            
            

        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //getChkTodosNiveles().setSelected(true);
        if (getChkTodosNiveles().isSelected()) {
            //banderaNiveles = true;
            getCmbNivelAcademico().setEnabled(false);
        }

        getChkTodosNiveles().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    //banderaNiveles = true;
                    getCmbNivelAcademico().setEnabled(false);
                } else {
                    //banderaNiveles = false;
                    getCmbNivelAcademico().setEnabled(true);
                }
            }
        });

        // getChkTodosRubros().setSelected(true);
        if (getChkTodosRubros().isSelected()) {
            //banderaRubros = true;
            getCmbTipoRubroPorMes().setEnabled(false);
        }

        getChkTodosRubros().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    //banderaRubros = true;
                    getCmbTipoRubroPorMes().setEnabled(false);
                } else {
                    //banderaRubros = false;
                    getCmbTipoRubroPorMes().setEnabled(true);

                }
            }
        });

    }

    private void cargarMesesPeriodo(Periodo periodo) {
        List<RubroPlantillaMes> Meses = periodo.obtenerTodosMesesGenerar();
        getCmbMesFiltro().removeAllItems();
        for (RubroPlantillaMes mes : Meses) {
            getCmbMesFiltro().addItem(mes);
        }
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

    private void generarConsulta() {
        try {
            Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
            CatalogoProducto catalogoProducto = (CatalogoProducto) getCmbTipoRubroPorMes().getSelectedItem();
            List<RubroPlantillaMes> mesesSeleccionados = obtenerMesesEnum();
            List<RubroEstudiante> dataRubro;

            NivelAcademico nivelBuscar = null;

            if (getChkTodosNiveles().isSelected() == false) {
                nivelBuscar = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
            }
            EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
            List<EstudianteInscrito> dataEstudiante = na.buscarPorNivelAcademico(periodo, nivelBuscar);
            data = new ArrayList<ReporteDeudasData>();

            for (EstudianteInscrito estudiante : dataEstudiante) {

                RubroEstudianteServiceIf rs = ServiceFactory.getFactory().getRubroEstudianteServiceIf();

                if (getChkTodosRubros().isSelected() == false) {
                    //if (banderaRubros == false) {
                    dataRubro = rs.buscarRubrosMes(estudiante, periodo, catalogoProducto, mesesSeleccionados);
                } else {
                    dataRubro = rs.buscarRubrosMes(estudiante, periodo, null, mesesSeleccionados);
                }

                // comparamos si el estudiante tiene rubros
                if (!dataRubro.isEmpty()) {
                    for (RubroEstudiante re : dataRubro) {
                        data.add(new ReporteDeudasData(
                                estudiante.getNivelAcademico().getNombre(),
                                estudiante.getEstudiante().getCedula(),
                                estudiante.getEstudiante().getNombreCompleto(),
                                re.getRubroNivel().getNombre(),
                                re.getSaldo().toString()
                        ));
                    }
                }

            }

        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void imprimir() {

        if (data == null || data.size() == 0) {
            DialogoCodefac.mensaje("No existen datos para el reporte", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }

        ReporteDeudasData.TipoReporteEnum tipoReporteEnum=(ReporteDeudasData.TipoReporteEnum) getCmbTipoReporte().getSelectedItem();
        InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream(tipoReporteEnum.getNombreReporte());
        Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();

        NivelAcademico nivela = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
        if (periodo != null) {
            parameters.put("periodo", periodo.getNombre());
        } else {
            parameters.put("periodo", "TODOS");
        }
        if (nivela != null) {
            parameters.put("nivelacademico", nivela.getNombre());
        } else {
            parameters.put("nivelacademico", "TODOS");
        }
        
        ///// Imprimir reporte de excel o pdf
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String nombreCabeceras[] = {"Identificación", "Estudiante", "Nivel Academico", "Rubro", "Valor"};
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, construirDataResumidoReporte(tipoReporteEnum, data));
                    excel.abrirDocumento();
                } catch (IOException ex) {
                    Logger.getLogger(ReporteDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(ReporteDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ReporteDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void pdf() {
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, construirDataResumidoReporte(tipoReporteEnum, data), panelPadre, "Reporte Deudas");
            }
        });

    }
    
    private List<ReporteDeudasData> construirDataResumidoReporte(ReporteDeudasData.TipoReporteEnum tipoReporteEnum,List<ReporteDeudasData> dataListOriginal) 
    {
        if(tipoReporteEnum.equals(ReporteDeudasData.TipoReporteEnum.DETALLADO))
        {
            return dataListOriginal;
        }
        
        Map<String,ReporteDeudasData> mapResultadoTemp=new LinkedHashMap<String,ReporteDeudasData>();
        
        for (ReporteDeudasData dataOriginal : dataListOriginal) 
        {
            ReporteDeudasData dataNueva=mapResultadoTemp.get(dataOriginal.getEstudiante());
            if(dataNueva==null)
            {
                dataNueva=new ReporteDeudasData(dataOriginal.getNivelAcademicoEstudiante(), dataOriginal.getCedulaEstudiante(),dataOriginal.getEstudiante(),dataOriginal.getRubro());                
                mapResultadoTemp.put(dataOriginal.getEstudiante(), dataNueva);
            }
            
            //sumar el valor del total
            dataNueva.sumarValor(dataOriginal.getValor());
        }
        
        //devolver el valor como una lista
        List<ReporteDeudasData> respuesta=UtilidadesMap.castMapToList(mapResultadoTemp);
               
        return respuesta;
        //return dataListOriginal;
        
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
        this.listaRubros = new ArrayList<RubrosNivel>();
    }

//    @Override
    public String getNombre() {
        return "Deudas por Curso";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.x176zt173ozn";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarNivelesPeriodo(Periodo periodo, JComboBox<NivelAcademico> comboNivel) {
        try {

            NivelAcademicoServiceIf servicio = ServiceFactory.getFactory().getNivelAcademicoServiceIf();
            //Map<String, Object> mapBusqueda = new HashMap<String, Object>();
            //mapBusqueda.put("periodo", periodo);
            //mapBusqueda.put("estado",GeneralEnumEstado.ACTIVO.getEstado());

            List<NivelAcademico> resultados = servicio.obtenerTodosActivosPorPeriodo(periodo);

            comboNivel.removeAllItems();
            if (!resultados.isEmpty()) {
                for (NivelAcademico resultado : resultados) {
                    comboNivel.addItem(resultado);

                }
            }

        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class
                    .getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void agregarRubroLista(List<RubrosNivel> rubros) {
        for (RubrosNivel rubro : rubros) {
            if (!listaRubros.contains(rubro)) {
                listaRubros.add(rubro);
                System.out.println("rubro ==============> " + rubro.getNombre());
            }
        }
    }

    private List<RubroPlantillaMes> obtenerMesesEnum() {

        List<RubroPlantillaMes> listaMeses = new ArrayList<RubroPlantillaMes>();

        for (int i = 0; i < modeloLista.getSize(); i++) {
            RubroPlantillaMes rubroPlantillaMes = modeloLista.get(i);
            listaMeses.add(rubroPlantillaMes);
        }

        return listaMeses;

    }

    private void listener() {
        listenerBotones();
    }

    private void listenerBotones() {
        getBtnAgregarMes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubroPlantillaMes rubroPlantillaMeS = (RubroPlantillaMes) getCmbMesFiltro().getSelectedItem();
                modeloLista.addElement(rubroPlantillaMeS);
                getLstFiltrosMes().setModel(modeloLista);

            }
        });

        getBtnEliminarMes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getLstFiltrosMes().getSelectedIndex() >= 0) {
                    RubroPlantillaMes rubroPlantillaMes = (RubroPlantillaMes) modeloLista.get(getLstFiltrosMes().getSelectedIndex());
                    modeloLista.removeElement(rubroPlantillaMes);
                    getLstFiltrosMes().setModel(modeloLista);
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarConsulta();
                Vector<String> titulo = new Vector<>();
                titulo.add("Identificación");
                titulo.add("Estudiante");
                titulo.add("Nivel Académico");
                titulo.add("Rubro");
                titulo.add("Valor");
                DefaultTableModel modeloTablaDeudas = new DefaultTableModel(titulo, 0);
                for (ReporteDeudasData reporteDeudasData : data) {
                    Vector<String> fila = new Vector<String>();

                    fila.add(reporteDeudasData.getCedulaEstudiante());
                    fila.add(reporteDeudasData.getEstudiante());
                    fila.add(reporteDeudasData.getNivelAcademicoEstudiante());
                    fila.add(reporteDeudasData.getRubro());
                    fila.add(reporteDeudasData.getValor());
                    modeloTablaDeudas.addRow(fila);
                }
                getTblDeudas().setModel(modeloTablaDeudas);

            }
        });

        getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel model = new DefaultListModel();
                model.clear();
                getLstFiltrosMes().setModel(model);
            }
        });
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
