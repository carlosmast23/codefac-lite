/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaSesionModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

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
