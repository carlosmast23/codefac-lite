/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.contabilidad;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.EntityAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoCuentaContableEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "CUENTA_CONTABLE")
public class CuentaContable extends EntityAbstract<GeneralEnumEstado>{
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "IMPUTABLE")
    private String imputable;
    
    @Column(name = "TIPO_CUENTA_CODIGO")
    private String tipoCuentaCodigo;
    
    @JoinColumn(name = "PLAN_CUENTA_ID")
    private PlanCuenta planCuenta;
    
    @JoinColumn(name = "CUENTA_PADRE_ID")
    private CuentaContable cuentaPadre;

    public CuentaContable(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    

    public CuentaContable() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImputable() {
        return imputable;
    }

    public void setImputable(String imputable) {
        this.imputable = imputable;
    }

    public String getTipoCuentaCodigo() {
        return tipoCuentaCodigo;
    }

    public void setTipoCuentaCodigo(String tipoCuentaCodigo) {
        this.tipoCuentaCodigo = tipoCuentaCodigo;
    }

    public PlanCuenta getPlanCuenta() {
        return planCuenta;
    }

    public void setPlanCuenta(PlanCuenta planCuenta) {
        this.planCuenta = planCuenta;
    }

    public CuentaContable getCuentaPadre() {
        return cuentaPadre;
    }

    public void setCuentaPadre(CuentaContable cuentaPadre) {
        this.cuentaPadre = cuentaPadre;
    }

    @Override
    public String toString() {
        return codigo+"  "+nombre;
    }
    
    
    
    //////////////////////////////////////////////////////
    ///             GER AND SET ADICIONAL
    /////////////////////////////////////////////////////
    
    public EnumSiNo getImputableEnum() {
        return EnumSiNo.getEnumByLetra(imputable);
    }

    public void setImputableEnum(EnumSiNo imputableEnum) {
        this.imputable = imputableEnum.getLetra();
    }
    
    public TipoCuentaContableEnum getTipoCuentaEnum() {
        return TipoCuentaContableEnum.getByCodigo(tipoCuentaCodigo);
    }

    public void setTipoCuentaCodigoEnum(TipoCuentaContableEnum tipoCuentaCodigoEnum) {
        this.tipoCuentaCodigo = tipoCuentaCodigoEnum.getCodigo();
    }
    
    
}
