/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.KardexPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.Kardex;
import ec.com.codesoft.codefaclite.servidor.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import ec.com.codesoft.codefaclite.servidor.service.CompraService;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import es.mityc.firmaJava.ocsp.config.ServidorOcsp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class KardexModel extends KardexPanel{
    
    /**
     * Referencia del producto para consultar el kardex
     */
    private Producto productoSeleccionado;
    
    /**
     * Variable donde se alamcena todos los kardex consultados
     */
    private Kardex kardex;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListernerBotones();
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListernerBotones() {
        getBtnProductoBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogo.getResultado();
                if (productoSeleccionado != null) {
                    getTxtProducto().setText(productoSeleccionado.getNombre());
                }
            }
        });
        
        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Date fechaInicio=new Date(getCmbFechaInicial().getDate().getTime());
                //Date fechaFin=new Date(getCmbFechaFinal().getDate().getTime());
                Bodega bodega=(Bodega) getCmbBodega().getSelectedItem();
                
                KardexService kardexService=new KardexService();
                
                Map<String,Object> parametrosMap=new HashMap<String, Object>();
                parametrosMap.put("bodega", bodega);
                parametrosMap.put("producto", productoSeleccionado);
                
                List<Kardex> resultados=kardexService.obtenerPorMap(parametrosMap);
                if(resultados!=null && resultados.size()>0)
                {
                    kardex=resultados.get(0);
                    cargarTablaKardex();
                }
                
            }
        });
    }
    
    private void cargarTablaKardex()
    {
        Integer cantidadAcumulada=0;
        BigDecimal precioUnitarioAcumulado=BigDecimal.ZERO;
        BigDecimal precioTotalAcumulado=BigDecimal.ZERO;
        
        String[] titulo={"#","Documento","Preimpreso","Proveedor","Cant","P.Unit","P.Total","Cant","P.Unit","P.Total","Cant","P.Unit","P.Total"};
        String[] primeraFila={" ","KARDEX","","","","INGRESO","","","EGRESO","","","","SALDO"};
        
        DefaultTableModel modeloTabla=new DefaultTableModel(primeraFila,0);
        modeloTabla.addRow(titulo);
        
        List<KardexDetalle> detalles=kardex.getDetallesKardex();
        for (int i = 0; i < detalles.size(); i++) {
            Vector<String> fila=new Vector<String>();
            KardexDetalle kardexDetalle=detalles.get(i);
            
            fila.add(i+""); //numeracion
            fila.add(kardexDetalle.getCodigoTipoDocumentoEnum().getNombre()); //tipo del documento
            TipoDocumentoEnum tipoDocumentoEnum=kardexDetalle.getCodigoTipoDocumentoEnum();
            
            switch(tipoDocumentoEnum.getModuloEnum())
            {
                case VENTAS:
                    cantidadAcumulada-=kardexDetalle.getCantidad();
                    precioTotalAcumulado=precioUnitarioAcumulado.subtract(kardexDetalle.getPrecioUnitario());
                    precioTotalAcumulado=precioUnitarioAcumulado.subtract(kardexDetalle.getPrecioTotal());
                    
                    FacturacionService servicio=new FacturacionService();
                    Map<String,Object> mapParametros=new HashMap<String,Object>();
                    mapParametros.put("id",kardexDetalle.getReferenciaDocumentoId());
                    Factura factura=servicio.obtenerPorMap(mapParametros).get(0);
                    fila.add(factura.getPreimpreso());
                    fila.add(factura.getCliente().getRazonSocial());
                    completarFila(fila, tipoDocumentoEnum.getModuloEnum(), kardexDetalle, cantidadAcumulada, precioUnitarioAcumulado, precioTotalAcumulado);
                    break;
                    
                case COMPRAS:
                    cantidadAcumulada+=kardexDetalle.getCantidad();
                    precioTotalAcumulado=precioUnitarioAcumulado.add(kardexDetalle.getPrecioUnitario());
                    precioTotalAcumulado=precioUnitarioAcumulado.add(kardexDetalle.getPrecioTotal());
                    
                    CompraService servicio2=new CompraService();
                    Map<String,Object> mapParametros2=new HashMap<String,Object>();
                    mapParametros2.put("id",kardexDetalle.getReferenciaDocumentoId());
                    Compra compra=servicio2.obtenerPorMap(mapParametros2).get(0);
                    fila.add(compra.getPreimpreso());
                    fila.add(compra.getProveedor().getRazonSocial());
                    completarFila(fila, tipoDocumentoEnum.getModuloEnum(), kardexDetalle, cantidadAcumulada, precioUnitarioAcumulado, precioTotalAcumulado);
                    break;
            }
            
            modeloTabla.addRow(fila);
            getTblKardexDetalle().setModel(modeloTabla);
            
            getLblCantidad().setText(kardex.getStock()+"");
            getLblPrecioPromedio().setText(kardex.getPrecioPromedio()+"");
            getLblPrecioUltimo().setText(kardex.getPrecioUltimo()+"");
            getLblTotal().setText(kardex.getPrecioTotal()+"");
                    
        }
   
        
    }
    
    private void completarFila(Vector<String> fila,ModuloEnum moduloEnum,KardexDetalle kardexDetalle,Integer cantidadAcumulada,BigDecimal precioUnitarioAcumulado,BigDecimal precioTotalAcumulado)
    {
        switch(moduloEnum)
        {
            case VENTAS:
                fila.add("");
                fila.add("");
                fila.add("");
                
                fila.add(kardexDetalle.getCantidad() + "");
                fila.add(kardexDetalle.getPrecioUnitario() + "");
                fila.add(kardexDetalle.getPrecioTotal() + "");
                
            case COMPRAS:
               
                fila.add(kardexDetalle.getCantidad()+"");
                fila.add(kardexDetalle.getPrecioUnitario()+"");
                fila.add(kardexDetalle.getPrecioTotal()+"");
                
                fila.add("");
                fila.add("");
                fila.add("");

        }

        fila.add(cantidadAcumulada + "");
        fila.add(precioUnitarioAcumulado + "");
        fila.add(precioTotalAcumulado + "");
        
    }
    
    private void valoresIniciales() {
        
        
        getCmbBodega().removeAllItems();
        BodegaService servicioBodega = new BodegaService();
        List<Bodega> bodegas = servicioBodega.obtenerTodos();
        for (Bodega bodega : bodegas) {
            getCmbBodega().addItem(bodega);
        }
        
    }

    
}
