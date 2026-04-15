package ec.com.codesoft.codefaclite.main.utilidades;

import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogVersionData;
import ec.com.codesoft.codefaclite.main.panel.ChangelogDialog;
import java.awt.Frame;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidad para mostrar la bitacora de cambios al iniciar el sistema.
 */
public class ChangelogInicioUtil {

    private static final Logger LOG = Logger.getLogger(ChangelogInicioUtil.class.getName());

    private ChangelogInicioUtil() {
    }

    public static void mostrarHistorial(Frame parent) {
        List<ChangelogVersionData> historial = ChangelogUtil.obtenerHistorial();
        ChangelogVersionData ultimaVersion = historial.isEmpty() ? null : historial.get(0);

        ChangelogDialog changelogDialog = new ChangelogDialog(parent, historial, ultimaVersion);
        changelogDialog.setVisible(true);
    }

    public static void mostrarSiExisteNuevaVersion(Frame parent) {
        List<ChangelogVersionData> historial = ChangelogUtil.obtenerHistorial();
        ChangelogVersionData ultimaVersion = historial.isEmpty() ? null : historial.get(0);
        if (ultimaVersion == null || ultimaVersion.getVersion() == null || ultimaVersion.getVersion().trim().isEmpty()) {
            return;
        }

        ArchivoConfiguracionesCodefac configuracion = ArchivoConfiguracionesCodefac.getInstance();
        String versionMostrada = configuracion.obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_ULTIMA_VERSION_CHANGELOG_MOSTRADA);

        if (ultimaVersion.getVersion().equals(versionMostrada)) {
            return;
        }

        ChangelogDialog changelogDialog = new ChangelogDialog(parent, historial, ultimaVersion);
        changelogDialog.setVisible(true);

        configuracion.agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_ULTIMA_VERSION_CHANGELOG_MOSTRADA, ultimaVersion.getVersion());
        try {
            configuracion.guardar();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "No se pudo guardar la ultima version de changelog mostrada", ex);
        }
    }
}
