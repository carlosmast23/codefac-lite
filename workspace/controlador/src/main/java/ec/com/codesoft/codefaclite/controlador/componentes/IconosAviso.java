/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.Icon;

/**
 * Iconos vectoriales de severidad (informacion/advertencia/error/exito) para dialogos de aviso,
 * dibujados con Graphics2D en vez de glifos Unicode -mismo motivo que IconosVentaRapida: un glifo
 * como "i" o un caracter de alerta puede salir como un cuadro/"tofu" segun la fuente instalada en
 * el equipo-. Cada icono es un circulo relleno del color de severidad con un simbolo blanco adentro,
 * para que el significado quede claro por el simbolo ademas de por el color (alguien con dificultad
 * para distinguir colores todavia puede diferenciar "i" de "!" de una "X" de un check).
 *
 * @author Carlos
 */
public final class IconosAviso {

    private IconosAviso() {
    }

    public static Icon informacion(Color colorFondo, int tamano) {
        return new IconoInformacion(colorFondo, tamano);
    }

    public static Icon advertencia(Color colorFondo, int tamano) {
        return new IconoAdvertencia(colorFondo, tamano);
    }

    public static Icon error(Color colorFondo, int tamano) {
        return new IconoError(colorFondo, tamano);
    }

    public static Icon exito(Color colorFondo, int tamano) {
        return new IconoExito(colorFondo, tamano);
    }

    private static abstract class IconoBadgeAbstract implements Icon {

        protected final Color colorFondo;
        protected final int tamano;

        IconoBadgeAbstract(Color colorFondo, int tamano) {
            this.colorFondo = colorFondo;
            this.tamano = tamano;
        }

        protected Graphics2D prepararCirculoRelleno(Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(colorFondo);
            g2.fill(new Ellipse2D.Double(x, y, tamano, tamano));
            g2.setColor(Color.WHITE);
            return g2;
        }

        @Override
        public int getIconWidth() {
            return tamano;
        }

        @Override
        public int getIconHeight() {
            return tamano;
        }
    }

    private static class IconoInformacion extends IconoBadgeAbstract {

        IconoInformacion(Color colorFondo, int tamano) {
            super(colorFondo, tamano);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepararCirculoRelleno(g, x, y);
            g2.setStroke(new BasicStroke(Math.max(1.8f, tamano / 11f)));
            double cx = x + tamano / 2.0;
            double puntoY = y + tamano * 0.28;
            double radioPunto = Math.max(1.2, tamano * 0.045);
            g2.fill(new Ellipse2D.Double(cx - radioPunto, puntoY - radioPunto, radioPunto * 2, radioPunto * 2));
            g2.draw(new Line2D.Double(cx, y + tamano * 0.44, cx, y + tamano * 0.74));
            g2.dispose();
        }
    }

    private static class IconoAdvertencia extends IconoBadgeAbstract {

        IconoAdvertencia(Color colorFondo, int tamano) {
            super(colorFondo, tamano);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepararCirculoRelleno(g, x, y);
            g2.setStroke(new BasicStroke(Math.max(1.8f, tamano / 11f)));
            double cx = x + tamano / 2.0;
            g2.draw(new Line2D.Double(cx, y + tamano * 0.26, cx, y + tamano * 0.58));
            double radioPunto = Math.max(1.2, tamano * 0.045);
            double puntoY = y + tamano * 0.74;
            g2.fill(new Ellipse2D.Double(cx - radioPunto, puntoY - radioPunto, radioPunto * 2, radioPunto * 2));
            g2.dispose();
        }
    }

    private static class IconoError extends IconoBadgeAbstract {

        IconoError(Color colorFondo, int tamano) {
            super(colorFondo, tamano);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepararCirculoRelleno(g, x, y);
            g2.setStroke(new BasicStroke(Math.max(1.8f, tamano / 11f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double margen = tamano * 0.3;
            g2.draw(new Line2D.Double(x + margen, y + margen, x + tamano - margen, y + tamano - margen));
            g2.draw(new Line2D.Double(x + tamano - margen, y + margen, x + margen, y + tamano - margen));
            g2.dispose();
        }
    }

    private static class IconoExito extends IconoBadgeAbstract {

        IconoExito(Color colorFondo, int tamano) {
            super(colorFondo, tamano);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepararCirculoRelleno(g, x, y);
            g2.setStroke(new BasicStroke(Math.max(1.8f, tamano / 11f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double[] xs = {x + tamano * 0.27, x + tamano * 0.43, x + tamano * 0.74};
            double[] ys = {y + tamano * 0.52, y + tamano * 0.68, y + tamano * 0.32};
            g2.draw(new Line2D.Double(xs[0], ys[0], xs[1], ys[1]));
            g2.draw(new Line2D.Double(xs[1], ys[1], xs[2], ys[2]));
            g2.dispose();
        }
    }
}
