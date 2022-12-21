/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.panel.DialogoBuscadorForm;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoBusquedaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.FuncionesSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.sql.UtilidadSql;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesDerby;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.KeyEventPostProcessor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Carlos
 */
public class BuscarDialogoModel extends DialogoBuscadorForm
{
    private static final int altoVentanaDefecto=410;
    private static final int anchoVentanaDefecto=800;
    
    private static final String ALIAS_BUSQUEDA="?1000";
    /**
     * Cantidad maxima de filas que va a cargar la tabla 
     */
    private static final int CANTIDAD_FILAS=15;
    /**
     * Setear la pagina actual de la consulta
     */
    private BuscarDialogoModel instancia=this;
    /**
     * Evento que controla cerrar la ventana utilizando la tecla de escape
     */
    private KeyEventPostProcessor eventoCerrarVentana;
    private int paginaActual;
    private int tamanioConsulta;
    
    private int cantidadPaginas;
    
    private DefaultTableModel modeloTablaBuscar;
    private InterfaceModelFind model;
    private List<Object> resultadosSeleccionados;
    private List<Object> listaResultados;
    private List<ComponenteFiltro> componenteFiltroList=new ArrayList<ComponenteFiltro>();
    
    /**
     * Variable que me permite configurar si la variable de busqueda quiero que normalize sin acentos o buscar de forma identica a lo escrito
     */
    private boolean normalizarTextoBusqueda;
    
    /**
     * Variable que va a tener las dimenciones de la ventana
     */
    private Dimension dimensionVentana;

    /*
    public BuscarDialogoModel(DefaultTableModel modeloTablaBuscar) 
    {
        super(null,true);
        this.modeloTablaBuscar = modeloTablaBuscar;
        this.paginaActual=0;
    }*/
    
    public BuscarDialogoModel(InterfaceModelFind model) 
    {        
        super(null,true);
        constructorGeneral(model,new Dimension(anchoVentanaDefecto, altoVentanaDefecto));
        
    }
    
    public BuscarDialogoModel(InterfaceModelFind model,int ancho) 
    {        
        super(null,true);
        constructorGeneral(model,new Dimension(ancho, altoVentanaDefecto));
        
    }
    
    public void constructorGeneral(InterfaceModelFind model,Dimension dimensionVentana)
    {
        this.model=model;
        iniciarValores();
        crearFiltrosVista();
        initListener();
        //crearConsulta("");
        ejecutarConsulta();
        this.dimensionVentana=dimensionVentana;
        //cargarDatos(listaResultados);
        establecerPropiedadesIniciales();        
        normalizarTextoBusqueda=false;
        agregarOyenteParaCerrarElDialogo();
        
    }
    
    /**
     * Metodo que permite asignar una forma de cerrar el dialogo con la tecla de escape
     * se utiliza la referencia del evento agregado para luego quitarle por que luego genera problemas
     * TODO: Ver como se puede mejorar esta parte
     */
    private void agregarOyenteParaCerrarElDialogo()
    {
        eventoCerrarVentana = new KeyEventPostProcessor() 
        {
            public boolean postProcessKeyEvent(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
                {
                    //instancia.setVisible(false);
                    instancia.dispose();
                    //JOptionPane.showMessageDialog(null,"Cerrando ventana");                    
                    DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(eventoCerrarVentana);
                }
                //System.out.println(e);
                return true;
            }
        };

        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(eventoCerrarVentana);        
        
    }
    
    private void crearFiltrosVista()
    {
        getPnlFiltros().setVisible(false);
        if(model instanceof FiltroDialogoIf)
        {
            getPnlFiltros().setVisible(true);
            FiltroDialogoIf filtroDialogIf=(FiltroDialogoIf) model;
            List<ComponenteFiltro> filtrosList= filtroDialogIf.getfiltrosList();
            //List<ComponenteFiltro> filtrosList= new ArrayList<ComponenteFiltro>();
            for (ComponenteFiltro componenteFiltro : filtrosList) 
            {
                if(componenteFiltro.tipoFiltroEnum.equals(ComponenteFiltro.TipoFiltroEnum.COMBO_BOX))
                {
                    JPanel panelCompontenteFiltro=crearComboxBox(componenteFiltro, componenteFiltroList);
                    agregarPanelFiltro(panelCompontenteFiltro, componenteFiltroList);
                }
                else if(componenteFiltro.tipoFiltroEnum.equals(ComponenteFiltro.TipoFiltroEnum.TEXTO))
                {
                    JPanel panelCompontenteFiltro=crearTextField(componenteFiltro);
                    agregarPanelFiltro(panelCompontenteFiltro, componenteFiltroList);
                }
            }
        }        
    }
    
    private void agregarPanelFiltro(JPanel panel,List<ComponenteFiltro> componenteFiltroList)
    {
        if(componenteFiltroList.size()%2==0)
        {
            getPnlFiltroParametrosB().add(panel);
            getPnlFiltroParametrosB().add(Box.createRigidArea(new Dimension(10, 10)));
        }
        else
        {
            getPnlFiltroParametrosA().add(panel);
            getPnlFiltroParametrosA().add(Box.createRigidArea(new Dimension(10, 10)));
        }
        pack();
    }
    
    private JPanel crearTextField(ComponenteFiltro componenteFiltro)
    {
        JTextField textField=new JTextField();
        return agregarComponente(componenteFiltro, textField);
    }
    
    private JPanel crearComboxBox(ComponenteFiltro componenteFiltro,List<ComponenteFiltro> componenteFiltroList)
    {
        JComboBox cmbFiltro=new JComboBox();
        cmbFiltro.addItem(null);
        for (Object dato : componenteFiltro.listaDatos) 
        {
            cmbFiltro.addItem(dato);
        }
        cmbFiltro.setSelectedItem(null);
        /*JPanel pnlNuevo=new JPanel();
        pnlNuevo.setLayout(new BoxLayout(pnlNuevo, BoxLayout.X_AXIS));
        pnlNuevo.add(new JLabel(componenteFiltro.titulo));
        pnlNuevo.add(Box.createRigidArea(new Dimension(5, 0)));
        pnlNuevo.add(cmbFiltro);*/
        /*JPanel pnlNuevo=crearComponentePanel(componenteFiltro.titulo,cmbFiltro);
        componenteFiltro.componenteVista=cmbFiltro;
        componenteFiltroList.add(componenteFiltro);*/
        
        return agregarComponente(componenteFiltro, cmbFiltro);
    }
    
    private JPanel agregarComponente(ComponenteFiltro componenteFiltro,JComponent componente)
    {
        JPanel pnlNuevo=crearComponentePanel(componenteFiltro.titulo,componente);
        componenteFiltro.componenteVista=componente;
        componenteFiltroList.add(componenteFiltro);
        return pnlNuevo;
    }
    
    private JPanel crearComponentePanel(String titulo,JComponent componente)
    {
        JPanel pnlNuevo=new JPanel();
        pnlNuevo.setLayout(new BoxLayout(pnlNuevo, BoxLayout.X_AXIS));
        pnlNuevo.add(new JLabel(titulo));
        pnlNuevo.add(Box.createRigidArea(new Dimension(5, 0)));
        pnlNuevo.add(componente);
        return pnlNuevo;
    }
    
    
   
    
    
    private void iniciarValores()
    {
        UtilidadesComboBox.llenarComboBox(getCmbTipoBusqueda(),TipoBusquedaEnum.values());
    }
    
    private Map<Integer,Object> obtenerDatosFiltro()
    {
        Map<Integer,Object> mapFiltro = new HashMap<Integer,Object>();
        
        for (ComponenteFiltro componenteFiltro : componenteFiltroList) 
        {
            if(componenteFiltro.tipoFiltroEnum.equals(ComponenteFiltro.TipoFiltroEnum.COMBO_BOX))
            {
                JComboBox comboBox=(JComboBox) componenteFiltro.componenteVista;
                
                Object valor=comboBox.getSelectedItem();
                if(valor!=null && componenteFiltro.filtroParametroIf!=null)
                {
                    valor=componenteFiltro.filtroParametroIf.getValor(valor);
                }
                
                mapFiltro.put(componenteFiltro.numeroParametro, valor);
            }
            else if(componenteFiltro.tipoFiltroEnum.equals(ComponenteFiltro.TipoFiltroEnum.TEXTO))
            {
                JTextField textField=(JTextField) componenteFiltro.componenteVista;
                
                Object valor=textField.getText();
                //TODO: Por el momento aumento el comodin de busqueda pero eso creo que se debe configurar por cada parametro
                mapFiltro.put(componenteFiltro.numeroParametro,"%"+valor+"%");                
            }
        }
        
        return mapFiltro;
    }

    
    /**
     * Consulta que obtiene datos segun los datos seteados
     */
    private void consultaSecundaria()
    {
        try {
            String filtro=getTxtBuscar().getText().toLowerCase();
            filtro=(normalizarTextoBusqueda)?UtilidadesDerby.normalizarTextoDerby(filtro):filtro;
            
            String filtroConsuta=filtro;
            
            TipoBusquedaEnum tipoBusquedaEnum=(TipoBusquedaEnum) getCmbTipoBusqueda().getSelectedItem();
            if(tipoBusquedaEnum.equals(TipoBusquedaEnum.EXACTO))
            {
                //Quito los filtro de porcentaje para hacer la busqueda exacta
                filtroConsuta=filtroConsuta.replace("%","");
            } 
            else if(tipoBusquedaEnum.equals(TipoBusquedaEnum.COINCIDENCIA))
            {            
                if(!filtro.contains("%"))
                {
                    filtroConsuta="%"+filtroConsuta+"%";
                }
            }
            
            Map<Integer,Object> mapFiltro=obtenerDatosFiltro();
            QueryDialog queryDialog=this.model.getConsulta(filtroConsuta,mapFiltro);
            agregarParametrosFiltros(queryDialog,this.model);
            
            //queryDialog.agregarParametro(1000,"%"+filtro+"%");
            
            int limiteInferior=CANTIDAD_FILAS*(paginaActual-1);
            int limiteSuperior=CANTIDAD_FILAS*(paginaActual);
            //Limpiar los resutados anteriores
            if(listaResultados!=null)
            {
                listaResultados.clear();
            }
            
            convertirMinusculasParametros(queryDialog);
            listaResultados=ServiceFactory.getFactory().getUtilidadesServiceIf().consultaGeneralDialogos(queryDialog.query,queryDialog.getParametros(),queryDialog.tipoQuery,limiteInferior,CANTIDAD_FILAS);
            
            if(model instanceof DialogoConfigAuxIf)
            {
                DialogoConfigAuxIf configIf=(DialogoConfigAuxIf) model;
                listaResultados=configIf.preProcessResult(listaResultados);
            }
            
            cargarDatos(listaResultados);
            
            setearBotonesSiguienteAtras();
            imprimirTexto();
        } catch (RemoteException ex) {
            Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void agregarParametrosFiltros(QueryDialog queryDialog,InterfaceModelFind model)
    {
        String query=queryDialog.query;
        if (model instanceof FiltroDialogoIf) 
        {
            Map<Integer,Object> mapFiltro = obtenerDatosFiltro();
            for (Map.Entry<Integer, Object> entry : mapFiltro.entrySet()) 
            {
                Integer numeroParametro = entry.getKey();
                Object valor = entry.getValue();
                
                //No tomar en cuenta parametros con simbolo negativo que solo se deben usar para hacer alguna logica dentrode las consultas pero no agregar como parametro
                if(numeroParametro>=0)
                {
                    if(valor==null)
                    {
                        query=remplazarParametroVerdadero(query, numeroParametro);
                    }
                    else
                    {
                        queryDialog.agregarParametro(numeroParametro, valor);
                    }
                }
                
            }
        }
        queryDialog.query=query;
        //return query;
    
    }
    
    //El objetivo de este metodo es encontrar un parametro y modificar por una igualdad
    //Ejemplo  p.nombre=?1 ==> 1=1
    private String remplazarParametroVerdadero(String query,Integer numeroParametro)
    {
        //Dividir el query en 2 textos antes y despues del caracteres que estoy buscando
        Integer posicionParametro=query.indexOf("?"+numeroParametro.toString());
        
        //Solo ejecutar el codigo  de remplazo si encuentra el numero de parametro en el query, por que no puede aparecer por que puede ser que esta condicionado
        if(posicionParametro>=0)
        {
            String queryPrimeraParte=query.substring(0,posicionParametro);
            String querySegundaParte=query.substring(posicionParametro,query.length());

            //Buscar en la parte derecha el tramo que debo eliminar hasta encontrar el primer espacio        
            Integer primerEspacio=querySegundaParte.indexOf(" ");        
            querySegundaParte=querySegundaParte.substring(primerEspacio,querySegundaParte.length());


            //Buscar en la parte izquierda el primer tramo antes de un espacio
            String textoInvertido=new StringBuilder(queryPrimeraParte).reverse().toString();
            Integer posicionCorte = textoInvertido.indexOf(" ");
            queryPrimeraParte=queryPrimeraParte.substring(0,queryPrimeraParte.length()-posicionCorte);

            //unificar el query total para retornar
            String queryModificado=queryPrimeraParte+" 1=1 "+querySegundaParte;
            return queryModificado;
        }
        return query;
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
            getBtnPrimero().setEnabled(false);
        }
        else
        {
            getBtnAtras().setEnabled(true);
            getBtnPrimero().setEnabled(true);
        }
        
        if(paginaActual==cantidadPaginas)
        {
            getBtnSiguiente().setEnabled(false);
            getBtnUltimo().setEnabled(false);
        }
        else
        {
            getBtnSiguiente().setEnabled(true);
            getBtnUltimo().setEnabled(true);
        }
        
        if(cantidadPaginas==0)
        {
            getBtnAtras().setEnabled(false);
            getBtnSiguiente().setEnabled(false);
            getBtnPrimero().setEnabled(false);
            getBtnUltimo().setEnabled(false);
        }
    }
    
    private int obtenerTamanioConsulta()
    {
        try {
            String filtro=getTxtBuscar().getText().toLowerCase();
            filtro=(normalizarTextoBusqueda)?UtilidadesDerby.normalizarTextoDerby(filtro):filtro;
            
            String filtroConsuta=filtro;
            
            TipoBusquedaEnum tipoBusquedaEnum=(TipoBusquedaEnum) getCmbTipoBusqueda().getSelectedItem();
            if(tipoBusquedaEnum.equals(TipoBusquedaEnum.EXACTO))
            {
                //Si la busqueda es exacta quito todo los porcentajes
                filtroConsuta=filtroConsuta.replace("%","");
                //Tambien cambio
                        
            }
            else if(tipoBusquedaEnum.equals(TipoBusquedaEnum.COINCIDENCIA))
            {            
                //Si el filtro ya contiene el porcentaje entonces desactivo la busqueda automatica                
                if(!filtro.contains("%"))
                {
                    filtroConsuta="%"+filtroConsuta+"%";
                }
            }
            Map<Integer,Object> mapFiltro=obtenerDatosFiltro();
            QueryDialog queryDialog=this.model.getConsulta(filtroConsuta,mapFiltro);
            agregarParametrosFiltros(queryDialog,this.model);
            
            //queryDialog.agregarParametro(1000,"%"+filtro+"%");
            /*String query=queryDialog.query;
            query=query.toLowerCase();
            int primerCorte=query.indexOf("select")+"select".length();
            int segundoCorte=query.indexOf("from");
            String queryModificado=queryDialog.query.substring(0,primerCorte)+" count(1) "+queryDialog.query.substring(segundoCorte);
            //Eliminar las columnas de ordenar porque no se pueden ejecutar en conjunto con el comando count(1)
            if(queryModificado.toLowerCase().indexOf("order by")>=0)
                queryModificado=queryModificado.substring(0,queryModificado.toLowerCase().indexOf("order by")); //TODO: Analizar como verificar para otros casos que tengan mas espacios entre order y by
            */
            String queryModificado=UtilidadSql.convertirConsultaEnConsultaTamanio(queryDialog.query);
            
            System.out.println(queryModificado);            
            convertirMinusculasParametros(queryDialog);
            Long tamanio=ServiceFactory.getFactory().getUtilidadesServiceIf().consultaTamanioGeneralDialogos(queryModificado, queryDialog.getParametros());
            
            return (tamanio!=null)?tamanio.intValue():0;
        } catch (RemoteException ex) {
            Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
            FuncionesSistemaCodefac.servidorConexionPerdida();
        }
        return 0;
    }
    
    /**
     * Consulta inicial que establece los parametros como cantidad de datos, numero de paginas
     * y otras configuraciones para costruir el dialogo
     */
    public void ejecutarConsulta()
    {
        this.tamanioConsulta=obtenerTamanioConsulta();
        this.paginaActual=1;        
        double paginas=(double)tamanioConsulta/(double)CANTIDAD_FILAS;
        this.cantidadPaginas=(int)(new BigDecimal(paginas).setScale(0,BigDecimal.ROUND_UP).intValue());

        //Enviar un mensaje si no existe datos para no abrir el dialogo sin necesidad

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
        if(resultadosSeleccionados!=null && resultadosSeleccionados.size()>0)
        {
            return resultadosSeleccionados.get(0);
        }
        else
        {
            return null;
        }
    }
    
    public List<Object> getResultados()
    {
        if(resultadosSeleccionados!=null)
        {
            return resultadosSeleccionados;
        }
        else
        {
            return null;
        }
    }
       
    /*private void agregarFiltroBusquedaEjemplo()
    {
        JPanel panelFiltroA= getPnlFiltroParametrosA();
        JPanel panelFiltroB= getPnlFiltroParametrosB();
        
        /*JLabel tituloFiltro=new JLabel("Filtro: ");
        panelFiltroA.add(tituloFiltro);
        panelFiltroB.add(tituloFiltro);
        
        JComboBox<String> cmbDatos=new JComboBox<String>();
        cmbDatos.addItem("dato1");
        cmbDatos.addItem("dato2");
        cmbDatos.addItem("dato3");
        panelFiltroA.add(cmbDatos);
        panelFiltroB.add(cmbDatos);*/
        
        /*GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        
        JPanel pnlNuevo=new JPanel();
        pnlNuevo.setLayout(new BoxLayout(pnlNuevo, BoxLayout.X_AXIS));
        pnlNuevo.add(new JLabel("Filtro1"));
        pnlNuevo.add(Box.createRigidArea(new Dimension(5, 0)));
        pnlNuevo.add(new JComboBox());
        panelFiltroA.add(pnlNuevo);
        panelFiltroA.add(Box.createRigidArea(new Dimension(5, 5)));
        
        
        
        pack();
        
        
    }*/
    
    private void cargarDatoSeleccionadoPanelAuxiliar(int filaSeleccionada)
    {
        if(filaSeleccionada>=0)
        {
            Object datoSeleccionado= listaResultados.get(filaSeleccionada);
            if(model instanceof DialogPanelAuxIf)
            {
                DialogPanelAuxIf panelAux=(DialogPanelAuxIf) model;
                // JPanel pnlNuevo=new JPanel();
                //pnlNuevo.setLayout(new BoxLayout(pnlNuevo, BoxLayout.X_AXIS));
                //pnlNuevo.add(new JLabel("ejemplo ..."));
                //getPnlAuxiliarInfo().add(pnlNuevo);
                //getPnlAuxiliarInfo().removeAll();
                
                getPnlAuxiliarInfo().removeAll();
                getPnlAuxiliarInfo().add(panelAux.getPanelAuxiliar(datoSeleccionado));
                getPnlAuxiliarInfo().repaint();
                //this.setAltoVentana(Integer.parseInt(this.dimensionVentana.getHeight()+"")+100);
                //System.out.println(getPnlAuxiliarInfo().getHeight());
                //this.setAltoVentana(this.getHeight()+getPnlAuxiliarInfo().getHeight());
                pack();
            }
        }
    }
    
    private List<Object> obtenerDatosSeleccionados()
    {
        int[] filas = getTblTabla().getSelectedRows();
        List<Object> datosSeleccionados=new ArrayList<Object>();
        
        for (int filaSeleccionada : filas) 
        {
            datosSeleccionados.add(listaResultados.get(filaSeleccionada));
        }
        return datosSeleccionados;
    }
    
    private void initListener()
    {        
        //Todo agregado artificio para setear el focus porque no funciona con un tema
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {
                getTxtBuscar().requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        getTxtBuscar().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(getTblTabla().getRowCount()>0)
                    {
                        getTblTabla().setRowSelectionInterval(0,0);//Seleccionar la primera fila de la tabla
                        getTblTabla().requestFocus();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Boolean filtroRapido = ParametroUtilidades.compararSinEmpresa(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA, EnumSiNo.SI);
                    
                    if (filtroRapido) 
                    {
                        ejecutarConsulta();                        
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnUltimo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual=cantidadPaginas;
                consultaSecundaria();
            }
        });
        
        getBtnPrimero().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual=1;
                consultaSecundaria();                
            }
        });
        
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
                //int fila=getTblTabla().getSelectedRow();
                //getTblTabla().getSelectedRows();
                //resultado=listaResultados.get(fila);
                resultadosSeleccionados=obtenerDatosSeleccionados();
                dispose();
            }
        });
        
        getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultadosSeleccionados=null;
                dispose();
            }
        });
        
        
        getBtnFiltrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta();
                //agregarFiltroBusquedaEjemplo();
                
            }
        });
        
        getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                try {
                    Boolean filtroRapido=ParametroUtilidades.compararSinEmpresa(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA,EnumSiNo.NO);
                    
                    if(filtroRapido || e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        ejecutarConsulta();
                    }
                    //else
                    //{
                    //    ejecutarConsulta();
                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
}); 
        
        /**
         * Agregar listener para que devuelva un resultado cuando el usuario ejecute doble click
         */
        
        getTblTabla().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        getTblTabla().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //JOptionPane.showMessageDialog(null,"evento click");
                cargarDatoSeleccionadoPanelAuxiliar(getTblTabla().getSelectedRow());
            }
        });
        
        getTblTabla().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    //int fila=getTblTabla().getSelectedRow();
                    //resultado=listaResultados.get(fila);
                    resultadosSeleccionados=obtenerDatosSeleccionados();
                    dispose();
                    // your valueChanged overridden method 
                }
            }
        });
        
        getTblTabla().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                
                //Solo ejecutar si presiona el evento enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {                   
                   int filaSeleccionada=getTblTabla().getSelectedRow();
                   if(filaSeleccionada>=0)   //
                   {
                       //resultado=listaResultados.get(filaSeleccionada);
                       resultadosSeleccionados=obtenerDatosSeleccionados();
                       dispose();
                   }
                }
                
                if(e.getKeyCode()==KeyEvent.VK_UP)
                {
                    int filaSeleccionada=getTblTabla().getSelectedRow();
                    if(filaSeleccionada==0)
                    {
                        getTxtBuscar().requestFocus();
                    }
                }
                
                if(e.getKeyCode()==KeyEvent.VK_LEFT)
                {
                   getBtnAtras().doClick();
                }
                
                if(e.getKeyCode()==KeyEvent.VK_RIGHT)
                {
                   getBtnSiguiente().doClick();
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    

    private void establecerPropiedadesIniciales() {
        
        //Verificar si tiene una ventana de datos adicionales se tiene que sumar el alto de esa ventana
        if(model instanceof DialogPanelAuxIf)
        {
            DialogPanelAuxIf panelAux=(DialogPanelAuxIf) model;
            //System.out.println("Alto: "+panelAux.getPanelAuxiliar(null).getPreferredSize().getHeight());
            dimensionVentana.height=dimensionVentana.height+panelAux.getPanelAuxiliar(null).getPreferredSize().height;
        }
        
        //sad
        //Centrar el dialogo
        
        setPreferredSize(dimensionVentana);
        setSize(dimensionVentana);
        setLocationRelativeTo(null);

    }
    
    public void setearTextoBusqueda(String texto)
    {
        getTxtBuscar().setText(texto.toLowerCase());
    }

    public long getTamanioConsulta() {
        return tamanioConsulta;
    }
    
    public Object obtenerResultadoLista(int indice)
    {
        return this.listaResultados.get(indice);
    }
    
    @Deprecated
    private void convertirMinusculasParametros(QueryDialog queryDialog)
    {
        for (Map.Entry<Integer, Object> en : queryDialog.getParametros().entrySet()) {
            Integer numero = en.getKey();
            Object valor = en.getValue();
            
            //Solo aplicar esta opcion cuando el tipo de dato enviado es String
            if(valor!=null && valor.getClass().equals(String.class))
            {
                //valor=valor.toString().toLowerCase();
                queryDialog.getParametros().put(numero, valor); //Remplazo el antiguo valor por el nuevo con minusculas
            }
            
            
            
        }

    }

    public boolean getNormalizarTextoBusqueda() {
        return normalizarTextoBusqueda;
    }

    public void setNormalizarTextoBusqueda(boolean normalizarTextoBusqueda) {
        this.normalizarTextoBusqueda = normalizarTextoBusqueda;
    }

    public Dimension getDimensionVentana() {
        return dimensionVentana;
    }

    public void setDimensionVentana(Dimension dimensionVentana) {
        this.dimensionVentana = dimensionVentana;
    }
    
    public void setAnchoVentana(int ancho)
    {
        this.dimensionVentana.setSize(ancho,this.dimensionVentana.getHeight());
    }
    
    public void setAltoVentana(int alto)
    {
        this.dimensionVentana.setSize(this.dimensionVentana.getWidth(),alto);
    }

    
}
