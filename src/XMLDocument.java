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


/**
 * This class creates a new XML document of a
 * Shakespeare play and parses it, asking for user input
 * and rewriting it into a new file
 * @author James Stevenson and Matt Zech
 *
 */
public class XMLDocument {
    Document document;
    Element root;
	
	/**
	 * Importing XML Doc
	 * @param file input file name for XML document
	 */
    XMLDocument(File file) {
        setXMLDocument(file);
    }
    /**
     * Creates new XML document
     */
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
    /**
     * Finds number of different characters in play
     * @return integer representing speakers in the play
     */

    public int findNumberOfPersona() {
        NodeList personaList = root.getElementsByTagName("PERSONA");
        int numPersona = personaList.getLength();
        return numPersona;
    }
	/**
	 * Finds how many times the user-specified speaker
	 * talks during the play
	 * @param checkedName automatically converts it to upper case
	 * @return number of lines in the play
	 */
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
    /**
     * Searches the play for a user-specified word or phrase
     * @param fragment used for what to search for
     * @return list of lines where the phrase is spoken
     */
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
}

