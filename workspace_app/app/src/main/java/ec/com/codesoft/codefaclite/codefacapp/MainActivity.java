package ec.com.codesoft.codefaclite.codefacapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;

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
    }
}
