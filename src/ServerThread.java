import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable {

        protected Socket socket;
        public DataOutputStream data;

        public ServerThread(){}

        public ServerThread(Socket socket, DataOutputStream data) {
            this.socket = socket;
            this.data = data;

        }

        public ServerThread(Socket socket) {
            this.socket = socket;
        }


        public void run() {
            ChatServer serv = new ChatServer();
            try {
                //Scanner clientInput = new Scanner(socket.getInputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                //PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
                data = new DataOutputStream(socket.getOutputStream());
            /*
            benytter while-loop til at scanne efter input fra client og sende tilbage i form af
            serverOutput. Kører indtil client skriver QUIT.
            */
                while (true) {
                    String msg = in.readUTF();
                    System.out.println("Message received");
                    //serv.sendToAll("Message - " + msg);
                    serv.sendToAllEasymode(msg);
                }
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

        public Socket getSocket() {
            return socket;
        }
    }

