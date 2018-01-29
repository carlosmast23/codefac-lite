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
    /**
     * Documento de la factura que puede ser electronica o fisica
     */
    FACTURA("Factura","FAC",DocumentoEnum.VENTAS,true,true),
    /**
     * Nota de Venta para los contribuyentes que estan en modalidad RIDE
     */
    NOTA_VENTA("Nota de venta","NVT",DocumentoEnum.VENTAS,true,false);

    private TipoDocumentoEnum(String nombre,String codigo, DocumentoEnum documentoEnum,Boolean comprobanteFisico,Boolean comprobanteElectronico) {
        this.nombre=nombre;
        this.codigo=codigo;
        this.comprobanteElectronico = comprobanteElectronico;
        this.comprobanteFisico = comprobanteFisico;
        this.documentoEnum = documentoEnum;
    }
    
    private String nombre;
    private String codigo;
    private Boolean comprobanteElectronico;
    private Boolean comprobanteFisico;
    private DocumentoEnum documentoEnum;

    public Boolean getComprobanteElectronico() {
        return comprobanteElectronico;
    }

    public Boolean getComprobanteFisico() {
        return comprobanteFisico;
    }

    public DocumentoEnum getDocumentoEnum() {
        return documentoEnum;
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
    public static List<TipoDocumentoEnum> obtenerPorDocumentosFisico(DocumentoEnum documentoEnum)
    {
        List<TipoDocumentoEnum> tipoDocumentosEnum=new ArrayList<TipoDocumentoEnum>();
        TipoDocumentoEnum[] documentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum documento : documentos) {
            if(documento.getDocumentoEnum().equals(documentoEnum) && documento.getComprobanteFisico())
            {
                tipoDocumentosEnum.add(documento);
            }
        }
        return tipoDocumentosEnum;
    }
    
        /**
     * Metodos personalizado para obtener documento y si es comprobante fisico o electronica
     */
    public  static List<TipoDocumentoEnum> obtenerPorDocumentosElectronicos(DocumentoEnum documentoEnum)
    {
        List<TipoDocumentoEnum> tipoDocumentosEnum=new ArrayList<TipoDocumentoEnum>();
        TipoDocumentoEnum[] documentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum documento : documentos) {
            if(documento.getDocumentoEnum().equals(documentoEnum) && documento.getComprobanteElectronico())
            {
                tipoDocumentosEnum.add(documento);
            }
        }
        return tipoDocumentosEnum;
    }
    
    public  static TipoDocumentoEnum obtenerDocumentoPorCodigo(String codigo)
    {
        TipoDocumentoEnum[] documentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum documento : documentos) {
            if(documento.getCodigo().equals(codigo))
            {
                return documento;
            }
        }
        return null;
    }
}
