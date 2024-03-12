/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.UtilidadReport;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author DellWin10
 */
public class UtilidadReporteModel extends FacturaReporteModel
{
    
    private UtilidadReport utilidadReport;

    /*public UtilidadReporteModel() 
    {
        super();
    }*/
    

    @Override
    public void iniciar()  
    {
        super.iniciar();
        //Poner estos campos en un mejor lugar
        getPnlUtilidades().setVisible(true);
        getPanelValores().setVisible(false);
        getLblSubtotalUtilidad().setText("0");
        getLblCostoUtilidad().setText("0");
        getTxtUtilidad().setText("0");
        getCmbTipoReporte().setVisible(false);
        getLblTipoReporte().setVisible(false);
        listenerTablas();
        //listenerBotones();        
    }
    
    /*public void listenerBotones()
    {
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBuscar();
            }
        });
    }*/

    @Override
    public void listenerBotonBuscar() {
        listenerBuscar();
    }
    
    
    
    private void listenerBuscar()
    {
        try {
            java.sql.Date fechaInicio = UtilidadesFecha.castDateUtilToSql(getDateFechaInicio().getDate());
            java.sql.Date fechaFin = UtilidadesFecha.castDateUtilToSql(getDateFechaFin().getDate());
            //CategoriaProducto categoriaProducto=getcmb            
            utilidadReport=ServiceFactory.getFactory().getFacturacionServiceIf().consultaUtilidadVentas(fechaInicio,fechaFin,categoriaFiltro);
            llenarTabla();
            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(UtilidadReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void llenarTabla()
    {
        if(utilidadReport!=null)
        {
            getTblDocumentos().setModel(utilidadReport.obtenerModeloTabla());
            UtilidadesTablas.ocultarColumna(getTblDocumentos(),0);
            
            getLblSubtotalUtilidad().setText(utilidadReport.getTotalFormatStr(UtilidadReport.DatoEnum.SUBTOTAL));
            getLblCostoUtilidad().setText(utilidadReport.getTotalFormatStr(UtilidadReport.DatoEnum.COSTO));
            getTxtUtilidad().setText(utilidadReport.getTotalFormatStr(UtilidadReport.DatoEnum.UTILIDAD));
            //llenar utilidades
            /*ControladorReporteFactura.TotalSumatoria totalSumatoria=new ControladorReporteFactura.TotalSumatoria(
                    BigDecimal.ZERO, 
                    utilidadReport.getTotal(UtilidadReport.DatoEnum.), 
                    BigDecimal.ONE, 
                    BigDecimal.ONE
            );*/
            
       }
    }

    @Override
    public void nuevo(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar()  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir(){
        
        if (utilidadReport != null) {
            InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("utilidades.jrxml");

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

                    ReporteCodefac.generarReporteInternalFramePlantilla(path, panelPadre, utilidadReport);
                    //dispose();
                    //setVisible(false);
                }
            });

        }
            
    }

    @Override
    public void actualizar(){
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
    public Map<Integer, Boolean> permisosFormulario() 
    {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public void listenerTablas() {
        JPopupMenu jpopMenuItem=new JPopupMenu();
        JMenuItem itemCambiarCosto= new JMenuItem("Cambiar Costo");
        itemCambiarCosto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valor=JOptionPane.showInputDialog("Ingrese el costo nuevo:");
                BigDecimal costoNuevo=UtilidadBigDecimal.convertirTextoEnBigDecimal(valor);
                int filaSeleccionada=getTblDocumentos().getSelectedRow();
                if(filaSeleccionada>-1)
                {
                    try {
                        UtilidadResult dataReport= (UtilidadResult) getTblDocumentos().getValueAt(filaSeleccionada,0);
                        //dataReport.getFacturaDetalleId();
                        FacturaDetalle facturaDetalle= ServiceFactory.getFactory().getFacturaDetalleServiceIf().buscarPorId(dataReport.getFacturaDetalleId());
                        facturaDetalle.setCostoPromedio(costoNuevo);
                        ServiceFactory.getFactory().getFacturaDetalleServiceIf().editar(facturaDetalle);
                        listenerBuscar();
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(UtilidadReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(UtilidadReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
        });
        jpopMenuItem.add(itemCambiarCosto);
        getTblDocumentos().setComponentPopupMenu(jpopMenuItem);
    }

    
    
    
}
