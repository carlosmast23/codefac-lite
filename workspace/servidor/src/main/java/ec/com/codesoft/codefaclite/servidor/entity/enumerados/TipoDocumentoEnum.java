/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity.enumerados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public enum TipoDocumentoEnum {
    VENTA(ModuloEnum.VENTAS,"Ventas"),
    /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_INVENTARIO(ModuloEnum.COMPRAS,"Compra Inventario"),
    /**
     * Tipo de compra que se registra como gastos de la empresa
     */
    COMPRA_GASTOS(ModuloEnum.COMPRAS,"Compra Gastos"),    
    /**
     * Retenciones del iva o del inventario
     */
    RETENCIONES(ModuloEnum.RETENCIONES,"Retenciones"),;
    
    private TipoDocumentoEnum(ModuloEnum moduloEnum,String nombre) {
        this.moduloEnum = moduloEnum;
        this.nombre=nombre;        
    }
    
    private String nombre;
    
    private ModuloEnum moduloEnum;

    public String getNombre() {
        return nombre;
    }
    
    public  static List<TipoDocumentoEnum> obtenerTipoDocumentoPorModulo(ModuloEnum modulo)
    {
        List<TipoDocumentoEnum> documentosEnum=new ArrayList<TipoDocumentoEnum>();
        TipoDocumentoEnum[] tipoDocumentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum tipo : tipoDocumentos) {
            if(tipo.moduloEnum.equals(modulo))
            {
                documentosEnum.add(tipo);
            }
        }
        return documentosEnum;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
