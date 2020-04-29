/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NombresEntidadesJPA;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = NombresEntidadesJPA.CARTERA)
public class Cartera implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    /**
     * Este campo me permite identificar a quien corresponde el cliente o proveedor
     * dependiendo el documento
     */
    @JoinColumn(name = "PERSONA_ID")
    private Persona persona;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @Column(name = "FECHA_EMISION")
    protected Date fechaEmision;
    
    @Column(name = "TOTAL")
    private BigDecimal total;
    
    @Column(name = "SALDO")
    private BigDecimal saldo;

    public Cartera() {
    }

    public Cartera(Persona persona, BigDecimal total, BigDecimal saldo, String puntoEstablecimiento, String puntoEmision, String codigoDocumento, String tipoCartera, Sucursal sucursal, Usuario usuario,GeneralEnumEstado estadoEnum) {
        this.persona = persona;
        this.total = total;
        this.saldo = saldo;
        this.puntoEstablecimiento = puntoEstablecimiento;
        this.puntoEmision = puntoEmision;
        this.secuencial=0;
        this.codigoDocumento = codigoDocumento;
        this.tipoCartera = tipoCartera;
        this.sucursal = sucursal;
        this.usuario = usuario;
        this.fechaCreacion=UtilidadesFecha.getFechaHoy();
        this.fechaEmision=UtilidadesFecha.getFechaHoy();
        this.estado=estadoEnum.getEstado();
    }
    
    
    
    /**
     * Referencia que me permite grabar el id  de donde viene el documento y en conjunto con el
     * documento puedo saber a donde consultar
     */
    @Column(name = "REFERENCIA_ID")
    private Long referenciaID;
    
    @Column(name = "SECUENCIAL")
    protected Integer secuencial;
    
    @Column(name = "PUNTO_ESTABLECIMIENTO")
    protected String puntoEstablecimiento;
    
    @Column(name = "PUNTO_EMISION")
    protected String puntoEmision;
    
    /**
     * Codigo del documento para poder enlazar posteriormente con la referencia si necesito algun dato adicional
     */
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    
    @Column(name = "TIPO_CARTERA")
    private String tipoCartera;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @JoinColumn(name = "SUCURSAL_ID")
    private Sucursal sucursal;
    
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    
    @Column(name = "AUTORIZACION")
    private String autorizacion;

    @Column(name = "REFERENCIA_MANUAL")
    private String referenciaManual;

    @Column(name = "CODIGO_AUXILIAR")
    private String codigoAuxiliar;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cartera", fetch = FetchType.EAGER)
    private List<CarteraDetalle> detalles;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carteraAfectada", fetch = FetchType.EAGER)
    private List<CarteraCruce> cruces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getReferenciaID() {
        return referenciaID;
    }

    public void setReferenciaID(Long referenciaID) {
        this.referenciaID = referenciaID;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(String tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public List<CarteraCruce> getCruces() {
        return cruces;
    }

    public void setCruces(List<CarteraCruce> cruces) {
        this.cruces = cruces;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodigo() 
    {
        return codigo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getReferenciaManual() {
        return referenciaManual;
    }

    public void setReferenciaManual(String referenciaManual) {
        this.referenciaManual = referenciaManual;
    }

    

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }
    
    public String obtenerDescripciones()
    {
        return UtilidadesLista.castListToString(detalles,",",new UtilidadesLista.CastListInterface<CarteraDetalle>() {
            @Override
            public String getString(CarteraDetalle dato) {
                return dato.getDescripcion();
            }
        });
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cartera other = (Cartera) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
    
    public void setEstadoEnum(GeneralEnumEstado estadoEnum)
    {
        this.estado=estadoEnum.getEstado();
    }
    

    public void setDetalles(List<CarteraDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<CarteraDetalle> getDetalles() {
        return detalles;
    }
    
    public DocumentoEnum getCarteraDocumentoEnum()
    {
        return DocumentoEnum.obtenerDocumentoPorCodigo(codigoDocumento);
    }
    
    
    public TipoCarteraEnum getTipoCarteraEnum()
    {
        return TipoCarteraEnum.buscarPorLetra(tipoCartera);
    }
    //Metodos personalizados
    
    public void addDetalle(CarteraDetalle carteraDetalle)
    {
        if(detalles==null)
        {
            detalles=new ArrayList<CarteraDetalle>();        
        }
        
        carteraDetalle.setCartera(this);
        detalles.add(carteraDetalle);
    }
    
    public  BigDecimal totalDetalles()
    {
        BigDecimal totalCartera=BigDecimal.ZERO;
        if(detalles!=null)
        {
            for (CarteraDetalle detalle : detalles) {
                totalCartera=totalCartera.add(detalle.getTotal());
            }
        }
        return totalCartera;
    }
    
    public String getPreimpreso() {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision, 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento, 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(secuencial + "", 9, "0");
    }
    
    /**
     * Metodos Personalizados
     */
    
    public BigDecimal calcularValorCobrado()
    {
        return this.total.subtract(saldo);
    }
    
    public enum TipoSaldoCarteraEnum
    {
        SIN_SALDO("Sin Saldo"),
        CON_SALDO("Con Saldo"),
        TODOS("Todos");
        
        private String nombre;

        private TipoSaldoCarteraEnum(String nombre) 
        {
            this.nombre = nombre;
        }
        
        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
    }
    
    /**
     * Enum que identifica que tipo de cartera es de cliente o de proveedores
     */
    public enum TipoCarteraEnum
    {
        CLIENTE("Cliente","C"), PROVEEDORES("Proveedor","P");

        private TipoCarteraEnum(String nombre, String letra) {
            this.nombre = nombre;
            this.letra = letra;
        }
        
        private String nombre;
        private String letra;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public void setLetra(String letra) {
            this.letra = letra;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        
        public static TipoCarteraEnum buscarPorLetra(String letra)
        {
            for (TipoCarteraEnum object : TipoCarteraEnum.values()) {
                if(object.getLetra().equals(letra))
                {
                    return object;
                }
            }
            return null;
        }        
        
    }
    
    /**
     * Tipo de enum que me permie separar los mismo tipos de comprobante pero por ventas y compras
     */
    public enum CarteraCategoriaEnum
    {        
        COMPRAS("Compras",TipoCarteraEnum.PROVEEDORES,DocumentoCategoriaEnum.COMPROBANTES_VENTA),
        VENTAS("Ventas",TipoCarteraEnum.CLIENTE,DocumentoCategoriaEnum.COMPROBANTES_VENTA),
        RETENCIONES_CLIENTES("Retención Clientes",TipoCarteraEnum.CLIENTE,DocumentoCategoriaEnum.COMPROBANTES_RETENCION),
        RETENCIONES_PROVEEDOR("Retención Proveedor",TipoCarteraEnum.PROVEEDORES,DocumentoCategoriaEnum.COMPROBANTES_RETENCION),
        DOCUMENTOS_COMPLEMENTARIOS_CLIENTE("Doc Complemetarios",TipoCarteraEnum.CLIENTE,DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS),
        DOCUMENTOS_COMPLEMENTARIOS_PROVEEDOR("Doc Complemetarios",TipoCarteraEnum.PROVEEDORES,DocumentoCategoriaEnum.DOCUMENTOS_COMPLEMENTARIOS),
        COMPROBANTE_INGRESO("Comprobante de Ingreso",TipoCarteraEnum.CLIENTE,DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS),
        COMPROBANTE_EGRESO("Comprobante de Egreso",TipoCarteraEnum.PROVEEDORES,DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS);

        private CarteraCategoriaEnum(String nombre,TipoCarteraEnum tipoCartera,DocumentoCategoriaEnum documentoCategoriaEnum) {
            this.tipoCartera = tipoCartera;
            this.nombre = nombre;
            this.documentoCategoriaEnum = documentoCategoriaEnum;
        }
        
        private TipoCarteraEnum tipoCartera;
        private String nombre;
        private DocumentoCategoriaEnum documentoCategoriaEnum;

        public TipoCarteraEnum getTipoCartera() {
            return tipoCartera;
        }

        public String getNombre() {
            return nombre;
        }

        public DocumentoCategoriaEnum getDocumentoCategoriaEnum() {
            return documentoCategoriaEnum;
        }

        
        public static List<CarteraCategoriaEnum> buscarPorTipoCartera(TipoCarteraEnum tipo)
        {
            List<CarteraCategoriaEnum> resultados=new ArrayList<CarteraCategoriaEnum>();
            for (CarteraCategoriaEnum object : CarteraCategoriaEnum.values()) 
            {
                if(object.tipoCartera.equals(tipo))
                {
                    resultados.add(object);
                }
            }
            return resultados;
        }
        
        public static CarteraCategoriaEnum buscarPorTipoYDocumentoCategoria(TipoCarteraEnum tipoEnum,DocumentoCategoriaEnum documentoCategoriaEnum)
        {
            for (CarteraCategoriaEnum value : CarteraCategoriaEnum.values()) {
                if(value.getTipoCartera().equals(tipoEnum) && value.getDocumentoCategoriaEnum().equals(documentoCategoriaEnum))
                {
                    return value;
                }
            }
            return null;
        }
    }
}
