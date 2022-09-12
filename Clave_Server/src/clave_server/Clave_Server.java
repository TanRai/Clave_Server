package clave_server;

import java.io.IOException;

public class Clave_Server {

    public static void main(String[] args) throws IOException {
        ApplicationNotifier applicationNotifier = new ApplicationNotifier();
        Thread thread2 = new Thread(applicationNotifier);
        thread2.start();
        ApplicationListener applicationListener = new ApplicationListener(applicationNotifier);
        Thread thread = new Thread(applicationListener);
        thread.start();
        
    }
    
}
