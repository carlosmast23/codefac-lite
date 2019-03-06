/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface GuiaRemisionServiceIf extends ServiceAbstractIf<GuiaRemision> {
    public List<GuiaRemision> obtenerConsulta(Date fechaInicial,Date fechaFinal,ComprobanteEntity.ComprobanteEnumEstado estado,Transportista transportista,Persona destinatario,String codigoProducto) throws ServicioCodefacException, RemoteException;
    public void editarGuiaRemision(GuiaRemision guiaRemision) throws ServicioCodefacException, RemoteException;
}
