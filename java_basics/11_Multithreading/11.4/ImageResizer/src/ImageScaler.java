import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageScaler extends Thread {

    private static final int MIN_WIDTH_FOR_DOUBLE_STEP_SCALE = 1400;

    private File file;
    private int newWidth;
    private String dstFolder;

    public ImageScaler(File file, int newWidth, String dstFolder) {
        this.file = file;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            BufferedImage image = ImageIO.read(file);

            int newHeight = (int) Math.round(
                    image.getHeight() / (image.getWidth() / (double) newWidth));

            double scale = (double) newWidth / image.getWidth();

            if (image.getWidth() > MIN_WIDTH_FOR_DOUBLE_STEP_SCALE) {
                double scale1 = (1 + scale) / 2d;
                int width1 = (image.getWidth() + newWidth) / 2;
                int height1 = (int) Math.round(image.getHeight() / (image.getWidth() / (double) width1));

                AffineTransform tr1 = AffineTransform.getScaleInstance(scale1, scale1);
                AffineTransformOp op1 = new AffineTransformOp(tr1, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

                BufferedImage image1 = new BufferedImage(width1, height1, image.getType());
                op1.filter(image, image1);

                double scale2 = newWidth / (double) width1;
                AffineTransform tr2 = AffineTransform.getScaleInstance(scale2, scale2);
                AffineTransformOp op2 = new AffineTransformOp(tr2, AffineTransformOp.TYPE_BICUBIC);

                BufferedImage newImage = new BufferedImage(newWidth, newHeight, image1.getType());
                op2.filter(image1, newImage);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);

            } else {
                AffineTransform tr = AffineTransform.getScaleInstance(scale, scale);
                AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

                BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
                op.filter(image, newImage);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:" + Thread.currentThread().getName() + "; Duration: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
