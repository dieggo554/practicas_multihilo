/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bolas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public abstract class Bola extends Thread {

    int posX;
    int posY;
    int retardo;
    int tamanho = 20; //aleatorio entre 0 y tamaño tablero - tamaño bola
    boolean corriendo;
    Color color;
    Tablero tablero = Tablero.getInstance();

    public Bola(int velocidad) {
        posY = 0;
        posX = new Random().nextInt() * (tablero.getWidth() - 20);
        corriendo = true;
        velocidad = retardo;
    }

    public void run() {
        do {
            try {
                mover();
    //            tablero.repaint(posX, posY, tamanho +5, tamanho + 5);
    //            colorear();
                tablero.repaint();
                Thread.sleep(retardo);
                if (!vive()){
                    corriendo = false;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Bola.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (corriendo = true);
    }

    abstract void mover();
    
    abstract boolean vive();
    
//    abstract void colorear();
    
    public void fin(){
        corriendo = false;
    }

    void pintate(Graphics g) {
        g.fillOval(posX, posY, tamanho, tamanho);
    }
}
