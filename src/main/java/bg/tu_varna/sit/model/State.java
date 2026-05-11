package bg.tu_varna.sit.model;

public class State {
    private String name;
    private boolean isFinal;

    public State(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        return name + (isFinal ? "[FINAL]" : "");
    }
}