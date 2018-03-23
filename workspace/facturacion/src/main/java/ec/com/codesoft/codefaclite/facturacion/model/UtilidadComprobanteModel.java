/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import com.sun.glass.ui.Cursor;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaLoteImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteUtilidadImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.interfaz.InterfaceCallbakClient;
import ec.com.codesoft.codefaclite.facturacion.panel.UtilidadComprobantePanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NotaCreditoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.ejemplo.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class UtilidadComprobanteModel extends UtilidadComprobantePanel {

    private UtilidadComprobanteModel formThis=this;
    
    private JInternalFrame frame;
    private DefaultTableModel tableModel;
    private List<ComprobanteElectronico> comprobantes;
    private DefaultListModel listaModel;

    public UtilidadComprobanteModel() {
        iniciarComponentes();
        addBotonListener();
        addComboListener();
        addTableListener();
        frame = this;
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
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {

    }

    @Override
    public String getNombre() {
        return "Utilidades Comprobante";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#eutilidad";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, false);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, false);
        return permisos;
    }

    
    private void cargarDatosComprobantesTabla(List<ComprobanteElectronico> comprobantes) {
        String[] titulo = new String[]{"Seleccion","Clave Acceso","Preimpreso","Fecha"};

        this.tableModel=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Boolean.class,String.class,String.class,String.class});
        
        for (ComprobanteElectronico comprobante : comprobantes) {
            Vector<Object> fila = new Vector<>();
            fila.add(false);
            fila.add(comprobante.getInformacionTributaria().getClaveAcceso());
            fila.add(comprobante.getInformacionTributaria().getPreimpreso());
            fila.add(comprobante.getFechaEmision());
            this.tableModel.addRow(fila);
        }
        this.getTblComprobantes().setModel(tableModel);
    }
    
    private void cargarSiguienteEtapaPorCarpeta(String carpeta)
    {
        //Por defecto en procesar etapa selecciono todos
        getCmbEstadoLimiteProcesar().setSelectedIndex(0);
        //Por defecto estaba seleccionando el siguiente nivel de la carpeta
        /*
        switch (carpeta) {

            case ComprobanteElectronicoService.CARPETA_GENERADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(3);
                break;

            case ComprobanteElectronicoService.CARPETA_FIRMADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(4);
                break;

            case ComprobanteElectronicoService.CARPETA_ENVIADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(5);
                break;

            case ComprobanteElectronicoService.CARPETA_AUTORIZADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(6);
                break;

            case ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(6);
                break;

            case ComprobanteElectronicoService.CARPETA_RIDE:
                getCmbEstadoLimiteProcesar().setSelectedIndex(7);
                break;
        }*/
    
    }

    private void addBotonListener() {
        
        getBtnAgregarCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valor=JOptionPane.showInputDialog(null,"Ingrese un correo:");
                listaModel.addElement((valor.equals("")?"":valor));
            }
        });
        
        getBtnEliminarCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer indice=getjListCorreos().getSelectedIndex();
                if(indice>=0)
                {
                    listaModel.remove(indice);
                }
            }
        });
        


        getBtnSiguienteEtapa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String etapa = getCmbCarpetaComprobante().getSelectedItem().toString();
                Integer etapaLimite = getCmbEstadoLimiteProcesar().getSelectedIndex();
                
                /**
                 * Agrega un valor alto para que genere todas las etapas restantes de comprobante
                 */
                if(etapaLimite.equals(0))
                {
                    etapaLimite=100;
                }
                else
                {
                    //etapaLimite++;
                }
                
                switch (etapa) {

                    case ComprobanteElectronicoService.CARPETA_GENERADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_GENERAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_FIRMADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_FIRMAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR:
                        if(getChkEnvioCorreo().isSelected())
                            procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE, etapaLimite);
                        else
                            procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE + 1, etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_ENVIADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_ENVIAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_AUTORIZADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_AUTORIZAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_AUTORIZAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_RIDE:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_RIDE+1,etapaLimite);
                        break;
                }
                
            }
        });
    }

    
    
    private void procesarComprobante(Integer etapaInicial,Integer etapaLimite) 
    {
        
        try {
            String claveAcceso = tableModel.getValueAt(getTblComprobantes().getSelectedRow(), 0).toString().replace(".xml", "");
            
            ComprobanteServiceIf comprobanteServiceIf=ServiceFactory.getFactory().getComprobanteServiceIf();
            ClienteUtilidadImplComprobante callBack=new ClienteUtilidadImplComprobante(this);

            estadoCargando();            
            comprobanteServiceIf.procesarComprobantesPendiente(etapaInicial, etapaLimite,claveAcceso,obtenerCorreos(),callBack);

        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void procesarComprobanteLote(Integer etapaInicial,Integer etapaLimite) 
    {
        try {
            //Buscar todos las filas seleccionadas
            List<String> clavesAcceso=new ArrayList<String>();
            for (int i = 0; i < tableModel.getRowCount(); i++)
            {
                Boolean opcion=(Boolean) tableModel.getValueAt(i, 0);
                if(opcion)
                {
                    String claveAcceso=tableModel.getValueAt(i, 1).toString();
                    clavesAcceso.add(claveAcceso);
                    System.out.println(claveAcceso);
                }
            }
            
            if(clavesAcceso.size()>0)
            {
                estadoCargando();
                ClienteInterfaceComprobanteLote cic = new ClienteFacturaLoteImplComprobante(this,new InterfaceCallbakClient() {
                    @Override
                    public void terminoProceso() {
                        formThis.estadoNormal();
                        getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex());
                    }
                });                
                ServiceFactory.getFactory().getComprobanteServiceIf().procesarComprobantesLotePendiente(etapaInicial, etapaLimite, clavesAcceso, session.getEmpresa().getIdentificacion(),cic);
                //estadoNormal();
            }
            else
            {
                DialogoCodefac.mensaje("Advertencia","Seleccione datos para procesar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private List<String> obtenerCorreos()
    {
        List<String> correos=new ArrayList<String>();
        for (int i = 0; i < listaModel.getSize(); i++) {
            correos.add(listaModel.get(i).toString());
        }
        return correos;
    }

    


    private void iniciarComponentes() {
        /**
         * Cargar las carpetas disponibles de los comprobantes
         */
        getCmbCarpetaComprobante().removeAllItems();
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_GENERADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_ENVIADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_AUTORIZADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_RIDE);
        
        cargarEtapas();

    }

    private void cargarEtapas() {
        getCmbEstadoLimiteProcesar().removeAllItems();

        getCmbEstadoLimiteProcesar().addItem("procesar todo");

        getCmbEstadoLimiteProcesar().addItem("generar");

        getCmbEstadoLimiteProcesar().addItem("prevalidar");

        getCmbEstadoLimiteProcesar().addItem("firmar");

        getCmbEstadoLimiteProcesar().addItem("enviar SRI");

        getCmbEstadoLimiteProcesar().addItem("autorizar SRI");

        getCmbEstadoLimiteProcesar().addItem("generar RIDE");

        getCmbEstadoLimiteProcesar().addItem("envio comprobante");

    }

    @Override
    public void iniciar() {
        getCmbCarpetaComprobante().setSelectedItem(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addComboListener() {
            getCmbCarpetaComprobante().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    //TODO: Revisar esta validacion temporal porque no existe la carpeta de no autorizado
                    if (getCmbCarpetaComprobante().getSelectedItem().equals(ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS)) {
                        return;
                    }
                    ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
                    comprobantes = comprobanteServiceIf.getComprobantesObjectByFolder(getCmbCarpetaComprobante().getSelectedItem().toString());
                    cargarDatosComprobantesTabla(comprobantes);
                    cargarSiguienteEtapaPorCarpeta(getCmbCarpetaComprobante().getSelectedItem().toString());
                } catch (RemoteException ex) {
                    Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addTableListener() {
            getTblComprobantes().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer indice = getTblComprobantes().getSelectedRow();

                ComprobanteElectronico comprobante = comprobantes.get(indice);
                listaModel = new DefaultListModel();
                List<InformacionAdicional> infoAdicional = comprobante.getInformacionAdicional();
                for (InformacionAdicional informacionAdicional : infoAdicional) {
                    if (informacionAdicional.getNombre().indexOf(FacturaAdicional.NOMBRE_CORREO) >= 0) {
                        listaModel.addElement(informacionAdicional.getValor());
                    }
                }
                getjListCorreos().setModel(listaModel);
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

}
