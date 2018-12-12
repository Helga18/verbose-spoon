package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;
    @Override
    public void run() {
       try {
           BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
           while(true) {
               String msg = br.readLine();
               System.out.println(msg);
               String [] data = msg.split(":");
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
