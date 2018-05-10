package com.ozone.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    String contentRoot;
    ServerSocket socket;
    ArrayList<ClientHandler> handlers;

    public Server(String contentRoot, int port) {
        this.contentRoot = contentRoot;
        handlers = new ArrayList<>();
        try {
            socket = new ServerSocket(port);
            while (true) {
                ClientHandler handler = new ClientHandler(socket.accept(), this);
                System.out.println("New connection");
                handler.start();
                handlers.add(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newFile(String path, ClientHandler origin){
        File file = new File(contentRoot + path);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ClientHandler c: handlers){
            if(c!=origin){
                c.send("newFile?"+path);
            }
        }
    }

}
