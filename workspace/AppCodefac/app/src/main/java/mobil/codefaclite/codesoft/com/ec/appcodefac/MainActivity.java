package mobil.codefaclite.codesoft.com.ec.appcodefac;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String PARAMETRO_DIRECCION_IP="direccionip";
    public static final String PARAMETRO_PUERTO="puerto";


    /**
     * Referencia que almacena el boton para conectar al servidor de Codefac
     */
    private Button btnConectar;
    /**
     * Referencia del texto donde se ingresa la direccion Ip del servidor
     */
    private TextView txtServidoIp;
    /**
     * Referencia del texto del puerto para conectarme con el servidor
     */
    private TextView txtPuerto;

    /**
     * Objeto que permite lanzar el servicio que se encarga de leer las peticiones en el celular
     */
    private Intent intent;

    /**
     * Referencia que me permite acceder a la actividad principal , por ejemplo para acceder al metodo de enviar mensajes
     * TODO: Ver si esta opcion se puede optimizar
     */
    public static MainActivity actividadPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarVariables();
        agregarPermisos();
        listenerBotones();

        //receptorMensajeIntent();
    }

    /**
     * Metodo que se encarga de recibir la respuesta del servicio que se encarga de enviar mensajes para comunicar a la pantalla principal
     */
    private void receptorMensajeIntent()
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServicioSMS.NOMBRE_MENSAJE);
        //filter.addAction(MiIntentService.ACTION_FIN);
        ProgressReceiver rcv = new ProgressReceiver();
        registerReceiver(rcv, filter);
    }

    private void agregarPermisos() {
        PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, PermissionManager.REQUEST_CODE_FOR_SMS);
    }

    private void listenerBotones() {
        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("App: ", "Presionando boton");
                intent.putExtra(PARAMETRO_DIRECCION_IP, txtServidoIp.getText().toString());
                intent.putExtra(PARAMETRO_PUERTO, Integer.parseInt(txtPuerto.getText().toString()));
                startService(intent); //Empezar el servicio que se encarga de esperar peticiones para enviar mensajes

                Toast.makeText(getApplicationContext(), "Conectando", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void iniciarVariables() {
        this.btnConectar= (Button) findViewById(R.id.btnConectar);
        this.txtServidoIp=(TextView)findViewById(R.id.txtDireccionIp);
        this.txtPuerto=(TextView)findViewById(R.id.txtPuerto);

        intent = new Intent(MainActivity.this,ServicioSMS.class);
        actividadPrincipal=this;
    }

    /**
     * Metodo que se encarga de enviar el mensaje de texto
     * @param Numero
     * @param Mensaje
     */
    public void enviarMensaje (String Numero, String Mensaje){
        try {
            Thread.sleep(500);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(Numero,null,Mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }


    public class ProgressReceiver extends BroadcastReceiver {

        //TODO: Metodo que se encarga de actualizar los datos recibidos del servicio en la pantalla principal
        //Falta terminar de implementar
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
            if(intent.getAction().equals(ServicioSMS.NOMBRE_MENSAJE)) {
                String log = intent.getStringExtra("log");

                txtConsola.setText(log+"\n"+txtConsola.getText());


                //Eliminar los logs cuando el tamaño es demasiado grande para no tener problemas
                //de rendimiento con el celular
                if(txtConsola.getText().length()>=10000)
                {
                    txtConsola.setText(txtConsola.getText().toString().substring(0,10000));
                }

            }
            */

        }
    }


}
