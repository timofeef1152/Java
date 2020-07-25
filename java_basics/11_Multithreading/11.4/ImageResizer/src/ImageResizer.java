import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageResizer extends Thread {
    private final File file;
    private final int reduceScale;
    private final String dstFolder;

    public ImageResizer(File file, int reduceScale, String dstFolder) {
        this.file = file;
        this.reduceScale = Math.abs(reduceScale);
        this.dstFolder = dstFolder;
    }

    private static int getAvgRGB(List<Integer> rgbList) {
        int red = 0, green = 0, blue = 0;
        for (int rgb : rgbList) {
            Color color = new Color(rgb);
            red += color.getRed();
            green += color.getGreen();
            blue += color.getBlue();
        }
        int redAvg = Math.round((float) red / rgbList.size());
        int greenAvg = Math.round((float) green / rgbList.size());
        int blueAvg = Math.round((float) blue / rgbList.size());
        return new Color(redAvg, greenAvg, blueAvg).getRGB();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) return;

            long newWidth, newHeight;
            if (reduceScale >= 1) {
                newWidth = Math.round(image.getWidth() / (double) reduceScale);
                newHeight = Math.round(image.getHeight() / (double) reduceScale);
            } else {
                newWidth = Math.round(image.getWidth() * (double) reduceScale);
                newHeight = Math.round(image.getHeight() * (double) reduceScale);
            }
            BufferedImage newImage = compress(image, (int)newWidth, (int)newHeight);

            File newFile = new File(dstFolder + "/" + file.getName());
            ImageIO.write(newImage, "jpg", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:" + Thread.currentThread().getName() + "; Duration: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    //TODO доработать (обрезаются углы)
    private BufferedImage compress(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage newImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
        );

        //Определяем смещение точек.
        int xOffset = reduceScale / 2;
        int yOffset = reduceScale / 2;

        for (int x = 0, oldX = x; x < newImage.getWidth(); x++, oldX += reduceScale) {
            for (int y = 0, oldY = y; y < newImage.getHeight(); y++, oldY += reduceScale) {
                try {
                    List<Integer> rgbList = new ArrayList<>();

                    int xLeft = oldX - xOffset;
                    int xRight = oldX + xOffset;
                    int yTop = oldY - yOffset;
                    int yBottom = oldY + yOffset;

                    int newRGB;
                    if (xLeft >= 0 && yTop >= 0 && xRight < image.getWidth() && yBottom < image.getHeight()) {
                        rgbList.add(image.getRGB(xLeft, yTop));
                        rgbList.add(image.getRGB(xRight, yTop));
                        rgbList.add(image.getRGB(xRight, yBottom));
                        rgbList.add(image.getRGB(xLeft, yBottom));
                        newRGB = getAvgRGB(rgbList);
                    } else {
                        newRGB = image.getRGB(oldX, oldY);
                    }
                    newImage.setRGB(x, y, newRGB);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
        }
        return newImage;
    }
}