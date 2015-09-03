package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class ClientHandler extends Thread {
        Scanner input;
        PrintWriter writer;
        Socket socket;
        
    public ClientHandler(Socket socket) throws IOException {
        this.input = new Scanner(socket.getInputStream());
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.socket = socket;
    }
    
    public void run(){
        
        String message = input.nextLine(); //IMPORTANT blocking call
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));
        while (!message.equals(ProtocolStrings.STOP)) {
            writer.println(message.toUpperCase());
            Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Closed a Connection");
    }
}
