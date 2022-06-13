/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.StockMinimoData;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class StockConsultaRapidaModel extends StockReporteModel
{

    @Override
    public void ocultarComponentes() {
        getLblMostrarDetalle().setVisible(false);
        getCmbMostrarDetalle().setVisible(false);
        
        getLblDiasCaducidad().setVisible(false);
        getTxtDiasCaducidad().setVisible(false);
    }

    @Override
    public void construirTabla(List<StockMinimoData> listaData) 
    {
        
        String[] titulo={
            "Código",
            "Bodega",
            "Producto",
            "Categoria",
            "Tipo",
            "Segmento",
            "Aplicación",
            "Ubicación",
            "Stock",
        };
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        for (StockMinimoData stockMinimo : listaData) {
            
            String tipo="";
            String segmento="";
            String aplicacion="";
            
            if(stockMinimo.getSegmento()!=null)                
            {
                segmento=stockMinimo.getSegmento().getNombre();
            }
            
            if(stockMinimo.getTipo()!=null)
            {
                tipo=stockMinimo.getTipo().getNombre();
            }
            
            if(stockMinimo.getAplicacion()!=null)
            {
                aplicacion=stockMinimo.getAplicacion();
            }
            
            String[] datos=
            {
                stockMinimo.getCodigo(),
                stockMinimo.getBodega(),
                stockMinimo.getProducto(),
                stockMinimo.getCategoria(),
                tipo,
                segmento,
                aplicacion,
                stockMinimo.getUbicacion(),
                stockMinimo.getStock(),
            };
            
            modeloTabla.addRow(datos);
        }
        
        getTblDato().setModel(modeloTabla);   
    }
    
    
    
    
}
