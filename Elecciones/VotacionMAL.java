/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Elecciones;

import com.sun.security.ntlm.Client;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.awt.windows.ThemeReader;


/**
 *
 * @author dpinepadi
 */
public class VotacionMAL {

    private static final int POBLACION = 100;
    private static final int PARTIDOS = 6;
    private static UrnaMAL urna = new UrnaMAL(PARTIDOS);

    public static void main(String[] args) {
        ArrayList<VotanteMAL> listaVotantes = new ArrayList<>();

        for (int i = 0; i < POBLACION; i++) {
            listaVotantes.add(new VotanteMAL(urna));
            listaVotantes.get(i).start();
        }

        for (VotanteMAL votante : listaVotantes) {
            try {
                votante.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(VotacionMAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int total = 0;
        int[] votos = urna.getVotos();
        for (int i = 0; i < votos.length; i++) {
            total += votos[i];
            System.out.println("votos de " + i + ": " + votos[i]);;
        }

        System.out.println("Total: " + total);
    }

    public static int getPARTIDOS() {
        return PARTIDOS;
    }
}

class UrnaMAL {

    private int[] votos;

    public UrnaMAL(int partidos) {
        votos = new int[VotacionMAL.getPARTIDOS()];

        for (int i = 0; i < votos.length; i++) {
            votos[i] = 0;
        }
    }

    public /*synchronized*/ void votar(int voto) { //no pueden votar a varios partidos en paralelo(DEBERIAN PODER)
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(UrnaMAL.class.getName()).log(Level.SEVERE, null, ex);
        }
//        !!!
//        synchronized(votos[voto]){  //int no es un objeto: NO SE PUEDE SINCRONIZAR, hay que crear un objeto CREAR CLASE PARTIDO con metodo sincronizado!
//        votos[voto]++;
//        }
    }

    public int[] getVotos() {
        return votos;
    }
}

class VotanteMAL extends Thread {

    Random random = new Random();
    private static UrnaMAL urna;

    public VotanteMAL(UrnaMAL urna) {
        this.urna = urna;
    }

    @Override
    public void run() {
        int numPartidos = VotacionMAL.getPARTIDOS();
        urna.votar(random.nextInt(numPartidos)); //el random debe estar fuera del sincronizado
    }
}
