import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.net.Socket;

/**
 * Class used in separate threads for each connected client.
 * implements Runnable to be used in thread.
 * Communicator to server via socket.
 * Takes incoming messages from server.
 * Forward incoming messages to Chat.
 * ChatSocketHandler is listening to the server while Chat can read the keyboard.
 */
public class ChatSocketHandler implements Runnable {

    private Chat chat;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    /**
     * Constructor
     * Create in and out streams.
     * @param chat = chat.
     * @param socket = connected socket.
     * @throws IOException propagates to startChat in Chat.
     */
    public ChatSocketHandler(Chat chat, Socket socket) throws IOException {
        this.chat = chat;
        this.socket = socket;
        createStreams();
    }


    /**
     * Continually reading from server.
     * Listening/waiting for messages is a blocking operation - needs a separate thread.
     * chat (Chat) fetches messages with getMessageFromChatHandler().
     */
    @Override
    public void run() {
        String message;
        // true validates XML to DTD.
        // Example: removing element in Document creation throws JDOMException.
        SAXBuilder saxBuilder = new SAXBuilder(true);

        try {
            while ((message = in.readLine()) != null) {
                try {
                    Document document = saxBuilder.build(new StringReader(message));

                    XMLOutputter outputterPretty = new XMLOutputter(Format.getPrettyFormat());

                    System.out.println("Receiving pretty xml:");
                    System.out.println(outputterPretty.outputString(document));

                    // Get info from XML structure in document
                    Element root = document.getRootElement();
                    Element header = root.getChild("header");
                    Element id = header.getChild("id");
                    String name = id.getChildText("name");
                    String email = id.getChildText("email");
                    String body = root.getChildText("body");

                    // <namn> (<epost>): <meddelande>
                    System.out.println(name + " (" + email + "): " + body);
                } catch (JDOMException e) {
                    System.err.println( "KUNDE INTE PARSA ETT MEDDELANDE: " + message);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection down.");
        }

    }


    /**
     * Send message to server via socket (out).
     * @param message = written text.
     */
    public void sendMessage(String message) {
        out.println(message);
    }


    /**
     * Create streams to server via socket.
     * out: to server
     * in: from server
     * @throws IOException propagates to constructor.
     */
    private void createStreams() throws IOException {
        // write stream to socket
        out = new PrintWriter(                                            // enables: .println(message)
                new OutputStreamWriter(                                   // text to bytes
                        socket.getOutputStream(), "UTF-8"),   // create output stream
                true);                                                    // flush buffer

        // get text from server
        in = new BufferedReader(                                          // enables: in.readLine()
                new InputStreamReader(                                    // byte to text
                        socket.getInputStream(),  "UTF-8"));  // create input stream

    }

}
