/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = ComprobanteElectronico.LIQUIDACION_COMPRA)
@XmlType(propOrder = {"informacionTributaria","informacionFactura","detalles","informacionAdicional"})
public class LiquidacionCompraComprobante {
    
}
