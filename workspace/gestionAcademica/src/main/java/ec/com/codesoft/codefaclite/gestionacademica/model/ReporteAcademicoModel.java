/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteAcademicoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
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

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteAcademicoModel extends ReporteAcademicoPanel {

    private DefaultTableModel modeloTablaEstudiantes;
    private List<EstudianteInscrito> dataEstudiantes;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {

        try {
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbNivelAcademico().removeAllItems();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                if (periodo != null) {
                    cargarNivelesPeriodo(periodo, getCmbNivelAcademico());
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<String> titulo = new Vector<>();
                    titulo.add("Cedula");
                    titulo.add("Nombres");
                    titulo.add("Apellidos");
                    titulo.add("Email");
                    titulo.add("Telefono");
                    titulo.add("Representante");
                    titulo.add("Nivel Academico");

                    modeloTablaEstudiantes = new DefaultTableModel(titulo, 0);

                    EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
                    dataEstudiantes = na.obtenerEstudiantesInscritos((NivelAcademico) getCmbNivelAcademico().getSelectedItem());
                    for (EstudianteInscrito est : dataEstudiantes) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(est.getEstudiante().getCedula());
                        fila.add(est.getEstudiante().getNombres());
                        fila.add(est.getEstudiante().getApellidos());
                        fila.add(est.getEstudiante().getEmail());
                        fila.add(est.getEstudiante().getTelefono());
                        if (est.getEstudiante().getRepresentante() != null) {
                            fila.add(est.getEstudiante().getRepresentante().getNombres() + " " + est.getEstudiante().getRepresentante().getApellidos());
                        } else {
                            fila.add("s/n");
                        }
                        fila.add(est.getNivelAcademico().getNombre());
                        modeloTablaEstudiantes.addRow(fila);

                    }

                    getTblEstudiantes().setModel(modeloTablaEstudiantes);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Reporte Estudiantes Nivel Académico";
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
}
