import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);;
    private static final Marker EXCEPTIONS_MARKER = MarkerManager.getMarker("EXCEPTIONS");
    private static final Marker INPUT_HISTORY_MARKER = MarkerManager.getMarker("INPUT_HISTORY").setParents(EXCEPTIONS_MARKER);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Path fromPath;
        Path toPath;
        for (;;){
            try{
                System.out.println("\nВведите полный путь к исходной папке: ");
                input = scanner.nextLine().trim();
                LOGGER.info( INPUT_HISTORY_MARKER,"fromPath:\t" + input);
                fromPath = Paths.get(input);
                if (Files.notExists(fromPath) || !Files.isDirectory(fromPath)){
                    System.out.println("Папка не найдена!");
                    LOGGER.info(INPUT_HISTORY_MARKER, "directory " + input + " not found");
                    continue;
                }
                System.out.println("Введите полный путь к новой папке: ");
                input = scanner.nextLine().trim();
                LOGGER.info(INPUT_HISTORY_MARKER,"toPath:\t" + input);
                toPath = Paths.get(input);
                if (fromPath.equals(toPath)){
                    System.out.println("Указанные пути не должны совпадать!");
                    LOGGER.info(INPUT_HISTORY_MARKER, "directories are same");
                    continue;
                }
                if (Files.notExists(Files.createDirectories(toPath))){
                    System.out.println("Указан некорректный путь для новой папки!");
                    LOGGER.info(INPUT_HISTORY_MARKER, "directory " + input + " can not be created");
                    continue;
                }
                copy(fromPath, toPath);
                System.out.println("Завершено!");
                Desktop.getDesktop().open(new File(toPath.toString()));
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error(EXCEPTIONS_MARKER, "", e);
            }
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        Files.walk(from).forEach(source -> {
            try {
                Path relative = from.relativize(source);
                Path dest = Paths.get(to.toString(), relative.toString());
                Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(EXCEPTIONS_MARKER, "", e);
            }
        });
    }
}
