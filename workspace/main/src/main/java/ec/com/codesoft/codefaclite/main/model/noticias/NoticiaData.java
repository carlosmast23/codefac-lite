package ec.com.codesoft.codefaclite.main.model.noticias;

/**
 * Representa una noticia/notificacion publicada desde el panel de administracion
 * (proyecto web codefac_pagina) para avisar novedades a los clientes al abrir el sistema.
 */
public class NoticiaData {

    private Long id;
    private String titulo;
    private String mensaje;
    /**
     * "URGENTE" o "INFORMATIVO". Cualquier otro valor (o nulo) se trata como informativo.
     */
    private String tipo;
    private String fecha;
    /**
     * Fecha limite de vigencia en formato yyyy-MM-dd. Si viene nula o vacia, la noticia no caduca.
     */
    private String validoHasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValidoHasta() {
        return validoHasta;
    }

    public void setValidoHasta(String validoHasta) {
        this.validoHasta = validoHasta;
    }

    public boolean esUrgente() {
        return "URGENTE".equalsIgnoreCase(tipo);
    }
}
