/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialog;

import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogModel;
import ec.com.codesoft.codefaclite.controlador.panel.DialogoCargando;
import ec.com.codesoft.codefaclite.controlador.panel.PanelCargando;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public abstract class DialogoCodefac {
    
    public static ModelControladorAbstract.MensajeVistaInterface intefaceMensaje=new ModelControladorAbstract.MensajeVistaInterface() {
        public void mensaje(CodefacMsj codefacMensaje) {
            DialogoCodefac.mensaje(codefacMensaje);
        }

        @Override
        public boolean dialogoPregunta(CodefacMsj codefacMensaje) {
           return DialogoCodefac.dialogoPregunta(codefacMensaje);
        }

    };
    
    public static final Integer MENSAJE_CORRECTO=1;
    public static final Integer MENSAJE_INCORRECTO=2;
    public static final Integer MENSAJE_ADVERTENCIA=3;
    
    public static final String ETIQUETA_MENSAJE_CORRECTO="Correcto";
    public static final String  ETIQUETA_MENSAJE_INCORRECTO="Incorrecto";
    public static final String  ETIQUETA_MENSAJE_ADVERTENCIA="Advertencia";
    
    public static void mensaje(CodefacMsj codefacMsj) 
    {
        mensaje(codefacMsj.titulo,codefacMsj.mensaje,codefacMsj.modoMensaje);
    }
    
    public static void mensaje(String mensaje,Integer tipoMensaje)
    {
        if(tipoMensaje.equals(MENSAJE_CORRECTO))
        {
            mensaje(ETIQUETA_MENSAJE_CORRECTO, mensaje, tipoMensaje);
        }
        else
        {
            if(tipoMensaje.equals(MENSAJE_INCORRECTO))
            {
                mensaje(ETIQUETA_MENSAJE_INCORRECTO, mensaje, tipoMensaje);
            }
            else
            {
                if (tipoMensaje.equals(MENSAJE_ADVERTENCIA)) {
                    mensaje(ETIQUETA_MENSAJE_ADVERTENCIA, mensaje, tipoMensaje);
                }
            }
        }
    }
    
    
    public static void mensaje(String titulo,String mensaje,Integer tipoMensaje)
    {
        ImageIcon icono=null;
       
        if(tipoMensaje.equals(MENSAJE_CORRECTO))
        {
            icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-correcto.png"));
        }
        else
        {
            if(tipoMensaje.equals(MENSAJE_INCORRECTO))
            {
                icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-error.png"));
            }
            else
            {
                if (tipoMensaje.equals(MENSAJE_ADVERTENCIA)) {
                    icono = new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-informacion.png"));
                }
            }
        }
       
        //ImageIcon icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-correcto.png"));
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Codefac ["+titulo+"]",
                JOptionPane.WARNING_MESSAGE,
                icono);
    }
    
    public static void mostrarDialogoCargando(ProcesoSegundoPlano proceso)
    {        
        DialogoCargando dialogo= DialogoCargando.getInstance();
        HiloSegundoPlano hilo=new HiloSegundoPlano(proceso,dialogo);
        hilo.start();
        dialogo.getLblMensaje().setText(proceso.getMensaje());
        dialogo.setVisible(true);
        //dialogo.getLblMensaje().setText(proceso.getMensaje());
        
        //dialogo.setVisible(true);
    }
    
    public static boolean dialogoPregunta(CodefacMsj codefacMsj) {
        return dialogoPregunta(codefacMsj.titulo,codefacMsj.mensaje,codefacMsj.modoMensaje);
    }
    
    /**
     * TODO: Mejorar el codigo porque esos if se repiten en el metodo interno , se puede agregar un parametro en el metodo interno para que me de poniendo las etiqueteas por edefecto
     * @param mensaje
     * @param tipoMensaje
     * @return 
     */
    public static Boolean dialogoPregunta(String mensaje, Integer tipoMensaje) {
        if(tipoMensaje.equals(MENSAJE_CORRECTO))
        {
            return dialogoPregunta(ETIQUETA_MENSAJE_CORRECTO, mensaje, tipoMensaje);
        }
        else
        {
            if(tipoMensaje.equals(MENSAJE_INCORRECTO))
            {
                return dialogoPregunta(ETIQUETA_MENSAJE_INCORRECTO, mensaje, tipoMensaje);
            }
            else
            {
                if (tipoMensaje.equals(MENSAJE_ADVERTENCIA)) {
                    return dialogoPregunta(ETIQUETA_MENSAJE_ADVERTENCIA, mensaje, tipoMensaje);
                }
            }
        }
        return null;
    }
    
    public static boolean dialogoPregunta(String titulo, String mensaje, Integer tipoMensaje) {
        ImageIcon icono=null;
       
        if(tipoMensaje.equals(MENSAJE_CORRECTO))
        {
            icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-correcto.png"));
        }
        else
        {
            if(tipoMensaje.equals(MENSAJE_INCORRECTO))
            {
                icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-error.png"));
            }
            else
            {
                if (tipoMensaje.equals(MENSAJE_ADVERTENCIA)) {
                    icono = new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-informacion.png"));
                }
            }
        }
        
        int resp = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, icono);
        if(resp == JOptionPane.YES_OPTION) {
          return true;
        } else {
          return false;
        } 
    }
    
    public static void dialogoReporteOpciones(ReporteDialogListener listener)
    {
        ReporteDialogModel dialog=new ReporteDialogModel();
        dialog.addListener(listener);
        dialog.setVisible(true);
    }
    
    public static int dialogoPreguntaPersonalizada(String titulo, String mensaje, Integer tipoMensaje,String[] opciones) {
        ImageIcon icono=null;
       
        if(tipoMensaje.equals(MENSAJE_CORRECTO))
        {
            icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-correcto.png"));
        }
        else
        {
            if(tipoMensaje.equals(MENSAJE_INCORRECTO))
            {
                icono=new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-error.png"));
            }
            else
            {
                if (tipoMensaje.equals(MENSAJE_ADVERTENCIA)) {
                    icono = new ImageIcon(RecursoCodefac.IMAGENES_ICONOS_DIALOGOS.getResourceURL("icono-informacion.png"));
                }
            }
        }
        
        int resp = JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, icono,opciones,opciones[0]);
        return resp;
    }
    
    

}
