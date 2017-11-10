/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable{
    @Id
    @Column (name = "NICK")
    private String nick;
    
    @Column (name = "CLAVE")
    private String clave;
    
    @Column (name = "TIPO")
    private String tipo;

    public Usuario(String nick, String clave) {
        this.nick = nick;
        this.clave = clave;
    }

    public Usuario() {
    }
    
    

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
