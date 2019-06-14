/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.session;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class SessionCodefac implements SessionCodefacInterface{
    
    protected Usuario usuario;
    protected Empresa empresa;
    protected Sucursal matriz;
    protected Sucursal sucursal;
    protected Map<String,ParametroCodefac>  parametrosCodefac;
    protected List<Perfil> perfiles;
    protected TipoLicenciaEnum tipoLicenciaEnum;
    protected String usuarioLicencia; 
    protected Licencia licencia;
    protected List<ModuloCodefacEnum> modulos;

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
        try {
            //return parametrosCodefac;
            return ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametrosMap();
        } catch (RemoteException ex) {
            Logger.getLogger(SessionCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
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

    @Override
    public List<ModuloCodefacEnum> getModulos() {
        return modulos;
    }

    public void setModulos(List<ModuloCodefacEnum> modulos) {
        this.modulos = modulos;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Sucursal getMatriz() {
        return matriz;
    }

    public void setMatriz(Sucursal matriz) {
        this.matriz = matriz;
    }

    /**
     * Obtiene el valor en escala de 100%
     * @return 
     */
    @Override
    public BigDecimal obtenerIvaActual() {
        ParametroCodefac parametroCodefac=getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO);
        String valorString=parametroCodefac.getValor();
        return new BigDecimal(valorString);
    }

    /**
     * Obtiene el el valor en escala de decimales
     * @return 
     */
    @Override
    public BigDecimal obtenerIvaActualDecimal() {
        return obtenerIvaActual().divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
    }
    
    
}
