import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String SITE_URL = "https://lenta.ru/";
    private static final String FOLDER_PATH = "images/";

    public static void main(String[] args) {
        try {
            new File(FOLDER_PATH).mkdirs();
            List<String> imgPaths = new ArrayList<String>();
            Document doc = Jsoup.connect(SITE_URL).get();
            Elements elements = doc.select("img");
            elements.forEach(element -> {
                imgPaths.add(element.absUrl("src"));
            });
            imgPaths.forEach(Main::saveImage);
            System.out.println("Сохранено изображений:\t" + imgPaths.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveImage(String src) {
        try {
            //Exctract the name of the image from the src attribute
            int indexname = src.lastIndexOf("/");

            if (indexname == src.length()) {
                src = src.substring(1, indexname);
            }

            indexname = src.lastIndexOf("/");
            String name = src.substring(indexname);

            //Open a URL Stream
            URL url = new URL(src);
            InputStream in = url.openStream();

            OutputStream out = new BufferedOutputStream(new FileOutputStream( FOLDER_PATH + name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_")));

            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
