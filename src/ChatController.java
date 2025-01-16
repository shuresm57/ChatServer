import java.io.IOException;

public class ChatController {

    private ClientManager clientManager;

    public ChatController(ClientManager cm) {
        this.clientManager = cm;
    }

    public void handleClientMessage(String message, ClientHandler sender) throws IOException {
        if(message.startsWith("/")){
            processCommand(sender,message);
        }
        else if(message.startsWith("@") || message.contains(" @")){
            mentionsCommand(sender, message);
        }
        else {
            broadcastMessage("[" + sender.getUsername() + "] " + message, sender);
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clientManager.getClients()) {
            if(sender == null || client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void mentionsCommand(ClientHandler sender, String message) {
            String[] words = message.split(" ");

            for (String word : words) {
                if (word.startsWith("@")) {
                    String mentionedUsername = word.substring(1); // Fjern '@'

                    ClientHandler mentionedUser = clientManager.getClientByUsername(mentionedUsername);

                    if (mentionedUser != null) {
                        mentionedUser.sendMessage("You were mentioned by [" + sender.getUsername() + "]");
                        return;
                }
            }
        }
    }

    public void processCommand(ClientHandler sender, String message) {
        if(message.startsWith("/quit")){
            sender.sendMessage("You have left the chat.");
            clientManager.removeClient(sender);
            sender.closeConnection();
        }
        else{
            sender.sendMessage("Unknown command: " + message);
        }
    }

    public ClientManager getClientManager(){
        return clientManager;
    }

}
