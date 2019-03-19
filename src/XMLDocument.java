import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLDocument {
    Document document;
    Element root;

    XMLDocument(File file) {
        setXMLDocument(file);
    }

    public void setXMLDocument(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            root = document.getDocumentElement();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findNumberOfPersona() {
        NodeList personaList = root.getElementsByTagName("PERSONA");
        int numPersona = personaList.getLength();
        return numPersona;
    }

    public int findNumberOfSpeaker(String checkedName) {
        int count = 0;
        NodeList speakerList = root.getElementsByTagName("SPEAKER");
        Element speakerElement;
        for (int i = 0; i < speakerList.getLength(); i++) {
            speakerElement = (Element) speakerList.item(i);
            if (checkedName.equals(speakerElement.getTextContent()))
                count++;
        }
        return count;
    }

    public SearchResult findLine(String fragment) {
        if (fragment.equals(""))
            fragment = "To be, or not to be";
        int numberOfSentences = 0;
        NodeList lineList = root.getElementsByTagName("LINE");
        Element lineElement;
        Node[] lineNodeArray = new Node[lineList.getLength()];

        long lStartTime = System.nanoTime();
        for (int i = 0; i < lineList.getLength(); i++) {
            lineElement = (Element) lineList.item(i);
            if (lineElement.getTextContent().contains(fragment)) {
                lineNodeArray[numberOfSentences] = lineList.item(i);
                numberOfSentences++;
            }
        }
        long lEndTime = System.nanoTime();
        SearchResult result = new SearchResult(lineNodeArray, (lEndTime - lStartTime) / 1000000000.0, numberOfSentences);
        return result;
    }

    public void replaceLine(Node[] lineNodeArray, int lineNumber, String replacement) {
        System.out.println("Enter number of line you want to replace");
        System.out.println("Insert new line");
        lineNodeArray[lineNumber - 1].setTextContent(replacement);
        System.out.println("The sentence has been replaced as follows:\n" +
                lineNodeArray[lineNumber - 1].getTextContent() + "\nDo you want to save changes? (Y/N)");
    }
}