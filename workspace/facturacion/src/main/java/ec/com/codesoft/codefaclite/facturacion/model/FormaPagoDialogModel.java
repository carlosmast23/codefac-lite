/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.facturacion.dialog.FormaPagoDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.Banco;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class FormaPagoDialogModel extends FormaPagoDialog{
    //Codigo SRI de "Efectivo" (ver SRI_FORMA_PAGO.CODIGO en la base, confirmado por consulta directa:
    //ID=1/Efectivo es el unico con codigo "01"; el resto -tarjetas, transferencia, cheque, etc.- son
    //codigos distintos y SI requieren datos de banco segun el catalogo oficial del SRI).
    private static final String CODIGO_SRI_EFECTIVO = "01";
    private final String[] categoriaTiempo={"ninguno","Dias","Meses","Años"};
    private FormaPago formaPago;

    public FormaPagoDialogModel(Frame parent, boolean modal) {
        super(parent, modal);
        componentesIniciales();
        agregarListerBotones();
        getRootPane().setDefaultButton(getBtnAceptar());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                //El cajero normalmente solo necesita confirmar (Valor ya viene precargado desde
                //quien abre el dialogo); dejar el foco inicial en Aceptar evita que tenga que
                //buscar el mouse y permite confirmar con Enter de una vez.
                getBtnAceptar().requestFocusInWindow();
            }
        });
    }

    private void componentesIniciales() {
        
        try {
            //Cargar valores de las formas de pago del sri
            SriServiceIf service=ServiceFactory.getFactory().getSriServiceIf();
            List<SriFormaPago> formasPagoSri=service.obtenerFormasPagoActivo();
            getCmbFormaPagoSri().removeAllItems();
            
            for (SriFormaPago sriFormaPago : formasPagoSri) {
                getCmbFormaPagoSri().addItem(sriFormaPago);
            }
            
            //Setear valores catergorias tiempo
            getCmbTiempo().removeAllItems();
            for (String categoria : categoriaTiempo) {
                getCmbTiempo().addItem(categoria);
            }
            
            //Setear valores de los bancos
            getCmbBanco().removeAllItems();
            List<Banco> bancoList= ServiceFactory.getFactory().getBancoServiceIf().obtenerActivosPorEmpresa(null);
            
            for (Banco banco : bancoList) 
            {
                getCmbBanco().addItem(banco);
            }
            
            getTxtPlazo().setText("");
            getTxtValor().setText("");

            getCmbFormaPagoSri().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        actualizarVisibilidadBanco();
                    }
                }
            });
            actualizarVisibilidadBanco();
        } catch (RemoteException ex) {
            Logger.getLogger(FormaPagoDialogModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FormaPagoDialogModel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Banco solo tiene sentido cuando la forma de pago elegida pasa por el sistema financiero
     * (cualquiera distinta de Efectivo, codigo SRI "01"); para Efectivo se oculta la fila completa
     * en vez de dejarla visible pero sin uso.
     */
    private void actualizarVisibilidadBanco() {
        SriFormaPago seleccionada = (SriFormaPago) getCmbFormaPagoSri().getSelectedItem();
        boolean requiereBanco = seleccionada != null && !CODIGO_SRI_EFECTIVO.equals(seleccionada.getCodigo());
        getLblBanco().setVisible(requiereBanco);
        getCmbBanco().setVisible(requiereBanco);
    }

    private void agregarListerBotones() {
        getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPago = null;
                dispose();
            }
        });

        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPago=new FormaPago();
                String plazoStr=(getTxtPlazo().getText().equals(""))?"0":getTxtPlazo().getText();
                formaPago.setPlazo(Integer.parseInt(plazoStr));
                formaPago.setSriFormaPago((SriFormaPago) getCmbFormaPagoSri().getSelectedItem());
                formaPago.setUnidadTiempo(getCmbTiempo().getSelectedItem().toString());
                formaPago.setTotal(new BigDecimal(getTxtValor().getText()));
                formaPago.setTotal(formaPago.getTotal().setScale(2, RoundingMode.HALF_UP));
                dispose();
            }
        });
    }
    
    public FormaPago getFormaPago()
    {
        return formaPago;
    }
    
    
}
