/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PRESUPUESTO_DETALLE_ACTIVIDAD")
public class PresupuestoDetalleActividad extends EntityAbstract<GeneralEnumEstado> 
{    
    private String terminado;
    
    @JoinColumn(name = "PRODUCTO_ACTIVIDAD_ID")
    private ProductoActividad productoActividad;
    
    @JoinColumn(name = "PRESUPUESTO_DETALLE_ID")
    private PresupuestoDetalle presupuestoDetalle;
    
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    
    
    /////////// GET AND SET ////////////////
    public ProductoActividad getProductoActividad() 
    {
        return productoActividad;
    }

    public void setProductoActividad(ProductoActividad productoActividad) {
        this.productoActividad = productoActividad;
    }

    public String getTerminado() {
        return terminado;
    }

    public void setTerminado(String terminado) {
        this.terminado = terminado;
    }
    
    public EnumSiNo getTerminadoEnum() 
    {
        return EnumSiNo.getEnumByLetra(terminado);
    }

    public void setTerminado(EnumSiNo terminadoEnum) 
    {
        this.terminado = terminadoEnum.getLetra();
    }

    public PresupuestoDetalle getPresupuestoDetalle() {
        return presupuestoDetalle;
    }

    public void setPresupuestoDetalle(PresupuestoDetalle presupuestoDetalle) 
    {
        this.presupuestoDetalle = presupuestoDetalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    
}
