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
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.DeudasAgrupadasPorCursoPanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteDeudasCursoData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.fechaInicioMes;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo
 */
public class DeudasAgrupadasPorCursoModel extends DeudasAgrupadasPorCursoPanel {

    Map parameters = new HashMap();
    private boolean banderaNiveles = false;
    private boolean banderaRubros = false;
    //private Map<EstudianteInscrito, List<RubroEstudiante>> mapEstudianteRubros;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        iniciarComponentes();
        listener();

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
        try {
            ReporteDeudasCursoData.TipoReporteEnum tipoReporteEnum=(ReporteDeudasCursoData.TipoReporteEnum) getCmbTipoReporte().getSelectedItem();
            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream(tipoReporteEnum.getNombreReporte());

            RubroEstudianteServiceIf na = ServiceFactory.getFactory().getRubroEstudianteServiceIf();

            java.sql.Date fechaInicial = (getDateFechaInicio().getDate() != null) ? new java.sql.Date(getDateFechaInicio().getDate().getTime()) : null;
            java.sql.Date fechaFinal = (getDateFechaFin().getDate() != null) ? new java.sql.Date(getDateFechaFin().getDate().getTime()) : null;

            List<Object[]> dataEstudiante = na.obtenerRubroPeriodoGrupo((Periodo) getCmbPeriodo().getSelectedItem(), fechaInicial, fechaFinal);
            List<ReporteDeudasCursoData> data = new ArrayList<ReporteDeudasCursoData>();
            for (Object[] obj : dataEstudiante) {
                NivelAcademico n = (NivelAcademico) obj[0];
                RubrosNivel r = (RubrosNivel) obj[1];
                BigDecimal abono = (BigDecimal) obj[2];
                BigDecimal deuda = (BigDecimal) obj[3];

                data.add(new ReporteDeudasCursoData(
                        n.getNombre(),
                        r.getNombre(),
                        abono,
                        deuda
                ));
            }

            Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
            if (periodo != null) {
                parameters.put("periodo", periodo.getNombre());
            } else {
                parameters.put("periodo", "TODOS");
            }

            DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                @Override
                public void excel() {
                    try {
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Rubro", "Nivel", "Abono", "Deuda"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    } catch (Exception exc) {
                        DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                        exc.printStackTrace();
                    }
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Deudas por Curso");
                }
            });

        } catch (RemoteException ex) {
            Logger.getLogger(DeudasAgrupadasPorCursoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Deudas General por Curso";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.lq0hacnxyqt2";
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

    private void iniciarComponentes() {
        try {
            Periodo p1 = new Periodo();
            p1.setNombre("Seleccione:");
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodosSinEliminar();
            getCmbPeriodo().removeAllItems();
            getCmbPeriodo().addItem(p1);
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
            
            UtilidadesComboBox.llenarComboBox(getCmbTipoReporte(),ReporteDeudasCursoData.TipoReporteEnum.values());
            
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listener() {
        listenerBotones();
    }

    private void listenerBotones() {
        getBtnLimpiarFechaInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });

        getBtnLimpiarFechaFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFin().setDate(null);
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<String> titulo = new Vector<>();

                    titulo.add("Nivel Academico");
                    titulo.add("Rubro");
                    titulo.add("Abono");
                    titulo.add("Deuda");

                    DefaultTableModel modeloTablaDeudas = new DefaultTableModel(titulo, 0);
                    RubroEstudianteServiceIf na = ServiceFactory.getFactory().getRubroEstudianteServiceIf();

                    java.sql.Date fechaInicial = (getDateFechaInicio().getDate() != null) ? new java.sql.Date(getDateFechaInicio().getDate().getTime()) : null;
                    java.sql.Date fechaFinal = (getDateFechaFin().getDate() != null) ? new java.sql.Date(getDateFechaFin().getDate().getTime()) : null;

                    List<Object[]> dataEstudiante = na.obtenerRubroPeriodoGrupo((Periodo) getCmbPeriodo().getSelectedItem(), fechaInicial, fechaFinal);
                    for (Object[] obj : dataEstudiante) {
                        NivelAcademico n = (NivelAcademico) obj[0];
                        RubrosNivel r = (RubrosNivel) obj[1];
                        BigDecimal abono = (BigDecimal) obj[2];
                        BigDecimal deuda = (BigDecimal) obj[3];
                        Vector<String> fila = new Vector<String>();
                        fila.add(n.getNombre());
                        fila.add(r.getNombre());
                        fila.add(abono.toString());
                        fila.add(deuda.toString());
                        modeloTablaDeudas.addRow(fila);

                    }

                    getTblDeudas().setModel(modeloTablaDeudas);
                } catch (RemoteException ex) {
                    Logger.getLogger(DeudasAgrupadasPorCursoModel.class.getName()).log(Level.SEVERE, null, ex);
                }

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

}
