/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum DocumentoDetalleEnum {
    RETENCION_IVA("Retención Renta",DocumentoEnum.RETENCIONES),
    RETENCION_RENTA("Retención Iva",DocumentoEnum.RETENCIONES)    
    ;
        
    private String nombre;
    private DocumentoEnum documentoEnum;

    private DocumentoDetalleEnum(String nombre,DocumentoEnum documentoEnum) {
        this.nombre = nombre;
        this.documentoEnum=documentoEnum;
    }

    public DocumentoEnum getDocumentoEnum() {
        return documentoEnum;
    }

    public void setDocumentoEnum(DocumentoEnum documentoEnum) {
        this.documentoEnum = documentoEnum;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
