/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * JPanel con FlowLayout pensado para ir dentro de un JScrollPane como grid de tiles
 * (ej. ProductoTileCard en Venta Rapida), o directamente en un BorderLayout.NORTH como
 * barra de categorias que necesite pasar a una segunda fila. GridLayout estira cada celda
 * para llenar toda la altura disponible del contenedor cuando hay pocos elementos (se ve
 * el tile empujado/estirado); WrapPanel en cambio mantiene el tamaño preferido de cada
 * componente y solo hace wrap de filas segun el ancho real disponible.
 *
 * IMPORTANTE: java.awt.FlowLayout por si solo NO calcula bien su alto preferido cuando
 * hace falta mas de una fila (siempre asume una sola fila para ese calculo, aunque al
 * dibujar si reparta los componentes en varias filas). Eso hace que un JScrollPane crea
 * que el contenido es mas bajo de lo que realmente es, y recorte silenciosamente todo lo
 * que cae en la fila "fantasma" que nunca contó. Por eso esta clase sobreescribe
 * getPreferredSize() simulando el wrap con el ancho real, en vez de confiar en
 * FlowLayout.preferredLayoutSize().
 *
 * @author Carlos
 */
public class WrapPanel extends JPanel implements Scrollable {

    public WrapPanel() {
        super(new FlowLayout(FlowLayout.LEFT, 8, 8));
    }

    @Override
    public Dimension getPreferredSize() {
        int targetWidth = getWidth();
        if (targetWidth <= 0) {
            Container parent = getParent();
            targetWidth = (parent != null) ? parent.getWidth() : 0;
        }
        if (targetWidth <= 0) {
            return super.getPreferredSize();
        }

        FlowLayout layout = (FlowLayout) getLayout();
        int hgap = layout.getHgap();
        int vgap = layout.getVgap();
        Insets insets = getInsets();
        int maxRowWidth = Math.max(1, targetWidth - insets.left - insets.right);

        int x = 0;
        int rowHeight = 0;
        int totalHeight = insets.top + insets.bottom;
        boolean primeroEnFila = true;

        for (Component componente : getComponents()) {
            if (!componente.isVisible()) {
                continue;
            }
            Dimension tamanio = componente.getPreferredSize();
            if (!primeroEnFila && x + hgap + tamanio.width > maxRowWidth) {
                totalHeight += rowHeight + vgap;
                x = 0;
                rowHeight = 0;
                primeroEnFila = true;
            }
            if (!primeroEnFila) {
                x += hgap;
            }
            x += tamanio.width;
            rowHeight = Math.max(rowHeight, tamanio.height);
            primeroEnFila = false;
        }
        totalHeight += rowHeight;

        return new Dimension(targetWidth, totalHeight);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 20;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 100;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
