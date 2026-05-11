package bg.tu_varna.sit.model;

import java.util.*;

public class NFAOperations {

    public static NFA union(NFA first, NFA second) {
        NFA result = new NFA();


        Map<State, State> stateMap1 = copyFromWithPrefix(first, result, "A_");

        Map<State, State> stateMap2 = copyFromWithPrefix(second, result, "B_");


        State newStart = new State("union_start", false);
        result.addState(newStart);
        result.setStartState(newStart);


        State firstStart = stateMap1.get(first.getStartState());
        State secondStart = stateMap2.get(second.getStartState());
        result.addTransition(new Transition(newStart, firstStart, null));
        result.addTransition(new Transition(newStart, secondStart, null));

        return result;
    }

    public static NFA concat(NFA first, NFA second) {
        NFA result = new NFA();

        Map<State, State> stateMap1 = copyFromWithPrefix(first, result, "A_");
        Map<State, State> stateMap2 = copyFromWithPrefix(second, result, "B_");

        State firstStart = stateMap1.get(first.getStartState());
        result.setStartState(firstStart);

        State secondStart = stateMap2.get(second.getStartState());


        for (State s : first.getFinalStates()) {
            State mappedFinal = stateMap1.get(s);
            mappedFinal.setFinal(false);
            result.addTransition(new Transition(mappedFinal, secondStart, null));
        }


        for (State s : second.getFinalStates()) {
            State mappedFinal = stateMap2.get(s);
            mappedFinal.setFinal(true);
        }

        return result;
    }

    public static NFA star(NFA nfa) {
        NFA result = new NFA();

        Map<State, State> stateMap = copyFromWithPrefix(nfa, result, "");

        State newStart = new State("star_start", true);
        result.addState(newStart);
        result.setStartState(newStart);

        State originalStart = stateMap.get(nfa.getStartState());
        result.addTransition(new Transition(newStart, originalStart, null));

        for (State s : nfa.getFinalStates()) {
            State mappedFinal = stateMap.get(s);
            result.addTransition(new Transition(mappedFinal, originalStart, null));
        }

        return result;
    }

    public static boolean isEmptyLanguage(NFA nfa) {
        Set<State> reachable = NFARecognizer.epsilonClosure(nfa, Set.of(nfa.getStartState()));

        boolean changed;
        do {
            changed = false;
            Set<State> newReachable = new HashSet<>(reachable);
            for (State s : reachable) {
                for (char c : nfa.getAlphabet()) {
                    Set<State> next = NFARecognizer.move(nfa, Set.of(s), c);
                    for (State ns : next) {
                        if (!reachable.contains(ns)) {
                            newReachable.add(ns);
                            changed = true;
                        }
                    }
                }
            }
            reachable = newReachable;
        } while (changed);

        for (State s : reachable) {
            if (s.isFinal()) return false;
        }
        return true;
    }


    private static Map<State, State> copyFromWithPrefix(NFA source, NFA destination, String prefix) {
        Map<State, State> stateMap = new HashMap<>();

        for (State s : source.getStates()) {
            String newName = prefix + s.getName();
            State newState = new State(newName, s.isFinal());
            destination.addState(newState);
            stateMap.put(s, newState);
        }

        for (Transition t : source.getTransitions()) {
            State newFrom = stateMap.get(t.getFrom());
            State newTo = stateMap.get(t.getTo());
            destination.addTransition(new Transition(newFrom, newTo, t.getSymbol()));
        }

        return stateMap;
    }
}