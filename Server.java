import java.net.*;
import java.io.*;

class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader read;
    PrintWriter write;

    // constructor
    public Server() {
        try {

            try {
                InetAddress myip = InetAddress.getLocalHost();
                System.out.println("IP Address of Server :"+myip.getHostAddress());

            } catch (Exception e) {
                System.out.println(e);
            }

            // making serversocket
            server = new ServerSocket(4444);
            System.out.println("ready to connect ! ");
            System.out.println("waiting for connection !");

            // accepting the connection
            socket = server.accept();

            // taking the input from the client
            read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            write = new PrintWriter(socket.getOutputStream());

            // calling reading and writing function
            Reading();
            Writing();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void Reading() {
        Runnable t1 = () -> {
            System.out.println("Reading started!");
            try {
                while (!socket.isClosed()) {
                    // message reading
                    String message = read.readLine();

                    // exit option
                    if (message.equals("exit")) {
                        System.out.println("connection terminated");
                        socket.close();
                        break;

                    }
                    System.out.println("Client : " + message);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(t1).start();
    }

    public void Writing() {

        // started writing
        System.out.println("Writer started !");
        Runnable t2 = () -> {
            try {
                while (!socket.isClosed()) {

                    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                    String content = input.readLine();
                    write.println(content);
                    write.flush();
                    if (content.equals("exit")) {
                        socket.close();
                        break;

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(t2).start();

    }

    public static void main(String[] args) {
        System.out.println("Server starting....");
        new Server();
    }
}