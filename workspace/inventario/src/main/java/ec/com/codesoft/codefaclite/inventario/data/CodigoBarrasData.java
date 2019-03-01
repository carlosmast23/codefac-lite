/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.data;

import java.awt.Image;

/**
 *
 * @author Carlos
 */
public class CodigoBarrasData {
    private Image imagen;    
    private String codigo;

    public CodigoBarrasData() 
    {
        
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
    
    
    
}
