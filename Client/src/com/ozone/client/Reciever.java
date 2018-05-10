package com.ozone.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Reciever extends Thread {
    Socket server;
    Client client;
    BufferedReader inStream;
    public boolean read = true;

    public Reciever(Socket server, Client client) {
        this.server = server;
        this.client = client;
        try {
            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            while (read){
                String message = inStream.readLine();
                System.out.println("[Server] "+message);
                String op = message.split("\\?")[0];
                String[] args = message.split("\\?")[1].split(",");
                switch (op){
                    case "newFile":
                        client.newFile(args[0], false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
