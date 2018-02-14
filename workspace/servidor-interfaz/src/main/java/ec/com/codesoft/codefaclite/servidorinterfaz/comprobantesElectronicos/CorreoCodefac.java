/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import ec.com.codesoft.ejemplo.utilidades.email.SmtpNoExisteException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 *
 * @author Carlos
 */
public abstract class CorreoCodefac {
    private CorreoElectronico correoElectronico;
    
    public abstract String getMensaje();
    public abstract String getTitulo();
    public abstract Map<String,String> getPathFiles();
    public abstract List<String> getDestinatorios();
    

    public CorreoCodefac() 
    {
        
    }
    
    public void enviarCorreo() throws RuntimeException
    {
        try
        {
            //ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO);
            String correo=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO).getValor();
            String clave=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_CLAVE).getValor();
            
            correoElectronico=new CorreoElectronico(correo,clave, getMensaje(), getDestinatorios(), getTitulo());
            correoElectronico.setPathFiles(getPathFiles());
            
            try
            {
                correoElectronico.sendMail();
            }catch(RuntimeException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (MessagingException ex) {
                Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SmtpNoExisteException ex) {
                Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }catch(RemoteException ex)
        {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
}
