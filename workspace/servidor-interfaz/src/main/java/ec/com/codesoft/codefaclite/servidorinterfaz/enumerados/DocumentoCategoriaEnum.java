/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 */
public enum  DocumentoCategoriaEnum {
 /**
     *  Se los debe entregar cuando se transfieren bienes, se prestan servicios o se realizan transacciones gravadas con tributos. Los tipos de comprobantes de venta son:
     * Facturas,Notas de venta - RISE,Liquidaciones de compra de bienes y prestación de servicios,Tiquetes emitidos por máquinas registradoras y boletos o entradas a espectáculos públicos,otros documentos autorizados
     */
    COMPROBANTES_VENTA("Comprobantes de Venta"),
    //COMPROBANTES_VENTA_PROVEEDOR("Comprobante de Compra"),
    /**
     * Comprobantes que acreditan la retención del impuesto, lo efectúan las personas o empresas que actúan como agentes de retención.
     */
    COMPROBANTES_RETENCION("Comprobantes de Retención"),
    //COMPROBANTES_RETENCION_PROVEEDOR("Comprobantes de Retención"),
    /**
     * Son documentos complementarios a los comprobantes de venta :
     * Notas de crédito,Notas de débito,Guías de remisión
     */
    DOCUMENTOS_COMPLEMENTARIOS("Documentos Complementarios"),
    //DOCUMENTOS_COMPLEMENTARIOS_PROVEEDOR("Documentos Complementarios Proveedor"),
    
    /**
     * Documentos que me prmite registrar cualquier ingresos o egreso de dinero como por ejemplo
     * depositos , cheques, pagos efectivo. etc
     */
    COMPROBANTE_INGRESOS_EGRESOS("Comprobantes Ingreso");
    //COMPROBANTE_EGRESOS("Comprobantes Egreso");
    
    private String nombre;

    private DocumentoCategoriaEnum(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}