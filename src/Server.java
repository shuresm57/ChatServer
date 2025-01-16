import java.io.*;
import java.net.*;

public class Server {

    private static int PORT = 4444;

    public static void main(String[] args)
    {

        ClientManager cm = new ClientManager();
        ChatController cc = new ChatController(cm);

        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running and waiting for connections...");

            while(true)
            {
                Socket clientSocket =  serverSocket.accept();
                System.out.println("New client connected " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, cc);
                cm.addClient(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
