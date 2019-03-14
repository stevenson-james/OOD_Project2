import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import java.io.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class XMLDocument {
    Document document;
    Element root;
    Scanner input;


    XMLDocument(){
        try{
            input = new Scanner(System.in);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String file = getFile();
            document = builder.parse(new File(file));
            root = document.getDocumentElement();
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

    public String getFile(){
        String file = "Shakespeare/hamlet.xml";
        System.out.println("Enter file name (ex: Shakespeare/hamlet.xml): ");
        file = input.nextLine();
        return file;
    }

    public void findPersona(){
        NodeList personaList = root.getElementsByTagName("PERSONA");
        int numPersona = personaList.getLength();
        System.out.println ("There are " + numPersona + " persona");
    }

    public void findNumberOfSpeaker(){
        System.out.println("Insert speaker name to find number of times they speak:");
        String checkedName = "HAMLET";
        checkedName = input.nextLine();
        checkedName = checkedName.toUpperCase();
        int count = 0;
        NodeList speakerList = root.getElementsByTagName("SPEAKER");
        Element speakerElement;
        for (int i = 0; i < speakerList.getLength(); i++)
        {
            speakerElement = (Element) speakerList.item(i);

            if (checkedName.equals(speakerElement.getTextContent()))
                count++;
        }
        System.out.println (checkedName + " speaks " + count + " times ");
    }

    public Node[] findLine(){
        System.out.println("Insert fragment to be found:");
        String fragment = "To be, or not to be";
        fragment = input.nextLine();
        int numberOfSentences = 0;
        boolean isFound = false;
        NodeList lineList = root.getElementsByTagName("LINE");
        Element lineElement;
        Node[] lineNodeArray = new Node[lineList.getLength()];

        long lStartTime = System.nanoTime();
        for (int i = 0; i < lineList.getLength(); i++)
        {
            lineElement = (Element) lineList.item(i);

            if (lineElement.getTextContent().contains(fragment)) {
                lineNodeArray[numberOfSentences] = lineList.item(i);
                numberOfSentences++;
                isFound = true;
            }
        }
        long lEndTime = System.nanoTime();
        if (!isFound)
            System.out.println("Sorry, fragment not found.");
        System.out.println("Search performed in " + ((lEndTime - lStartTime)/1000000000.0) + " seconds");
        if (isFound) {
            System.out.println("The fragment has been found in the following sentence(s):");
            for (int i = 0; i < numberOfSentences; i++)
                System.out.println("'" + lineNodeArray[i].getTextContent() + "'");
            System.out.println("Do you want to replace it? (Y/N)");
        }
        return lineNodeArray;
    }

    public void replaceText(Node[] lineNodeArray){
        System.out.println("Enter number of line you want to replace");
        int lineNumber = input.nextInt();
        input.nextLine();
        System.out.println("Insert new line");
        String replacement = input.nextLine();
        lineNodeArray[lineNumber - 1].setTextContent(replacement);
        System.out.println("The sentence has been replaced as follows:\n" +
                lineNodeArray[lineNumber - 1].getTextContent() +"\nDo you want to save changes? (Y/N)");
    }
}
