/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum VisualizadorReporteEnum implements ParametroUtilidades.ComparadorInterface<VisualizadorReporteEnum> {
    ESTANDAR_JASPER("j", "Estandar Jasper"),
    PERSONALIZADO_CODEFAC("c", "Personalizado Codefac");

    private String letra;
    private String descripcion;

    private VisualizadorReporteEnum(String letra, String descripcion) {
        this.letra = letra;
        this.descripcion = descripcion;
    }

    public String getLetra() {
        return letra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static VisualizadorReporteEnum buscarPorLetra(String letra) {
        for (VisualizadorReporteEnum value : VisualizadorReporteEnum.values()) {
            if (value.getLetra().equals(letra)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public VisualizadorReporteEnum consultarParametro(String nombreParametro) {
        return buscarPorLetra(nombreParametro);
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
