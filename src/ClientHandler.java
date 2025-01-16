import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private ChatController chatController;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private static List<String> usernames;


    public ClientHandler(Socket clientSocket, ChatController chatController) {
        this.clientSocket = clientSocket;
        this.chatController = chatController;
        try{
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            out.println("Enter your name:");
            username = in.readLine();
            chatController.broadcastMessage(username + " has joined the chat.", null);

            String message;
            while((message = in.readLine()) != null) {
                chatController.handleClientMessage(message, this);
                }

            } catch (IOException e) {
            e.printStackTrace();
            } finally{
            chatController.broadcastMessage(username + " has left the chat.", this);
            chatController.getClientManager().removeClient(this);
            closeConnection();
        }
    }

    public String getUsername(){
        return username;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
