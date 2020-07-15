import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
                DataInputStream in = new DataInputStream(socket.getInputStream());
                data = new DataOutputStream(socket.getOutputStream());
                while (true) {
                        String msg = in.readUTF();
                        System.out.println("Message received");
                        serv.broadcast(msg);
                }
            } catch (EOFException eof) {
                //
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

