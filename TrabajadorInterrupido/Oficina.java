/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrabajadorInterrupido;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Oficina {

    private static Trabajador trabajador;
    private static Molestia molestia;

    public static void main(String[] args) {
        trabajador = new Trabajador();
        molestia = new Molestia(trabajador);

        System.out.println("Inicio del main");
        trabajador.start();
        molestia.start();
        System.out.println("Fin del main");
    }
}

class Molestia extends Thread {

    Trabajador trabajador;

    Molestia(Trabajador trabajador) {
        //controlar no interrumpir si ya esta interrumpido
        this.trabajador = trabajador;
    }

    @Override
    public void run() {
        int nInterrupciones = 0;
        System.out.println("Inicio interrupciones");
        while (trabajador.isAlive()) {
            if (!trabajador.isInterrupted()) {
                try { 
                    System.out.println("Interrupcion (" + ++nInterrupciones + "º vez)");
                    trabajador.interrupt();
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Molestia.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Fin interrupciones");
    }
}

class Trabajador extends Thread {

    int paciencia = 5;

    @Override
    public void run() {
        int nTrabajo = 0;
        System.out.println("Inicio trabajo");
        do {
            System.out.println("Trabajo " + ++nTrabajo + " º:");
            if (Thread.interrupted()) {
                paciencia--;
                System.out.println("Trabajo " + nTrabajo + " º interrumpido al inicio");
            } else {
                System.out.println("Trabajo " + nTrabajo + "º a tope");
                try {
                    Thread.sleep(250);
                    System.out.println("Trabajo " + nTrabajo + "º terminado");
                } catch (InterruptedException ex) {
                    paciencia--;
                    System.out.println("Trabajo " + nTrabajo + " º interrupido durante el trabajo");
                }
            }

        } while (paciencia > 0);
        System.out.println("Paciencia agotada");
    }
}