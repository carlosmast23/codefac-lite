/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaSesionModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AbrirCajaModel extends CajaSessionModel
{

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar();
        getjTextValorCierreTeorico().setEnabled(false);
        
        //Buscar el valor de cierre anterior para dejar ingresando 
        //agregarListenerComboBox();                
        
    }
    
    private void agregarListenerComboBox()
    {
        getjCmbCajaPermiso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Caja cajaSeleccionada= (Caja) getjCmbCajaPermiso().getSelectedItem();
                if(cajaSeleccionada!=null)
                {
                    try {
                        CajaSession cajaSession=ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerUltimaCajaSession(cajaSeleccionada);
                        getjTextValorApertura().setText(cajaSession.getValorCierreReal()+"");
                    } catch (RemoteException ex) {
                        Logger.getLogger(AbrirCajaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
        );
        
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

    @Override
    public CajaSesionModelControlador.TipoProcesoCajaEnum getTipoProcesoEnum() {
        return CajaSesionModelControlador.TipoProcesoCajaEnum.APERTURA_CAJA; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
