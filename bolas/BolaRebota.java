/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bolas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class BolaRebota extends Bola {

    int rebotesX = 0;
    int rebotesY = 0;
    int maxRebotes = 10;

    public BolaRebota(int velocidad) {
        super(velocidad);
        color = Color.RED;
    }

    @Override
    void mover() {
        if ((rebotesX % 2) == 0) {
            posX++;
        } else {
            posX--;
        }
        if (posX == (tablero.getWidth() - tamanho) || posX == 0) {
            rebotesX += 1;
        }

        if ((rebotesY % 2) == 0) {
            posY++;
        } else {
            posY--;
        }
        if (posY == (tablero.getHeight() - tamanho) || posY == 0) {
            rebotesY += 1;
        }
    }
    
    @Override
    boolean vive(){
        return (rebotesX + rebotesY <= maxRebotes);
    }
    
    void pintate(Graphics g) {
        g.setColor(color);
        g.fillOval(posX, posY, tamanho, tamanho);
        g.drawString(String.valueOf(maxRebotes -(rebotesX + rebotesY)), posX + 5, posX + 5);
    }
}
