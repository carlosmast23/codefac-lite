/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Desarrollo
 */
public class CerrarCajaModel extends CajaSessionModel
{
    private CajaSession cajaSessionA = null;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        //super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        //super.cicloVida=false;
        //super.validacionDatosIngresados=false;
        cajaSessionA = null;
        limpiar();
        getjTextValorApertura().setEnabled(false);
        getjCmbCajaPermiso().removeAllItems();
        getjComboBoxEstadoCierre().removeAllItems();
        cargarDatos();
        addListenerCombo();
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        //super.cicloVida=false;
        //super.validacionDatosIngresados = false;
        
        if(cajaSessionA != null)
        {
            try {
                ServiceFactory.getFactory().getCajaSesionServiceIf().editar(cajaSessionA);
                //Mensaje
                DialogoCodefac.mensaje("Correcto","La caja se ha cerrado", DialogoCodefac.MENSAJE_CORRECTO);              
                this.iniciar();
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }

    @Override
    public void limpiar() {
        getjTextFechaApertura().setText("");
        getjTextFechaCierre().setText("");
        getjTextHoraApertura().setText("");
        getjTextHoraCierre().setText("");
        getjTextValorApertura().setText("");
        getjTextValorCierre().setText("");
    }
   
    private void cargarDatos()
    {
        List<Caja> cajas = new ArrayList<>();
        try {
            List<CajaSession> cajasSession = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuarioYSucursal(this.session.getUsuario(), this.session.getSucursal());
            if(cajasSession.size()>0)
            {
                cajasSession.forEach(cajaSession -> {
                    cajas.add(cajaSession.getCaja());
                });
            }            
        } catch (RemoteException ex) {
            Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        UtilidadesComboBox.llenarComboBox(getjCmbCajaPermiso(), cajas);
        UtilidadesComboBox.llenarComboBox(getjComboBoxEstadoCierre(), CajaSessionEnum.values()); 
    }
    
    public void addListenerCombo(){
        getjCmbCajaPermiso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Caja caja = (Caja) getjCmbCajaPermiso().getSelectedItem();
                if(caja != null)
                {
                    try {
                        List<CajaSession> cajasSession = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuarioYSucursal(session.getUsuario(), session.getSucursal());
                        if(cajasSession.size()>0)
                        {
                            cajasSession.forEach(cajaSession ->{
                                if(cajaSession.getCaja().equals(caja)){
                                    cajaSessionA = cajaSession;
                                    cargarDatosPantalla(cajaSessionA);
                                }
                            });
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
}
