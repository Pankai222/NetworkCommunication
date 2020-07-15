import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static ServerSocket servSock;
    private static final int port = 7080;
    //benytter Vector i stedet for ArrayList, da den er "thread-safe"
    private static Vector<Socket> clientList = new Vector<>();
    private static final int max = 5;

    public static void main(String[] args) {
        Socket socket = null;
        System.out.println("Opening port");

        ExecutorService pool = Executors.newFixedThreadPool(max);

        try {
            servSock = new ServerSocket(port);
        } catch (IOException io) {
            System.out.println("Unable to find port");
            System.exit(1);
        }
        while (true) {
            try {

                socket = servSock.accept();
                clientList.add(socket);
                System.out.println("Found connection");
                Runnable r = new ServerThread(socket);

                pool.execute(r);

            } catch (IOException io) {
                System.out.println("Error:" + io);
                io.printStackTrace();
            }
        }
    }

    public void broadcast(String message) {
        for (Socket socket : clientList) {
            try {
                if (!socket.isClosed()) {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(message);
                }
            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
    }
}

