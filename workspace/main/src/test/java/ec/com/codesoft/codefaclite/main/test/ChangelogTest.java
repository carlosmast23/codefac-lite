package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogCambioData;
import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogData;
import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogVersionData;
import ec.com.codesoft.codefaclite.main.utilidades.ChangelogUtil;

/**
 * Clase simple para validar la lectura del archivo changelog.json.
 */
public class ChangelogTest {

    public static void main(String[] args) {
        ChangelogData changelogData = ChangelogUtil.leerChangelog();

        System.out.println("=== CHANGELOG CODEFAC ===");
        System.out.println("Version actual: " + changelogData.getVersionActual());
        System.out.println("Total versiones: " + changelogData.getHistorial().size());
        System.out.println("");

        for (ChangelogVersionData versionData : changelogData.getHistorial()) {
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
