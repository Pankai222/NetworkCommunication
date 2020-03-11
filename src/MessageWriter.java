import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MessageWriter extends Thread {

    private DataOutputStream out;
    private Socket socket;
    private Client client;

    public MessageWriter(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

    public void run() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nWrite JOIN followed by your username:");
        String splChrs = "-/@#$!%^&_+=()";
        String userName = userInput.nextLine();
        if (!userName.substring(0,4).equals("JOIN") || userName.matches(splChrs) ||	userName.length() > 16) {
            System.out.println("Error: Follow the JOIN instructions and try again.");
            run();
        }

        client.setUserName(userName.substring(5,userName.length()));
        try {
            out.writeUTF(client.getUserName() + " has connected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String message;

        do {
            message = userInput.nextLine();
            try {
                out.writeUTF(client.getUserName() + ": " + message);
            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }

        } while (!message.equals("QUIT"));

        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        }
    }
}

