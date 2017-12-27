/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.varios;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadVarios {
    public static void abrirArchivo(String path)
    {
        try {
            File file = new File(path);
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static  String obtenerMac()
    {
        NetworkInterface a;
        String linea;
        try {
            //a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = a.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return "";
    }
    
    public static  String obtenerMacSinInternet()
    {
        Vector<String> excepcionesMac=new Vector<String>();
        excepcionesMac.add("Microsoft Wi-Fi Direct Virtual Adapter");
        
        try {
            final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) 
            {
                NetworkInterface networkInterface=e.nextElement();
                final byte [] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++)
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    
                    String nombreInterface=networkInterface.getName().toLowerCase();
                    int indiceNet=nombreInterface.indexOf("eth");
                    int indiceWlan=nombreInterface.indexOf("wlan");
                    if((indiceNet>=0 || indiceWlan>=0) && !excepcionesMac.contains(networkInterface.getDisplayName()))
                    {
                        System.out.println(networkInterface.getDisplayName());
                        System.out.println(networkInterface.getName());
                        System.out.println(sb.toString());
                        return sb.toString();
                    }
                }
                //break;
            }
        } catch (SocketException ex) {
            Logger.getLogger(UtilidadVarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        
    }
    
    
    public static String getStringHtmltoUrl(InputStream input)
    {
        try {
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            String htmlText = "";
            while ((line = br.readLine()) != null) {
                htmlText += line;
            }
            return htmlText;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadVarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
