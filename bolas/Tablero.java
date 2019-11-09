package bolas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Tablero extends JPanel {

    ArrayList<Bola> listaBolas = new ArrayList<Bola>();
    Bola bola;
    boolean modoDebug = true;
    private static Tablero INSTANCE = new Tablero();

    private Tablero() {
        this.setPreferredSize(new Dimension(700, 400));
        this.setBackground(Color.WHITE);
    }

    public static Tablero getInstance() {
        return INSTANCE;
    }

    public void nuevaBola() {
        bola = new BolaDisparo(10);
        listaBolas.add(bola);
        bola.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = listaBolas.size() - 1; i >= 0; i--) {
            if (listaBolas.get(i).vive()) {
                listaBolas.get(i).pintate(g);
            } else {
                listaBolas.get(i).fin();
            }
            if (!listaBolas.get(i).isAlive()) {
                listaBolas.remove(i);
            }
        }
        if (modoDebug == true) {
            g.drawString(String.valueOf(listaBolas.size()), 10, 10);
        }
    }
}