/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "ORDEN_TRABAJO")
public class OrdenTrabajo implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "OBJECTO_MANTENIMIENTO_ID")
    private ObjetoMantenimiento objetoMantenimiento;
    
    /**
     * @deprecated 
     * Todo: Este estado me parece que esta mal ingresado
     */
    //@Column(name = "ESTADO_DETALLES")
    //private String estadoDetalles;
    
    @Column(name = "FECHA_INGRESO")
    private Date fechaIngreso;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;
    
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    @ManyToOne
    private CatalogoProducto catalogoProducto; 
    
    @JoinColumn(name = "USUARIO_ID")
    protected Usuario usuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordenTrabajo",fetch = FetchType.EAGER)
    private List<OrdenTrabajoDetalle> detalles;

    public List<OrdenTrabajoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenTrabajoDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public EstadoEnum getEstadoEnum() {
        return EstadoEnum.getEnum(estado);
    }

    public void setEstadoEnum(EstadoEnum estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public ObjetoMantenimiento getObjetoMantenimiento() {
        return objetoMantenimiento;
    }

    public void setObjetoMantenimiento(ObjetoMantenimiento objetoMantenimiento) {
        this.objetoMantenimiento = objetoMantenimiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }
    
    
    /*public String getEstadoDetalles() {
        return estadoDetalles;
    }

    public void setEstadoDetalles(String estadoDetalles) {
        this.estadoDetalles = estadoDetalles;
    }*/
       
     public void addDetalle(OrdenTrabajoDetalle detalle)
    {
        if(this.detalles==null)
        {
            this.detalles= new ArrayList<>();
        }
        detalle.setOrdenTrabajo(this);
        this.detalles.add(detalle);
        
    }
     
    public String obtenerCorreosStr()
    {        
        List<OrdenTrabajoDetalle> resultadoDetalle=getDetalles();
        return  UtilidadesLista.castListToString(resultadoDetalle,",",new UtilidadesLista.CastListInterface<OrdenTrabajoDetalle>() {
                @Override
                public String getString(OrdenTrabajoDetalle dato) {
                    if(dato.getEmpleado()!=null)
                    {
                        return dato.getEmpleado().getCorreoElectronico();
                    }
                    return null;
                }
            });
    }

     
    /**
     * Metodos Personalizados
     */
     
     public String getDetalleString()
     {
        //Si no hay datos retorna vacio
        if(this.detalles==null)return"";
        
        String detallesTotales="";
        for (OrdenTrabajoDetalle detalle : detalles) {
             String detalleStr=UtilidadesTextos.acortarTexto(detalle.getTitulo(),50);
             detallesTotales=detallesTotales+detalleStr+" |";
        }
        
        //Recortar el caracter final |
        if(detallesTotales.length()>0)
        {
            detallesTotales=detallesTotales.substring(0,detallesTotales.length()-1);
        }
        
        return detallesTotales;
     }
     
     public enum EstadoEnum{
         
         /**
         * Estado inicial en el cual se guarda cualquier Orden de trabajo y lo que siginifica es que esta todavia en proceso y que aun no se ha ligado
         * algo detalle con un presupuesto.
         */    
        GENERADO("G","Generado"),
        
         /**@deprecated 
          * TODO: Este estado esta de revisar porque parece que esta dificil de controlar y no genera mayo beneficio
         * Estado utilizado para indicar que la orden de trabajo mas especificamente uno de sus detalles esta ligado a un presupuesto
         */
        LIGADO("L","Ligado"),
        
        /**
         * Estado utilizado para indicar que todos los detalles de la orden de trabajo han sido finculadas a un presupuesto
         */
        FINALIZADO("F","Finalizado"),
        
         /**
         * Estado utilizado para indicar que todos los detalles de la orden de trabajo han sido finculadas a un presupuesto
         */
        TERMINADO("T","Terminado"),
        /**
         * Estado para indicar que esa orden de trabajo ya esta facturada
         */
        FACTURADO("C","Facturado"),
        /**
         * Estado para identificar las ordenes de trabajo eliminadas
         */
        ELIMINADO("E","Eliminado")
        ;
        
        private String estado;
        private String nombre;
        
        private EstadoEnum(String estado, String nombre)
        {
            this.estado = estado;
            this.nombre = nombre;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public static EstadoEnum getEnum(String estado) 
        {
            for (EstadoEnum enumerador : EstadoEnum.values()) {
                if (enumerador.estado.equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }
        
     }
}
