/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.mail;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import java.util.List;

/**
 *
 * @author Carlos
 */
public abstract class CorreoCodefac {
    private CorreoElectronico correoElectronico;
    private SessionCodefacInterface session;
    
    public abstract String getMensaje();
    public abstract String getTitulo();
    public abstract List<String> getDestinatorios();
    

    public CorreoCodefac(SessionCodefacInterface session) 
    {
        this.session=session;
    }
    
    public void enviarCorreo()
    {
        correoElectronico=new CorreoElectronico(ParametroCodefac.CORREO_USUARIO,ParametroCodefac.CORREO_CLAVE, getMensaje(), getDestinatorios().get(0), getTitulo());
        correoElectronico.sendMail();
    }
    
}
