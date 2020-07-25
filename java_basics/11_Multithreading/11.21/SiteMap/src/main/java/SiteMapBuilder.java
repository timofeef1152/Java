import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SiteMapBuilder extends RecursiveTask<Boolean> {

    private static final List<String> mapList = Collections.synchronizedList(new ArrayList<>());

    private final String url;
    private Document document;
    private Connection.Response response;

    public SiteMapBuilder(String url) {
        this.url = url;
    }

    public static List<String> getMapList() {
        return mapList;
    }

    @Override
    protected Boolean compute() {
        try {
            Thread.sleep(150);
            response = Jsoup.connect(url).execute();
            document = response.parse();
            List<SiteMapBuilder> tasks = new ArrayList<>();
            List<String> links = getLinks();
            links.forEach(l -> {
                SiteMapBuilder task = new SiteMapBuilder(l);
                task.fork();
                tasks.add(task);
            });
            synchronized (mapList) {
                if (mapList.stream().anyMatch(x -> x.trim().equals(url))) return null;
                mapList.add(url);
            }
            tasks.forEach(ForkJoinTask::join);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(url);
            return false;
        }
    }

    private List<String> getLinks() {
        List<String> hrefs = new ArrayList<>();
        document.select("a[href!=http]").forEach(a -> {
            String href = a.attr("href");
            if (!href.isEmpty() && !href.equals("/") && !href.contains("#")) {
                String link = href.startsWith("/")
                        ? (response.url().getProtocol() + "://" + response.url().getHost() + href)
                        : url + href;
                hrefs.add(link);
            }
        });
        return hrefs;
    }
}
