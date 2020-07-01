/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
 ;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface PeriodoServiceIf extends ServiceAbstractIf<Periodo>{
    public List<Periodo> obtenerPeriodoActivo();    
    public Periodo obtenerUnicoPeriodoActivo();    
    public List<Periodo> obtenerPeriodosSinEliminar();    
    public void setearPeriodoActivo(Periodo periodoActivar) throws   ServicioCodefacException;
    public Periodo buscarPorNombreyEstado(String nombre,GeneralEnumEstado estado);    
}
