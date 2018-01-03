/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.session;

import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Perfil;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import java.util.List;
import java.util.Map;

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
    public List<Perfil> getPerfiles();
    /**
     * Obtiene los parametros de configuracion de codefac como variables de session
     * @return 
     */
    public Map<String,ParametroCodefac> getParametrosCodefac();
    
    public boolean verificarExistePerfil(String nombre);

}
