/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.panel.WidgetNotificacionesCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse.TipoAdvertenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class WidgetNotificacionCodefacModelo extends WidgetNotificacionesCodefac{

    private final static String TITULO_PAGINA="Notificaciones Codefac";
    private Sucursal sucursal;
    public WidgetNotificacionCodefacModelo(JDesktopPane parentPanel,Sucursal sucursal) {
        super(parentPanel); 
        getLblNotificaciones().setText(TITULO_PAGINA);
        this.sucursal=sucursal;
        listenerBotones();
        listenerPopUps();
        actualizarNotificaciones(ModoProcesarEnum.NORMAL);
        
    }

    
    @Override
    public JPanel getPanelMovimiento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
   
    public void actualizarNotificaciones(ModoProcesarEnum modoEnum) {
        //Eejcutar proceso de notrificaciones en segundo plano
        limpiarDatos();
        getLblNotificaciones().setText(TITULO_PAGINA+ " [ Cargando ... ]");
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.INFO,"Actualizando Notificaciones en el servidor");
                    //ServiceFactory.getFactory().getAlertaServiceIf().procesoBloqueado(empresa);
                    //ServiceFactory.getFactory().getParametroCodefacServiceIf().procesoBloqueadoPrueba();
                    
                    
                    List<AlertaResponse> alertas = ServiceFactory.getFactory().getAlertaServiceIf().actualizarNotificacionesCargaRapida(sucursal);
                    String[] tituloTabla = {"Tipo", "Problema", "Solución"};
                    
                    UtilidadesTablas.LlenarDatoTablaIf interfazLlenarDatos=new UtilidadesTablas.LlenarDatoTablaIf<AlertaResponse>() {
                        @Override
                        public void agregarDato(List<Object> fila, AlertaResponse dato) {
                            fila.add(dato.tipoAdvertenciaEnum.toString());
                            fila.add(dato.descripcion);
                            fila.add(dato.solucion);
                        }
                    };
                    
                    //Cargar datos rapidos
                    UtilidadesTablas.llenarTablasDatos(
                            getTblNotificaciones(),
                            tituloTabla,
                            alertas,
                            interfazLlenarDatos
                    );
                    
                    definirFormatoTabla();
                    
                    //cargar datos lentos
                    List<AlertaResponse> alertasLentas = ServiceFactory.getFactory().getAlertaServiceIf().actualizarNotificacionesCargaLenta(sucursal.getEmpresa(),modoEnum);
                    UtilidadesTablas.llenarTablasDatos(
                            getTblNotificaciones(),
                            alertasLentas, 
                            interfazLlenarDatos);

                    //DefaultTableModel modeloTabla=new DefaultTableModel(tituloTabla,0);
                    //Agregando notificacions de los comprobantes en el caso de que exista            
                    //Agregando notificacion de la firma con 1 mes de anticipación si esta próximo a cumplirse la fecha tope
                    //Metodo que permite visualizar o no el cuadro de notificaciones en el caso de que si exista
                    if (getTblNotificaciones().getRowCount() == 0) {
                        setVisible(false);
                    } else {
                        setVisible(true);
                    }
                    //getTblNotificaciones().setModel(modeloTabla);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Pongo nuevamente el titulo original
                getLblNotificaciones().setText(TITULO_PAGINA);

            }
        }).start();

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
                        } 
                        else if (value.toString().equals(TipoAdvertenciaEnum.ALERTA.toString())) {
                            setBackground(Color.YELLOW);
                        } 
                        else {
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
                actualizarNotificaciones(ModoProcesarEnum.FORZADO);
            }
        });
    }
    
    private void limpiarDatos() 
    {
        getTblNotificaciones().setModel(new DefaultTableModel());
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    private void listenerPopUps() {
        JPopupMenu menu=new JPopupMenu();
        JMenuItem item= new JMenuItem("Solucionar Problema");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer filaSeleccionada=getTblNotificaciones().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    String nombreAlerta=(String) getTblNotificaciones().getValueAt(filaSeleccionada, 1);
                    
                    if(nombreAlerta.contains(AlertaResponse.ALERTA_COMPROBANTES_PENDIENTES_AUTORIZAR))
                    {
                        listenerTerminarAutorizarComprobantes();
                    }
                }
            }
        });
       
        menu.add(item);
        getTblNotificaciones().setComponentPopupMenu(menu);
    }

    private void listenerTerminarAutorizarComprobantes()
    {
        try {
            ServiceFactory.getFactory().getComprobanteServiceIf().procesarSinAutorizarYEnviadosPendientes(sucursal.getEmpresa());
            actualizarNotificaciones(ModoProcesarEnum.NORMAL);
            DialogoCodefac.mensaje(new CodefacMsj("Proceso finalizado correctamente", CodefacMsj.TipoMensajeEnum.CORRECTO));            
        } catch (RemoteException ex) {
            Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        }
    }
    
}
