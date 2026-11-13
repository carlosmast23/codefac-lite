package ec.com.codesoft.codefaclite.main.utilidades;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ec.com.codesoft.codefaclite.main.model.noticias.NoticiaData;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Descarga y filtra las noticias/notificaciones publicadas desde el panel de administracion
 * (proyecto web codefac_pagina), para avisar novedades a los clientes al abrir el sistema.
 * Se guardan en el mismo repositorio que ya se usa para verificar actualizaciones de version
 * (ver ActualizarVersionCodefac), solo que en un archivo propio porque es una lista que crece,
 * no un conjunto fijo de claves.
 */
public class NoticiasUtil {

    private static final Logger LOG = Logger.getLogger(NoticiasUtil.class.getName());
    private static final String NOMBRE_ARCHIVO_NOTICIAS = "noticias.json";
    private static final String CARPETA_DESCARGA = "tmp";

    private NoticiasUtil() {
    }

    /**
     * Descarga noticias.json desde el repositorio de actualizaciones. Nunca lanza excepcion:
     * si falla la descarga o el parseo, devuelve una lista vacia (no debe romper el arranque).
     */
    public static List<NoticiaData> descargarNoticias() {
        try {
            String path = ParametrosSistemaCodefac.REPOSITORIO_ACTUALIZACION_ESTABLE;

            if (!UtilidadesWeb.descargarArchivo(NOMBRE_ARCHIVO_NOTICIAS, path + NOMBRE_ARCHIVO_NOTICIAS, CARPETA_DESCARGA)) {
                return new ArrayList<NoticiaData>();
            }

            try (FileReader reader = new FileReader(CARPETA_DESCARGA + "/" + NOMBRE_ARCHIVO_NOTICIAS)) {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<NoticiaData>>() {}.getType();
                List<NoticiaData> noticias = gson.fromJson(reader, tipoLista);
                return (noticias != null) ? noticias : new ArrayList<NoticiaData>();
            }
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "No se pudo descargar o leer noticias.json", ex);
            return new ArrayList<NoticiaData>();
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "Error procesando noticias.json", ex);
            return new ArrayList<NoticiaData>();
        }
    }

    /**
     * Descarta las noticias cuya fecha de vigencia (validoHasta, yyyy-MM-dd) ya paso.
     * Una noticia sin validoHasta nunca caduca. Una fecha con formato invalido se trata
     * como "sin vigencia" para no ocultar por error una noticia valida.
     */
    public static List<NoticiaData> filtrarVigentes(List<NoticiaData> noticias) {
        List<NoticiaData> vigentes = new ArrayList<NoticiaData>();
        if (noticias == null) {
            return vigentes;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);
        Date hoy = new Date();

        for (NoticiaData noticia : noticias) {
            String validoHasta = noticia.getValidoHasta();
            if (validoHasta == null || validoHasta.trim().isEmpty()) {
                vigentes.add(noticia);
                continue;
            }

            try {
                Date fechaLimite = formato.parse(validoHasta.trim());
                if (!fechaLimite.before(truncarAFechaSolamente(hoy))) {
                    vigentes.add(noticia);
                }
            } catch (java.text.ParseException ex) {
                LOG.log(Level.WARNING, "Fecha validoHasta invalida en noticia id={0}: {1}", new Object[]{noticia.getId(), validoHasta});
                vigentes.add(noticia);
            }
        }

        return vigentes;
    }

    private static Date truncarAFechaSolamente(Date fecha) {
        java.util.Calendar calendario = java.util.Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendario.set(java.util.Calendar.MINUTE, 0);
        calendario.set(java.util.Calendar.SECOND, 0);
        calendario.set(java.util.Calendar.MILLISECOND, 0);
        return calendario.getTime();
    }
}
