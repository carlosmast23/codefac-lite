/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;


/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface KardexServiceIf extends ServiceAbstractIf<Kardex>
{
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException;
    public void IngresoEgresoInventarioEnsamble(Bodega bodega, Producto productoEnsamble,Integer cantidad,List<Kardex> componentesKardex, boolean ingreso) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(List<KardexDetalle> detalles) throws java.rmi.RemoteException,ServicioCodefacException;
}
