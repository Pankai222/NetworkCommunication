import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;

public class MessageReader implements Runnable {

    private DataInputStream in;
    private Socket socket;
    private Client client;

    public MessageReader(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

    public void run() {
        boolean reading = true;
        while (reading) {
            try {
                try {
                    String response = in.readUTF();
                    System.out.println("\n" + response);
                } catch (SocketException se) {
                    System.out.println("User closed connection");
                    reading = false;
                }
            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
    }
}

