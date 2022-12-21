/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import java.util.List;

/**
 * Interface que me va a permitir tener configuraciones extras para los dialogos
 * @author DellWin10
 */
public interface DialogoConfigAuxIf<T> {
    
    /**
     * Metodo que me permite hacer un pre procesamiento del resultado antes de mostrar
     * @param datos 
     */
    public List<T> preProcessResult(List<T> datos);
}
