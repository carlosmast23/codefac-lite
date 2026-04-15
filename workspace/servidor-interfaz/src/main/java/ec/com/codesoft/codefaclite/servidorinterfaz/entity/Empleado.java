/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "EMPLEADO")
public class Empleado implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "CEDULA")
    private String identificacion;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "GENERO")
    private String sexo;
    @Column(name = "CARGO")
    private String cargo;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "PLACA")
    private String placa;
    @Column(name = "ALIAS")
    private String alias;
    
//    @JoinColumn(name = "PERSONA_ID")
//    @ManyToOne    
//    private Persona cliente;
    
    @JoinColumn(name = "DEPARTAMENTO_ID")
    @ManyToOne    
    private Departamento departamento;
    
    //@Column(name = "DEPARTAMENTO_ID")
   // private Long departamentoId;
    
    //@JoinColumn(name = "NACIONALIDAD_ID")
    //@ManyToOne
    //private Nacionalidad nacionalidad;
    
    @Column(name = "NACIONALIDAD_ID")
    private Long nacionalidadId;
    
    @Column(name = "PORCENTAJE_VENDEDOR")
    private Integer porcentajeVendedor;
    
    @Column(name = "PORCENTAJE_REFERIDOS")
    private Integer porcentajeReferidos;
    
    @Column(name = "PORCENTAJE_COLABORADOR")
    private Integer porcentajeColaborador;
    
    //TODO: Falta aumentar la empresa
            
    public Empleado() {
        
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

//    public Persona getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Persona cliente) {
//        this.cliente = cliente;
//    }

    /*public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }*/

    
    

    public Departamento getDepartamento() {
        return departamento;
    }

    /*@Deprecated
    public Departamento getDepartamento() {
    try {
    return ServiceFactory.getFactory().getDepartamentoServiceIf().buscarPorId(departamentoId);
    } catch (RemoteException ex) {
    Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
    @Deprecated
    public void setDepartamento(Departamento departamento) {
    if(departamento!=null)
    {
    this.departamentoId = departamento.getId();
    }
    }*/
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Long getNacionalidadId() {
        return nacionalidadId;
    }

    public void setNacionalidadId(Long nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }
    
    

    public Nacionalidad getNacionalidadTmp() {
        try {
            return ServiceFactory.getFactory().getNacionalidadServiceIf().buscarPorId(nacionalidadId);
            //return nacionalidad;
        } catch (RemoteException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setNacionalidadTmp(Nacionalidad nacionalidad) {
        if(nacionalidad!=null)
        {
            this.nacionalidadId = nacionalidad.getIdNacionalidad();
        }
    }
    
    public String getNombresCompletos()
    {
        return nombres+" "+apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getPorcentajeVendedor() {
        if(porcentajeVendedor==null)
        {
            porcentajeVendedor=0;
        }
        
        return porcentajeVendedor;
    }

    public void setPorcentajeVendedor(Integer porcentajeVendedor) {
        this.porcentajeVendedor = porcentajeVendedor;
    }

    public Integer getPorcentajeReferidos() {
        if(porcentajeReferidos==null)
            porcentajeReferidos=0;            
            
        return porcentajeReferidos;
    }

    public void setPorcentajeReferidos(Integer porcentajeReferidos) {
        this.porcentajeReferidos = porcentajeReferidos;
    }

    public Integer getPorcentajeColaborador() {
        return porcentajeColaborador;
    }

    public void setPorcentajeColaborador(Integer porcentajeColaborador) {
        this.porcentajeColaborador = porcentajeColaborador;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    
    
    
    public Boolean verificarSupervisor()
    {
        //Departamento departamento=getDepartamento();
        if(departamento!=null)
        {
            if(Departamento.TipoEnum.Supervisor.equals(departamento.getTipoEnum()))
            {
                return true;
            }
        }
        return false;
    }
     

    @Override
    public String toString() {
        String nombreCompleto = this.apellidos + " " +this.nombres;
        return " Departamento - " + nombreCompleto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Empleado other = (Empleado) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
}
