
package com.jsbtech.gestao_vendas.modelo.util;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public abstract class AbstractMouseListener implements MouseInputListener{

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) {}
    
}
