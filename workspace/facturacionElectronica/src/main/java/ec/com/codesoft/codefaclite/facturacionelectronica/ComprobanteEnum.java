/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

/**
 *
 * @author Carlos
 */
public enum ComprobanteEnum {
    FACTURA("FACTURA","01"),
    NOTA_CREDITO("NOTA_CREDITO","04"),
    NOTA_DEBITO("NOTA_DEBITO","04"),
    GUIA_REMISION("GUIA_REMISION","04"),
    COMPROBANTE_RETENCION("COMPROBANTE_RETENCION","04");
    
    private String nombre;
    private String codigo;

    private ComprobanteEnum(String nombre, String codigo)
    {
        this.nombre=nombre;
        this.codigo=codigo;
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
    
    
    
}
