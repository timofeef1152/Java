import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Logger logger;

    public static void main(String[] args) {
        logger = LogManager.getRootLogger();
        Scanner scanner = new Scanner(System.in);
        String input;
        for (;;){
            System.out.println("\nВведите полный путь к папке: ");
            input = scanner.nextLine().trim();
            File folder = new File(input);
            if (!folder.exists() || !folder.isDirectory()){
                System.out.println("Папка не найдена!");
                continue;
            }
            List<File> files = new ArrayList<>();
            getAllFiles(folder, files);
            System.out.println("\nСодержимое:");
            long folderSize = 0;
            for(File f : files){
                try{
                    System.out.println(f.getAbsolutePath() + ":\t" + getFileLength(f.length()));
                    folderSize += f.length();
                }
                catch (Exception e){
                    logger.error("\n" + getStackTrace(e));
                }
            }
            try{
                System.out.println("\nОбщий объем:\t" + getFileLength(folderSize));
            }
            catch (Exception e){
                logger.error("\n" + getStackTrace(e));
            }
        }
    }

    private static void getAllFiles(File directory, List<File> files) {
        if (files == null) {
            files = new ArrayList<>();
        }
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                getAllFiles(file, files);
            }
        }
    }

    private static String getFileLength(long length) throws Exception {
        double len = (double) length;
        double l024 = 1024;
        int depth = 0;
        for (;;){
            len = len / l024;
            if (len < 1){
                break;
            }
            depth++;
        }
        double size = depth > 0 ? length / Math.pow(l024,  depth) : length;
        String sizeStr = String.format("%.2f", size);
        switch (depth){
            case 0:
                return sizeStr + " байт";
            case 1:
                return sizeStr + " кб";
            case 2:
                return sizeStr + " мб";
            case 3:
                return sizeStr + " гб";
            case 4:
                return sizeStr + " тб";
            case 5:
                return sizeStr + " пб";
            case 6:
                return sizeStr + " эб";
            case 7:
                return sizeStr + " зб";
            case 8:
                return sizeStr + " йб";
            default:
                throw new Exception("Не реализовано!");
        }
    }

    private static String getStackTrace(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
