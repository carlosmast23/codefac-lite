/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.panel.DialogoBuscadorForm;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private static final String ALIAS_BUSQUEDA="?1000";
    /**
     * Cantidad maxima de filas que va a cargar la tabla 
     */
    private static final int CANTIDAD_FILAS=12;
    /**
     * Setear la pagina actual de la consulta
     */
    private int paginaActual;
    private long tamanioConsulta;
    
    private long cantidadPaginas;
    
    private DefaultTableModel modeloTablaBuscar;
    private InterfaceModelFind model;
    private Object resultado;
    private List<Object> listaResultados;

    public BuscarDialogoModel(DefaultTableModel modeloTablaBuscar) 
    {
        super(null,true);
        this.modeloTablaBuscar = modeloTablaBuscar;
        this.paginaActual=0;
    }
    
    public BuscarDialogoModel(InterfaceModelFind model) 
    {
        super(null,true);
        this.model=model;
        initListener();
        //crearModeloTabla();
        //listaResultados=this.model.getConsulta();
        //crearConsulta("");
        ejecutarConsulta();
        //cargarDatos(listaResultados);
        establecerPropiedadesIniciales();
        //this.paginaActual=0;
        //this.tamanioConsulta=obtenerTamanioConsulta();
        
    }
    
    /**
     * Consulta que obtiene datos segun los datos seteados
     */
    private void consultaSecundaria()
    {
        String filtro=getTxtBuscar().getText();
        QueryDialog queryDialog=this.model.getConsulta(ALIAS_BUSQUEDA);
        queryDialog.agregarParametro(1000,"%"+filtro+"%");
        
        int limiteInferior=CANTIDAD_FILAS*(paginaActual-1);
        int limiteSuperior=CANTIDAD_FILAS*(paginaActual);
        //Limpiar los resutados anteriores
        if(listaResultados!=null)
            listaResultados.clear();
        
        
        listaResultados=ServiceAbstract.consultaGeneralDialogos(queryDialog.query,queryDialog.getParametros(),limiteInferior,CANTIDAD_FILAS);
        cargarDatos(listaResultados);
        
        setearBotonesSiguienteAtras();
        imprimirTexto();
        
    }
    
    private void imprimirTexto()
    {
        String piePagina="Resultado "+paginaActual+" - "+cantidadPaginas+ " de "+tamanioConsulta+" registros.";
        getLblPiePagina().setText(piePagina);
    }
    
    private void setearBotonesSiguienteAtras()
    {
        
        if(paginaActual==1)
        {
            getBtnAtras().setEnabled(false);
        }
        else
        {
            getBtnAtras().setEnabled(true);
        }
        
        if(paginaActual==cantidadPaginas)
        {
            getBtnSiguiente().setEnabled(false);
        }
        else
        {
            getBtnSiguiente().setEnabled(true);
        }
        
        if(cantidadPaginas==0)
        {
            getBtnAtras().setEnabled(false);
            getBtnSiguiente().setEnabled(false);
        }
    }
    
    private Long obtenerTamanioConsulta()
    {
        String filtro=getTxtBuscar().getText();
        QueryDialog queryDialog=this.model.getConsulta(ALIAS_BUSQUEDA);
        queryDialog.agregarParametro(1000,"%"+filtro+"%");
        String query=queryDialog.query;
        query=query.toLowerCase();
        int primerCorte=query.indexOf("select")+"select".length();        
        int segundoCorte=query.indexOf("from");
        String queryModificado=queryDialog.query.substring(0,primerCorte)+" count(1) "+queryDialog.query.substring(segundoCorte);
        System.out.println(queryModificado);
        return ServiceAbstract.consultaTamanioGeneralDialogos(queryModificado, queryDialog.getParametros());
    }
    
    /**
     * Consulta inicial que establece los parametros como cantidad de datos, numero de paginas
     * y otras configuraciones para costruir el dialogo
     */
    private void ejecutarConsulta()
    {
        this.tamanioConsulta=obtenerTamanioConsulta();
        this.paginaActual=1;        
        double paginas=(double)tamanioConsulta/(double)CANTIDAD_FILAS;
        //new BigDecimal(paginas).setScale(BigDecimal.ROUND_UP).toBigInteger();
        this.cantidadPaginas=(int)(new BigDecimal(paginas).setScale(0,BigDecimal.ROUND_UP).intValue());
        //if(cantidadPaginas==0)
        //    cantidadPaginas=1;
        
        consultaSecundaria();
        
    }
    
    private void crearModeloTabla()
    {
        Vector<ColumnaDialogo> columnas=this.model.getColumnas();
        Vector<String> titulos=new Vector<String>();
        Vector<Double> tamanios=new Vector<Double>();
        for (ColumnaDialogo columna : columnas) {
            titulos.add(columna.getNombre());
            tamanios.add(columna.getPorcentaje());        }

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
        getBtnSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual++;
                consultaSecundaria();
            }
        });
        
        getBtnAtras().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual--;
                consultaSecundaria();
            }
        });
        
        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getTblTabla().getSelectedRow();
                resultado=listaResultados.get(fila);
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
                ejecutarConsulta();
                /*
                if(getTxtBuscar().getText().equals(""))
                {
                    cargarDatos(listaResultados);
                }
                else
                {
                    ejecutarConsulta();
                }*/
                
            }
        });
        
        getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    ejecutarConsulta();
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
                    resultado=listaResultados.get(fila);
                    dispose();
                    // your valueChanged overridden method 
                }
            }
});
    }
    
    private void filtrar()
    {
        /**
        listaResultados=new ArrayList<Object>();
        listaResultados.clear();
        for (Object obj : listaResultados) {
            if(this.model.buscarObjeto(obj,getTxtBuscar().getText()))
            {
                listaResultadosFiltrados.add(obj);
            }
        }
        cargarDatos(listaResultadosFiltrados);
        */
    }

    private void establecerPropiedadesIniciales() {
        //Centrar el dialogo
        setPreferredSize(new Dimension(700,316));
        setSize(700, 316);
        setLocationRelativeTo(null);

    }
    
}
