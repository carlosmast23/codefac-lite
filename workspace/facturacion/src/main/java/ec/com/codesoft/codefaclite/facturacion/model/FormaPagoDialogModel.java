/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.facturacion.dialog.FormaPagoDialog;
import ec.com.codesoft.codefaclite.servidor.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidor.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidor.service.SriService;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class FormaPagoDialogModel extends FormaPagoDialog{
    private final String[] categoriaTiempo={"ninguno","Dias","Meses","Años"};
    private FormaPago formaPago;
    
    public FormaPagoDialogModel(Frame parent, boolean modal) {
        super(parent, modal);
        componentesIniciales();
        agregarListerBotones();        
    }

    private void componentesIniciales() {
        
        //Cargar valores de las formas de pago del sri
        SriService service=new SriService();
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
        
        getTxtPlazo().setText("");
        getTxtValor().setText("");
        
               
    }

    private void agregarListerBotones() {
        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPago=new FormaPago();
                formaPago.setPlazo(Integer.parseInt(getTxtPlazo().getText()));
                formaPago.setSriFormaPago((SriFormaPago) getCmbFormaPagoSri().getSelectedItem());
                formaPago.setUnidadTiempo(getCmbTiempo().getSelectedItem().toString());
                formaPago.setTotal(new BigDecimal(getTxtValor().getText())); 
                dispose();
            }
        });
    }
    
    public FormaPago getFormaPago()
    {
        return formaPago;
    }
}
