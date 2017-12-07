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
    FACTURA("FACTURA","01","FAC"),
    NOTA_CREDITO("NOTA_CREDITO","04","CRE"),
    NOTA_DEBITO("NOTA_DEBITO","05",""),
    GUIA_REMISION("GUIA_REMISION","06",""),
    COMPROBANTE_RETENCION("COMPROBANTE_RETENCION","07","");
    
    private String nombre;
    private String codigo;
    private String prefijo;

    private ComprobanteEnum(String nombre, String codigo,String prefijo)
    {
        this.nombre=nombre;
        this.codigo=codigo;
        this.prefijo=prefijo;
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
    
    
    
    
    
}
