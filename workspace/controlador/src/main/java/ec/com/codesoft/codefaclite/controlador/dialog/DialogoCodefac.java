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
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public abstract class DialogoCodefac {
    
    public static final Integer MENSAJE_CORRECTO=1;
    public static final Integer MENSAJE_INCORRECTO=2;
    public static final Integer MENSAJE_ADVERTENCIA=3;
    
    public static void mensaje(CodefacMsj codefacMsj) 
    {
        mensaje(codefacMsj.titulo,codefacMsj.mensaje,codefacMsj.modoMensaje);
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
