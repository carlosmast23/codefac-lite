/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
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
@Table(name = "cartera")
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cartera", fetch = FetchType.EAGER)
    private List<CarteraDetalle> detalles;

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
        
    }
}
