/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.mensajes;

//import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;

import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_ADVERTENCIA;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_CORRECTO;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_INCORRECTO;
import java.util.Map;


/**
 *
 * @author Carlos
 */
public class CodefacMsj {
    
    /*public static final Integer MENSAJE_CORRECTO=1;
    public static final Integer MENSAJE_INCORRECTO=2;
    public static final Integer MENSAJE_ADVERTENCIA=3;*/
    
    public static final String TITULO_ADVERTENCIA="Advertencia";
    public static final String TITULO_CORRECTO="Correcto";
    public static final String TITULO_ERROR="Error";
    
    public String titulo;
    public String mensaje;
    public ModoMensajeEnum modoMensaje;
    
    
    public CodefacMsj(String mensaje,TipoMensajeEnum tipoMensajeEnum)
    {
        this.mensaje=mensaje;
        this.titulo=tipoMensajeEnum.getTitulo();
        this.modoMensaje=tipoMensajeEnum.getTipo();
        
    }

    
    public CodefacMsj(String titulo, String mensaje, ModoMensajeEnum modoMensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.modoMensaje = modoMensaje;
    }
    
    @Deprecated
    public CodefacMsj(String titulo, String mensaje, Integer modoMensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.modoMensaje = ModoMensajeEnum.buscarPorCodigo(modoMensaje);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ModoMensajeEnum getModoMensaje() {
        return modoMensaje;
    }

    public void setModoMensaje(ModoMensajeEnum modoMensaje) {
        this.modoMensaje = modoMensaje;
    }
    
    
    
    /**
     * Metodo que me permite agregar los parametro cuando el campo es parametrizable
     * @param mapParametros 
     */
    public CodefacMsj agregarParametros(Map<String,String> mapParametros)
    {
        for (Map.Entry<String, String> map : mapParametros.entrySet()) {
            String clave = map.getKey();
            String valor = map.getValue();
            
            this.titulo=this.titulo.replace("?"+clave,valor);
            this.mensaje=this.mensaje.replace("?"+clave,valor);
            
        }
        return this;
    }
        
    public enum TipoMensajeEnum
    {
        CORRECTO(TITULO_CORRECTO,MENSAJE_CORRECTO),
        ADVERTENCIA(TITULO_ADVERTENCIA,MENSAJE_ADVERTENCIA),
        ERROR(TITULO_ERROR,MENSAJE_INCORRECTO);
        
        private TipoMensajeEnum(String titulo,ModoMensajeEnum tipo) 
        {
            this.titulo = titulo;
            this.tipo=tipo;
        }
        
        private String titulo;
        private ModoMensajeEnum tipo;

        public String getTitulo() {
            return titulo;
        }

        public ModoMensajeEnum getTipo() {
            return tipo;
        }

        
        
    }
    
    public enum ModoMensajeEnum
    {
        MENSAJE_CORRECTO(1),
        MENSAJE_INCORRECTO(2),
        MENSAJE_ADVERTENCIA(3);
        
        /**
         * Codigo especialmente util si tengo que persistir los datos en una base de datos y luego volver a utilizar
         */
        private Integer codigo;
        
        private ModoMensajeEnum(Integer codigo)
        {
            this.codigo=codigo;
        }

        public Integer getCodigo() {
            return codigo;
        }

        public static ModoMensajeEnum buscarPorCodigo(Integer codigo)
        {
            for(ModoMensajeEnum modoMensajeEnum  : ModoMensajeEnum.values())
            {
                if(modoMensajeEnum.getCodigo().equals(codigo))
                {
                    return modoMensajeEnum;
                }
            }
            return null;
        }
        
    }
}
