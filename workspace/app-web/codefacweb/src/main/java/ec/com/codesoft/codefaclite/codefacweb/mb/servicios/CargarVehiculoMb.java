/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarVehiculosMantenimiento;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.inventario.ReporteInventarioStockControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class CargarVehiculoMb extends GeneralAbstractMb implements ControladorVistaIf, ReporteInventarioStockControlador.WebIf
{
    //Variable para almacenar los datos que se deben almacenar o cargar de forma temporal que se van a realizar el mantenimiento    
    private List<Mantenimiento> mantenimientoList;

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return "";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        mantenimientoList=new ArrayList<Mantenimiento>();
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
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @PostConstruct
    public void init() {
        try {
            iniciar();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(CargarVehiculoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CargarVehiculoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /////////////////////// METODOS ADICIONALES /////////////////////////////
    public void handleFileUpload(FileUploadEvent event) 
    {
        // Obtener el archivo cargado del evento
        UploadedFile  file = event.getFile();

        // Realizar cualquier acción adicional con el archivo cargado, como guardarlo en el disco o en la base de datos
        // ...
        // Mostrar un mensaje de éxito al usuario
        FacesMessage message = new FacesMessage("El archivo " + file.getFileName() + " fue cargado con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("ejecutando metodo de handleFileUpload ...");
        cargarTablaExcel(file);
    }
    
    public void cargarTablaExcel(UploadedFile uploadedFile)
    {
        try {
            ExcelMigrarVehiculosMantenimiento migrarExcel=new ExcelMigrarVehiculosMantenimiento();
            migrarExcel.setArchivoExel(convertUploadedFileToFile(uploadedFile));
            migrarExcel.leerDatos();
            migrarExcel.migrar(new ExcelMigrar.MigrarInterface() {
                @Override
                public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel, ExcelMigrar.ExcepcionExcelRegistroDuplicado {
                    
                    String modelo=fila.getValueByEnum(ExcelMigrarVehiculosMantenimiento.Enum.MODELO);
                    String color=fila.getValueByEnum(ExcelMigrarVehiculosMantenimiento.Enum.COLOR);
                    String vin=fila.getValueByEnum(ExcelMigrarVehiculosMantenimiento.Enum.VIN);
                    String fechaIngresoStr=fila.getValueByEnum(ExcelMigrarVehiculosMantenimiento.Enum.FECHA_INGRESO);
                    
                    ObjetoMantenimiento objMantenimiento=new ObjetoMantenimiento();
                    objMantenimiento.setModelo(modelo);
                    objMantenimiento.setColor(color);
                    objMantenimiento.setVin(vin);
                    
                    Mantenimiento mantenimiento=new Mantenimiento();
                    mantenimiento.setVehiculo(objMantenimiento);
                    
                    mantenimientoList.add(mantenimiento);
                    
                    
                }
            });
            
            
            
            
            /*Vehiculo vehiculo=new Vehiculo();
            vehiculo.setModelo("sdasdasd");
            vehiculo.setColor("rojo");
            vehiculo.setVin("1238912312783");
            Mantenimiento mantenimiento=new Mantenimiento();
            mantenimiento.setVehiculo(vehiculo);
            
            mantenimientoList.add(mantenimiento);*/
        } catch (ExcelMigrar.ExcepcionMigrar ex) {
            Logger.getLogger(CargarVehiculoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public static File convertUploadedFileToFile(UploadedFile uploadedFile) {
        try {
            byte[] fileContent = uploadedFile.getContents();
            File tempFile = File.createTempFile("temp", ".tmp");
            try
            {
                OutputStream outputStream = new FileOutputStream(tempFile);
                outputStream.write(fileContent);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CargarVehiculoMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CargarVehiculoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
            return tempFile;
        } catch (IOException ex) {
            Logger.getLogger(CargarVehiculoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public class ModeloTabla
    {
        
    
    }

    public List<Mantenimiento> getMantenimientoList() {
        return mantenimientoList;
    }

    public void setMantenimientoList(List<Mantenimiento> mantenimientoList) {
        this.mantenimientoList = mantenimientoList;
    }
    
    
    
    
}
