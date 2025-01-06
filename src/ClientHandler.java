import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

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
            username = getUserName();
            System.out.println("User" + username + " connected");
            out.println("Welcome to the chat " + username + "!");
            out.println("Type your message");
            String inputLine;

            while((inputLine = in.readLine()) != null)
            {
                System.out.println("[" + username + "] " + inputLine);
                Server.broadcast("[" + username + "]" + inputLine, this);
            }
            Server.getClients().remove(this);
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUserName() throws IOException {
        out.println("Enter your username: ");
        return in.readLine();
    }

    public void sendMessage(String message) {
        out.println(message);
        out.println("Type your message");
    }

}
