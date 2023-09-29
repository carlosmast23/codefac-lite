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
public enum TipoNoConformidadEnum {
    CONTAMINACION("CON","Contaminacion"),
    CORTADO("COR","Cortado"),
    DEFORMADO("DEF","Deformado"),
    DESPOSTILLADO("DES","Despotillado"),
    FALLO_PINTURA("FDP","Fallo de Pintura"),
    FALTANTE("FAL","Faltante"),
    FISURADO("FIS","Fisurado"),
    GOLPE("GPE","Golpe"),
    ESTAMPADO("EST","Estampado"),
    MAL_INSTALADO("MIN","Mal Instalado"),
    MALA_REPARACION("MAR","Mala reparación"),
    MANCHAS("MAN","Manchas"),
    NO_FUNCIONA("NFU","No funciona"),
    OBJETO_SUELTO("OSU","Objeto Suelto"),
    ONDULADO("OND","Ondulado"),
    PICADO("PIC","Picado"),
    RASPADO("RAS","Raspado"),
    RAYAS("RAY","Rayas"),
    ROTO("ROT","ROTO"),
    SUCIEDAD_INTERNA("SUC","Suciedad Interna");
    
    private String codigo;
    private String nombre;

    private TipoNoConformidadEnum(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
    
    public static TipoNoConformidadEnum buscarPorCodigo(String codigo)
    {
        for (TipoNoConformidadEnum tipo : TipoNoConformidadEnum.values()) 
        {
            if(tipo.getCodigo().equals(codigo))
            {
                return tipo;
            }
        }
        return null;
    }
    
    
    
}
