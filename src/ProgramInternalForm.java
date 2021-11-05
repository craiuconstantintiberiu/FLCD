import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {

    private List<Pair<String, Integer>> elementsAndPositionsInSymbolTable;

    public ProgramInternalForm() {
        elementsAndPositionsInSymbolTable = new ArrayList<>();
    }

    public void addElement(String element, Integer position) {
        elementsAndPositionsInSymbolTable.add(new Pair<>(element, position));
    }

    public List<Pair<String, Integer>> getElementsAndPositionsInSymbolTable() {
        return elementsAndPositionsInSymbolTable;
    }
}
