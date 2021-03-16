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
public class CajaSessionReporteData  implements ExcelDatosInterface
{

    private String nombreCaja;
    private String nombreUsuario;
    private String nombreSucursal;
    private String nombrePuntoEmision;
    
    private String fechaHoraAperturaCaja;
    private String fechaHoraCierreCaja;
    
    private String valorAperturaCaja;
    private String valorCierreCaja; 

    public CajaSessionReporteData(String nombreCaja, String nombreUsuario, String nombreSucursal, String nombrePuntoEmision, String fechaHoraAperturaCaja, String fechaHoraCierreCaja, String valorAperturaCaja, String valorCierreCaja) 
    {
        this.nombreCaja = nombreCaja;
        this.nombreUsuario = nombreUsuario;
        this.nombreSucursal = nombreSucursal;
        this.nombrePuntoEmision = nombrePuntoEmision;
        this.fechaHoraAperturaCaja = fechaHoraAperturaCaja;
        this.fechaHoraCierreCaja = fechaHoraCierreCaja;
        this.valorAperturaCaja = valorAperturaCaja;
        this.valorCierreCaja = valorCierreCaja;
    }
    
    
    
    @Override
    public List<TipoDato> getDatos() 
    {
        List<TipoDato> datos = new ArrayList<>();
        datos.add(new TipoDato(this.nombreCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.nombreUsuario, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.nombreSucursal, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.nombrePuntoEmision, Excel.TipoDataEnum.TEXTO));        
        datos.add(new TipoDato(this.fechaHoraAperturaCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.fechaHoraCierreCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.valorAperturaCaja, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.valorCierreCaja, Excel.TipoDataEnum.TEXTO));
        
        return datos;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombrePuntoEmision() {
        return nombrePuntoEmision;
    }

    public void setNombrePuntoEmision(String nombrePuntoEmision) {
        this.nombrePuntoEmision = nombrePuntoEmision;
    }

    public String getFechaHoraAperturaCaja() {
        return fechaHoraAperturaCaja;
    }

    public void setFechaHoraAperturaCaja(String fechaHoraAperturaCaja) {
        this.fechaHoraAperturaCaja = fechaHoraAperturaCaja;
    }

    public String getFechaHoraCierreCaja() {
        return fechaHoraCierreCaja;
    }

    public void setFechaHoraCierreCaja(String fechaHoraCierreCaja) {
        this.fechaHoraCierreCaja = fechaHoraCierreCaja;
    }

    public String getValorAperturaCaja() {
        return valorAperturaCaja;
    }

    public void setValorAperturaCaja(String valorAperturaCaja) {
        this.valorAperturaCaja = valorAperturaCaja;
    }

    public String getValorCierreCaja() {
        return valorCierreCaja;
    }

    public void setValorCierreCaja(String valorCierreCaja) {
        this.valorCierreCaja = valorCierreCaja;
    }

}
