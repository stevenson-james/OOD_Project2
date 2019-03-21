import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


/**
 * GUI program for XML Parsing
 * @author James Stevenson and Matt Zech
 *
 */
public class Display extends Application {

    private FlowPane pane = new FlowPane();
    private Button openBt = new Button("Choose Input File");
    private Button personaBt = new Button ("Find Number of Persona in Play");
    private final TextField speakerTF = new TextField();
    private Button speakerBt = new Button ("Find Number of Lines");
    private final TextField searchLineTF = new TextField();
    private Button searchLineBt = new Button ("Search for Line");
    private final FileChooser fileChooser = new FileChooser();
    private File file = new File("Shakespeare/hamlet.xml");
    private Text fileDisplay = new Text(60, 60, " Input file name: " + file.getName());
    private XMLDocument xmlDocument = new XMLDocument(file);
    private Text output = new Text(60, 60, "");
    private SearchResult searchResult = new SearchResult();
    private TextField replaceTF = new TextField();
    private Button replaceBt = new Button("Replace Line");
    private TextField lineNumTF = new TextField();
    private Button exitReplaceBt = new Button ("Do Not Replace Line");
    private Button yesReplaceBt = new Button ("Yes");
    private Button noReplaceBt = new Button ("No");
    private int replaceNum = 1;
    private File outputFile = new File ("Shakespeare/hamlet.xml");
    private TextField outputFileTF = new TextField();
    private Button saveFileBt = new Button ("Save File");
    private Button yesSaveBt = new Button ("Yes");
    private Button noSaveBt = new Button ("No");
    private String newLine = "";

    /**
     * Creates buttons and text fields on the stage and calls
     * respective methods on action
     */
    public void start(Stage primaryStage) throws Exception {
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setHgap(5);
        pane.setVgap(5);

        outputFileTF.setPromptText("Insert name of output file (Default: 'hamlet.xml)'");
        outputFileTF.setPrefColumnCount(40);

        speakerTF.setPromptText("Insert name of speaker (Default: 'HAMLET')");
        speakerTF.setPrefColumnCount(25); //sets width of text field

        searchLineTF.setPromptText("Insert line (Default: 'To be, or not to be')");
        searchLineTF.setPrefColumnCount(25); //sets width of text field

        replaceTF.setPromptText("Insert replacement for line");
        replaceTF.setPrefColumnCount(20);

        lineNumTF.setPromptText("Insert line number to replace (Default: '1')");
        lineNumTF.setPrefColumnCount(20);

        pane.getChildren().addAll(openBt, fileDisplay, outputFileTF, saveFileBt, personaBt, speakerTF,
                speakerBt, searchLineTF, searchLineBt, output);

        Scene scene = new Scene(pane, 500, 300);
        primaryStage.setTitle("MyJavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();


        openBt.setOnAction(e -> chooseInputFile(primaryStage));
        personaBt.setOnAction(e -> findNumberOfPersona());
        speakerBt.setOnAction(e -> findNumberOfSpeaker());
        searchLineBt.setOnAction(e -> findLine());
        replaceBt.setOnAction(e -> replaceLine());
        exitReplaceBt.setOnAction(e -> exitReplace());
        yesReplaceBt.setOnAction(e -> yesReplaceLine());
        noReplaceBt.setOnAction(e -> noReplaceLine());
        saveFileBt.setOnAction(e -> checkSaveFile());
        yesSaveBt.setOnAction(e -> yesSaveFile());
        noSaveBt.setOnAction(e -> noSaveFile());
    }

    /**
     * User chooses input file
     * @param primaryStage stage used for displaying GUI
     */
    public void chooseInputFile(Stage primaryStage){
        file = fileChooser.showOpenDialog(primaryStage);
        fileDisplay.setText(" Input file name: " + file.getName());
        xmlDocument.setXMLDocument(file);
    }

    /*public void chooseOutputFile(Stage primaryStage) {
        outputFile = fileChooser.showOpenDialog(primaryStage);
        outputFileDisplay.setText(" Output file name: " + outputFile.getName());
    }
    */

    /**
     * Finds the total number of speakers
     */
    public void findNumberOfPersona(){
        output.setText("There are " + xmlDocument.findNumberOfPersona() + " personae in " + file.getName());
    }

    public void findNumberOfSpeaker() {
        String speaker;
        if (speakerTF.getText().equals(""))
            speaker = "HAMLET";
        else
            speaker = speakerTF.getText().toUpperCase();
        int count = xmlDocument.findNumberOfSpeaker(speaker);
        output.setText(speaker + " speaks " + count + " times");
    }

    /**
     * Finds how many lines a certain phrase is included in
     */
    public void findLine() {
        searchResult = xmlDocument.findLine(searchLineTF.getText());
        if (searchResult.getNumberOfSentences() == 0)
            output.setText("Sorry, fragment not found.\n");
        else {
            output.setText("The fragment has been found in the following sentence(s):\n");
            for (int i = 0; i < searchResult.getNumberOfSentences(); i++)
                output.setText(output.getText() + (i + 1) + ". '" + searchResult.getLineNodeArray()[i].getTextContent() + "'\n");
        }
        output.setText(output.getText() + "Search performed in " + searchResult.getTimeToSearch() + " seconds\n");
        if (searchResult.getNumberOfSentences() > 0) {
            pane.getChildren().addAll(replaceTF, replaceBt, exitReplaceBt, lineNumTF);
        }
    }

    /**
     * Prompts the user to replace the line(s) with a specified phrase
     */
    public void replaceLine(){
        if (!lineNumTF.getText().equals("") && Integer.parseInt(lineNumTF.getText()) <= searchResult.getNumberOfSentences() &&
                Integer.parseInt(lineNumTF.getText()) > 0)
            replaceNum = Integer.parseInt(lineNumTF.getText());
        String previousLine = searchResult.getLineNodeArray()[replaceNum - 1].getTextContent();
        newLine = searchResult.getLineNodeArray()[replaceNum - 1].getTextContent().replace(searchLineTF.getText(), replaceTF.getText());
        output.setText("'" + previousLine + "' will be replaced with: \n'" + newLine + "'\n" +
                "Do you want to save the changes? (Y/N)");
        pane.getChildren().addAll(yesReplaceBt, noReplaceBt);
        pane.getChildren().removeAll(replaceTF, replaceBt, exitReplaceBt, lineNumTF);
    }

    /**
     * Exits the replacement section
     */
    public void exitReplace(){
        pane.getChildren().removeAll(replaceTF, replaceBt, exitReplaceBt, lineNumTF);
        output.setText("");
    }

    /**
     * Runs if the user selects yes
     */
    public void yesReplaceLine(){
        searchResult.getLineNodeArray()[replaceNum - 1].setTextContent(newLine);
        pane.getChildren().removeAll(yesReplaceBt, noReplaceBt);
        output.setText("");
    }

    /**
     * Removes the replacement section and does not run
     */
    public void noReplaceLine(){
        pane.getChildren().removeAll(yesReplaceBt, noReplaceBt);
        findLine();
    }

    /**
     * Checks to see if an overwrite is necessary
     * Then prompts the user to overwrite the file
     */
    public void checkSaveFile(){
        if (!outputFileTF.getText().equals(""))
            outputFile = new File(outputFileTF.getText());
        if (file.getName().equals(outputFile.getName()))
        {
            output.setText("Would you like to overwrite file: " + file.getName() + " (Y/N)");
            pane.getChildren().addAll(yesSaveBt, noSaveBt);
        }
        else
            saveFile();
    }
    /**
     * If user wants to overwrite, run
     */
    public void yesSaveFile(){
        saveFile();
        pane.getChildren().removeAll(yesSaveBt, noSaveBt);
    }

    /**
     * If user does not want to overwrite, break
     */
    public void noSaveFile(){
        pane.getChildren().removeAll(yesSaveBt, noSaveBt);
        output.setText("");
    }

    /**
     * Operation for saving the file into the computer
     */
    public void saveFile() {
        boolean success = true;
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(outputFile);
            Source input = new DOMSource(xmlDocument.document);
            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
            success = false;
            output.setText("Incompatible filename");
        }
        if (success)
            output.setText("File saved successfully!");
    }

    /**
     * Launches the display
     * @param args arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
