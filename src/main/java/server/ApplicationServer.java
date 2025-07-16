package server;

import annotation.Autowired;
import app.controller.UserController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ApplicationServer {

    @Autowired
    private static UserController userController;

    public ApplicationServer() {
    }

    private static void handleMessage(String message){
        switch(message){
            case "controller":
                userController.greet();
                break;
            case "service":
                userController.getUserGreeting();
                break;
            default:
                System.out.println("Invalid request");

        }
    }

    public static void runServer(int port) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Application listening on port " + port);

            while(true){
                Socket clientSocket = serverSocket.accept();

                InputStream inputStream = clientSocket.getInputStream();

                byte[] dataReceived = inputStream.readAllBytes();

                handleMessage(new String(dataReceived).toLowerCase());

                clientSocket.close();

            }
        }catch(IOException e){
            throw new RuntimeException("Couldn't start application Server: " + e.getMessage());
        }


    }
}
