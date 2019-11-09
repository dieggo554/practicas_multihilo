/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bolas;

import java.awt.Graphics;

/**
 *
 * @author dpinepadi
 */
public class BolaDisparo extends Bola {

    public BolaDisparo(int velocidad) {
        super(velocidad);
    }
    
    @Override
    void mover() {
        posX++;
    }

    @Override
    boolean vive() {
        return (posX < tablero.getWidth());
    }
}
