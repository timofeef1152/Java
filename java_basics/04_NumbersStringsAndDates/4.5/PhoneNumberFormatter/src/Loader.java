import java.util.Scanner;

public class Loader {
    static final String STOP_WORD = "stop";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(true) {
            System.out.print("Введите номер телефона: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase(STOP_WORD)) {
                return;
            }
            String phone = input.replaceAll("\\D", "");
            if (phone.length() > 11 || phone.length() < 10){
                System.out.println("Ошибка ввода. Недопустимое количество цифр!");
            } else {
                phone = phone.substring(phone.length() - 10);
                phone = "+7 " + phone.substring(0, 3) + " " + phone.substring(3, 6) + "-" + phone.substring(6, 8) + "-" + phone.substring(8, 10);
                System.out.println("Ваш номер: " + phone + " занесен в базу.");
            }
        }
    }
}
