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
import java.util.Map;
import javax.mail.MessagingException;

/**
 *
 * @author Carlos
 */
public abstract class CorreoCodefac {
    private CorreoElectronico correoElectronico;
    private SessionCodefacInterface session;
    
    public abstract String getMensaje();
    public abstract String getTitulo();
    public abstract Map<String,String> getPathFiles();
    public abstract List<String> getDestinatorios();
    

    public CorreoCodefac(SessionCodefacInterface session) 
    {
        this.session=session;
    }
    
    public void enviarCorreo() throws RuntimeException
    {
        String correo=session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor();
        String clave=session.getParametrosCodefac().get(ParametroCodefac.CORREO_CLAVE).getValor();
        
        correoElectronico=new CorreoElectronico(correo,clave, getMensaje(), getDestinatorios(), getTitulo());
        correoElectronico.setPathFiles(getPathFiles());
        
        try
        {
            correoElectronico.sendMail();
        }catch(RuntimeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);            
        }

        
    }
    
}
