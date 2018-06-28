/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 * Enum que me permite tener una clasificacion de los tipos de documentos que maneja el sri
 */
public enum DocumentoSriEnum {
    /**
     *  Se los debe entregar cuando se transfieren bienes, se prestan servicios o se realizan transacciones gravadas con tributos. Los tipos de comprobantes de venta son:
     * Facturas,Notas de venta - RISE,Liquidaciones de compra de bienes y prestación de servicios,Tiquetes emitidos por máquinas registradoras y boletos o entradas a espectáculos públicos,otros documentos autorizados
     */
    COMPROBANTES_VENTA("Comprobantes de Venta"),
    /**
     * Comprobantes que acreditan la retención del impuesto, lo efectúan las personas o empresas que actúan como agentes de retención.
     */
    COMPROBANTES_RETENCION("Comprobantes de Retencion"),
    /**
     * Son documentos complementarios a los comprobantes de venta :
     * Notas de crédito,Notas de débito,Guías de remisión
     */
    DOCUMENTOS_COMPLEMENTARIOS("Documentos Complementarios");
    
    private String nombre;

    private DocumentoSriEnum(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
