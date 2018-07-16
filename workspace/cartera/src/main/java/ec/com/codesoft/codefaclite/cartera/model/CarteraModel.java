/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.CarteraPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraTipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
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
        for (CarteraEnum carteraEnum : CarteraEnum.values()) {
            getCmbTipoCartera().addItem(carteraEnum);
        }
    }

    private void listenerCombos() {
        getCmbTipoCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarteraEnum carteraEnum=(CarteraEnum) getCmbTipoCartera().getSelectedItem();
                if(carteraEnum!=null)
                {
                    List<CarteraDocumentoEnum> lista=CarteraDocumentoEnum.buscarPorTipoCartera(carteraEnum);
                    getCmbDocumentoCartera().removeAllItems();
                    for (CarteraDocumentoEnum carteraDocumentoEnum : lista) {
                        getCmbDocumentoCartera().addItem(carteraDocumentoEnum);
                    }                    
                }
            }
        });
        
        
        getCmbDocumentoCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarteraDocumentoEnum carteraDocumentoEnum=(CarteraDocumentoEnum) getCmbDocumentoCartera().getSelectedItem();
                if(carteraDocumentoEnum!=null)
                {
                    List<CarteraTipoDocumentoEnum> resultados=CarteraTipoDocumentoEnum.buscarPorDocumentoCartera(carteraDocumentoEnum);
                    
                    getCmbTipoDocumentoCartera().removeAllItems();
                    for (CarteraTipoDocumentoEnum resultado : resultados) 
                    {
                        getCmbTipoDocumentoCartera().addItem(resultado);
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

            private void cargarDatosCliente(Persona persona) {
                getLblNombresClientes().setText(persona.getRazonSocial());
                getLblDireccion().setText(persona.getDireccion());
                getLblTelefonos().setText(persona.getTelefonosTodos());
            }
        });
    }
    
}
