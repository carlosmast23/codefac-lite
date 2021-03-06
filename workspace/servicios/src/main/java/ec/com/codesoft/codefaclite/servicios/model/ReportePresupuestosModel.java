/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.data.PresupuestoReporteData;
import ec.com.codesoft.codefaclite.servicios.data.PresupuestoReporteDetalleData;
import ec.com.codesoft.codefaclite.servicios.panel.ReportePresupuestosPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ReportePresupuestosModel extends ReportePresupuestosPanel {

    private Persona cliente;
    private List<PresupuestoReporteData> presupuestosData;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        listenerCheckBox();
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
        InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("presupuestosReporteHorizontal.jrxml");
        InputStream pathReporteAgrupado = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("presupuestosReporteHorizontalDetalles.jrxml");


        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    if(getChkReporteDetallesCompras().isSelected())
                    {   
                        excel.gestionarIngresoInformacionExcel(getTituloTablaPantallaDetalle(), getReporteDetalleData(presupuestosData));
                    }
                    else
                    {
                        excel.gestionarIngresoInformacionExcel(getTituloTablaPantalla(), presupuestosData);
                    }
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "Error al general el archivo excel : \n" + exc.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                if(getChkReporteDetallesCompras().isSelected())
                {                    
                    ReporteCodefac.generarReporteInternalFramePlantilla(pathReporteAgrupado, new HashMap(),getReporteDetalleData(presupuestosData), panelPadre, "Reporte Presupuestos Detalle",OrientacionReporteEnum.HORIZONTAL);
                }
                else
                {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, new HashMap(), presupuestosData, panelPadre, "Reporte Presupuestos",OrientacionReporteEnum.HORIZONTAL);
                }
            }
        });
    }
    
    public List<PresupuestoReporteDetalleData> getReporteDetalleData(List<PresupuestoReporteData> presupuestosData)
    {
        List<PresupuestoReporteDetalleData> detalleReporteData=new  ArrayList<PresupuestoReporteDetalleData>();
        for (PresupuestoReporteData data : presupuestosData) {
            for (PresupuestoDetalle presupuestoDetalle : data.getPresupuesto().getPresupuestoDetalles()) {
                PresupuestoReporteDetalleData dataDetalle=new PresupuestoReporteDetalleData(data);
                dataDetalle.setProveedor(presupuestoDetalle.getPersona().getNombreSimple());
                dataDetalle.setProducto(presupuestoDetalle.getProducto().getNombre());
                dataDetalle.setPrecioCompra(presupuestoDetalle.getPrecioCompra());
                dataDetalle.setDescuentoCompra(presupuestoDetalle.getDescuentoCompra());
                dataDetalle.setCantidadCompra(presupuestoDetalle.getCantidad());
                dataDetalle.setTotalCompra(presupuestoDetalle.calcularTotalCompra());
                detalleReporteData.add(dataDetalle);
            }
        }
        return detalleReporteData;
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        getCmbEstado().removeAllItems();
        for (Presupuesto.EstadoEnum estadoEnum : Presupuesto.EstadoEnum.values()) {
            getCmbEstado().addItem(estadoEnum);
        }
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true); //Habilito el boton de buscar
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerBotones() {

        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                cliente = ((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                if (cliente != null) {
                    setearValoresCliente();
                }
            }

        });

        getBtnLimpiarFechaInicial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbFechaInicial().setDate(null);
            }
        });

        getBtnLimpiarFechaFinal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbFechaFinal().setDate(null);
            }
        });

        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Presupuesto.EstadoEnum estadoEnum=(getCmbEstado().isEnabled())?(Presupuesto.EstadoEnum) getCmbEstado().getSelectedItem():null;
                    List<Presupuesto> presupuestos = ServiceFactory.getFactory().getPresupuestoServiceIf().consultarPresupuestos(
                            getCmbFechaInicial().getDate(),
                            getCmbFechaFinal().getDate(),
                            cliente,
                            estadoEnum);

                    construirDataReporte(presupuestos);
                    mostrarDatosTabla();
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ReportePresupuestosModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReportePresupuestosModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void setearValoresCliente() {
        getTxtCliente().setText(cliente.toString());
    }

    private void construirDataReporte(List<Presupuesto> lista) {
        presupuestosData = new ArrayList<PresupuestoReporteData>();
        for (Presupuesto presupuesto : lista) {
            PresupuestoReporteData presupuestoData = new PresupuestoReporteData();
            presupuestoData.setCodigo(presupuesto.getId().toString());
            presupuestoData.setOrdenTrabajo(presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getId().toString());
            presupuestoData.setFecha(presupuesto.getFechaPresupuesto().toString());
            presupuestoData.setEstado(presupuesto.getEstadoEnum().getNombre());
            presupuestoData.setDescripcion(presupuesto.getDescripcion());
            presupuestoData.setIdentificacion(presupuesto.getPersona().getIdentificacion());
            presupuestoData.setNombres(presupuesto.getPersona().getRazonSocial());
            
            Presupuesto.ResultadoTotales totales=presupuesto.obtenerMapReporteTotales(session.getEmpresa().getIdentificacion());
            presupuestoData.setTotal(totales.valorPagarCliente);
            presupuestoData.setCompras(totales.valoresProveedores);
            presupuestoData.setProduccionInterna(totales.produccionInterna);
            presupuestoData.setUtilidad(totales.utilidad);
            
            
            presupuestoData.setPresupuesto(presupuesto);
            presupuestosData.add(presupuestoData);
        }

    }
    

    private String[] getTituloTablaPantalla() {
        String[] titulo = {"Código","Orden T.", "Identificación", "Fecha", "Estado", "Razon Social", "Descripción", "Ventas","Compras","Interno","Utilidad"};
        return titulo;
    }
    
    private String[] getTituloTablaPantallaDetalle() {
        String[] titulo = {"Proveedor","Producto","Prec Compra","Desc Compra","Cant Compra","Tot Compra","Código","Orden T.", "Identificación", "Fecha", "Estado", "Razon Social", "Descripción", "Ventas","Compras","Interno","Utilidad"};
        return titulo;
    }

    private void mostrarDatosTabla() {

        String[] titulo = getTituloTablaPantalla();
        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0);

        if (presupuestosData != null) {
            for (PresupuestoReporteData presupuestoData : presupuestosData) {
                modeloTabla.addRow(new String[]{
                    presupuestoData.getCodigo(),
                    presupuestoData.getOrdenTrabajo(),
                    presupuestoData.getIdentificacion(),
                    presupuestoData.getFecha(),
                    presupuestoData.getEstado(),
                    presupuestoData.getNombres(),
                    presupuestoData.getDescripcion(),
                    presupuestoData.getTotal().toString(),
                    presupuestoData.getCompras().toString(),
                    presupuestoData.getProduccionInterna().toString(),
                    presupuestoData.getUtilidad().toString(),
                });

            }
        }

        getTblDatos().setModel(modeloTabla);
    }

    private void listenerCheckBox() {
        getChkTodosClientes().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    cliente = null;
                    getTxtCliente().setText("");                    
                    getBtnBuscarCliente().setEnabled(false);
                } else {
                    getBtnBuscarCliente().setEnabled(true);
                }
            }
        });
        
        getChkTodosEstados().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected                    
                    getCmbEstado().setEnabled(false);
                } else {
                    getCmbEstado().setEnabled(true);
                }
            }
        });
    }

}
