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
 * Fila de un desglose de ventas agrupado por forma de pago (ej. "Efectivo $9.20", "Tarjeta crédito
 * $25.00"). Mismo patron que TopProductoRespuesta: DTO simple para traer el resultado de una
 * consulta nativa agrupada (GROUP BY) sin exponer entidades JPA completas via RMI.
 *
 * @author Carlos
 */
public class FormaPagoTotalRespuesta implements Serializable
{
    public String alias;
    public BigDecimal total;

    public FormaPagoTotalRespuesta() {
    }

    public FormaPagoTotalRespuesta(String alias, BigDecimal total) {
        this.alias = alias;
        this.total = total;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public static List<FormaPagoTotalRespuesta> castList(List<Object[]> resultadoList)
    {
        List<FormaPagoTotalRespuesta> datosList = new ArrayList<FormaPagoTotalRespuesta>();
        for (Object[] dato : resultadoList) {
            datosList.add(new FormaPagoTotalRespuesta((String) dato[0], (BigDecimal) dato[1]));
        }
        return datosList;
    }

}
