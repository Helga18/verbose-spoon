package server;

import models.Hero;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection implements Runnable {

    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Hero hero;
    private static final String HOST = "localhost";
    private static final int PORT = 3444;
    private Socket clientSocket = null;

    public Hero getHero() {
        return hero;
    }

    public Connection(Socket socket, Server server) {
        try {
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    String target = clientMessage.split(":")[0];
                    String[] parts = clientMessage.split(":")[1].split(";");
                    switch (target) {
                        case "newUser":
                            hero = new Hero(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                            server.sendMessageToAllClients("newUser:" + hero.getName() + ";" + hero.getX() + ";" + hero.getY());
                            server.sendLocationsToNewClient(this);
                            server.getClients().add(this);
                            break;
                        case "move":
                            hero.setX(Double.parseDouble(parts[0]));
                            hero.setY(Double.parseDouble(parts[1]));
                            server.sendMessageToAllClients("move:" + hero.getName() + ";" + hero.getX() + ";" + hero.getY());
                            break;
                        case "remove":
                            System.out.println(server.getClient(this));
                            server.sendMessageToAllClients("remove:" + parts[0]);
                            break;
                        case "shoot":
                            server.sendMessageToAllClients("shoot:" + hero.getName() + ";" + parts[0] + ";" + parts[1]);
                        default:
                            if (clientMessage.equalsIgnoreCase("##session##end##")) {
                                break;
                            }
                    }
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            if (clientSocket.isClosed()) {
                System.out.println("Player left");
            }
            this.close();

        }
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);

    }
}
