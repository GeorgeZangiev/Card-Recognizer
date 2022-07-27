import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Base64;

public class CardRecognizer {
    private HashMap<String, String> cardsMap = new HashMap<>();

    public CardRecognizer() throws IOException {
        this.loadCards();
    }

    private void loadCards() throws IOException {
        String directoryPath = "/Users/elmo/Desktop/java-projects/Card-Recognizer/learning_imgs";
        File[] directoryListing = new File(directoryPath).listFiles();
        for (File picture: directoryListing) {
            BufferedImage image = ImageIO.read(picture);
            String fileName = picture.getName();
            String fileWithoutExtension = fileName.split("[.]")[0];
            String[] cardAbbreviations = fileWithoutExtension.split("(?<=\\G(\\d{1,2}|[A-Z])[a-z])");
            int x = 150;
            for (String cardAbbreviation : cardAbbreviations) {
                BufferedImage croppedCard = this.cropCard(image, x);
                String encodedCroppedCard = this.convertBase64(croppedCard);
                cardsMap.put(encodedCroppedCard, cardAbbreviation);
                x = x + 72;
            }
        }
    }

    public void readCards() throws IOException {
        String directoryPath = "/Users/elmo/Desktop/java-projects/Card-Recognizer/learning_imgs";
        File[] directoryListing = new File(directoryPath).listFiles();
        for (File picture: directoryListing) { 
            BufferedImage image = ImageIO.read(picture);
            String cards = "";
            int x = 150;
            for (int i = 0; i < 5; i++) {
                BufferedImage croppedCard = this.cropCard(image, x);
                String encodedCroppedCard = this.convertBase64(croppedCard);
                String cardAbbreviation = cardsMap.get(encodedCroppedCard);
                if (cardAbbreviation != null) {
                    cards += cardAbbreviation;
                }
                x = x + 72;
            }
            System.out.println(picture.getName() + " - " + cards);
        }
    }

    public String convertBase64(BufferedImage bi) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", out);
        byte[] bytes = out.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    private BufferedImage cropCard(BufferedImage image, int x) {
        return image.getSubimage(x, 590, 51, 76);
    }

}
