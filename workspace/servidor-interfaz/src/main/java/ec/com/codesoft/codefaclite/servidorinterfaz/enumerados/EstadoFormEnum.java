/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 * Enum que permite distinguir en los formularios en que tipo de estado nos encontramos
 * si podemos grabar o editar
 * @author Carlos
 */
public enum EstadoFormEnum {
    GRABAR("grabar"),
    EDITAR("editar");
    
    private final static String SIGNO_APERTURA="[";
    private final static String SIGNO_CIERRE="]";
    
    private String nombre;

    private EstadoFormEnum(String nombre) {
        this.nombre = nombre;
    }
    
    public String construirFormato(String nombreFormulario)
    {
        return nombreFormulario+" "+" "+SIGNO_APERTURA+nombre+" "+SIGNO_CIERRE;
    }
    
}
