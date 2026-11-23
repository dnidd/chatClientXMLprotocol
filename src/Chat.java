import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Connects to server via Socket.
 * Create communication Thread.
 * Take user input - send to Handler.
 * Take message coming from server.
 * Chat can read from the keyboard while ChatSocketHandler is listening to the server.
 */
public class Chat {

    private Socket socket;
    private ChatSocketHandler chatSocketHandler;

    /**
     * Constructor
     * @param host = server address/host
     * @param port = port
     */
    public Chat(String host, int port) throws IOException {
        // Connect socket to server
        socket = new  Socket(host, port);
    }

    /**
     * Starts chat.
     * @throws IOException propagates to main in Client
     */
    public void startChat() throws IOException {

        // Create and return new user
        User user = createUser();

        chatSocketHandler = new ChatSocketHandler(this, socket);
        // Chat can read from the keyboard while
        // ChatSocketHandler is listening to the server.
        Thread thread = new Thread(chatSocketHandler);
        thread.start();

        System.out.println("\nWrite message: ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();

            if (message.equals("exit")) {
                chatSocketHandler.sendMessage("exit");
                socket.close();
                break;
            }

            // Create and fetch xml document
            Document document = XmlMessageCreator.createXmlMessage(user, message);

            // Pretty XML for terminal output/print.
            XMLOutputter outputterPretty = new XMLOutputter(Format.getPrettyFormat());
            String terminalXml = outputterPretty.outputString(document);
            System.out.println("Sending pretty xml:");
            System.out.println(terminalXml);

            // Single line XML/text to Server.
            Format format = Format.getCompactFormat();
            format.setLineSeparator("");
            XMLOutputter outputter = new XMLOutputter(format);
            String xmlMessage = outputter.outputString(document);

            chatSocketHandler.sendMessage(xmlMessage);
        }
    }

    /**
     * Create and return user.
     * @return user
     */
    private User createUser() {
        String name;
        String email;
        String homepage;

        Scanner scanUser = new Scanner(System.in);

        System.out.println("name: ");
        name = scanUser.nextLine();

        System.out.println("email: ");
        email = scanUser.nextLine();

        System.out.println("homepage: ");
        homepage = scanUser.nextLine();

        return new User(name, email, homepage);
    }


}




