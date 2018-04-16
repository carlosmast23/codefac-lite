/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.data;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ProductoData implements ExcelDatosInterface
{
    private String codigoPrincipal;
    private String tipoProducto;
    private String nombre;
    private String valorUnitario;
    private String impuestoIva;

    public ProductoData() {
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getImpuestoIva() {
        return impuestoIva;
    }

    public void setImpuestoIva(String impuestoIva) {
        this.impuestoIva = impuestoIva;
    }

    @Override
    public List<TipoDato> getDatos() 
    {
        List<TipoDato> tiposDatos = new ArrayList<>();
        tiposDatos.add(new TipoDato(this.codigoPrincipal,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.tipoProducto,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombre,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.valorUnitario,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.impuestoIva,Excel.TipoDataEnum.TEXTO));
        return tiposDatos;
    }
    
    
}
