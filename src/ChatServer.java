import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static ServerSocket servSock;
    private static final int port = 7080;
    //benytter Vector i stedet for ArrayList, da den er "thread-safe"
    private static Hashtable outputStreams = new Hashtable();
    private static Vector<Socket> clientList = new Vector<>();
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
        while (true) {
            try {
                /*
                 benytter ServerSocket.accept() i uendeligt while-loop til at blive ved med at
                 lytte efter
                 client-sockets. Når client-socket accepteres skabes der vha metoden en ny
                 serversocket, som tilføjes til en ServerThread-tråd.
                 */
                socket = servSock.accept();
                clientList.add(socket);
                System.out.println("Found connection");
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                //outputStreams.put(socket, out);
                //Runnable r = new ServerThread(socket, out);
                Runnable r = new ServerThread(socket);

                pool.execute(r);
                //TODO: shutdown og slet indhold af tråden når den er idle

            } catch (IOException io) {
                System.out.println("Error:" + io);
                io.printStackTrace();
            }
        }
    }


    Enumeration getOutputStreams() {
        return outputStreams.elements();
    }


    public void sendToAll(String message) {
        for (Enumeration e = getOutputStreams(); e.hasMoreElements();) {
            DataOutputStream out = (DataOutputStream) e.nextElement();

            try {
                out.writeUTF(message);

            } catch (IOException ex) {
                System.out.println("Error");
            }
        }

    }

    public void sendToAllEasymode(String message) {
        for (Socket socket : clientList) {
            try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(message);

            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
    }
}

