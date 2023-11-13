package ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.EntityAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "BODEGA")
public class CuentaBanco extends  EntityAbstract<GeneralEnumEstado>
{   
    /*@Id
    @Column(name = "BODEGA_ID")
    private Long id;*/
    
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    
    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;
    
    @Column(name = "CLASIFICACION_INTERNA")
    private String clasificacionInterna;
    
    @Column(name = "SECUENCIAL_CHEQUERA")
    private Integer secuencialChequera;
    
    @Column(name = "SALDO_TOTAL")
    private BigDecimal saldoTotal;
    
    @JoinColumn(name = "BANCO_ID")
    private Banco banco;
    
    /*@Column(name = "ESTADO")
    private String estado;*/

    public CuentaBanco() {
    }

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public TipoCuentaBancariaEnum getTipoCuentaEnum() {
        return TipoCuentaBancariaEnum.buscarPorLetra(tipoCuenta);
    }

    public void setTipoCuentaEnum(TipoCuentaBancariaEnum tipoCuentaEnum) {
        if(tipoCuentaEnum!=null)
        {
            this.tipoCuenta = tipoCuentaEnum.getLetra();
        }
    }

    public String getClasificacionInterna() {
        return clasificacionInterna;
    }

    public void setClasificacionInterna(String clasificacionInterna) {
        this.clasificacionInterna = clasificacionInterna;
    }
    
    public ClasificacionCuentaBancariaEnum getClasificacionInternaEnum() {
        return ClasificacionCuentaBancariaEnum.buscarPorLetra(clasificacionInterna);
    }

    public void setClasificacionInterna(ClasificacionCuentaBancariaEnum clasificacionInternaEnum) {
        this.clasificacionInterna = clasificacionInternaEnum.getLetra();
    }

    public Integer getSecuencialChequera() {
        return secuencialChequera;
    }

    public void setSecuencialChequera(Integer secuencialChequera) {
        this.secuencialChequera = secuencialChequera;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    
    
    
    /**
     * =======================================================================
     *                      DATOS ADICIONALES
     * =======================================================================
     */
    public enum TipoCuentaBancariaEnum
    {
        AHORROS("a","Cuenta de Ahorros"),
        CORRIENTE("c","Cuenta Corrientes");
        
        private String letra;
        private String nombre;

        private TipoCuentaBancariaEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static TipoCuentaBancariaEnum buscarPorLetra(String letra)
        {
            for (TipoCuentaBancariaEnum value : TipoCuentaBancariaEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
    }
    
    public enum ClasificacionCuentaBancariaEnum
    {
        INTERNO_EMPRESA("i","Interno Empresa"),
        CLIENTE_PROVEEDOR("c","Cliente o Proveedor");
        
        private String letra;
        private String nombre;

        private ClasificacionCuentaBancariaEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static ClasificacionCuentaBancariaEnum buscarPorLetra(String letra)
        {
            for (ClasificacionCuentaBancariaEnum value : ClasificacionCuentaBancariaEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
        
    }
    
    
}
