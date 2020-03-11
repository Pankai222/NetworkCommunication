import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

    private String userName;
    private InetAddress ipAddress;
    private int port;

    public Client() {
        try {
            this.ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException uhE) {
            System.out.println("No host found");
            System.exit(1);
        }
        this.port = 7080;
    }

    public void connectToServer() {

        try {
            Socket socket = new Socket(ipAddress, port);
            System.out.println("Connection established - Welcome to the Chat Room");
            new MessageReader(socket, this).start();
            new MessageWriter(socket, this).start();

        } catch (IOException io) {
            io.printStackTrace();

        }
    }

        public String getUserName() { return userName;}

        public void setUserName(String userName) {
        this.userName = userName;
        }

        public InetAddress getIpAddress() { return ipAddress; }

        public int getPort() { return port; }


    public static void main(String[] args) {
        Client client = new Client();
        client.connectToServer();
        }

    }



