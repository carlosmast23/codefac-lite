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
 * @author Robert
 */
public class ArqueoCajaReporteData implements ExcelDatosInterface
{
    private String codigoArqueoCaja;
    private String nombreUsuarioArqueoCaja;
    private String nombreUsuarioCajaSession;
    private String nombreCaja;
    private String puntoEmisionArqueoCaja;
    private String fechaHoraArqueoCaja;
    private String valorTeoricoArqueoCaja;
    private String valorFisicoArqueoCaja;
    private String estadoArqueoCaja;

    public ArqueoCajaReporteData(String codigoArqueoCaja, String nombreUsuarioArqueoCaja, String nombreUsuarioCajaSession, String nombreCaja, String puntoEmisionArqueoCaja, String fechaHoraArqueoCaja, String valorTeoricoArqueoCaja, String valorFisicoArqueoCaja, String estadoArqueoCaja) 
    {
        this.codigoArqueoCaja = codigoArqueoCaja;
        this.nombreUsuarioArqueoCaja = nombreUsuarioArqueoCaja;
        this.nombreUsuarioCajaSession = nombreUsuarioCajaSession;
        this.nombreCaja = nombreCaja;
        this.puntoEmisionArqueoCaja = puntoEmisionArqueoCaja;
        this.fechaHoraArqueoCaja = fechaHoraArqueoCaja;
        this.valorTeoricoArqueoCaja = valorTeoricoArqueoCaja;
        this.valorFisicoArqueoCaja = valorFisicoArqueoCaja;
        this.estadoArqueoCaja = estadoArqueoCaja;
    }
    
    @Override
    public List<TipoDato> getDatos() 
    {
        List<TipoDato> datos = new ArrayList<>();
        datos.add(new TipoDato(this.nombreUsuarioArqueoCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.nombreUsuarioCajaSession, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.nombreCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.puntoEmisionArqueoCaja, Excel.TipoDataEnum.TEXTO));        
        datos.add(new TipoDato(this.fechaHoraArqueoCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.valorTeoricoArqueoCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.valorFisicoArqueoCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.estadoArqueoCaja, Excel.TipoDataEnum.TEXTO));
        
        return datos;
    }

    public String getCodigoArqueoCaja() {
        return codigoArqueoCaja;
    }

    public void setCodigoArqueoCaja(String codigoArqueoCaja) {
        this.codigoArqueoCaja = codigoArqueoCaja;
    }

    public String getNombreUsuarioArqueoCaja() {
        return nombreUsuarioArqueoCaja;
    }

    public void setNombreUsuarioArqueoCaja(String nombreUsuarioArqueoCaja) {
        this.nombreUsuarioArqueoCaja = nombreUsuarioArqueoCaja;
    }

    public String getNombreUsuarioCajaSession() {
        return nombreUsuarioCajaSession;
    }

    public void setNombreUsuarioCajaSession(String nombreUsuarioCajaSession) {
        this.nombreUsuarioCajaSession = nombreUsuarioCajaSession;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getPuntoEmisionArqueoCaja() {
        return puntoEmisionArqueoCaja;
    }

    public void setPuntoEmisionArqueoCaja(String puntoEmisionArqueoCaja) {
        this.puntoEmisionArqueoCaja = puntoEmisionArqueoCaja;
    }

    public String getFechaHoraArqueoCaja() {
        return fechaHoraArqueoCaja;
    }

    public void setFechaHoraArqueoCaja(String fechaHoraArqueoCaja) {
        this.fechaHoraArqueoCaja = fechaHoraArqueoCaja;
    }

    public String getValorTeoricoArqueoCaja() {
        return valorTeoricoArqueoCaja;
    }

    public void setValorTeoricoArqueoCaja(String valorTeoricoArqueoCaja) {
        this.valorTeoricoArqueoCaja = valorTeoricoArqueoCaja;
    }

    public String getValorFisicoArqueoCaja() {
        return valorFisicoArqueoCaja;
    }

    public void setValorFisicoArqueoCaja(String valorFisicoArqueoCaja) {
        this.valorFisicoArqueoCaja = valorFisicoArqueoCaja;
    }

    public String getEstadoArqueoCaja() {
        return estadoArqueoCaja;
    }

    public void setEstadoArqueoCaja(String estadoArqueoCaja) {
        this.estadoArqueoCaja = estadoArqueoCaja;
    }

}
