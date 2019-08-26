/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class LoginRespuesta implements Serializable{
    /**
     * Usuario del login cargado
     */
    public Usuario usuario;
    /**
     * Estado del Login
     */
    public EstadoLoginEnum estadoEnum;
    /**
     *  Variable que me permite imprimir alertas al usuario de cualquier tipo
     */
    public List<String> alertas;

    public LoginRespuesta(Usuario usuario, EstadoLoginEnum estadoEnum, List<String> alertas) {
        this.usuario = usuario;
        this.estadoEnum = estadoEnum;
        this.alertas = alertas;
    }

    public LoginRespuesta() {
        this.estadoEnum = EstadoLoginEnum.ERROR_DESCONOCIDO; //Por defecto muestro este tipo de error
    }
    
    
    
    public String obtenerAlertasConFormato()
    {
        String mensaje="";
        for (String alerta : alertas) {
            mensaje="- "+alerta+"\n";
        }
        //String nl = System.getProperty("line.separator");

        return mensaje;
        
    }
    
    
    public enum EstadoLoginEnum
    {
        /**
         * Estado cuando se el usuario esta logueado correctamente
         */
        CORRECTO_USUARIO("Usuario Correcto"),
        /**
         * Estado cuando el usuario ingreso mal los datos
         */
        INCORRECTO_USUARIO("Credenciales del Usuario Incorrecto"),
        /**
         * Estado cuando el usuario se encuentra inactivo en el sistem
         */
        INACTIVO_USUARIO("Usuario Inactivo en el sistema , porfavor consulte con el administrador"),
        /**
         * Mensaje cuando no existe seleccionado un directorio para poder verificar la licencia
         */
        NO_EXISTE_DIRECTORIO_LICENCIA("No se encuentra definido un directorio para la empresa"),
        /**
         * Estado que representa que la licencia se debe actualizar 
         */
        LICENCIA_DESACTUALIZADA("Existe un problema con la licencia, porfavor actualice su licencia"),
        /**
         * Estado que me permite informar que el sistema tiene valores pendientes de pago
         */
        LICENCIA_PAGO_VENCIDO("No puede usar el sistema porque se registran deudas pendientes"),
        
        /**
         * Estado que me permite informar que la licencia tiene un error con las fechas puede ser orque cambiaron en el sistema
         */
        LICENCIA_ERROR_FECHAS("Error al comprobar la licencia , revise que la fecha del sistema son correctas"),
        
        /**
         * Estado que me permite informar que la licencia no se puede validar porque no tiene internet
         */
        LICENCIA_ERROR_INTERNET("No se puede comprobar la licencia porque no tiene internet"),
        
        /**
         * Estado que me permite informar que no existe novedades con la licencia
         */
        LICENCIA_CORRECTA("Licencia Correcta"),
        
        /**
         * Mensaje cuando el sistema detecta pagos pendientes
         */
        PAGOS_PENDIENTES("El sistema detecta valores pendientes de pago y no se puede abrir\n Porfavor cancele los valores pendientes para continuar con el servicio."),
        
        /**
         * Estado cuando sucede algun problema pero no esta clasificado
         */
        ERROR_DESCONOCIDO("Error desconocido");
        
        private String mensaje;

        private EstadoLoginEnum(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }
        
        
        
        
    }
}
