/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface  CarteraServiceIf extends ServiceAbstractIf<Cartera>{
    public Cartera grabarCartera(Cartera cartera,List<CarteraCruce> cruces) throws ServicioCodefacException,java.rmi.RemoteException;
    public List<Cartera> listaCarteraSaldoCero(Persona persona, Date fi, Date ff,DocumentoCategoriaEnum categoriaMenuEnum,Cartera.TipoCarteraEnum tipoCartera,Boolean carteraConSaldo) throws ServicioCodefacException, RemoteException;
    public List<CarteraCruce> consultarMovimientoCartera(Persona persona) throws java.rmi.RemoteException;
    public void eliminar(Cartera entity,ModoProcesarEnum modo) throws ServicioCodefacException, RemoteException;
    public void editar(Cartera entity,List<CarteraCruce> cruces) throws ServicioCodefacException, RemoteException;
    
}
