/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio.TipoClienteSwingEnum;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import static ec.com.codesoft.codefaclite.main.init.Main.modoAplicativo;
import ec.com.codesoft.codefaclite.main.panel.IngresarDatosClienteDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Carlos
 */
public class IngresarDatosClienteModel extends IngresarDatosClienteDialog{
    
    public IngresarDatosClienteModel() {
        super(null,true);
        setLocationRelativeTo(null);
        init();
        listenerBotones();
        listenerVentana();
    }
    
    private void listenerVentana()
    {
        WindowAdapter windowsAdapter=new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        
        addWindowListener(windowsAdapter);
    }
    
    public IngresarDatosClienteModel(String ip,TipoClienteSwingEnum tipoClienteSwingEnum) {
        super(null,true);
        setLocationRelativeTo(null);
        init();
        listenerBotones();
        cargarDatosDefecto(ip,tipoClienteSwingEnum);
    }
    
    private void init()
    {
        getCmbTipoCliente().removeAllItems();
        UtilidadesComboBox.llenarComboBox(getCmbTipoCliente(),TipoClienteSwingEnum.values());
    }
    
    public Respuesta obtenerDatosIngresados()
    {
               
        return new Respuesta(
                getTxtIpServidor().getText(), 
                (TipoClienteSwingEnum) getCmbTipoCliente().getSelectedItem());
        
    }
    
    private boolean validarDatos()
    {
        if(getTxtIpServidor().getText().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","El campo de la ip no puede estar vacio ",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        return true;
    }

    private void listenerBotones() {
        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarDatos()) {
                    return;
                }
                
                dispose(); //Si todo va bien cierro el dialogo
            }
        });
        
        getBtnActualizarSistema().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ACTUALIZAR_SISTEMA))
                {
                    ArchivoConfiguracionesCodefac.grabarConfiguracion(ArchivoConfiguracionesCodefac.CAMPO_MODO_ACTUALIZACION,ArchivoConfiguracionesCodefac.ModoActualizacionEnum.DESARROLLO.getNombre());
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.REINICIAR_SISTEMA);                    
                    System.exit(0);
                }
            }
        });
    }

    private void cargarDatosDefecto(String ip, TipoClienteSwingEnum tipoClienteSwingEnum) {
        if(ip!=null && !ip.isEmpty())
        {
            getTxtIpServidor().setText(ip);
        }
        
        if(tipoClienteSwingEnum!=null)
        {
            getCmbTipoCliente().setSelectedItem(tipoClienteSwingEnum);
        }
    }
    
    public class Respuesta
    {
        public String ipPublica;
        public TipoClienteSwingEnum tipoClienteEnum;

        public Respuesta(String ipPublica, TipoClienteSwingEnum tipoClienteEnum) {
            this.ipPublica = ipPublica;
            this.tipoClienteEnum = tipoClienteEnum;
        }
        
        
    }
}
