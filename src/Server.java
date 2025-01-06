import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private static int PORT = 4444;
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static CopyOnWriteArrayList<ClientHandler> getClients()
    {
        return clients;
    }

    public static void main(String[] args)
    {
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running and waiting for connections...");

            while(true)
            {
                Socket clientSocket =  serverSocket.accept();
                System.out.println("New client connected " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler sender){
        for (ClientHandler clientHandler : clients) {
            if(clientHandler != (sender)){
                clientHandler.sendMessage(message);
            }
        }
    }

}
