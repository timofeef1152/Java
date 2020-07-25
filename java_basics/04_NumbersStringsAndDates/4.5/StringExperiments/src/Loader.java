import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.*;

public class Loader {
    public static void main(String[] args) {
        //1
        //========================================================================================
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);

        int sum = 0;
        while (m.find()){
            sum += Integer.parseInt(m.group());
        }
        System.out.println(text);
        System.out.println("Общий заработок: " + sum);

        //2
        //========================================================================================
        try {
            String content = Files.readString(Path.of("./bbcText.txt"));
            p = Pattern.compile("[A-Za-zА-Яа-я’]+");
            m = p.matcher(content);
            while (m.find()){
                System.out.println(m.group());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //========================================================================================
    }
}
