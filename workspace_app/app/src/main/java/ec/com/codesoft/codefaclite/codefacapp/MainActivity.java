package ec.com.codesoft.codefaclite.codefacapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.txtEtiqueta);
        Usuario usaurio=new Usuario();
        usaurio.setNick("carlos");
        usaurio.setClave("1234");
        textView.setText(usaurio.getNick());
        new Conn().execute();
    }

    class Conn extends AsyncTask<Void, Void, MainActivity> {

        @Override
        protected MainActivity doInBackground(Void... params) {
            Looper.prepare();
            ServiceFactory.newController("192.168.1.5");

            PersonaServiceIf personaServiceIf= ServiceFactory.getFactory().getPersonaServiceIf();
            List<Persona> buscarList= personaServiceIf.obtenerTodos();
            //Persona persona=personaServiceIf.getEjemplo();
            //System.out.println(persona);

            for (Persona persona : buscarList) {
                System.out.println(persona.getNombres());
                System.out.println(persona.getIdentificacion());
            }
            Looper.loop();
            return null;
        }

    }
}
