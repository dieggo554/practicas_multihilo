/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WaitNotify;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Embotelladora {

    static Buffer cinta = new Buffer();
    final static int TAMAÑO_PALE = 50;
    
    public static void main(String[] args) {
        ArrayList<Botella> pale = new ArrayList<>();
        
        for (int i = 0; i < TAMAÑO_PALE; i++) {
            pale.add(new Botella(i));
            System.out.println(pale.get(i));
        }
        
        Encorchador encorchador = new Encorchador(cinta, Velocidad.RAPIDO);
        Rellenador rellenador = new Rellenador(cinta, Velocidad.MEDIO, pale, encorchador);
        
        encorchador.start();
        rellenador.start();
    }
}

class Buffer {

    final static int MAX = 10;
    ArrayList<Botella> botellas = new ArrayList<>();

    public boolean hayBotellas() {
        return botellas.size() > 0;
    }
    
    public boolean cabenMasBotellas(){
        return botellas.size() < MAX;
    }

    public synchronized Botella getBotella() {
        while (!hayBotellas()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Botella botella = botellas.remove(0);
        System.out.println("GET Botella:" + botella);
        notify();
        return botella;
    }

    public synchronized void putBotella(Botella botella) {
        while (!cabenMasBotellas()){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            botellas.add(botella);
            System.out.println("PUT Botella:" + botella);
            notify();
    }
}

class Rellenador extends Thread {

    Buffer cinta;
    Velocidad velocidad;
    ArrayList<Botella> pale;
    Encorchador destino;

    public Rellenador(Buffer cinta, Velocidad velocidad, ArrayList<Botella> pale, Encorchador destino) {

        this.destino = destino;
        this.cinta = cinta;
        this.velocidad = velocidad;
        this.pale = pale;
    }

    @Override
    public void run() {
        for (int i = 0; i < pale.size(); i++) {
            Botella botella = pale.get(i);
            botella.llenar();
            cinta.putBotella(botella);
        }
        destino.interrupt();
    }
}

class Encorchador extends Thread {

    Buffer cinta;
    Velocidad velocidad;
    Botella botella;

    public Encorchador(Buffer cinta, Velocidad velocidad) {
        this.cinta = cinta;
        this.velocidad = velocidad;
    }

    @Override
    public void run() {
        
        try {
                botella = cinta.getBotella();
                Thread.sleep(velocidad.getMilisegundos());
                botella.tapar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Encorchador.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        while (cinta.hayBotellas() && !Thread.interrupted()) {
            try {
                botella = cinta.getBotella();
                Thread.sleep(velocidad.getMilisegundos());
                botella.tapar();
            } catch (InterruptedException ex) {
//                Logger.getLogger(Encorchador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class Botella {

    int id;
    boolean llena;
    boolean tapada;

    public Botella(int id) {
        this.id = id;
        llena = false;
        tapada = false;
    }

    public void llenar() {
        llena = true;
    }

    public void tapar() {
        tapada = false;
    }

    @Override
    public String toString() {
        return " " + id + " Llena: " + (llena ? "Si" : "No") + " Tapada: " + (tapada ? "Si" : "No");
    }
}

enum Velocidad {

    LENTO(1000), MEDIO(500), RAPIDO(100), INMEDIATO(0), ALEATORIO(-1);
    private int milisegundos;

    Velocidad(int milisegundos) {
        this.milisegundos = milisegundos;
    }

    public int getMilisegundos() {
        if (milisegundos >= 0) {
            return milisegundos;
        }
        return new Random().nextInt(LENTO.getMilisegundos());
    }
}
