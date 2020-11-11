/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.email.PropiedadCorreo;
import ec.com.codesoft.codefaclite.utilidades.email.SmtpNoExisteException;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
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
    
    public void enviarCorreo(Empresa empresa) throws ExcepcionCorreoCodefac
    {
        try
        {
            //ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO);
            String correo=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO,empresa).getValor();
            

            String clave=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_CLAVE,empresa).getValor();
            //Obtener clave desencriptada
            clave=UtilidadesEncriptar.desencriptar(clave,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            
            //Construir los datos de las propiedades si existen
            String smtpHost=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.SMTP_HOST,empresa).getValor();
            String smtpPort=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.SMTP_PORT,empresa).getValor();
            
            PropiedadCorreo propiedadCorreo=null;
            if(!smtpHost.isEmpty() && !smtpPort.isEmpty())
            {
                propiedadCorreo=new PropiedadCorreo(smtpHost,new Integer(smtpPort));
            } 
            
            //Agregar el nombre de la empresa o la razon social
            String alias=empresa.getNombreLegal();
            if(alias==null || alias.trim().isEmpty())
            {
                alias=empresa.getRazonSocial();
            }
            
            correoElectronico=new CorreoElectronico(correo,alias,clave, getMensaje(), getDestinatorios(), getTitulo(),propiedadCorreo);
            correoElectronico.setPathFiles(getPathFiles());
            
            try
            {
                correoElectronico.sendMail();
            }catch(RuntimeException e)
            {
                e.printStackTrace();
                throw new ExcepcionCorreoCodefac(e.getMessage());
            //} catch (MessagingException ex) {
            //    Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            //    throw new ExcepcionCorreoCodefac(ex.getMessage());
            } catch (SmtpNoExisteException ex) {
                Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCorreoCodefac(ex.getMessage());
            }catch (AuthenticationFailedException ex) {
                Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCorreoCodefac("Error de autentificación de las credenciales del correo configurado en el sistema");
            }
            
            
        }catch(RemoteException ex)
        {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCorreoCodefac(ex.getMessage());
        }

        
    }
    
    public class ExcepcionCorreoCodefac extends Exception {

        public ExcepcionCorreoCodefac(String message) {
            super(message);
        }
    
    }
    
}
