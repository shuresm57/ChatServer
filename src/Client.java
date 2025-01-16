import java.io.*;
import java.net.*;
import java.util.Scanner;

//Test 1234

public class Client {

    private static final String serverAdress = "192.168.0.208";
    private static final int PORT = 4444;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(serverAdress, PORT);
            System.out.println("Connected to the chat server!");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String serverResponse;
                    while((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            String userInput;
            while(true) {
                userInput = scanner.nextLine();
                out.println(userInput);
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            System.exit(1);
        }
    }

}
