import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import java.io.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {
        XMLDocument document = new XMLDocument();
        document.findPersona();
        document.findNumberOfSpeaker();
        Node[] lineNodeArray = document.findLine();
        document.replaceText(lineNodeArray);
    }
}