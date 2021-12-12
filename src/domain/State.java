package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {
    public List<Pair<String, String>> productions;

    public State() {
        productions = new ArrayList<>();
    }

    public State(List<Pair<String,String>> productions) {
        this.productions = productions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(productions, state.productions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productions);
    }

    @Override
    public String toString() {
        return productions.toString();
    }
}
