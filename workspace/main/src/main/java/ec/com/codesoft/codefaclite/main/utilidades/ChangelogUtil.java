package ec.com.codesoft.codefaclite.main.utilidades;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogVersionData;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Type;

/**
 * Utilidad para leer la bitacora de cambios del sistema desde recursos.
 */
public class ChangelogUtil {

    private static final Logger LOG = Logger.getLogger(ChangelogUtil.class.getName());
    private static final String CHANGELOG_PATH = "/versiones/changelog.json";

    private ChangelogUtil() {
    }

    public static List<ChangelogVersionData> leerChangelog() {
        InputStream inputStream = ChangelogUtil.class.getResourceAsStream(CHANGELOG_PATH);

        if (inputStream == null) {
            LOG.log(Level.WARNING, "No se encontro el archivo del changelog: {0}", CHANGELOG_PATH);
            return new ArrayList<ChangelogVersionData>();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<ChangelogVersionData>>() {}.getType();
            List<ChangelogVersionData> historial = gson.fromJson(reader, tipoLista);
            return (historial != null) ? historial : new ArrayList<ChangelogVersionData>();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error leyendo el archivo de changelog", ex);
            return new ArrayList<ChangelogVersionData>();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error procesando el archivo de changelog", ex);
            return new ArrayList<ChangelogVersionData>();
        }
    }

    public static String obtenerVersionActual() {
        ChangelogVersionData ultimaVersion = obtenerUltimaVersion();
        return (ultimaVersion != null) ? ultimaVersion.getVersion() : null;
    }

    public static List<ChangelogVersionData> obtenerHistorial() {
        List<ChangelogVersionData> historial = leerChangelog();
        return (historial != null) ? historial : new ArrayList<ChangelogVersionData>();
    }

    public static ChangelogVersionData obtenerUltimaVersion() {
        List<ChangelogVersionData> historial = obtenerHistorial();
        if (historial.isEmpty()) {
            return null;
        }
        return historial.get(0);
    }
}
