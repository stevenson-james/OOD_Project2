import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        String file = "hamlet.xml";
        Document document = builder.parse(new File(file));

        //Extract root
        Element root = document.getDocumentElement();

        //Find number of personae
        NodeList personaList = root.getElementsByTagName("PERSONA");
        int numPersona = personaList.getLength();
        System.out.println ("There are " + numPersona + " persona");

        //Find number of times a character speaks
        String speakerName = "HAMLET";
        NodeList speakerList = root.getElementsByTagName("SPEAKER");
        int numOfSpeaker = 0;

        for(Node speaker: speakerList)
        {
            numOfSpeaker++;
        }
    }
}
