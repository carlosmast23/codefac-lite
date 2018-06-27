/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Carlos
 */
@MappedSuperclass
public class ComprobanteEntity implements Serializable{
    
    @Column(name = "CLAVE_ACCESO")
    protected String claveAcceso;
    
    @Column(name = "SECUENCIAL")
    protected Integer secuencial;
    
    @Column(name = "PUNTO_ESTABLECIMIENTO")
    protected String puntoEstablecimiento;
    
    @Column(name = "PUNTO_EMISION")
    protected String puntoEmision;
    
    @Column(name = "EMPRESA_ID")
    protected Long empresaId;
    
    @Column(name = "USUARIO_ID")
    protected Long usuarioId;
    
    @Column(name = "FECHA_CREACION")
    protected Date fechaCreacion;
    
    @Column(name = "FECHA_EMISION")
    protected Date fechaEmision;
    
    @Column(name = "RAZON_SOCIAL")
    protected String razonSocial;
    
    @Column(name = "IDENTIFICACION")
    protected String identificacion;
    
    @Column(name = "DIRECCION")
    protected String direccion;
    
    @Column(name = "TELEFONO")
    protected String telefono;
    
    @Column(name = "ESTADO")
    protected String estado;
    

    public ComprobanteEntity() {
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    /**
     * Metodos Personalizados
     * @return 
     */
    public String getPreimpreso()
    {
       return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial+"",9,"0");
    }
    
    
    
    public enum ComprobanteEnumEstado {
        /**
         * Cuando la factura se grabo y se autorizo en el SRI y no aplica
         * ninguna nota de credito
         */
        FACTURADO("F", "Facturado"),
        /**
         * Estado cuando se graba la factura en la base de datos pero no esta
         * autorizado en el SRI
         */
        SIN_AUTORIZAR("S", "Sin Autorizar"),
        /**
         * Estado eliminado solo permitido si el comprobante no fue autorizado
         */
        ELIMINADO("E", "Eliminado");

        private ComprobanteEnumEstado(String estado, String nombre) {
            this.estado = estado;
            this.nombre = nombre;
        }

        private String estado;
        private String nombre;

        public static ComprobanteEnumEstado getEnum(String estado) {

            for (ComprobanteEnumEstado enumerador : ComprobanteEnumEstado.values()) {
                if (enumerador.estado.equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
