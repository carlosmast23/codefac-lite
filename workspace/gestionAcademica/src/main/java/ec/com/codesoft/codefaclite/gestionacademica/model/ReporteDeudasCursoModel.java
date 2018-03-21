/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteDeudasCursoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
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
public class ReporteDeudasCursoModel extends ReporteDeudasCursoPanel {

    Map parameters = new HashMap();
    private boolean banderaNiveles = false;
    private boolean banderaRubros = false;
    //private Map<EstudianteInscrito, List<RubroEstudiante>> mapEstudianteRubros;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        try {
            Periodo p1 = new Periodo();
            p1.setNombre("Seleccione:");
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbPeriodo().removeAllItems();
            getCmbPeriodo().addItem(p1);
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<String> titulo = new Vector<>();

                    titulo.add("Nivel Academico");
                    titulo.add("Rubro");
                    titulo.add("Abono");
                    titulo.add("Deuda");
                    BigDecimal acumAbono = BigDecimal.ZERO;
                    BigDecimal acumDeuda = BigDecimal.ZERO;
                    BigDecimal auxAbono = BigDecimal.ZERO;
                    BigDecimal auxDeuda = BigDecimal.ZERO;
                    DefaultTableModel modeloTablaDeudas = new DefaultTableModel(titulo, 0);
                    RubroEstudianteServiceIf na = ServiceFactory.getFactory().getRubroEstudianteServiceIf();
                    List<Object[]> dataEstudiante = na.obtenerRubroPeriodoGrupo((Periodo) getCmbPeriodo().getSelectedItem());
                    for (Object[] obj : dataEstudiante) {
                        NivelAcademico n = (NivelAcademico) obj[0];
                        RubrosNivel r = (RubrosNivel) obj[1];
                        BigDecimal b = (BigDecimal) obj[2];
                        Vector<String> fila = new Vector<String>();
                        fila.add(n.getNombre());
                        fila.add(r.getNombre());
                        fila.add(b.toString());
                        modeloTablaDeudas.addRow(fila);

                    }

                    /* List<NivelAcademico> dataEstudiante = na.obtenerRubroPeriodo((Periodo) getCmbPeriodo().getSelectedItem());
                    //obtengo los niveles del periodo
                    for (NivelAcademico nivel : dataEstudiante) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(nivel.getNombre());
                        modeloTablaDeudas.addRow(fila);

                        List<RubrosNivel> dataEstudiante2 = na.obtenerRubroNivel(nivel);
                        for (RubrosNivel rubro2 : dataEstudiante2) {
                            //aqui obtengo rubros por nivel
                            Vector<String> fila2 = new Vector<String>();
                            acumAbono = BigDecimal.ZERO;
                            acumDeuda = BigDecimal.ZERO;
                            //modeloTablaDeudas.addRow(fila2);
                            List<RubroEstudiante> dataEstudiante3 = na.obtenerRubro(nivel, rubro2);
                            for (RubroEstudiante rubro3 : dataEstudiante3) {
                                //aqui sumo los ruubros

                                if (rubro3.getEstadoFactura().compareTo("s") == 0) {
                                    auxAbono = BigDecimal.ZERO;
                                    auxDeuda = rubro3.getRubroNivel().getValor();
                                } else if (rubro3.getEstadoFactura().compareTo("p") == 0) {
                                    auxAbono = rubro3.getSaldo();
                                    auxDeuda = rubro3.getRubroNivel().getValor().subtract(rubro3.getSaldo());
                                }
                                acumAbono = acumAbono.add(auxAbono);
                                acumDeuda = acumDeuda.add(auxDeuda);

                      
                            }
                            fila2.add("-");
                            fila2.add(rubro2.getNombre());
                            fila2.add(acumAbono.toString());
                            fila2.add(acumDeuda.toString());
                            modeloTablaDeudas.addRow(fila2);

                        }

                    }*/
                    getTblDeudas().setModel(modeloTablaDeudas);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteDeudasCursoModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        return "Reporte Deudas por Curso";
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

}
