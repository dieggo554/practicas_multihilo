/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Elecciones;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Votacion {

    private static final int POBLACION = 100;
    private static final ArrayList<String> nombre = new ArrayList<>();
    private static final ArrayList<Partido> partidos = new ArrayList<>();

    public static void main(String[] args) {
        nombre.add("PP");
        nombre.add("PSOE");
        nombre.add("CI");
        nombre.add("POD");
        
        for(String nomePartido: nombre)
            partidos.add(new Partido(nomePartido));
        
        
//        for (int i = 0; i < NOMBRES.size(); i++) {
//            PARTIDOS.add(new Partido(NOMBRES.get(i)));
//        }
        
        ArrayList<Votante> listaVotantes = new ArrayList<>();

        for (int i = 0; i < POBLACION; i++) {
            listaVotantes.add(new Votante(partidos));
            listaVotantes.get(i).start();
        }

        for (Votante votante : listaVotantes) {
            try {
                votante.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(VotacionMAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        int total = 0;
        for (int i = 0; i < partidos.size(); i++) {
            System.out.println(partidos.get(i).toString());
            total += partidos.get(i).getVotos();
        }
        
        System.out.println("Total: " + total);
    }
}


class Partido {

    private int votos;
    private String nombre;

    public Partido(String nombre) {
        votos = 0;
        this.nombre=nombre;
    }
    
     synchronized void votar() {
        votos ++;
    }
    
    int getVotos(){
        return votos;
    }

    public String toString(){
        return nombre + " " + votos + " votos";
    }
}

class Votante extends Thread {

    Random random = new Random();
    ArrayList<Partido> partidos;

    public Votante(ArrayList<Partido> partidos) {
        this.partidos=partidos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10);
            partidos.get(random.nextInt(partidos.size())).votar();
        } catch (InterruptedException ex) {
            Logger.getLogger(Votante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
