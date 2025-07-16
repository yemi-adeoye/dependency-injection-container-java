package server;

import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket server = new Socket("127.0.0.1", 8080);
            System.out.println("connected to server at " + server.getInetAddress().getHostName());

            OutputStream outputStream = server.getOutputStream();

            outputStream.write("controller".getBytes());

            server.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
