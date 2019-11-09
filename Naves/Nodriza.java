/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Naves;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dpinepadi
 */
public class Nodriza {

    static final int nMeteoritos = 20;
    static final int nPerforadoras = 5;
    static final int nDinamiteras = 10;
    static int meteoritosAvistados = 0;
    private static ArrayList<Meteorito> listaMeteoritos = new ArrayList<>();

    public static void main(String[] args) {

        for (int i = 0; i < nMeteoritos; i++) {
            listaMeteoritos.add(new Meteorito("M" + i));
        }

        for (int i = 0; i < nPerforadoras; i++) {
            new Perforadora("P" + i).start();
        }

        for (int i = 0; i < nDinamiteras; i++) {
            new Dinamitera("D" + i, listaMeteoritos).start();
        }
    }

    public static Meteorito getMeteorito() {
        Meteorito meteorito = null;

        System.out.println();
        if (meteoritosAvistados < listaMeteoritos.size()) {
            
            meteorito = listaMeteoritos.get((int) (Math.random() * listaMeteoritos.size() - 1));

            while (meteorito.isPerforado()) {
                meteorito = listaMeteoritos.get((int) (Math.random() * listaMeteoritos.size()));
            }
            
            meteoritosAvistados++;
            System.out.println("Avistado el meteorito " + meteorito.toString() + " " + meteoritosAvistados + "/" + listaMeteoritos.size());
        }

        return meteorito;
    }
}
