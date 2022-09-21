/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.inventario.busqueda.CatalogoProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.CatalogoProductoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class CatalogoProductoModel extends CatalogoProductoPanel implements DialogInterfacePanel<CatalogoProducto>,InterfazPostConstructPanel{
    
    private CatalogoProducto catalogoProducto;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        valoresIniciales();
        listenerCombos();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearVariables();
            catalogoProducto=ServiceFactory.getFactory().getCatalogoProductoServiceIf().grabar(catalogoProducto);
            DialogoCodefac.mensaje("Correcto","Los datos se grabaron correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error al grabar los datos",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CatalogoProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","No existe comunicacion con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CatalogoProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearVariables();
            CatalogoProductoServiceIf servicio=ServiceFactory.getFactory().getCatalogoProductoServiceIf();
            servicio.editar(catalogoProducto);
            DialogoCodefac.mensaje("Correcto","Los datos fueron editados correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Los datos fueron editados correctamente",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CatalogoProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CatalogoProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        CatalogoProductoBusquedaDialogo busquedDialog = new CatalogoProductoBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedDialog);
        buscarDialogoModel.setVisible(true);
        CatalogoProducto catalogoProductoTemp= (CatalogoProducto) buscarDialogoModel.getResultado();
        
        if(catalogoProductoTemp==null)
        {
            throw new ExcepcionCodefacLite("Cancelar buscar");
        }
        else
        {
            catalogoProducto=catalogoProductoTemp;
            cargarDatos();
        }
        
    }
    
    private void cargarDatos()
    {
        getCmbModulo().setSelectedItem(catalogoProducto.getModuloCodefacEnum());
        getCmbCategoriaProducto().setSelectedItem(catalogoProducto.getCategoriaProducto());
        getTxtNombre().setText(catalogoProducto.getNombre());
        getComboIva().setSelectedItem(catalogoProducto.getIva());
        getComboIrbpnr().setSelectedItem(catalogoProducto.getIrbpnr());
        getComboIce().setSelectedItem(catalogoProducto.getIce());   
        getCmbCatalogoTipo().setSelectedItem(catalogoProducto.getTipoCodEnum());
    }

    @Override
    public void limpiar() {
        limpiarVariables();
        if(getCmbModulo().getModel().getSize()>0)
        {
            getCmbModulo().setSelectedIndex(0);
        }
        
        if(getCmbCatalogoTipo().getModel().getSize()>0)
        {
            getCmbCatalogoTipo().setSelectedIndex(0);
        }
        
        if(getCmbCategoriaProducto().getModel().getSize()>0)
        {
            getCmbCategoriaProducto().setSelectedIndex(0);
        }
        
        getComboIva().setSelectedIndex(0);
        
        getComboIce().setEditable(true);
        getComboIce().setSelectedItem("Seleccione : ");
        
        getComboIrbpnr().setEditable(true);
        getComboIrbpnr().setSelectedItem("Seleccione: ");
        //getCmbCatalogoTipo().setSelectedIndex(0);
    }

//    @Override
    public String getNombre() {
        return "Catalogo Producto";
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
        //permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void valoresIniciales() {
        try {
            getCmbModulo().removeAllItems();
            getCmbModulo().addItem(ModuloCodefacEnum.INVENTARIO);
            getCmbModulo().addItem(ModuloCodefacEnum.GESTIONA_ACADEMICA);
            getCmbModulo().addItem(ModuloCodefacEnum.SERVICIOS);
            //for (TipoProductoEnum tipo : tipos) {
            //    getCmbModulo().addItem(tipo);
            //}
            
            //Agregar las categorias disponibles
            getCmbCategoriaProducto().removeAllItems();
            CategoriaProductoServiceIf catProdService=ServiceFactory.getFactory().getCategoriaProductoServiceIf();
            List<CategoriaProducto> catProdList = catProdService.obtenerTodos();
            for (CategoriaProducto cat : catProdList) {
                getCmbCategoriaProducto().addItem(cat);
            }
            
             
            getComboIva().removeAllItems();
            getComboIce().removeAllItems();
            getComboIrbpnr().removeAllItems();
            
            ImpuestoServiceIf impuestoService=ServiceFactory.getFactory().getImpuestoServiceIf();
            ImpuestoDetalleServiceIf impuestoDetalleService=ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
            
            List<ImpuestoDetalle> impuestoDetalleList = impuestoDetalleService.obtenerIvaVigente();
            ImpuestoDetalle impuestoDefault = null;
            String ivaDefecto =ParametrosSistemaCodefac.IVA_DEFECTO; //TODO: Analizar que configuracion vamos a usar por defecto agrego esto para poder usas cuando aun no se ha configurado un iva
            ParametroCodefac parametroIva = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO);
            if(parametroIva!=null)
            {
                ivaDefecto=parametroIva.valor;
            }
            for (ImpuestoDetalle impuesto : impuestoDetalleList) {
                if (impuesto.getTarifa().toString().equals(ivaDefecto)) {
                    impuestoDefault = impuesto;
                }
                getComboIva().addItem(impuesto);
            }
            getComboIva().setSelectedItem(impuestoDefault);
            
            Impuesto ice = impuestoService.obtenerImpuestoPorCodigo(Impuesto.ICE);
            for (ImpuestoDetalle impuesto : ice.getDetalleImpuestos()) {
                getComboIce().addItem(impuesto);
            }
            getComboIce().setEditable(true);
            getComboIce().setSelectedItem("Seleccione : ");
            
            Impuesto irbpnr = impuestoService.obtenerImpuestoPorCodigo(Impuesto.IRBPNR);
            for (ImpuestoDetalle impuesto : irbpnr.getDetalleImpuestos()) {
                getComboIrbpnr().addItem(impuesto);
            }
            getComboIrbpnr().setEditable(true);
            getComboIrbpnr().setSelectedItem("Seleccione: ");
            

        } catch (RemoteException ex) {
            Logger.getLogger(CatalogoProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limpiarVariables() {
        catalogoProducto=new CatalogoProducto();
    }

    private void setearVariables() {
        CategoriaProducto categoriaProducto=(CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        catalogoProducto.setCategoriaProducto(categoriaProducto);
        
        
        if(getComboIce().getSelectedItem()!=null && !getComboIce().getSelectedItem().getClass().equals(String.class))
        {
            ImpuestoDetalle ice= (ImpuestoDetalle) getComboIce().getSelectedItem();
            catalogoProducto.setIce(ice);
        }
        
        if(getComboIrbpnr().getSelectedItem()!=null && !getComboIrbpnr().getSelectedItem().getClass().equals(String.class))
        {
            ImpuestoDetalle ibpnr=(ImpuestoDetalle) getComboIrbpnr().getSelectedItem();
            catalogoProducto.setIrbpnr(ibpnr);
        }
        
        CatalogoProducto.TipoEnum tipoEnum=(CatalogoProducto.TipoEnum) getCmbCatalogoTipo().getSelectedItem();
        catalogoProducto.setTipoCod((tipoEnum!=null)?tipoEnum.getCodigo():"");
        
        ImpuestoDetalle iva= (ImpuestoDetalle) getComboIva().getSelectedItem();
        catalogoProducto.setIva(iva);
        
        catalogoProducto.setNombre(getTxtNombre().getText());
        
        ModuloCodefacEnum moduloEnum=(ModuloCodefacEnum) getCmbModulo().getSelectedItem();
        catalogoProducto.setModuloCod(moduloEnum.getCodigo());
        
        catalogoProducto.setEmpresa(session.getEmpresa());
    }

    @Override
    public CatalogoProducto getResult() throws ExcepcionCodefacLite {
        try {
            grabar();
            return catalogoProducto;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(CatalogoProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }
    
    @Override
    public void postConstructorExterno(Object[] parametros)
    {
        ModuloCodefacEnum modulo=(ModuloCodefacEnum) parametros[0];
        getCmbModulo().setSelectedItem(modulo);
        //DialogoCodefac.mensaje("adasd","post contructor",1);
        //repaint();
    }

    private void listenerCombos() {
        
        getCmbModulo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModuloCodefacEnum modulo=(ModuloCodefacEnum) getCmbModulo().getSelectedItem();
                List<CatalogoProducto.TipoEnum> tipos=CatalogoProducto.TipoEnum.obtenerPorModulo(modulo);
                
                getCmbCatalogoTipo().removeAllItems();
                for (CatalogoProducto.TipoEnum tipo : tipos) {
                    getCmbCatalogoTipo().addItem(tipo);
                }
            }
        });        

    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
