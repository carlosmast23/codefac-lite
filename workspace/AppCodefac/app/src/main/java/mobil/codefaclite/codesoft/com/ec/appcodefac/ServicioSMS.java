package mobil.codefaclite.codesoft.com.ec.appcodefac;

/**
 * Created by Carlos on 10/08/2018.
 */

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Clase que me permite lanzar un servicio(Hilo) que se va a encargar de leer peticiones para enviar mensajes
 */
public class ServicioSMS extends IntentService
{
    public ServicioSMS() {
        super("ServicioSMS");
    }

    public static final String NOMBRE_MENSAJE ="appcodefac.serviciosms";


    /**
     *  Metodo que ejecutara una tarea en segundo plano
     * @param intencion
     */
    @Override
    protected void onHandleIntent(Intent intencion) {

        try {
            Log.i("I/TCP Client", "Iniciando servicio");
            String direccionServidor=intencion.getStringExtra(MainActivity.PARAMETRO_DIRECCION_IP); //Obtiene la direccion ip de la clase principal
            int puertoConexion=intencion.getIntExtra(MainActivity.PARAMETRO_PUERTO,0); //Obtiene el puerto de conexion de la clase principal

            InetAddress serverAddr = InetAddress.getByName(direccionServidor);
            mensajeBroadcast("Conectando con "+direccionServidor+":"+puertoConexion+" ...");
            Socket socket = new Socket(serverAddr,puertoConexion);
            Log.i("I/TCP Client", "Conectado con el Servidor");

            mensajeBroadcast("Conectado exitosamente ...");
            //Se queda en bucle infinito hasta enviar los mensajes

            InputStream inputStream = socket.getInputStream();
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader( inputStream ) );
            while(true) {
                String numeroStr="";
                numeroStr=entrada.readLine(); //Lectura de la primera linea que contiene el numero de telefono
                Log.i("I/TCP Client", "Telefono a Enviar: " + numeroStr);

                String mensajeTxt =entrada.readLine(); // Lectura que contiene el mensaje a enviar
                Log.i("I/TCP Client", "MensajeRecibido: " + mensajeTxt);
                mensajeBroadcast("enviando mensaje a "+numeroStr+ ": "+mensajeTxt);
                MainActivity.actividadPrincipal.enviarMensaje(numeroStr,mensajeTxt); //TODO: Optimizar el codigo para ver si puedo usar el metodo de enviar mensaje desde esta misma clase
                mensajeBroadcast("mensaje enviado ...");

            }
            //cierra conexion
            //socket.close();
        }catch (UnknownHostException ex) {
            Log.e("E/TCP Client", "" + ex.getMessage());
            mensajeBroadcast(ex.getMessage());

        } catch (IOException ex) {
            Log.e("E/TCP Client", "" + ex.getMessage());
            mensajeBroadcast(ex.getMessage());
        }
    }

    /**
     * Metodo que me permite enviar un mensaje de log a la pantalla principal
     */
    private void mensajeBroadcast (String log)
    {
        //Agregando la fecha de la aplicacion ///
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR, 17);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 2);

        log="I:["+simpleDateFormat.format(calendar.getTime())+" "+log+"]";


        Intent bcIntent = new Intent();
        bcIntent.setAction(NOMBRE_MENSAJE);
        bcIntent.putExtra("log",log);
        sendBroadcast(bcIntent);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        mensajeBroadcast("intentado conectar con el servidor ...");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mensajeBroadcast("proceso conexion terminado ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

}
