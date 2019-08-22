package utils.textonimage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextOnImage {
    public static final String LEFT_TEXT_MODE = "left-text-mode";
    public static final String RIGHT_TEXT_MODE = "right-text-mode";
    public static final String CENTER_TEXT_MODE = "center-text-mode";
    public static final String WIDTH_TEXT_MODE = "width-text-mode";

    private BufferedImage bufferedImage;
    private String fileName;

    public TextOnImage(File imageFile) {
        try {
            bufferedImage = ImageIO.read(imageFile);
            fileName = imageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            bufferedImage = null;
            imageFile = null;
        }
    }

    public TextOnImage(String imageFilePath) {
        this(new File(imageFilePath));
    }

    public static void addTextToImageAndSave(String text, String filePath) {
        TextOnImage img = new TextOnImage(filePath);
        img.addTextToImage(text, 10, 10, 560, 1, TextOnImage.LEFT_TEXT_MODE, "Arial", Font.BOLD, 14, Color.BLACK);
        img.saveAs(filePath);
    }

    public BufferedImage getAsBufferedImage() {
        return bufferedImage;
    }

    public void saveAs(String fileName) {
        saveImage(new File(fileName));
        this.fileName = fileName;
    }

    public void save() {
        saveImage(new File(fileName));
    }

    private void saveImage(File file) {
        try {
            ImageIO.write(bufferedImage, getFileType(file), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int idx = fileName.lastIndexOf(".");
        if (idx == -1) {
            throw new RuntimeException("Invalid file name");
        }

        return fileName.substring(idx + 1);
    }

    public void addTextToImage(String text,
                               int topX, int topY,
                               int zoneW, float alpha,
                               String mode, String font,
                               int type, int size, Color color) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.BLACK);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font(font, type, size));

        final FontMetrics fontMetrics = g.getFontMetrics();
        g.dispose();

        int lineHeight = fontMetrics.getHeight();
        String[] words = text.replaceAll("\n", " \n").split(" ");
        String line = "";
        List<String> lines = new ArrayList<>();
        for (String word : words) {
            if (fontMetrics.stringWidth(line + word) > zoneW || word.contains("\n")) {
                lines.add(line);
                line = "";
            }
            if (!word.equals("\n")) {
                line += word + " ";
            }
        }

        lines.add(line);

        for (int i = 0; i < lines.size(); i++) {
            addTextLineToImage(lines.get(i),
                    topX, lineHeight + topY + i * lineHeight,
                    zoneW, i == (lines.size() - 1),
                    alpha, mode, font, type, size, color);
        }
    }

    private void addTextLineToImage(String text,
                                    int topX, int topY,
                                    int zoneW, boolean isLastLine,
                                    float alpha, String mode,
                                    String font, int type,
                                    int size, Color color) {
        String[] words = text.trim().split(" ");

        if (words.length == 0) {
            return;
        } else if (words.length == 1) {
            addTextToImage(text, topX, topY, alpha, font, type, size, color);
        } else {
            Graphics2D g = bufferedImage.createGraphics();
            g.setColor(Color.BLACK);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setFont(new Font(font, type, size));

            final FontMetrics fontMetrics = g.getFontMetrics();
            g.dispose();

            if (mode.equalsIgnoreCase(LEFT_TEXT_MODE)) {
                addTextToImage(text, topX, topY, alpha, font, type, size, color);
            } else if (Objects.equals(mode, CENTER_TEXT_MODE)) {
                topX += (zoneW - fontMetrics.stringWidth(text)) / 2;
                addTextToImage(text, topX, topY, alpha, font, type, size, color);
            } else if (Objects.equals(mode, RIGHT_TEXT_MODE)) {
                topX += zoneW - fontMetrics.stringWidth(text);
                addTextToImage(text, topX, topY, alpha, font, type, size, color);
            } else {
                int totalWordsWidth = 0;
                for (String word1 : words) {
                    totalWordsWidth += fontMetrics.stringWidth(word1);
                }

                int delta = Math.round((zoneW - totalWordsWidth) / (words.length - 1));
                int offset = 0;

                if (isLastLine) {
                    delta = Math.min(delta, 10);
                }

                for (String word : words) {
                    addTextToImage(word, topX + offset, topY, alpha, font, type, size, color);

                    offset += fontMetrics.stringWidth(word) + delta;
                }
            }
        }
    }

    private void addTextToImage(String text,
                                int topX, int topY,
                                float alpha,
                                String font, int type,
                                int size, Color color) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // 0.5 = 50% transparency
        g.setFont(new Font(font, type, size));

        g.drawString(text, topX, topY);
        g.dispose();
    }
}
