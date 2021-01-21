import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread{
    Socket client;
    DataInputStream clientRead;
    DataOutputStream clientWrite;
    String name;

    ClientHandler(Socket client){
        try{
            this.client = client;
            clientRead = new DataInputStream(client.getInputStream());
            clientWrite = new DataOutputStream(client.getOutputStream());
            clientWrite.writeUTF("You are connected");
        }
        catch (IOException e){
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void run() {
        try {
            clientWrite.writeUTF("What is your name:");
            name = clientRead.readUTF();

            Server.sendMessage(this, name + " has joined!!");

            while(true) {
                String message = clientRead.readUTF();
                if (message.equalsIgnoreCase("quit")) {
                    clientWrite.close();
                    clientRead.close();
                    client.close();
                    Server.removeClient(this);
                    break;
                }

                Server.sendMessage(this, name + " : " + message);
            }
        } catch (IOException e) {
            System.out.println(name + " have been disconnected!!");
            try{clientWrite.close();} catch(IOException ignored){}
            try{clientRead.close();} catch(IOException ignored){}
            try{client.close();} catch(IOException ignored){}
            Server.removeClient(this);
        }
    }

    public Socket getClient() {
        return client;
    }

    public DataInputStream getClientRead() {
        return clientRead;
    }

    public DataOutputStream getClientWrite() {
        return clientWrite;
    }

    public String getClientName() {
        return name;
    }

    public void setClientName(String name) {
        this.name = name;
    }
}
