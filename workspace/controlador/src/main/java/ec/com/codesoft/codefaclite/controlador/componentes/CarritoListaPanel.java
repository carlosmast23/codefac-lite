/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Lista vertical de filas (ej. CarritoLineaPanel) para ir dentro de un JScrollPane, como el
 * detalle del carrito de Venta Rapida. A diferencia de WrapPanel (que hace wrap horizontal tipo
 * grid), aqui cada fila ocupa el ancho completo y se apilan verticalmente; BoxLayout.Y_AXIS ya
 * calcula bien su alto preferido como la suma de sus filas (no tiene el problema de FlowLayout
 * que motivo WrapPanel), asi que no hace falta sobreescribir getPreferredSize().
 *
 * @author Carlos
 */
public class CarritoListaPanel extends JPanel implements Scrollable {

    public CarritoListaPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 30;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 150;
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
