/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
 ;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface AulaServiceIf extends ServiceAbstractIf<Aula> {
    public List<Aula> obtenerAulasActivas();    
}
