/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.reportdata;

import java.math.BigDecimal;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteDeudasCursoData {
    private String nivel;
    private String rubro;
    private BigDecimal abono;
    private BigDecimal deuda;

    public ReporteDeudasCursoData(String nivel, String rubro, BigDecimal abono, BigDecimal deuda) {
        this.nivel = nivel;
        this.rubro = rubro;
        this.abono = abono;
        this.deuda = deuda;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public BigDecimal getAbono() {
        return abono;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    
   
    
    
}
