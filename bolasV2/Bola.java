/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bolasV2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Bola extends Thread {

    private int posX = 1;
    private int posY = 1; //aleatorio entre 0 y tamaño tablero - tamaño bola
    private int velocidad = 50;
    private int tamanho = 50;
    private int rebotesX = 0;
    private int rebotesY = 0;
    private Color color;
    private Random aleatorio = new Random();
    private Tablero tablero = Tablero.getInstance();

    public Bola() {
        posX = 0;
        posY = (new Random().nextInt(tablero.getHeight() - tamanho));
    }

    public Bola(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        
    }

    @Override
    public void run() {
        do {
            mover();
//            colorear();
            tablero.repaint(posX, posY, tamanho+2, tamanho+2);
        } while (posX <= tablero.getWidth()+1 && posY <= tablero.getHeight()+1 && (rebotesX + rebotesY) < 10);
    }

    void mover() {
        if ((rebotesX % 2) == 0){
            posX++;
        } else {
            posX--;
        }
        if (posX == (tablero.getWidth() - tamanho) || posX == 0){
            rebotesX += 1;
            colorear();
        }
        
        if ((rebotesY % 2) == 0){
            posY++;
        } else {
            posY--;
        }
        if (posY == (tablero.getHeight()- tamanho) || posY == 0){
            rebotesY += 1;
            colorear();
        }
        
        try {
            Thread.sleep(velocidad);
        } catch (InterruptedException ex) {
            Logger.getLogger(Bola.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void pintate(Graphics g) {
        g.setColor(color);
        g.fillOval(posX, posY, tamanho, tamanho);
    }

    private void colorear() {
        color = new Color(aleatorio.nextInt(255), aleatorio.nextInt(255), aleatorio.nextInt(255));
    }
}
