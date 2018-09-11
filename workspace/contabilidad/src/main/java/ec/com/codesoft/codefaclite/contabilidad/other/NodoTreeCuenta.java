/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.contabilidad.other;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Cuenta;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Carlos
 */
public class NodoTreeCuenta extends DefaultMutableTreeNode{
    public Cuenta cuenta;    

    public NodoTreeCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    

    @Override
    public String toString() {
        return cuenta.toString();
    }
    
    
}
