/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.FacturaReembolsoPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraFacturaReembolso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FacturaReembolsoModel extends FacturaReembolsoPanel implements DialogInterfacePanel<CompraFacturaReembolso>{
    
    private CompraFacturaReembolso reembolso;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarCombosBox();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        getTxtBaseImpExeReemb().setText("0");
        getTxtBaseImpGravReemb().setText("0");
        getTxtBaseImponibleReemb().setText("0");
        getTxtBaseNoGraIvaReemb().setText("0");
        getTxtMontoIceRemb().setText("0");
        getTxtMontoIvaRemb().setText("0");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CompraFacturaReembolso getResult() throws ExcepcionCodefacLite {
        CompraFacturaReembolso reembolso= getObjectBuild();
        return reembolso;
    }
    
    public CompraFacturaReembolso getObjectBuild()
    {
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=(Persona.TipoIdentificacionEnum) getCmbTipoIdentificacion().getSelectedItem();
        CompraFacturaReembolso reembolso=new CompraFacturaReembolso();
        reembolso.setTipoComprobanteReemb("18");
        reembolso.setTpIdProvReemb(tipoIdentificacionEnum.getCodigoSriCompra());
        reembolso.setIdProvReemb(getTxtIdentificacion().getText());
        reembolso.setEstablecimientoReemb(getTxtEstablecimientoCompra().getText());
        reembolso.setPuntoEmisionReemb(getTxtPuntoEmisionCompra().getText());
        reembolso.setSecuencialReemb(getTxtSecuencialCompra().getText());
        reembolso.setFechaEmisionReemb(UtilidadesFecha.castDateUtilToSql(getCmbFechaCompra().getDate()));
        reembolso.setAutorizacionReemb(getTxtAutorizacion().getText());
        reembolso.setBaseImponibleReemb(new BigDecimal(getTxtBaseImponibleReemb().getText()));
        reembolso.setBaseImpGravReemb(new BigDecimal(getTxtBaseImpGravReemb().getText()));
        reembolso.setBaseNoGraIvaReemb(new BigDecimal(getTxtBaseNoGraIvaReemb().getText()));
        reembolso.setBaseImpExeReemb(new BigDecimal(getTxtBaseImpExeReemb().getText()));
        reembolso.setMontoIceRemb(new BigDecimal(getTxtMontoIceRemb().getText()));
        reembolso.setMontoIvaRemb(new BigDecimal(getTxtMontoIvaRemb().getText()));
        return reembolso;
        
    }
    private void cargarCombosBox()
    {
        getCmbTipoIdentificacion().removeAllItems();
        for (Persona.TipoIdentificacionEnum tipoIdentificacion : Persona.TipoIdentificacionEnum.values()) {
            getCmbTipoIdentificacion().addItem(tipoIdentificacion);
        }
    }
    
}
