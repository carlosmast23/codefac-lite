/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
 
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface SriServiceIf   
{
    public SriFormaPago obtenerFormarPagoDefecto()   ;
    public SriFormaPago obtenerFormarPagoConCartera()   ;
    public List<SriFormaPago> obtenerFormasPagoActivo()   ;
    public List<SriIdentificacion> obtenerIdentificaciones(String tipo)   ;
}
