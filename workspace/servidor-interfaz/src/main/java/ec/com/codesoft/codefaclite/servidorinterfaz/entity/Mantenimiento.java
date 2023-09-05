/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.EstadoEntidadIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
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
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "MANTENIMIENTO")
public class Mantenimiento extends EntityAbstract<Mantenimiento.MantenimientoEnum>{

    public Mantenimiento() {
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "COMENTARIO")
    private String comentario;
    
    @JoinColumn(name = "SUPERVISOR_ID")
    private Empleado supervisor;
    
    @Column(name = "FECHA_INGRESO")
    private Timestamp fechaIngreso; 
    
    @Column(name = "FECHA_SALIDA")
    private Timestamp fechaSalida;
    
    @Column(name = "UBICACION")
    private String ubicacion;
    
    @Column(name = "TALLER_NOMBRE")
    private String tallerNombre;
    
    @Column(name = "PRIORIDAD")
    private Integer prioridad;
    
    
    @JoinColumn(name = "OBJETO_MANTENIMIENTO_ID")
    private ObjetoMantenimiento vehiculo;
    
    @JoinColumn(name = "TALLER_ID")
    private Taller taller;
    
    @OneToMany(mappedBy = "mantenimiento", fetch = FetchType.EAGER)
    private List<MantenimientoTareaDetalle> tareaList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Empleado getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Empleado supervisor) {
        this.supervisor = supervisor;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Timestamp getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public ObjetoMantenimiento getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(ObjetoMantenimiento vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTallerNombre() {
        return tallerNombre;
    }

    public void setTallerNombre(String tallerNombre) {
        this.tallerNombre = tallerNombre;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Taller getTaller() {
        return taller;
    }

    public void setTaller(Taller taller) {
        this.taller = taller;
    }

    public List<MantenimientoTareaDetalle> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<MantenimientoTareaDetalle> tareaList) {
        this.tareaList = tareaList;
    }
    
    
    public String obtenerSubTareasTxt()
    {
        if(tareaList!=null)
        {
           return  UtilidadesLista.castListToString(tareaList,",",new UtilidadesLista.CastListInterface<MantenimientoTareaDetalle>() {
                @Override
                public String getString(MantenimientoTareaDetalle dato) {
                    TallerTarea tallerTarea=dato.getTallerTarea();
                    if(tallerTarea!=null)
                    {
                        TareaMantenimiento tareaMantenimiento=tallerTarea.getTareaMantenimiento();                    
                        return tareaMantenimiento.getNombre();
                    }
                    else
                    {
                        Logger.getLogger(Mantenimiento.class.getName()).log(Level.SEVERE,"Revisar MantenimientoTareaDetalle id= "+dato.getId()+", Taller"+dato.getMantenimiento().getTallerNombre());
                    }
                    return "";
                }
            });
        }
        return "";
    }
    
    public UbicacionEnum getUbicacionEnum() {
        return UbicacionEnum.getEnum(ubicacion);
    }

    public void setUbicacionEnum(UbicacionEnum ubicacionEnum) {
        if(ubicacionEnum!=null)
        {
            this.ubicacion = ubicacionEnum.letra;
        }        
    }
    
    public void agregarTarea(MantenimientoTareaDetalle tallerTarea)
    {
        if(tareaList==null)
        {
            tareaList=new ArrayList<MantenimientoTareaDetalle>();
        }
        
        tareaList.add(tallerTarea);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Mantenimiento other = (Mantenimiento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public void setEstadoEnum(MantenimientoEnum estadoEnum) {
        if(estadoEnum!=null)
        {
            estado=estadoEnum.getLetra();
        }
        else
        {
            estado=null;
        }
    }

    @Override
    public MantenimientoEnum getEstadoEnum() {
        return MantenimientoEnum.getEnum(estado);
    }
    
    public MantenimientoEnum getEstadoEnumTemp() {
        return MantenimientoEnum.getEnum(estado);
    }

    @Override
    public String toString() {
        return vehiculo.getModelo()+" : "+vehiculo.getColor()+" : "+vehiculo.getVin();
    }
    
    public enum UbicacionEnum
    {
        TALLER("T","Taller"),
        DIRECTO("D","Directo");
        
        private String letra;
        private String nombre;

        private UbicacionEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static UbicacionEnum getEnum(String letra) {
            for (UbicacionEnum enumerador : UbicacionEnum.values()) {
                if (enumerador.letra.equals(letra)) 
                {
                    return enumerador;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return nombre; 
        }
        
        
    }
    
    public enum TallerEnum
    {
        COLISIONES("c","Taller Colisiones"),
        EXPRESS("e","Taller Express"),
        EXTERNO("x","Taller Externos"),
        MECANICO("m","Taller Mécanico"),
        RETORNO("r","Taller Retorno");
        
        private String letra;
        private String nombre;

        private TallerEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
    }
    
    public enum MantenimientoEnum implements EstadoEntidadIf
    {
        INGRESADO("I","Ingresado"),
        FACTURADO("F","Facturado"),
        TERMINADO("T","Terminado"),
        ELIMINADO("E","Eliminado");

        private MantenimientoEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        private String letra;
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static MantenimientoEnum getEnum(String letra) {
        for (MantenimientoEnum enumerador : MantenimientoEnum.values()) {
            if (enumerador.letra.equals(letra)) {
                return enumerador;
            }
        }
        return null;
        
        
    }

        @Override
        public String toString() {
            return nombre;
        }
        
        
    }
    
        
}
