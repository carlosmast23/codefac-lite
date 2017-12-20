/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity.enumerados;

/**
 *
 * @author Carlos
 */
public enum FacturaEnumEstado {
    /**
     * Cuando la factura se grabo y se autorizo en el SRI
     */
    FACTURADO("F","Facturado"),
    /**
     * Estado cuando se graba la factura en la base de datos pero no esta
     * autorizado en el SRI
     */
    SIN_AUTORIZAR("S","Sin Autorizar"),
    /**
     * Estado anulado cuando una nota de credito anulo totalmente la factura
     */
    ANULADO_TOTAL("A","Anulado Total"),
    /**
     * Estado cuando aplica una nota de credito pero no sobre el total de la
     * factura
     */
    ANULADO_PARCIAL("P","Anulado Parcial"),
    /**
     * Estado eliminado solo permitido si el comprobante no fue autorizado
     */
    ELIMINADO("E","Eliminado");

    private FacturaEnumEstado(String estado, String nombre) {
        this.estado = estado;
        this.nombre = nombre;
    }

    private String estado;
    private String nombre;

    public static FacturaEnumEstado getEnum(String estado) {

        for (FacturaEnumEstado enumerador : FacturaEnumEstado.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

}
