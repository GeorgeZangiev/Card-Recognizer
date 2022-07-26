import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CardRecognizer cardRecognizer = new CardRecognizer();
        cardRecognizer.readCards();
    }
}