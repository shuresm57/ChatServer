import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {

    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public void addClient(ClientHandler client) {
        clients.add(client);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static CopyOnWriteArrayList<ClientHandler> getClients() {
        return clients;
    }

    public ClientHandler getClientByUsername(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equalsIgnoreCase(username)) {
                return client;
            }
        }
        return null;
    }
}
