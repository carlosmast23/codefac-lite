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
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.controlador.panel.MigrarPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
 ;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
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
public class MigrarEstudiantesModel extends MigrarModel{
    
    //private List<Estudiante> estudiantes;
    //private ExcelMigrarEstudiantes excelMigrarEstudiantes;
    //private JCheckBox chkPermitirGrabarSinRepresentante;

    @Override
    public void agregarOtrosComponentes() {
        //chkPermitirGrabarSinRepresentante=new JCheckBox("Representantes requeridos");
        //agregarComponenteOtroPanel(chkPermitirGrabarSinRepresentante);
    }
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite   {
       super.iniciar();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite   {
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
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        ExcelMigrar.MigrarInterface interfaceMigrar = new ExcelMigrar.MigrarInterface() {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel,ExcelMigrar.ExcepcionExcelRegistroDuplicado {
                try {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setCedula(((String) fila.get(ExcelMigrarEstudiantes.Enum.IDENTIFICACION.posicion).valor).trim());
                    estudiante.setNombres(((String) fila.get(ExcelMigrarEstudiantes.Enum.NOMBRES.posicion).valor).trim());
                    estudiante.setApellidos(((String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor).trim());
                    estudiante.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                    
                    ExcelMigrar.CampoResultado fechaCampo=fila.get(ExcelMigrarEstudiantes.Enum.FECHA_NACIMIENTO.posicion);                    
                    if(fechaCampo!=null)
                    {
                        try
                        {
                            java.util.Date fechaNacimiento=(java.util.Date) fechaCampo.valor;
                            estudiante.setFechaNacimiento(new java.sql.Date(fechaNacimiento.getTime()));
                        }
                        catch(Exception ex)
                        {
                            System.out.println("error al leer la fecha"+ex.getMessage());
                        }
                    }

                    procesarGenero(fila.getByEnum(ExcelMigrarEstudiantes.Enum.GENERO),estudiante);
                    

                    //Verificar si existe el representante1
                    procesarRepresentantes1(fila.getByEnum(ExcelMigrarEstudiantes.Enum.IDENTIFICACION_REPRESENTATE_1), estudiante);
                    
                    //Verificar si existe el representante1
                   // String identificacionRepresentante2=(String) fila.getByEnum(ExcelMigrarEstudiantes.Enum.IDENTIFICACION_REPRESENTATE_2).valor;
                    procesarRepresentantes2(fila.getByEnum(ExcelMigrarEstudiantes.Enum.IDENTIFICACION_REPRESENTATE_2), estudiante);
                    
                    
                    
                    //Verificar si existe el curso a inscribir
                    String nombreCurso=(String) fila.getByEnum(ExcelMigrarEstudiantes.Enum.CURSO_ACTUAL).valor;
                    NivelAcademico nivelAcademico=ServiceFactory.getFactory().getNivelAcademicoServiceIf().obtenerPorNombreYEstado(nombreCurso,GeneralEnumEstado.ACTIVO); //TODO: Verificarsi para esta migracion tengo que mandar tambien el periodo
                    
                    if(nivelAcademico==null)
                    {
                        throw new ExcelMigrar.ExcepcionExcel("El curso ingresado no existe para registrar el estudiante");
                    }
                    
                    //Verificar que no este creado el estudiante
                    Estudiante estudianteTmp=ServiceFactory.getFactory().getEstudianteServiceIf().buscarPorCedulayEstado(estudiante.getCedula(), GeneralEnumEstado.ACTIVO);
                    if(estudianteTmp==null)
                    {
                        estudiante=ServiceFactory.getFactory().getEstudianteServiceIf().grabar(estudiante);
                    }
                    else
                    {
                        estudiante=estudianteTmp;
                    }                    
                    
                    EstudianteInscrito estudianteInscritoTmp= ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerPorEstudianteYNivelYEstado(estudiante, nivelAcademico, GeneralEnumEstado.ACTIVO);
                    
                    if(estudianteInscritoTmp!=null)
                    {
                        throw new ExcelMigrar.ExcepcionExcelRegistroDuplicado("El estudiante ya esta inscrito");
                    }
                    
                    //Incribir al estudiante en el curso
                    EstudianteInscrito estudianteInscrito=new EstudianteInscrito();
                    estudianteInscrito.setEstudiante(estudiante);
                    estudianteInscrito.setNivelAcademico(nivelAcademico);
                    estudianteInscrito.setEstado(GeneralEnumEstado.ACTIVO.getEstado());                    
                    ServiceFactory.getFactory().getEstudianteInscritoServiceIf().grabar(estudianteInscrito);
 

                } catch (ServicioCodefacException ex) {
                    //Logger.getLogger(MigrarEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                    
                }catch(Exception ex)
                {
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                }


            }
            
            
            
            public void procesarGenero(ExcelMigrar.CampoResultado campo,Estudiante estudiante) throws ExcelMigrar.ExcepcionExcel,ExcelMigrar.ExcepcionExcelRegistroDuplicado
            {
                String genero = (String) campo.valor;

                if (genero.trim().toLowerCase().equals("femenino") || genero.trim().toLowerCase().equals("f")) {
                    estudiante.setGenero(GeneroEnum.FEMENINO.getEstado());
                } else if (genero.trim().toLowerCase().equals("masculino") || genero.trim().toLowerCase().equals("m") ) {
                    estudiante.setGenero(GeneroEnum.MASCULINO.getEstado());
                } else {
                    throw new ExcelMigrar.ExcepcionExcel("No se reconece un formato valido para el campo genero");
                }
            }
            
            
            public void procesarRepresentantes2(ExcelMigrar.CampoResultado campo,Estudiante estudiante) throws ExcelMigrar.ExcepcionExcel,ExcelMigrar.ExcepcionExcelRegistroDuplicado,    ServicioCodefacException
            {
                
                String identificacionRepresentante2 = (String) campo.valor;

                if (!identificacionRepresentante2.isEmpty()) {
                    Persona representante2 = ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacionYestado(identificacionRepresentante2, GeneralEnumEstado.ACTIVO);

                    if (representante2 == null) {
                        if(campo.campoEnum.getCampoRequerido())
                        {
                            throw new ExcelMigrar.ExcepcionExcel("El presentante 2 ingresado no existe en el sistema");
                        }
                    } else {
                        estudiante.setRepresentante2(representante2);
                    }
                }
            }
            
            
            public void procesarRepresentantes1(ExcelMigrar.CampoResultado campo,Estudiante estudiante) throws ExcelMigrar.ExcepcionExcel,ExcelMigrar.ExcepcionExcelRegistroDuplicado,    ServicioCodefacException
            {
                
                String identificacionRepresentante1 = (String) campo.valor;

                if (!identificacionRepresentante1.isEmpty()) {
                    Persona representante1 = ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacionYestado(identificacionRepresentante1, GeneralEnumEstado.ACTIVO);

                    if (representante1 == null) {
                        if(campo.campoEnum.getCampoRequerido())
                        {
                            throw new ExcelMigrar.ExcepcionExcel("El presentante 1 ingresado no existe en el sistema");
                        }
                    } else {
                        estudiante.setRepresentante(representante1);
                    }
                }
            }
            
            
        };
        return interfaceMigrar;
    }

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarEstudiantes();
    }

    @Override
    public InputStream getInputStreamExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTitulo() {
        return "Migrar Estudiantes"; //To change body of generated methods, choose Tools | Templates.
    }

    
}
