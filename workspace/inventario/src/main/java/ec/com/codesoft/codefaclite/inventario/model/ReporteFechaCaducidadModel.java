/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.panel.StockMinimoPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.FechaCaducidadData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReporteFechaCaducidadReport;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReporteFechaCaducidadModel extends StockReporteModel{

    private ReporteFechaCaducidadReport reporte;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException 
    {
        super.valoresIniciales();
        listenerBotones();
    }
    
    private void listenerBotones()
    {
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBuscar();
            }
        });
    }
    
    private void listenerBuscar()
    {
        try {
            Bodega bodega=null;
            if(!getChkTodasBodega().isSelected())
            {
                bodega=(Bodega) getCmbBodega().getSelectedItem();
            }            
            
            Integer diasCaducidad=(Integer) getTxtDiasCaducidad().getValue()+1;
            Date fechaHoy=UtilidadesFecha.getFechaHoy();
            Date fechaComparar=UtilidadesFecha.castDateUtilToSql(UtilidadesFecha.sumarDiasFecha(fechaHoy,diasCaducidad));
            System.out.println("Fecha Comparar: "+fechaComparar);
            
            
            reporte=(ReporteFechaCaducidadReport) ServiceFactory.getFactory().getLoteSeviceIf().reporteFechaCaducidad(session.getSucursal(), bodega,fechaComparar);
            imprimirTabla();
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteFechaCaducidadModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteFechaCaducidadModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void imprimirTabla()
    {
        String[] titulos={"Código","Bodega","Lote","Nombre Producto","Fecha Vencimiento","Stock","Valor Unitario"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulos,0);
        
        if(reporte!=null)
        {
            
            for (FechaCaducidadData dato : reporte.getDetalleList()) {
                Vector<String> datos=new Vector<String>();
                datos.add(dato.getCodigoPersonalizado());
                datos.add(dato.getNombreBodega());
                datos.add(dato.getCodigoLote());
                datos.add(dato.getNombreProducto());
                datos.add(dato.getFechaCaducidad());
                datos.add(dato.getStock());
                datos.add(dato.getValorUnitario());
                modeloTabla.addRow(datos);
            }
        }
        getTblDato().setModel(modeloTabla);
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        if (reporte != null) {
            InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream("productoPorCaducar.jrxml");

            DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                @Override
                public void excel() {
                    try {
                        Excel excel = new Excel();
                        String[] titulos={"Código","Bodega","Lote","Nombre Producto","Fecha Vencimiento","Stock","Valor Unitario"};
                        excel.gestionarIngresoInformacionExcel(titulos, new ArrayList());
                        excel.abrirDocumento();
                    } catch (Exception exc) {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                }

                @Override
                public void pdf() {

                    ReporteCodefac.generarReporteInternalFramePlantilla(path, panelPadre, reporte);
                    //dispose();
                    //setVisible(false);
                }
            });

        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
}
