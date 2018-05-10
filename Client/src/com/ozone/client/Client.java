package com.ozone.client;

import java.io.*;
import java.net.Socket;

public class Client {
    String contentRoot;
    Socket server;
    DataOutputStream outStream;
    BufferedReader inStream;

    public Client(String contentRoot) {
        this.contentRoot = contentRoot;
    }

    public void connect(String serverAddress, int port){
        try {
            server = new Socket(serverAddress, port);
            outStream = new DataOutputStream(server.getOutputStream());
            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void newFile(String path){
        File file = new File(contentRoot+path);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            send("newFile?"+path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
