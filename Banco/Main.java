/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpinepadi
 */
public class Main {

    private static final int CLIENTES = 1000;
    static CuentaBancaria cuenta = new CuentaBancaria();

    public static void main(String[] args) {
        //puede ser un array estatico, tenemos el campo clientes
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        for (int i = 0; i < CLIENTES; i++) {
            listaClientes.add(new Cliente(cuenta));
            listaClientes.get(i).start();
        }

        for (Cliente cliente : listaClientes) {
            try {
                cliente.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println(cuenta.leerSaldo());
    }
}

class CuentaBancaria {

    private int saldo;

    public CuentaBancaria() {
        saldo = 0;
    }

    public void ingresar(int cantidad) {
        //dos hilos en lugares distintos si llamas a distintos metodos sobre 
        //el mismo objeto no deberán esperear
        synchronized (this) {
            saldo += cantidad;
        }
    }
    
    //se puede sincronizar el metodo, el OBJETO SINCRONIZADO this (comun)
    public synchronized void retirar (int cantidad){
        saldo -= cantidad;
    }
    
    public synchronized int leerSaldo(){
        return saldo;
    }
//    public int getSaldo() {
//        return saldo;
//    }
//
//    public void setSaldo(int saldo) {
//        this.saldo = saldo;
//    }
}

class Cliente extends Thread {

    private static final int RETRASO = 10;
    private static final int INGRESO = 1;
    private CuentaBancaria cuenta;

    public Cliente(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public void run() {
//        MEJOR sincronizar en el objerto a sincronizar mediante un metodo
//        pero hay que saber hacerlo de las 2 formas
//        synchronized (cuenta) {
        try {
            Thread.sleep(RETRASO);
            cuenta.ingresar(INGRESO);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
//        }
    }
    
    public void transefencia(CuentaBancaria cuenta, int cantidad){
        //tener sincronizado el objeto permite tener metodos que llaman
        //a dos objetos sincrinozados
        this.cuenta.retirar(cantidad);
        cuenta.ingresar(cantidad);
    }
}
