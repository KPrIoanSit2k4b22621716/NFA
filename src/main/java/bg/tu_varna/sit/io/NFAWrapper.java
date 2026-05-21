package bg.tu_varna.sit.io;

import java.util.List;

public class NFAWrapper {
    public List<String> states;
    public String startState;
    public List<String> finalStates;
    public List<TransitionInfo> transitions;
    public List<String> alphabet;

    public static class TransitionInfo {
        public String from;
        public String to;
        public String symbol;
    }
}