package me.tim.net;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Client client = new Client("", 3000);
        client.connect();
        client.sendMessage("Test Message");
    }

    private Socket socket;
    private String hostname;
    private int port;
    private PrintWriter printWriter;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() {
        try {
            socket = ((port == 0 || port == 3000) ? new Socket(((hostname == null || hostname == "localhost") ? "localhost" : hostname), 3000) : new Socket(((hostname == null || hostname == "localhost") ? "localhost" : hostname), port));
            System.out.println("[Client] -> SUCCESS: Connected!");
        } catch (IOException e) {
            System.out.println("[Client] -> ERROR: Connecting to the Server!");
        }
    }

    public void sendMessage(String msg) {
        try {
            if (socket == null) {
                socket = ((port == 0 || port == 3000) ? new Socket(((hostname == null || hostname == "localhost") ? "localhost" : hostname), 3000) : new Socket(((hostname == null || hostname == "localhost") ? "localhost" : hostname), port));
                System.out.println("[Client] -> SUCCESS: Connected!");
            }

            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println(msg);
            printWriter.flush();
            System.out.println("[Client] -> SUCCESS: Sending Message!");

            printWriter.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("[Client] -> ERROR: Sending a message to the Server(" + ((hostname == null || hostname == "localhost") ? "localhost" : hostname) + ")!");
        }
    }
}
