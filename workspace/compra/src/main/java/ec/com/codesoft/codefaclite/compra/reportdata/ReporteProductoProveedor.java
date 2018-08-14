/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.reportdata;

import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteProductoProveedor 
{
    private String nombre;
    private List<InformacionProductoProveedor> informacion;

    public ReporteProductoProveedor() {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<InformacionProductoProveedor> getInformacion() {
        return informacion;
    }

    public void setInformacion(List<InformacionProductoProveedor> informacion) {
        this.informacion = informacion;
    }

}
