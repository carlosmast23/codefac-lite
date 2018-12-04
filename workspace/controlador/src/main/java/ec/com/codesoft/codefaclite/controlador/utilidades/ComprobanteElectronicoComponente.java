/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoComponente {
    
    public static void cargarSecuencialConsulta(ComprobanteEntity comprobante,JComboBox<PuntoEmision> cmbPuntoEmision,JLabel lblEstablecimiento,JLabel lblSecuencial )
    {        
        
        try {        
            PuntoEmision puntoEmision=ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerPorCodigo(Integer.valueOf(comprobante.getPuntoEmision()));
            
            
            cmbPuntoEmision.setSelectedItem((PuntoEmision)puntoEmision); //TODO: Analizar para todos los casos porque aveces no me va a permitir cargagar cuando pertenece a otra sucursal
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex); 
            PuntoEmision puntoEmisionTmp=new PuntoEmision();
            puntoEmisionTmp.setPuntoEmision(Integer.valueOf(comprobante.getPuntoEmision()));
            cmbPuntoEmision.addItem(puntoEmisionTmp); //TODO: Revisar que salga bien
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblEstablecimiento.setText(ComprobantesUtilidades.formatoEstablecimiento(comprobante.getPuntoEstablecimiento()));
        lblSecuencial.setText(ComprobantesUtilidades.formatoSecuencial(comprobante.getSecuencial().toString()));
        
    }
    
    
    public static void cargarSecuencial(ComprobanteEnum tipoComprobante, Sucursal sucursal,JComboBox<PuntoEmision> cmbPuntoEmision,JLabel lblEstablecimiento,JLabel lblSecuencial)
    {
        int indiceSeleccionado=cmbPuntoEmision.getSelectedIndex();
        //Cargar Puntos de Venta disponibles para la sucursal

        try {
            List<PuntoEmision> puntosVenta = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(sucursal);
            cmbPuntoEmision.removeAllItems();
            //Canfigurar un cell render para las sucursales
            //getCmbPuntoEmision().setRenderer(new RenderPersonalizadoCombo());

            for (PuntoEmision puntoVenta : puntosVenta) {
                cmbPuntoEmision.addItem(puntoVenta);
            }

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if(indiceSeleccionado<0 && cmbPuntoEmision.getModel().getSize()>0 )
        {
            cmbPuntoEmision.setSelectedIndex(0); // Seleccionar el primero registro la primera vez
        }
        else
        {
            cmbPuntoEmision.setSelectedIndex(indiceSeleccionado);
        }
        
        
        lblEstablecimiento.setText(sucursal.getCodigoSucursalFormatoTexto()+"-");
        PuntoEmision puntoEmision=(PuntoEmision) cmbPuntoEmision.getSelectedItem();
        if(puntoEmision!=null)
        {
            Integer secuencial=-1; 
            switch(tipoComprobante)
            {
                case COMPROBANTE_RETENCION:
                    secuencial=puntoEmision.getSecuencialRetenciones();
                    break;
                    
                case FACTURA:
                    secuencial=puntoEmision.getSecuencialFactura();
                    break;
                    
                case GUIA_REMISION:
                    secuencial=puntoEmision.getSecuencialGuiaRemision();
                    break;
                    
                case NOTA_CREDITO:
                    secuencial=puntoEmision.getSecuencialNotaCredito();
                    break;                    
                    
                case NOTA_DEBITO:
                    secuencial=puntoEmision.getSecuencialNotaDebito();
                    break;
            }
            lblSecuencial.setText("-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(), 8, "0"));
        }
    }
}
