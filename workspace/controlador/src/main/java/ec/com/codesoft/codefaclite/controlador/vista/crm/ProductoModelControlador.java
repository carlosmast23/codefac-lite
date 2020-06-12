/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.crm;

import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoModelControlador extends ModelControladorAbstract {
    
    private ProductoModelControladorInterface interfaz;

    public ProductoModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session,ProductoModelControladorInterface interfaz) {
        super(mensajeVista, session);
        this.interfaz=interfaz;
    }
    
    
    
    public void iniciarCombosBox()
    {
        try {
            //Agregar las categorias disponibles
            CategoriaProductoServiceIf catProdService = ServiceFactory.getFactory().getCategoriaProductoServiceIf();
            List<CategoriaProducto> catProdList;
            catProdList = catProdService.obtenerTodosPorEmpresa(session.getEmpresa());
            interfaz.llenarCmbCategoriaProducto(catProdList);

            //Agregar los tipos de producto
            interfaz.llenarCmbTipoProducto(TipoProductoEnum.values());

            //Agregar las opcoiones segun los modulos habilitados
            if (session.getModulos().contains(ModuloCodefacEnum.INVENTARIO) || ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO)) {
                interfaz.llenarCmbManejaInventario(new EnumSiNo[]{EnumSiNo.SI, EnumSiNo.NO});
            } else {
                interfaz.llenarCmbManejaInventario(new EnumSiNo[]{EnumSiNo.NO});
            }

            //Cargar los estados para generar los codigos de barras
            //interfaz.llenarCmbGenerarCodigoBarras(new EnumSiNo[]{EnumSiNo.NO, EnumSiNo.SI});
            
            ImpuestoServiceIf impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
            ImpuestoDetalleServiceIf impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
            
            List<ImpuestoDetalle> impuestoDetalleList = impuestoDetalleService.obtenerIvaVigente();
            ImpuestoDetalle impuestoDefault = null;
            
            String ivaDefecto = ParametrosSistemaCodefac.IVA_DEFECTO; //TODO: Analizar que configuracion vamos a usar por defecto agrego esto para poder usas cuando aun no se ha configurado un iva
            ParametroCodefac parametroIva = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO);
            if (parametroIva != null) {
                ivaDefecto = parametroIva.valor;
            }
            
            List<ImpuestoDetalle> listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
            for (ImpuestoDetalle impuesto : impuestoDetalleList) {
                if (impuesto.getTarifa().toString().equals(ivaDefecto)) {
                    impuestoDefault = impuesto;
                }
                listaImpuestosDetalle.add(impuesto);
            }
            interfaz.llenarComboIva(impuestoDetalleList);
            interfaz.seleccionarComboIva(impuestoDefault);
            
            listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
            Impuesto ice = impuestoService.obtenerImpuestoPorCodigo(Impuesto.ICE);
            for (ImpuestoDetalle impuesto : ice.getDetalleImpuestos()) {
                listaImpuestosDetalle.add(impuesto);
            }
            interfaz.llenarComboIce(impuestoDetalleList);
            //getComboIce().setEditable(true);
            //getComboIce().setSelectedItem("Seleccione : ");
            
            listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
            Impuesto irbpnr = impuestoService.obtenerImpuestoPorCodigo(Impuesto.IRBPNR);
            for (ImpuestoDetalle impuesto : irbpnr.getDetalleImpuestos()) {
                listaImpuestosDetalle.add(impuesto);
            }
            interfaz.llenarComboIrbpnr(impuestoDetalleList);

            //Agregar combo de garantia
            interfaz.llenarCmbGarantia(EnumSiNo.values());
            //getComboIrbpnr().setEditable(true);
            //getComboIrbpnr().setSelectedItem("Seleccione: ");
            
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public interface ProductoModelControladorInterface
    {
        public void llenarCmbManejaInventario(EnumSiNo[] datos);
        //public void llenarCmbGenerarCodigoBarras(EnumSiNo[] datos);
        public void llenarCmbTipoProducto(TipoProductoEnum[] tipoProductoList);
        public void llenarCmbCategoriaProducto(List<CategoriaProducto> catProdList);
        public void llenarComboIva(List<ImpuestoDetalle> impuestos);
        public void llenarComboIce(List<ImpuestoDetalle> impuestos);
        public void llenarComboIrbpnr(List<ImpuestoDetalle> impuestos);
        public void llenarCmbGarantia(EnumSiNo[] datos);
        
        public void seleccionarComboIva(ImpuestoDetalle impuesto);
    }
}
