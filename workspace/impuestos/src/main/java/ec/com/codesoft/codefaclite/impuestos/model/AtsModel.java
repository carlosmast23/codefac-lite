/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.impuestos.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.impuestos.panel.AtsPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JSpinner;
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

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
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
                }
            }
        });

        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer anio=(Integer)getTxtAnio().getValue();
                    MesEnum mesEnum=(MesEnum)getCmbMes().getSelectedItem();
                    String establecimiento=session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor;
                    atsJaxb = ServiceFactory.getFactory().getAtsServiceIf().consultarAts(anio,mesEnum,session.getEmpresa(),establecimiento);
                    construirTablaVenta();

                } catch (RemoteException ex) {
                    Logger.getLogger(AtsModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(AtsModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void construirTablaVenta() {
        String titulo[] = {
            "Identificación",
            "# Comprobantes",
            "Base Imponible",
            "Iva",};
        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0);

        for (VentaAts venta : atsJaxb.getVentas()) {
            modeloTabla.addRow(new String[]{
                venta.getIdCliente(),
                venta.getNumeroComprobantes() + "",
                venta.getBaseImponible().toString(),
                venta.getMontoIva().toString()

            });
        }

        getTblVentas().setModel(modeloTabla);
    }

    private void valoresIniciales() {

        getCmbMes().removeAllItems();
        MesEnum[] meses = MesEnum.values();

        for (MesEnum mes : meses) 
        {
            getCmbMes().addItem(mes);
        }

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

}
