/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import java.math.RoundingMode;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadReport extends ReportDataAbstract<UtilidadResult>
{

    public UtilidadReport(String tituloReporte) {
        super(tituloReporte);
    }

    @Override
    public String[] getTitulos() {
        return new String[]{
            DatoEnum.OBJETO.nombre,
            DatoEnum.SECUENCIAL.nombre,
            DatoEnum.FECHA.nombre,
            DatoEnum.RAZON_SOCIAL.nombre,
            DatoEnum.IDENTIFICACION.nombre,
            DatoEnum.PRODUCTO.nombre,
            DatoEnum.SUBTOTAL.nombre,
            DatoEnum.COSTO.nombre,
            DatoEnum.UTILIDAD.nombre,
        };
    }

    @Override
    public void construirFilaTabla(UtilidadResult dato, Vector<Object> fila) {
        
        fila.add(dato);
        fila.add(dato.getSecuencial());
        fila.add(dato.getFechaEmision());
        fila.add(dato.getRazonSocial());
        fila.add(dato.getIdentificacion());
        fila.add(dato.getNombreProducto());
        fila.add(dato.getSubtotal().setScale(2, RoundingMode.HALF_UP));
        fila.add(dato.getCosto().setScale(2, RoundingMode.HALF_UP));
        fila.add(dato.getUtilidad().setScale(2, RoundingMode.HALF_UP));
        
        //sumarTotal(DatoEnum.SUBTOTAL, dato.getSubtotal());
        sumarTotal(DatoEnum.SUBTOTAL,dato.getSubtotal());
        sumarTotal(DatoEnum.COSTO,dato.getCosto());
        sumarTotal(DatoEnum.UTILIDAD,dato.getUtilidad());
        
    }
    
        
    public enum DatoEnum implements DataReportIf
    {
        OBJETO(""),
        SECUENCIAL("Secuencial"),
        FECHA("Fecha"),
        RAZON_SOCIAL("Razón Social"),
        IDENTIFICACION("Identificación"),
        PRODUCTO("Producto"),
        SUBTOTAL("Subtotal"),
        COSTO("Costo"),
        UTILIDAD("Utilidad");
        
        private String nombre;

        private DatoEnum(String nombre) {
            this.nombre = nombre;
        }
        
        public String getNombre() 
        {
            return nombre;
        }

        @Override
        public String getNombreDato() 
        {
            return nombre;
        }

        
    }
    
}
