import java.util.*;

public class Lexer {

    public static class Token {
        public final String type;
        public final String value;

        public Token(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String toString() {
            return "(" + type + ", " + value + ")";
        }
    }

    private static final Set<String> keywords = Set.of("tanımla", "olursa", "iken", "sayaç", "yaz");

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        String[] parts = input.split("\\s+|(?=[{}();=+\\-*/<>])|(?<=[{}();=+\\-*/<>])");

        for (String part : parts) {
            if (part.isBlank()) continue;

            if (keywords.contains(part)) {
                tokens.add(new Token("KEYWORD", part));
            } else if (part.matches("[0-9]+")) {
                tokens.add(new Token("NUMBER", part));
            } else if (part.matches("[a-zA-ZçÇğĞöÖşŞüÜıİ][a-zA-Z0-9çÇğĞöÖşŞüÜıİ]*")) {
                tokens.add(new Token("IDENTIFIER", part));
            } else if (part.equals(";")) {
                tokens.add(new Token("SEMICOLON", part));
            } else if ("+-*/".contains(part)) {
                tokens.add(new Token("OPERATOR", part));
            } else if (part.equals("=")) {
                tokens.add(new Token("ASSIGN", part));
            } else {
                tokens.add(new Token("UNKNOWN", part));
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String code = "tanımla x; x = 5 + 3; yaz x;";
        List<Token> result = tokenize(code);
        for (Token token : result) {
            System.out.println(token);
        }
    }
}
