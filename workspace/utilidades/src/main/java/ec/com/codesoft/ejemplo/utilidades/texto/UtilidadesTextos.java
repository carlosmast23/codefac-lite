/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.texto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesTextos {
    public static String getStringURLFile(InputStream input)
    {   
        String text = "";
        try {
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            String line = null;        
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                text += line;
            }
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesTextos.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return text;
    }
    
    public static String llenarCarateresDerecha(String texto,Integer cantidad,String caracter)
    {
        return StringUtils.rightPad(texto, cantidad, caracter);
    }
    
    public static String llenarCarateresIzquierda(String texto,Integer cantidad,String caracter)
    {
        return StringUtils.leftPad(texto, cantidad, caracter);
    }
    
    public static String calcularModulo11(String clave) {
        int digitoVerificador = 0;
        int[] secuenciaMultiplicadores = {2, 3, 4, 5, 6, 7};
        int indiceMultiplicador = 0;
        int longitudClave = clave.length();
        int suma = 0;
        for (int i = longitudClave - 1; i >= 0; i--) {
            int c = Integer.parseInt(String.valueOf(clave.charAt(i)));
            suma += (c * (secuenciaMultiplicadores[indiceMultiplicador]));
            if (indiceMultiplicador == 5) {
                indiceMultiplicador = -1;
            }
            indiceMultiplicador++;
        }
        digitoVerificador = 11 - (suma % 11);
        if (digitoVerificador == 11) {
            digitoVerificador = 0;
        } else if (digitoVerificador == 10) {
            digitoVerificador = 1;
        }
        return new Integer(digitoVerificador).toString();
    }
    
    public static String castVectorToString(Vector<String> datos)
    {
        String cadena="";
        for (String dato : datos) 
        {
            cadena+=dato;
        }
        return cadena;
    }    
    

    /**
     * Convierte un input Stream a una cadena de texto
     * @param is
     * @return 
     */
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
    
    public static String documentToString(Document document) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            StringWriter sw = new StringWriter();
            trans.transform(new DOMSource(document), new StreamResult(sw));

            //Covertir a formato utf-8
            byte[] byteText = sw.toString().getBytes(Charset.forName("UTF-8"));
            return new String(byteText,"UTF-8");
        }catch (TransformerException tEx) {
            tEx.printStackTrace();
        }catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UtilidadesTextos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Integer transformarStringEntero(String dato)
    {
        Integer datoEntero = null;
        try
        {
            datoEntero = Integer.parseInt(dato);
        }catch(NumberFormatException exc)
        {
            System.out.println("Error entero: " + exc);
        }
                
        return datoEntero;
    }
    
    public static Double transformarStringDouble(String dato)
    {
        Double datoDouble = null;
        try
        {
            datoDouble = Double.parseDouble(dato);
        }catch(NumberFormatException exc)
        {
            System.out.println("Error double: " + exc);
        }
                
        return datoDouble;
    }
    
    public static Date transformarStringDate(String dato)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = null;
        try {
            fechaDate = dateFormat.parse(dato);
        } 
        catch (ParseException ex) 
        {
            System.out.println("Error fecha: " + ex);
        }
        return fechaDate;
    }
    
    public static String obtenerLetra(int columna)
    {
        int numero = 65 + columna;
        char letra = (char)numero;
        return letra+"";
    }
    
    
}
