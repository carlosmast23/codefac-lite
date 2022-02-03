/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.impuestos.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
//import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.impuestos.panel.AtsPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AnuladoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.CompraAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoAtsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.UtilidadesServidorXml;
import ec.com.codesoft.codefaclite.utilidades.archivos.UtilidadesDirectorios;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.xml.UtilidadesXml;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Carlos
 */
public class AtsModel extends AtsPanel {

    private AtsJaxb atsJaxb;
    
    private final static int COLUMNA_OBJETO=0;
    private final static int COLUMNA_VENTA_RET_RENTA=5;
    private final static int COLUMNA_VENTA_RET_IVA=6;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        listenerComboBox();
        valoresIniciales();
        iniciarValores();
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
        atsJaxb = new AtsJaxb();
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void listenerBotones() {
        
        getBtnGenerarXml().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /*try {
                    JAXBContext contexto = JAXBContext.newInstance(AtsJaxb.class);
                    Marshaller marshaller = contexto.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    //marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "libro.xsd");
                    //StringWriter sw = new StringWriter();
                    //marshaller.marshal(libro, System.out);
                    File file = new File( "tmp/ejemplo.xml" );
                    marshaller.marshal(atsJaxb, file);
                    UtilidadesSistema.abrirDocumento(file);
                    //return sw;
                    } catch (JAXBException ex) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                    Logger.getLogger(AtsModel.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                    UtilidadesArchivos.generarNombreArchivoUnico(MENU_BAR_PROPERTY, title)
                    File file = new File( ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES+"/ejemplo.xml" );
                    UtilidadesServidorXml.convertirObjetoXmlEnArchivo(atsJaxb,file);
                    UtilidadesSistema.abrirDocumento(file);
                } catch (IOException ex) {
                    Logger.getLogger(AtsModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer anio=(Integer)getTxtAnio().getValue();
                    MesEnum mesEnum=(MesEnum)getCmbMes().getSelectedItem();
                    String establecimiento=session.getSucursal().getCodigoSucursalFormatoTexto();
                    TipoAtsEnum tipoAts=(TipoAtsEnum) getCmbTipoAts().getSelectedItem();
        
                    atsJaxb = ServiceFactory.getFactory().getAtsServiceIf().consultarAts(tipoAts,anio,mesEnum,session.getEmpresa(),establecimiento,getRdbCompras().isSelected(),getRdbVentas().isSelected(),getRdbAnulados().isSelected());
                    if(atsJaxb.getAlertas().size()>0)
                    {
                        DialogoCodefac.mensaje(new CodefacMsj("El Ats se genero pero tiene advertencias", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    }
                    
                    construirTablaVenta();
                    construirTablaCompra();
                    construirTablaAnulados();
                    mostrarAlertasVista();

                } catch (RemoteException ex) {
                    Logger.getLogger(AtsModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(AtsModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void construirTablaCompra()
    {
        String titulo[] = {
            "",
            "Preimpreso",
            "Identificación",
            "# Comprobantes",
            "Base Imponible",
            "Iva",
            "Ret Renta",
            "Ret Iva"};
        
        Class clase[]={
            CompraAts.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
        };
        
        Boolean editar[]={
            false,
            false,
            false,
            false,
            false,
            false,
            true,
            true,
        };
        
        DefaultTableModel modeloTabla = UtilidadesTablas.crearModeloTabla(titulo, clase, editar);

        if(atsJaxb.getCompras()!=null)
        {
            for (CompraAts compra : atsJaxb.getCompras()) {
                modeloTabla.addRow(new Object[]{
                    compra,
                    compra.getPreimpreso(),
                    compra.getIdProv(),
                    "1",
                    (compra.getBaseImpGrav()==null)?"Error":compra.getBaseImpGrav().toString(),
                    (compra.getMontoIva()==null)?"Error":compra.getMontoIva().toString(),
                    "0",
                    "0",
                });
            }
        }

        getTblCompras().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblCompras(),0);
        
        //listener tabla de las ventas 
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int filaSeleccionada=e.getFirstRow();
                if(filaSeleccionada>=0)
                {
                    int columnaSeleccionada=e.getColumn();
                    DefaultTableModel modeloTabla=(DefaultTableModel) e.getSource();
                    VentaAts ventaAts=(VentaAts) modeloTabla.getValueAt(filaSeleccionada,COLUMNA_OBJETO);
                    
                    switch(columnaSeleccionada)
                    {
                        case COLUMNA_VENTA_RET_IVA:
                            BigDecimal valorNuevoIva=new BigDecimal(modeloTabla.getValueAt(filaSeleccionada,COLUMNA_VENTA_RET_IVA).toString());
                            ventaAts.setValorRetIva(valorNuevoIva);
                            break;

                        case COLUMNA_VENTA_RET_RENTA:
                            BigDecimal valorNuevoRenta=new BigDecimal(modeloTabla.getValueAt(filaSeleccionada,COLUMNA_VENTA_RET_RENTA).toString());
                            ventaAts.setValorRetRenta(valorNuevoRenta);
                            break;
                        
                    
                    }
                    
                    
                    
                }
            }
        });
    }

    private void construirTablaVenta() {
        
        String titulo[] = {
            "",
            "Identificación",
            "# Comprobantes",
            "Base Imponible",
            "Iva",
            "Ret Renta",
            "Ret Iva"};
        
        Class clase[]={
            VentaAts.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
        };
        
        Boolean editar[]={
            false,
            false,
            false,
            false,
            false,
            true,
            true,
        };
        
        DefaultTableModel modeloTabla = UtilidadesTablas.crearModeloTabla(titulo, clase, editar);

        if(atsJaxb.getVentas()!=null)
        {
            for (VentaAts venta : atsJaxb.getVentas()) {
                modeloTabla.addRow(new Object[]{
                    venta,
                    venta.getIdCliente(),
                    venta.getNumeroComprobantes() + "",
                    venta.getBaseImpGrav().toString(),
                    venta.getMontoIva().toString(),
                    venta.getValorRetRenta().toString(),
                    venta.getValorRetIva().toString(),
                });
            }
        }

        getTblVentas().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblVentas(),0);
        
        //listener tabla de las ventas 
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int filaSeleccionada=e.getFirstRow();
                if(filaSeleccionada>=0)
                {
                    int columnaSeleccionada=e.getColumn();
                    DefaultTableModel modeloTabla=(DefaultTableModel) e.getSource();
                    VentaAts ventaAts=(VentaAts) modeloTabla.getValueAt(filaSeleccionada,COLUMNA_OBJETO);
                    
                    switch(columnaSeleccionada)
                    {
                        case COLUMNA_VENTA_RET_IVA:
                            BigDecimal valorNuevoIva=new BigDecimal(modeloTabla.getValueAt(filaSeleccionada,COLUMNA_VENTA_RET_IVA).toString());
                            ventaAts.setValorRetIva(valorNuevoIva);
                            break;

                        case COLUMNA_VENTA_RET_RENTA:
                            BigDecimal valorNuevoRenta=new BigDecimal(modeloTabla.getValueAt(filaSeleccionada,COLUMNA_VENTA_RET_RENTA).toString());
                            ventaAts.setValorRetRenta(valorNuevoRenta);
                            break;
                        
                    
                    }
                    
                    
                    
                }
            }
        });
    }
    
    private void mostrarAlertasVista()
    {
        String alertaTxt="";
        if(atsJaxb!=null)
        {            
            for (String alerta : atsJaxb.getAlertas()) 
            {
                if(alertaTxt.isEmpty())
                {
                    alertaTxt+=alerta;
                }
                else
                {
                    alertaTxt+="\n"+alerta;
                }
            }
        }
        getTxtAlertasArea().setText(alertaTxt);
    }
    
    private void construirTablaAnulados() {
        String titulo[] = {
            "",
            "Establecimientos",
            "Punto Emisión",
            "Secuencial",
            "Autorización"};
        
        Class clase[]={
            AnuladoAts.class,
            String.class,
            String.class,
            String.class,
            String.class,
        };
        
        Boolean editar[]={
            false,
            false,
            false,
            false,
            false,
        };
        
        DefaultTableModel modeloTabla = UtilidadesTablas.crearModeloTabla(titulo, clase, editar);

        if(atsJaxb.getAnuladosAts()!=null)
        {
            for (AnuladoAts anulado : atsJaxb.getAnuladosAts()) {
                modeloTabla.addRow(new Object[]{
                    anulado,
                    anulado.getEstablecimiento(),
                    anulado.getPuntoEmision()+ "",
                    anulado.getSecuencialInicio().toString(),
                    anulado.getAutorizacion().toString(),
                });
            }
        }

        getTblAnulados().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblAnulados(),0);
        
        //listener tabla de las ventas 
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int filaSeleccionada=e.getFirstRow();
                if(filaSeleccionada>=0)
                {
                    int columnaSeleccionada=e.getColumn();
                    DefaultTableModel modeloTabla=(DefaultTableModel) e.getSource();
                    AnuladoAts anuladoAts=(AnuladoAts) modeloTabla.getValueAt(filaSeleccionada,COLUMNA_OBJETO);
                    /*
                    switch(columnaSeleccionada)
                    {
                        case COLUMNA_VENTA_RET_IVA:
                            BigDecimal valorNuevoIva=new BigDecimal(modeloTabla.getValueAt(filaSeleccionada,COLUMNA_VENTA_RET_IVA).toString());
                            ventaAts.setValorRetIva(valorNuevoIva);
                            break;

                        case COLUMNA_VENTA_RET_RENTA:
                            BigDecimal valorNuevoRenta=new BigDecimal(modeloTabla.getValueAt(filaSeleccionada,COLUMNA_VENTA_RET_RENTA).toString());
                            ventaAts.setValorRetRenta(valorNuevoRenta);
                            break;
                        
                    
                    }*/
                    
                    
                    
                }
            }
        });
    }

    private void valoresIniciales() {
        
        //Cargar los valores por defecto de los ats
        UtilidadesComboBox.llenarComboBox(getCmbTipoAts(),TipoAtsEnum.values());

        getCmbMes().removeAllItems();
        MesEnum[] meses = MesEnum.values();

        for (MesEnum mes : meses) 
        {
            getCmbMes().addItem(mes);
        }
        
        getRdbVentas().setSelected(true);
        getRdbCompras().setSelected(true);
        getRdbAnulados().setSelected(true);

    }

    private void iniciarValores() {
        
        JComponent componentSpinner=new JSpinner.NumberEditor(getTxtAnio(),"###############");
        getTxtAnio().setEditor(componentSpinner);
        
        int anioActual = UtilidadesFecha.obtenerAnio(UtilidadesFecha.getFechaHoy());
        getTxtAnio().setValue(anioActual);

        int mesActual = UtilidadesFecha.obtenerMes(UtilidadesFecha.getFechaHoy());
        MesEnum mesEnum = MesEnum.obtenerPorNumero(mesActual);
        getCmbMes().setSelectedItem(mesEnum);
        
    }

    private void listenerComboBox() {
        getCmbTipoAts().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) 
            {
                TipoAtsEnum tipoAtsEnum= (TipoAtsEnum) getCmbTipoAts().getSelectedItem();
                if(tipoAtsEnum.equals(TipoAtsEnum.MENSUAL))
                {
                    getCmbMes().setEnabled(true);
                }
                else
                {
                    getCmbMes().setEnabled(false);
                }
                
            }
        });
    }

    

}
