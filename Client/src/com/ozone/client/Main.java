package com.ozone.client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("D:\\Documents\\IntelliJ\\Ozone\\files\\client");
        client.connect("localhost",8080);
        client.newFile("\\folder\\test.txt");
        while(true){}
        //client.disconnect();
    }
}
