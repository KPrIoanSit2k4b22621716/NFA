package bg.tu_varna.sit.model;

public class Transition {
    private State from;
    private State to;
    private Character symbol;

    public Transition(State from, State to, Character symbol) {
        this.from = from;
        this.to = to;
        this.symbol = symbol;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public Character getSymbol() {
        return symbol;
    }

    public boolean isEpsilon() {
        return symbol == null;
    }

    @Override
    public String toString() {
        if (isEpsilon()) {
            return from.getName() + ": ε -> " + to.getName();
        }
        return from.getName() + ": " + symbol + " -> " + to.getName();
    }
}