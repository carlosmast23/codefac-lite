/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
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
    
    public static void eliminarComprobante(GeneralPanelInterface panel,ComprobanteEntity comprobante,JLabel labelEstado) throws ExcepcionCodefacLite
    {
        //Varible 
        boolean respuesta = false;

        //Eliminar solo si esta en modo editar
        if (panel.estadoFormulario.equals(panel.ESTADO_EDITAR)) {
            if (comprobante != null) {

                //Eliminar solo si el estado esta en sin autorizar, o esta en el modo de facturacion normal y esta con estado facturado
                if (comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado())
                        || (comprobante.getTipoFacturacion().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()) && comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado()))) {

                    respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Esta seguro que desea eliminar el comprobante electrónico? ", DialogoCodefac.MENSAJE_ADVERTENCIA);

                } else {
                    respuesta = DialogoCodefac.dialogoPregunta("Alerta", "El comprobante electrónico se encuentra autorizada en el SRI , \nPorfavor elimine  solo si tambien esta anulado en el SRI\nDesea eliminar de todos modos?", DialogoCodefac.MENSAJE_INCORRECTO);
                }

                //Eliminar la factura si eligen la respuesta si
                if (respuesta) {
                    try {
                        
                        switch(comprobante.getCodigoDocumentoEnum())
                        {
                            case FACTURA:
                                ServiceFactory.getFactory().getFacturacionServiceIf().eliminarFactura((Factura) comprobante);
                                break;
                                
                            case RETENCIONES:
                                ServiceFactory.getFactory().getRetencionServiceIf().eliminar((Retencion)comprobante);
                                break;
                                
                            case GUIA_REMISION:
                                ServiceFactory.getFactory().getGuiaRemisionServiceIf().eliminar((GuiaRemision) comprobante);
                                break;
                                
                            case NOTA_CREDITO:
                                ServiceFactory.getFactory().getNotaCreditoServiceIf().eliminar((NotaCredito)comprobante);
                                break;                                
                        }
                        
                        //ServiceFactory.getFactory().getComprobanteServiceIf().eliminarComprobante(comprobante);
                        DialogoCodefac.mensaje("Exitoso", "El comprobante electrónico se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                        
                        /// Opcion para tambien eliminar el comprobante fisico
                        if (comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado()))
                        {
                            Boolean respuestaEliminar=DialogoCodefac.dialogoPregunta("Exitoso","Desea eliminar también el comprobante físico?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                            if(respuestaEliminar)
                            {
                                ServiceFactory.getFactory().getComprobanteServiceIf().eliminarComprobanteFisico(comprobante.getClaveAcceso());
                                DialogoCodefac.mensaje("Exitoso", "El comprobante electrónico se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                                panel.panelPadre.actualizarNotificacionesCodefac();
                            }
                        }
                        
                        if(labelEstado!=null)
                        {
                            labelEstado.setText(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getNombre());
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje("Error al eliminar",ex.getMessage(),DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                }
            }
        } else {
            throw new ExcepcionCodefacLite("Cancelar evento eliminar porque no esta en modo editar");
        }
    
    }
    
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
