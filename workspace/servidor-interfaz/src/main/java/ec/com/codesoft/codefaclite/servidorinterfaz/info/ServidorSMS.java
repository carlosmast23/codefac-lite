/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ServidorSMS implements Runnable{
    
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
            servidorSocket = new ServerSocket(puerto);
            hilo=new Thread(this); //Iniciar el escucha de peticiones para conectarse al servidor
            hilo.start();
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void esperarConexion()
    {
        try {
            Socket socket=servidorSocket.accept();
            clientesConectados.add(socket);
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarMensaje(String numero,String mensaje)
    {
        if(!clientesConectados.isEmpty())
        {
            Socket socketCliente=clientesConectados.get(0);
            escribirSocket(ParametrosSistemaCodefac.CODIGO_TELEFONO_ECUADOR+numero, socketCliente);
            escribirSocket(mensaje, socketCliente);            
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
    
}
