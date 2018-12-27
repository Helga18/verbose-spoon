package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int PORT = 3444;
    private ArrayList<Connection> clients = new ArrayList<>();

    public ArrayList<Connection> getClients() {
        return clients;
    }

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                clientSocket = serverSocket.accept();
                Connection client = new Connection(clientSocket, this);
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendLocationsToNewClient(Connection client) {
        for (Connection c : clients) {
            client.sendMsg("old:" + c.getHero().getName() + ";" + c.getHero().getX() + ";" + c.getHero().getY());
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (Connection c : clients) {
            c.sendMsg(msg);
        }

    }

    public void removeClient(Connection client) {
        clients.remove(client);
    }

    public String getClient(Connection clientConnection) {
        for (Connection c: clients){
            System.out.println(c);
            System.out.println(clientConnection);
            if(c==clientConnection){
                return c.getHero().getName();
            }
        }
        return null;
    }
}
