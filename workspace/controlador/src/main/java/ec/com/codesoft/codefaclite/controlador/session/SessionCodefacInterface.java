/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.session;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import java.util.List;
import java.util.Map;
import org.jfree.ui.about.Licences;

/**
 *
 * @author Carlos
 */
public interface SessionCodefacInterface {
    /**
     * Obtiene el usuario que se haya logueado
     * @return 
     */
    public Usuario getUsuario();
    /**
     * Obtiene la empresa la cual es logueada al inicio
     * @return 
     */
    public Empresa getEmpresa();
    /**
     * Metodo que devuelve todos los permisos habilitados para ese usuario
     * @return 
     */
    
    public void setUsuario(Usuario usuario);
    public void setEmpresa(Empresa empresa);
    
    public List<Perfil> getPerfiles();
    /**
     * Obtiene los parametros de configuracion de codefac como variables de session
     * @return 
     */
    public Map<String,ParametroCodefac> getParametrosCodefac();
    
    /**
     * Verifica si el nombre de perfil existe para poder hacer las comprobaciones
     * @param nombre
     * @return 
     */
    public boolean verificarExistePerfil(String nombre);
    
    /**
     * Obtener el tipo de licencia del usuario
     * @return 
     */
    public TipoLicenciaEnum getTipoLicenciaEnum();
    
    /**
     * Obtiene el nombre de usuario actual de la licencia del software
     * @return 
     */
    public String getUsuarioLicencia();

    public Map<ModuloCodefacEnum,Boolean> getModulosMap();
}
