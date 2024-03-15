package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BMPPrinter {
    private char[][] pixelsArray;

    public BMPPrinter() {

    }

    public void convert(String pathToBMP,
                        char whiteChar, char blackChar) {
        try {
            BufferedImage image = ImageIO.read(new File(pathToBMP));
            pixelsArray = new char[image.getHeight()][image.getWidth()];
            int pixelColor;
            for (int y = 0; y < pixelsArray.length; y++) {
                for (int x = 0; x < pixelsArray[y].length; x++) {
                    pixelColor = image.getRGB(x, y);
                    if (pixelColor == Color.BLACK.getRGB()) {
                        pixelsArray[y][x] = blackChar;
                    } else if (pixelColor == Color.WHITE.getRGB()) {
                        pixelsArray[y][x] = whiteChar;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public char[][] getPixelsArray() {
        return pixelsArray;
    }

    public void printCharArray() {
        if (pixelsArray != null) {
            for (char[] line : pixelsArray) {
                for (char pixel : line) {
                    System.out.print(pixel);
                }
                System.out.println();
            }
        } else {
            System.out.println("File not selected");
        }
    }
}
