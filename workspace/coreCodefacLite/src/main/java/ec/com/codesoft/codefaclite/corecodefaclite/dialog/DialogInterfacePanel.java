/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

/**
 *
 * @author Carlos
 * Funcion que proporciona un interfaz para pasar una referencia de un objeto y permitir
 * utilizar como una pantalla de dialogo relacionada con otra para pasar un valor al
 * formulario principal
 */
public interface DialogInterfacePanel<T> {
    public static final String CLIENTE_PANEL="ec.com.codesoft.codefaclite.crm.model.ClienteModel";
    public static final String PRODUCTO_PANEL="ec.com.codesoft.codefaclite.crm.model.ProductoModel";
    
    public T getResult();
}
