package ec.com.codesoft.codefaclite.main.model.changelog;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una version completa dentro de la bitacora del sistema.
 */
public class ChangelogVersionData {

    private String version;
    private String fecha;
    private String titulo;
    private List<ChangelogCambioData> cambios;

    public ChangelogVersionData() {
        cambios = new ArrayList<ChangelogCambioData>();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<ChangelogCambioData> getCambios() {
        return cambios;
    }

    public void setCambios(List<ChangelogCambioData> cambios) {
        this.cambios = cambios;
    }
}
