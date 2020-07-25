import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Loader {
    public static void main(String[] args) throws ParseException {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        String pattern = "dd.MM.yyyy - EEE";
        SimpleDateFormat myFormat = new SimpleDateFormat(pattern);

        System.out.print("Введите дату рождения в формате DD.MM.YYYY: ");
        Scanner scanner = new Scanner(System.in);
        String dateStr = scanner.nextLine();
        Date birthDate = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru-RU")).parse(dateStr);

        cal.setTime(new Date());
        int currentYear = cal.get(Calendar.YEAR);
        cal.setTime(birthDate);
        int birthYear = cal.get(Calendar.YEAR);

        for (int i = birthYear, j = 0; i < currentYear; i++, j++) {
            cal.add(Calendar.YEAR, 1);
            System.out.println(j + " - " + myFormat.format(cal.getTime()));
        }
    }
}
