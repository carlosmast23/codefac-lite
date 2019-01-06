/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarEstudiantes;
import ec.com.codesoft.codefaclite.controlador.panel.MigrarPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public abstract class MigrarModel extends MigrarPanel{
    
    /**
     * Archivo donde se selecciona 
     */
    //private File archivoMigrar;
    private ExcelMigrar excelMigrar;
    
    //public abstract void construirDatosExcel(File archivo);
    public abstract ExcelMigrar.MigrarInterface getInterfaceMigrar();
    public abstract ExcelMigrar getExcelMigrar() ;
    
    //private ExcelMigrar excelMigrar;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotonesInit();        
    }
    
    private void listenerBotonesInit()
    {
        getBtnRecargarDatos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getTxtRutaArchivo().getText().isEmpty())
                {
                    DialogoCodefac.mensaje("Error","No se puede recargar porque no existe un archivo cargado", DialogoCodefac.MENSAJE_INCORRECTO);
                }
                else
                {
                    construirDatosExcel(new File(getTxtRutaArchivo().getText()));
                }
            }
        });
        
        getBtnMigrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excelMigrar.migrar(getInterfaceMigrar());
                //DefaultTableModel modelo=excelMigrarEstudiantes.construirTabla();
                excelMigrar.construirTabla(getTblDatos());
                //getTblDatos().setModel(modelo);                
                
            }
        });
        
        getBtnCargarExcel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ubicacionRespaldo = "";
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
            construirExcelMigrar(archivo);
            excelMigrar.leerDatos();
            //getTblDatos().setModel(excelMigrarEstudiantes.construirTabla());
            this.excelMigrar.construirTabla(getTblDatos());
            
            
            
            /*for (ExcelMigrar.FilaResultado filaResultado : resultado) {
            Estudiante estudiante=new Estudiante();
            estudiante.setCedula((String) filaResultado.fila.get(0).valor);
            estudiante.setNombres((String) filaResultado.fila.get(1).valor);
            estudiante.setApellidos((String) filaResultado.fila.get(2).valor);
            estudiantes.add(estudiante);
            }*/
        } catch (ExcelMigrar.ExcepcionMigrar ex) {
            Logger.getLogger(MigrarModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
        
    }
    
    public void construirExcelMigrar(File archivo)
    {
        this.excelMigrar=getExcelMigrar();
        this.excelMigrar.setArchivoExel(archivo);
        this.excelMigrar.setHojaActual((int) getCmbNumeroHoja().getSelectedItem()-1);
        construirDatosRequeridos();
        
    }

    private void construirDatosRequeridos() {
        getPnlCamposRequeridos().removeAll();
        
        for (ExcelMigrar.CampoMigrarInterface campoMigrar : this.excelMigrar.obtenerCampos()) {
            JCheckBox jcheckBox=new JCheckBox(campoMigrar.getNombre());
            jcheckBox.setSelected(true);
            getPnlCamposRequeridos().add(jcheckBox);
            campoMigrar.setCampoRequerido(true);
            
            //AgregarListener para campos requeridos
            jcheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    campoMigrar.setCampoRequerido(jcheckBox.isSelected());
                }
            });
            
        }
        
        getPnlCamposRequeridos().revalidate();
        getPnlCamposRequeridos().repaint();

    }
    
    
}
