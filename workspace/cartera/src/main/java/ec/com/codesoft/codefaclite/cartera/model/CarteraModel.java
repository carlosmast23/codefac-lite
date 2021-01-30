/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.busqueda.CarteraBusqueda;
import ec.com.codesoft.codefaclite.cartera.panel.CarteraPanel;
import ec.com.codesoft.codefaclite.cartera.reportdata.CarteraData;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera.CarteraCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoDetalleEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class CarteraModel extends CarteraPanel{
    
    /**
     * Referencia para guardar con la cartera que se va a trabajar
     */
    private Cartera cartera;
    
    /**
     * Referencia para saber que detalle editar
     */
    private CarteraDetalle detalleEditar;
    
    /**
     * Lista de documentos que se pueden cruzar con el comprobante
     */
    private List<Cartera> carteraDocumentosCruzar;
    
    private List<CarteraCruce> cruces;
    

    public CarteraModel() {
    }

    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        valoresIniciales();
        listenerCombos();
        listenerTextos();
        listenerBotones();
        listenerTablas();
        listenerTabs();
        nuevo();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        getCmbDocumentoCategoriaCartera().setSelectedItem(CarteraCategoriaEnum.COMPROBANTE_INGRESO);
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {            
            setearVariables();
            validar();
            CarteraServiceIf carteraServiceIf=ServiceFactory.getFactory().getCarteraServiceIf();
            Cartera carteraTmp=carteraServiceIf.grabarCartera(cartera, cruces);
            DialogoCodefac.mensaje("Cartera Grabado correctamente","El registro fue grabado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            imprimirPdf(carteraTmp);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearVariables();
            validar();
            ServiceFactory.getFactory().getCarteraServiceIf().editar(cartera, cruces);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            try 
            {
               if(DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
               {
                    /*if(cartera.getCruces().size()>0)
                    {
                        DialogoCodefac.dialogoPregunta("La cartera tiene cruces registrados desea eliminar de todas maneras?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        
                    }
                    else
                    {*/
                    ServiceFactory.getFactory().getCarteraServiceIf().eliminar(cartera,ModoProcesarEnum.FORZADO);
                    //}
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
               }
               else
               {
                   throw new ExcepcionCodefacLite("Cancelar eliminar registro");
               }
                
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                throw new ExcepcionCodefacLite(ex.getMessage());
            } catch (RemoteException ex) {
                Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ACCION_PERMITIDA_MODULO_EDITAR);
        }
    }

    @Override
    public void imprimir() {
        if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            imprimirCartera(cartera);
        }
    }
    
    private void imprimirCartera(Cartera cartera)
    {
        //InputStream path = RecursoCodefac.JASPER_CARTERA.getResourceInputStream("comprobante_ingreso_egreso.jrxml");
        //Map<String,Object> parameters=CarteraData.mapParametros(cartera);
        List<CarteraData> data=CarteraData.castCarteraData(cartera);
        
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String nombreCabeceras[] = {"Identificación", "Nombres completos", "Nombre Legal", "Telefono", "Dirección", "Email"};
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, getNombreReporte());
                imprimirPdf(cartera);
            }
        });
    }
    
    private void imprimirPdf(Cartera cartera)
    {
        InputStream path = RecursoCodefac.JASPER_CARTERA.getResourceInputStream("comprobante_ingreso_egreso.jrxml");
        Map<String,Object> parameters=CarteraData.mapParametros(cartera);
        List<CarteraData> data=CarteraData.castCarteraData(cartera);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, getNombreReporte());
    }
    
    private String getNombreReporte()
    {
        return "Cartera ["+cartera.getCarteraDocumentoEnum().getNombre()+"]";
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
        cartera=new Cartera();
        detalleEditar=null;
        carteraDocumentosCruzar=new ArrayList<Cartera>();
        cruces=new ArrayList<CarteraCruce>();
        getCmbTipoCartera().setSelectedIndex(0);
        getCmbFechaEmision().setDate(UtilidadesFecha.getFechaHoy());
        
        getTblDetalles().setModel(new DefaultTableModel());
        getTblDetallesCruzar().setModel(new DefaultTableModel());
        getTblDetallesCruzar().setModel(new DefaultTableModel());
        getTblCruceDetalles().setModel(new DefaultTableModel());
        
        getTblDocumentosCruzar().setModel(new DefaultTableModel());
        
        
        getTxtIdentificacion().setText("");
        getLblDireccion().setText("");
        getLblNombresClientes().setText("");
        getLblTelefonos().setText("");
        getLblTotalCartera().setText("0");
        getTxtValorDetalle().setText("");
        getTxtDescripcionDetalle().setText("");
        getTxtPreimpreso().setText("");
    }

    //@Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        CarteraBusqueda carteraBusqueda=new CarteraBusqueda();
        return carteraBusqueda;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        Cartera cartera=(Cartera) entidad;
        this.cartera=cartera;
        this.cruces=cartera.getCruces();
        
        getCmbTipoCartera().setSelectedItem(this.cartera.getTipoCarteraEnum());
        CarteraCategoriaEnum carteraCategoria=CarteraCategoriaEnum.buscarPorTipoYDocumentoCategoria(this.cartera.getTipoCarteraEnum(),this.cartera.getCarteraDocumentoEnum().getCategoria());
                
        getCmbDocumentoCategoriaCartera().setSelectedItem(carteraCategoria);
        //getCmbDocumentoCartera().setSelectedItem(this.cartera.getCarteraDocumentoEnum());        
        getCmbDocumentoCartera().setSelectedItem(this.cartera.getCarteraDocumentoEnum());
        getCmbFechaEmision().setDate(this.cartera.getFechaEmision());
        cargarDatosCliente(this.cartera.getPersona());
        
        //cargar datos adicionales
        getTxtAutorizacion().setText(cartera.getAutorizacion());
        getTxtCodigoAuxiliar().setText(cartera.getCodigoAuxiliar());
        getTxtReferenciaManual().setText(cartera.getReferenciaManual());
        getTxtPreimpreso().setText(cartera.getPreimpreso());
        
        cargarCruces();
        actualizaVistaTablaDetalles();
        actualizarTablaCruces();
        actualizarTablaDocumentosCruzar();
        actualizarTablaCrucesDetalle();
        
    }
    
   
    /**
     * Metodo que se encarga de cargar los cruces relacionados con el documento
     */
    private void cargarCruces()
    {
        //CarteraCruce c;
        //c.getCarteraAfectada();
        //c.getCarteraDetalle();
        Cartera.TipoCarteraEnum tipoCarteraEnum=(Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem();
        CarteraCruceServiceIf servicio=ServiceFactory.getFactory().getCarteraCruceServiceIf();
        
        for (CarteraDetalle carteraDetalle : cartera.getDetalles()) {
            try {
                //Map<String, Object> parametros = new HashMap<String, Object>();
                //if(cartera.getTipoCarteraEnum().equals(tipoCarteraEnum.CL))
                //{
                //parametros.put("carteraDetalle", carteraDetalle);
                //}
                //List<CarteraCruce> cruces = servicio.obtenerPorMap(parametros);
                List<CarteraCruce> cruces = servicio.buscarPorCarteraDetalle(carteraDetalle);
                this.cruces.addAll(cruces);
            
            } catch (RemoteException ex) {
                Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Cargar documentos relacionados con los cruces         * 
         */
        for (CarteraCruce carteraCruce : cruces) {
            //Solo agregar a la cartera documentos si no estan repetidos
            if(!carteraDocumentosCruzar.contains(carteraCruce.getCarteraAfectada()))
            {
                this.carteraDocumentosCruzar.add(carteraCruce.getCarteraAfectada());
            }
        }
        
    }

    private void valoresIniciales() {
        
        getBtnAgregarDetalle().setToolTipText("Agregar Detalle");
        getBtnEditarDetalle().setToolTipText("Editar Detalle");
        getBtnEliminarDetalle().setToolTipText("Eliminar Detalle");
        
        getCmbTipoCartera().removeAllItems();
        for (Cartera.TipoCarteraEnum carteraEnum : Cartera.TipoCarteraEnum.values()) {
            getCmbTipoCartera().addItem(carteraEnum);
        }
    }

    private void listenerCombos() {
        getCmbTipoCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cartera.TipoCarteraEnum tipoCarteraEnum=(Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem();
                if(tipoCarteraEnum!=null)
                {
                    List<Cartera.CarteraCategoriaEnum> lista=Cartera.CarteraCategoriaEnum.buscarPorTipoCartera(tipoCarteraEnum);
                    getCmbDocumentoCategoriaCartera().removeAllItems();
                    for (Cartera.CarteraCategoriaEnum carteraDocumentoEnum : lista) {
                        getCmbDocumentoCategoriaCartera().addItem(carteraDocumentoEnum);
                    }                    
                }
            }
        });
        
        
        getCmbDocumentoCategoriaCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cartera.CarteraCategoriaEnum carteraDocumentoEnum=(Cartera.CarteraCategoriaEnum) getCmbDocumentoCategoriaCartera().getSelectedItem();
                if(carteraDocumentoEnum!=null)
                {
                    List<DocumentoEnum> resultados=DocumentoEnum.obtenerPorCategoria(carteraDocumentoEnum.getDocumentoCategoriaEnum());
                    
                    getCmbDocumentoCartera().removeAllItems();
                    for (DocumentoEnum resultado : resultados) 
                    {
                        getCmbDocumentoCartera().addItem(resultado);
                    }
                }
            }
        });
        
        getCmbDocumentoCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DocumentoEnum documentoSeleccionado= (DocumentoEnum) getCmbDocumentoCartera().getSelectedItem();
                UtilidadesComboBox.llenarComboBox(getCmbDetalleDocumento(), DocumentoDetalleEnum.findListByDocumentoEnum(documentoSeleccionado));
                
            }
        });
        
    }

    private void listenerTextos() {
        getTxtIdentificacion().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String identificacion=getTxtIdentificacion().getText();
                if(!identificacion.equals(""))
                {
                    //mapParametros.put("tipo",OperadorNegocioEnum.CLIENTE); //TODO: Falta optimizar cuando sean clientes y proveedores o ambos 

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            //Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            //mapParametros.put("identificacion", identificacion);
                            Persona persona=ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacion(identificacion,session.getEmpresa()); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(persona==null)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Cliente","No existe el Cliente, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    Object[] parametros = {OperadorNegocioEnum.CLIENTE, getTxtIdentificacion().getText()};
                                    panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                                        @Override
                                        public void updateInterface(Persona entity) {                                            
                                            if (entity!= null) 
                                            {
                                                cartera.setPersona(entity);
                                                cargarDatosCliente(entity);
                                            }
                                        }
                                    }, VentanaEnum.CLIENTE, false, parametros, formularioActual);
                                }
                            }
                            else
                            {
                                cartera.setPersona(persona);
                                cargarDatosCliente(persona);
                               //Opcion cuando encuentra los datos del cliente 
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

        });
    }
    
    private void cargarDatosCliente(Persona persona) {
        getLblNombresClientes().setText(persona.getRazonSocial());
        getLblDireccion().setText(persona.getEstablecimientos().get(0).getDireccion());
        getLblTelefonos().setText(persona.getEstablecimientos().get(0).getTelefonoCelular());
        getTxtIdentificacion().setText(persona.getIdentificacion());
    }
    
    private void btnListenerBuscarCliente() {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo= new ClienteEstablecimientoBusquedaDialogo(session);
        //ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());
        PersonaEstablecimiento personaEstablecimientoTmp=(PersonaEstablecimiento) buscarDialogoModel.getResultado();
        Persona personaTemp=personaEstablecimientoTmp.getPersona();
        if(personaTemp!=null)
        {
            cartera.setPersona(personaTemp);
            cargarDatosCliente(personaTemp);
        }
       
    }
    
    /**
     * Metodo que permite gestionar los cruces entre los documentos que afectan y los documentos de venta para clientes y proveedores
     * @param detalles
     * @param documentos 
     */
    private boolean cruzarDocumentos(Map<CarteraDetalle,BigDecimal> mapDetalles, Cartera documentoCruzar)
    {        
        //Validaciones
        if(mapDetalles.size()==0)
        {
            DialogoCodefac.mensaje("Adveretencia","No existen valores para cruzar con el documento",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        if(documentoCruzar==null)
        {
            DialogoCodefac.mensaje("Advertencia","Tiene que seleccionar un documento para cruzar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        BigDecimal saldoDocumento=documentoCruzar.getTotal().subtract(buscarValorCruceDocumento(documentoCruzar));
        if(saldoDocumento.compareTo(BigDecimal.ZERO)==0)
        {
            DialogoCodefac.mensaje("Advertencia","El documento ya no tiene saldo pendiente para cruzar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        Date fechaCruce=getCmbFechaCruzar().getDate();
        
        if(fechaCruce==null)
        {
            DialogoCodefac.mensaje("Advertencia","Tiene que seleccionar una fecha para hacer el cruce",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        List<CarteraCruce> crucesTemporales=new ArrayList<CarteraCruce>();
        BigDecimal totalCruzar=BigDecimal.ZERO;
        Boolean valoresCruzarCero=false;
        for (Map.Entry<CarteraDetalle, BigDecimal> entry : mapDetalles.entrySet()) {
            CarteraDetalle carteraDetalle = entry.getKey();
            BigDecimal valor = entry.getValue();
            
            CarteraCruce carteraCruce=new CarteraCruce();
            carteraCruce.setCarteraAfectada(documentoCruzar);
            carteraCruce.setCarteraDetalle(carteraDetalle);
            carteraCruce.setValor(valor);
            carteraCruce.setFechaCruce(new java.sql.Date(fechaCruce.getTime())); //Todo:Esta parte debe poder seleccionar el usuario y se debe agregar una fecha de creacion para saber cuando efectivamente hizo el cruce el usuario
            carteraCruce.setFechaCreacion(UtilidadesFecha.getFechaHoy());
            crucesTemporales.add(carteraCruce);
            totalCruzar=totalCruzar.add(carteraCruce.getValor()); //Acumulador para saber la cantidad total a cruzar

            if(valor.compareTo(BigDecimal.ZERO)==0)
            {
                valoresCruzarCero=true;
            }
        }
        
        
        if(valoresCruzarCero) //No se puede cruzar valores que tengan 0
        {
            DialogoCodefac.mensaje("Advertencia", "Los valores a cruzar no pueden ser 0", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        if(totalCruzar.compareTo(saldoDocumento)<=0)
        {
            //Si todo es correcto agrego los nuevos cruces a la lista 
            cruces.addAll(crucesTemporales);
        }
        else
        {
            DialogoCodefac.mensaje("Advertencia", "Los valores a cruzar son superiores al valor del saldo del documento", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        return true; //Si el proceso termina sin novedad envia verdadero
        
    }
    
    private  Map<CarteraDetalle,BigDecimal> obtenerMapDetallesSeleccionadosCruce()
    {
        Map<CarteraDetalle,BigDecimal>  mapResultado=new HashMap<CarteraDetalle,BigDecimal>();
        DefaultTableModel modeloTabla=(DefaultTableModel) getTblDetallesCruzar().getModel();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Boolean seleccionado=(Boolean) modeloTabla.getValueAt(i,1);
            if(seleccionado)
            {
                CarteraDetalle carteraDetalle=(CarteraDetalle) modeloTabla.getValueAt(i,0);
                BigDecimal valorCruzar=new BigDecimal(modeloTabla.getValueAt(i,5).toString());
                mapResultado.put(carteraDetalle,valorCruzar);
            }
        }
        return mapResultado;
    }
    
    private void listenerBotones() {
        
        getBtnEliminarCruce().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarteraCruce cruce=UtilidadesTablas.obtenerDatoSeleccionado(getTblCruceDetalles(),0);
                if(cruce!=null)
                {
                    cruces.remove(cruce);
                    actualizarTablaCrucesDetalle();
                }
            }
        });
        
        getBtnCruzar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<CarteraDetalle,BigDecimal>  detallesCruzar=obtenerMapDetallesSeleccionadosCruce(); //Obtiene los detalles con los valores a cruzar
                Cartera cartera=UtilidadesTablas.obtenerDatoSeleccionado(getTblDocumentosCruzar(),0); //Dato seleccionado de la tabla
                //Solo actualizar las tablas si el proces de cruce termina correctamente
                if (cruzarDocumentos(detallesCruzar, cartera)) {
                    actualizarTablaDocumentosCruzar();
                    actualizarTablaCruces();
                }              
            }
        });
        
        getBtnBuscarPersona().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });
        
        getBtnAgregarDocumentoCruzar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Abrir el buscar de cartera dependiendo si son documento para el cliente o proveedor
                Cartera.TipoCarteraEnum tipoCarteraEnum = (Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem();
                if (tipoCarteraEnum != null) {
                    CarteraBusqueda carteraBusqueda;
                    if(tipoCarteraEnum.equals(Cartera.TipoCarteraEnum.CLIENTE))
                        carteraBusqueda= new CarteraBusqueda(true, false, DocumentoEnum.obtenerPorCategoria(DocumentoCategoriaEnum.COMPROBANTES_VENTA),(cartera.getPersona()!=null)?cartera.getPersona():null,true);
                    else
                        carteraBusqueda= new CarteraBusqueda(false,true, DocumentoEnum.obtenerPorCategoria(DocumentoCategoriaEnum.COMPROBANTES_VENTA),(cartera.getPersona()!=null)?cartera.getPersona():null,true);
                    
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(carteraBusqueda);
                    buscarDialogoModel.setVisible(true);
                    Cartera carteraTmp = (Cartera) buscarDialogoModel.getResultado();
                    if (carteraTmp != null) {
                        if(carteraTmp.getSaldo().compareTo(BigDecimal.ZERO)==0)
                        {
                            DialogoCodefac.mensaje("Advertencia","Este documento tiene saldo 0 , ya no se puede cruzar",DialogoCodefac.MENSAJE_ADVERTENCIA);                            
                            return;
                        }
                        carteraDocumentosCruzar.add(carteraTmp);
                        actualizarTablaDocumentosCruzar();
                    }
                }
            }
        });
        
        
        getBtnAgregarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarteraDetalle carteraDetalle=new CarteraDetalle();
                if( setearDatosDetalle(carteraDetalle,false))
                {
                    cartera.addDetalle(carteraDetalle);

                    //Actualizar la vista
                    actualizaVistaTablaDetalles();
                    limpiarDetalleVista();
                }
                
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(detalleEditar!=null)
                {
                    setearDatosDetalle(detalleEditar,true);
                    actualizaVistaTablaDetalles();
                }
            }
        });
        
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblDetalles().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    CarteraDetalle carteraDetalle=(CarteraDetalle) getTblDetalles().getModel().getValueAt(filaSeleccionada,0);
                    if(carteraDetalle!=null)
                    {
                        cartera.getDetalles().remove(carteraDetalle);
                        actualizaVistaTablaDetalles();
                    }
                }
            }
        });
    }
    
    private boolean setearDatosDetalle(CarteraDetalle carteraDetalle,Boolean editar)
    {
        /**
         * TODO: Analziar para seprar esto en otra funcion
         * =====================> VALIDAR INGRESO DE LA INFORMACION <==========
         */
        BigDecimal total =null;
        try
        {
            total = new BigDecimal(getTxtValorDetalle().getText());
        }catch(NumberFormatException e)
        {
            DialogoCodefac.mensaje("Advertencia","Ingreso un formato correcto para el número",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        
        String descripcion = getTxtDescripcionDetalle().getText();
        
        if(descripcion.isEmpty())
        {
            DialogoCodefac.mensaje("Advertencia","No se puede agregar descripciones vacias", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        if(total.compareTo(BigDecimal.ZERO)<=0)
        {
            DialogoCodefac.mensaje("Advertencia","No se puede ingresar valores menores iguales que cero", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }

        carteraDetalle.setTotal(total);
        carteraDetalle.setDescripcion(descripcion);
        
        DocumentoDetalleEnum documentoDetalleEnum=(DocumentoDetalleEnum) getCmbDetalleDocumento().getSelectedItem();
        if(documentoDetalleEnum!=null)
        {
            carteraDetalle.setCodigoDetalleDocumentoEnum(documentoDetalleEnum);
        }
        
        if(!editar)
        {
            carteraDetalle.setSaldo(total);
            carteraDetalle.setId(Long.parseLong(System.identityHashCode(carteraDetalle)*-1+"")); //Cuando creo un objeto por primera vez creo un hashCode para despues poder relacionar con los cruces
        }
        return true;
    }
    
    private void limpiarDetalleVista()
    {
        getTxtValorDetalle().setText("");
        getTxtDescripcionDetalle().setText("");
    }
    
    /**
     * Busca si existe un documento anterior ya cruzado
     * @param cartera
     * @return 
     */
    public BigDecimal buscarValorCruceDocumento(Cartera cartera)
    {
        BigDecimal valorCruce=BigDecimal.ZERO;
        for (CarteraCruce cruce : cruces) {
            if(cruce.getCarteraAfectada().equals(cartera))
            {
                valorCruce=valorCruce.add(cruce.getValor());
            }
        }
        return valorCruce;
    }
    
    public BigDecimal buscarValorCruceDocumento(CarteraDetalle carteraDetalle)
    {
        BigDecimal valorCruce=BigDecimal.ZERO;
        for (CarteraCruce cruce : cruces) {
            if(cruce.getCarteraDetalle().equals(carteraDetalle))
            {
                valorCruce=valorCruce.add(cruce.getValor());
            }
        }
        return valorCruce;
    }
    
    public void actualizarTablaDocumentosCruzar()
    {
        String[] titulo = {"", "Preimpreso","Total","Saldo Pendiente"};
        DefaultTableModel modelTabla = UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Cartera.class, String.class, String.class, String.class});
        
        for (Cartera cartera : carteraDocumentosCruzar){
            //Buscar si existe un cruce anterior para esta cartera
            BigDecimal valorCruzado=buscarValorCruceDocumento(cartera);
            
            modelTabla.addRow(new Object[]
            {cartera,
            cartera.getPreimpreso(),
            cartera.getTotal().toString(),
            cartera.getSaldo().subtract(valorCruzado).toString()});
        }
        
        getTblDocumentosCruzar().setModel(modelTabla);
        UtilidadesTablas.ocultarColumna(getTblDocumentosCruzar(),0);
    }
    
    public void actualizarTablaCruces()
    {
        if(cartera.getDetalles()==null)return;
        
        String[] titulo={"","","Detalle","Total","Saldo Disponible","Valor Cruzar"};
        DefaultTableModel modelTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{CarteraDetalle.class,Boolean.class,String.class,String.class,String.class,String.class});
        
        for (CarteraDetalle carteraDetalle : cartera.getDetalles()) {
            
            //Solo aparecen los detalles que tiene un saldo superior a 0 para poder cruzar con los documento
            if(carteraDetalle.getSaldo().compareTo(BigDecimal.ZERO)>0)
            {
                BigDecimal valorCruzado=buscarValorCruceDocumento(carteraDetalle);
                
                modelTabla.addRow(new Object[]{
                    carteraDetalle,
                    false,
                    carteraDetalle.getDescripcion(),
                    carteraDetalle.getTotal().toString(),
                    carteraDetalle.getSaldo().subtract(valorCruzado).toString(),
                    "0"});                
            }
        } 
        
        getTblDetallesCruzar().setModel(modelTabla);
        UtilidadesTablas.ocultarColumna(getTblDetallesCruzar(),0);
        
        //listener que se encarga de sumar el saldo para poder hacer el cruce
        modelTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int filaSeleccionada=getTblDetallesCruzar().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    /*
                    int columnaSeleccionada=getTblDetallesCruzar().getSelectedColumn();
                    
                    //Solo verificar si se modifica el campo 1 que corresponde al check box
                    if(columnaSeleccionada==1)
                    {
                        CarteraDetalle carteraDetalle=(CarteraDetalle) getTblDetallesCruzar().getModel().getValueAt(filaSeleccionada,0);
                        Boolean seleccionado=(Boolean) getTblDetallesCruzar().getModel().getValueAt(filaSeleccionada,columnaSeleccionada);
                        String valorCruzadoTxt=getTxtValorCruzado().getText();
                        
                        //Si no existe un valor anterior los seteo con 0
                        if(valorCruzadoTxt.trim().isEmpty())
                        {
                            valorCruzadoTxt="0";
                        }
                        
                        BigDecimal valorCruzar=new BigDecimal(valorCruzadoTxt);
                        if(seleccionado)
                        {
                            valorCruzar=valorCruzar.add(carteraDetalle.getSaldo());
                        }
                        else
                        {
                            valorCruzar=valorCruzar.subtract(carteraDetalle.getSaldo());
                        }                        
                        
                        getTxtValorCruzado().setText(valorCruzar.toString());
                    }
*/
                }
            }
        });
        
    }
    
    public void actualizaVistaTablaDetalles()
    {
        if(cartera.getDetalles()==null)return; //validar que existan datos
        String[] tituloTabla={"","Descripción","Valor"};
        
                
        DefaultTableModel modeloTabla=new DefaultTableModel(tituloTabla,0);
        
        for (CarteraDetalle carteraDetalle : cartera.getDetalles()) {
            Object[] fila={carteraDetalle,carteraDetalle.getDescripcion(),carteraDetalle.getTotal()};
            modeloTabla.addRow(fila);
        }        
        
        getTblDetalles().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblDetalles(),0);
        
        //ActualizarTotalesVista
        calcularTotalVista();
        
    }
    
    public void actualizarTablaCrucesDetalle()
    {
        String[] tituloTabla={"","Fecha Cruce","Identificación","Documento Preimpreso","Cod Cartera Afecta","Valor"};
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(tituloTabla,new Class[]{CarteraCruce.class,String.class,String.class,String.class,String.class,String.class});
                
        for (CarteraCruce cruce : cruces) 
        {
            Object[] fila={
            cruce,
            cruce.getFechaCruce(),
            cruce.getCarteraAfectada().getPersona().getIdentificacion(),
            cruce.getCarteraAfectada().getPreimpreso(),
            cruce.getCarteraDetalle().getDescripcion(),
            cruce.getValor()};   
            modeloTabla.addRow(fila);
        }
        
        getTblCruceDetalles().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblCruceDetalles(),0);
        
    }

    private void listenerTablas() {
        getTblDetalles().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada=getTblDetalles().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    detalleEditar=(CarteraDetalle) getTblDetalles().getModel().getValueAt(filaSeleccionada,0);
                    getTxtDescripcionDetalle().setText(detalleEditar.getDescripcion());
                    getTxtValorDetalle().setText(detalleEditar.getTotal().toString());
                    DocumentoDetalleEnum documentoDetalleEnum=detalleEditar.getCodigoDetalleDocumentoEnum();
                    if(documentoDetalleEnum!=null)
                    {
                        getCmbDetalleDocumento().setSelectedItem(documentoDetalleEnum);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    private void calcularTotalVista()
    {
        BigDecimal totalDetalles=BigDecimal.ZERO;
        
        for (CarteraDetalle carteraDetalle : cartera.getDetalles()) 
        {
            totalDetalles=totalDetalles.add(carteraDetalle.getTotal());
        }
        
        getLblTotalCartera().setText(totalDetalles.toString());
        
    }

    private void listenerTabs() {
        getTabDetalles().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                actualizarTablaCruces();
                actualizarTablaCrucesDetalle();
                //actualizarTablaDocumentosCruzar();
            }
        });
    }

    private void setearVariables() {
        DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumentoCartera().getSelectedItem();
        cartera.setCodigoDocumento(documentoEnum.getCodigo());
        
        cartera.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        cartera.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        cartera.setFechaEmision(new java.sql.Date(getCmbFechaEmision().getDate().getTime()));
        
        String puntoEmision="";
        String puntoEstablecimiento="";
        String secuencial="";
        
        if(!getTxtPreimpreso().getText().isEmpty())
        {
            String preimpreso[]=getTxtPreimpreso().getText().split("-");
            puntoEmision=preimpreso[0];
            puntoEstablecimiento=preimpreso[1];
            secuencial=preimpreso[2];
        }
        
        cartera.setPuntoEmision((puntoEmision.isEmpty())?"0":puntoEmision);
        cartera.setPuntoEstablecimiento((puntoEstablecimiento.isEmpty())?"0":puntoEstablecimiento);
        cartera.setSecuencial((secuencial.isEmpty())?0:Integer.parseInt(secuencial));
        
        cartera.setSaldo(cartera.totalDetalles()); //TODO: Si no cruza con nada el saldo es el mismo del total
        Cartera.TipoCarteraEnum tipoEnum=(Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem();
        cartera.setTipoCartera(tipoEnum.getLetra());
        cartera.setTotal(cartera.totalDetalles());
        cartera.setSucursal(session.getSucursal());
        cartera.setUsuario(session.getUsuario());
        
        cartera.setAutorizacion(getTxtAutorizacion().getText());
        cartera.setCodigoAuxiliar(getTxtCodigoAuxiliar().getText());
        
        cartera.setReferenciaManual(getTxtReferenciaManual().getText());
        
    }

    private void validar() throws ExcepcionCodefacLite {
        if(cartera.getDetalles()==null || cartera.getDetalles().size()==0)
        {
           DialogoCodefac.mensaje("Error","No se pueden grabar con detalles vacios",DialogoCodefac.MENSAJE_INCORRECTO);
           throw new ExcepcionCodefacLite("No se puede grabar una cartera con detalles vacios");
        }
        
        if(!cartera.getCarteraDocumentoEnum().getCategoria().getGenerarManualmente())
        {
            if(!DialogoCodefac.dialogoPregunta("Advertencia","Este tipo de documento no es recomedable crear por el usuario , desea continuar de todos modos? ",DialogoCodefac.MENSAJE_INCORRECTO))
            {
                throw new ExcepcionCodefacLite("Advertencia tipo de documento no es permitido");
            }
        }
    }
    
    
}
