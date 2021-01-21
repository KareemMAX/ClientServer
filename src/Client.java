import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static public boolean isOn = true;

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            InetAddress ip = InetAddress.getLocalHost();
            Socket server = new Socket(ip, 22000);

            DataOutputStream serverWrite = new DataOutputStream(server.getOutputStream());
            DataInputStream serverRead = new DataInputStream(server.getInputStream());

            Thread clientThread = new ClientThread(server);
            clientThread.start();

            while(isOn){
                String message = input.nextLine();
                serverWrite.writeUTF(message);
                if (message.equalsIgnoreCase("quit") || !isOn) {
                    clientThread.interrupt();
                    serverRead.close();
                    serverWrite.close();
                    server.close();
                    break;
                }
            }

        } catch (IOException ignored) {}
    }
}
