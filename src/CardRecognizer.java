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
        String directoryPath = "/Users/arturzangiev/Projects/Card-Recognizer/learning_imgs";
        File[] directoryListing = new File(directoryPath).listFiles();
        for (File picture: directoryListing) {
            BufferedImage image = ImageIO.read(picture);
            String fileName = picture.getName();
            String fileWithoutExtention = fileName.split("[.]")[0];
            String[] cardAbbriviations = fileWithoutExtention.split("(?<=\\G(\\d{1,2}|[A-Z])[a-z])");
            int x = 150;
            for (String cardAbbriviation : cardAbbriviations) {
                BufferedImage croppedCard = this.cropCard(image, x);
                String encodedCroppedCard = this.convertBase64(croppedCard);
                cardsMap.put(encodedCroppedCard, cardAbbriviation);
                x = x + 70;
            }
        }
    }

    public void readCards() throws IOException {
        String directoryPath = "/Users/arturzangiev/Projects/Card-Recognizer/learning_imgs";
        File[] directoryListing = new File(directoryPath).listFiles();
        for (File picture: directoryListing) { 
            BufferedImage image = ImageIO.read(picture);
            int x = 150;
            System.out.println(picture);
            for (int i = 0; i < 5; i++) {
                BufferedImage croppedCard = this.cropCard(image, x);
                String encodedCroppedCard = this.convertBase64(croppedCard);
                String cardAbbriviation = cardsMap.get(encodedCroppedCard);
                if (cardAbbriviation != null) {
                    System.out.println(cardAbbriviation);
                }
                x = x + 70;
            }
        }
    }

    public String convertBase64(BufferedImage bi) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", out);
        byte[] bytes = out.toByteArray();
        String base64bytes = Base64.getEncoder().encodeToString(bytes);
        return base64bytes;
    }

    private BufferedImage cropCard(BufferedImage image, int x) {
        BufferedImage croppedCard = image.getSubimage(x, 590, 51, 76);
        return croppedCard;
    }

}
