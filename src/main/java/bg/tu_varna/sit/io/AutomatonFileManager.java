package bg.tu_varna.sit.io;

import bg.tu_varna.sit.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class AutomatonFileManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static void saveToFile(NFA nfa, String filePath) throws IOException {
        NFAWrapper wrapper = convertToWrapper(nfa);
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(wrapper, writer);
        }
    }


    public static NFA loadFromFile(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            NFAWrapper wrapper = gson.fromJson(reader, NFAWrapper.class);
            return convertFromWrapper(wrapper);
        }
    }

    private static NFAWrapper convertToWrapper(NFA nfa) {
        NFAWrapper wrapper = new NFAWrapper();
        wrapper.states = new ArrayList<>();
        wrapper.finalStates = new ArrayList<>();


        wrapper.alphabet = new ArrayList<>();
        for (Character c : nfa.getAlphabet()) {
            wrapper.alphabet.add(String.valueOf(c));
        }


        Map<State, String> stateNames = new HashMap<>();
        for (State s : nfa.getStates()) {
            stateNames.put(s, s.getName());
            wrapper.states.add(s.getName());
            if (s.isFinal()) {
                wrapper.finalStates.add(s.getName());
            }
        }
        wrapper.startState = nfa.getStartState().getName();


        wrapper.transitions = new ArrayList<>();
        for (Transition t : nfa.getTransitions()) {
            NFAWrapper.TransitionDTO dto = new NFAWrapper.TransitionDTO();
            dto.from = t.getFrom().getName();
            dto.to = t.getTo().getName();
            dto.symbol = t.isEpsilon() ? null : String.valueOf(t.getSymbol());
            wrapper.transitions.add(dto);
        }
        return wrapper;
    }

    private static NFA convertFromWrapper(NFAWrapper wrapper) {
        NFA nfa = new NFA();
        Map<String, State> stateMap = new HashMap<>();


        for (String stateName : wrapper.states) {
            boolean isFinal = wrapper.finalStates != null && wrapper.finalStates.contains(stateName);
            State state = new State(stateName, isFinal);
            stateMap.put(stateName, state);
            nfa.addState(state);
            if (wrapper.startState.equals(stateName)) {
                nfa.setStartState(state);
            }
        }




        if (wrapper.transitions != null) {
            for (NFAWrapper.TransitionDTO dto : wrapper.transitions) {
                State from = stateMap.get(dto.from);
                State to = stateMap.get(dto.to);
                Character symbol = (dto.symbol == null || dto.symbol.equals("null") || dto.symbol.isEmpty())
                        ? null
                        : dto.symbol.charAt(0);
                nfa.addTransition(new Transition(from, to, symbol));
            }
        }

        return nfa;
    }
}