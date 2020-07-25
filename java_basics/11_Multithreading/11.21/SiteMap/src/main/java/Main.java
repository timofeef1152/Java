import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {

    static String url = "https://secure-headland-59304.herokuapp.com";

    public static void main(String[] args) {
        SiteMapBuilder siteMapBuilder = new SiteMapBuilder(url);
        new ForkJoinPool().invoke(siteMapBuilder);
        System.out.println("The End");

        SiteMapBuilder.getMapList().sort(Comparator.comparing(String::trim));
        List<String> list = SiteMapBuilder.getMapList();
        int delTab = 0;
        for (int i = 0; i < list.size(); i++) {
            String cur = list.get(i);
            int depth = cur.split("/").length;
            if (i == 0) {
                delTab = depth;
            }
            print(depth - delTab, cur);
        }
    }

    private static void print(int depth, String text) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            temp.append("\t");
        }
        System.out.println(temp.toString() + text);
    }
}
