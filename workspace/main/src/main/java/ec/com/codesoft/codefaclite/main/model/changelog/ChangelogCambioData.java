package ec.com.codesoft.codefaclite.main.model.changelog;

/**
 * Representa un cambio individual dentro de una version del sistema.
 */
public class ChangelogCambioData {

    private String tipo;
    private String detalle;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
