/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.prestamos.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.PrestamoDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.prestamos.panel.PrestamoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoCuota;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
 ;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class PrestamoModel extends PrestamoPanel{

    private Prestamo prestamo;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite   {
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
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new PrestamoDialogo();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        prestamo=(Prestamo) entidad;
        getCmbFecha().setDate(prestamo.getFechaCreacion());
        getTxtCliente().setText(prestamo.getCliente().getRazonSocial());
        if(prestamo.getVenta()!=null)
        {
            getTxtFactura().setText(prestamo.getVenta().getPreimpreso());
        }
        
        getTxtCuotaInicial().setText(prestamo.getCuotaInicial().toString());
        getTxtCuotaMensual().setText(prestamo.calcularCuotaMensual().toString());
        getTxtDiaPago().setText(prestamo.getDiaPago().toString());
        getTxtPlazo().setText(prestamo.getPlazo().toString());
        getTxtValorFinaciamiento().setText(prestamo.getCapital().toString());
        getTxtValorTotal().setText(prestamo.getTotalPrestamo().toString());
        
        crearTablaCuotas();
    }
    
    
    private void crearTablaCuotas()
    {
        try {
            String[] titulo={"Número Cuota","Fecha Pago"," Valor"};
            DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
            
            List<PrestamoCuota> cuotas= ServiceFactory.getFactory().getPrestamoServiceIf().buscarCuotasPorPrestamo(prestamo);
            
            for (PrestamoCuota cuota : cuotas) {
                modeloTabla.addRow(new String[]{                    
                    cuota.getNumeroCuota().toString(),
                    cuota.getFechaPagoGenerado().toString(),
                    cuota.getValorCuota().toString()
                });
            }
            
            getTblCuotas().setModel(modeloTabla);
            
        }catch (ServicioCodefacException ex) {
            Logger.getLogger(PrestamoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
