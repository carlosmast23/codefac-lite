/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RUBROS_PLANTILLA")
public class RubroPlantilla implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "valor")
    private BigDecimal valor;
    
    @Column(name = "DIAS_CREDITO")
    private Integer diasCredito;
    
    @Column(name = "enero")
    private String enero;
    
    @Column(name = "febrero")
    private String febrero;
    
    @Column(name = "marzo")
    private String marzo;
    
    @Column(name = "abril")
    private String abril;
    
    @Column(name = "mayo")
    private String mayo;
    
    @Column(name = "junio")
    private String junio;
    
    @Column(name = "julio")
    private String julio;
    
    @Column(name = "agosto")
    private String agosto;
    
    @Column(name = "septiembre")
    private String septiembre;
    
    @Column(name = "octubre")
    private String octubre;
    
    @Column(name = "noviembre")
    private String noviembre;
    
    @Column(name = "diciembre")
    private String diciembre;
    
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    private CatalogoProducto catalogoProducto;

    @JoinColumn(name = "PERIODO_ID")
    private Periodo periodo;
 
    private List<RubroPlantillaEstudiante> detalles;

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

    public String getEnero() {
        return enero;
    }

    public void setEnero(String enero) {
        this.enero = enero;
    }

    public String getFebrero() {
        return febrero;
    }

    public void setFebrero(String febrero) {
        this.febrero = febrero;
    }

    public String getMarzo() {
        return marzo;
    }

    public void setMarzo(String marzo) {
        this.marzo = marzo;
    }

    public String getAbril() {
        return abril;
    }

    public void setAbril(String abril) {
        this.abril = abril;
    }

    public String getMayo() {
        return mayo;
    }

    public void setMayo(String mayo) {
        this.mayo = mayo;
    }

    public String getJunio() {
        return junio;
    }

    public void setJunio(String junio) {
        this.junio = junio;
    }

    public String getJulio() {
        return julio;
    }

    public void setJulio(String julio) {
        this.julio = julio;
    }

    public String getAgosto() {
        return agosto;
    }

    public void setAgosto(String agosto) {
        this.agosto = agosto;
    }

    public String getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(String septiembre) {
        this.septiembre = septiembre;
    }

    public String getOctubre() {
        return octubre;
    }

    public void setOctubre(String octubre) {
        this.octubre = octubre;
    }

    public String getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(String noviembre) {
        this.noviembre = noviembre;
    }

    public String getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(String diciembre) {
        this.diciembre = diciembre;
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
    
    //Metodo personalizados
    
    public void adddDetalle(RubroPlantillaEstudiante detalle)
    {
        if(detalle==null)
        {
            detalles=new ArrayList<RubroPlantillaEstudiante>();
        }
        
        detalle.setRubroPlantilla(this);
        detalles.add(detalle);
    }
    
}
