import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.IIOException;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.FileImageOutputStream;

public class Main {

    static double x = 0;
    static double y = 0;

    static final int w = 750;
    static final int h = 600;
    static final BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

    public static void main(String[] args) {
        // Paint canvas black
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                img.setRGB(j, i, Color.BLACK.getRGB());
        try {
            ImageOutputStream output = new FileImageOutputStream(new File("out.gif"));
            GifSequenceWriter writer = new GifSequenceWriter(output, img.getType(), 100, true);
            writer.writeToSequence(img);
            for (int i = 0; i < 20000; i++) {
                plot();
                if (i % 100 == 0) writer.writeToSequence(img);
                next();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IIOException e) {
            System.err.println("Caught IIOException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    static void plot() {
        double mappedX = Math.abs(-3.5-x)*100;
        double mappedY = Math.abs(5.5-y/2)*100;
        img.setRGB((int)mappedX, (int)mappedY, Color.GREEN.getRGB());
    }

    static void next() {
        double nextX;
        double nextY;
        double r = Math.random();
        if (r < 0.01) {
            nextX = 0;
            nextY = 0.16*y;
        } else if (r < 0.86) {
            nextX = 0.85*x + 0.04*y;
            nextY = -0.04*x + 0.85*y + 1.6;
        } else if (r < 0.93) {
            nextX = 0.2*x - 0.26*y;
            nextY = 0.23*x + 0.22*y + 1.6;
        } else {
            nextX = -0.15*x + 0.28*y;
            nextY = 0.26*x + 0.24*y + 0.44;
        }
        x = nextX;
        y = nextY;
    }
}
