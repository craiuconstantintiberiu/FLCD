import java.util.Optional;

public class SymbolTable {

    private int capacity;
    private int numberElements;
    private float growRate;
    private float growCriteria;
    private String[] elements;

    public SymbolTable(int capacity, float growRate, float growCriteria) {
        this.capacity = capacity;
        this.growRate = growRate;
        this.numberElements = 0;
        this.growCriteria = growCriteria;
        this.elements = new String[capacity];
    }

    public int add(String elementToAdd) {
        // if cannot add return -2

        return hashElementToArray(elementToAdd, elements, capacity);
    }

    private int hashElementToArray(String elementToAdd, String[] elemsToHashTo, int hashTableSize) {
        int computedHash = computeHash(elementToAdd);
        int positionToInsertElement = findFirstEmptyPosition(computedHash);

        for (int pos = computedHash; pos < hashTableSize; pos++) {
            if (isPositionFilled(pos) && getElementOnPosition(pos).get().equals(elementToAdd)) {
                return pos;
            }

            if (isPositionEmpty(pos)) {
                elemsToHashTo[pos] = elementToAdd;
                numberElements++;
                return pos;
            }
        }

        for (int pos = 0; pos < computedHash; pos++) {
            if (isPositionFilled(pos) && getElementOnPosition(pos).get().equals(elementToAdd)) {
                return pos;
            }

            if (isPositionEmpty(pos)) {
                elemsToHashTo[pos] = elementToAdd;
                numberElements++;
                return pos;
            }
        }

        if (getElementOnPosition(positionToInsertElement).get().equals(elementToAdd)) {
            return positionToInsertElement;
        }

        return -2;
    }

    public int find(String elementToFind) {
        //Returns position, returns -2 if nothing found.

        int computedHash = computeHash(elementToFind);

        if (isPositionFilled(computedHash) && getElementOnPosition(computedHash).get().equals(elementToFind)) {
            return computedHash;
        }

        for (int pos = computedHash; pos < capacity; pos++) {
            if (isPositionFilled(pos) && getElementOnPosition(pos).get().equals(elementToFind)) {
                return pos;
            }
        }

        for (int pos = 0; pos < computedHash; pos++) {
            if (isPositionFilled(pos) && getElementOnPosition(pos).get().equals(elementToFind)) {
                return pos;
            }
        }

        return -2;
    }

    public boolean contains(String element){
        return find(element)!=-2;
    }

    private int findFirstEmptyPosition(int start) {
        //Returns -2 if no empty position. Should not happen though.
        for (int pos = start; pos < capacity; pos++) {
            if (isPositionEmpty(pos)) {
                return pos;
            }
        }

        for (int pos = 0; pos < start; pos++) {
            if (isPositionEmpty(pos)) {
                return pos;
            }
        }

        return -2;
    }

    private boolean isPositionFilled(int position) {
        return getElementOnPosition(position).isPresent();
    }

    private boolean isPositionEmpty(int position) {
        return getElementOnPosition(position).isEmpty();
    }

    private Optional<String> getElementOnPosition(int position) {
        return elements[position] == null ? Optional.empty() : Optional.of(elements[position]);
    }

    private int computeHash(String toBeAdded) {
        return toBeAdded.hashCode() % capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getGrowRate() {
        return growRate;
    }

    public void setGrowRate(float growRate) {
        this.growRate = growRate;
    }

    public float getGrowCriteria() {
        return growCriteria;
    }

    public void setGrowCriteria(float growCriteria) {
        this.growCriteria = growCriteria;
    }

}
