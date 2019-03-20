import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Display extends Application {

    private FlowPane pane = new FlowPane();
    private Button openBt = new Button("Choose File");
    private Button personaBt = new Button ("Find Number of Persona in Play");
    private final TextField speakerTF = new TextField();
    private Button speakerBt = new Button ("Find Number of Lines");
    private final TextField searchLineTF = new TextField();
    private Button searchLineBt = new Button ("Search for Line");
    private final FileChooser fileChooser = new FileChooser();
    private File file = new File("Shakespeare/hamlet.xml");
    private Text fileDisplay = new Text(60, 60, " File name: " + file.getName());
    private XMLDocument document = new XMLDocument(file);
    private Text output = new Text(60, 60, "");
    private SearchResult searchResult = new SearchResult();
    private TextField replaceTF = new TextField();
    private Button replaceBt = new Button("Replace Line");
    private TextField lineNumTF = new TextField();

    public void start(Stage primaryStage) throws Exception {
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setHgap(5);
        pane.setVgap(5);

        speakerTF.setPromptText("Insert name of speaker (Default: 'HAMLET')");
        speakerTF.setPrefColumnCount(25); //sets width of text field

        searchLineTF.setPromptText("Insert line (Default: 'To be, or not to be')");
        searchLineTF.setPrefColumnCount(25); //sets width of text field

        replaceTF.setPromptText("Insert replacement for line");
        replaceTF.setPrefColumnCount(25);

        lineNumTF.setPromptText("Insert line number to replace (Default: '1')");
        lineNumTF.setPrefColumnCount(25);

        pane.getChildren().add(openBt);
        pane.getChildren().add(fileDisplay);
        pane.getChildren().add(personaBt);
        pane.getChildren().add(speakerTF);
        pane.getChildren().add(speakerBt);
        pane.getChildren().add(searchLineTF);
        pane.getChildren().add(searchLineBt);
        pane.getChildren().add(output);

        Scene scene = new Scene(pane, 500, 300);
        primaryStage.setTitle("MyJavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();


        openBt.setOnAction(e -> chooseFile(primaryStage));
        personaBt.setOnAction(e -> findNumberOfPersona());
        speakerBt.setOnAction(e -> findNumberOfSpeaker());
        searchLineBt.setOnAction(e -> findLine());
        replaceBt.setOnAction(e -> replaceLine());
    }

    public void chooseFile(Stage primaryStage){
        file = fileChooser.showOpenDialog(primaryStage);
        fileDisplay.setText(" File name: " + file.getName());
        document.setXMLDocument(file);
    }

    public void findNumberOfPersona(){
        output.setText("There are " + document.findNumberOfPersona() + " personae in " + file.getName());
    }

    public void findNumberOfSpeaker() {
        String speaker;
        if (speakerTF.getText().equals(""))
            speaker = "HAMLET";
        else
            speaker = speakerTF.getText().toUpperCase();
        int count = document.findNumberOfSpeaker(speaker);
        output.setText(speaker + " speaks " + count + " times");
    }

    public void findLine() {
        searchResult = document.findLine(searchLineTF.getText());
        if (searchResult.getNumberOfSentences() == 0)
            output.setText("Sorry, fragment not found.\n");
        else {
            output.setText("The fragment has been found in the following sentence(s):\n");
            for (int i = 0; i < searchResult.getNumberOfSentences(); i++)
                output.setText(output.getText() + (i + 1) + ". '" + searchResult.getLineNodeArray()[i].getTextContent() + "'\n");
        }
        output.setText(output.getText() + "Search performed in " + searchResult.getTimeToSearch() + " seconds\n");
        if (searchResult.getNumberOfSentences() > 0) {
            pane.getChildren().add(replaceTF);
            pane.getChildren().add(replaceBt);
        }
    }

    public void replaceLine(){
        //if statement for empty/too large lineNum
        //static cast String to int
        int num = 1; //(int) lineNumTF.getText()
        String previousLine = searchResult.getLineNodeArray()[num - 1].getTextContent();
        output.setText("'" + previousLine + "' will be replaced with: \n'" + replaceTF.getText() + "'\n" +
                "Do you want to save the changes?");
        //conditional for yes or no
        //if yes
            searchResult.getLineNodeArray()[num - 1].setTextContent(replaceTF.getText());
        pane.getChildren().removeAll(replaceTF, replaceBt, lineNumTF);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}