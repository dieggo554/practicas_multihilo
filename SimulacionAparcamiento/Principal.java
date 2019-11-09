/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SimulacionAparcamiento;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Principal {

    private static final int N_PLAZAS = 4;
    private static final int N_COCHES = 9;
    private static final int TIEMPO_MAXIMO = 500;
    private static final int TIEMPO_MINIMO = 100;

    public static void main(String[] args) {
        ArrayList<Plaza> plazas = new ArrayList<>();
        ArrayList<Conductor> conductores = new ArrayList<>();

        for (int i = 0; i < N_PLAZAS; i++) {
            plazas.add(new Plaza("P" + i));
        }

        Aparcamiento aparcamiento = Aparcamiento.getInstance(plazas);

        for (int i = 0; i < N_COCHES; i++) {
            conductores.add(new Conductor("C" + i, TIEMPO_MAXIMO, TIEMPO_MINIMO));
            conductores.get(i).start();
        }
    }
}

class Aparcamiento {

    private static Aparcamiento instance = null;
    ArrayList<Plaza> plazas;

    public static Aparcamiento getInstance(ArrayList<Plaza> plazas) {
        if (instance == null) {
            instance = new Aparcamiento(plazas);
        }
        return instance;
    }

    private Aparcamiento(ArrayList<Plaza> plazas) {
        this.plazas = plazas;
    }

    public Plaza plazaLibre() {
        for (Plaza plaza : plazas) {
            if (plaza.estaLibre()) {
                return plaza;
            }
        }
        return null;
    }

    public synchronized boolean aparcar(Conductor conductor) {
        Plaza plaza;
        if ((plaza = plazaLibre()) == null) {

            try {
                
                wait();
                plaza = plazaLibre();
            } catch (InterruptedException ex) {
                Logger.getLogger(Aparcamiento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        plaza.meterCoche(conductor);
        imprimirPlazas();
        return true;
    }

    public Plaza buscarCoche(Conductor conductor) {
        for (Plaza plaza : plazas) {
            if (plaza.verCoche() != null && plaza.verCoche().getName().equals(conductor.getName())) {
                return plaza;
            }
        }
        return null;
    }

    public synchronized boolean dejarPlaza(Conductor conductor) {
        Plaza plaza = buscarCoche(conductor);
        plaza.sacarCoche();
        notify();
        imprimirPlazas();
        return true;
    }

    public void imprimirPlazas() {
        for (Plaza plaza : plazas) {
            System.out.print(plaza.toString() + " " + (plaza.verCoche() != null ? plaza.verCoche().toString() : "XX") + " " + " | ");
        }
        System.out.println("");
    }
}

class Conductor extends Thread {

    Aparcamiento aparcamiento = Aparcamiento.getInstance(null);
    int retardo_MAX, retardo_MIN;

    public Conductor(String nombre, int retardo_MAX, int retardo_MIN) {
        super.setName(nombre);
        this.retardo_MAX = retardo_MAX;
        this.retardo_MIN = retardo_MIN;
    }

    @Override
    public void run() {

        aparcamiento.aparcar(this);

        try {
            Thread.sleep((int) (Math.random() * retardo_MAX) + retardo_MIN);
        } catch (InterruptedException ex) {
            Logger.getLogger(Conductor.class.getName()).log(Level.SEVERE, null, ex);
        }

        aparcamiento.dejarPlaza(this);

    }

    @Override
    public String toString() {
        return this.getName();
    }
}

class Plaza {

    private String nombre;
    private Conductor conductor = null;

    public Plaza(String nombre) {
        this.nombre = nombre;
    }

    public boolean estaLibre() {
        return conductor == null;
    }

    public void meterCoche(Conductor conductor) {
        this.conductor = conductor;
    }

    public Conductor verCoche() {
        return conductor;
    }

    public void sacarCoche() {
        conductor = null;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
