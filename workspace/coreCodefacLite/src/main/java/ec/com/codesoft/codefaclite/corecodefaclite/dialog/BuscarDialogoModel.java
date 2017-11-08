/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.panel.DialogoBuscadorForm;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Carlos
 */
public class BuscarDialogoModel extends DialogoBuscadorForm
{
    private DefaultTableModel modeloTablaBuscar;
    private InterfaceModelFind model;
    private Object resultado;
    private List<Object> listaResultados;
    private List<Object> listaResultadosFiltrados;

    public BuscarDialogoModel(DefaultTableModel modeloTablaBuscar) 
    {
        super(null,true);
        this.modeloTablaBuscar = modeloTablaBuscar;
    }
    
    public BuscarDialogoModel(InterfaceModelFind model) 
    {
        super(null,true);
        this.model=model;
        initListener();
        //crearModeloTabla();
        listaResultados=this.model.getConsulta();
        listaResultadosFiltrados=listaResultados;
        cargarDatos(listaResultados);
        establecerPropiedadesIniciales();
    }
    
    private void crearModeloTabla()
    {
        Vector<ColumnaDialogo> columnas=this.model.getColumnas();
        Vector<String> titulos=new Vector<String>();
        Vector<Double> tamanios=new Vector<Double>();
        for (ColumnaDialogo columna : columnas) {
            titulos.add(columna.getNombre());
            tamanios.add(columna.getPorcentaje());
        }

        this.modeloTablaBuscar=new DefaultTableModel(titulos,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
       
        this.getTblTabla().setModel(this.modeloTablaBuscar);
        //Establecer el ancho de las tablas
        TableColumnModel columnasTabla=this.getTblTabla().getColumnModel();
        
        for (int i = 0; i < columnasTabla.getColumnCount(); i++) {
            double tamanio=(double)tamanios.get(i)*(double)getTblTabla().getWidth();
            columnasTabla.getColumn(i).setPreferredWidth((int)tamanio);
        }
        
        
    }
    
    private void cargarDatos(List<Object> datos)
    {
        crearModeloTabla();
        
        for (Object object : datos) 
        {
            Vector<String> dato=new Vector<String>();
            this.model.agregarObjeto(object, dato);
            this.modeloTablaBuscar.addRow(dato);
        }
        this.getTblTabla().setModel(this.modeloTablaBuscar);
    }
    
    public Object getResultado()
    {
        if(resultado!=null)
        {
            return resultado;
        }
        else
        {
            return null;
        }
    }
       
    
    private void initListener()
    {
        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getTblTabla().getSelectedRow();
                resultado=listaResultadosFiltrados.get(fila);
                dispose();
            }
        });
        
        getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultado=null;
                dispose();
            }
        });
        
        
        getBtnFiltrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getTxtBuscar().getText().equals(""))
                {
                    listaResultadosFiltrados=listaResultados;
                    cargarDatos(listaResultadosFiltrados);
                }
                else
                {
                    filtrar();
                }
                
            }
        });
        
        getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        if(getTxtBuscar().getText().equals(""))
                    {
                        listaResultadosFiltrados=listaResultados;
                        cargarDatos(listaResultadosFiltrados);
                    }
                    else
                    {
                        filtrar();
                    }
                }
            }
}); 
        
        /**
         * Agregar listener para que devuelva un resultado cuando el usuario ejecute doble click
         */
        getTblTabla().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    int fila=getTblTabla().getSelectedRow();
                    resultado=listaResultadosFiltrados.get(fila);
                    dispose();
                    // your valueChanged overridden method 
                }
            }
});
    }
    
    private void filtrar()
    {
        listaResultadosFiltrados=new ArrayList<Object>();
        listaResultadosFiltrados.clear();
        for (Object obj : listaResultados) {
            if(this.model.buscarObjeto(obj,getTxtBuscar().getText()))
            {
                listaResultadosFiltrados.add(obj);
            }
        }
        cargarDatos(listaResultadosFiltrados);
    }

    private void establecerPropiedadesIniciales() {
        //Centrar el dialogo
        setPreferredSize(new Dimension(700,400));
        setSize(700, 400);
        setLocationRelativeTo(null);

    }
    
}
