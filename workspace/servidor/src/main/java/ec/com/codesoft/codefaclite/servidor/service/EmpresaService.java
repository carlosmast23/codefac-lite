/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.EmpresaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
 ;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class EmpresaService extends ServiceAbstract<Empresa, EmpresaFacade> implements EmpresaServiceIf
{
    private EmpresaFacade empresaFacade;
    
    public EmpresaService()    
    {        
        super(EmpresaFacade.class);
        this.empresaFacade = new EmpresaFacade();
    }
    
    public Empresa grabar(Empresa p) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException   {
                //Grabar la empresa
                entityManager.persist(p);
                
                //Grabar un perfil por defecto
                PerfilService perfilService = new PerfilService();
                perfilService.crearPerfilPorDefectoSinTransaccion(p);
                
                //Grabar el usuario de consumidor final por defecto
                PersonaService personaService=new PersonaService();
                personaService.crearConsumidorFinalSinTransaccion(p);
                
            }
        });
        
        return p;
    }
    
    /*public void editar(Empresa p)
    {
        try
        {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    entityManager.merge(p);
                }
            });
        }
        catch(ServicioCodefacException e)
        {
            e.printStackTrace();
        }
        
    }*/
    
    public void eliminar(Empresa p) throws ServicioCodefacException   
    {
        //Empresa empresa;
        //empresa.getEstado();
        //p.setEstadoEnum(GeneralEnumEstado.);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException   {
                p.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(p);
            }
        });
        //empresaFacade.remove(p);
    }
    
    public List<Empresa> buscar()
    {
        return empresaFacade.findAll();
    } 
    
    public Empresa buscarPorIdentificacion(String identificacion)    
    {
        //Empresa empresa;       
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("identificacion", identificacion);
        List<Empresa> empresas=getFacade().findByMap(mapParametros);
        if(empresas.size()>0)
        {
            return empresas.get(0);
        }
        return null;
    }
    
    public List<Empresa> obtenerTodosActivos()   
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        List<Empresa> empresas=getFacade().findByMap(mapParametros);
        return empresas;
        
    }
        
}
