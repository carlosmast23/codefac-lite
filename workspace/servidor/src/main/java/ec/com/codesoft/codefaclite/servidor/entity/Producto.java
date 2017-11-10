/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
//    @NamedQuery(name = "Producto.findByIdentificacion", query = "SELECT p FROM Producto p WHERE p.identificacion = :identificacion"),
//    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombreSocial = :nombre")})
/**
 *
 * @author PC
 */
public class Producto implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID_CLIENTE")
    private Integer  idCliente;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    @Column(name = "NOMBRE_SOCIAL")
    private String nombreSocial;
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
    
}
