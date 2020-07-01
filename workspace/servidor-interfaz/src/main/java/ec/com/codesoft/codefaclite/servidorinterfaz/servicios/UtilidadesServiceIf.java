/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.EmpresaLicencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
 
 ;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Carlos
 */
public interface UtilidadesServiceIf   
{
    /**
     * Metodo que me permite sincronizar con la persistencia el objecto actual
     * @param entity
     * @return
     * @   
     */
    public Object mergeEntity(Object entity)   ;
    public List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo)   ;
    public Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map)   ;
    public boolean verificarConexionesServidor(Empresa empresa)   ;
    public TipoLicenciaEnum getTipoLicencia(Empresa empresa)   ;
    public List<ModuloCodefacEnum> getModulosSistema(Empresa empresa);     
    /**
     * Me devuelve un objeto session con algunos datos preconstruidos
     * @return
     * @   
     */
    public SessionCodefac getSessionPreConstruido(Empresa empresa) ;    
    
    public EmpresaLicencia obtenerLicenciaEmpresa(Empresa empresa) throws   ServicioCodefacException;
    
    public Properties crearLicencia(Empresa empresa,Licencia licencia) throws   ServicioCodefacException;
    
    public Properties crearLicenciaDescargada(Empresa empresa,Licencia licencia) throws   ServicioCodefacException;
    
    
    
}
