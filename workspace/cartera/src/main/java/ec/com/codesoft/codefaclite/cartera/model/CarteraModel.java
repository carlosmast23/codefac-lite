/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.busqueda.CarteraBusqueda;
import ec.com.codesoft.codefaclite.cartera.panel.CarteraPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.apache.commons.collections4.map.HashedMap;

/**
 *
 * @author Carlos
 */
public class CarteraModel extends CarteraPanel{
    
    /**
     * Referencia para guardar con la cartera que se va a trabajar
     */
    private Cartera cartera;

    public CarteraModel() {
    }

    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        valoresIniciales();
        listenerCombos();
        listenerTextos();
        listenerBotones();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        getCmbTipoCartera().setSelectedIndex(0);
        getCmbFechaEmision().setDate(UtilidadesFecha.getFechaHoy());
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

    private void valoresIniciales() {
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
                            Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            mapParametros.put("identificacion", identificacion);
                            List<Persona> resultados=ServiceFactory.getFactory().getPersonaServiceIf().obtenerPorMap(mapParametros); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(resultados.size()==0)
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
                                cartera.setPersona(resultados.get(0));
                                cargarDatosCliente(resultados.get(0));
                               //Opcion cuando encuentra los datos del cliente 
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(CarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
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
        getLblDireccion().setText(persona.getDireccion());
        getLblTelefonos().setText(persona.getTelefonosTodos());
        getTxtIdentificacion().setText(persona.getIdentificacion());
    }
    
    private void btnListenerBuscarCliente() {
        ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());
        Persona personaTemp=(Persona) buscarDialogoModel.getResultado();
        if(personaTemp!=null)
        {
            cartera.setPersona(personaTemp);
            cargarDatosCliente((Persona) buscarDialogoModel.getResultado());
        }
       
    }

    private void listenerBotones() {
        getBtnBuscarPersona().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });
        
        getBtnBuscarDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cartera.TipoCarteraEnum tipoCarteraEnum=(Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem();                
                if(tipoCarteraEnum!=null)
                {
                    CarteraBusqueda carteraBusqueda=new CarteraBusqueda();
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(carteraBusqueda);
                    buscarDialogoModel.setVisible(true);
                    Cartera carteraTmp=(Cartera) buscarDialogoModel.getResultado();
                    if(carteraTmp!=null)
                    {
                        
                    }
                }
            }
        });
    }
    
    
}
