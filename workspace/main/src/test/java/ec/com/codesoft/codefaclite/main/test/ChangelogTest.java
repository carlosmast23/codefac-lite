package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogCambioData;
import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogVersionData;
import ec.com.codesoft.codefaclite.main.utilidades.ChangelogUtil;
import java.util.List;

/**
 * Clase simple para validar la lectura del archivo changelog.json.
 */
public class ChangelogTest {

    public static void main(String[] args) {
        List<ChangelogVersionData> historial = ChangelogUtil.leerChangelog();

        System.out.println("=== CHANGELOG CODEFAC ===");
        System.out.println("Version actual: " + ChangelogUtil.obtenerVersionActual());
        System.out.println("Total versiones: " + historial.size());
        System.out.println("");

        for (ChangelogVersionData versionData : historial) {
            System.out.println("Version: " + versionData.getVersion());
            System.out.println("Fecha: " + versionData.getFecha());
            System.out.println("Titulo: " + versionData.getTitulo());
            System.out.println("Cambios:");

            if (versionData.getCambios() != null) {
                for (ChangelogCambioData cambioData : versionData.getCambios()) {
                    System.out.println(" - [" + cambioData.getTipo() + "] " + cambioData.getDetalle());
                }
            }

            System.out.println("");
        }
    }
}
