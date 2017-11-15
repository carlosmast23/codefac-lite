/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.session;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class SessionCodefac implements SessionCodefacInterface{
    private Usuario usuario;
    private Empresa empresa;
    private List<ParametroCodefac> parametrosCodefac;

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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public List<ParametroCodefac> getParametrosCodefac() {
        return parametrosCodefac;
    }

    public void setParametrosCodefac(List<ParametroCodefac> parametrosCodefac) {
        this.parametrosCodefac = parametrosCodefac;
    }
    
    
    
    
    
}
