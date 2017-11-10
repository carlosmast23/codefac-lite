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

/**
 *
 * @author PC
 */
@Entity
@Table(name = "EMISOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByIdentificacion", query = "SELECT p FROM Persona p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombreSocial = :nombre")})
public class Emisor implements Serializable
{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID_CLIENTE")
    private Integer idCliente;
    @Column (name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column (name = "RUC")
    private String ruc;
    @Column (name = "NOM_COMERCIAL")
    private String nomComercial;
    @Column (name = "DIR_ESTABLECIMIENTO")
    private String dirEstablecimiento;
    @Column (name = "COD_ESTABLECIMIENTO")
    private String codEstablecimiento;
    @Column (name = "RESOLUSION")
    private String resolucion;
    @Column (name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column (name = "COD_PUNTO_EMISION")
    private String codPuntoEmision;
    @Column (name = "LLEVA_CONTABILIDAD")
    private String llevaContabilidad;
    @Column (name = "LOGO_IMAGEN")
    private String logoImagen;
    @Column (name = "TIPO_EMISION")
    private String tipoEmision;
    @Column (name = "TIEMPO_ESPERA")
    private String tiempoEspera;
    @Column (name = "CLAVE_INTERNA")
    private String claveInterna;
    @Column (name = "TIPO_AMBIENTE")
    private String tipoAmbiente;
    @Column (name = "DIRECCION_MATRIZ")
    private String direccionMatriz;
    @Column (name = "TOKEN")
    private String token;
}
