import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable {

    protected Socket socket;

    public ServerThread(){}

    public ServerThread(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        int msgNumber = 0;

        try {
            Scanner clientInput = new Scanner(socket.getInputStream());
            String msg = clientInput.nextLine();
            PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
            /*
            benytter while-loop til at scanne efter input fra client og sende tilbage i form af
            serverOutput. Kører indtil client skriver QUIT.
            */
            while (!msg.equals("QUIT")) {
                System.out.println("Message received");
                msgNumber++;
                serverOutput.println("Message " + msgNumber + ": " + msg);
                msg = clientInput.nextLine();

            }
            serverOutput.println(msgNumber + " messages received");
        } catch (IOException io) {
            System.out.println("Error");
            io.printStackTrace();
    } finally {
        try {
            System.out.println("Closing connection");
            socket.close();
        } catch (IOException io) {
            System.out.println("Unable to disconnect");
            System.exit(1);
            }
        }
    }
}
