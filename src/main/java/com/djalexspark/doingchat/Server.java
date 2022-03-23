package com.djalexspark.doingchat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;




public class Server extends Thread{
    @Override
    public void run() {
        try {
            server = new ServerSocket(Port);
            setServerStatus("new ServerSocket(Const.Port)");
            System.out.println(getServerStatus());
            while (true) {
                Socket socket = server.accept();
                setServerStatus("server.accept()");
                System.out.println(getServerStatus());
                Connection con = new Connection(socket);
                setServerStatus("new Connection(socket)");
                System.out.println(getServerStatus());
                connections.add(con);
                setServerStatus("connections.add(con)");
                System.out.println(getServerStatus());
                con.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

    }

    private String serverStatus = "";
    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;
    private int Port = 8283;


    //todo обеспечть доступ к методу closeall() через экземляр класса и выполнять его из кнопки closeServer


    public String getServerStatus() {
        return serverStatus;
    }



    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Server() {

    }


    public void closeAll() {

        try {
            server.close();
            synchronized(connections) {
                Iterator<Connection> iter = connections.iterator();
                while(iter.hasNext()) {
                    ((Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.err.println("closeAll внутри сломался");
        }
    }


    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private String name = "";


        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }


        @Override
        public void run() {


            try {
                name = in.readLine();

                synchronized(connections) {
                    Iterator<Connection> iter = connections.iterator();
                    while(iter.hasNext()) {
                        ((Connection) iter.next()).out.println(name + " cames now");
                    }
                }

                String str = "";
                while (true) {
                    str = in.readLine();
                    if(str.equals("exit")) break;


                    synchronized(connections) {
                        Iterator<Connection> iter = connections.iterator();
                        while(iter.hasNext()) {
                            ((Connection) iter.next()).out.println(name + ": " + str);
                        }
                    }
                }

                synchronized(connections) {
                    Iterator<Connection> iter = connections.iterator();
                    while(iter.hasNext()) {
                        ((Connection) iter.next()).out.println(name + " has left");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }


        public void close() {
            try {
                in.close();
                out.close();
                socket.close();


                connections.remove(this);
                if (connections.size() == 0) {
                    Server.this.closeAll();
                    System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("closeAll() не сработал");
            }
        }
    }
}
