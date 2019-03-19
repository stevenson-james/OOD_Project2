import org.w3c.dom.Node;

public class SearchResult {
    private Node[] lineNodeArray;
    private double timeToSearch;
    private int numberOfSentences;

    SearchResult(){
        lineNodeArray = new Node[0];
        timeToSearch = -1;
        numberOfSentences = 0;
    }
    SearchResult(Node[] lineNodeArray, double timeToSearch, int numberOfSentences) {
        this.lineNodeArray = lineNodeArray;
        this.timeToSearch = timeToSearch;
        this.numberOfSentences = numberOfSentences;
    }

    public Node[] getLineNodeArray(){
        return lineNodeArray;
    }

    public double getTimeToSearch() {
        return timeToSearch;
    }

    public int getNumberOfSentences() {
        return numberOfSentences;
    }
}
