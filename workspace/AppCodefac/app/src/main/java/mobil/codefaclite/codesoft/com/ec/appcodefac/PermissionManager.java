package mobil.codefaclite.codesoft.com.ec.appcodefac;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Carlos on 10/08/2018.
 */

/**
 * Clase que pemirte agregar permisos para ciertas funcionalidad de las aplicaciones Android
 */
public class PermissionManager {

    /**
     * CODIGO PERMISO PARA ENVIO DE MENSAJES
     */
    public static final int REQUEST_CODE_FOR_SMS=1;

    //A method that can be called from any Activity, to check for specific permission
    public static void check(Activity activity, String permission, int requestCode){
        //If requested permission isn't Granted yet
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
        }
    }
}
