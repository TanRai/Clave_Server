package clave_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationListenerThread implements Runnable {

    private final Socket socket;
    private final ApplicationNotifier applicationNotifier;
    private final DAO_Access daoAccess;

    public ApplicationListenerThread(Socket socket, ApplicationNotifier applicationNotifier) {
        this.socket = socket;
        this.applicationNotifier = applicationNotifier;
        daoAccess = new DAO_Access();
    }

    @Override
    public void run() {
        try {

            BufferedReader in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out_socket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            do {
                String requestType = in_socket.readLine();
                if(requestType.equals("sql")){
                    String sqlType = in_socket.readLine();
                    String json = in_socket.readLine();
                    String response = daoAccess.processRequest(sqlType, json);
                    out_socket.println(response);
                    if(sqlType.equals("insertMessage") || sqlType.equals("insertServerMessage")){
                        applicationNotifier.broadcastAll("chat");
                    }
                    else if(sqlType.equals("updateRequest")){
                        applicationNotifier.broadcastAll("friend");
                    }
                }
            } while (true);

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
            
        }
    }

}
