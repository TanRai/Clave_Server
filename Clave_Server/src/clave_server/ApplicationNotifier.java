package clave_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationNotifier implements Runnable{
    public static ArrayList<ApplicationNotifierObject> applicationNotifierObjects = new ArrayList();

    @Override
    public void run() {
        try {
            ServerSocket server_socket = new ServerSocket(2022);
            System.out.println("Port 2022 is now open.");

            while (true) {
                Socket socket = server_socket.accept();
                applicationNotifierObjects.add(new ApplicationNotifierObject(socket));
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void broadcastAll(String notify){
        for (ApplicationNotifierObject applicationNotifierObject : applicationNotifierObjects) {
            applicationNotifierObject.broadCast(notify);
        }
    }
}
