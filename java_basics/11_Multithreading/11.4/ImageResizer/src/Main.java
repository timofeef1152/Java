import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String srcFolder = "C:\\Users\\inchm\\Desktop\\study\\SKILLBOX\\images";
    private static final String dstFolder = "C:\\Users\\inchm\\Desktop\\study\\SKILLBOX\\resized_images_my";

    public static void main(String[] args) throws IOException, InterruptedException {
        int logicalProcessorsCount = Runtime.getRuntime().availableProcessors();

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("Каталог " + srcDir + " пустой или не существует.");
            return;
        }

        Files.createDirectories(Paths.get(dstFolder));

        ExecutorService service = Executors.newFixedThreadPool(logicalProcessorsCount);
        long startTime = System.currentTimeMillis();
        Arrays.stream(files).forEach(f -> {
            Thread thread = new ImageResizer(f, 4, dstFolder);
//            Thread thread = new ImageScaler(f, 300, dstFolder);
            service.submit(thread);
        });
        service.shutdown();
        //Верно же, что лучше поставить слип, чтобы процессор успевал "отдохнуть"?
        while (!service.isTerminated()) Thread.sleep(1);
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.println("Затрачено времени всего: " + executionTime + "ms ±1ms");
    }
}