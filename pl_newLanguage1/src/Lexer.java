import java.util.*;

public class Lexer {

    public static class Token {
        public final String tür;
        public final String değer;

        public Token(String tür, String değer) {
            this.tür = tür;
            this.değer = değer;
        }

        public String toString() {
            return "(" + tür + ", " + değer + ")";
        }
    }

    private static final Set<String> komutlar = Set.of("tanımla", "olursa", "iken", "say", "yaz");

    public static List<Token> ayıkla(String girdi) {
        List<Token> tokenlar = new ArrayList<>();

        // Parçalama: boşluk ve sembol ayrımı
        String[] parçalar = girdi.split("\\s+|(?=[{}();=+\\-*/<>])|(?<=[{}();=+\\-*/<>])");

        for (String parça : parçalar) {
            if (parça.isBlank()) continue;

            if (komutlar.contains(parça)) {
                tokenlar.add(new Token("KOMUT", parça));
            } else if (parça.matches("[0-9]+")) {
                tokenlar.add(new Token("SAYI", parça));
            } else if (parça.matches("\".*\"")) {
                tokenlar.add(new Token("METİN", parça));
            } else if (parça.matches("[a-zA-ZçÇğĞöÖşŞüÜıİ][a-zA-Z0-9çÇğĞöÖşŞüÜıİ]*")) {
                tokenlar.add(new Token("DEĞİŞKEN_ADI", parça));
            } else if ("+-*/%".contains(parça)) {
                tokenlar.add(new Token("İŞLEM", parça));
            } else if (parça.matches("==|!=|<=|>=|<|>")) {
                tokenlar.add(new Token("KARŞILAŞTIRMA", parça));
            } else if (parça.equals("=")) {
                tokenlar.add(new Token("ATAMA", parça));
            } else if (parça.equals(";")) {
                tokenlar.add(new Token("NOKTALI_VİRGÜL", parça));
            } else if (parça.equals(",")) {
                tokenlar.add(new Token("VİRGÜL", parça));
            } else if (parça.equals("(") || parça.equals(")")) {
                tokenlar.add(new Token("PARANTEZ", parça));
            } else if (parça.equals("{") || parça.equals("}")) {
                tokenlar.add(new Token("SÜSLÜ_PARANTEZ", parça));
            } else {
                tokenlar.add(new Token("BİLİNMEYEN", parça));
            }
        }

        return tokenlar;
    }

    public static void main(String[] args) {
        String kod = """
            tanımla x;
            x = 5 + 3;
            olursa (x > 0) {
                yaz "pozitif";
            }
        """;

        List<Token> sonuç = ayıkla(kod);
        for (Token t : sonuç) {
            System.out.println(t);
        }
    }
}
