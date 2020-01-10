/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class CuentasPorCobrarData implements ExcelDatosInterface {
    private String codigo;
    private String documento;
    private String preimpreso;
    private String fechaEmision;
    private String identificacion;
    private String razonSocial;
    private String nombreComercial;
    private String total;
    private String saldo;
    private String cobrado;
    

    public CuentasPorCobrarData() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCobrado() {
        return cobrado;
    }

    public void setCobrado(String cobrado) {
        this.cobrado = cobrado;
    }
    
    

    @Override
    public List<TipoDato> getDatos() {
        /*private String codigo;
    private String documento;
    private String preimpreso;
    private String fechaEmision;
    private String identificacion;
    private String razonSocial;
    private String nombreComercial;
    private String total;
    private String saldo;*/
        
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.codigo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.documento, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.preimpreso,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaEmision, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.identificacion, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.razonSocial,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreComercial,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.total,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.saldo,Excel.TipoDataEnum.NUMERO));
        return tiposDatos; 
    }
    
    /**
     * Metodo para hacer la conversion de la lista de cartera a cuentas por cobrar data
     * @param resultadoCartera
     * @return 
     */
    public static List<CuentasPorCobrarData> castCuentasPorCobrar(List<Cartera> resultadoCartera)
    {
        List<CuentasPorCobrarData> dataReport=new ArrayList<CuentasPorCobrarData>();
        
        for (Cartera cartera : resultadoCartera) {
            CuentasPorCobrarData data=new CuentasPorCobrarData();
            data.setCodigo(cartera.getId().toString());
            data.setDocumento(cartera.getCarteraDocumentoEnum().getNombre());
            data.setFechaEmision(cartera.getFechaEmision().toString());
            data.setIdentificacion(cartera.getPersona().getIdentificacion());
            String nombreComercial=cartera.getPersona().getEstablecimientos().get(0).getNombreComercial();
            data.setNombreComercial((nombreComercial!=null)?nombreComercial:"");
            data.setPreimpreso(cartera.getPreimpreso());
            data.setRazonSocial(cartera.getPersona().getRazonSocial());
            data.setSaldo(cartera.getSaldo().toString());
            data.setCobrado(cartera.calcularValorCobrado().toString());
            data.setTotal(cartera.getTotal().toString());
            dataReport.add(data);
            
        }
        return dataReport;
    }
    
    
}
