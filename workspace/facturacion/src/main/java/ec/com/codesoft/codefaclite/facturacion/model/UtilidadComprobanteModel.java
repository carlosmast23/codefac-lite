/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import com.sun.glass.ui.Cursor;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.other.FacturacionElectronica;
import ec.com.codesoft.codefaclite.facturacion.panel.UtilidadComprobantePanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class UtilidadComprobanteModel extends UtilidadComprobantePanel {

    private JInternalFrame frame;
    private DefaultTableModel tableModel;

    public UtilidadComprobanteModel() {
        iniciarComponentes();
        addBotonListener();
        frame=this;
        getBtnCompletarProceso().setEnabled(false);
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {

    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, false);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, false);
        return permisos;
    }

    private void cargarDatosTabla(File[] archivos) {
        Vector<String> titulo = new Vector<>();
        titulo.add("clave");
        titulo.add("fecha");

        this.tableModel = new DefaultTableModel(titulo, 0);

        for (File archivo : archivos) {
            Vector<String> fila = new Vector<>();
            fila.add(archivo.getName());

            Date d = new Date(archivo.lastModified());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");

            fila.add(dateFormat.format(d));
            this.tableModel.addRow(fila);
        }
        this.getTblComprobantes().setModel(tableModel);
    }

    private void addBotonListener() {
        getBtnCompletarProceso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
                File[] archivos = ComprobantesElectronicosUtil.getComprobantesByFolder(path, getCmbCarpetaComprobante().getSelectedItem().toString());
                cargarDatosTabla(archivos);
            }
        });

        getCmbCarpetaComprobante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
                File[] archivos = ComprobantesElectronicosUtil.getComprobantesByFolder(path, getCmbCarpetaComprobante().getSelectedItem().toString());
                cargarDatosTabla(archivos);
            }
        });

        getBtnSiguienteEtapa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturacionElectronica servicio = new FacturacionElectronica(session, panelPadre);
                
                estadoCargando();
                
                servicio.getServicio().addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
                    @Override
                    public void termino() {
                        estadoNormal();                        
                        DialogoCodefac.mensaje("Dialogo", "RIDE generado correctamente", 1);
                       //frame.setEnabled(true);
                    }

                    @Override
                    public void iniciado() {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void procesando(int etapa) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void error(ComprobanteElectronicoException cee) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                String claveAcceso=tableModel.getValueAt(getTblComprobantes().getSelectedRow(),0).toString().replace(".xml","");
                servicio.setClaveAcceso(claveAcceso);
                servicio.procesarComprobanteEtapa(ComprobanteElectronicoService.ETAPA_RIDE, false);
            }
        });
    }

    private void iniciarComponentes() {
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS);
    }

}
