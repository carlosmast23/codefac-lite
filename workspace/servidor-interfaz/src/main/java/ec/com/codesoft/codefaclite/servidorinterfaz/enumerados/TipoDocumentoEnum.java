/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Estos documentos son internos del sistema y me permite clasificar a que proceso
 * pertenece o con cual modulo se debe relacionar
 * @author Carlos
 * @nota No mover los codigos anteriores porque en la basese de datos ya estan referenciados a las mismas
 */
public enum TipoDocumentoEnum {
    
    /**
     * Tipo de documento que vincula a un producto pero sin afectar el stock
     */
    LIBRE(ModuloCodefacEnum.FACTURACION, 
            "LIB", 
            "Libre", 
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION}),
    
        /**
     * Tipo de documento general que uso para clasificar alguna compra como por ejemplo en las retenciones
     */
    COMPRA(ModuloCodefacEnum.COMPRA, 
            "COG", 
            "Compra", 
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.COMPRA}),
    
    /**
     * Tipo de documento que afecta al stock de los productos
     */
    INVENTARIO(ModuloCodefacEnum.FACTURACION,
            "VET",
            "Inventario",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.INVENTARIO}),
    /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_INVENTARIO(ModuloCodefacEnum.COMPRA, 
            "COI", 
            "Compra Inventario", 
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),
        /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_SERVICIOS(ModuloCodefacEnum.COMPRA,
            "COS",
            "Compra Servicios",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),
    /**
     * Tipo de compra que se registra como gastos de la empresa
     */
    COMPRA_GASTOS(ModuloCodefacEnum.COMPRA,
            "COG",
            "Compra Gastos",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),    
    /**
     * Retenciones del iva o del inventario
     */
    RETENCIONES(ModuloCodefacEnum.COMPRA,
            "RET","Retenciones",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),
    
    /**
     *  Tipo de documento cuando se hace un ingreso de los ensambles
     */
    ENSAMBLE_INGRESO(ModuloCodefacEnum.INVENTARIO,
            "INV",
            "Ingreso Inventario Ensamble",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),
    
     /**
     *  Tipo de documento cuando se hace un egreso de los ensambles
     */
    ENSAMBLE_EGRESO(ModuloCodefacEnum.INVENTARIO,
            "INE",
            "Egreso Inventario Ensamble",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO),
    
    AGREGAR_MERCADERIA_MANUAL(ModuloCodefacEnum.INVENTARIO,
            "AMM",
            "Ingreso Manual",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),
    
    VENTA_INVENTARIO(ModuloCodefacEnum.INVENTARIO,
            "VEI",
            "Venta inventario",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO),
    
    QUITAR_MERCADERIA_MANUAL(ModuloCodefacEnum.INVENTARIO,
            "QMM",
            "Eliminar Manual",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO),
    
    
    /**
     * Tipo de documento utilizado para facturar desde el modulo acaemico
     */
    ACADEMICO(ModuloCodefacEnum.FACTURACION,
            "ACA","Académico",
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.GESTIONA_ACADEMICA}),
    
    /**
     * Tipo de documento utilizado para facturar desde los presupuestos
     */
    PRESUPUESTOS(ModuloCodefacEnum.FACTURACION,
            "PRE",
            "Presupuestos",
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.SERVICIOS});

    
    public static final String AFECTA_INVENTARIO_POSITIVO="+";
    public static final String AFECTA_INVENTARIO_NEGATIVO="-";
    public static final String NO_AFECTA_INVETARIO="";
    
    private TipoDocumentoEnum(ModuloCodefacEnum moduloEnum,String codigo,String nombre,String signoInventario) {
        this.moduloEnum = moduloEnum;
        this.codigo=codigo;
        this.nombre=nombre;        
        this.signoInventario=signoInventario;
        this.modulosPermisos=new ModuloCodefacEnum[0];
    }
    
    private TipoDocumentoEnum(ModuloCodefacEnum moduloEnum,String codigo,String nombre,String signoInventario,ModuloCodefacEnum[] modulosPermisos) {
        this.moduloEnum = moduloEnum;
        this.codigo=codigo;
        this.nombre=nombre;        
        this.signoInventario=signoInventario;
        this.modulosPermisos=modulosPermisos;
    }
    
    private String codigo;
    
    private String nombre;
    
    private ModuloCodefacEnum moduloEnum;
        
    /**
     * El signo del inventario para saber si se tiene que restar o sumar
     */
    private String signoInventario;
    
    /**
     * Lista de los modulos en los cuales son permitidos el tipo de documento
     */
    private ModuloCodefacEnum[] modulosPermisos;

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public ModuloCodefacEnum getModuloEnum() {
        return moduloEnum;
    }

    public ModuloCodefacEnum[] getModulosPermisos() {
        return modulosPermisos;
    }

    public String getSignoInventario() {
        return signoInventario;
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
    
    
    public  static List<TipoDocumentoEnum> obtenerTipoDocumentoPorModulo(ModuloCodefacEnum modulo)
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

    
    public  static List<TipoDocumentoEnum> obtenerTipoDocumentoPorModulo(ModuloCodefacEnum modulo,List<ModuloCodefacEnum> modulosActivos)
    {
        
        List<TipoDocumentoEnum> documentosEnumResultado=new ArrayList<TipoDocumentoEnum>();
        
        List<TipoDocumentoEnum> documentosPorModulo=obtenerTipoDocumentoPorModulo(modulo);
        
        ///Si esta activo el modulo de desarrollo activo todos los documentos y los envio
        if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO))
        {
            return documentosPorModulo;
        }
        
        for (ModuloCodefacEnum modulosActivo : modulosActivos) 
        {
            for (TipoDocumentoEnum tipoDocumento : documentosPorModulo) {

                //Buscar documentos que son permitidos para la lista de modulos activos
                for (ModuloCodefacEnum moduloPermitido : tipoDocumento.getModulosPermisos()) {
                    
                    if (moduloPermitido.equals(modulosActivo)) 
                    {
                        documentosEnumResultado.add(tipoDocumento);
                    }
                }
            }

        }
        
        return documentosEnumResultado;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
