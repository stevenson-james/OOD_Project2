import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Scanner input = new Scanner(System.in);
        String file = "Shakespeare/hamlet.xml";
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(file));
            //Extract root
            Element root = document.getDocumentElement();

            //Find number of personae
            NodeList personaList = root.getElementsByTagName("PERSONA");
            int numPersona = personaList.getLength();
            System.out.println ("There are " + numPersona + " persona");

            //Find number of times a character speaks
            String checkedName = "HAMLET";
            int count = 0;
            NodeList speakerList = root.getElementsByTagName("SPEAKER");
            Element speakerElement;
            Node speakerNode;
            for (int i = 0; i < speakerList.getLength(); i++)
            {
                speakerNode = speakerList.item(i);
                speakerElement = (Element) speakerNode;

                if (checkedName.equals(speakerElement.getTextContent()))
                    count++;
            }
            System.out.println (checkedName + " speaks " + count + " times ");

            //Find line of text from uer input
            String fragment = input.nextLine();
            NodeList lineList = root.getElementsByTagName("LINE");
            Element lineElement;
            Node lineNode;
            for (int i = 0; i < lineList.getLength(); i++)
            {
                lineNode = lineList.item(i);
                lineElement = (Element) lineNode;

                if (lineElement.getTextContent().contains(fragment))
                    System.out.println(lineElement.getTextContent());
            }

        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
