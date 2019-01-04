/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarClientes;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarEstudiantes;
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class MigrarClientesModel extends MigrarModel {

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        setTitle("Migrar Clientes");
    }
    
    

    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        return new ExcelMigrar.MigrarInterface() {
            @Override
            public boolean procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel {
                try {
                    Persona cliente = new Persona();
                    cliente.setIdentificacion((String) fila.get(ExcelMigrarEstudiantes.Enum.IDENTIFICACION.posicion).valor);
                    cliente.setNombres((String) fila.get(ExcelMigrarEstudiantes.Enum.NOMBRES.posicion).valor);
                    cliente.setApellidos((String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor);

                    String genero = (String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor;

                    //TODO: Ver si es necesario agregar el genero al momento de genera los clientes
                    /*if (genero.toLowerCase().equals("femenino")) {
                        estudiante.set(GeneroEnum.FEMENINO.getEstado());
                    } else {
                        estudiante.setGenero(GeneroEnum.FEMENINO.getEstado());
                    }*/ 

                    cliente.setApellidos((String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor);
                    ServiceFactory.getFactory().getPersonaServiceIf().grabar(cliente);
                    
                    
                    //ServiceFactory.getFactory().getEstudianteServiceIf().grabar(cliente);

                    return true;
                } catch (ServicioCodefacException ex) {
                    //Logger.getLogger(MigrarEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(MigrarClientesModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
            }
        };
    }
    

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarClientes();
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
