/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class AtsService extends UnicastRemoteObject implements Serializable,AtsServiceIf {

    public AtsService() throws RemoteException {
        
    }
    
    public List<VentaAts> consultarVentasAts() throws  RemoteException,ServicioCodefacException
    {
        FacturacionService facturacionService=new FacturacionService();
        List<Factura> facturas=facturacionService.obtenerFacturasReporte(null,null,null,null,false,null,false);
        
        Map<String,VentaAts> mapVentas=new HashMap<String,VentaAts>();
        
        for (Factura factura : facturas) 
        {
            VentaAts ventaAts=mapVentas.get(factura.getIdentificacion());
            if(ventaAts==null)
            { //Cuando no existe el dato en el map lo creo
                ventaAts=new VentaAts();
                
                ventaAts.setBaseImponible(factura.getSubtotalImpuestos());
                ventaAts.setIdCliente(factura.getIdentificacion());
                ventaAts.setMontoIva(factura.getIva());
                ventaAts.setNumeroComprobantes(1);
                ventaAts.setTpIdCliente("04"); //Ver que significa este simbolo
                mapVentas.put(factura.getIdentificacion(),ventaAts);
            }
            else
            {//Si existe solo consulto y edito los valores
                ventaAts.setBaseImponible(ventaAts.getBaseImponible().add(factura.getSubtotalImpuestos()));
                ventaAts.setMontoIva(ventaAts.getMontoIva().add(factura.getIva()));
                ventaAts.setNumeroComprobantes(ventaAts.getNumeroComprobantes()+1);
            }
        }
        
        ////====================> Reconstruir los Valores de las ventas en una Lista <============================///
        List<VentaAts> ventasAts=new ArrayList<VentaAts>();
        for (Map.Entry<String, VentaAts> entry : mapVentas.entrySet()) {
            //String key = entry.getKey();
            VentaAts value = entry.getValue();
            ventasAts.add(value);
        }
        return ventasAts;
        
    }
    
}
