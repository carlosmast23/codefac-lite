/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes.ComponenteEnvioSmsData;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteEnvioSmsInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.EstudiantePanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.EstudianteData;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteAcademicoData;
import ec.com.codesoft.codefaclite.inventario.busqueda.RepresentanteBusquedaDialogo;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDiscapacidadEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class EstudianteModel extends EstudiantePanel implements ComponenteEnvioSmsInterface,DialogInterfacePanel<Estudiante>{

    private Persona representante;
    private Persona representanteParaFacturar;
    private Estudiante estudiante;
    private EstudianteServiceIf estudianteService;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaNacimiento = null;
    String fechanacimiento = "";
    Boolean banderaNacionalidad;
    
    public EstudianteModel() {
        estudianteService = ServiceFactory.getFactory().getEstudianteServiceIf();
        listaExclusionComponentes.add(getTxtPorcentajeDiscapacidad()); //Agrego a la lista de exluciones para evitar que valide cuando existan datos ingresados
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListenerBotones();
        agregarListenerCombos();
        iniciarVariables();
        getDateFechaNacimiento().setDate(hoy());
        this.banderaNacionalidad = false;
    }

    @Override

    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresEstudiante(estudiante);
            estudiante=estudianteService.grabar(estudiante);
            DialogoCodefac.mensaje("Datos correctos", "El registro de estudiante se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar aula modelo");
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error", "Error de comunicación con el servidor , estudiantes", DialogoCodefac.MENSAJE_ADVERTENCIA);
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresEstudiante(Estudiante estudiante) {
        //estudiante.setCodigoSistema(getTxtCodSistema().getText());
        estudiante.setCodigoAuxiliar(getTxtCodAuxiliar().getText());
        estudiante.setCedula(getTxtCedula().getText());
        estudiante.setEmail(getTxtCorreo().getText());
        estudiante.setNombres(getTxtNombres().getText());
        estudiante.setApellidos(getTxtApellidos().getText());
        estudiante.setTelefono(getTxtTelefono().getText());
        estudiante.setCelular(getTxtCelular().getText());
        estudiante.setDireccion(getTxtDireccion().getText());
        estudiante.setDatosAdicionales(getTxtAdicionales().getText());
        estudiante.setNacionalidad(((Nacionalidad) getCmbNacionalidad().getSelectedItem()));
        if(banderaNacionalidad && getCmbEtnia().getSelectedItem()!=null){
            estudiante.setEtnia(getCmbEtnia().getSelectedItem().toString());
        }
        else{
            estudiante.setEtnia("Otro");
        }
        estudiante.setObsDiscapacidad(getTxtObsDiscapacidad().getText());
        estudiante.setConadis(getTxtConadis().getText());
        estudiante.setPorcentajeDiscapacidad(Integer.parseInt(getTxtPorcentajeDiscapacidad().getText()));

        estudiante.setTipoDiscapacidad(((TipoDiscapacidadEnum) getCmbTipoDiscapacidad().getSelectedItem()).getLetra());
        estudiante.setGenero(((GeneroEnum) getCmbGenero().getSelectedItem()).getEstado());
        estudiante.setEstado(((GeneralEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
        
        if(getCmbDiscapacidad().getSelectedItem()!=null)
        {
            estudiante.setDiscapacidad(((EnumSiNo) getCmbDiscapacidad().getSelectedItem()).getLetra());
        }

        if (getDateFechaNacimiento().getDate() != null) {
            fechaNacimiento = new Date(getDateFechaNacimiento().getDate().getTime());
            fechanacimiento = dateFormat.format(getDateFechaNacimiento().getDate());
            estudiante.setFechaNacimiento(fechaNacimiento);
        }

        estudiante.setRepresentante(representante);
        estudiante.setRepresentante2(representanteParaFacturar);

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresEstudiante(estudiante);
            estudianteService.editar(estudiante);
            DialogoCodefac.mensaje("Datos correctos", "El estudiante se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el estudiante?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion aula");
                }
                estudianteService.eliminarEstudiante(estudiante);
                DialogoCodefac.mensaje("Datos correctos", "El estudiante se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void imprimir() {
        if(estudiante != null)
        {   
            InputStream path = RecursoCodefac.JASPER_ESTUDIANTE.getResourceInputStream("reporteEstudiante.jrxml");
            Map parametros = new HashMap();
            //parametros.put("codSistema", estudiante.getCodigoSistema()+"");
            parametros.put("codAuxiliar", estudiante.getCodigoAuxiliar()+"");
            parametros.put("cedula", estudiante.getCedula()+"");
            parametros.put("correo", estudiante.getEmail()+"");
            parametros.put("nombres", estudiante.getNombres()+"");
            parametros.put("apellidos", estudiante.getApellidos()+"");
            parametros.put("telefono", estudiante.getTelefono()+"");
            parametros.put("celular", estudiante.getCelular()+"");
            parametros.put("direccion", estudiante.getDireccion()+"");
            parametros.put("datosAdicionales", estudiante.getDatosAdicionales()+"");
            parametros.put("etnia", estudiante.getEtnia()+"");
            parametros.put("conadis", estudiante.getConadis()+"");
            parametros.put("observacionesDiscapacidad", estudiante.getObsDiscapacidad()+"");
            parametros.put("porcentajeDiscapacidad", estudiante.getPorcentajeDiscapacidad()+"");
            parametros.put("genero", estudiante.getGenero()+"");
            parametros.put("nacionalidad", estudiante.getNacionalidad()+"");
            parametros.put("estado", estudiante.getEstado()+"");
            parametros.put("discapacidad", estudiante.getDiscapacidad()+"");
            parametros.put("fechaNacimiento", estudiante.getFechaNacimiento()+"");
            if(estudiante.getRepresentante() != null){
                
                parametros.put("representante1", estudiante.getRepresentante().getIdentificacion() +" - "+ estudiante.getRepresentante().getRazonSocial());
                parametros.put("representante2", estudiante.getRepresentante2().getIdentificacion() +" - "+ estudiante.getRepresentante().getRazonSocial());
            }
            
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, new ArrayList(), panelPadre, "Reporte Datos Estudiantes");
      
        }else
        {
            DialogoCodefac.mensaje("Error","Debe buscar un estudiante", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
             
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    @Override
    public void buscar() throws ExcepcionCodefacLite {
        EstudianteBusquedaDialogo aulaBusquedaDialogo = new EstudianteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        estudiante = (Estudiante) buscarDialogoModel.getResultado();
        if (estudiante == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar estudiante vacio");
        }
        
        cargarDatosPantalla();
        
    }*/
    
    private void cargarDatos()
    {
        getLblCodigoSistema().setText((estudiante.getIdEstudiante()==null)?"":estudiante.getIdEstudiante().toString());
        getTxtCodAuxiliar().setText(estudiante.getCodigoAuxiliar());
        getTxtCedula().setText(estudiante.getCedula());
        getTxtCorreo().setText(estudiante.getEmail());
        getTxtNombres().setText(estudiante.getNombres());
        getTxtApellidos().setText(estudiante.getApellidos());
        getTxtTelefono().setText(estudiante.getTelefono());
        getTxtCelular().setText(estudiante.getCelular());
        getTxtDireccion().setText(estudiante.getDireccion());
        getTxtAdicionales().setText(estudiante.getDatosAdicionales());
        getCmbEtnia().setSelectedItem(estudiante.getEtnia());
        getTxtConadis().setText(estudiante.getConadis());
        getTxtObsDiscapacidad().setText(estudiante.getObsDiscapacidad());
        getTxtPorcentajeDiscapacidad().setText(String.valueOf(estudiante.getPorcentajeDiscapacidad()));
        
        GeneroEnum generoEnum=GeneroEnum.getEnum(estudiante.getGenero());
        getCmbGenero().setSelectedItem(generoEnum);
        
        getCmbNacionalidad().setSelectedItem((estudiante.getNacionalidad()!=null)?estudiante.getNacionalidad():null);
        getCmbEstado().setSelectedItem(estudiante.getEstado());
        getCmbDiscapacidad().setSelectedItem((estudiante.getDiscapacidad()!=null)?estudiante.getDiscapacidad():null);
        getDateFechaNacimiento().setDate(estudiante.getFechaNacimiento());
        String identificacion = "";
        String nombre = "";
        if (estudiante.getRepresentante() != null) {
            representante=estudiante.getRepresentante();
            identificacion = estudiante.getRepresentante().getIdentificacion();
            nombre = estudiante.getRepresentante().getRazonSocial();
            getTxtRepresentante().setText(identificacion + " - " + nombre);
        }
        else
        {
            getTxtRepresentante().setText("");
        }
        
        if(estudiante.getRepresentante2() != null){
            representanteParaFacturar=estudiante.getRepresentante2();
            identificacion = estudiante.getRepresentante2().getIdentificacion();
            nombre = estudiante.getRepresentante2().getRazonSocial();
            getTxtFacturarANombre().setText(identificacion + " - " + nombre);
        }
        else
        {
            getTxtFacturarANombre().setText("");
        }
    }

    @Override
    public void limpiar() {
        this.estudiante = new Estudiante();
        this.representante = null;
        this.representanteParaFacturar = null;
        getDateFechaNacimiento().setDate(hoy());
        getCmbEstado().setSelectedIndex(0);
        getCmbGenero().setSelectedIndex(0);
        getCmbTipoDiscapacidad().setSelectedIndex(0);
        getCmbDiscapacidad().setSelectedIndex(0);
        getTxtPorcentajeDiscapacidad().setText("0");
        getCmbNacionalidad().setSelectedIndex(52);
        getLblCodigoSistema().setText("");
        getTxtRepresentante().setText("");
        getTxtFacturarANombre().setText("");
    }

//    @Override
    public String getNombre() {
        return "Estudiantes";
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
    
    public void crearRepresentante(int opc)
    {
         panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                        @Override
                        public void updateInterface(Persona entity) {
                            if (entity != null) {
                                Persona representanteGenerico = entity;
                                switch(opc)
                                {
                                    case 1:
                                        representante = representanteGenerico;
                                        getTxtRepresentante().setText(representante.getIdentificacion() + " - " + representante.getNombresCompletos());
                                    break;
                                    case 2:
                                        representanteParaFacturar = representanteGenerico;
                                        getTxtFacturarANombre().setText(representanteParaFacturar.getIdentificacion() + " - " + representanteParaFacturar.getNombresCompletos());
                                    break;
                                }
                                
                            }
                        }
                    }, VentanaEnum.CLIENTE, false,formularioActual);
    }
    
    public void obtenerRepresentante(int opc)
    {
        RepresentanteBusquedaDialogo buscarBusquedaDialogo = new RepresentanteBusquedaDialogo();
                    BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                    buscarDialogo.setVisible(true);
                    Persona representanteGenerico = (Persona) buscarDialogo.getResultado();
                    if (representanteGenerico != null) {
                        String identificacion = representanteGenerico.getIdentificacion();
                        String nombre = representanteGenerico.getRazonSocial();
                        switch(opc)
                        {
                            case 1:
                                representante = representanteGenerico;
                                getTxtRepresentante().setText(identificacion + " - " + nombre);
                            break;
                            case 2:
                                representanteParaFacturar = representanteGenerico;
                                getTxtFacturarANombre().setText(identificacion + " - " + nombre);
                            break;
                        }
                        
                    }
    }

    @Override
    public Estudiante getResult() throws ExcepcionCodefacLite {
        try {
            grabar();
            return estudiante;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
            throw  ex;
        }
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        EstudianteBusquedaDialogo aulaBusquedaDialogo = new EstudianteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
        buscarDialogoModel.setNormalizarTextoBusqueda(true); //Activo que al buscar no se active los acentos
        
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        estudiante=(Estudiante) entidad;
        cargarDatos();
    }

    private void agregarListenerBotones() {
        
        getBtnReporteLista().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    imprimirListaEstudiantes();
                } catch (IOException ex) {
                    Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnBuscarRepresentante().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    obtenerRepresentante(1);
                }
            });

            getBtnAgregarRepresentante().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    crearRepresentante(1);
                }
            });
            
            getBtnBuscarPersonaFacturar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    obtenerRepresentante(2);
                }
            });
            
            getBtnAgregarPersonaFacturar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    crearRepresentante(2);
                }
            });
            
            getBtnEliminarRepre1().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Boolean siNo = DialogoCodefac.dialogoPregunta("Advertencia", "Deseea eliminar el representante 1", DialogoCodefac.MENSAJE_ADVERTENCIA);                    
                    if(siNo)
                    {
                        representante = null;
                        getTxtRepresentante().setText("");
                    }
                }
            });
            
            getBtnEliminarRepre2().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   
                    Boolean siNo = DialogoCodefac.dialogoPregunta("Advertencia", "Deseea eliminar el representante 2", DialogoCodefac.MENSAJE_ADVERTENCIA);                    
                    if(siNo)
                    {
                        representanteParaFacturar = null;
                        getTxtFacturarANombre().setText("");
                    }
                }
            });

    }

    private void agregarListenerCombos() {
        
        try {
            List<Nacionalidad> nacion = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
            getCmbNacionalidad().removeAllItems();
            for (Nacionalidad n : nacion) {
                getCmbNacionalidad().addItem(n);
            }
            
            getCmbTipoDiscapacidad().removeAllItems();
            for (TipoDiscapacidadEnum tipo : TipoDiscapacidadEnum.values()) {
                getCmbTipoDiscapacidad().addItem(tipo);
            }

            getCmbDiscapacidad().removeAllItems();
            for (EnumSiNo sino : EnumSiNo.values()) {
                getCmbDiscapacidad().addItem(sino);
            }

            getCmbEstado().removeAllItems();
            for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
                getCmbEstado().addItem(enumerador);
            }

            getCmbGenero().removeAllItems();
            for (GeneroEnum generoEnum : GeneroEnum.values()) {
                getCmbGenero().addItem(generoEnum);
            }

            getCmbDiscapacidad().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(getCmbDiscapacidad().getSelectedItem()!=null)
                    {
                        String disc = ((EnumSiNo) getCmbDiscapacidad().getSelectedItem()).getLetra();
                        if (disc == "n") {
                            getTxtConadis().setText("");
                            getTxtObsDiscapacidad().setText("");
                            getCmbTipoDiscapacidad().setSelectedIndex(0);
                            getTxtPorcentajeDiscapacidad().setText("0");
                        }
                    }
                }
            });
            
            getCmbNacionalidad().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(getCmbNacionalidad().getSelectedItem()!=null)
                    {
                        String nacionalidad = (String) getCmbNacionalidad().getSelectedItem().toString();
                        if (nacionalidad.equals("ECUATORIANA")) {
                            banderaNacionalidad = true;
                            getCmbEtnia().setEnabled(true);
                        } else {
                            banderaNacionalidad = false;
                            getCmbEtnia().setEnabled(false);
                        }
                    }
                               
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void imprimirListaEstudiantes() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        try {
            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("listado_estudiantes.jrxml");
            Map parameters = new HashMap();
            List<EstudianteData> data = new ArrayList<EstudianteData>();
            EstudianteServiceIf service=ServiceFactory.getFactory().getEstudianteServiceIf();
            List<Estudiante> estudiantes=service.obtenerTodos(); //Todo: Obtener filtrar solo por clientes
            
            for (Estudiante estudiante : estudiantes) {
                EstudianteData estudianteData=new EstudianteData();
                estudianteData.setIdentificacion(estudiante.getCedula());
                estudianteData.setNombresCompletos(estudiante.getNombreCompleto());
                
                Persona representantePrincipal=estudiante.getRepresentante();
                
                if(representantePrincipal!=null)
                {
                    estudianteData.setRepresentante(representantePrincipal.getNombresCompletos());
                    estudianteData.setTelefono(representantePrincipal.getEstablecimientos().get(0).getTelefonoCelular());
                }
                else
                {
                    estudianteData.setRepresentante("");
                    estudianteData.setTelefono("");
                }
                
                data.add(estudianteData);
            }
            
            Collections.sort(data, new Comparator<EstudianteData>()
            {
                public int compare(EstudianteData obj1, EstudianteData obj2)
                {
                    return obj1.getNombresCompletos().compareTo(obj2.getNombresCompletos());
                }
            });
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Identificación", "Nombres completos","Representante", "Telf Representante"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Clientes ");                    
                }
            });
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(EstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    @Override
    public String getNumeroTelefono() {
        return estudiante.getCelular();
    }*/

    //@Override
    public Map<PlantillaSmsEnum.EtiquetaEnum, String> getPlantillaTags() {
        Map<PlantillaSmsEnum.EtiquetaEnum, String> mapParametros=new HashMap<PlantillaSmsEnum.EtiquetaEnum,String>();
        mapParametros.put(PlantillaSmsEnum.EtiquetaEnum.EMPRESA,session.getEmpresa().getNombreLegal());
        mapParametros.put(PlantillaSmsEnum.EtiquetaEnum.ESTUDIANTE_NOMBRE,estudiante.getNombreSimple());        
        return mapParametros;
    }

    @Override
    public boolean getValidacionEnvioSms() {
        
        if(!estadoFormulario.equals(ESTADO_EDITAR))
        {
            DialogoCodefac.mensaje("Error","Porfavor seleccione un cliente para enviar el mensaje",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
                
        return true;
    }

    private void iniciarVariables() {
        getPnlEnvioSmsPanel().setControlador(this); //Agragando inmplementacion de los metodos necesarios para la funcionalidad de enviar sms
    }

    @Override
    public VentanaEnum getVentanaEnum() {
        return VentanaEnum.ESTUDIANTES;
    }

    @Override
    public List<ComponenteEnvioSmsData> getDataSms() {
        //TODO: Aumentar el numero para el segundo representante
        ComponenteEnvioSmsData componenteEnvioSmsData = new ComponenteEnvioSmsData(estudiante.getRepresentante().getEstablecimientos().get(0).getTelefonoCelular(), getPlantillaTags());
        List<ComponenteEnvioSmsData> dataList = new ArrayList<ComponenteEnvioSmsData>();
        dataList.add(componenteEnvioSmsData);
        return dataList;
    }

    @Override
    public EnvioMensajesCallBackInterface getInterfaceCallback() {
        return null;
    }
    
}
