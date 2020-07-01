/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import com.healthmarketscience.rmiio.RemoteInputStream;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import java.io.InputStream;
 
 ;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface RecursosServiceIf    {
    public RemoteInputStream getResourceInputStream(RecursoCodefac recurso,String file) ;   
    public RemoteInputStream getResourceInputStreamByFile(Empresa empresa,DirectorioCodefac directorio,String nameFile);    
    public RemoteInputStream getDataBaseResources();    
    public void uploadFileServer(DirectorioCodefac directorio,RemoteInputStream recurso,String nombre,Empresa empresa);    
    public void uploadFileServer(String pathServidor,DirectorioCodefac directorio,RemoteInputStream recurso,String nombre);    
}
