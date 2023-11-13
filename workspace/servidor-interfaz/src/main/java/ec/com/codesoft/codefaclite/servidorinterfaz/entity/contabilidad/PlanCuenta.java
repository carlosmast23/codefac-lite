/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.contabilidad;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.EntityAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PLAN_CUENTA")
public class PlanCuenta extends EntityAbstract<GeneralEnumEstado>{
    
    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<CuentaContable> cuentaContableList;

    public PlanCuenta() {
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<CuentaContable> getCuentaContableList() {
        return cuentaContableList;
    }

    public void setCuentaContableList(List<CuentaContable> cuentaContableList) {
        this.cuentaContableList = cuentaContableList;
    }
    
    
    /**
     * ==============================================================
     *              METODOS PERSONALIZADOS
     * ==============================================================
     */
    
    public void agregarCuenta(CuentaContable cuenta)
    {
        if(cuentaContableList==null)
        {
            cuentaContableList=new ArrayList<CuentaContable>();
        }
        
        cuenta.setPlanCuenta(this);
        cuentaContableList.add(cuenta);
    }
    
    /*
    Plan de cuentas para seleccionar un planes de cuenta por defecto
    */
    public enum PlanCuentaPlantillaEnum
    {
        PLAN_SENCILLO(new PlanCuenta() {
            {
                agregarCuenta(new CuentaContable("1", "dato1"));
                agregarCuenta(new CuentaContable("2", "dato2"));
            }
        });
        
        private PlanCuenta planCuenta;

        public PlanCuenta getPlanCuenta() {
            return planCuenta;
        }
        
        

        private PlanCuentaPlantillaEnum(PlanCuenta planCuenta) {
            this.planCuenta = planCuenta;
        }
        
        
        
    }
    
}
