import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

public class Client extends Thread {

    private String userName;
    private static InetAddress ipAddress;
    private int port;
    private static Vector<Client> clientVector = new Vector<Client>();

    public Client() {}

    public Client(String userName) {
        this.userName = userName;
        try {
            this.ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException uhE) {
            System.out.println("No host found");
            System.exit(1);
        }
        this.port = 7080;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connectToServer();
    }
    /*
    Requester først at man følger protokollen ved at skrive JOIN efterfulgt af et brugernavn.
    Connecter så til en serversocket vha socket lavet ud fra port og ipAddress i constructor.
    Venter på input i konsol fra bruger, henter så response fra serveren vha. scanner
    i serverInputStream.
     */
    public void connectToServer() {
                ChatServer s = new ChatServer();
                System.out.println("Write JOIN followed by username to join the chatroom.");
                ProtocolHandler protHandler = new ProtocolHandler();
                //metode i ProtocolHandler som tjekker efter fejl i username.
                Client client = new Client(protHandler.join());
                System.out.println("User " + client.getUserName() + " " + client.getIpAddress() + ":" + client.getPort() +
                        " has " +
                        "connected.");
                Socket socket = null;

                try {
                    socket = new Socket(client.getIpAddress(), client.getPort());
                    Scanner serverInput = new Scanner(socket.getInputStream());
                    PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);

                    Scanner userEntry = new Scanner(System.in);
                    String msg, response;

                    do {
                        System.out.println("Enter message:");
                        msg = userEntry.nextLine();
                        clientOutput.println(msg);
                        response = serverInput.nextLine();
                        System.out.println(client.getUserName() + " - " + response);

                    } while (!msg.equals("QUIT"));
                } catch (IOException io) {
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

            public String getUserName() {
                return userName;
            }

            public InetAddress getIpAddress() {
                return ipAddress;
            }

            public int getPort() {
                return port;
            }

        }



