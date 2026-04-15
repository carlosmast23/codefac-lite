package ec.com.codesoft.codefaclite.main.model.changelog;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el archivo completo de la bitacora de versiones.
 */
public class ChangelogData {

    private String versionActual;
    private List<ChangelogVersionData> historial;

    public ChangelogData() {
        historial = new ArrayList<ChangelogVersionData>();
    }

    public String getVersionActual() {
        return versionActual;
    }

    public void setVersionActual(String versionActual) {
        this.versionActual = versionActual;
    }

    public List<ChangelogVersionData> getHistorial() {
        return historial;
    }

    public void setHistorial(List<ChangelogVersionData> historial) {
        this.historial = historial;
    }
}
