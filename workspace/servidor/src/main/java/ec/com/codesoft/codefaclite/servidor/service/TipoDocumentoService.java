/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.TipoDocumentoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoDocumento;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TipoDocumentoServiceIf;
 ;

/**
 *
 * @author Carlos
 */
public class TipoDocumentoService extends ServiceAbstract<TipoDocumento, TipoDocumentoFacade> implements TipoDocumentoServiceIf{

    public TipoDocumentoService()    {
        super(TipoDocumentoFacade.class);
    }
    
}
