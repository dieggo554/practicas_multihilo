/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Naves;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Meteorito {

    private String nombre;
    private boolean perforado;
    private boolean dinamitado;

    public Meteorito(String nombre) {
        this.nombre = nombre;
        perforado = false;
        dinamitado = false;
    }

    public synchronized void perforar(Nave nave) {
        if (perforado == false) {
            perforado = true;
            System.out.println("La nave " + nave.getName() + " ha perforado el meteorito " + nombre + " y espera");
            try {

                wait();

            } catch (InterruptedException ex) {
                Logger.getLogger(Meteorito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void dinamitar(Nave nave) {
        dinamitado = true;
        System.out.println("La nave " + nave.getName() + " ha dinamitado el meteorito " + nombre + " y continua");
        notify();
    }

    public boolean isPerforado() {
        return perforado;
    }

    public boolean isDinamitado() {
        return dinamitado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
