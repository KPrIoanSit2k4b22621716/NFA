package bg.tu_varna.sit.model;

import java.util.*;

public class NFA {
    private Set<State> states = new HashSet<>();
    private Set<Transition> transitions = new HashSet<>();
    private State startState;
    private Set<State> finalStates = new HashSet<>();
    private Set<Character> alphabet = new HashSet<>();

    public void addState(State state) {
        states.add(state);
        if (state.isFinal()) {
            finalStates.add(state);
        }
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
        states.add(transition.getFrom());
        states.add(transition.getTo());

        if (transition.getSymbol() != null) {
            alphabet.add(transition.getSymbol());
        }

        if (transition.getTo().isFinal()) {
            finalStates.add(transition.getTo());
        }
        if (transition.getFrom().isFinal()) {
            finalStates.add(transition.getFrom());
        }
    }


    public void setStartState(State startState) { this.startState = startState; }
    public State getStartState() { return startState; }
    public Set<State> getStates() { return states; }
    public Set<Transition> getTransitions() { return transitions; }
    public Set<State> getFinalStates() { return finalStates; }
    public Set<Character> getAlphabet() { return alphabet; }

    @Override
    public String toString() {
        return "NFA: " + states.size() + " states, " + transitions.size() + " transitions";
    }
}