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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    
    @Column(name = "DIRECCION_PARTIDA")
    private String direccionPartida;    
    @Column(name = "RISE")
    private String rise;
    @Column(name = "OBLIGADO_LLEVAS_CONTABILIDAD")
    private String obligadoLlevarContabilidad; //Aumentar este campo al ingresar los datos
    @Column(name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column(name = "FECHA_INICIO_TRANSPORTE")
    private Date fechaIniciaTransporte;
    @Column(name = "FECHA_FIN_TRANSPORTE")
    private Date fechaFinTransporte;
    @Column(name = "PLACA")
    private String placa;
    
    @JoinColumn(name = "TRANSPORTISTA_ID")
    @ManyToOne
    private Transportista transportista;   
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guiaRemision",fetch = FetchType.EAGER)
    private List<DestinatarioGuiaRemision> destinatarios;

    public GuiaRemision() {
    }
      
    
    public List<DestinatarioGuiaRemision> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<DestinatarioGuiaRemision> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccionPartida() {
        return direccionPartida;
    }

    public void setDireccionPartida(String direccionPartida) {
        this.direccionPartida = direccionPartida;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public Date getFechaIniciaTransporte() {
        return fechaIniciaTransporte;
    }

    public void setFechaIniciaTransporte(Date fechaIniciaTransporte) {
        this.fechaIniciaTransporte = fechaIniciaTransporte;
    }

    public Date getFechaFinTransporte() {
        return fechaFinTransporte;
    }

    public void setFechaFinTransporte(Date fechaFinTransporte) {
        this.fechaFinTransporte = fechaFinTransporte;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    /**
     * Agregar Destinatarios a la guia de remision
     * @param detalle 
     */
    public void addDestinario(DestinatarioGuiaRemision detalle) {
        if (this.destinatarios == null) {
            this.destinatarios = new ArrayList<DestinatarioGuiaRemision>();
        }
        detalle.setGuiaRemision(this);
        this.destinatarios.add(detalle);
    }

    public Integer obtenerTotalProductos()
    {
        Integer cantidad=0;
        if(destinatarios!=null)
        {
            for (DestinatarioGuiaRemision destinatario : destinatarios) 
            {
                for (DetalleProductoGuiaRemision detalle : destinatario.getDetallesProductos()) {
                    cantidad+=detalle.getCantidad();
                }
            }
        }
        return cantidad;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final GuiaRemision other = (GuiaRemision) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}