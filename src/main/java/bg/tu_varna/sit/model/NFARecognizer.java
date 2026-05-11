package bg.tu_varna.sit.model;

import java.util.*;

public class NFARecognizer {

    public static boolean recognize(NFA nfa, String word) {
        Set<State> currentStates = epsilonClosure(nfa, Set.of(nfa.getStartState()));

        for (char c : word.toCharArray()) {
            currentStates = move(nfa, currentStates, c);
            currentStates = epsilonClosure(nfa, currentStates);
            if (currentStates.isEmpty()) {
                return false;
            }
        }

        for (State s : currentStates) {
            if (s.isFinal()) {
                return true;
            }
        }
        return false;
    }

    public static Set<State> epsilonClosure(NFA nfa, Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            State current = stack.pop();
            for (Transition t : nfa.getTransitions()) {
                if (t.getFrom().equals(current) && t.isEpsilon()) {
                    if (!closure.contains(t.getTo())) {
                        closure.add(t.getTo());
                        stack.push(t.getTo());
                    }
                }
            }
        }
        return closure;
    }

    public static Set<State> move(NFA nfa, Set<State> states, char c) {
        Set<State> result = new HashSet<>();
        for (State s : states) {
            for (Transition t : nfa.getTransitions()) {
                if (t.getFrom().equals(s) && !t.isEpsilon() && t.getSymbol() == c) {
                    result.add(t.getTo());
                }
            }
        }
        return result;
    }
}