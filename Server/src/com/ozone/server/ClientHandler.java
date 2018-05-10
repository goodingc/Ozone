package com.ozone.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread{
    Socket client;
    BufferedReader inStream;
    DataOutputStream outStream;
    Server server;

    public ClientHandler(Socket client, Server server) {
        this.server = server;
        this.client = client;
        try {
            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outStream = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("["+client.getInetAddress()+" connected]");
        try {
            while (true){
                String message = inStream.readLine();
                if(message == null) break;
                System.out.println("["+client.getInetAddress()+"] "+message);
                String op = message.split("\\?")[0];
                String[] args = message.split("\\?")[1].split(",");
                switch (op){
                    case "newFile":
                        server.newFile(args[0]);
                }
            }
            System.out.println("Closing");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("["+client.getInetAddress()+" disconnected]");
    }
}
