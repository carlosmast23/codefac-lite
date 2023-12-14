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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TareaMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;

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
    private Boolean fechaFinalExacta;
    
    private String tipoReporte;
    private List<Taller> tallerList;  
    private Taller tallerSeleccionado;
    private Boolean todosTalleres;
    
    
    private Boolean visibleVin;
    private Boolean visibleTarea;
    private Boolean visibleTaller;
    private Boolean visibleHoras;
    private Boolean visibleMarca;
    private Boolean visibleModelo;
    private Boolean visibleColor;
    private Boolean visibleFechaIngreso;
    private Boolean visibleFechaSalida;
    private Boolean visibleDuracionDias;
    private Boolean visibleEstado;
    private Boolean visibleComentario;
    private Boolean visibleFechaInicioTarea;
    private Boolean visibleFechaFinTarea;
    private Boolean visibleHorasProc;
    private Boolean visibleDefecto;
    private Boolean visibleParteVehiculo;
    
    //
    
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
            
            tallerList=ServiceFactory.getFactory().getTallerServiceIf().obtenerActivos();
            //tallerList.remove(3); //TODO: Por el momento remuevo directamente el indice que no necesito
            
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
            habilitarDeshabilitarColumnas(true);
            
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
    
    public void eventoMenuReporte()
    {
        habilitarDeshabilitarColumnas(true);
        fechaFinal=null;
        //fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());
        fechaInicial=null;
        fechaFinalExacta=false; 
        if(tipoReporte.equals("liberadas"))
        {
            //fechaFinal=UtilidadesFecha.getFechaHoy();
            //fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());
            //System.out.println("Fecha Inicial"+fechaInicial);
            fechaFinalExacta=true;
            fechaFinal=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());
            estadoSeleccionado=MantenimientoEnum.TERMINADO;
            habilitarReporteLiberadas();
        }
        else if(tipoReporte.equals("taller"))
        {
            estadoSeleccionado=MantenimientoEnum.INGRESADO;
            habilitarReporteLiberadas();
            //fechaFinal=null;
            //UtilidadesFecha.restarDiasFecha(fechaFinal, 0)
            //fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());   
        }else if(tipoReporte.equals("proceso"))
        {
            estadoSeleccionado=MantenimientoEnum.INGRESADO;
            habilitarReporteProceso();
        }
        else if(tipoReporte.equals("noConformidad"))
        {
            fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());
            estadoSeleccionado=null;
        }
        else
        {
            habilitarReporteLiberadas();
        }
    }
    
    private void habilitarReporteProceso()
    {
        visibleDefecto=false;
        visibleParteVehiculo=false;
    }
    
    private void habilitarReporteLiberadas()
    {
        visibleTarea=false;
        visibleComentario=false;
        visibleFechaInicioTarea=false;
        visibleFechaFinTarea=false;
        visibleHorasProc=false;
        visibleDefecto=false;
        visibleParteVehiculo=false;
    }
    
    public void consultarMantenimientos() 
    {
        //visibleTarea=!visibleTarea;
        //Encontrar sola las unidades finalmente terminadas
        /*if(tipoReporte.equals("liberadas"))
        {
            fechaFinal=UtilidadesFecha.getFechaHoy();
            fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());
            System.out.println("Fecha Inicial"+fechaInicial);
            estadoSeleccionado=MantenimientoEnum.TERMINADO;
        }
        else if(tipoReporte.equals("taller") || tipoReporte.equals("proceso") || tipoReporte.equals("noConformidad"))
        {
            estadoSeleccionado=MantenimientoEnum.INGRESADO;
            fechaFinal=null;
            //UtilidadesFecha.restarDiasFecha(fechaFinal, 0)
            fechaInicial=UtilidadesFecha.eliminarHorasFecha(UtilidadesFecha.getFechaHoraHoy());   
        }*/
                
        try { 
            System.out.println("Datos Consulta | fechaIni: "+fechaInicial+" | fechaFin: "+fechaFinal+" | estado: "+estadoSeleccionado+" | marca: "+marcaSeleccionada+" | ubi: "+ubicacionSeleccionada+" | tarea: "+tareaSeleccionada+" | taller: "+tallerSeleccionado+" | tallerTod:"+todosTalleres);
            mantenimientoList=ServiceFactory.getFactory().getMantenimientoServiceIf().consultarMantenimiento(fechaInicial,fechaFinal,fechaFinalExacta,todosTalleres,tallerSeleccionado,estadoSeleccionado,marcaSeleccionada,ubicacionSeleccionada,true,tareaSeleccionada);
            System.out.println("Datos consultados ANTES: "+mantenimientoList.size());  
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
        ocultarTamanioExcel(sheet);
        
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            HSSFRow row = sheet.getRow(rowIndex);

            if (row != null) {
                HSSFCell cell2 = row.getCell(4); 
                
                if (cell2 != null && cell2.getCellTypeEnum().equals(CellType.STRING)) {
                    cell2.setCellValue(Double.parseDouble(cell2.getStringCellValue()));
                    cell2.setCellType(CellType.NUMERIC);                    
                }

                
                HSSFCell cell5 = row.getCell(10); 
                if (cell5 != null && cell5.getCellTypeEnum().equals(CellType.STRING)) 
                {
                    cell5.setCellValue(Double.parseDouble(cell5.getStringCellValue()));
                    cell5.setCellType(CellType.NUMERIC);  
                } 
                        
                
                
                //Aplicar formato de fecha corta excel
                cambiarEstiloFecha(row.getCell(8), wb,dateCellStyle,1);                
                cambiarEstiloFecha(row.getCell(9), wb,dateCellStyle,1);
                
                cambiarEstiloFecha(row.getCell(13), wb,dateCellStyle,2);                
                cambiarEstiloFecha(row.getCell(14), wb,dateCellStyle,2);
                
                
                
            }
        }
    }
    
    private void ocultarTamanioExcel(HSSFSheet sheet)
    {
        if(!visibleTarea)
        {
            sheet.setColumnWidth(2, 0);
        }
        
        if(!visibleComentario)
        {
            sheet.setColumnWidth(12, 0);
        }
        
        if(!visibleFechaInicioTarea)
        {
            sheet.setColumnWidth(13, 0);
        }
        
        if(!visibleFechaFinTarea)
        {
            sheet.setColumnWidth(14, 0);
        }
        
        if(!visibleHorasProc)
        {
            sheet.setColumnWidth(15, 0);
        }
        
        if(!visibleDefecto)
        {
            sheet.setColumnWidth(16, 0);
        }
        
        if(!visibleParteVehiculo)
        {
            sheet.setColumnWidth(17, 0);
        }
    }
    
    private void habilitarDeshabilitarColumnas(Boolean estado)
    {
        visibleVin=estado;
        visibleTarea=estado;
        visibleTaller=estado;
        visibleHoras=estado;
        visibleMarca=estado;
        visibleModelo=estado;
        visibleColor=estado;
        visibleFechaIngreso=estado;
        visibleFechaSalida=estado;
        visibleDuracionDias=estado;
        visibleEstado=estado;
        visibleComentario=estado;
        visibleFechaInicioTarea=estado;
        visibleFechaFinTarea=estado;
        visibleHorasProc=estado;
        visibleDefecto=estado;
        visibleParteVehiculo=estado;

    }
    
    private void cambiarEstiloFecha(HSSFCell cellFecha,HSSFWorkbook wb,CellStyle dateCellStyle,int estilo)
    {        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(estilo==2)
        {
            formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        }
        
        //cellFecha = row.getCell(13);
        if (cellFecha != null && !UtilidadesTextos.verificarNullOVacio(cellFecha.getStringCellValue())) {
            try {
                String fechaStr = cellFecha.getStringCellValue();
                cellFecha.setCellValue(formato.parse(fechaStr));
                cellFecha.setCellStyle(dateCellStyle);
            } catch (ParseException ex) {
                Logger.getLogger(ReporteMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<Taller> getTallerList() {
        return tallerList;
    }

    public void setTallerList(List<Taller> tallerList) {
        this.tallerList = tallerList;
    }

    public Taller getTallerSeleccionado() {
        return tallerSeleccionado;
    }

    public void setTallerSeleccionado(Taller tallerSeleccionado) {
        this.tallerSeleccionado = tallerSeleccionado;
    }

    public Boolean getFechaFinalExacta() {
        return fechaFinalExacta;
    }

    public void setFechaFinalExacta(Boolean fechaFinalExacta) {
        this.fechaFinalExacta = fechaFinalExacta;
    } 

    public Boolean getVisibleTarea() { 
        return visibleTarea;
    }

    public void setVisibleTarea(Boolean visibleTarea) {
        this.visibleTarea = visibleTarea;
    }

    public Boolean getVisibleVin() {
        return visibleVin;
    }

    public void setVisibleVin(Boolean visibleVin) {
        this.visibleVin = visibleVin;
    }

    public Boolean getVisibleTaller() {
        return visibleTaller;
    }

    public void setVisibleTaller(Boolean visibleTaller) {
        this.visibleTaller = visibleTaller;
    }

    public Boolean getVisibleHoras() {
        return visibleHoras;
    }

    public void setVisibleHoras(Boolean visibleHoras) {
        this.visibleHoras = visibleHoras;
    }

    public Boolean getVisibleMarca() {
        return visibleMarca;
    }

    public void setVisibleMarca(Boolean visibleMarca) {
        this.visibleMarca = visibleMarca;
    }

    public Boolean getVisibleModelo() {
        return visibleModelo;
    }

    public void setVisibleModelo(Boolean visibleModelo) {
        this.visibleModelo = visibleModelo;
    }

    public Boolean getVisibleColor() {
        return visibleColor;
    }

    public void setVisibleColor(Boolean visibleColor) {
        this.visibleColor = visibleColor;
    }

    public Boolean getVisibleFechaIngreso() {
        return visibleFechaIngreso;
    }

    public void setVisibleFechaIngreso(Boolean visibleFechaIngreso) {
        this.visibleFechaIngreso = visibleFechaIngreso;
    }

    public Boolean getVisibleFechaSalida() {
        return visibleFechaSalida;
    }

    public void setVisibleFechaSalida(Boolean visibleFechaSalida) {
        this.visibleFechaSalida = visibleFechaSalida;
    }

    public Boolean getVisibleDuracionDias() {
        return visibleDuracionDias;
    }

    public void setVisibleDuracionDias(Boolean visibleDuracionDias) {
        this.visibleDuracionDias = visibleDuracionDias;
    }

    public Boolean getVisibleEstado() {
        return visibleEstado;
    }

    public void setVisibleEstado(Boolean visibleEstado) {
        this.visibleEstado = visibleEstado;
    }

    public Boolean getVisibleComentario() {
        return visibleComentario;
    }

    public void setVisibleComentario(Boolean visibleComentario) {
        this.visibleComentario = visibleComentario;
    }

    public Boolean getVisibleFechaInicioTarea() {
        return visibleFechaInicioTarea;
    }

    public void setVisibleFechaInicioTarea(Boolean visibleFechaInicioTarea) {
        this.visibleFechaInicioTarea = visibleFechaInicioTarea;
    }

    public Boolean getVisibleFechaFinTarea() {
        return visibleFechaFinTarea;
    }

    public void setVisibleFechaFinTarea(Boolean visibleFechaFinTarea) {
        this.visibleFechaFinTarea = visibleFechaFinTarea;
    }

    public Boolean getVisibleHorasProc() {
        return visibleHorasProc;
    }

    public void setVisibleHorasProc(Boolean visibleHorasProc) {
        this.visibleHorasProc = visibleHorasProc;
    }

    public Boolean getVisibleDefecto() {
        return visibleDefecto;
    }

    public void setVisibleDefecto(Boolean visibleDefecto) {
        this.visibleDefecto = visibleDefecto;
    }

    public Boolean getVisibleParteVehiculo() {
        return visibleParteVehiculo;
    }

    public void setVisibleParteVehiculo(Boolean visibleParteVehiculo) {
        this.visibleParteVehiculo = visibleParteVehiculo;
    }

    public Boolean getTodosTalleres() {
        return todosTalleres;
    }

    public void setTodosTalleres(Boolean todosTalleres) {
        this.todosTalleres = todosTalleres;
    }

    
    
}
