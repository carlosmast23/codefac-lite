/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.UtilidadPrecioModelControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.inventario.panel.UtilidadPrecioPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadPrecioModel extends UtilidadPrecioPanel implements DialogInterfacePanel,InterfazPostConstructPanel, ControladorVistaIf,UtilidadPrecioModelControlador.SwingIf{

    
    private UtilidadPrecioModelControlador controlador;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException 
    {
        verificarConfiguracionInicial();
        this.controlador = new UtilidadPrecioModelControlador(DialogoCodefac.intefaceMensaje, session,this, UtilidadPrecioModelControlador.TipoVista.ESCRITORIO);
        listenerTablas();
        crearModeloTabla();
    }
    
    private void verificarConfiguracionInicial()
    {
        if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.RECALCULA_PRECIO_PORCENTAJE,EnumSiNo.SI))
        {
            getChkEditarValoresDirectos().setSelected(false);
        }
    }
    
    private void listenerCargarPreciosOriginal(ProductoPrecioDataTable productoData)
    {
        if(productoData==null)
        {
            getTblPreciosOriginal().setModel(new DefaultTableModel());
        }
        
        BigDecimal costo=productoData.costoCalculo;
        DefaultTableModel tableModel = UtilidadesTablas.crearModeloTabla(new String[]{"Nombre","Costo","Original","Nuevo","%"}, new Class[]{String.class,String.class,String.class,String.class,String.class});
        List<Producto.PrecioVenta> precioList= productoData.producto.obtenerPreciosVenta();
        
        BigDecimal porcentajeCosto=BigDecimal.ZERO;
        BigDecimal precioEditado=BigDecimal.ZERO;
        Integer indice=0;
        for (Producto.PrecioVenta precioVenta : precioList) 
        {
            indice++;
            
            switch(indice)
            {
                case 1:
                    precioEditado=productoData.pvp1;
                    porcentajeCosto=UtilidadesPorcentajes.calcularPorcentajeDosValores(productoData.pvp1.subtract(costo), costo);
                    break;

                case 2:
                    precioEditado=productoData.pvp2;
                    porcentajeCosto=UtilidadesPorcentajes.calcularPorcentajeDosValores(productoData.pvp2.subtract(costo), costo);
                    break;

                case 3:
                    precioEditado=productoData.pvp3;
                    porcentajeCosto=UtilidadesPorcentajes.calcularPorcentajeDosValores(productoData.pvp3.subtract(costo), costo);
                    break;

                case 4:
                    precioEditado=productoData.pvp4;
                    porcentajeCosto=UtilidadesPorcentajes.calcularPorcentajeDosValores(productoData.pvp4.subtract(costo), costo);
                    break;

                case 5:
                    precioEditado=productoData.pvp5;
                    porcentajeCosto=UtilidadesPorcentajes.calcularPorcentajeDosValores(productoData.pvp5.subtract(costo), costo);
                    break;

                case 6:
                    precioEditado=productoData.pvp6;
                    porcentajeCosto=UtilidadesPorcentajes.calcularPorcentajeDosValores(productoData.pvp6.subtract(costo), costo);
                    break;
            
            }
            
            String porcentajeStr="";
            
            if(porcentajeCosto!=null)
            {
                porcentajeStr=porcentajeCosto.setScale(0, RoundingMode.HALF_UP)+"%";
            }
            
            String precioEditadoStr="";
            if(precioEditado!=null)
            {
                precioEditadoStr=precioEditado.setScale(2, RoundingMode.HALF_UP)+"";
            }
            
            Object[] fila={precioVenta.alias,productoData.costoCalculo.setScale(2, RoundingMode.HALF_UP),precioVenta.precio.setScale(2, RoundingMode.HALF_UP)+"",precioEditadoStr,porcentajeStr};
            tableModel.addRow(fila);
        }
        
        getTblPreciosOriginal().setModel(tableModel);
    }
    
    private void listenerTablas()
    {
        getTblProductos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                int filaSeleccionada=getTblProductos().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    ProductoPrecioDataTable productoData=(ProductoPrecioDataTable) getTblProductos().getValueAt(filaSeleccionada,0);
                    
                    listenerCargarPreciosOriginal(productoData);
                }
            }
        });        
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    public UtilidadPrecioModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(UtilidadPrecioModelControlador controlador) {
        this.controlador = controlador;
    }
    
    
    //////////////////// METODOS ADICIONALES ////////////////////////////
    
    public void crearModeloTabla()
    {   
        String titulo[]=new String[]
        {
            "Objeto",
            "Seleccion",
            "Código",
            "Nombre Producto",
            "Costo U",
            "CU+IVA ",
            "Costo P",
            "CP+IVA ",
            "Pvp1",
            "Pvp2",
            "Pvp3",
            "Pvp4",
            "Pvp5",
            "Pvp6"
        };
        
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(
                titulo, 
                new Class[]{
                    Object.class,
                    Boolean.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                }
        );
        
         getTblProductos().setModel(modelo);
        
        JPopupMenu popup = new JPopupMenu();        
        JMenuItem jMenuItemCambiarDocumento = new JMenuItem("Cambiar Documento"); 
        popup.add(jMenuItemCambiarDocumento);
        //jMenuItemCambiarDocumento.addActionListener(itemListener);
        
        getTblProductos().setComponentPopupMenu(popup);
        

        UtilidadesTablas.definirTamanioColumnas(getTblProductos(),new Integer[]{0,50,250,450,80,80});
    }
    
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<ProductoPrecioDataTable>() {
            @Override
            public Object[] addData(ProductoPrecioDataTable valueTmp) {
                Producto producto=valueTmp.producto;
                System.out.println(producto.getValorUnitario());
                System.out.println(valueTmp.pvp1);
                String codigo=producto.getCodigoPersonalizado();
                String nombreProducto=producto.getNombre();
                            
                if(!getChkEditarValoresDirectos().isSelected())
                {
                    valueTmp.recalcularValoresDesdePorcentajes(valueTmp.costoCalculo);
                }
                
                BigDecimal costoUltimoConIva= UtilidadesImpuestos.agregarValorIva(new BigDecimal(producto.getCatalogoProducto().getIva().getTarifa()),valueTmp.costoCalculo);
                BigDecimal costoPromedioConIva= UtilidadesImpuestos.agregarValorIva(new BigDecimal(producto.getCatalogoProducto().getIva().getTarifa()),valueTmp.costoPromedio);
                
                                                                
                return new Object[]{
                    valueTmp,
                    codigo,
                    nombreProducto,
                    UtilidadBigDecimal.redondearCuatroDecimales(valueTmp.costoUltimo),
                    UtilidadBigDecimal.redondearCuatroDecimales(costoUltimoConIva),
                    UtilidadBigDecimal.redondearCuatroDecimales(valueTmp.costoPromedio),
                    UtilidadBigDecimal.redondearCuatroDecimales(costoPromedioConIva),
                    valueTmp.pvp1,
                    valueTmp.pvp2,
                    valueTmp.pvp3,
                    valueTmp.pvp4,
                    valueTmp.pvp5,
                    valueTmp.pvp6,
                };
            }

            @Override
            public void setData(ProductoPrecioDataTable objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                final int COLUMNA_OBJETO=0;
                final int COLUMNA_PVP1_PORCENTAJE=8;
                final int COLUMNA_PVP2_PORCENTAJE=COLUMNA_PVP1_PORCENTAJE+1;
                final int COLUMNA_PVP3_PORCENTAJE=COLUMNA_PVP2_PORCENTAJE+1;
                final int COLUMNA_PVP4_PORCENTAJE=COLUMNA_PVP3_PORCENTAJE+1;
                final int COLUMNA_PVP5_PORCENTAJE=COLUMNA_PVP4_PORCENTAJE+1;
                final int COLUMNA_PVP6_PORCENTAJE=COLUMNA_PVP5_PORCENTAJE+1;
                
                BigDecimal valorModificado=null;
                
                try
                {
                    valorModificado=new BigDecimal(objetoModificado+"");
                }
                catch(NumberFormatException nfe)
                {
                    nfe.printStackTrace();
                }
                
                //BigDecimal costoCalculo=objetoOriginal.costoCalculo;
                
                
                switch (columnaModificada) {
                    case COLUMNA_OBJETO:
                        break;

                    case COLUMNA_PVP1_PORCENTAJE:
                        objetoOriginal.pvp1=valorModificado;
                        listenerCargarPreciosOriginal(objetoOriginal);
                        break;

                    case COLUMNA_PVP2_PORCENTAJE:
                        objetoOriginal.pvp2=valorModificado;
                        listenerCargarPreciosOriginal(objetoOriginal);
                        break;
                        
                    case COLUMNA_PVP3_PORCENTAJE:
                        objetoOriginal.pvp3=valorModificado;
                        listenerCargarPreciosOriginal(objetoOriginal);
                        break;

                    case COLUMNA_PVP4_PORCENTAJE:
                        objetoOriginal.pvp4=valorModificado;
                        listenerCargarPreciosOriginal(objetoOriginal);
                        break;
                        
                    case COLUMNA_PVP5_PORCENTAJE:
                        objetoOriginal.pvp5=valorModificado;
                        listenerCargarPreciosOriginal(objetoOriginal);
                        break;
                        
                    case COLUMNA_PVP6_PORCENTAJE:
                        objetoOriginal.pvp6=valorModificado;
                        listenerCargarPreciosOriginal(objetoOriginal);
                        break;
                }
                
                
            }
        };
    };

    @Override
    public ModelControladorAbstract getControladorVista() 
    {
        return controlador;
    }

    @Override
    public Boolean pendientesActualizarPrecio() {
        return getChkPendientesActualizar().isSelected();
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        if(parametros==null)
        {
            return ;
        }
        
        if(parametros.length>0)
        {
            List<ProductoPrecioDataTable> productoListTmp=(List<ProductoPrecioDataTable>) parametros[0];
            if(productoListTmp.size()>0)
            {
                this.controlador.setProductoFiltro(productoListTmp.get(0).producto);
            }               
            this.controlador.castListDataTable(productoListTmp);
            actualizarBindingCompontValues();
        }
    }

    @Override
    public Object getResult() throws ExcepcionCodefacLite {
        try {
            this.controlador.grabar();
            //Todo: Terminar logica para terminr de retornar los datos para grabar            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadPrecioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void limpiarTablaDetalle() {
        listenerCargarPreciosOriginal(null);
    }
    
}
