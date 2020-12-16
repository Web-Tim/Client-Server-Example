package me.tim.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Server server = new Server(0);
        server.start();
    }

    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner scanner;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        serverSocket = ((port == 0 || port == 3000) ? new ServerSocket(3000) : new ServerSocket(port));
                        System.out.println("[Server] -> UNKNOWN: Waiting for Connections...");
                        socket = serverSocket.accept();
                        System.out.println("[Server] -> SUCCESS: Client(" + socket.getRemoteSocketAddress() + ") connected on Port: " + ((port == 0) ? 3000 : port));

                        scanner = new Scanner(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                        if (scanner.hasNextLine()) {
                            System.out.println("[Server] -> MESSAGE: " + scanner.nextLine());
                        }
                        scanner.close();
                        socket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        System.out.println("[Server] -> ERROR: Starting up the Server or connecting to the Server!");
                    }
                }
            }
        }).start();
    }
}
