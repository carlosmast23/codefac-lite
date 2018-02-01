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
    
    VENTA(ModuloEnum.VENTAS,"VET","Ventas"),
    /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_INVENTARIO(ModuloEnum.COMPRAS,"COI","Compra Inventario"),
        /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_SERVICIOS(ModuloEnum.COMPRAS,"COS","Compra Servicios"),
    /**
     * Tipo de compra que se registra como gastos de la empresa
     */
    COMPRA_GASTOS(ModuloEnum.COMPRAS,"COG","Compra Gastos"),    
    /**
     * Retenciones del iva o del inventario
     */
    RETENCIONES(ModuloEnum.RETENCIONES,"RET","Retenciones"),;
    
    private TipoDocumentoEnum(ModuloEnum moduloEnum,String codigo,String nombre) {
        this.moduloEnum = moduloEnum;
        this.codigo=codigo;
        this.nombre=nombre;        
    }
    
    private String codigo;
    
    private String nombre;
    
    private ModuloEnum moduloEnum;

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el objeto por el codigo del tipo de documento
     * @param codigo
     * @return 
     */
    public  static TipoDocumentoEnum obtenerTipoDocumentoPorCodigo(String codigo)
    {
        TipoDocumentoEnum[] tipoDocumentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum tipo : tipoDocumentos) {
            if(tipo.getCodigo().equals(codigo))
            {
                return tipo;
            }
        }
        return null;
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
