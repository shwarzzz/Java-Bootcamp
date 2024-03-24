package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BMPPrinter {
    private Ansi.BColor[][] pixelsArray;

    public BMPPrinter() {

    }

    public void convert(String whiteReplace, String blackReplace) {
        try {
            BufferedImage image = ImageIO.read(BMPPrinter.class.getResource("/resources/image.bmp"));
            pixelsArray = new Ansi.BColor[image.getHeight()][image.getWidth()];
            Ansi.BColor whiteColor = Ansi.BColor.valueOf(whiteReplace);
            Ansi.BColor blackColor = Ansi.BColor.valueOf(blackReplace);
            int pixelColor;
            for (int y = 0; y < pixelsArray.length; y++) {
                for (int x = 0; x < pixelsArray[y].length; x++) {
                    pixelColor = image.getRGB(x, y);
                    if (pixelColor == Color.BLACK.getRGB()) {
                        pixelsArray[y][x] = blackColor;
                    } else if (pixelColor == Color.WHITE.getRGB()) {
                        pixelsArray[y][x] = whiteColor;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong color name!");
            System.exit(-1);
        }
    }

    public Ansi.BColor[][] getPixelsArray() {
        return pixelsArray;
    }

    public void printCharArray() {
        ColoredPrinter printer = new ColoredPrinter();
        if (pixelsArray != null) {
            for (Ansi.BColor[] lineOfColors : pixelsArray) {
                for (Ansi.BColor color : lineOfColors) {
                    printer.setBackgroundColor(color);
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, color);
                }
                System.out.println();
            }
        } else {
            System.out.println("File not selected");
        }
    }
}
