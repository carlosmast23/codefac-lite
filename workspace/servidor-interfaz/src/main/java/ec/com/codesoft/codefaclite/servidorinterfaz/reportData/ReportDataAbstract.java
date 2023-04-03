/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class ReportDataAbstract<T> implements Serializable{
    
    //Map para almacenar todos los PARAMETROS del reporte
    private Map<String,Object> mapParametros;
    
    private Map<DataReportIf,BigDecimal> mapTotales;
    
    //Variable para almacenar el reporte del titulo 
    private String tituloReporte;
    
    private List<T> detalleList;
    
    public abstract String[] getTitulos();
    public abstract void construirFilaTabla(T dato,Vector<Object> fila);

    public ReportDataAbstract(String tituloReporte) {
        this.tituloReporte = tituloReporte;
        this.mapParametros=new HashMap<String, Object>();
        agregarParametro("pl_titulo", tituloReporte);
        this.detalleList=new ArrayList<T>();
    }
    
    ///////////////////////////////////////////////////////////////////
    ///                     METODOS PERSONALIZADOS
    ///////////////////////////////////////////////////////////////////
    
    
    public DefaultTableModel obtenerModeloTabla()
    {
        DefaultTableModel model=new DefaultTableModel(getTitulos(), 0);
        Map<String,Object> mapTotales=new HashMap<String,Object>();
        
        for (T t : detalleList) {
            Vector<Object> filaTabla=new Vector<Object>();
            construirFilaTabla(t, filaTabla);
            model.addRow(filaTabla);
        }
        
        return model;
    }
    
    public void sumarTotal(DataReportIf dataReport,BigDecimal valor)
    {
        if(mapTotales==null)
        {
            mapTotales=new HashMap<DataReportIf,BigDecimal>();
        }
        
        BigDecimal total= mapTotales.get(dataReport);
        
        if(total==null)
        {
            total=BigDecimal.ZERO;
        }
        
        //sumar el nuevo valor
        total=total.add(valor);
        
        mapTotales.put(dataReport, total);
        
    }
    
    /**
     * Obtiene el valor calculado
     * @param dataReport
     * @return 
     */
    public BigDecimal getTotal(DataReportIf dataReport)
    {
        BigDecimal total=BigDecimal.ZERO;
        if(mapTotales!=null)
        {
            total= mapTotales.get(dataReport);
            if(total==null)
            {
                total=BigDecimal.ZERO;
            }
        }
        return total;
    }
    
    
    public String getTotalFormatStr(DataReportIf dataReport)
    {
        BigDecimal valor= getTotal(dataReport);
        return valor.setScale(2, RoundingMode.HALF_UP).toString();
    }
    
    
    public void agregarParametro(String campo,Object valor)
    {
        this.mapParametros.put(campo, valor);
    }
    
    
    public void agregarDetalle(T detalle)
    {
        this.detalleList.add(detalle);
    }
    
    
    ///////////////////////////////////////////////////////////////////
    ///                     GET AND SET
    ///////////////////////////////////////////////////////////////////

    public Map<String, Object> getMapParametros() {
        return mapParametros;
    }

    public void setMapParametros(Map<String, Object> mapParametros) {
        this.mapParametros = mapParametros;
    }

    public String getTituloReporte() {
        return tituloReporte;
    }

    public void setTituloReporte(String tituloReporte) {
        this.tituloReporte = tituloReporte;
    }

    public List<T> getDetalleList() {
        return detalleList;
    }

    public void setDetalleList(List<T> detalleList) {
        this.detalleList = detalleList;
    }

    public interface DataReportIf
    {
        public String getNombreDato();
    }
    

}
