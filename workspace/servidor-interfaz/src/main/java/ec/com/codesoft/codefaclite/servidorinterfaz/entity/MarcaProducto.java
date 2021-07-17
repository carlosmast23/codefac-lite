/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "MARCA_PRODUCTO")
public class MarcaProducto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Column(name = "FECHA_ULTIMA_MODIFICACION")
    private Date fechaUltimaModificacion;

    
    

}
