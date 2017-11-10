/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

/**
 *
 * @author Carlos
 */
public class Usuario {
    private String nick;
    private String clave;

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
    
    
}
