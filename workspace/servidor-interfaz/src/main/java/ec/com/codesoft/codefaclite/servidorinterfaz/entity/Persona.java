/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
public class Persona implements Serializable, Comparable<Persona> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIENTE_ID")
    private Long idCliente;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "NOMBRE_LEGAL")
    private String nombreLegal;
    @Column(name = "TIPO_CLIENTE")
    private String tipCliente;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;
    @Column(name = "EXTENSION_TELEFONO")
    private String extensionTelefono;
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;

    @Column(name = "OBLIGADO_LLEVAR_CONTABILIDAD")
    private String obligadoLlevarContabilidad;

    @JoinColumn(name = "NACIONALIDAD_ID")
    @ManyToOne
    private Nacionalidad nacionalidad;

    @JoinColumn(name = "SRI_FORMA_PAGO_ID")
    private SriFormaPago sriFormaPago;
    
    /**
     * Variable para saber si el envia clientes recomendados
     */
    @Column(name = "CONTACTO_CLIENTES")
    private String contactoCliente;

    @Column(name = "CONTACTO_CLIENTES_PORCENTAJE")
    private BigDecimal contactoClientePorcentaje;

    //@JoinColumn(name = "SRI_IDENTIFICACION_ID")
    //private SriIdentificacion sriTipoIdentificacion;
    /**
     * Variable para identificar el tipo de la persona, si es proveedor ,
     * cliente, o ambos
     */
    @Column(name = "TIPO_OPERADOR")
    private String tipo;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idcliente) {
        this.idCliente = idcliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public String getTipCliente() {
        return tipCliente;
    }

    public void setTipCliente(String tipCliente) {
        this.tipCliente = tipCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
    }

    public String getExtensionTelefono() {
        return extensionTelefono;
    }

    public void setExtensionTelefono(String extensionTelefono) {
        this.extensionTelefono = extensionTelefono;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public SriFormaPago getSriFormaPago() {
        return sriFormaPago;
    }

    public void setSriFormaPago(SriFormaPago sriFormaPago) {
        this.sriFormaPago = sriFormaPago;
    }

    //public SriIdentificacion getSriTipoIdentificacion() {
    //    return sriTipoIdentificacion;
    //}
    //public void setSriTipoIdentificacion(SriIdentificacion sriTipoIdentificacion) {
    //    this.sriTipoIdentificacion = sriTipoIdentificacion;
    //}
    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getContactoCliente() {
        return contactoCliente;
    }

    public void setContactoCliente(String contactoCliente) {
        this.contactoCliente = contactoCliente;
    }

    public BigDecimal getContactoClientePorcentaje() {
        return contactoClientePorcentaje;
    }

    public void setContactoClientePorcentaje(BigDecimal contactoClientePorcentaje) {
        this.contactoClientePorcentaje = contactoClientePorcentaje;
    }
    
    

    ///Metodos personalizados
    public String getNombresCompletos() {
        String nombresTmp = (nombres != null) ? nombres : "";
        String apellidosTmp = (apellidos != null) ? apellidos : "";
        return nombresTmp + " " + apellidosTmp;
    }

    public String getNombreSimple() {
        return apellidos.split(" ")[0] + " " + nombres.split(" ")[0];
    }

    public String getTelefonosTodos() {
        String telefonos = "";

        if (telefonoConvencional != null && !telefonoConvencional.equals("")) {
            telefonos = telefonoConvencional;
        }

        if (telefonoCelular != null && !telefonoCelular.equals("")) {
            //Si ya existe un telefono anterior agregado aumento el separador
            if (!telefonos.equals("")) {
                telefonos = telefonos + " - ";
            }

            telefonos = telefonos + telefonoCelular;
        }

        return telefonos;

    }

    public TipoIdentificacionEnum getTipoIdentificacionEnum() {
        return TipoIdentificacionEnum.obtenerPorLetra(tipoIdentificacion);
    }

    public EnumSiNo getObligadoLlevarContabilidadEnum() {
        return EnumSiNo.getEnumByLetra(obligadoLlevarContabilidad);
    }
    
    public EnumSiNo getContactoClientesEnum()
    {
        return EnumSiNo.getEnumByLetra(contactoCliente);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificacion != null ? identificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.identificacion == null && other.identificacion != null) || (this.identificacion != null && !this.identificacion.equals(other.identificacion))) {
            return false;
        }
        return true;
    }

    /**
     * Datos adicionales
     *
     * @return
     */
    public OperadorNegocioEnum getTipoEnum() {
        return OperadorNegocioEnum.getEnum(tipo);
    }

    @Override
    public String toString() {
        return identificacion + " - " + getNombresCompletos();
    }

    @Override
    public int compareTo(Persona o) {
        return this.getIdCliente().compareTo(o.getIdCliente());
    }

    public enum TipoIdentificacionEnum {
        RUC("R", "Ruc"),
        CEDULA("C", "Cédula"),
        PASAPORTE("P", "Pasaporte"),
        CLIENTE_FINAL("F", "Consumidor Final","9999999999999",new BigDecimal("200"));

        private String letra;
        private String nombre;
        private String identificacion;
        private BigDecimal montoMaximo;

        private TipoIdentificacionEnum(String letra, String nombre) 
        {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        private TipoIdentificacionEnum(String letra,String nombre,String identificacion,BigDecimal montoMaximo ) 
        {
            this.letra = letra;
            this.nombre = nombre;
            this.identificacion=identificacion;
            this.montoMaximo=montoMaximo;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }

        public String getIdentificacion() {
            return identificacion;
        }

        public BigDecimal getMontoMaximo() {
            return montoMaximo;
        }
        
        
        
        

        public static TipoIdentificacionEnum obtenerPorLetra(String letra) {
            for (Persona.TipoIdentificacionEnum tipoIdentificacion : TipoIdentificacionEnum.values()) {
                if (tipoIdentificacion.getLetra().equals(letra)) {
                    return tipoIdentificacion;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return nombre;
        }

    }

}
