import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    // ServerSocket server;
    Socket socket;
    BufferedReader read;
    PrintWriter write;

    // constructor
    public Client() {
        try {
            // making serversocket
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter IP Address Of Server : ");
            String s = sc.nextLine();
            socket = new Socket(s, 4444);
            System.out.println("ready to connect ! ");
            System.out.println("waiting for connection !");

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
                    System.out.println("Server :" + message);
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
        System.out.println("Client starting ....");
        new Client();

    }
}
