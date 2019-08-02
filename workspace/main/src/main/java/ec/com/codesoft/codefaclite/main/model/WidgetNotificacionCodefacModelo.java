/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.main.panel.WidgetNotificacionesCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class WidgetNotificacionCodefacModelo extends WidgetNotificacionesCodefac{

    private Empresa empresa;
    public WidgetNotificacionCodefacModelo(JDesktopPane parentPanel,Empresa empresa) {
        super(parentPanel); 
        this.empresa=empresa;
        listenerBotones();
        actualizarNotificaciones();
        
    }

    
    @Override
    public JPanel getPanelMovimiento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void actualizarNotificaciones()
    {
        try {
            String[] tituloTabla={"Tipo","Problema","Solución"};
            DefaultTableModel modeloTabla=new DefaultTableModel(tituloTabla,0);
            
            
            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            List<ComprobanteElectronico> comprobantesFirmadoSinEnviar = comprobanteServiceIf.getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR,empresa);
            List<ComprobanteElectronico> comprobantesEnviadosSinRespuesta = comprobanteServiceIf.getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA,empresa);
            
            Integer totalComprobantesSinEnviar=comprobantesFirmadoSinEnviar.size()+comprobantesEnviadosSinRespuesta.size();
            
            if(totalComprobantesSinEnviar>0)
            {
                modeloTabla.addRow(new Object[]{"Advertencia",totalComprobantesSinEnviar+" Comprobantes de enviar al Sri","Utilizar herramienta enviar"});
            }
                       
            if(modeloTabla.getRowCount()==0)
            {
                setVisible(false);
            }else
            {
                setVisible(true);
            }
            
            
            
            getTblNotificaciones().setModel(modeloTabla);
            
            //UtilidadesTablas.cambiarTamanioColumnas(getTblNotificaciones(),new Integer[]{50,200,100});
            UtilidadesTablas.definirTamanioColumnas(getTblNotificaciones(),new Integer[]{80,280,100});
            
            
            getTblNotificaciones().setDefaultRenderer(getTblNotificaciones().getColumnClass(0), new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //System.out.println(row+":"+column);
                if (column ==0) {
                    
                    //Si por algun motivo no existe ningun dato pinto de otro color para ver el eror
                    if(value==null)
                    {
                        setBackground(Color.green);
                    }
                    else
                    {

                        if (value.toString().equals("Advertencia")) {
                            setBackground(Color.orange);
                        }else
                        {
                            setBackground(Color.white);
                        }
                    }
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
            }          
            
        });
            
        } catch (RemoteException ex) {
            Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private void listenerBotones() {
        getBtnActualizarNotificaciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarNotificaciones();
            }
        });
    }
    
}
