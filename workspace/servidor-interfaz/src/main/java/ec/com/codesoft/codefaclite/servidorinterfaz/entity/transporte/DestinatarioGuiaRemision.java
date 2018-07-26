/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Carlos
 */
public class DestinatarioGuiaRemision implements Serializable{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Persona destinatorio;
    private String razonSocial;
    private String direccionDestino;
    private String motivoTranslado;
    private String ruta;
    private String codDucumentoSustento;
    private String preimpreso;
    private String autorizacionNumero;
    private Date fechaEmision;
    
    private DetalleProductoGuiaRemision detallesProductos;
    
    
}
