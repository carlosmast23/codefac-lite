/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_RIDE;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.gestionacademica.other.EstudianteDeudaData;
import ec.com.codesoft.codefaclite.gestionacademica.other.NotificacionDeudaImprimir;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NotificacionesDeudasPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos
 */
public class NotificacionesDeudasModel extends NotificacionesDeudasPanel implements Runnable {

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

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarCombos();
        listenerCombos();
        listenerBotones();
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
            List<RubroEstudiante> rubrosEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosEstudiantesPorRubros(listaRubros);
            Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante = convertirMapRubrosEstudiante(rubrosEstudiante);            
            /**
             * Llenar datos
             */
            for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapRubrosEstudiante.entrySet()) {
                EstudianteInscrito estudianteInscrito = entry.getKey();
                List<RubroEstudiante> detalles = entry.getValue();
                
                List<EstudianteDeudaData> listaReporte = new ArrayList<EstudianteDeudaData>();

                BigDecimal total = BigDecimal.ZERO;
                for (RubroEstudiante detalle : detalles) {
                    total = total.add(detalle.getRubroNivel().getValor());
                    listaReporte.add(new EstudianteDeudaData(detalle.getRubroNivel().getNombre(), detalle.getRubroNivel().getValor().toString()));
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
                notificacionesDeudaImprimir.add(ndi);
            }
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
    }

    @Override
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
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
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
        getCmbTipoRubroPorRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                    CatalogoProducto catalogoProducto = (CatalogoProducto) getCmbTipoRubroPorRubro().getSelectedItem();

                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("periodo", periodo);
                    mapParametros.put("catalogoProducto", catalogoProducto);

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

    private void listenerBotones() {

        getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //enviarComunicados();
                hiloNotificaciones = new Thread(instanceThis);
                hiloNotificaciones.start();
                DialogoCodefac.mensaje("Correcto", "Las notificaciones se estan enviado , puede revisar en el monitor", DialogoCodefac.MENSAJE_CORRECTO);
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

        getBtnAgregarPorMes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                    CatalogoProducto catalogoProducto = (CatalogoProducto) getCmbTipoRubroPorMes().getSelectedItem();
                    List<MesEnum> mesesSeleccionados = obtenerMesesEnum();

                    List<RubrosNivel> listaRubros = ServiceFactory.getFactory().getRubrosNivelServiceIf().buscarPorPeriodoYMeses(periodo, catalogoProducto, mesesSeleccionados);

                    agregarRubroLista(listaRubros);
                    construirTablaRubros();

                } catch (RemoteException ex) {
                    Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        getBtnAgregarPorRubro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubrosNivel rubroNivel = (RubrosNivel) getCmbRubro().getSelectedItem();

                agregarRubro(rubroNivel);
                construirTablaRubros();
            }
        });

        getBtnAgregarPorGrupo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });
    }

    private List<MesEnum> obtenerMesesEnum() {
        List<MesEnum> listaMeses = new ArrayList<MesEnum>();

        if (getChkEnero().isSelected()) {
            listaMeses.add(MesEnum.ENERO);
        }

        if (getChkFebrero().isSelected()) {
            listaMeses.add(MesEnum.FEBRERO);
        }

        if (getChkMarzo().isSelected()) {
            listaMeses.add(MesEnum.MARZO);
        }

        if (getChkAbril().isSelected()) {
            listaMeses.add(MesEnum.ABRIL);
        }

        if (getChkMayo().isSelected()) {
            listaMeses.add(MesEnum.MAYO);
        }
        if (getChkJunio().isSelected()) {
            listaMeses.add(MesEnum.JUNIO);
        }

        if (getChkJulio().isSelected()) {
            listaMeses.add(MesEnum.JULIO);
        }
        if (getChkAgosto().isSelected()) {
            listaMeses.add(MesEnum.AGOSTO);
        }

        if (getChkSeptiembre().isSelected()) {
            listaMeses.add(MesEnum.SEPTIEMBRE);
        }

        if (getChkOctubre().isSelected()) {
            listaMeses.add(MesEnum.OCTUBRE);
        }

        if (getChkNoviembre().isSelected()) {
            listaMeses.add(MesEnum.NOVIEMBRE);
        }
        if (getChkDiciembre().isSelected()) {
            listaMeses.add(MesEnum.DICIEMBRE);
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
        Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
        if (listaRubros.size() > 0) {
            try {
                List<RubroEstudiante> rubrosEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerRubrosEstudiantesPorRubros(listaRubros);

                MonitorComprobanteData monitorData = getMonitorComprobanteData();
                MonitorComprobanteModel.getInstance().mostrar();

                //Obtiene la lista de rubros agrupada por los estudiantes inscritos
                Map<EstudianteInscrito, List<RubroEstudiante>> mapRubrosEstudiante = convertirMapRubrosEstudiante(rubrosEstudiante);

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
                        enviarCorreo(estudianteInscrito, mensaje);
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

            } catch (RemoteException ex) {
                Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            DialogoCodefac.mensaje("Advertencia", "No existen datos para enviar", DialogoCodefac.MENSAJE_INCORRECTO);
        }

    }

    private void enviarCorreo(EstudianteInscrito estudianteInscrito, String mensaje) {
        CorreoCodefac correoCodefac = new CorreoCodefac() {
            @Override
            public String getMensaje() {
                return mensaje;
            }

            @Override
            public String getTitulo() {
                return "Comunicado deudas";
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
            total = total.add(detalle.getRubroNivel().getValor());
            listaReporte.add(new EstudianteDeudaData(detalle.getRubroNivel().getNombre(), detalle.getRubroNivel().getValor().toString()));
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

    @Override
    public void run() {
        enviarComunicados();
    }

}
