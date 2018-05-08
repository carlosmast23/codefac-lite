/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.RetencionComprobante;

/**
 *
 * @author Carlos
 */
public enum ComprobanteEnum {
    FACTURA("FACTURA","01","FAC",FacturaComprobante.class),
    NOTA_CREDITO("NOTA DE CRÉDITO","04","CRE",NotaCreditoComprobante.class),
    NOTA_DEBITO("NOTA DEBITO","05","",null),
    GUIA_REMISION("GUIA REMISION","06","",null),
    COMPROBANTE_RETENCION("COMPROBANTE RETENCION","07","",RetencionComprobante.class);
    
    private String nombre;
    private String codigo;
    private String prefijo;
    private Class clase;

    private ComprobanteEnum(String nombre, String codigo,String prefijo,Class clase)
    {
        this.nombre=nombre;
        this.codigo=codigo;
        this.prefijo=prefijo;
        this.clase=clase;
    }
    
    public static ComprobanteEnum getEnumByCodigo(String codigo)
    {
        if(FACTURA.codigo.equals(codigo))
            return FACTURA;
        else
            if(NOTA_CREDITO.codigo.equals(codigo))
                return NOTA_CREDITO;
            else
                if(COMPROBANTE_RETENCION.codigo.equals(codigo))
                    return COMPROBANTE_RETENCION;
        
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public Class getClase() {
        return clase;
    }
    
    
    
    
    
}
