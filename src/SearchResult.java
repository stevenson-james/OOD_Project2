import org.w3c.dom.Node;

public class SearchResult {
    private Node[] lineNodeArray;
    private double timeToSearch;
    private int numberOfSentences;
    /**
     * This class stores necessary variables
     * for the results from a search of the
     * XML document
     * @author James Stevenson and Matt Zech
     *
     */
    SearchResult(){
        lineNodeArray = new Node[0];
        timeToSearch = -1;
        numberOfSentences = 0;
    }

    /**
     * Constructs SearchResult passing in
     * parameters to be set
     * @param lineNodeArray node array of lines found
     * @param timeToSearch time taken to search in seconds
     * @param numberOfSentences number of sentences found in document
     */
    SearchResult(Node[] lineNodeArray, double timeToSearch, int numberOfSentences) {
        this.lineNodeArray = lineNodeArray;
        this.timeToSearch = timeToSearch;
        this.numberOfSentences = numberOfSentences;
    }

    /**
     * Returns lineNodeArray
     * @return node array of lines found
     */
    public Node[] getLineNodeArray(){
        return lineNodeArray;
    }

    /**
     * Returns time taken to make search
     * @return time taken to search in seconds
     */
    public double getTimeToSearch() {
        return timeToSearch;
    }

    /**
     * Returns number of sentences found
     * @return number of sentences found in document
     */
    public int getNumberOfSentences() {
        return numberOfSentences;
    }
}
