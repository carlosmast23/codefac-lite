/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Ver si aumentar un campo para saber cuales son documentos legales del Sri y cuales son documentos internos para otros procesos
 * @author Carlos
 */
public enum DocumentoEnum {
    /**
     * Documento de la factura que puede ser electronica o fisica
     * TODO:Crear otro documento para facturacion fisica y poder tener clasificado
     */
    FACTURA("Factura",
            "FAC",
            DocumentoCategoriaEnum.COMPROBANTES_VENTA,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION,ModuloCodefacEnum.COMPRA},
            false,
            true,
            "01"),
    
    /**
     * Documento no valido por el SRI //Ver si hago una clasificacion para diferencia este tipo de documentos
     */
    PROFORMA("Proforma",
            "PRO",
            DocumentoCategoriaEnum.COMPROBANTES_VENTA,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},
            false,
            false),
    /**
     * Nota de Venta para los contribuyentes que estan en modalidad RIDE 
     * TODO: Analizar si este documento tiene o no validez tributarioa o ver si creo otro documento
     */
    NOTA_VENTA("Nota de venta",
            "NVT",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION,ModuloCodefacEnum.COMPRA},
            true,
            false,
            "02"),
    
    NOTA_VENTA_INTERNA("Nota de venta interna",
            "NVI",
            DocumentoCategoriaEnum.COMPROBANTES_VENTA,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION,ModuloCodefacEnum.COMPRA},
            true,
            false,
            "999",
            false),
    
    BOLETOS_ESPETACULOS_PUBLICOS("Boletos espectáculos públicos",
            "BEP",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{ ModuloCodefacEnum.COMPRA},
            true,
            false,
            "08"),
    
    /**
     * Documentos emitidos por maquinas registradoras
    */
    TIQUETES_MAQUINAS_REGISTRADORAS("Tiquet maq.reg",
            "TMR",
            DocumentoCategoriaEnum.COMPROBANTES_VENTA,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.COMPRA},
            true,
            false,
            "09"),
    
    PASAJES_EMPRESA_AVIACION("Pasajes empresas de aviación",
            "PEA",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{ ModuloCodefacEnum.COMPRA},
            true,
            false,
            "011"),
    
    DOCUMENTOS_INSTITUCIONES_FINANCIERAS("Documentos Instituciones financieras",
            "DIF",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.COMPRA},
            true,
            false,
            "12"),
    
    COMPROBANTE_CUOTAS_APORTES("Comprobantes de Cuotas o Aportes",
            "CCA",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.COMPRA},
            true,
            false,
            "19"),
    
    
    /**
     * Nota de credito para anular parcial o total facturas
     */
    NOTA_CREDITO("Nota de crédito",
            "NCR",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{},
            true,
            false),
    
    /**
     * Retenciones cuando la entidad es encargada de retener el IVA o la RENTA
     */
    RETENCIONES("Retención",
            "RET",
            DocumentoCategoriaEnum.COMPROBANTES_RETENCION,
            new ModuloCodefacEnum[]{},
            true,
            false),
    
    
    ABONOS("Abono",
            "ABO",
            DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS,
            new ModuloCodefacEnum[]{},
            true,
            false),
    
    GUIA_REMISION("Guía Remisión",
            "GIR",
            DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS,
            new ModuloCodefacEnum[]{},
            true,
            false),;
    
    
    
    
    //Tiquetes emitidos por máquinas registradoras;
    
    private DocumentoEnum(String nombre,String codigo,DocumentoCategoriaEnum categoria, ModuloCodefacEnum[] moduloEnum,Boolean comprobanteFisico,Boolean comprobanteElectronico) {
        this.nombre=nombre;
        this.codigo=codigo;
        this.categoria=categoria;
        this.comprobanteElectronico = comprobanteElectronico;
        this.comprobanteFisico = comprobanteFisico;
        this.moduloEnum = moduloEnum;
    }
    
    private DocumentoEnum(String nombre, String codigo, DocumentoCategoriaEnum categoria, ModuloCodefacEnum[] moduloEnum, Boolean comprobanteFisico, Boolean comprobanteElectronico,String codigoSri) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.categoria = categoria;
        this.comprobanteFisico = comprobanteFisico;
        this.comprobanteElectronico = comprobanteElectronico;        
        this.moduloEnum = moduloEnum;
        this.codigoSri=codigoSri;
        this.documentoLegal=true;
    }
    
    private DocumentoEnum(String nombre, String codigo, DocumentoCategoriaEnum categoria, ModuloCodefacEnum[] moduloEnum, Boolean comprobanteFisico, Boolean comprobanteElectronico,String codigoSri,Boolean documentoLegal) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.categoria = categoria;
        this.comprobanteElectronico = comprobanteElectronico;
        this.comprobanteFisico = comprobanteFisico;
        this.moduloEnum = moduloEnum;
        this.codigoSri=codigoSri;
        this.documentoLegal=documentoLegal;
    }
    
    private String nombre;
    private DocumentoCategoriaEnum categoria;
    private String codigo;
    private Boolean comprobanteElectronico;
    private Boolean comprobanteFisico;
    private ModuloCodefacEnum[] moduloEnum;
    private String codigoSri;
    /**
     * Este parametro me va a servir para clasificar si es un documento legal del Sri o en un documento para control interno
     */
    private Boolean documentoLegal;

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

    public String getCodigoSri() {
        return codigoSri;
    }

    public Boolean getDocumentoLegal() {
        return documentoLegal;
    }
    
    

    public DocumentoCategoriaEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(DocumentoCategoriaEnum categoria) {
        this.categoria = categoria;
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
    
    public  static List<DocumentoEnum> obtenerPorDocumentosElectronicos(ModuloCodefacEnum moduloEnum,ComprobanteEntity.TipoEmisionEnum tipoEmision)
    {
        List<DocumentoEnum> documentosEnum=new ArrayList<DocumentoEnum>();
        DocumentoEnum[] documentos=DocumentoEnum.values();
        for (DocumentoEnum documento : documentos) {
            for(ModuloCodefacEnum modulo:documento.getModuloEnum())
            {
                if(modulo.equals(moduloEnum))
                {
                    if(tipoEmision.equals(tipoEmision.ELECTRONICA) && documento.comprobanteElectronico)//Si me esta solicitando solo documentos electronicos verifico que el documento sea electronic
                    {
                        documentosEnum.add(documento);
                    }
                    else
                    {
                        if(tipoEmision.equals(tipoEmision.NORMAL) && documento.getComprobanteFisico()) //Si no me pido electronicos por defecto asumo que me esta pidiendo un comprobante fisico
                        {
                            documentosEnum.add(documento);
                        }
                    }
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
                if(modulo1oEnum.equals(modulo))
                {
                    documentosEnum.add(documento);
                }
                //sbreak;
            }
        }
        return documentosEnum;
    }
    
    public static List<DocumentoEnum> obtenerPorCategoria(DocumentoCategoriaEnum categoriaEnum)
    {
        List<DocumentoEnum> resultados=new ArrayList<DocumentoEnum>();
        for (DocumentoEnum object : DocumentoEnum.values()) {
            if(object.getCategoria().equals(categoriaEnum))
            {
                resultados.add(object);
            }
        }
        return resultados;
    }
    
    
    public static DocumentoEnum obtenerPorCodigoSri(String codigoSri)
    {
        if(codigoSri==null)return null;
            
        for (DocumentoEnum value : DocumentoEnum.values()) {
            if(value!=null && value.getCodigoSri().equals(codigoSri))
            {
                return value;
            }
        }
        return null;
    }
    
    /**
     *TODO: Reutilizar o Utilizar este Enum en todas partes para referirme a facturacion electronica o fisica
     */    
    public enum TipoEmisionDocumento
    {
        FISICA,
        ELECTRONICA,
    }
}
