/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.session;

import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface SessionCodefacInterface {
    public Usuario getUsuario();
    public Empresa getEmpresa();
    public List<ParametroCodefac> getParametrosCodefac();
}
