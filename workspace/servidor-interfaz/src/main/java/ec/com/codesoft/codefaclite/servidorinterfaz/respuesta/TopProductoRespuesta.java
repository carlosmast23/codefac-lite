/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TopProductoRespuesta implements Serializable
{
    public String codigo;
    public String descripcion;
    public BigDecimal cantidad;

    public TopProductoRespuesta() {
    }

    public TopProductoRespuesta(String codigo,String descripcion, BigDecimal cantidad) {
        this.codigo=codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
   
    public static List<TopProductoRespuesta> castList(List<Object[]> resultadoList )
    {
        List<TopProductoRespuesta> datosList=new ArrayList<TopProductoRespuesta>();
        for (Object[] dato : resultadoList) {
            datosList.add(new TopProductoRespuesta((String)dato[0],(String)dato[1],(BigDecimal)dato[2]));
        }
        return datosList;
    }
    
    
}
