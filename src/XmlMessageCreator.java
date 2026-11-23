import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Class constructing XML document as Document.
 */
public class XmlMessageCreator {

    public static Document createXmlMessage(User user, String message) {

        // Create elements and content with sub elements.
        Element root = new Element("message");
        Element header = new Element("header");

        Element protocol = new Element("protocol");
        protocol.addContent(new Element("type").setText("CTTP"));
        protocol.addContent(new Element("version").setText("1.0"));
        protocol.addContent(new Element("command").setText("MESS"));

        Element id = new Element("id");
        id.addContent(new Element("name").setText(user.getName()));
        id.addContent(new Element("email").setText(user.getEmail()));
        id.addContent(new Element("homepage").setText(user.getHomepage()));
        id.addContent(new Element("host").setText("unknown"));

        header.addContent(protocol);
        header.addContent(id);

        // place message in XML element <body>
        Element body = new Element("body");
        body.setText(message);

        root.addContent(header);
        root.addContent(body);


        // Creates the DocType with the specified element name and a reference to an external DTD.
        DocType doctype = new DocType(
                "message",
                "1//--//Example//123",
                "https://--.--.---.---/~-----/--/---__---/---/-/-.-.-/message.dtd");

        Document document = new Document(root, doctype);

        return document;
    }
}
