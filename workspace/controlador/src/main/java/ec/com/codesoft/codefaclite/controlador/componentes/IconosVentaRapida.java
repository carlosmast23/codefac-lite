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
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;

/**
 * Iconos vectoriales simples (dibujados con Graphics2D, sin recursos de imagen externos) para
 * pantallas tipo Venta Rapida. Se usan en vez de glifos Unicode (lupa, papelera) porque esos
 * caracteres pueden salir como un cuadro/"tofu" segun la fuente instalada en el equipo.
 *
 * @author Carlos
 */
public final class IconosVentaRapida {

    private IconosVentaRapida() {
    }

    public static Icon lupa(Color color, int tamano) {
        return new IconoLupa(color, tamano);
    }

    public static Icon papelera(Color color, int tamano) {
        return new IconoPapelera(color, tamano);
    }

    public static Icon productoGenerico(Color color, int tamano) {
        return new IconoProductoGenerico(color, tamano);
    }

    public static Icon lapiz(Color color, int tamano) {
        return new IconoLapiz(color, tamano);
    }

    public static Icon equis(Color color, int tamano) {
        return new IconoEquis(color, tamano);
    }

    public static Icon flecha(Color color, int tamano, boolean derecha) {
        return new IconoFlecha(color, tamano, derecha);
    }

    private static class IconoLupa implements Icon {

        private final Color color;
        private final int tamano;

        IconoLupa(Color color, int tamano) {
            this.color = color;
            this.tamano = tamano;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f));
            int circuloDiametro = (int) (tamano * 0.62);
            g2.draw(new Ellipse2D.Double(x + 1, y + 1, circuloDiametro, circuloDiametro));
            int mangoX = x + 1 + circuloDiametro - 3;
            int mangoY = y + 1 + circuloDiametro - 3;
            g2.draw(new Line2D.Double(mangoX, mangoY, x + tamano - 1, y + tamano - 1));
            g2.dispose();
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

    private static class IconoPapelera implements Icon {

        private final Color color;
        private final int tamano;

        IconoPapelera(Color color, int tamano) {
            this.color = color;
            this.tamano = tamano;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));

            int tapaY = y + 2;
            g2.drawLine(x + 1, tapaY, x + tamano - 1, tapaY);

            int mangoAncho = Math.max(4, tamano / 3);
            g2.drawRoundRect(x + (tamano - mangoAncho) / 2, y, mangoAncho, 3, 2, 2);

            int cuerpoX = x + 3;
            int cuerpoY = tapaY + 2;
            int cuerpoAncho = tamano - 6;
            int cuerpoAlto = (y + tamano - 2) - cuerpoY;
            g2.draw(new RoundRectangle2D.Double(cuerpoX, cuerpoY, cuerpoAncho, cuerpoAlto, 3, 3));

            for (int i = 1; i <= 3; i++) {
                int lx = cuerpoX + (cuerpoAncho * i) / 4;
                g2.drawLine(lx, cuerpoY + 3, lx, cuerpoY + cuerpoAlto - 3);
            }
            g2.dispose();
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

    /**
     * Caja/paquete generico: placeholder neutro para productos sin foto propia ni de categoria,
     * en vez de dejar la tarjeta vacia o un texto de "sin imagen" que ensucia visualmente el grid.
     */
    private static class IconoProductoGenerico implements Icon {

        private final Color color;
        private final int tamano;

        IconoProductoGenerico(Color color, int tamano) {
            this.color = color;
            this.tamano = tamano;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f));

            int margen = Math.max(2, tamano / 8);
            int ladoX = x + margen;
            int ladoY = y + margen + (tamano - 2 * margen) / 6;
            int ancho = tamano - 2 * margen;
            int alto = tamano - 2 * margen - (tamano - 2 * margen) / 6;

            g2.draw(new RoundRectangle2D.Double(ladoX, ladoY, ancho, alto, 4, 4));
            g2.draw(new Line2D.Double(ladoX, ladoY + alto / 2.5, ladoX + ancho, ladoY + alto / 2.5));
            g2.draw(new Line2D.Double(ladoX + ancho / 2.0, ladoY + alto / 2.5, ladoX + ancho / 2.0, ladoY + alto));
            g2.dispose();
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

    private static class IconoLapiz implements Icon {

        private final Color color;
        private final int tamano;

        IconoLapiz(Color color, int tamano) {
            this.color = color;
            this.tamano = tamano;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.6f));

            double x1 = x + tamano * 0.15;
            double y1 = y + tamano * 0.85;
            double x2 = x + tamano * 0.75;
            double y2 = y + tamano * 0.25;
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
            g2.draw(new Line2D.Double(x2, y2, x2 + tamano * 0.1, y2 - tamano * 0.1));

            int[] puntaX = {(int) (x + tamano * 0.1), (int) (x + tamano * 0.28), (int) (x + tamano * 0.15)};
            int[] puntaY = {(int) (y + tamano * 0.9), (int) (y + tamano * 0.9), y + tamano};
            g2.fillPolygon(puntaX, puntaY, 3);
            g2.dispose();
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

    private static class IconoEquis implements Icon {

        private final Color color;
        private final int tamano;

        IconoEquis(Color color, int tamano) {
            this.color = color;
            this.tamano = tamano;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.8f));
            int margen = Math.max(2, tamano / 5);
            g2.draw(new Line2D.Double(x + margen, y + margen, x + tamano - margen, y + tamano - margen));
            g2.draw(new Line2D.Double(x + tamano - margen, y + margen, x + margen, y + tamano - margen));
            g2.dispose();
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

    private static class IconoFlecha implements Icon {

        private final Color color;
        private final int tamano;
        private final boolean derecha;

        IconoFlecha(Color color, int tamano, boolean derecha) {
            this.color = color;
            this.tamano = tamano;
            this.derecha = derecha;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            int margen = Math.max(2, tamano / 4);
            int[] xs;
            int[] ys = {y + margen, y + tamano / 2, y + tamano - margen};
            if (derecha) {
                xs = new int[]{x + margen, x + tamano - margen, x + margen};
            } else {
                xs = new int[]{x + tamano - margen, x + margen, x + tamano - margen};
            }
            g2.fillPolygon(xs, ys, 3);
            g2.dispose();
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
}
