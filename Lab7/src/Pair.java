import java.util.Objects;

public class Pair<T, S> {

    public T first;

    public S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {

    }

    public Pair<T, S> of(Pair<T, S> that) {
        return new Pair<>(that.first, that.second);
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public String toString() {
        return first + "->"+second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
