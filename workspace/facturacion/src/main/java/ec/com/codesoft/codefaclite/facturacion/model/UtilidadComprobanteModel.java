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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NotaCreditoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
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

    private JInternalFrame frame;
    private DefaultTableModel tableModel;
    private List<ComprobanteElectronico> comprobantes;
    private DefaultListModel listaModel;

    public UtilidadComprobanteModel() {
        iniciarComponentes();
        addBotonListener();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        Vector<String> titulo = new Vector<>();
        titulo.add("Clave Acceso");
        titulo.add("Preimpreso");
        titulo.add("Fecha");

        this.tableModel = new DefaultTableModel(titulo, 0);

        for (ComprobanteElectronico comprobante : comprobantes) {
            Vector<String> fila = new Vector<>();
            fila.add(comprobante.getInformacionTributaria().getClaveAcceso());
            fila.add(comprobante.getInformacionTributaria().getPreimpreso());
            fila.add(comprobante.getFechaEmision());
            this.tableModel.addRow(fila);
        }
        this.getTblComprobantes().setModel(tableModel);
    }
    
    private void cargarSiguienteEtapaPorCarpeta(String carpeta)
    {
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
        }
    
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
        
        getTblComprobantes().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               Integer indice=getTblComprobantes().getSelectedRow();
               
               ComprobanteElectronico comprobante=comprobantes.get(indice);
               listaModel=new DefaultListModel();
               List<InformacionAdicional> infoAdicional=comprobante.getInformacionAdicional();
                for (InformacionAdicional informacionAdicional : infoAdicional) {
                    if(informacionAdicional.getNombre().equals(InformacionAdicional.EMAIL))
                    {
                        String[] correos=informacionAdicional.getValor().split(",");
                        for (String correo : correos) {
                            listaModel.addElement(correo);
                        }
                        break;
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
        
                
        getCmbCarpetaComprobante().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                try {
                    //TODO: Revisar esta validacion temporal porque no existe la carpeta de no autorizado
                    if(getCmbCarpetaComprobante().getSelectedItem().equals(ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS))
                    {
                        return ;
                    }
                    
                    String path = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
                    String modoFacturacion=session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor;
                    String pathComprobantes="";
                    if(modoFacturacion.equals(ComprobanteElectronicoService.MODO_PRODUCCION))
                        pathComprobantes=DirectorioCodefac.COMPROBANTES_PRODUCCION.getDirectorio();
                    else
                        pathComprobantes=DirectorioCodefac.COMPROBANTES_PRUEBAS.getDirectorio();
                    
                    
                    //File[] archivos = ComprobantesElectronicosUtil.getComprobantesByFolder(path, getCmbCarpetaComprobante().getSelectedItem().toString());
                    comprobantes= ComprobantesElectronicosUtil.getComprobantesObjectByFolder(pathComprobantes,getCmbCarpetaComprobante().getSelectedItem().toString());
                    cargarDatosComprobantesTabla(comprobantes);
                    cargarSiguienteEtapaPorCarpeta(getCmbCarpetaComprobante().getSelectedItem().toString());
                } catch (RemoteException ex) {
                    Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                }*/
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
                        procesarComprabante(ComprobanteElectronicoService.ETAPA_GENERAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_FIRMADOS:
                        procesarComprabante(ComprobanteElectronicoService.ETAPA_FIRMAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_ENVIADOS:
                        procesarComprabante(ComprobanteElectronicoService.ETAPA_ENVIAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_AUTORIZADOS:
                        procesarComprabante(ComprobanteElectronicoService.ETAPA_AUTORIZAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS:
                        procesarComprabante(ComprobanteElectronicoService.ETAPA_AUTORIZAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_RIDE:
                        procesarComprabante(ComprobanteElectronicoService.ETAPA_RIDE+1,etapaLimite);
                        break;
                }

            }
        });
    }

    private ListenerComprobanteElectronico listener = new ListenerComprobanteElectronico() {
        @Override
        public void termino() {
            estadoNormal();
            DialogoCodefac.mensaje("Dialogo", "Proceso Terminado", 1);
        }

        @Override
        public void iniciado(ComprobanteElectronico comprobante) {
            
        }

        @Override
        public void procesando(int etapa,ClaveAcceso clave) {
            if(etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) //Si ya cumple la etapa de autorizar cambio el estado de los comprobantes
            {
                try {
                    ComprobanteEnum comprobante = ComprobanteEnum.getEnumByCodigo(clave.tipoComprobante);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("claveAcceso", clave.clave);
                    switch(comprobante)
                    {
                        case FACTURA:
                            FacturacionServiceIf servicio=ServiceFactory.getFactory().getFacturacionServiceIf();
                            List<Factura> facturas=servicio.obtenerPorMap(map);
                            for (Factura factura : facturas) {
                                try {
                                    factura.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                                    servicio.editar(factura);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                            
                        case NOTA_CREDITO:
                            NotaCreditoServiceIf servicioNotaCredito=ServiceFactory.getFactory().getNotaCreditoServiceIf();
                            List<NotaCredito> notasCredito=servicioNotaCredito.obtenerPorMap(map);
                            for (NotaCredito notaCredito : notasCredito) {
                                try {
                                    notaCredito.setClaveAcceso(NotaCreditoEnumEstado.TERMINADO.getEstado());
                                    servicioNotaCredito.editar(notaCredito);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public void error(ComprobanteElectronicoException cee) {
            estadoNormal();
            DialogoCodefac.mensaje("Dialogo", cee.getMessage(), 1);
        }
    };
    
    private void procesarComprabante(Integer etapaInicial,Integer etapaLimite) 
    {
        /*
        FacturacionElectronica servicio = new FacturacionElectronica(session, panelPadre);
        servicio.setCorreosAdicionales(obtenerCorreos());
        servicio.getServicio().addActionListerComprobanteElectronico(listener);
        estadoCargando();
        String claveAcceso = tableModel.getValueAt(getTblComprobantes().getSelectedRow(), 0).toString().replace(".xml", "");
        servicio.setClaveAcceso(claveAcceso);
        servicio.procesarComprobanteEtapa(etapaInicial,etapaLimite);*/
    }
    
    private List<String> obtenerCorreos()
    {
        List<String> correos=new ArrayList<String>();
        for (int i = 0; i < listaModel.getSize(); i++) {
            correos.add(listaModel.get(i).toString());
        }
        return correos;
    }

    

    private void etapaGenerados(Boolean completarTodasEtapas) {
        /*
        FacturacionElectronica servicio = new FacturacionElectronica(session, panelPadre);
        servicio.getServicio().addActionListerComprobanteElectronico(listener);
        estadoCargando();
        String claveAcceso = tableModel.getValueAt(getTblComprobantes().getSelectedRow(), 0).toString().replace(".xml", "");
        servicio.setClaveAcceso(claveAcceso);
        //servicio.procesarComprobanteEtapa(ComprobanteElectronicoService.ETAPA_GENERAR + 1, completarTodasEtapas);
        */
    }

    private void iniciarComponentes() {
        /**
         * Cargar las carpetas disponibles de los comprobantes
         */
        getCmbCarpetaComprobante().removeAllItems();
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_GENERADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
