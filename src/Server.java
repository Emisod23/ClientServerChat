import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author magnus
 */
public class Server {
    public static void main(String[] args){
        int port = 2323;
        boolean run = true;
        ServerSocket serverSocket;
        Socket socket;
        Socket socket2;
        System.out.println("Server started.");

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for connections!");
                socket = serverSocket.accept();
                socket2 = serverSocket.accept();
                // Go
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                PrintWriter out2 = new PrintWriter(socket2.getOutputStream(), true);
                ListenerThread in = new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                ListenerThread in2 = new ListenerThread(new BufferedReader(new InputStreamReader(socket2.getInputStream())));
                Thread listener = new Thread(in);
                Thread listener2 = new Thread(in2);
                listener.start();
                listener2.start();
                System.out.println("Client connected!");
                Scanner tgb = new Scanner(System.in);
                //Protocol
                while (run) {
                    String msg = tgb.nextLine();
                    System.out.println("SERVER: " + msg);
                }
                out.close();
                out2.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Server fail");
        }
    }
}