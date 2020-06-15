/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteInscritoDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.NivelAcademicoDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NotificacionEstudiantesPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EtiquetaMensajeEnum;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class NotificacionEstudiantesModel extends NotificacionEstudiantesPanel {

    private List<EstudianteInscrito> estudiantesIngresados;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        placeHolder();
        popUps();
        listenerBotones();
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
        estudiantesIngresados = new ArrayList<EstudianteInscrito>();
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
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

    private void placeHolder() {
        UtilidadesSwingX.placeHolder("Titulo Mensaje", getTxtTitulo());
        UtilidadesSwingX.placeHolder("Mensaje de Texto", getTxtMensaje());
    }

    private void popUps() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        for (EtiquetaMensajeEnum etiqueta : EtiquetaMensajeEnum.values()) {
            JMenuItem menuITem = new JMenuItem(etiqueta.getNombre());
            menuITem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getTxtMensaje().setText(getTxtMensaje().getText() + etiqueta.getEtiqueta());

                }
            });
            jPopupMenu.add(menuITem);
        }

        //jPopupMenu.add(menuITem);
        getTxtMensaje().setComponentPopupMenu(jPopupMenu);

        /**
         * ==============> popup tabla <==================
         */
        JPopupMenu jPopupMenuTable = new JPopupMenu();
        JMenuItem menuITem = new JMenuItem("Eliminar");
        menuITem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = getTblDatos().getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int[] filaInicial = getTblDatos().getSelectedRows();

                    //Seleccionar los datos a eliminar
                    List<EstudianteInscrito> estudiantesParaEliminar = new ArrayList<EstudianteInscrito>();
                    for (int fila : filaInicial) {
                        estudiantesParaEliminar.add(estudiantesIngresados.get(fila));
                    }

                    for (EstudianteInscrito estudianteInscrito : estudiantesParaEliminar) {
                        estudiantesIngresados.remove(estudianteInscrito);
                    }

                    actualizarTabla();
                }
            }
        });
        jPopupMenuTable.add(menuITem);

        getTblDatos().setComponentPopupMenu(jPopupMenuTable);
    }

    private Periodo obtenerPeriodoActivo() {
        try {
            Periodo periodo = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerUnicoPeriodoActivo();
            if (periodo == null) //ERROR CUANDO NO EXISTE NINGUN PERIODO ACTIVO
            {
                DialogoCodefac.dialogoPregunta("Error", "No existe ningun periodo activo", DialogoCodefac.MENSAJE_INCORRECTO);
                return null;
            }
            return periodo;
        } catch (RemoteException ex) {
            Logger.getLogger(NotificacionEstudiantesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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

    private void enviarCorreo(EstudianteInscrito estudianteInscrito, String mensaje, String titulo) {
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
                //mapArchivos.put("comunicado.pdf", PATH_REPORTE_TMP);
                return mapArchivos;
            }

            @Override
            public List<String> getDestinatorios() {
                List<String> destinatarios = new ArrayList<String>();
                Persona representante = (estudianteInscrito.getEstudiante().getRepresentante() != null) ? estudianteInscrito.getEstudiante().getRepresentante() : estudianteInscrito.getEstudiante().getRepresentante2();
                destinatarios.add((representante != null) ? representante.getCorreoElectronico() : "");
                return destinatarios;
            }
        };

        try {
            correoCodefac.enviarCorreo(session.getEmpresa());
            //Todo:Ver si poner un mensaje para saber que el correo fue enviado correctamente
        } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
            Logger.getLogger(NotificacionesDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error", "No se pudo enviar el correo \n\nProblema:\n", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    private String construirMensaje(EstudianteInscrito estudianteInscrito) {
        String mensaje = getTxtMensaje().getText().replace("\n", "<br>");

        mensaje = mensaje.replace(EtiquetaMensajeEnum.ETIQUETA_NOMBRE_ESTUDIANTE.getEtiqueta(), estudianteInscrito.getEstudiante().getNombreCompleto());
        mensaje = mensaje.replace(EtiquetaMensajeEnum.ETIQUETA_NOMBRE_REPRESENTANTE.getEtiqueta(), estudianteInscrito.getEstudiante().getRepresentante().getNombresCompletos());

        return mensaje;
    }

    private void enviarCorreos() {
        if (getTxtTitulo().getText().isEmpty()) {
            DialogoCodefac.mensaje("Advertencia", "Por favor ingrese un titulo para enviar el correo", DialogoCodefac.MENSAJE_INCORRECTO);
            return;
        }

        if (getTxtMensaje().getText().isEmpty()) {
            DialogoCodefac.mensaje("Advertencia", "Por favor ingrese un mensaje para enviar el correo", DialogoCodefac.MENSAJE_INCORRECTO);
            return;
        }

        MonitorComprobanteData monitorData = getMonitorComprobanteData();
        MonitorComprobanteModel.getInstance().mostrar();
        
        int contador = 0;
        for (EstudianteInscrito estudiantesIngresado : estudiantesIngresados) {
            contador++;
            enviarCorreo(estudiantesIngresado, construirMensaje(estudiantesIngresado), getTxtTitulo().getText());

            //Setear la cantidad segun el total de datos enviados
            double relacion = (double) contador / (double) estudiantesIngresados.size();
            int porcentaje = (int) (relacion * 100);
            monitorData.getBarraProgreso().setValue(porcentaje);
        }

        //Mostrar el monitor cuando termina
        monitorData.getBarraProgreso().setForeground(Color.GREEN);
        monitorData.getBtnAbrir().setEnabled(true);
        monitorData.getBtnCerrar().setEnabled(true);
        monitorData.getBtnAbrir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoCodefac.mensaje("Correcto", "Se enviaron " + estudiantesIngresados.size() + " notificaciones a los correos", DialogoCodefac.MENSAJE_CORRECTO);
            }
        });

    }

    private void listenerBotones() {

        getBtnEnviarPorCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        enviarCorreos();
                    }
                }).start();
            }
        });

        getBtnAgregarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo = obtenerPeriodoActivo();
                if (periodo == null) //ERROR CUANDO NO EXISTE NINGUN PERIODO ACTIVO
                {
                    return;
                }
                EstudianteInscritoDialogo dialog = new EstudianteInscritoDialogo(periodo);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(dialog);
                buscarDialogoModel.setVisible(true);
                EstudianteInscrito estudianteInscrito = (EstudianteInscrito) buscarDialogoModel.getResultado();
                if (estudianteInscrito != null) {
                    agregarEstudiante(estudianteInscrito);
                    actualizarTabla();
                }

            }
        });

        getBtnAgregarDesdeCurso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo = obtenerPeriodoActivo();
                    if (periodo == null) //ERROR CUANDO NO EXISTE NINGUN PERIODO ACTIVO
                    {
                        return;
                    }
                    NivelAcademicoDialogo dialog = new NivelAcademicoDialogo(periodo);
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(dialog);
                    buscarDialogoModel.setVisible(true);
                    NivelAcademico curso = (NivelAcademico) buscarDialogoModel.getResultado();
                    if (curso != null) {
                        List<EstudianteInscrito> estudianteInscritos = ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritos(curso, periodo);
                        agregarEstudiantes(estudianteInscritos);
                        actualizarTabla();

                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(NotificacionEstudiantesModel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getBtnAgregarTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodo = obtenerPeriodoActivo();
                    if (periodo == null) //ERROR CUANDO NO EXISTE NINGUN PERIODO ACTIVO
                    {
                        return;
                    }

                    List<EstudianteInscrito> estudianteInscritos = ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerEstudiantesInscritosPorPeriodo(periodo);
                    agregarEstudiantes(estudianteInscritos);
                    actualizarTabla();

                } catch (RemoteException ex) {
                    Logger.getLogger(NotificacionEstudiantesModel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Metodo para agregar estudiantes a la lista del modelo para enviar los
     * mensajes
     *
     * @param estudiantesAgregar
     */
    private void agregarEstudiantes(List<EstudianteInscrito> estudiantesAgregar) {
        for (EstudianteInscrito estudianteInscrito : estudiantesAgregar) {
            agregarEstudiante(estudianteInscrito);
        }
    }

    private void agregarEstudiante(EstudianteInscrito estudianteInscrito) {
        if (!estudiantesIngresados.contains(estudianteInscrito)) {
            estudiantesIngresados.add(estudianteInscrito);
        }

    }

    private void actualizarTabla() {
        String[] titulo = {"Nombres",
            "Curso",
            "Representante",
            "Correo"
        };

        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0);

        if (estudiantesIngresados != null) {
            for (EstudianteInscrito estudiantesIngresado : estudiantesIngresados) {
                Persona representante = (estudiantesIngresado.getEstudiante().getRepresentante() != null) ? estudiantesIngresado.getEstudiante().getRepresentante() : estudiantesIngresado.getEstudiante().getRepresentante2();
                String representanteNombre = (representante != null) ? representante.getNombresCompletos() : "";
                String representanteCorreo = (representante != null) ? representante.getCorreoElectronico() : "";
                modeloTabla.addRow(new String[]{
                    estudiantesIngresado.getEstudiante().getNombreCompleto(),
                    estudiantesIngresado.getNivelAcademico().getNombre(),
                    representanteNombre,
                    representanteCorreo,});
            }
        }

        getTblDatos().setModel(modeloTabla);
    }

}
