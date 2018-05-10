/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RETENCION")
@XmlRootElement
public class Retencion extends Comprobante  implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne    
    private Persona proveedor;

    @JoinColumn(name = "COMPRA_ID")
    @ManyToOne        
    private Compra compra;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retencion",fetch = FetchType.EAGER)
    private List<RetencionDetalle> detalles;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retencion",fetch = FetchType.EAGER)
    private List<RetencionAdicional> datosAdicionales;

    public Retencion() {

    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }


    public List<RetencionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RetencionDetalle> detalles) {
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Long getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public List<RetencionAdicional> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<RetencionAdicional> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }
    
    
    
       public void addDatoAdicional(String campo, String valor)
    {
        RetencionAdicional dato=new RetencionAdicional();
        dato.setCampo(campo);
        dato.setNumero(0);
        dato.setTipo(FacturaAdicional.Tipo.TIPO_OTRO.getLetra());
        dato.setValor(valor);
        
        addDatoAdicional(dato);
    }
    
    public void addDatoAdicional(RetencionAdicional datoAdicional)
    {
        if(this.datosAdicionales==null)
        {
            this.datosAdicionales=new ArrayList<RetencionAdicional>();
        }
        datoAdicional.setRetencion(this);
        this.datosAdicionales.add(datoAdicional);
    }
    
    public void addDatosAdicionalCorreo(String correo)
    {
        RetencionAdicional retencionAdicional=new RetencionAdicional();
        retencionAdicional.setCampo(FacturaAdicional.NOMBRE_CORREO);
        retencionAdicional.setRetencion(this);
        retencionAdicional.setTipo(FacturaAdicional.Tipo.TIPO_CORREO.getLetra());
        retencionAdicional.setValor(correo);
        
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<RetencionAdicional>();
        }
        
        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo=0;
        for (RetencionAdicional datoAdicional : datosAdicionales) {            
            if(datoAdicional.getTipo().equals(FacturaAdicional.Tipo.TIPO_CORREO.getLetra()))
            {
                if(datoAdicional.getNumero()>numeroMaximo)
                {
                    numeroMaximo=datoAdicional.getNumero();
                }
            }
        }
        
        retencionAdicional.setNumero(numeroMaximo+1);
        //Modificar el nombre si el correo es mas de 2
        if(retencionAdicional.getNumero()>1)
        {
            retencionAdicional.setCampo(FacturaAdicional.NOMBRE_CORREO+" "+retencionAdicional.getNumero());
        }

        this.datosAdicionales.add(retencionAdicional);
    
    }
    
    public RetencionAdicional obtenerDatoAdicionalPorCampo(String nombre)
    {
        if(this.datosAdicionales!=null)
        {
            for (RetencionAdicional retencionAdicional : datosAdicionales) {
                if(retencionAdicional.getCampo().equals(nombre))
                {
                    return retencionAdicional;
                }
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Retencion other = (Retencion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    /**
     *Metodos personalizados
     */
         /**
     * Formas de pago adicional
     */
    public void addDetalle(RetencionDetalle DetalleRetencion)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<RetencionDetalle>();
        }
        DetalleRetencion.setRetencion(this);
        this.detalles.add(DetalleRetencion);
        
    }
    
    public String getPeriodoFiscal()
    {
        return UtilidadesFecha.obtenerMesStr(fechaEmision)+"/"+UtilidadesFecha.obtenerAnioStr(fechaEmision);
    }
    
    public String getPreimpreso() 
    {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision, 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento, 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(secuencial + "", 8, "0");
    }

    
}
