/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ActividadPresupuestoData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReporteActividadesPresupuestoModel extends ReportePresupuestosModel{

    private ReportDataAbstract<ActividadPresupuestoData> reportResult;
    
    @Override
    protected void ejecutarConsulta(Presupuesto.EstadoEnum estadoEnum, Date fechaInicial, Date fechaFinal, Persona cliente, String placa,Usuario usuario) throws ServicioCodefacException, RemoteException {
        reportResult=ServiceFactory.getFactory().getPresupuestoServiceIf().consultarActividadesPresupuesto(fechaInicial, fechaFinal, cliente,usuario, placa, estadoEnum);
        mostrarDatosTabla();
         
    }
    
    @Override
    public String[] getTituloTablaPantalla() {
        String[] titulo = {"Orde T", "Usuario","Fecha","Tarea","Terminado"};
        return titulo;
    }

    @Override
    protected void mostrarDatosTabla() {
        String[] titulo = getTituloTablaPantalla();
        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0);

        if (reportResult != null && reportResult.getDetalleList()!=null) 
        {
            for (ActividadPresupuestoData data : reportResult.getDetalleList()) 
            {
                modeloTabla.addRow(new String[]{
                    data.getCodigoOrdenTrabajo(),
                    data.getUsuario(),
                    data.getFecha(),
                    data.getTarea(),
                    data.getTerminado()
                });

            }
        }

        getTblDatos().setModel(modeloTabla);
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("presupuestosActividades.jrxml");


        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel(); 
                    excel.gestionarIngresoInformacionExcel(getTituloTablaPantalla(), reportResult.getDetalleList());
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "Error al general el archivo excel : \n" + exc.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                //ReporteCodefac.generarReporteInternalFramePlantilla(path, new HashMap(), presupuestosData, panelPadre, "Reporte Presupuestos",OrientacionReporteEnum.HORIZONTAL);
                ReporteCodefac.generarReporteInternalFramePlantilla(path,panelPadre, reportResult);
            }
        });
    }
    
    
    
    
}
