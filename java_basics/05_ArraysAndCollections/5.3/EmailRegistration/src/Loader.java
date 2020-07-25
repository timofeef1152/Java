import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {
    static final String HELP_MESSAGE =
            "Доступные команды:\n" +
            ">>>ADD\n" +
            ">>>LIST\n" +
            ">>>STOP\n";

    public static void main(String[] args) {
        HashSet<String> emails = new HashSet<>();
        //Паттерн взят с сайта:
        //https://howtodoinjava.com/regex/java-regex-validate-email-address/
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Email Registration v1.0");
        System.out.println(HELP_MESSAGE);

        boolean run = true;
        while (run){
            System.out.print("Введите команду: ");
            String command = scanner.nextLine().toUpperCase();
            switch (command){
                case "ADD":
                    System.out.print("Введите email: ");
                    String email = scanner.nextLine().toLowerCase();
                    Matcher matcher = pattern.matcher(email);
                    if (!matcher.matches()){
                        System.out.println("Введенный email содержит недопустимые символы");
                        System.out.println("Исправьте ошибку и попробуйте еще раз");
                        continue;
                    }
                    if (emails.contains(email)){
                        System.out.println("Введенный email уже был добавлен ранее");
                        continue;
                    }
                    emails.add(email);
                    break;
                case "LIST":
                    System.out.println("Список email:");
                    for (String e : emails){
                        System.out.println("\t" + e);
                    }
                    break;
                case "STOP":
                    run = false;
                    break;
                default:
                    System.out.println(HELP_MESSAGE);
                    break;
            }
        }
    }
}
