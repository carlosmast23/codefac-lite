/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import javax.swing.JPanel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface DialogPanelAuxIf<T> {
    public abstract JPanel getPanelAuxiliar(T dato);
    
}
