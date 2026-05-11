package bg.tu_varna.sit.model;

public class RegexToNFA {

    public static NFA fromRegex(String regex) {
        RegexParser parser = new RegexParser(regex);
        return parser.parseExpression();
    }

    private static class RegexParser {
        private final String input;
        private int pos;

        RegexParser(String input) {
            this.input = input;
            this.pos = 0;
        }

        private char peek() { return pos < input.length() ? input.charAt(pos) : '\0'; }
        private char consume() { return input.charAt(pos++); }

        private boolean matches(char c) {
            if (peek() == c) {
                pos++;
                return true;
            }
            return false;
        }

        NFA parseExpression() {
            NFA left = parseTerm();
            while (matches('|')) {
                NFA right = parseTerm();
                left = NFAOperations.union(left, right);
            }
            return left;
        }

        NFA parseTerm() {
            NFA result = parseFactor();
            while (peek() != '\0' && peek() != '|' && peek() != ')') {
                NFA next = parseFactor();
                result = NFAOperations.concat(result, next);
            }
            return result;
        }

        NFA parseFactor() {
            NFA atom = parseAtom();
            if (matches('*')) {
                return NFAOperations.star(atom);
            }
            return atom;
        }

        NFA parseAtom() {
            if (matches('(')) {
                NFA expr = parseExpression();
                if (!matches(')')) {
                    throw new RuntimeException("Missing closing parenthesis");
                }
                return expr;
            }

            char c = consume();
            if (c == '\0') {
                throw new RuntimeException("Unexpected end of regex");
            }

            NFA nfa = new NFA();
            State start = new State("s0", false);
            State end = new State("s1", true);
            nfa.addState(start);
            nfa.addState(end);
            nfa.setStartState(start);
            nfa.addTransition(new Transition(start, end, c));
            return nfa;
        }
    }
}