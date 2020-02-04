/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.CompraDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.CompraFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class CompraService extends ServiceAbstract<Compra,CompraFacade> implements CompraServiceIf{
    
    CompraFacade compraFacade;
    CompraDetalleFacade compraDetalleFacade;
    
    
    public CompraService() throws RemoteException {
        super(CompraFacade.class);
        this.compraFacade = new CompraFacade();
        this.compraDetalleFacade = new CompraDetalleFacade();
    }
    
    @Override
    public void editarCompra(Compra compra) throws ServicioCodefacException
    {
        //TODO: Editar este metodo porque el de grabar es muy similar
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
                    for (CompraDetalle compraDetalle : compra.getDetalles()) {
                        if (compraDetalle.getProductoProveedor().getId() == null) {
                            entityManager.persist(compraDetalle.getProductoProveedor());
                        } else {
                            entityManager.merge(compraDetalle.getProductoProveedor());
                        }
                    }

                    entityManager.merge(compra);
            }
        });
    }
    
    
    @Override
    public void grabarCompra(Compra compra) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                try {
                    compra.setInventarioIngreso(EnumSiNo.NO.getLetra()); //La primera vez que grabo por defecto grabo para poder ingresar al inventario
                    //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
                    for (CompraDetalle compraDetalle : compra.getDetalles()) {
                        if (compraDetalle.getProductoProveedor().getId() == null) {
                            entityManager.persist(compraDetalle.getProductoProveedor());
                        } else {
                            entityManager.merge(compraDetalle.getProductoProveedor());
                        }
                    }                    
                    entityManager.persist(compra);
                    grabarCartera(compra); //Grabo la cartera desde de grabar la compra para tener el id de referencia que necesito en cartera
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServicioCodefacException(e.getMessage());

                }
            }
        });
        
        //TODO: Falta retornar el tipo de dato por ejemplo en los dialogos necesita obtener el nuevo dato modificado.
    }
    
    private void grabarCartera(Compra compra) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(compra, Cartera.TipoCarteraEnum.PROVEEDORES);
    }

    public void eliminarCompra(Compra compra) throws ServicioCodefacException,RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                RetencionService retencionService=new RetencionService();
                List<Retencion> retencionesAsociadas= retencionService.obtenerRetencionesPorCompra(compra);
                
                if(retencionesAsociadas.size()==0)
                {
                    compra.setEstado(GeneralEnumEstado.ELIMINADO.getEstado()); //Cambiar el estado de la compra
                    entityManager.merge(compra);                    
                }
                else
                {
                    //Obtener las retenciones asociadas
                    String retencionesStr="";
                    for (Retencion retencionesAsociada : retencionesAsociadas) {
                        retencionesStr+=retencionesAsociada.getPreimpreso()+"  ";                        
                    }
                    //retencionesStr=UtilidadesTextos.quitarUltimaLetra(retencionesStr);
                    
                    throw new ServicioCodefacException("No se puede eliminar porque existe retenciones asociadas "+retencionesStr);
                }
                
            }
        });
    }
    
    
    

    @Override
    public List<Compra> obtenerTodos()
    {
        return compraFacade.findAll();
    }
    
    @Override
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum de, TipoDocumentoEnum tde,GeneralEnumEstado estadoEnum,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        return compraFacade.obtenerCompraReporte(proveedor, fechaInicial, fechaFin, de, tde,estadoEnum,empresa);
    }    
    
    @Override
    public List<Compra> obtenerCompraDisenable()
    {
        return compraFacade.getCompraRetencionDisenable();
    }
}
