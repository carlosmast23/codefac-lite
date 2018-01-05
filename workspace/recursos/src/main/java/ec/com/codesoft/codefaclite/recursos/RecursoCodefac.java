/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.recursos;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Carlos
 */
public enum RecursoCodefac {
    IMAGENES_ICONOS("img/iconos"),
    IMAGENES_ICONOS_DIALOGOS("img/iconos/dialogos"),
    IMAGENES_GENERAL("img/general"),
    IMAGENES_ACCESO_DIRECTO("img/accesoDirecto"),
    IMAGENES_REDES_SOCIALES("img/redes_sociales"),
    SQL("sql"),
    JASPER_CRM("reportes/crm"),
    JASPER_FACTURACION("reportes/facturacion"),
    JASPER_COMPROBANTES_ELECTRONICOS("reportes/comprobantes_electronicos"),
    JASPER("reportes"),
    HTML("html/factura_electronica"),
    AYUDA("ayuda");

    private String subPathResource;

    private RecursoCodefac(String subPathResource) {
        this.subPathResource = subPathResource;
    }

    public URL getURL() {
        return getClass().getResource(subPathResource);
    }

    public String getPath() {
        return getClass().getResource(subPathResource).getPath();
    }

    public URL getResourceURL(String file) {
        return getClass().getResource("/" + subPathResource + "/" + file);
    }

    public String getResourcePath(String file) {
        return getClass().getResource("/" + subPathResource + "/" + file).getPath();
    }

    public String getResourcesParentPath(String file) {
        String path = getResourcePath(file);
        File archivo = new File(path);
        //File file = new File(getClass().getResource("/pagina/ayudaHtml.html").toURI());

        return archivo.getParentFile().toURI().getPath();
    }

    public InputStream getResourceInputStream(String file) {
        return getClass().getResourceAsStream("/" + subPathResource + "/" + file);
    }

}
