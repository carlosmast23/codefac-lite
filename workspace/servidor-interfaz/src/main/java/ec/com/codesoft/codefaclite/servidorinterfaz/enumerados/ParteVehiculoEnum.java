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
public enum ParteVehiculoEnum {
    CAPOT("CAPOT"),
    TECHO("TECHO"),
    COMPUERTA("COMPUERTA"),
    PUERTA_DELANT_LH("PUERTA DELANT LH"),
    PUERTA_DELANT_RH("PUERTA DELANT RH"),
    PUERTA_POST_RH("PUERTA POST RH"),
    PUERTA_POST_LH("PUERTA POST LH"),
    GUARDAFANGO_DELANT_LH("GUARDAFANGO DELANT LH"),
    GUARDAFANGO_DELANT_RH("GUARDAFANGO DELANT RH"),
    MOLDURA_CENTRAL_LH("MOLDURA CENTRAL LH"),
    MOLDURA_POST_LH("MOLDURA POST LH"),
    MOLDURA_DELANT_LH("MOLDURA DELANT LH"),
    MOLDURA_CENTRAL_RH("MOLDURA CENTRAL RH"),
    MOLDURA_POST_RH("MOLDURA POST RH"),
    MOLDURA_DELANT_RH("MOLDURA DELANT RH"),
    PARANTE_CENTRAL_LH("PARANTE CENTRAL LH"),
    PARANTE_DELANT_LH("PARANTE DELANT LH"),
    PARANTE_POST_LH("PARANTE POST LH"),
    PARANTE_CENTRAL_RH("PARANTE CENTRAL RH"),
    PARANTE_DELANT_RH("PARANTE DELANT RH"),
    PARANTE_POST_RH("PARANTE POST RH"),
    ZOCALO_DELANT_LH("ZÓCALO DELANT LH"),
    ZOCALO_DELANT_RH("ZÓCALO DELANT RH"),
    ZOCALO_POST_LH("ZÓCALO POST LH"),
    ZOCALO_POST_RH("ZÓCALO POST RH"),
    BAJO_PISO("BAJO PISO"),
    PARANTE_INTERNO_LH("PARANTE INTERNO LH"),
    PARANTE_INTERNO_RH("PARANTE INTERNO RH"),
    LATERAL_LH("LATERAL LH"),
    LATERAL_RH("LATERAL RH"),
    PARACHOQUE_DELANT("PARACHOQUE DELANT"),
    PARACHOQUE_POST("PARACHOQUE POST"),
    MASCARILLA("MASCARILLA"),
    NIQUELADO("NIQUELADO"),
    FARO_DELANT_LH("FARO DELANT LH"),
    FARO_DELANT_RH("FARO DELANT RH"),
    FARO_POST_LH("FARO POST LH"),
    FARO_POST_RH("FARO POST RH"),
    LUZ_DE_STOP("LUZ DE STOP"),
    LUZ_DE_SALON("LUZ DE SALON"),
    TAPIZADO_DELANT_LH("TAPIZADO DELANT LH"),
    TAPIZADO_DELANT_RH("TAPIZADO DELANT RH"),
    TAPIZADO_POST_LH("TAPIZADO POST LH"),
    TAPIZADO_POST_RH("TAPIZADO POST RH"),
    PARABRISAS_DELANT("PARABRISAS DELANT"),
    PARABRISAS_POST("PARABRISAS POST"),
    VENTANA_DELANT_LH("VENTANA DELANT LH"),
    VENTANA_DELANT_RH("VENTANA DELANT RH"),
    VENTANA_POST_LH("VENTANA POST LH"),
    VENTANA_POST_RH("VENTANA POST RH"),
    CONSOLA_CENTRAL("CONSOLA CENTRAL"),
    TABLERO("TABLERO"),
    ODOMETRO("ODOMETRO"),
    ASIENTOS("ASIENTOS"),
    TECHO_INTERNO("TECHO_INTERNO"),
    CONTROL_DE_ENCENDIDO("CONTROL DE ENCENDIDO"),
    PINTURA("PINTURA"),
    TAPA_COMBUSTIBLE("TAPA COMBUSTIBLE"),
    MANIJAS("MANIJAS"),
    PIANOS_PLUMAS("PIANOS/PLUMAS"),
    PERNOS_OXIDADOS("PERNOS ÓXIDADOS"),
    RETROVISORES("RETROVISORES"),
    MOLDURA("MOLDURA INTERNA/EXTERNA"),
    AROS("AROS"),
    ANTENA("ANTENA"),
    LLUVIA_ACIDA("LLUVIA ÁCIDA")
    ;
    
    private String nombre;    

    private ParteVehiculoEnum(String nombre) {
        this.nombre = nombre;
    }
    
    public static ParteVehiculoEnum buscarPorNombre(String nombre)
    {
        for (ParteVehiculoEnum value : ParteVehiculoEnum.values()) {
            if(value.getNombre().equals(nombre))
            {
                return value;
            }
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
