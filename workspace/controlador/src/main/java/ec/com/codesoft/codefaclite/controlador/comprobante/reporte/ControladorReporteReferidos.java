/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.io.InputStream;
import java.sql.Date;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class ControladorReporteReferidos extends ControladorReporteFactura{
    

    public ControladorReporteReferidos() {
    }
    
    
    
    public ControladorReporteReferidos(Persona persona, Date fechaInicio, Date fechaFin, ComprobanteEntity.ComprobanteEnumEstado estadoFactura, Boolean filtrarReferidos, Persona referido, Boolean reporteAgrupado, Boolean afectarNotaCredito, DocumentosConsultarEnum documentoConsultaEnum) {
        super(persona, fechaInicio, fechaFin, estadoFactura, filtrarReferidos, referido, reporteAgrupado, afectarNotaCredito, documentoConsultaEnum);
    }

    @Override
    protected InputStream getReporte() {
        
        if(this.reporteAgrupado)
        {
            return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_referidos_acumulado.jrxml");
        }   
        else
        {
            return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_referidos.jrxml");
        }
    }


    @Override
    public Vector<String> crearCabezeraTabla() {
            Vector<String> titulo = new Vector<>();
        titulo.add("Identificación Ref");
        titulo.add("Nombres Ref");
        titulo.add("Porcentaje Ref");
        titulo.add("Comisión Ref");
        titulo.add("Preimpreso");
        titulo.add("Referencia");
        titulo.add("Fecha");
        titulo.add("Identificación");
        titulo.add("Razón social");
        titulo.add("Referido");           
        titulo.add("Documento");
        titulo.add("Estado");
        titulo.add("Tipo");
        titulo.add("Subtotal 12%");
        titulo.add("Subtotal 0% ");
        titulo.add("Descuentos");
        titulo.add("IVA 12%");
        titulo.add("Valor Afecta");
        titulo.add("Total");
        return titulo;
    }
    
    
    
    
}