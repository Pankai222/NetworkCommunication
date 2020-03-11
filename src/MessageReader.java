import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageReader extends Thread {

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
        while (true) {
            try {
                String response = in.readUTF();
                System.out.println("\n" + response);
            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
    }
}
