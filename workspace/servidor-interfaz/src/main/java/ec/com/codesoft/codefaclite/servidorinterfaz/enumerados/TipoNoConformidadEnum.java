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
    FALLO_ELECTRONICO("FE","Fallo Electrónico"),
    FALLO_MECANICO("FM","Fallo Mécanico"),
    FALLO_PINTURA("FDP","Fallo de Pintura"),
    FALTANTE("FL","Faltante"),
    FISURADO("FIS","Fisurado"),
    GARANTIA("GR","Garantia"),
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
    SONIDO("SN","Sonido"),
    OXIDOS("OX","Oxidos"),
    ABOLLADO("OB","Obollado"),
    RETOQUE("RT","Retoque"),
    LLUVIA_ACIDA("LLA","Lluva acida"),
    MAL_FUNCIONAMIENTO("MFC","Mal funcionamiento"),
    INOPERANTE("INO","Inoperante"),
    PPF("PPF","PPF"),
    LAMINAS("LAM","Laminas"),
    GRUMOS("GRM","Grumos"),
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
