/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TableBindingImp extends ComponentBindingAbstract<JTable,TableBinding> {

    
    private ITableBindingAddData addDataInterface;
    
    public ComponentBindingIf interfaceAddData=new ComponentBindingIf<ITableBindingAddData,TableBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            
        }

        @Override
        public void setAccion(ITableBindingAddData value) {
            addDataInterface=value;
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.tableAddDataInterface();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    };
    
    
    
    
    public ComponentBindingIf value=new ComponentBindingIf<List,TableBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            //getComponente()
            //TODO: Este campo seria cuando necesite que desde algun evento del comonente actualizo al modelo de datos
        }

        @Override
        public void setAccion(List value) {
            DefaultTableModel defaultTableModel=(DefaultTableModel) getComponente().getModel();
           if(defaultTableModel!=null)
           {
               if(addDataInterface!=null)
               {
                   //Limpiar todos los datos de la tabla
                   UtilidadesTablas.eliminarTodosLosDatos(defaultTableModel);
                   if(value!=null)
                   {
                        
                        for (Object valor : value) {
                            defaultTableModel.addRow(addDataInterface.addData(valor));
                        }
                   }
               }
           }
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.source();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
            
    };
    
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(interfaceAddData);
        lista.add(value);
        
    }

    @Override
    public Class<JTable> getClassComponente() {
        return JTable.class;
    }
    
}
