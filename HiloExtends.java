/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dpinepadi
 */
public class HiloExtends extends Thread 
{
    public HiloExtends(String nombre) {
        super(nombre);
    }
    
    @Override
    public void run() {
        for(int i=0; i<10;i++)
        { System.out.println(getName() + " > " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) { }
        }
    }
    public static void main(String[] args) {  // el main suele estar situado en otra clase
        HiloExtends hilo1 = new HiloExtends("hilo1");
        HiloExtends hilo2 = new HiloExtends("hilo2");
        HiloExtends hilo3 = new HiloExtends("hilo3");
        HiloExtends hilo4 = new HiloExtends("hilo4");
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        System.out.println("Fin del main");
    }
}

