/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dpinepadi
 */
class HiloRunnable implements Runnable {
    public void run() {
        for(int i=0; i<10;i++)
        { System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) { }
        }
    }
}
class Principal {
    public static void main(String[] args) {
        Thread hilo = new Thread (new HiloRunnable());
        Thread hilo2 = new Thread (new HiloRunnable());
        hilo.start();
        hilo2.start();
    } 
}

// CON CLASE ANONIMA
class PrincipalConAnonima {
    public static void main(String[] args) {
        
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) { }
                }
            }
        });
      
        hilo.start();
    }
}


// ().start
class PrincipalSinNombre {
    public static void main(String[] args) {
        
        (new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) { }
                }
            }
        })).start();
    }
}

// bucle NO eficiente, crea 100 clases anonimas y 100 objetos uno por cada clase
// hay que crear una clase si se utiliza el codigo mas de una vez

class PrincipalEnBucle {
    static int i;
    
    public static void main(String[] args) {
        
        for (i = 0; i < 100; i++) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                        System.out.println("Hilo: " + i + " empieza");
                        for (int j = 0; j < 3; j++) {
                            System.out.println("Hilo: " + i + " cilco " + j);
                    }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                    }
                }
            })).start();
        }
    }
}


