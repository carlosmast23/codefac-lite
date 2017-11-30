/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.evento;

import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;

/**
 *
 * @author Carlos
 */
public interface ListenerComprobanteElectronico {
    public abstract void termino();
    public abstract void iniciado();
    public abstract void procesando(int etapa);
    public abstract void error(ComprobanteElectronicoException cee);
}
