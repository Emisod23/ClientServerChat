import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author magnus
 */
public class Client2 {

    public static void main(String[] args) {
        String ip = (String) JOptionPane.showInputDialog(null,"IP?","Connect to..",JOptionPane.QUESTION_MESSAGE);
        int port = Integer.parseInt(JOptionPane.showInputDialog(null,"Port?","Connect to..",JOptionPane.QUESTION_MESSAGE));       ;
        Socket socket2 = null;

        try {
            socket2 = new Socket(ip,port);
        } catch (Exception e) {
            System.out.println("Client failed to connect");
            System.exit(0);
        }

        // GO
        try {
            Scanner tgb = new Scanner(System.in);
            PrintWriter out2 = new PrintWriter(socket2.getOutputStream(),true);
            ListenerThread in2 = new ListenerThread(new BufferedReader(new InputStreamReader(socket2.getInputStream())));
            Thread listener = new Thread(in2);
            listener.start();
            boolean run = true;
            while (run) {
                String msg = tgb.nextLine();
                out2.println(msg);
            }

            out2.close();
            socket2.close();
            System.out.println("Done!");
        } catch (Exception e) {
            System.out.println("Client failed to communicate");
        }
    }
}