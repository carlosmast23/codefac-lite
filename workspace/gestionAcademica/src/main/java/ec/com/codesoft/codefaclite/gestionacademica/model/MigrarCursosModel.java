/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarCursos;
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class MigrarCursosModel extends MigrarModel {

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        setTitle("Migración Cursos");
    }
    

    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        return new ExcelMigrar.MigrarInterface() {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel {
                try {
                    NivelAcademico curso=new NivelAcademico();
                    curso.setNombre((String) fila.getByEnum(ExcelMigrarCursos.Enum.NOMBRE).valor);
                    curso.setDescripcion((String) fila.getByEnum(ExcelMigrarCursos.Enum.DESCRIPCION).valor);
                    curso.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                    
                    String nombreNivel=(String) fila.getByEnum(ExcelMigrarCursos.Enum.NIVEL).valor;
                    String nombrePeriodo=(String) fila.getByEnum(ExcelMigrarCursos.Enum.PERIODO).valor;
                    
                    Nivel nivel=ServiceFactory.getFactory().getNivelServiceIf().obtenerNivelPorNombreYEstado(nombreNivel, GeneralEnumEstado.ACTIVO);
                    if(nivel==null)
                    {
                        throw new ExcelMigrar.ExcepcionExcel("No existe el nivel en el sistema");
                    }
                    curso.setNivel(nivel);
                    
                    Periodo periodo=ServiceFactory.getFactory().getPeriodoServiceIf().buscarPorNombreyEstado(nombrePeriodo, GeneralEnumEstado.ACTIVO);
                    if(periodo==null)
                    {
                        throw new ExcelMigrar.ExcepcionExcel("No existe el periodo en el sistema");
                    }
                    curso.setPeriodo(periodo); 
                    
                    
                    NivelAcademico cursoTmp=ServiceFactory.getFactory().getNivelAcademicoServiceIf().obtenerPorNombreYEstadoYPeriodo(curso.getNombre(), GeneralEnumEstado.ACTIVO, periodo);
                    if(cursoTmp!=null)
                    {
                        throw new ExcelMigrar.ExcepcionExcel("Ya existe un curso con el mismo nombre para ese periodo");
                    }
                        
                    
                    ServiceFactory.getFactory().getNivelAcademicoServiceIf().grabar(curso);
                   
                } catch (RemoteException ex) {
                    Logger.getLogger(MigrarCursosModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(MigrarCursosModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                }
            }
        };
    }

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarCursos();
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
    
}
