/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class VentaReporteData implements ExcelDatosInterface{

    private String secuencial;
    private String identificacion;
    private String cliente;
    private String total;    
    private String estado;

    public VentaReporteData(String secuencial, String identificacion, String cliente, String total,String estado) {
        this.secuencial = secuencial;
        this.identificacion = identificacion;
        this.cliente = cliente;
        this.total = total;
        this.estado=estado;
    }
    
    

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    @Override
    public List<TipoDato> getDatos() {
                List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.secuencial,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.identificacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.cliente,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.total, Excel.TipoDataEnum.TEXTO));
        return tiposDatos;
    }
    
}
