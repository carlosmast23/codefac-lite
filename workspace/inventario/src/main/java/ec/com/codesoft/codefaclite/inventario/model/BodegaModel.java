package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.inventario.busqueda.BodegaBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.BodegaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.BodegaPermisoTransferencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class BodegaModel extends BodegaPanel implements DialogInterfacePanel<Bodega> {

    private Bodega bodega;
    private BodegaServiceIf bodegaService;
    private JFileChooser jFileChooser;
    private Path origen = null;
    private Path destino = null;
    
    private Bodega bodegaPermisoSeleccionado;
    private BodegaPermisoTransferencia bodegaPermisoTransferenciaSeleccionado;

    public BodegaModel() {
        crearModeloTabla();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresBodega(bodega);
            bodegaService.grabar(bodega);
            DialogoCodefac.mensaje("Datos correctos", "La bodega se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        } catch (RemoteException ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresBodega(Bodega bodega) {
        bodega.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        bodega.setNombre(getTxtNombre().getText());
        bodega.setDescripcion(getTxtDescripcion().getText());
        bodega.setEncargado(getTxtEncargado().getText());
        bodega.setImagenPath(getTxtFoto().getText());
        
        //Guardar a la sucursal que pertenece la empresa
        Sucursal sucursal=(Sucursal) getCmbSucursal().getSelectedItem();
        bodega.setSucursal(sucursal);
        
        //Guardar el tipo de bodega
        Bodega.TipoBodegaEnum tipoBodegaEnum=(Bodega.TipoBodegaEnum) getCmbTipoBodega().getSelectedItem();
        bodega.setTipoBodegaEnum(tipoBodegaEnum);
        
        bodega.setEmpresa(session.getEmpresa());

        moverArchivo();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        bodegaService = ServiceFactory.getFactory().getBodegaServiceIf();

        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Foto", "png", "jpg", "bmp"));
        agregarListenerBotones();
        cargarCombosBox();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresBodega(bodega);
            bodegaService.editar(bodega);
            DialogoCodefac.mensaje("Datos correctos", "La bodega se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar la bodega?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion bodega");
                }
                bodegaService.eliminar(bodega);
                DialogoCodefac.mensaje("Datos correctos", "La bodega se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        BodegaBusquedaDialogo bodegaBusquedaDialogo = new BodegaBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(bodegaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Bodega bodegaTmp = (Bodega) buscarDialogoModel.getResultado();

        if (bodegaTmp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar bodega vacio");
        }
        
        bodega=bodegaTmp;
        cargarDatosVista();
        
    }
    
    private void cargarDatosVista()
    {
        getTxtNombre().setText(bodega.getNombre());
        getTxtDescripcion().setText(bodega.getDescripcion());
        getTxtEncargado().setText(bodega.getEncargado());        
        getCmbTipoBodega().setSelectedItem(bodega.getTipoBodegaEnum());
        if(bodega.getSucursal()!=null)
        {
            getCmbSucursal().setSelectedItem(bodega.getSucursal());
        }
        else
        {
            getCmbSucursal().setSelectedItem(Sucursal.getSucursalPermitirTodos());
        }
    }

    @Override
    public void limpiar() {
        bodega = new Bodega();
        getCmbTipoBodega().setSelectedIndex(0);
        getCmbSucursal().setSelectedIndex(0);        
    }

//    @Override
    public String getNombre() {
        return "Bodega";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bodega getResult() throws ExcepcionCodefacLite {
        try {
            grabar();
            return bodega;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
            throw  ex;
        }
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        //permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosArchivos(File archivoEscogido) {
        File archivo = archivoEscogido;
        String rutaArchivo = archivo.getPath();
        String nombreArchivo = archivo.getName();
        getTxtFoto().setText(nombreArchivo);
        //TODO cambiar el metodo de copiar por un servicio de transferencia al servidor
        String rutaDestino = "";
        //String rutaDestino = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/";
        rutaDestino += nombreArchivo;
        establecerDondeMoverArchivo(rutaArchivo, rutaDestino);
    }

    public void establecerDondeMoverArchivo(String rutaArchivo, String rutaDestino) {
        this.origen = FileSystems.getDefault().getPath(rutaArchivo);
        this.destino = FileSystems.getDefault().getPath(rutaDestino);
    }

    private void agregarListenerBotones() {
        getBtnCargarImagen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = jFileChooser.showDialog(null, "Abrir");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        cargarDatosArchivos(jFileChooser.getSelectedFile());
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }
            }
        });

    }

    public void moverArchivo() {
        //Verifica que solo cuando exista un origen y destino exista se copien los datos
        if (origen == null || destino == null) {
            return;
        }
        File file = destino.toFile();
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }
        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            getTxtFoto().setText("" + destino.getFileName());
        } catch (IOException ex) {
            ex.printStackTrace();
            DialogoCodefac.mensaje("Firma", "Problema en guardar firma", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarCombosBox() {
        try {
            List<Sucursal> sucursales =ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(session.getEmpresa());
            sucursales.add(Sucursal.getSucursalPermitirTodos());
            
            UtilidadesComboBox.llenarComboBox(getCmbSucursal(), sucursales);
            
            UtilidadesComboBox.llenarComboBox(getCmbTipoBodega(),Bodega.TipoBodegaEnum.values());
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listenerBotonBuscarBodega()
    {
        BodegaBusquedaDialogo bodegaBusquedaDialogo = new BodegaBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(bodegaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Bodega bodegaTmp = (Bodega) buscarDialogoModel.getResultado();
        
        if (buscarDialogoModel.getResultado() != null) 
        {   
            bodegaPermisoSeleccionado=(Bodega) buscarDialogoModel.getResultado();
        }


    }
    
    public void listenerEditarDetalle()
    {
        if(bodegaPermisoSeleccionado==null)
        {
            DialogoCodefac.mensaje(new CodefacMsj("No esta seleccionado un dato para editar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        
        if(validarAgregarDetalle(bodegaPermisoSeleccionado,CrudEnum.EDITAR))
        {
            bodegaPermisoSeleccionado.setObject(bodegaPermisoSeleccionado);
            //ruta.addDetalle(rutaDetalle);
            //Despues de agregar al detalle creo uno nuevo            
            crearNuevoDetalle();
        }
    }
    
    private void crearNuevoDetalle()
    {
        bodegaPermisoSeleccionado=new Bodega();
        bodegaPermisoTransferenciaSeleccionado=null;
    }
    
    
    public void listenerEliminarDetalle()
    {
        if(bodegaPermisoTransferenciaSeleccionado==null)
        {
            DialogoCodefac.mensaje(new CodefacMsj("No esta seleccionado un dato para eliminar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        
        
        if(DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
        {
            this.bodega.getBodegasPermisoTransfereciaList().remove(bodegaPermisoTransferenciaSeleccionado);
            crearNuevoDetalle();
        }
        
        
    }
    
    public void listenerAddDetalle()
    {
        if(validarAgregarDetalle(bodegaPermisoSeleccionado,CrudEnum.CREAR))
        {
            BodegaPermisoTransferencia bodegaTransferencia=new BodegaPermisoTransferencia();
            bodegaTransferencia.setBodegaPermiso(bodegaPermisoSeleccionado);    
            bodega.agregarPermisoTransferenciaBodega(bodegaTransferencia);
            bodegaPermisoSeleccionado=null;
        }
        
    }
    
    public boolean validarAgregarDetalle(Bodega bodegaSeleccionada,CrudEnum crudEnum)
    {
        if (bodegaSeleccionada == null) {
            DialogoCodefac.mensaje(new CodefacMsj("No se puede agregar una bodega vacia", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        
        if(bodega.buscarBodegaPermiso(bodegaSeleccionada)!=null)
        {
            DialogoCodefac.mensaje(new CodefacMsj("No se pueden ingresar bodegas duplicadas", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
                
        return true;
    }
    
    public void crearModeloTabla()
    {   
        String titulo[]=new String[]{
            "",
            "Bodega Permiso Transferencia"};
        
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Object.class,Object.class});
        getTblDatos().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDatos(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<BodegaPermisoTransferencia>() {
            @Override
            public Object[] addData(BodegaPermisoTransferencia value) {
                return new Object[]{
                    value,
                    value.getBodegaPermiso(),
                };
            }

            @Override
            public void setData(BodegaPermisoTransferencia objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    };

    ////////////////////////////////////////////////////////////////////////////
    ///                          GET AND SET
    ///////////////////////////////////////////////////////////////////////////
    
    public Bodega getBodegaPermisoSeleccionado() {
        return bodegaPermisoSeleccionado;
    }

    public void setBodegaPermisoSeleccionado(Bodega bodegaPermisoSeleccionado) {
        this.bodegaPermisoSeleccionado = bodegaPermisoSeleccionado;
    }

    public BodegaPermisoTransferencia getBodegaPermisoTransferenciaSeleccionado() {
        return bodegaPermisoTransferenciaSeleccionado;
    }

    public void setBodegaPermisoTransferenciaSeleccionado(BodegaPermisoTransferencia bodegaPermisoTransferenciaSeleccionado) {
        this.bodegaPermisoTransferenciaSeleccionado = bodegaPermisoTransferenciaSeleccionado;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }
    
    

    
}
