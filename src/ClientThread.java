import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread{
    Socket server;
    DataInputStream serverRead;
    DataOutputStream serverWrite;

    ClientThread(Socket server){
        try{
            this.server = server;
            serverRead = new DataInputStream(server.getInputStream());
            serverWrite = new DataOutputStream(server.getOutputStream());
        }
        catch (IOException e){
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                String message = serverRead.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                System.out.println("You have been disconnected!!");
                Client.isOn = false;
                interrupt();
            }
        }
    }
}
