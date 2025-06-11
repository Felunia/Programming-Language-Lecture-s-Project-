import java.util.*;

public class parser {
    private List<String> tokens;
    private int currentTokenIndex = 0;

    public parser(String input) {
        tokens = Arrays.asList(input.replace("(", " ( ")
                                    .replace(")", " ) ")
                                    .replace(";", " ; ")
                                    .split("\\s+"));
    }

    public void parse() {
        parseProgram();
    }

    private void parseProgram() {
        if (match("ise")) {
            match("(");
            parseCondition();
            match(")");
            match("yap");
            parseStatement();
            match("bitir");
            System.out.println("Doğru yapı: if koşulu bulundu.");
        } else {
            error("ise ile başlamalı");
        }
    }

    private void parseCondition() {
        String left = nextToken();
        String operator = nextToken();
        String right = nextToken();
        System.out.println("Koşul: " + left + " " + operator + " " + right);
    }

    private void parseStatement() {
        String var = nextToken();
        match("=");
        String value = nextToken();
        match(";");
        System.out.println("Atama: " + var + " = " + value);
    }

    private boolean match(String expected) {
        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).equals(expected)) {
            currentTokenIndex++;
            return true;
        } else {
            error("Beklenen: '" + expected + "', ama bulunan: '" + peek() + "'");
            return false;
        }
    }

    private String nextToken() {
        return currentTokenIndex < tokens.size() ? tokens.get(currentTokenIndex++) : "";
    }

    private String peek() {
        return currentTokenIndex < tokens.size() ? tokens.get(currentTokenIndex) : "EOF";
    }

    private void error(String message) {
        throw new RuntimeException("Sözdizimi hatası: " + message);
    }

    public static void main(String[] args) {
        String input = "ise ( x > 5 ) yap x = 3 ; bitir";
        parser parser = new parser(input);
        parser.parse();
    }
}
