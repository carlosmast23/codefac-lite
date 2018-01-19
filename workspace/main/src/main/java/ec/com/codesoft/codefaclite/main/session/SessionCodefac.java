/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.session;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Perfil;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoLicenciaEnum;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class SessionCodefac implements SessionCodefacInterface{
    
    private Usuario usuario;
    private Empresa empresa;
    private Map<String,ParametroCodefac>  parametrosCodefac;
    private List<Perfil> perfiles;
    private TipoLicenciaEnum tipoLicenciaEnum;
    private String usuarioLicencia; 

    public SessionCodefac() {
    }

    public SessionCodefac(Usuario usuario, Empresa empresa) {
        this.usuario = usuario;
        this.empresa = empresa;        
    }

    @Override
    public Usuario getUsuario() {
        return this.usuario;
    }

    @Override
    public Empresa getEmpresa() {
        return this.empresa;
    }

    @Override
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public Map<String,ParametroCodefac>  getParametrosCodefac() {
        return parametrosCodefac;
    }

    public void setParametrosCodefac(Map<String,ParametroCodefac> parametrosCodefac) {
        this.parametrosCodefac = parametrosCodefac;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }
    
    

    @Override
    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    @Override
    public boolean verificarExistePerfil(String nombre) {
        for (Perfil perfil : perfiles) {
            if(perfil.getNombre().equals(nombre))
            {
                return true;
            }
        }
        return false;
    }

    public void setTipoLicenciaEnum(TipoLicenciaEnum tipoLicenciaEnum) {
        this.tipoLicenciaEnum = tipoLicenciaEnum;
    }

    @Override
    public TipoLicenciaEnum getTipoLicenciaEnum() {
        return tipoLicenciaEnum;
    }

    public void setUsuarioLicencia(String usuarioLicencia) {
        this.usuarioLicencia = usuarioLicencia;
    }
    
    @Override
    public String getUsuarioLicencia() {
        return this.usuarioLicencia;
    }
    
    
    
    
    
    
}
