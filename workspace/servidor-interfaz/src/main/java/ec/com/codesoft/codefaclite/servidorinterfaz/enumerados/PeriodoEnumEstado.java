/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CodesoftDesarrollo
 */
public enum PeriodoEnumEstado{
      /**
     * Valor por defecto del cliente
     */
    ACTIVO("A"),
    /**
     * Estado inactivo del cliente seguramente por algun motivo que el usuario
     * cosidere
     */
    INACTIVO("I"),
    /**
     * Estado cuando un cliente es eliminado de forma permanente
     */
    ELIMINADO("E"),;

    private String estado;

    private PeriodoEnumEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static PeriodoEnumEstado getEnum(String estado) {
        for (PeriodoEnumEstado enumerador : PeriodoEnumEstado.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }

}
