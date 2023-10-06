/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesReporteWeb;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento.MantenimientoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TareaMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class ReporteMantenimientoMb extends GeneralAbstractMb implements Serializable
{
    private List<MantenimientoEnum> estadoMantenimietoList;  
    private List<MantenimientoResult> mantenimientoList; 
    private List<MarcaProducto> marcaList;  
    private List<Mantenimiento.UbicacionEnum> ubicacionList;
    private List<TareaMantenimiento> tareaList;
    
    private MantenimientoEnum estadoSeleccionado;  
    private MarcaProducto marcaSeleccionada;
    private Mantenimiento.UbicacionEnum ubicacionSeleccionada;
    private TareaMantenimiento tareaSeleccionada;
    
    private java.util.Date fechaInicial; 
    private java.util.Date fechaFinal;
    
    private String tipoReporte;
    
    ///private String 
 
    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        //Mantenimiento m;
        //m.getUbicacionEnum()
        //TareaMantenimiento t;
        
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
        //todo
        if(!UtilidadesLista.verificarListaVaciaONull(mantenimientoList))
        {
            InputStream path =  RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("mantenimientoReporte.jrxml");
            JasperPrint jasperPrint = ReporteCodefac.construirReporte(
                    path, new HashMap<String, Object>(),
                    MantenimientoResult.convertirDataReporte(mantenimientoList,false), 
                    sessionMb.getSession(), 
                    "Listado de mantemientos", 
                    OrientacionReporteEnum.HORIZONTAL, 
                    FormatoHojaEnum.A4);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
            UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint, "Reporte_mantenimientos.pdf");
        /*Map<String, Object>mapParametros = ReporteCodefac.mapReportePlantilla(OrientacionReporteEnum.HORIZONTA*/
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        try {
            
            System.out.println(">> Cargando Metodo Iniciar <<");
            
            mantenimientoList=new ArrayList<MantenimientoResult>();
            System.out.println("Tarea 1 ");
            estadoMantenimietoList=UtilidadesLista.arrayToList(MantenimientoEnum.values());
            System.out.println("Tarea 2 ");
            //estadoMantenimietoList.remove(MantenimientoEnum.FACTURADO);
            
            System.out.println("Tarea 3 ");
            marcaList=ServiceFactory.getFactory().getMarcaProductoServiceIf().obtenerActivosPorEmpresa(sessionMb.getSession().getEmpresa());
            ubicacionList=UtilidadesLista.arrayToList(Mantenimiento.UbicacionEnum.values());
            fechaInicial=UtilidadesFecha.getFechaHoy();
            System.out.println("Tarea 4 ");
            fechaFinal=UtilidadesFecha.sumarDiasFecha(UtilidadesFecha.getFechaHoy(),1); 
            tareaList=ServiceFactory.getFactory().getTareaMantenimientoServiceIf().obtenerTodosActivos(sessionMb.getSession().getEmpresa());
            System.out.println("Tarea 5 ");
            
            System.out.println("Tareas seleccionadas: "+tareaList.size());
            System.out.println("Fecha Inicial: "+fechaInicial);
            System.out.println("Fecha Final: "+fechaFinal);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void ejemplo()
    {
        System.out.println("verificar presionar boton ...");
    }
    
    public void consultarMantenimientos()
    {
        //Encontrar sola las unidades finalmente terminadas
        if(tipoReporte.equals("liberadas"))
        {
            fechaFinal=UtilidadesFecha.getFechaHoy();
            fechaInicial=UtilidadesFecha.obtenerPrimerDiaDelMes();
            System.out.println("Fecha Inicial"+fechaInicial);
            estadoSeleccionado=MantenimientoEnum.TERMINADO;
        }
        else if(tipoReporte.equals("taller") || tipoReporte.equals("proceso") || tipoReporte.equals("noConformidad"))
        {
            estadoSeleccionado=MantenimientoEnum.INGRESADO;
            fechaFinal=null;
            //UtilidadesFecha.restarDiasFecha(fechaFinal, 0)
            fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());   
        }
                
        try {
            mantenimientoList=ServiceFactory.getFactory().getMantenimientoServiceIf().consultarMantenimiento(fechaInicial,fechaFinal,estadoSeleccionado,marcaSeleccionada,ubicacionSeleccionada,true,tareaSeleccionada);
            
            if(tipoReporte.equals("proceso"))
            {
                System.out.println("Datos antes: "+mantenimientoList.size());
                mantenimientoList=MantenimientoResult.convertirDataReporte(mantenimientoList,false);
                System.out.println("Datos luego: "+mantenimientoList.size());
            }
            else
            if(tipoReporte.equals("noConformidad"))
            {
                mantenimientoList=MantenimientoResult.convertirDataReporte(mantenimientoList,true);
            }
            
    //mantenimientoList.add(0,null);
                        
            System.out.println("Datos consultados DE MANTENIMIENTOS: "+mantenimientoList.size());  
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*public void agregarTareaList(List<MantenimientoResult> resultadoList)
    {
        List<MantenimientoResult> listaResultado=new ArrayList<MantenimientoResult>();
        for (MantenimientoResult resultado : resultadoList) 
        {
            for (MantenimientoResult.DetalleTareaResult detalleTareaResult : resultado.getTareaLista()) {
                
                listaResultado.add(N);
            }
            
        }
        
    }*/
    
    public void customXLSPostProcessor(Object document) {
        System.out.println("customXLSPostProcessor PROCESANDO ...");
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);

        for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            HSSFRow row = sheet.getRow(rowIndex);

            if (row != null) {
                HSSFCell cell2 = row.getCell(4); // Columna 2
                
                if (cell2 != null && cell2.getCellTypeEnum().equals(CellType.STRING)) {
                    cell2.setCellValue(Double.parseDouble(cell2.getStringCellValue()));
                    cell2.setCellType(CellType.NUMERIC);                    
                }

                
                HSSFCell cell5 = row.getCell(10); // Columna 5
                if (cell5 != null && cell5.getCellTypeEnum().equals(CellType.STRING)) 
                {
                    cell5.setCellValue(Double.parseDouble(cell5.getStringCellValue()));
                    cell5.setCellType(CellType.NUMERIC);  
                } 
            }
        }
    }


    public List<MantenimientoResult> getMantenimientoList() {
        return mantenimientoList;
    }

    public void setMantenimientoList(List<MantenimientoResult> mantenimientoList) {
        this.mantenimientoList = mantenimientoList;
    }

    public List<MantenimientoEnum> getEstadoMantenimietoList() {
        return estadoMantenimietoList;
    }

    public void setEstadoMantenimietoList(List<MantenimientoEnum> estadoMantenimietoList) {
        this.estadoMantenimietoList = estadoMantenimietoList;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public MantenimientoEnum getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(MantenimientoEnum estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public List<MarcaProducto> getMarcaList() {
        return marcaList;
    }

    public void setMarcaList(List<MarcaProducto> marcaList) {
        this.marcaList = marcaList;
    }

    public MarcaProducto getMarcaSeleccionada() {
        return marcaSeleccionada;
    }

    public void setMarcaSeleccionada(MarcaProducto marcaSeleccionada) {
        this.marcaSeleccionada = marcaSeleccionada;
    }

    public List<Mantenimiento.UbicacionEnum> getUbicacionList() {
        return ubicacionList;
    }

    public void setUbicacionList(List<Mantenimiento.UbicacionEnum> ubicacionList) {
        this.ubicacionList = ubicacionList;
    }

    public Mantenimiento.UbicacionEnum getUbicacionSeleccionada() {
        return ubicacionSeleccionada;
    }

    public void setUbicacionSeleccionada(Mantenimiento.UbicacionEnum ubicacionSeleccionada) {
        this.ubicacionSeleccionada = ubicacionSeleccionada;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public List<TareaMantenimiento> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<TareaMantenimiento> tareaList) {
        this.tareaList = tareaList;
    }

    public TareaMantenimiento getTareaSeleccionada() {
        return tareaSeleccionada;
    }

    public void setTareaSeleccionada(TareaMantenimiento tareaSeleccionada) {
        this.tareaSeleccionada = tareaSeleccionada;
    }
    
    
    
    
    
    
}
