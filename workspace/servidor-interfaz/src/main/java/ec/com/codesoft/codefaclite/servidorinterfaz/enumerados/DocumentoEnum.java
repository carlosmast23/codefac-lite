/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public enum DocumentoEnum {
    /**
     * Documento de la factura que puede ser electronica o fisica
     */
    FACTURA("Factura","FAC",new ModuloCodefacEnum[]{
        ModuloCodefacEnum.FACTURACION,
        ModuloCodefacEnum.COMPRA},true,true),
    /**
     * Nota de Venta para los contribuyentes que estan en modalidad RIDE
     */
    NOTA_VENTA("Nota de venta","NVT",new ModuloCodefacEnum[]{
        ModuloCodefacEnum.FACTURACION,
        ModuloCodefacEnum.COMPRA},true,false),
    
    /**
     * Nota de credito para anular parcial o total facturas
     */
    NOTA_CREDITO("Nota de crédito","NVT",new ModuloCodefacEnum[]{},true,false),
    
    /**
     * Retenciones cuando la entidad es encargada de retener el IVA o la RENTA
     */
    RETENCIONES("Retención","RET",new ModuloCodefacEnum[]{},true,false),
    
    /**
     * Documentos emitidos por maquinas registradoras
     */
    TIQUETES_MAQUINAS_REGISTRADORAS("Tiquet maq.reg","TMR",new ModuloCodefacEnum[]{
        ModuloCodefacEnum.COMPRA},true,false);
    
    
    //Tiquetes emitidos por máquinas registradoras;
    
    private DocumentoEnum(String nombre,String codigo, ModuloCodefacEnum[] moduloEnum,Boolean comprobanteFisico,Boolean comprobanteElectronico) {
        this.nombre=nombre;
        this.codigo=codigo;
        this.comprobanteElectronico = comprobanteElectronico;
        this.comprobanteFisico = comprobanteFisico;
        this.moduloEnum = moduloEnum;
    }
    
    private String nombre;
    private String codigo;
    private Boolean comprobanteElectronico;
    private Boolean comprobanteFisico;
    private ModuloCodefacEnum[] moduloEnum;

    public Boolean getComprobanteElectronico() {
        return comprobanteElectronico;
    }

    public Boolean getComprobanteFisico() {
        return comprobanteFisico;
    }

    public ModuloCodefacEnum[] getModuloEnum() {
        return moduloEnum;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    /**
     * Metodos personalizado para obtener documento y si es comprobante fisico o electronica
     */
    public static List<DocumentoEnum> obtenerPorDocumentosFisico(ModuloCodefacEnum moduloEnum)
    {
        List<DocumentoEnum> documentosEnum=new ArrayList<DocumentoEnum>();
        DocumentoEnum[] documentos=DocumentoEnum.values();
        for (DocumentoEnum documento : documentos) {
            for(ModuloCodefacEnum modulo:documento.getModuloEnum())
            {
                if(modulo.equals(moduloEnum) && documento.comprobanteFisico)
                {
                    documentosEnum.add(documento);
                }
            }   

        }
        return documentosEnum;
    }
    
     /**
     * Metodos personalizado para obtener documento y si es comprobante fisico o electronica
     */
    public  static List<DocumentoEnum> obtenerPorDocumentosElectronicos(ModuloCodefacEnum moduloEnum)
    {
        List<DocumentoEnum> documentosEnum=new ArrayList<DocumentoEnum>();
        DocumentoEnum[] documentos=DocumentoEnum.values();
        for (DocumentoEnum documento : documentos) {
            for(ModuloCodefacEnum modulo:documento.getModuloEnum())
            {
                if(modulo.equals(moduloEnum) && documento.comprobanteElectronico)
                {
                    documentosEnum.add(documento);
                }
            }   

        }
        return documentosEnum;
    }
    
    public  static DocumentoEnum obtenerDocumentoPorCodigo(String codigo)
    {
        DocumentoEnum[] documentos=DocumentoEnum.values();
        for (DocumentoEnum documento : documentos) {
            if(documento.getCodigo().equals(codigo))
            {
                return documento;
            }
        }
        return null;
    }
    
    /**
     * Obtiene los documentos que pertenezcan al modulo seleccionado
     * @param modulo
     * @return 
     */
    public  static List<DocumentoEnum> obtenerDocumentoPorModulo(ModuloCodefacEnum modulo)
    {
        List<DocumentoEnum> documentosEnum=new ArrayList<DocumentoEnum>();
        DocumentoEnum[] documentos=DocumentoEnum.values();
        for (DocumentoEnum documento : documentos) {
            ModuloCodefacEnum modulos[]=documento.getModuloEnum();
            for (ModuloCodefacEnum modulo1oEnum : modulos) {
                documentosEnum.add(documento);
                break;
            }
        }
        return documentosEnum;
    }
    
}
