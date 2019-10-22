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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Date;
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
        String[] tituloTabla={"Tipo","Problema","Solución"};
        DefaultTableModel modeloTabla=new DefaultTableModel(tituloTabla,0);
        //Agregando notificacions de los comprobantes en el caso de que exista
        Object[] fila=agregarNotificacionComprobantesElectronicos();
        if(fila!=null)
        {
            modeloTabla.addRow(fila);
        }
        
        //Agregando noficaciones de la firma para avisar cuando ya va a caducar
        fila=agregarNotificacionFechaLimiteFirma();        
        if(fila!=null)
        {
            modeloTabla.addRow(fila);
        }
        
        //Agregando notificacion de la firma con 1 mes de anticipación si esta próximo a cumplirse la fecha tope
        //Metodo que permite visualizar o no el cuadro de notificaciones en el caso de que si exista
        if(modeloTabla.getRowCount()==0)
        {
            setVisible(false);
        }else
        {
            setVisible(true);
        }
        getTblNotificaciones().setModel(modeloTabla);
        definirFormatoTabla();
        
        
    }
    
    private Object[] agregarNotificacionFechaLimiteFirma()
    {
        
        try {
            String valorFechaEmision=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.FIRMA_FECHA_EMISION);
            if(valorFechaEmision!=null && !valorFechaEmision.isEmpty())
            {
                Date fechaEmisionFirma=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.parse(valorFechaEmision);
                
                String duracionFirmaStr=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.FIRMA_TIEMPO_EXPIRACION_AÑOS);
                if(duracionFirmaStr!=null && !duracionFirmaStr.isEmpty())
                {
                    Integer anios=Integer.parseInt(duracionFirmaStr);
                    if(anios>0)
                    {
                        Date fechaLimite=UtilidadesFecha.sumarAniosFecha(fechaEmisionFirma,anios);
                        Date fechaActual=UtilidadesFecha.getFechaHoy();
                        Integer diasFaltantes=UtilidadesFecha.obtenerDistanciaDias(fechaActual, fechaLimite);
                        if(diasFaltantes<30 && diasFaltantes>=0)
                        {
                            return new Object[]{TipoAdvertenciaEnum.ADVERTENCIA,"Faltan "+diasFaltantes+" días para caducar la firma","Tramitar la firma"};
                        } else if(diasFaltantes<0)
                        {
                            return new Object[]{TipoAdvertenciaEnum.GRAVE,"Error "+Math.abs(diasFaltantes)+" días que la firma ya caduco","Tramitar la firma"};
                        }
                        
                    }
                    
                }
                
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private Object[] agregarNotificacionComprobantesElectronicos()
    {
        try {
            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            List<ComprobanteElectronico> comprobantesFirmadoSinEnviar = comprobanteServiceIf.getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR, empresa);
            List<ComprobanteElectronico> comprobantesEnviadosSinRespuesta = comprobanteServiceIf.getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA, empresa);
            
            Integer totalComprobantesSinEnviar = comprobantesFirmadoSinEnviar.size() + comprobantesEnviadosSinRespuesta.size();
            
            if (totalComprobantesSinEnviar > 0) {
                //modeloTabla.addRow(new Object[]{TipoAdvertenciaEnum.ADVERTENCIA,totalComprobantesSinEnviar+" Comprobantes de enviar al Sri","Utilizar herramienta enviar"});
                return new Object[]{TipoAdvertenciaEnum.ADVERTENCIA, totalComprobantesSinEnviar + " Comprobantes de enviar al Sri", "Utilizar herramienta enviar"};
            }       
            
        } catch (RemoteException ex) {
            Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que se encarga de definir el tamañoa de las columnas y adicional el color de cada fila de la notificación
     */
    private void definirFormatoTabla()
    {
        //UtilidadesTablas.cambiarTamanioColumnas(getTblNotificaciones(),new Integer[]{50,200,100});
        UtilidadesTablas.definirTamanioColumnas(getTblNotificaciones(), new Integer[]{80, 280, 100});

        getTblNotificaciones().setDefaultRenderer(getTblNotificaciones().getColumnClass(0), new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //System.out.println(row+":"+column);
                if (column == 0) {

                    //Si por algun motivo no existe ningun dato pinto de otro color para ver el eror
                    if (value == null) {
                        setBackground(Color.green);
                    } else {

                        if (value.toString().equals(TipoAdvertenciaEnum.ADVERTENCIA.toString())) {
                            setBackground(Color.orange);
                        } else if (value.toString().equals(TipoAdvertenciaEnum.GRAVE.toString())) {
                            setBackground(Color.red);
                        } else {
                            setBackground(Color.white);
                        }
                    }
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }

    private void listenerBotones() {
        getBtnActualizarNotificaciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarNotificaciones();
            }
        });
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public enum TipoAdvertenciaEnum
    {
        INFORMATIVO("Info"),
        ADVERTENCIA("Advertencia"),
        GRAVE("Grave");
        
        private String grave;

        private TipoAdvertenciaEnum(String grave) {
            this.grave = grave;
        }

        @Override
        public String toString() {
            return grave;
        }
        
        
    }
    
}
