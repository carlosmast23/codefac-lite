/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Carlos
 */
public enum ModuloCodefacEnum {
    COMPRA("Compra","COM","modulo_compra",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/compra.png")),
    INVENTARIO("Inventario","INVS","modulo_inventario",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/inventario.png")),
    FACTURACION("Facturacion","FAC","modulo_facturacion",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/facturacion.png")),
    CRM("Crm","CRM","modulo_crm",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/crm.png")),
    GESTIONA_ACADEMICA("Academico","ACAS","modulo_gestion_academica",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/gestion_academica.png")),
    SISTEMA("Sistema","SIST","modulo_sistema",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/sistema.png"));
    private String nombre;
    private String codigo;
    private String nombrePropiedad;
    private ImageIcon icono;

    private ModuloCodefacEnum(String nombre, String codigo, String nombrePropiedad,URL path) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.nombrePropiedad = nombrePropiedad;
        setImageIcon(path);
    }

    private void setImageIcon(URL path)
    {
        icono=new ImageIcon(path);
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

    public ImageIcon getIcono() {
        return icono;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    //Metodos personalizados
    
    public static ModuloCodefacEnum getModuloCodefacEnumByCodigo(String codigo)
    {
        for (ModuloCodefacEnum modulo : ModuloCodefacEnum.values()) {
            if(modulo.getCodigo().equals(codigo))
            {
                return modulo;
            }
        }
        return null;
    }
    
}
