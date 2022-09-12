package clave_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationNotifierObject{

    private final Socket socket;
    BufferedReader in_socket;
    PrintWriter out_socket;

    public ApplicationNotifierObject(Socket socket) {
        this.socket = socket;
        try {
            in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out_socket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }
    }
    public void broadCast(String notify){
        out_socket.println(notify);
    }
}
