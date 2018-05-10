package com.ozone.client;

import java.io.*;
import java.net.Socket;

public class Client {
    String contentRoot;
    Socket server;
    DataOutputStream outStream;
    BufferedReader inStream;
    Reciever reciever;

    public Client(String contentRoot) {
        this.contentRoot = contentRoot;
    }

    public void connect(String serverAddress, int port){
        try {
            server = new Socket(serverAddress, port);
            outStream = new DataOutputStream(server.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reciever = new Reciever(server, this);
        reciever.start();
    }

    public void disconnect(){
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        try {
            outStream.writeBytes(message+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newFile(String path, boolean origin){
        File file = new File(contentRoot+path);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(origin){
            send("newFile?"+path);
        }
    }

    public void newFile(String path){newFile(path, true);}
}
