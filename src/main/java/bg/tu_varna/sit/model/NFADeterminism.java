package bg.tu_varna.sit.model;

import java.util.*;

public class NFADeterminism {

    public static boolean isDeterministic(NFA nfa) {
        for (Transition t : nfa.getTransitions()) {
            if (t.isEpsilon()) {
                return false;
            }
        }

        for (State s : nfa.getStates()) {
            Set<Character> seenSymbols = new HashSet<>();
            for (Transition t : nfa.getTransitions()) {
                if (t.getFrom().equals(s)) {
                    char symbol = t.getSymbol();
                    if (seenSymbols.contains(symbol)) {
                        return false;
                    }
                    seenSymbols.add(symbol);
                }
            }
        }
        return true;
    }

    public static NFA determinize(NFA nfa) {
        NFA dfa = new NFA();
        Map<Set<State>, State> dfaStates = new HashMap<>();

        Set<State> startSet = NFARecognizer.epsilonClosure(nfa, Set.of(nfa.getStartState()));
        State dfaStart = new State("D{" + stateSetToString(startSet) + "}", containsFinal(startSet));
        dfa.addState(dfaStart);
        dfa.setStartState(dfaStart);
        dfaStates.put(startSet, dfaStart);

        Queue<Set<State>> queue = new LinkedList<>();
        queue.add(startSet);

        while (!queue.isEmpty()) {
            Set<State> currentSet = queue.poll();
            State currentState = dfaStates.get(currentSet);

            for (char c : nfa.getAlphabet()) {
                Set<State> nextSet = NFARecognizer.epsilonClosure(nfa, NFARecognizer.move(nfa, currentSet, c));

                if (nextSet.isEmpty()) continue;

                if (!dfaStates.containsKey(nextSet)) {
                    State newState = new State("D{" + stateSetToString(nextSet) + "}", containsFinal(nextSet));
                    dfa.addState(newState);
                    dfaStates.put(nextSet, newState);
                    queue.add(nextSet);
                }

                State nextState = dfaStates.get(nextSet);
                dfa.addTransition(new Transition(currentState, nextState, c));
            }
        }
        return dfa;
    }

    private static boolean containsFinal(Set<State> states) {
        for (State s : states) {
            if (s.isFinal()) return true;
        }
        return false;
    }

    private static String stateSetToString(Set<State> states) {
        List<String> names = new ArrayList<>();
        for (State s : states) {
            names.add(s.getName());
        }
        Collections.sort(names);
        return String.join(",", names);
    }
}