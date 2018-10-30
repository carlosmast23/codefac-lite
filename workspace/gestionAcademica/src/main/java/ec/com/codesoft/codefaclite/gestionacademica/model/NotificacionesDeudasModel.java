/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes.ComponenteEnvioSmsData;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteEnvioSmsInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_RIDE;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.gestionacademica.callback.EnvioMensajesCallBack;
import ec.com.codesoft.codefaclite.gestionacademica.other.EstudianteDeudaData;
import ec.com.codesoft.codefaclite.gestionacademica.other.NotificacionDeudaImprimir;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NotificacionesDeudasPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos
 */
public class NotificacionesDeudasModel extends NotificacionesDeudasPanel implements Runnable,ComponenteEnvioSmsInterface {
    
    private static final int TAB_POR_MES=0;
    private static final int TAB_POR_GRUPO=1;
    private static final int TAB_POR_RUBRO=2;
    
    private static final int COLUMNA_ESTUDIANTE_INSCRITO=0;
    private static final int COLUMNA_SELECCION_ESTUDIANTE=1;
    

    private static final String PATH_REPORTE_TMP = "tmp/reporteDeuda.pdf";

    private NotificacionesDeudasModel instanceThis = this;
    /**
     * Listado donde van a estar el listado de todos los rubros para enviar al
     * correo
     */
    private List<RubrosNivel> listaRubros;

    /**
     * Hilo para procesar el envio de las notificaciones
     */
    private Thread hiloNotificaciones;
    
    /**
     *  
     */
    private List<NotificacionDeudaImprimir>  notificacionesDeudaImprimir;
    
    public static final String ETIQUETA_NOMBRE_ESTUDIANTE = "[nombre_estudiante]";
    public static final String ETIQUETA_NOMBRE_REPRESENTANTE = "[nombre_representante]";
    
    private DefaultListModel<RubroPlantillaMes> modeloLista;
    
    
    
    /**
     * Lista donde van a estar cargado los datos consultados para el reporte
     */
    private Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante;

    public NotificacionesDeudasModel() {
        listaExclusionComponentes.add(getTxtFormatoMensaje()); //Agrego a la lista de exluciones para evitar que valide cuando existan datos ingresados
    }
    
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarVariables();
        iniciarCombos();
        listenerCombos();
        listenerBotones();
        listenerChecks();
        notificacionesDeudaImprimir = new ArrayList<>();
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
        
        try {
            
            cargarDatosReporte();
                        
            Map<String, Object> parametros = new HashMap<String, Object>();
            
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream path =RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_ACADEMICO, "reporte_deuda_imprimir.jrxml"));
            InputStream pathSubReporte = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_ACADEMICO, "subreporte_deuda_imprimir.jrxml"));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(pathSubReporte);
            
            parametros.put("subreporte_datos",reportPiePagina);
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, notificacionesDeudaImprimir, panelPadre, "Reporte Academico Deudas");           
        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private Map<EstudianteInscrito,List<RubroEstudiante>> obtenerSoloDatosSeleccionado()
    {
        Map<EstudianteInscrito,List<RubroEstudiante>> mapSeleccionados=new HashMap<EstudianteInscrito, List<RubroEstudiante>>();
         for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapRubrosEstudiante.entrySet()) 
         {
            EstudianteInscrito estudianteInscrito = entry.getKey();
            List<RubroEstudiante> rubros=entry.getValue();
            
            //Busco cada dato en la tabla para ver si no sta seleccionado para no agregar
            if(buscarEstudianteInscritoSeleccionadoTabla(estudianteInscrito))
            {
                mapSeleccionados.put(estudianteInscrito, rubros);
            }
            
         }
         return mapSeleccionados;
    }
    
    private boolean buscarEstudianteInscritoSeleccionadoTabla(EstudianteInscrito estudianteInscrito)
    {
        DefaultTableModel modeloTablaEstudiantes = (DefaultTableModel) getTblEstudiantes().getModel();
        for (int i = 0; i < modeloTablaEstudiantes.getRowCount(); i++) {
            boolean seleccionado=(boolean) modeloTablaEstudiantes.getValueAt(i,COLUMNA_SELECCION_ESTUDIANTE);
            
            if(seleccionado)
            {
                EstudianteInscrito estudianteInscritoTmp=(EstudianteInscrito) modeloTablaEstudiantes.getValueAt(i,COLUMNA_ESTUDIANTE_INSCRITO);
                if(estudianteInscritoTmp.equals(estudianteInscrito))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void cargarDatosReporte()
    {
        notificacionesDeudaImprimir=new ArrayList<NotificacionDeudaImprimir>();
        Map<EstudianteInscrito,List<RubroEstudiante>> mapSeleccionados= obtenerSoloDatosSeleccionado();
        /**
         * Llenar datos
         */
        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapSeleccionados.entrySet()) {
            EstudianteInscrito estudianteInscrito = entry.getKey();
            List<RubroEstudiante> detalles = entry.getValue();
            
            List<EstudianteDeudaData> listaReporte = new ArrayList<EstudianteDeudaData>();
            
            BigDecimal total = BigDecimal.ZERO;
            for (RubroEstudiante detalle : detalles) {
                total = total.add(detalle.getSaldo());
                String fechaReporte="";
                
                if(detalle.getFechaGenerado()!=null)
                {
                    fechaReporte=UtilidadesFecha.formatoDiaMesAño(detalle.getFechaGenerado());
                }
                listaReporte.add(new EstudianteDeudaData(detalle.getRubroNivel().getNombre(), detalle.getSaldo().toString(),fechaReporte));
            }
            
            //Agrego parametros y lista, para tener para imprimir
            NotificacionDeudaImprimir ndi = new NotificacionDeudaImprimir();
            ndi.setDeudas(listaReporte);
            ndi.setPeriodo(estudianteInscrito.getNivelAcademico().getPeriodo().getNombre());
            ndi.setCurso(estudianteInscrito.getNivelAcademico().getNombre());
            ndi.setNombres(estudianteInscrito.getEstudiante().getNombreCompleto());
            ndi.setRepresentante((estudianteInscrito.getEstudiante().getRepresentante() == null) ? "" : estudianteInscrito.getEstudiante().getRepresentante().getNombresCompletos());
            ndi.setNota("");
            ndi.setTotal(total.toString());
            ndi.estudiante=estudianteInscrito.getEstudiante();
            notificacionesDeudaImprimir.add(ndi);
        }
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
        this.listaRubros = new ArrayList<RubrosNivel>();
        this.getCmbTipoRubroPorRubro().setSelectedIndex(0);
        this.getCmbPeriodo().setSelectedIndex(0);
    }

//    @Override
    public String getNombre() {
        return "Notificacion Deudas";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, false);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarCombos() {

        try {
            //Cargar los periodos activos
            List<Periodo> periodosActivos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            getCmbPeriodo().removeAllItems();
            for (Periodo periodosActivo : periodosActivos) {
                getCmbPeriodo().addItem(periodosActivo);
            }

            //Cargar los catalogos de los combos
            getCmbTipoRubroGrupo().removeAllItems();
            getCmbTipoRubroPorMes().removeAllItems();
            getCmbTipoRubroPorRubro().removeAllItems();

            List<CatalogoProducto> tipoRubros = ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerPorModulo(ModuloCodefacEnum.GESTIONA_ACADEMICA);
            for (CatalogoProducto catalogo : tipoRubros) {
                getCmbTipoRubroGrupo().addItem(catalogo);
                getCmbTipoRubroPorMes().addItem(catalogo);
                getCmbTipoRubroPorRubro().addItem(catalogo);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerCombos() {
        
        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
                if(periodo!=null)
                {
                    cargarMesesPeriodo(periodo);
                }
            }
        });
        
        getCmbTipoRubroPorRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                    CatalogoProducto catalogoProducto = (CatalogoProducto) getCmbTipoRubroPorRubro().getSelectedItem();

                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("periodo", periodo);
                    mapParametros.put("catalogoProducto", catalogoProducto);
                    mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

                    List<RubrosNivel> listaRubros = ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);

                    //Cargar la lista de rubros segun la seleccion del combo
                    getCmbRubro().removeAllItems();
                    for (RubrosNivel rubro : listaRubros) {
                        getCmbRubro().addItem(rubro);
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    /**
     * Construye una tabla con los datos segun los rubros ingresados
     */
    private void construirTablaRubros() {
        String[] titulo = {"Nombre", "Valor"};
        DefaultTableModel modelo = new DefaultTableModel(titulo, 0);

        for (RubrosNivel rubroNivel : listaRubros) {
            String[] fila = {rubroNivel.getNombre(), rubroNivel.getValor().toString()};
            modelo.addRow(fila);
        }

        getTblRubros().setModel(modelo);

    }

    /**
     * Agrega un rubro a la lista de la tabla individualmente
     *
     * @param rubrosNivel
     */
    private void agregarRubro(RubrosNivel rubrosNivel) {
        if (!listaRubros.contains(rubrosNivel)) {
            listaRubros.add(rubrosNivel);
        }
    }

    /**
     * Agrega un conjunto de rubros a la lista
     *
     * @param rubros
     */
    private void agregarRubroLista(List<RubrosNivel> rubros) {
        for (RubrosNivel rubro : rubros) {
            if (!listaRubros.contains(rubro)) {
                listaRubros.add(rubro);
            }
        }
    }
    
    private void agregarRubroPorMes()
    {
        try {
            Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
            CatalogoProducto catalogoProducto = (CatalogoProducto) getCmbTipoRubroPorMes().getSelectedItem();
            List<RubroPlantillaMes> mesesSeleccionados = obtenerMesesEnum();

            List<RubrosNivel> listaRubros = ServiceFactory.getFactory().getRubrosNivelServiceIf().buscarPorPeriodoYMeses(periodo, catalogoProducto, mesesSeleccionados);

            agregarRubroLista(listaRubros);
            construirTablaRubros();

        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void agregarRubroPorGrupo()
    {
        try {
            Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
            CatalogoProducto tipoRubro = (CatalogoProducto) getCmbTipoRubroGrupo().getSelectedItem();

            Map<String, Object> mapParametros = new HashMap<String, Object>();
            mapParametros.put("periodo", periodo);
            mapParametros.put("catalogoProducto", tipoRubro);

            List<RubrosNivel> rubros = ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
            agregarRubroLista(rubros);
            construirTablaRubros();

        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void agregarPorRubro()
    {
        RubrosNivel rubroNivel = (RubrosNivel) getCmbRubro().getSelectedItem();
        agregarRubro(rubroNivel);
        construirTablaRubros();
    }
    
    private void consultarDatosRubros()
    {
        try {
            List<RubroEstudiante> rubrosEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosEstudiantesPorRubros(listaRubros);
            mapRubrosEstudiante = convertirMapRubrosEstudiante(rubrosEstudiante);
        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerBotones() {
        
        getBtnConsultarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarDatosRubros();
                //cargarDatosReporte();
                construirTablaEstudiantes();
            }

        });
        
        getBtnAgregarRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tabSeleccionado=getTabOpciones().getSelectedIndex();
                switch(tabSeleccionado)
                {
                    case TAB_POR_MES:
                        agregarRubroPorMes();
                        break;

                    case TAB_POR_GRUPO:
                        agregarRubroPorGrupo();
                        break;

                    case TAB_POR_RUBRO:
                        agregarPorRubro();
                        break;

                }
            }
        });
        
        getBtnImprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimir();
            }
        });
        
        getBtnAgregarMes().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubroPlantillaMes rubroPlantillaMeS=(RubroPlantillaMes) getCmbMesFiltro().getSelectedItem();
                modeloLista.addElement(rubroPlantillaMeS);
                getLstFiltrosMes().setModel(modeloLista);                
            }
        });
        
        getBtnEliminarMes().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getLstFiltrosMes().getSelectedIndex()>=0)
                {
                    RubroPlantillaMes rubroPlantillaMes=(RubroPlantillaMes) modeloLista.get(getLstFiltrosMes().getSelectedIndex());
                    modeloLista.removeElement(rubroPlantillaMes);
                    getLstFiltrosMes().setModel(modeloLista);
                }
            }
        });

        getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //enviarComunicados();
                hiloNotificaciones = new Thread(instanceThis);
                hiloNotificaciones.start();                
            }
        });

        getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaRubros.clear();
                construirTablaRubros();
            }
        });

        getBtnQuitar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTblRubros().getSelectedRow();

                if (fila >= 0) {
                    //RubrosNivel rubroNivel=listaRubros.get(fila);
                    listaRubros.remove(fila);
                    construirTablaRubros();
                }

            }
        });


    }

    private List<RubroPlantillaMes> obtenerMesesEnum() {
        List<RubroPlantillaMes> listaMeses = new ArrayList<RubroPlantillaMes>();
        for (int i = 0; i < getLstFiltrosMes().getModel().getSize(); i++) {
            listaMeses.add(getLstFiltrosMes().getModel().getElementAt(i));
        }
        return listaMeses;

    }

    /**
     * Obtiene una version del monitor del comprobante para mostrar el avance de
     * las notificaciones enviadas
     *
     * @return
     */
    private MonitorComprobanteData getMonitorComprobanteData() {
        //Obtener una instancia del monitor para mostrar el avance de los datos
        MonitorComprobanteData monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();

        monitorData.getLblPreimpreso().setText("enviado notificaciones");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString("enviando notificaciones");
        monitorData.getBarraProgreso().setStringPainted(true);

        return monitorData;

    }

    private String construirMensaje(EstudianteInscrito estudianteInscrito) {
        String mensaje = getTxtFormatoMensaje().getText();

        mensaje = mensaje.replace(ETIQUETA_NOMBRE_ESTUDIANTE, estudianteInscrito.getEstudiante().getNombreCompleto());
        mensaje = mensaje.replace(ETIQUETA_NOMBRE_REPRESENTANTE, estudianteInscrito.getEstudiante().getRepresentante().getNombresCompletos());

        return mensaje;
    }

    private void enviarComunicados() {
        
        //Validación previa 
        if (getTxtCorreoTitulo().getText().isEmpty()) {
            DialogoCodefac.mensaje("Advertencia", "Porfavor ingrese un titulo para enviar el correo", DialogoCodefac.MENSAJE_INCORRECTO);
            return;
        }

        Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
        if (listaRubros.size() > 0) {
            //List<RubroEstudiante> rubrosEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosEstudiantesPorRubros(listaRubros);
            
            Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante=obtenerSoloDatosSeleccionado();
            if(mapRubrosEstudiante.size()==0)
            {
                DialogoCodefac.mensaje("Advertencia","No existen estudiantes registrados con los rubros ingresados, \nno hay datos para enviar al correo",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }
            else
            {
                DialogoCodefac.mensaje("Correcto", "Las notificaciones se estan enviado , puede revisar en el monitor", DialogoCodefac.MENSAJE_CORRECTO);
            }
            MonitorComprobanteData monitorData = getMonitorComprobanteData();
            MonitorComprobanteModel.getInstance().mostrar();
            //Obtiene la lista de rubros agrupada por los estudiantes inscritos
            //Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante = convertirMapRubrosEstudiante(rubrosEstudiante);
            
            int contador = 0;
            for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapRubrosEstudiante.entrySet()) {
                
                EstudianteInscrito estudianteInscrito = entry.getKey();
                if(estudianteInscrito.getEstudiante().getRepresentante()!=null)
                {
                    contador++;
                    List<RubroEstudiante> detalle = entry.getValue();
                    
                    //Generar el reporte
                    generarReporte(estudianteInscrito, detalle, periodo);
                    //Enviar al correo
                    String mensaje = construirMensaje(estudianteInscrito);
                    
                    enviarCorreo(estudianteInscrito, mensaje,getTxtCorreoTitulo().getText());
                    System.out.println("estudiante: " + estudianteInscrito.getEstudiante().getNombreCompleto());
                    
                    double relacion = (double) contador / (double) mapRubrosEstudiante.size();
                    int porcentaje = (int) (relacion * 100);
                    monitorData.getBarraProgreso().setValue(porcentaje);
                }

            }
            //Mostrar el monitor cuando termina
            monitorData.getBarraProgreso().setForeground(Color.GREEN);
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DialogoCodefac.mensaje("Correcto", "Se enviaron " + mapRubrosEstudiante.size() + " notificaciones a los correos", DialogoCodefac.MENSAJE_CORRECTO);
                }
            });

        } else {
            DialogoCodefac.mensaje("Advertencia", "No existen datos para enviar", DialogoCodefac.MENSAJE_INCORRECTO);
        }

    }

    private void enviarCorreo(EstudianteInscrito estudianteInscrito, String mensaje,String titulo) {
        CorreoCodefac correoCodefac = new CorreoCodefac() {
            @Override
            public String getMensaje() {
                return mensaje;
            }

            @Override
            public String getTitulo() {
                return titulo;
            }

            @Override
            public Map<String, String> getPathFiles() {
                HashMap<String, String> mapArchivos = new HashMap<String, String>();
                mapArchivos.put("comunicado.pdf", PATH_REPORTE_TMP);
                return mapArchivos;
            }

            @Override
            public List<String> getDestinatorios() {
                List<String> destinatarios = new ArrayList<String>();
                destinatarios.add(estudianteInscrito.getEstudiante().getRepresentante().getCorreoElectronico());
                return destinatarios;
            }
        };

        correoCodefac.enviarCorreo();
    }
    
    private void construirTablaEstudiantes() {
        String[] titulos={"","Enviar","Estudiante","Curso","Representante","Email"};
        Class[] opciones={EstudianteInscrito.class,Boolean.class,String.class,String.class,String.class,String.class};
        Boolean[] puedeEditar={false,true,false,false,false,false};
        
        getChkSeleccionarTodos().setSelected(true);
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulos, opciones, puedeEditar);
        
        //private Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante;
        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapRubrosEstudiante.entrySet()) {
            EstudianteInscrito estudianteInscrito = entry.getKey();
            Persona representante=estudianteInscrito.getEstudiante().getRepresentante();
            String correoRepresentante="";
            if(representante!=null)
            {
                if(representante.getCorreoElectronico()!=null && !representante.getCorreoElectronico().isEmpty())
                {
                    correoRepresentante=representante.getCorreoElectronico();
                }
            }
            
            modeloTabla.addRow(new Object[]{
                estudianteInscrito,
                true,
                estudianteInscrito.getEstudiante().getNombreCompleto(),
                estudianteInscrito.getNivelAcademico().getNombre(),
                (representante!=null)?representante.getNombresCompletos():"",
                correoRepresentante,
            });
            
        }
        
        
        getTblEstudiantes().setModel(modeloTabla);
        Integer[] tamanioTabla={0,50,250,100};
        //UtilidadesTablas.cambiarTamanioColumnas(getTblEstudiantes(), tamanioTabla);
        UtilidadesTablas.definirTamanioColumnas(getTblEstudiantes(), tamanioTabla);
        UtilidadesTablas.ocultarColumna(getTblEstudiantes(),0);
    }

    private void generarReporte(EstudianteInscrito estudianteInscrito, List<RubroEstudiante> detalles, Periodo periodo) { 
        
        InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporte_estudiante_deuda.jrxml");

        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("periodo", periodo.getNombre());
        mapParametros.put("curso", estudianteInscrito.getNivelAcademico().getNombre());
        mapParametros.put("nombres", estudianteInscrito.getEstudiante().getNombreCompleto());
        mapParametros.put("representante",(estudianteInscrito.getEstudiante().getRepresentante()==null)?"":estudianteInscrito.getEstudiante().getRepresentante().getNombresCompletos());
        mapParametros.put("nota", "");

        List<EstudianteDeudaData> listaReporte = new ArrayList<EstudianteDeudaData>();

        BigDecimal total = BigDecimal.ZERO;
        for (RubroEstudiante detalle : detalles) {
            total = total.add(detalle.getSaldo());
            String fechaReporte = UtilidadesFecha.formatoDiaMesAño(detalle.getFechaGenerado());
            listaReporte.add(new EstudianteDeudaData(detalle.getRubroNivel().getNombre(), detalle.getSaldo().toString(),fechaReporte));
        }

        mapParametros.put("total", total.toString());

        mapParametros = ReporteCodefac.agregarMapPlantilla(mapParametros, "Reporte Deuda", panelPadre);
        
        UtilidadesComprobantes.generarReporteJasper(path, mapParametros, listaReporte, PATH_REPORTE_TMP);
    } 

    private Map<EstudianteInscrito, List<RubroEstudiante>> convertirMapRubrosEstudiante(List<RubroEstudiante> rubrosEstudiante) {
        Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante = new HashMap<EstudianteInscrito, List<RubroEstudiante>>();

        for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {

            List<RubroEstudiante> detalles = mapRubrosEstudiante.get(rubroEstudiante.getEstudianteInscrito());
            if (detalles == null) {
                detalles = new ArrayList<RubroEstudiante>();
                detalles.add(rubroEstudiante);
                mapRubrosEstudiante.put(rubroEstudiante.getEstudianteInscrito(), detalles);
            } else {
                detalles.add(rubroEstudiante);
            }

        }

        return mapRubrosEstudiante;
    }
    
    private void cargarMesesPeriodo(Periodo periodo) {
        List<RubroPlantillaMes> Meses=periodo.obtenerTodosMesesGenerar();
        getCmbMesFiltro().removeAllItems();
        for (RubroPlantillaMes mes : Meses) {
            getCmbMesFiltro().addItem(mes);
        }
    }

    @Override
    public void run() {
        enviarComunicados();
    }

    private void iniciarVariables() {
        modeloLista=new DefaultListModel();
        getPnlSms().setControlador(this); //Iniciar panel de envio de mensajes con el controlador de sms
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public boolean getValidacionEnvioSms() {
        return true; //TODO: por el momento no hago ninguna validacion
    }


    @Override
    public VentanaEnum getVentanaEnum() {
        return VentanaEnum.NOTIFICACION_DEUDAS;
    }

    @Override
    public List<ComponenteEnvioSmsData> getDataSms() {
        
        List<String> listaNoEnviado=new ArrayList<String>();
        
        List<ComponenteEnvioSmsData> lista=new ArrayList<ComponenteEnvioSmsData>();
        for (NotificacionDeudaImprimir notificaciones : notificacionesDeudaImprimir) {
            
            Map<PlantillaSmsEnum.EtiquetaEnum,String>mapParametros=new HashMap<PlantillaSmsEnum.EtiquetaEnum,String>();
            mapParametros.put(PlantillaSmsEnum.EtiquetaEnum.ESTUDIANTE_NOMBRE,notificaciones.estudiante.getNombreSimple());
            mapParametros.put(PlantillaSmsEnum.EtiquetaEnum.VALOR_PENDIENTE,notificaciones.getTotal());
            
            String numeroRepresentante=(notificaciones.estudiante.getRepresentante()!=null)?notificaciones.estudiante.getRepresentante().getTelefonoCelular():"";
            if(!numeroRepresentante.equals(""))
            {
                ComponenteEnvioSmsData componenteSms = new ComponenteEnvioSmsData(numeroRepresentante, mapParametros);
                lista.add(componenteSms);
            }
            else
            {
                listaNoEnviado.add(notificaciones.estudiante.getNombreSimple());
            }
        }
        
        /**
         * Lista de estudiantes que no se puede enviar porque no tienen asigando un representante con numero de celular
         */
        if(listaNoEnviado.size()>0)
        {
            String mensaje="Los siguiente estudiantes no se pueden enviar las notificaciones SMS:\n";
            
            for (String noEnviados : listaNoEnviado) {
                mensaje+="-"+noEnviados+"\n";
            }
            
            DialogoCodefac.mensaje("Sms no enviados",mensaje,DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        
        return lista;
        
    }

    @Override
    public EnvioMensajesCallBackInterface getInterfaceCallback() {
        try {
            return new EnvioMensajesCallBack();
        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void listenerChecks() {
        getChkSeleccionarTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seleccionar=getChkSeleccionarTodos().isSelected();
                
                DefaultTableModel modeloTabla=(DefaultTableModel) getTblEstudiantes().getModel();
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    modeloTabla.setValueAt(seleccionar, i,COLUMNA_SELECCION_ESTUDIANTE); 
                    
                }
                
            }
        });
    }

}
