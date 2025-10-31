/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.KardexDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class KardexDetalleService extends ServiceAbstract<KardexDetalle, KardexDetalleFacade> implements KardexDetalleServiceIf
{
    
    public KardexDetalleService() throws RemoteException {
        super(KardexDetalleFacade.class);
    }
    
    public KardexDetalle consultarPorReferencia(TipoDocumentoEnum tipoDocumentoEnum,Long referenciaDocumentoId,Producto producto,EntityManager em) throws RemoteException
    {

        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("codigoTipoDocumento",tipoDocumentoEnum.getCodigo());
        mapParametros.put("referenciaDocumentoId",referenciaDocumentoId);
        mapParametros.put("kardex.producto",producto);
        
        List<KardexDetalle> resultado=getFacade().findByMap(mapParametros,em);
        
        if(resultado.size()>0)
        {
            return resultado.get(0);
        }
        
        return null;
    }
    
    public List<KardexDetalle> consultarPorKardex(Kardex kardex) throws java.rmi.RemoteException,ServicioCodefacException
    {
        return (List<KardexDetalle>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                return consultarPorKardex(kardex);
            }
        });
    }
    
    public List<KardexDetalle> consultarPorKardex(Kardex kardex,EntityManager em) throws java.rmi.RemoteException,ServicioCodefacException
    {
        KardexDetalle kd;
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("kardex",kardex);
        List<KardexDetalle> resultado=getFacade().findByMap(mapParametros,em);
        
        return resultado;
    }
    
}
