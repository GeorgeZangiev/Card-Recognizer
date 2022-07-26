import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        recognizeCards("/Users/elmo/Downloads/Тестовое задание Java/imgs_marked");
    }

    private static void recognizeCards(String directoryPath) throws IOException {
        File[] directoryListing = new File(directoryPath).listFiles();
        HashMap<String, List<Coordinates>> cardNames = getCardNames();
        HashMap<String, List<Coordinates>> cardSuits = getCardSuits();
        HashMap<String, Integer> colors = getColors();
        for (File picture: directoryListing) {
            long startTime = System.currentTimeMillis();
            String result = "";
            BufferedImage image = ImageIO.read(picture);
            boolean correctCard = false;
            for (int i = 0; i < 5; i++) {
                int whiteCheck = image.getRGB(175 + 72 * i, 625);
                for (String cardName: cardNames.keySet()) {
                    if (whiteCheck != colors.get("white") && whiteCheck != colors.get("whiteFaded"))
                        break;
                    List<Coordinates> coordinatesList = cardNames.get(cardName);
                    for (Coordinates coordinates: coordinatesList) {
                        int color = image.getRGB(coordinates.width() + 72 * i, coordinates.height());
                        if (color == colors.get("red1") || color == colors.get("red2") ||
                                color == colors.get("red3") || color == colors.get("red4") ||
                                color == colors.get("redFaded1") || color == colors.get("redFaded2") ||
                                color == colors.get("black1") || color == colors.get("black2") ||
                                color == colors.get("black3") || color == colors.get("blackFaded1") ||
                                color == colors.get("blackFaded2")) correctCard = true;
                        else {
                            correctCard = false;
                            break;
                        }
                    }
                    if (correctCard){
                        result += cardName;
                        correctCard = false;
                        break;
                    }
                }
                for (String cardSuit: cardSuits.keySet()) {
                    if (whiteCheck != colors.get("white") && whiteCheck != colors.get("whiteFaded"))
                        break;
                    List<Coordinates> coordinatesList = cardSuits.get(cardSuit);
                    for (Coordinates coordinates: coordinatesList) {
                        int color = image.getRGB(coordinates.width() + 72 * i, coordinates.height());
                        if (color == colors.get("red1") || color == colors.get("redFaded1") ||
                                color == colors.get("black1") || color == colors.get("blackFaded1")) correctCard = true;
                        else {
                            correctCard = false;
                            break;
                        }
                    }
                    if (correctCard) result += cardSuit;
                    correctCard = false;
                }
            }
            System.out.println(picture.getName() + " - " + result);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            System.out.println("reading took " + duration + " milliseconds");
        }
    }

    private static HashMap<String, List<Coordinates>> getCardSuits() {
        HashMap<String, List<Coordinates>> cardSuits = new HashMap<>();
        cardSuits.put("c", List.of(new Coordinates(180, 638),
                new Coordinates(187, 638)));
        cardSuits.put("s", List.of(new Coordinates(177, 645),
                new Coordinates(191, 645), new Coordinates(175, 658),
                new Coordinates(194, 657)));
        cardSuits.put("h", List.of(new Coordinates(184, 662),
                new Coordinates(174, 642), new Coordinates(193, 642)));
        cardSuits.put("d", List.of(new Coordinates(175, 645),
                new Coordinates(192, 645), new Coordinates(192, 653),
                new Coordinates(175, 653), new Coordinates(184, 658),
                new Coordinates(183, 637)));
        return cardSuits;
    }

    private static HashMap<String, Integer> getColors() {
        HashMap<String, Integer> colors = new HashMap<>();
        colors.put("red1", -3323575);
        colors.put("red2", -3390139);
        colors.put("red3", -3323832);
        colors.put("red4", -3456703);
        colors.put("redFaded1", -10477022);
        colors.put("redFaded2", -10477279);
        colors.put("black1", -14474458);
        colors.put("black2", -14540251);
        colors.put("black3", -14737630);
        colors.put("blackFaded1", -15724526);
        colors.put("blackFaded2", -15724527);
        colors.put("white", -1);
        colors.put("whiteFaded", -8882056);
        return colors;
    }

    private static HashMap<String, List<Coordinates>> getCardNames(){
        HashMap<String, List<Coordinates>> cardNames = new HashMap<>();
        cardNames.put("2", List.of(new Coordinates(154, 595), new Coordinates(160, 593),
                new Coordinates(164, 597), new Coordinates(157, 607),
                new Coordinates(153, 611), new Coordinates(164, 612)));
        cardNames.put("3", List.of(new Coordinates(154, 593), new Coordinates(164, 593),
                new Coordinates(158, 602), new Coordinates(163, 611),
                new Coordinates(156, 611), new Coordinates(162, 597)));
        cardNames.put("4", List.of(new Coordinates(167, 607), new Coordinates(164, 607),
                new Coordinates(164, 612), new Coordinates(164, 594),
                new Coordinates(153, 606), new Coordinates(164, 606)));
        cardNames.put("5", List.of(new Coordinates(164, 593), new Coordinates(159, 593),
                new Coordinates(155, 593), new Coordinates(155, 601),
                new Coordinates(154, 611)));
        cardNames.put("6", List.of(new Coordinates(164, 594), new Coordinates(160, 593),
                new Coordinates(153, 601), new Coordinates(154, 604),
                new Coordinates(161, 601), new Coordinates(160, 612)));
        cardNames.put("7", List.of(new Coordinates(154, 593), new Coordinates(164, 593),
                new Coordinates(156, 611), new Coordinates(158, 607)));
        cardNames.put("8", List.of(new Coordinates(152, 606), new Coordinates(157, 612),
                new Coordinates(161, 612), new Coordinates(154, 595)));
        cardNames.put("9", List.of(new Coordinates(154, 611), new Coordinates(159, 612),
                new Coordinates(159, 604), new Coordinates(159, 593)));
        cardNames.put("10", List.of(new Coordinates(153, 593), new Coordinates(159, 602)));
        cardNames.put("J", List.of(new Coordinates(162, 593), new Coordinates(162, 597),
                new Coordinates(161, 610), new Coordinates(157, 612),
                new Coordinates(153, 611)));
        cardNames.put("Q", List.of(new Coordinates(165, 606), new Coordinates(171, 611),
                new Coordinates(157, 611), new Coordinates(156, 595)));
        cardNames.put("K", List.of(new Coordinates(154, 603), new Coordinates(160, 602),
                new Coordinates(165, 609)));
        cardNames.put("A", List.of(new Coordinates(153, 607), new Coordinates(159, 595),
                new Coordinates(164, 606)));
        return cardNames;
    }
}
record Coordinates(int width, int height){}