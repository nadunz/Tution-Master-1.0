
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Nadun
 */
public class DragJFrame{

    private Point pressedPoint;
    private Component source;
    private JPanel draggedComponent;
    
    
    
    public DragJFrame(JFrame parent, JPanel draggedPanel) {
        this.source=parent;    
        this.draggedComponent=draggedPanel;
        getPressedPoint();
        moveSourceWhenDragging();
        
    }
    
    
    private void getPressedPoint(){
    
        draggedComponent.addMouseListener(new MouseListener() {


            @Override
            public void mousePressed(MouseEvent e) {
               pressedPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
        });
}
    
    private void moveSourceWhenDragging(){
        draggedComponent.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                
                Point p=e.getLocationOnScreen();
                source.setLocation(p.x-pressedPoint.x,p.y-pressedPoint.y);
                
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
        
    }

  
    
}
