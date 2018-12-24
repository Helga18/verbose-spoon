package server;

import server.ClientConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int PORT = 3444;
    private ArrayList<ClientConnection> clients = new ArrayList<>();

    public ArrayList<ClientConnection> getClients() {
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
                ClientConnection client = new ClientConnection(clientSocket, this);
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

    public void sendLocationsToNewClient(ClientConnection client) {
        for (ClientConnection c : clients) {
            client.sendMsg("old:" + c.getHero().getName() + ";" + c.getHero().getX() + ";" + c.getHero().getY());
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientConnection c : clients) {
            c.sendMsg(msg);
        }

    }

    public void removeClient(ClientConnection client) {
        clients.remove(client);
    }

    public String getClient(ClientConnection clientConnection) {
        for (ClientConnection c: clients){
            System.out.println(c);
            System.out.println(clientConnection);
            if(c==clientConnection){
                return c.getHero().getName();
            }
        }
        return null;
    }
}
