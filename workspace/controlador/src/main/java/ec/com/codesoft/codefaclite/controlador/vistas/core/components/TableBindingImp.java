/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    
    public ComponentBindingIf valueSelect=new ComponentBindingIf<Object,TableBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            
            getComponente().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int filaSeleccionada=getComponente().getSelectedRow();
                    System.out.println("fila Seleccionada: "+filaSeleccionada);
                    if(filaSeleccionada>=0)
                    {
                        DefaultTableModel modeloTabla=(DefaultTableModel) getComponente().getModel();
                        if(modeloTabla!=null)
                        {
                            //TODO:Asumiendo que el dato que necesito siempre esta en la primera fila                        
                            Object valorSeleccionado=getComponente().getValueAt(filaSeleccionada,0);

                            setValoresAlControlador(valorSeleccionado,nombrePropiedadControlador,converter);
                            actualizarBindingVista();
                        }
                        
                    }
 
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
                        
        }

        @Override
        public void setAccion(Object value) {
            
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.selectValue();
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
        lista.add(valueSelect);
    }

    @Override
    public Class<JTable> getClassComponente() {
        return JTable.class;
    }
    
}
