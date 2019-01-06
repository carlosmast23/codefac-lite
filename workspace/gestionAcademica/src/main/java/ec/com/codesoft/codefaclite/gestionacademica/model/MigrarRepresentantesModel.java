/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar.ExcepcionExcel;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarClientes;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarRepresentantes;
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class MigrarRepresentantesModel extends MigrarModel {

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        setTitle("Migrar Representantes");
    }
    
    private void datosRequeridos(Persona representante) throws ExcelMigrar.ExcepcionExcel
    {
        if(representante.getIdentificacion()==null || representante.getIdentificacion().isEmpty())
        {
            throw new ExcelMigrar.ExcepcionExcel("La identificación es un dato requerido");
        }
    }

    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        
        return new ExcelMigrar.MigrarInterface()  {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel{
                try {
                    Persona representante=new Persona();
                    representante.setIdentificacion((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.IDENTIFICACION).valor);
                    representante.setNombres((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.NOMBRES).valor);
                    representante.setApellidos((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.APELLIDOS).valor);
                    
                    representante.setDireccion((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.DIRECCION).valor);
                    representante.setTelefonoConvencional((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.TELEFONO).valor);
                    representante.setTelefonoCelular((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.CELULAR).valor);
                    representante.setCorreoElectronico((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.CORREO).valor);
                    
                    representante.setTipoEnum(OperadorNegocioEnum.CLIENTE);
                    representante.setObligadoLlevarContabilidadEnum(EnumSiNo.NO);
                    
                    representante.setContactoClienteEnum(EnumSiNo.NO);
                    representante.setTipClienteEnum(Persona.TipoClienteEnum.CLIENTE);//TODO: VALOR SETEADO
                    
                    //Todo: por el momento solo dejo considerado para estos 2 casos
                    if(representante.getIdentificacion().length()>10)
                    {
                        representante.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.RUC);
                    }
                    else
                    {
                        representante.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.RUC);
                    }
                    
                    String razonSocialStr=(String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.RAZON_SOCIAL).valor;
                    //Si no tiene razon social ingresada construyo con los nombres y apellidos
                    if(razonSocialStr==null || razonSocialStr.isEmpty())
                    {
                        representante.setRazonSocial(representante.getNombres()+" "+representante.getApellidos());
                    }
                    else
                    {
                        representante.setRazonSocial((String) fila.getByEnum(ExcelMigrarRepresentantes.Enum.RAZON_SOCIAL).valor);
                    }
                    
                    Persona representanteTmp=ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacionYestado(representante.getIdentificacion(),GeneralEnumEstado.ACTIVO);
                    if(representanteTmp!=null)
                    {
                        //ExcepcionExcel excepcionExcel = new ExcepcionExcel("");
                        
                        throw new ExcelMigrar.ExcepcionExcel("El dato ya se encuentra registrado en el sistema");
                    }
                    
                    datosRequeridos(representante);
                    
                    ServiceFactory.getFactory().getPersonaServiceIf().grabar(representante);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(MigrarRepresentantesModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(MigrarRepresentantesModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarRepresentantes(); 
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
