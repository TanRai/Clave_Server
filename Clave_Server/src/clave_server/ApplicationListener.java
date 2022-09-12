package clave_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationListener implements Runnable {
    ApplicationNotifier applicationNotifier;

    public ApplicationListener(ApplicationNotifier applicationNotifier){
        this.applicationNotifier = applicationNotifier;
    }
    
    @Override
    public void run() {
        try {
            ServerSocket server_socket = new ServerSocket(2020);
            System.out.println("Port 2020 is now open.");

            while (true) {
                Socket socket = server_socket.accept();
                ApplicationListenerThread applicationListenerThread = new ApplicationListenerThread(socket, applicationNotifier);
                Thread thread = new Thread(applicationListenerThread);
                thread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
