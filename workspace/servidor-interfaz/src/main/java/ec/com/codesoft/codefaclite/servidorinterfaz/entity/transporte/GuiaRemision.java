/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "GUIA_REMISION")
public class GuiaRemision extends ComprobanteEntity implements  Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String direccionPartida;
    private Transportista transportista;
    private String rise;
    private String obligadoLlevarContabilidad; //Aumentar este campo al ingresar los datos
    private String contribuyenteEspecial;
    private Date fechaIniciaTransporte;
    private Date fechaFinTransporte;
    private String placa;
    
    List<DestinatarioGuiaRemision> destinatarios;
    
    
}
