/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.ReporteCarteraPanel;
import ec.com.codesoft.codefaclite.cartera.reportdata.CarteraDocumentoData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReporteCarteraModel extends ReporteCarteraPanel{
    
    private List<CarteraDocumentoData> resultadoData;
    private Persona personaBusqueda;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        setTitle(VentanaEnum.REPORTE_CARTERA.getNombre());
        datosInicialesVista();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void datosInicialesVista() {
        
        UtilidadesComboBox.llenarComboBox(getCmbTipoCartera(), Cartera.TipoCarteraEnum.values());
        UtilidadesComboBox.llenarComboBox(getCmbTipoSaldo(),Cartera.TipoSaldoCarteraEnum.values());        
    }
    
    private void construirResultadoData(List<Cartera> datos)
    {
        if(datos!=null)
        {
            resultadoData=new ArrayList<CarteraDocumentoData>();
            
            for (Cartera dato : datos) {
                resultadoData.add(new CarteraDocumentoData(
                        dato.getCodigo(), 
                        dato.obtenerDescripciones(),
                        dato.getTotal().toString(), 
                        dato.getSaldo().toString(), 
                        dato.getPreimpreso(), 
                        dato.getFechaEmision().toString(), 
                        dato.getPersona().getNombresCompletos(), 
                        dato.getCarteraDocumentoEnum().getNombre()));                
            }
        }
    }
    
    private void mostrarDatosTabla()
    {
        DefaultTableModel modeloTabla=new DefaultTableModel(new String[]{
            "Código",
            "Documento",
            "Descripción",
            "Nombres Persona",
            "Total",
            "Saldo",
            "Preimpreso",
            "Fecha Emisión",
        },0);
        
        for (CarteraDocumentoData carteraData : resultadoData) {
            modeloTabla.addRow(new String[]{
                carteraData.getCodigo(),
                carteraData.getDocumento(),
                carteraData.getDescripcion(),
                carteraData.getPersona(),
                carteraData.getValor(),
                carteraData.getSaldo(),
                carteraData.getPreimpreso(),
                carteraData.getFechaEmision()
            });
        }
        getTblDatos().setModel(modeloTabla);
        
        
        
    }

    private void listenerBotones() {
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                java.sql.Date fechaInicio=(getCmbFechaInicio().getDate()!=null)?new java.sql.Date(getCmbFechaInicio().getDate().getTime()):null;
                java.sql.Date fechaFin=(getCmbFechaFin().getDate()!=null)? new java.sql.Date(getCmbFechaFin().getDate().getTime()):null;
                Cartera.CarteraCategoriaEnum carteraCategoriaEnum=(Cartera.CarteraCategoriaEnum) getCmbDocumentoCategoriaCartera().getSelectedItem();
                //DocumentoCategoriaEnum.
                try {
                    List<Cartera> resultado= ServiceFactory.getFactory().getCarteraServiceIf().listaCarteraSaldoCero(
                            personaBusqueda,
                            fechaInicio,
                            fechaFin,
                            carteraCategoriaEnum.getDocumentoCategoriaEnum(),
                            (Cartera.TipoCarteraEnum)getCmbTipoCartera().getSelectedItem(),
                            true);
                    
                    construirResultadoData(resultado);
                    mostrarDatosTabla();
                                        
                    
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ReporteCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
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
    }
    
    
}
