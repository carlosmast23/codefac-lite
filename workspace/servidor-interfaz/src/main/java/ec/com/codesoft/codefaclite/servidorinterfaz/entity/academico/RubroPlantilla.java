/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RUBRO_PLANTILLA")
public class RubroPlantilla implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "VALOR")
    private BigDecimal valor;
    
    @Column(name = "DIAS_CREDITO")
    private Integer diasCredito;
 
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    private CatalogoProducto catalogoProducto;

    @JoinColumn(name = "PERIODO_ID")
    private Periodo periodo;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubroPlantilla",fetch = FetchType.EAGER)
    private List<RubroPlantillaEstudiante> detalles;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubroPlantilla",fetch = FetchType.EAGER)
    private List<RubroPlantillaMes> mesesGenerados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

 

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<RubroPlantillaEstudiante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RubroPlantillaEstudiante> detalles) {
        this.detalles = detalles;
    }

    

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public List<RubroPlantillaMes> getMesesGenerados() {
        return mesesGenerados;
    }

    public void setMesesGenerados(List<RubroPlantillaMes> mesesGenerados) {
        this.mesesGenerados = mesesGenerados;
    }
    
    
    
    //Metodo personalizados
    
    public void adddDetalle(RubroPlantillaEstudiante detalle)
    {
        if(detalles==null)
        {
            detalles=new ArrayList<RubroPlantillaEstudiante>();
        }
        
        detalle.setRubroPlantilla(this);
        detalles.add(detalle);
    }
    
    public void addMesGenerado(RubroPlantillaMes mes)
    {
    
        if(mesesGenerados==null)
        {
            mesesGenerados=new ArrayList<RubroPlantillaMes>();            
        }
        mes.setRubroPlantilla(this);
        mesesGenerados.add(mes);
    }
    
    public List<RubroPlantillaMes> obtenerTodosMesesGenerar()
    {
        List<RubroPlantillaMes> rubrosMes=new ArrayList<RubroPlantillaMes>();
        
        //Generar todos los meses segun el periodo
        Date fechaInicio=periodo.getFechaInicio();
        Date fechaFinal=periodo.getFechaFin();
        
        Integer anioInicial=UtilidadesFecha.obtenerAnio(fechaInicio);
        Integer anioFinal=UtilidadesFecha.obtenerAnio(fechaFinal);
        
        Integer mesInicial=UtilidadesFecha.obtenerMes(fechaInicio);
        Integer mesFinal=UtilidadesFecha.obtenerMes(fechaFinal);
        
        for (int anio = anioInicial; anio <= anioFinal; anio++) {
            for (int mes =mesInicial;mes<=12;mes++) 
            {
                RubroPlantillaMes rpm=new RubroPlantillaMes();
                rpm.setAnio(anio);
                rpm.setNumeroMes(mes);
                rpm.setRubroPlantilla(this);
                
                rubrosMes.add(rpm);
                
                //Comparar si ya termino solo cuando este en el anio final
                if(anio==anioFinal)
                {
                    if(mesFinal==mes)
                    {
                        break;
                    }
                }                

            }
        }
        
        return rubrosMes;
    }
    
    
    public List<RubroPlantillaMes> obtenerMesesSinGenerar()
    {
        List<RubroPlantillaMes> rubrosMes=obtenerTodosMesesGenerar();       
   
        if(rubrosMes!=null)
        {
            for (int i=0;i<rubrosMes.size();) {
                //Si ya existe en la lista los borro
                RubroPlantillaMes mes=rubrosMes.get(i);
                
                if(buscarPorAnioYMes(mes.getAnio(),mes.getNumeroMes()))
                {
                    rubrosMes.remove(i);
                }
                else
                {
                    i++;
                }
            }
        }
        
        
        
        return rubrosMes;
    }
    
    private Boolean buscarPorAnioYMes(Integer anio, Integer mes)
    {
        if(mesesGenerados!=null)
        {
            for (RubroPlantillaMes mesPlantilla : mesesGenerados) {
                if(mesPlantilla.getAnio().equals(anio) && mesPlantilla.getNumeroMes().equals(mes))
                {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final RubroPlantilla other = (RubroPlantilla) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
