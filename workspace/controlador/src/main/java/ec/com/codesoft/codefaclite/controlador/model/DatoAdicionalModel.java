/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.panel.DatoAdicionalDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Carlos
 */
public class DatoAdicionalModel extends DatoAdicionalDialog{
    
    public String valor;
    public String campo;
    public FacturaAdicional.Tipo tipo;
    
    public DatoAdicionalModel() {
        super(null,true);
        
        //Centrar el dialogo en el centro de la pantalla
        setLocationRelativeTo(null);
        agregarValoresIniciales();
        agregarListenerBotones();
        agregarListenerCombos();
        getCmbTipo().setSelectedIndex(0);
    }

    private void agregarListenerBotones() {
        getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getTxtDato().getText()==null || getTxtDato().getText().equals(""))
                {
                    DialogoCodefac.mensaje("Advertencia","Por favor ingrese un datos para grabar",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                if(getTxtCampo().getText()==null || getTxtCampo().getText().equals(""))
                {
                    DialogoCodefac.mensaje("Advertencia","Por favor ingrese un nombre de campo para grabar",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }

                valor = getTxtDato().getText();
                campo = getTxtCampo().getText();
                tipo = (FacturaAdicional.Tipo) getCmbTipo().getSelectedItem();
                dispose();
            }
        });
        
        getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void agregarValoresIniciales() {
        //Agregar valor por defecto para el combo de tipo
        getCmbTipo().removeAllItems();
        getCmbTipo().addItem(FacturaAdicional.Tipo.TIPO_CORREO);
        getCmbTipo().addItem(FacturaAdicional.Tipo.TIPO_GUIA_REMISION);
        getCmbTipo().addItem(FacturaAdicional.Tipo.TIPO_OTRO);
        
        String datoAdicionalTitulo = ParametroUtilidades.obtenerValorParametroSinEmpresa(ParametroCodefac.DATO_ADICIONAL_TITULO);
        
        if(!UtilidadesTextos.verificarNullOVacio(datoAdicionalTitulo))
        {
            FacturaAdicional.Tipo tipoEnum=FacturaAdicional.Tipo.TIPO_ADICIONAL;
            tipoEnum.setNombre(datoAdicionalTitulo);
            getCmbTipo().addItem(tipoEnum);         
        }
        
    }

    private void agregarListenerCombos() {
        getCmbTipo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaAdicional.Tipo tipoEnum=(FacturaAdicional.Tipo) getCmbTipo().getSelectedItem();
                
                if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_CORREO))
                {
                    getTxtCampo().setText("correo");
                    getTxtCampo().setEnabled(false);
                }
                else if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_GUIA_REMISION))
                {
                    getTxtCampo().setText("guiaRemision");
                    getTxtCampo().setEnabled(false);
                }
                else if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_ADICIONAL))
                {
                    getTxtCampo().setText(FacturaAdicional.Tipo.TIPO_ADICIONAL.getNombre());
                    getTxtCampo().setEnabled(false);
                }
                else if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_OTRO))
                {
                    getTxtCampo().setText("");
                    getTxtCampo().setEnabled(true);
                }
            }
        });
    }
    
    
    
}
