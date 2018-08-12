/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.util.Log;

/**
 *
 * @author Carlos
 */
public class ServidorSMS implements Runnable{

    private static final Logger LOG = Logger.getLogger(ServidorSMS.class.getName());
    /*
    Variable que me indica la cantidad maxima de caracteres que puede enviar en un mensaje de texto
    */
    public static final int LIMITE_CARACTERES=160;
    
    
    private int puerto;
    private ServerSocket servidorSocket;
    private List<Socket> clientesConectados;
    private Thread hilo;
    
    private static ServidorSMS instance;
    
    public static ServidorSMS getInstance()
    {
        if(instance==null)
        {
            instance=new ServidorSMS();
        }
        return instance;
    }
    
    public ServidorSMS() 
    {
        puerto=ParametrosSistemaCodefac.PUERTO_APP_MOVIL_SMS; //Obtener el puerto por el que se va a comunicar el sistema
        this.clientesConectados=new ArrayList<Socket>();
        //iniciarServidor();
    }
    
    public void iniciarServidor()
    {
        try {
            InetAddress addr = InetAddress.getByName("192.168.1.3");
            servidorSocket = new ServerSocket(puerto,50,addr);
            hilo=new Thread(this); //Iniciar el escucha de peticiones para conectarse al servidor
            hilo.start();
            LOG.log(Level.INFO,"Iniciado servicio SMS CODEFAC , PUERTO:"+servidorSocket.getLocalPort()+"IP: "+servidorSocket.getInetAddress().getHostAddress());
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void esperarConexion()
    {
        try {
            Socket socket=servidorSocket.accept();
            clientesConectados.add(socket);
            
            LOG.log(Level.INFO,"Nuevo celular conectado: "+socket.getLocalAddress().getLocalHost());
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarMensaje(String numero,String mensaje)
    {
        if(!clientesConectados.isEmpty())
        {
            Socket socketCliente=clientesConectados.get(0);
            String numeroCompleto=ParametrosSistemaCodefac.CODIGO_TELEFONO_ECUADOR+numero.substring(1);
            escribirSocket(numeroCompleto, socketCliente);
            escribirSocket(mensaje, socketCliente);

            LOG.log(Level.INFO,"Mensaje SMS enviado Número: "+numeroCompleto+", Mensaje:"+mensaje);            
        }
    }
    
    private void escribirSocket(String mensaje,Socket socketCliente)
    {
        PrintStream output = null;
        try {
            output = new PrintStream(socketCliente.getOutputStream());
            output.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //output.close();
        }
    }

    @Override
    public void run() {
        //Metodo que siempre esta esperando conexiones de clientes al servidor
        while(true)
        {
            esperarConexion();
        }
    }
    
    /**
     * Metodo que permite verificar si el servicio esta dicponible para proceder a enviar el mensaje
     * @return 
     */
    public Boolean servicioDisponible()
    {
        if(clientesConectados.size()>0)
            return true;
        
        return false;
    }
    
}
