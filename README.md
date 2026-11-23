### Chat client using XML structure
Chat client implementing an XML based protocol defined by the serverside
DTD file 'message.dtd' for messages.

Stream sockets and XML.

#### XML
XML helps standardize data.

Data parsed in XML format can be parsed in two ways:
1. By controlling that data in an XML document follows
the general XML standard and if so, it's well-designed.
2. You control (1) and that the document also follows specified directives 
in a so called DTD or an XML schema. If a document fulfills these more
stricter requirements it's called 'valid'.

Parsing is also used to deconstruct an XML document and to be able to fetch data.
Technically parsing can be done in two ways:
- SAX parsing is quick and do not have a complete representation av an XML document in memory.
- DOM parsing is slower but has a complete representation od an XML in memory.

JDOM API which works on top of SAX and DOM is used in this program.
Java and XML is a perfect couple for platform independence processing. 

#### JDOM 2
Download anf implement jdom2 as classpath or dependency if Maven project. 
jdom-2.0.6.1.jar (in lib) added to classpath.

Processing XML with Java, Elliotte Rusty Harold<br>
Over 1000 pages of detailed information on
SAX, DOM, JDOM, JAXP, TrAX, XPath, XSLT, SOAP, and lots of other juicy acronyms.
https://www.cafeconleche.org/books/xmljava/

Creating and document type:
https://www.cafeconleche.org/books/xmljava/chapters/ch14s05.html
https://www.jdom.org/docs/apidocs/org/jdom2/DocType.html


### Execute application
Application cannot be run and testet in this shape and form due to server implementation and DTD template.
Server is masked in XmlMessageCreator.java.
Run application and client side server.