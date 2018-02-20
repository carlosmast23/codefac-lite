/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;

/**
 *
 * @author Carlos
 */
public enum VentanaEnum {
    CLIENTE_PANEL("","CLIE","Cliente model",ModuloCodefacEnum.CRM,CategoriaMenuEnum.GESTIONAR),
    PRODUCTO_PANEL("ec.com.codesoft.codefaclite.crm.model.ProductoModel","PROD","Producto model",ModuloCodefacEnum.CRM,CategoriaMenuEnum.GESTIONAR);

    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu) {
        this.clase = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
    }
        
    private String clase;
    private String codigo;    
    private String nombre;
    private ModuloCodefacEnum modulo;
    private CategoriaMenuEnum categoriaMenu;

    public String getClase() {
        return clase;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public ModuloCodefacEnum getModulo() {
        return modulo;
    }

    public CategoriaMenuEnum getCategoriaMenu() {
        return categoriaMenu;
    }
    
    

}
