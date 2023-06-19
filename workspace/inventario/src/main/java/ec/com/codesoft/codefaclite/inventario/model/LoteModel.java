/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.LoteControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.inventario.panel.LotePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwing;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class LoteModel extends LotePanel implements DialogInterfacePanel<Lote>,InterfazPostConstructPanel,ControladorVistaIf,LoteControlador.ISwing{
    
    private LoteControlador controlador;

    //TODO: Optimizar esta parte para 
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new LoteControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
        
        
        /*JTextField textField = (JTextField) getCmbFechaVencimiento().getComponent(0);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDatePicker(getCmbFechaVencimiento(), textField);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDatePicker(getCmbFechaVencimiento(), textField);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDatePicker(getCmbFechaVencimiento(), textField);
            }
        });*/
        
        //UtilidadesSwing.corregirFuncionamientoSeteoManualJxDate(getCmbFechaVencimiento());        
        UtilidadesSwing.corregirFuncionamientoSeteoManualJxDate(getCmbFechaVencimiento(),new UtilidadesSwing.SetearFechaIf() {
            @Override
            public void setear(java.util.Date fecha) 
            {
                controlador.getLote().setFechaVencimiento(UtilidadesFecha.castDateUtilToSql(fecha));
            }
        });
    }
    
    /*private void updateDatePicker(JXDatePicker datePicker, JTextField textField) {
        String text = textField.getText();
        if(!UtilidadesTextos.verificarNullOVacio(text))
        {
            //DateFormat dateFormat = datePicker.getFormats()[0];
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date date = dateFormat.parse(text);
                //datePicker.setDate(date);
                //datePicker.getEditor().commitEdit();
                java.sql.Date fechaSql= UtilidadesFecha.castDateUtilToSql(date);
                controlador.getLote().setFechaVencimiento(fechaSql);
                System.out.println("Seteado fecha: "+date);
            } catch (Exception ex) {
                ex.printStackTrace();
                // Manejar el error aquí si el formato es inválido
                System.out.println("error verificando la fecha: >>"+text+"<<");
            }
        }
    }*/
    
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        
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
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    public LoteControlador getControlador() {
        return controlador;
    }

    public void setControlador(LoteControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    @Override
    public void limpiarPantalla() {
        limpiar();
    }

    @Override
    public Lote getResult() throws ExcepcionCodefacLite {
        try {
            controlador.grabar();
            return controlador.getLote();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(LoteModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (RemoteException ex) {
            Logger.getLogger(LoteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        
        if(parametros[0]!=null)
        {
            Producto productoTmp=(Producto) parametros[0];
            controlador.getLote().setProducto(productoTmp);            
        }
        actualizarBindingCompontValues();
    }
    
}
