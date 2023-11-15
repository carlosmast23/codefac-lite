/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ComandaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ComandaModel extends FacturacionModel{

    public ComandaModel() 
    {
        super();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.iniciar();
        getPanelFormasPago().setVisible(false);
        getjPanelDescuentoGlobal().setVisible(false);
        getChkFacturaReembolso().setVisible(false);
        getPanelDatosAdicionales().setVisible(false);
        getPanelValores().setVisible(false);
        getPnlVuelto().setVisible(false);
        getChkReserva().setVisible(false);
        getCmbPreciosVenta().setVisible(false);
        getLblUnidad().setVisible(false);
        getCmbPresentacionProducto().setVisible(false);
        getLblDescuento().setVisible(false);
        getCmbDescuento().setVisible(false);
        getCheckPorcentaje().setVisible(false);
        
        
        Font fuentePersonalizada = new Font("Arial", Font.BOLD, 50); 
        getBtnAgregarDetalleFactura().setFont(fuentePersonalizada);
        
        
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.COMANDA);
        
        
        //getPanelTabDatos().setEnabledAt(4,true);
        //getPanelTabDatos().setSelectedIndex(4);
        activarTabDatos(4);
        cargarDatosIniciales();
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        super.nuevo(); //To change body of generated methods, choose Tools | Templates.
        //Setear el número de orden que tenga pendiente
        getSpnNumeroOrdenComanda().setValue(Factura.numeroOrdenComanda);
    }
    
    @Override
    public InterfaceModelFind getBusquedaInterface()
    {
        /*FacturaBusqueda facturaBusqueda = null;
        ParametroCodefac siNofiltrarFacturaPorUsuario = session.getParametrosCodefac().get(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO);
        EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra((siNofiltrarFacturaPorUsuario != null ) ? siNofiltrarFacturaPorUsuario.getValor() : null);
        if(enumSiNo != null && enumSiNo.equals(EnumSiNo.SI)){
            facturaBusqueda = new FacturaBusqueda(session.getSucursal(), session.getUsuario());
        }else{
            facturaBusqueda = new FacturaBusqueda(session.getSucursal());
        }
        
        return facturaBusqueda;*/
        return new ComandaBusquedaDialogo(session.getEmpresa());
    }
    
    private void cargarDatosIniciales()
    {
        try {
            List<Mesa> mesaList=ServiceFactory.getFactory().getMesaSeviceIf().obtenerTodosActivos(session.getEmpresa());
            
            UtilidadesComboBox.llenarComboBox(this.getCmbMesaComanda(),mesaList);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComandaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComandaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
}
