import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
            // System.out.println(fileWithoutExtention);
            String[] cardAbbriviations = fileWithoutExtention.split("(?<=\\G(\\d{1,2}|[A-Z])[a-z])");
            int x = 150;
            for (String cardAbbriviation : cardAbbriviations) {
                // System.out.println(cardAbbriviation);
                BufferedImage croppedCard = image.getSubimage(x, 590, 51, 76);
                // File outputfile = new File(cardAbbriviation + ".png");
                // ImageIO.write(croppedCard, "png", outputfile);
                x = x + 70;
                cardsMap.put(image.getRaster().getDataBuffer().getData(), cardAbbriviation);
                // System.out.println(cardsMap.get(croppedCard));
            }
        }
    }

    public void readCards() throws IOException {
        String directoryPath = "/Users/arturzangiev/Projects/Card-Recognizer/learning_imgs";
        File[] directoryListing = new File(directoryPath).listFiles();
        for (File picture: directoryListing) { 
            BufferedImage image = ImageIO.read(picture);
            int x = 150;
            for (int i = 0; i < 5; i++) {
                BufferedImage croppedCard = image.getSubimage(x, 590, 51, 76);
                String cardAbbriviation = cardsMap.get(image);
                System.out.println(cardAbbriviation);
                x = x + 70;
            }
        }
    }

}
