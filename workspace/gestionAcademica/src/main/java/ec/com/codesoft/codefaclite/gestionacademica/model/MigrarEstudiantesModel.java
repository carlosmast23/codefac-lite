/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarEstudiantes;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.MigrarEstudiantesPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xalan.xsltc.compiler.util.StringStack;

/**
 *
 * @author Carlos
 */
public class MigrarEstudiantesModel extends MigrarEstudiantesPanel{
    
    private List<Estudiante> estudiantes;
    private ExcelMigrarEstudiantes excelMigrarEstudiantes;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerBotones() {
        
        getBtnMigrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excelMigrarEstudiantes.migrar(interfaceMigrar);
                //DefaultTableModel modelo=excelMigrarEstudiantes.construirTabla();
                excelMigrarEstudiantes.construirTabla(getTblDatos());
                //getTblDatos().setModel(modelo);
                
                
                                
                
            }
        });
        
        getBtnCargarExcel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String ubicacionRespaldo="";
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter2 = new FileNameExtensionFilter("Archivo Excel", "xls");
                FileFilter filter = new FileNameExtensionFilter("Archivo Excel Nuevo", "xlsx");
                fileChooser.setFileFilter(filter);
                fileChooser.addChoosableFileFilter(filter2);
                int seleccion = fileChooser.showDialog(null, "Seleccionar el archivo");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        ubicacionRespaldo = "" + fileChooser.getSelectedFile();
                        getTxtRutaArchivo().setText("" + ubicacionRespaldo);
                        construirDatosExcel(fileChooser.getSelectedFile());
                        //construirTabla();
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }

            }
        });
    }
    
    private void construirDatosExcel(File archivo)
    {
        try {
            estudiantes=new ArrayList<Estudiante>();
            
            excelMigrarEstudiantes=new ExcelMigrarEstudiantes(archivo);
                        
            List<ExcelMigrar.FilaResultado> resultado=excelMigrarEstudiantes.leerDatos();
            //getTblDatos().setModel(excelMigrarEstudiantes.construirTabla());
            excelMigrarEstudiantes.construirTabla(getTblDatos());
            
                       
            
            /*for (ExcelMigrar.FilaResultado filaResultado : resultado) {
                Estudiante estudiante=new Estudiante();
                estudiante.setCedula((String) filaResultado.fila.get(0).valor);
                estudiante.setNombres((String) filaResultado.fila.get(1).valor);
                estudiante.setApellidos((String) filaResultado.fila.get(2).valor);
                estudiantes.add(estudiante);
            }*/
        } catch (ExcelMigrar.ExcepcionMigrar ex) {
            Logger.getLogger(MigrarEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
        
    }
    
    private ExcelMigrar.MigrarInterface interfaceMigrar=new ExcelMigrar.MigrarInterface() {
        @Override
        public boolean procesar(ExcelMigrar.FilaResultado fila) throws Exception {
            try {
                Estudiante estudiante=new Estudiante();
                estudiante.setCedula((String) fila.get(ExcelMigrarEstudiantes.Enum.IDENTIFICACION.posicion).valor);
                estudiante.setNombres((String) fila.get(ExcelMigrarEstudiantes.Enum.NOMBRES.posicion).valor);
                estudiante.setApellidos((String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor);
                
                String genero=(String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor;
                
                if(genero.toLowerCase().equals("femenino"))
                {
                    estudiante.setGenero(GeneroEnum.FEMENINO.getEstado());
                }
                else
                {
                    estudiante.setGenero(GeneroEnum.FEMENINO.getEstado());
                }
                
                estudiante.setApellidos((String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor);
                ServiceFactory.getFactory().getEstudianteServiceIf().grabar(estudiante);                
                
                return true;
            } catch (ServicioCodefacException ex) {
                //Logger.getLogger(MigrarEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex.getMessage());
                
            } catch (RemoteException ex) {
                Logger.getLogger(MigrarEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
            
        }
    };
    /*
    private void construirTabla()
    {
        DefaultTableModel  modeloDefecto=new DefaultTableModel(tituloTabla,0);
         
        for (int i = 0; i < 10; i++) {
            modeloDefecto.addRow(new String[]{"1","2","3"});
        }
        
        getTblDatos().setModel(modeloDefecto);
        
    }*/
    
    
}
