/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.fecha;

import java.sql.Date;

/**
 *
 * @author Carlos
 */
public class UtilidadesFecha {
    public static java.sql.Date getFechaHoy()
    {
        java.util.Date fechaHoy=new java.util.Date();
        return new java.sql.Date(fechaHoy.getTime());
    }
}
