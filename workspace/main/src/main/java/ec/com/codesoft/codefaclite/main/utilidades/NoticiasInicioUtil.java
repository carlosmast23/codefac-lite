package ec.com.codesoft.codefaclite.main.utilidades;

import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.main.model.noticias.NoticiaData;
import ec.com.codesoft.codefaclite.main.panel.NoticiaDialog;
import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidad para avisar al usuario, una sola vez por noticia, de las novedades publicadas desde
 * el panel de administracion del proyecto web (codefac_pagina). Mismo patron que ChangelogInicioUtil,
 * pero el origen de datos se descarga en cada arranque (ver NoticiasUtil) en vez de venir empaquetado
 * en el .jar, y el marcador de "ya visto" es el id mas alto de noticia, no una version.
 */
public class NoticiasInicioUtil {

    private static final Logger LOG = Logger.getLogger(NoticiasInicioUtil.class.getName());

    private NoticiasInicioUtil() {
    }

    public static void mostrarSiHayNoticiasNuevas(Frame parent) {
        try {
            List<NoticiaData> noticias = NoticiasUtil.filtrarVigentes(NoticiasUtil.descargarNoticias());
            if (noticias.isEmpty()) {
                return;
            }

            ArchivoConfiguracionesCodefac configuracion = ArchivoConfiguracionesCodefac.getInstance();
            Long ultimaIdMostrada = parsearId(configuracion.obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_ULTIMA_NOTICIA_MOSTRADA));

            List<NoticiaData> nuevas = new ArrayList<NoticiaData>();
            Long idMaxima = ultimaIdMostrada;
            for (NoticiaData noticia : noticias) {
                if (noticia.getId() == null) {
                    continue;
                }
                if (idMaxima == null || noticia.getId() > idMaxima) {
                    idMaxima = noticia.getId();
                }
                if (ultimaIdMostrada == null || noticia.getId() > ultimaIdMostrada) {
                    nuevas.add(noticia);
                }
            }

            if (!nuevas.isEmpty()) {
                Collections.sort(nuevas, new Comparator<NoticiaData>() {
                    @Override
                    public int compare(NoticiaData a, NoticiaData b) {
                        return a.getId().compareTo(b.getId());
                    }
                });

                NoticiaDialog dialog = new NoticiaDialog(parent, nuevas);
                dialog.setVisible(true);
            }

            if (idMaxima != null && !idMaxima.equals(ultimaIdMostrada)) {
                configuracion.agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_ULTIMA_NOTICIA_MOSTRADA, idMaxima.toString());
                try {
                    configuracion.guardar();
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, "No se pudo guardar el id de la ultima noticia mostrada", ex);
                }
            }
        } catch (Exception ex) {
            //Nunca debe interrumpir el arranque por un problema mostrando noticias
            LOG.log(Level.WARNING, "No se pudo verificar si hay noticias nuevas", ex);
        }
    }

    private static Long parsearId(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
