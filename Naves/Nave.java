/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Naves;

import java.util.ArrayList;

/**
 *
 * @author dpinepadi
 */
public class Nave extends Thread {

    protected Meteorito meteorito;

    public Nave(String name) {
        super(name);
    }

    public Meteorito getMeteorito() {
        return meteorito;
    }

    public void setMeteorito(Meteorito meteorito) {
        this.meteorito = meteorito;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}

class Perforadora extends Nave {

    public Perforadora(String name) {
        super(name);
    }

    @Override
    public void run() {
        while ((meteorito = Nodriza.getMeteorito()) != null) {
            meteorito.perforar(this);
        }
        System.out.println("Perforadora " + this.getName() + " termina su misión");
    }
}

class Dinamitera extends Nave {

    ArrayList<Meteorito> meteoritos;

    //esta nave recibira el array y lo recorrerá por su cuenta
    public Dinamitera(String name, ArrayList meteoritos) {
        super(name);
        this.meteoritos = meteoritos;
    }

    @Override
    public void run() {
        int meteoritosDinamitados = 0;
        while (meteoritosDinamitados < meteoritos.size()) {
            meteorito = meteoritos.get((int) (Math.random() * meteoritos.size() - 1));
            if (meteorito.isDinamitado()) {
                meteoritosDinamitados++;
            } else if (meteorito.isPerforado()) {
                meteorito.dinamitar(this);
            }
        }
        System.out.println("Dinamitera " + this.getName() + " termina su misión");
    }
}
