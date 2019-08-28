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
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    
    //TODO: COMPLETAR LOGICA PARA MIGRAR CREANDO LAS SUCURSALES O ESTABLECIMIENTOS
    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        return new ExcelMigrar.MigrarInterface() {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel {
                try {
                    Persona cliente = new Persona();
                    cliente.setIdentificacion(((String) fila.getByEnum(ExcelMigrarClientes.Enum.IDENTIFICACION).valor).trim());
                    cliente.setNombres((String) fila.getByEnum(ExcelMigrarClientes.Enum.NOMBRES).valor);
                    cliente.setApellidos((String) fila.getByEnum(ExcelMigrarClientes.Enum.APELLIDOS).valor);
                    cliente.setRazonSocial(((String) fila.getByEnum(ExcelMigrarClientes.Enum.RAZON_SOCIAL).valor).trim());
                    //cliente.setNombreLegal((String) fila.getByEnum(ExcelMigrarClientes.Enum.NOMBRE_COMERCIAL).valor);
                    
                    String telefono=(String) fila.getByEnum(ExcelMigrarClientes.Enum.TELEFONO).valor;
                    String celular=(String) fila.getByEnum(ExcelMigrarClientes.Enum.CELULAR).valor;
                    //cliente.setTelefonoConvencional(UtilidadesTextos.formatearTextoSinNingunEspacio(telefono));
                    //cliente.setTelefonoCelular((String) fila.getByEnum(ExcelMigrarClientes.Enum.CELULAR).valor);
                    
                    //cliente.setDireccion((String) fila.getByEnum(ExcelMigrarClientes.Enum.DIRECCION).valor);
                    
                    cliente.setCorreoElectronico((String) fila.getByEnum(ExcelMigrarClientes.Enum.CORREO).valor);
                    String nombreComercial=(String) fila.getByEnum(ExcelMigrarClientes.Enum.NOMBRE_COMERCIAL).valor;
                    
                    
                    cliente.setTipoEnum(OperadorNegocioEnum.CLIENTE);
                    cliente.setObligadoLlevarContabilidadEnum(EnumSiNo.NO);

                    cliente.setContactoClienteEnum(EnumSiNo.NO);
                    cliente.setTipClienteEnum(Persona.TipoClienteEnum.CLIENTE);//TODO: VALOR SETEADO
                    
                    
                    //Todo: por el momento solo dejo considerado para estos 2 casos
                    if (cliente.getIdentificacion().length() > 10) {
                        cliente.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.RUC);
                    } else {
                        cliente.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.CEDULA);
                    }
                    
                    //Todo: si la razon social es vacia entonces yo me encargo de setear ese campo automaticamente
                    if(cliente.getRazonSocial()==null || cliente.getRazonSocial().isEmpty())
                    {
                        cliente.setRazonSocial(cliente.getApellidos()+" "+cliente.getNombres());
                    }

                    //String genero = (String) fila.get(ExcelMigrarEstudiantes.Enum.APELLIDOS.posicion).valor;
                    PersonaEstablecimiento personaEstablecimiento=new PersonaEstablecimiento();
                    personaEstablecimiento.setCodigoSucursal("1");
                    personaEstablecimiento.setCorreoElectronico((String) fila.getByEnum(ExcelMigrarClientes.Enum.CORREO).valor);
                    personaEstablecimiento.setDireccion((String) fila.getByEnum(ExcelMigrarClientes.Enum.DIRECCION).valor);
                    personaEstablecimiento.setTelefonoConvencional((telefono!=null)?telefono.trim():"");
                    personaEstablecimiento.setTelefonoCelular((celular!=null)?celular.trim():"");
                    personaEstablecimiento.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);
                    personaEstablecimiento.setNombreComercial((nombreComercial!=null)?nombreComercial.trim():"matriz");
                    personaEstablecimiento.setPersona(cliente);
                    
                    //List<PersonaEstablecimiento> establecimientos=new ArrayList<PersonaEstablecimiento>();
                    //establecimientos.add(personaEstablecimiento);
                    cliente.setEstablecimientos(Arrays.asList(personaEstablecimiento));
                    cliente.setEmpresa(session.getEmpresa());
                    ServiceFactory.getFactory().getPersonaServiceIf().grabar(cliente);
                    
                    
                    //ServiceFactory.getFactory().getEstudianteServiceIf().grabar(cliente);

                    //return true;
                } catch (ServicioCodefacException ex) {
                    //Logger.getLogger(MigrarEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(MigrarClientesModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                //return false;
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

    @Override
    public InputStream getInputStreamExcel() {
        return RecursoCodefac.PLANTILLAS_EXCEL.getResourceInputStream("clientes_migrar.xlsx");
    }
    
}
