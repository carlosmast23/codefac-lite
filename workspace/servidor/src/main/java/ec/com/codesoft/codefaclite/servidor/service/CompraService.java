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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
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
    public void grabarCompra(Compra compra) throws ServicioCodefacException
    {
        entityManager.getTransaction().begin(); //Inicio de la transaccion
        
        try
        {
            //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
            for (CompraDetalle compraDetalle : compra.getDetalles()) {
                if (compraDetalle.getProductoProveedor().getId() == null) 
                {
                    entityManager.persist(compraDetalle.getProductoProveedor());
                } 
                else 
                {
                    entityManager.merge(compraDetalle.getProductoProveedor());
                }
            }

            entityManager.persist(compra);
            entityManager.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw  new ServicioCodefacException("Error al grabar la compra");       
            
        }
        //TODO: Falta retornar el tipo de dato por ejemplo en los dialogos necesita obtener el nuevo dato modificado.
    }
    

    @Override
    public List<Compra> obtenerTodos()
    {
        return compraFacade.findAll();
    }
    
    @Override
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum de, TipoDocumentoEnum tde)
    {
        return compraFacade.obtenerCompraReporte(proveedor, fechaInicial, fechaFin, de, tde);
    }    
    
    @Override
    public List<Compra> obtenerCompraDisenable()
    {
        return compraFacade.getCompraRetencionDisenable();
    }
}
