/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface RetencionServiceIf extends ServiceAbstractIf<Retencion> {

    public List<RetencionDetalle> obtenerRetencionesReporte(Persona persona, Date fi, Date ff, SriRetencionIva iva,SriRetencionRenta renta) throws java.rmi.RemoteException;

}
