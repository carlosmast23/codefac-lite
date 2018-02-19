/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 */
public enum ModuloCodefacEnum {
    INVENTARIO("Inventario","INVS","modulo_inventario"),
    FACTURACION("Facturacion","FAC","modulo_facturacion"),
    CRM("Crm","CRM","modulo_crm"),
    GESTIONA_ACADEMICA("Academico","ACAS","modulo_gestion_academica");
    
    private String nombre;
    private String codigo;
    private String nombrePropiedad;

    private ModuloCodefacEnum(String nombre, String codigo, String nombrePropiedad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.nombrePropiedad = nombrePropiedad;
    }

    

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombrePropiedad() {
        return nombrePropiedad;
    }
    
    
    
    
    
}
