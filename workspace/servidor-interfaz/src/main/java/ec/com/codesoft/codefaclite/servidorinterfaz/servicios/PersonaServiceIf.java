/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
// 
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface PersonaServiceIf extends ServiceAbstractIf<Persona>{
    
    public Persona grabar(Persona p,Boolean validarCedula) throws ServicioCodefacException;    
    
    public Persona grabar(Persona p) throws ServicioCodefacException;    
   
    public void editar(Persona p) throws ServicioCodefacException;
    
    public void eliminar(Persona p) throws ServicioCodefacException;
    
    public List<Persona> buscar() ;
    
    public List<Persona> buscarPorTipo(OperadorNegocioEnum tipoEnum, GeneralEnumEstado estado,Empresa empresa) ;
    
    public Persona buscarPorIdentificacionYestado(String identificacion,GeneralEnumEstado estado) throws ServicioCodefacException;
    
    public Persona buscarPorIdentificacion(String identificacion,Empresa empresa) ;
    
    //TODO: Ver si se hace un solo metodo que el de editar pero falta agregar la exepcion de ServicioCodefacException
    public void editarPersona(Persona p) throws ServicioCodefacException;
    
    public Persona buscarConsumidorFinal(Empresa empresa) throws ServicioCodefacException;
    
    public Persona crearConsumidorFinal(Empresa empresa) throws ServicioCodefacException;
    
    public Persona getEjemplo() ;
}
