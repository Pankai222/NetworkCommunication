import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static ServerSocket servSock;
    private static final int port = 7080;
    //benytter Vector i stedet for ArrayList, da den er "thread-safe"
    private Vector<Socket> clientList = new Vector<>();
    private static final int max = 5;

    public static void main(String[] args) {
        Socket socket = null;
        System.out.println("Opening port");
        /*
        initialiserer ny threadpool og begrænser threads til 5, så der kun kan være 5 server
        sockets og derfor kun 5 clients. Pool'en executer runnables af ServerThread skabt længere
        nede.
        */
        ExecutorService pool = Executors.newFixedThreadPool(max);

        try {
            servSock = new ServerSocket(port);
        } catch (IOException io) {
            System.out.println("Unable to find port");
            System.exit(1);
        }
        ChatServer chat = new ChatServer();
        while (true) {
            try {
                /*
                 benytter ServerSocket.accept() i uendeligt while-loop til at blive ved med at
                 lytte efter
                 client-sockets. Når client-socket accepteres skabes der vha metoden en ny
                 serversocket, som tilføjes til en ServerThread-tråd.
                 */
                socket = servSock.accept();
                chat.clientList.add(socket);
                System.out.println("Found connection");
                Runnable r = new ServerThread(socket);
                pool.execute(r);
                //TODO: shutdown og slet indhold af tråden når den er idle

            } catch (IOException io) {
                System.out.println("Error:" + io);
                io.printStackTrace();
            }
        }
    }

    public void addToList(Client client) {

    }

    public Vector<Socket> getClientList() {
        return clientList;
    }

    public void setClientList(Vector<Socket> clientList) {
        this.clientList = clientList;
    }

    //TODO: send besked fra en client-socket til alle sockets
    public void sendToAll(String message) {
    for (Socket s : clientList) {
        try {
            PrintWriter output = new PrintWriter(s.getOutputStream());
            output.println(message);

        } catch (IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
            }
        }
    }
}

