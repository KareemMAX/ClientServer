import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static List<ClientHandler> clientsList = new ArrayList<>();
    static boolean serverIsOn = true;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Thread commandsThread = new Thread(){
            @Override
            public void run() {
                String command = input.nextLine();
                
            }
        };
        commandsThread.start();

        try{
            ServerSocket server = new ServerSocket(22000);
            System.out.println("Server is up!!");

            while(serverIsOn){
                Socket client = server.accept();
                System.out.println(client.getInetAddress() + " Connected");
                ClientHandler clientHandler = new ClientHandler(client);
                clientHandler.start();
                clientsList.add(clientHandler);
            }
        }
        catch (IOException e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void sendMessage(ClientHandler clientSending, String message){
        for (ClientHandler client : clientsList) {
            try{
                if(client != clientSending){
                    client.getClientWrite().writeUTF(message);
                }
            }
            catch (IOException e){
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
            }

        }
    }

    public static void removeClient(ClientHandler clientToRemove) {
        clientsList.remove(clientToRemove);
        System.out.println(clientToRemove.getClient().getInetAddress() + " Disconnected");
    }
}
