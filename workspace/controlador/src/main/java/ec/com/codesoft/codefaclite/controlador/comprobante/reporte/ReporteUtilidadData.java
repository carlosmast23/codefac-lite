/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.TipoDato;
import ec.com.codesoft.codefaclite.controlador.reportes.AgrupadoReporteIf;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReporteUtilidadData implements ExcelDatosInterface,Cloneable{
    
    //protected String codigo;
    //protected String bodega;
    //protected String lote;

    @Override
    public List<TipoDato> getDatos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
