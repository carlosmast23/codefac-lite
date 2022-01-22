/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReportDataAbstract<T> implements Serializable{
    
    //Map para almacenar todos los parametros del reporte
    private Map<String,Object> mapParametros;
    
    //Variable para almacenar el reporte del titulo 
    private String tituloReporte;
    
    private List<T> detalleList;

    public ReportDataAbstract(String tituloReporte) {
        this.tituloReporte = tituloReporte;
        this.mapParametros=new HashMap<String, Object>();
        agregarParametro("pl_titulo", tituloReporte);
        this.detalleList=new ArrayList<T>();
    }
    
    ///////////////////////////////////////////////////////////////////
    ///                     METODOS PERSONALIZADOS
    ///////////////////////////////////////////////////////////////////
    
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

    
    

}
