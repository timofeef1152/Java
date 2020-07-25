import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {
    public static void main(String[] args) {
        TreeMap<String, String> phone2name = new TreeMap<>();

        Pattern phonePattern = Pattern.compile("[0-9 \\-+()]+");
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.print("Введите номер телефона или имя: ");
            String input = scanner.nextLine();

            if (input.toLowerCase().equals("list")){
                if (phone2name.size() > 0){
                    System.out.println("Список <Телефон, Имя>:");
                    printMap(phone2name);
                }
                else {
                    System.out.println("Список пуст");
                }
                continue;
            }
            if (input.toLowerCase().equals("stop")){
                break;
            }
            if (phone2name.containsKey(input)){
                printMap(phone2name, input);
            }
            else if (phone2name.containsValue(input)){
                for (String phone : phone2name.keySet()){
                    if (phone2name.get(phone).equals(input)){
                        printMap(phone2name, phone);
                    }
                }
            }
            else {
                //Телефон
                if (phonePattern.matcher(input).matches()){
                    if (!checkPhoneLength(input)){
                        continue;
                    }
                    System.out.print("Введите имя нового контакта: ");
                    String name = scanner.nextLine();
                    phone2name.put(input, name);
                }
                //Имя
                else {
                    System.out.print("Введите телефон нового контакта: ");
                    String phoneNumber = scanner.nextLine();
                    if (phonePattern.matcher(phoneNumber).matches() && !checkPhoneLength(phoneNumber)){
                        phone2name.put(phoneNumber, input);
                    }
                    else {
                        System.out.println("Ошибка ввода. Попробуйте еще раз");
                    }
                }
            }
        }
    }

    static boolean checkPhoneLength(String input){
        String p = input.replaceAll("\\D", "");
        if (p.length() < 10 || p.length() > 11){
            System.out.println("Неверная длина номера");
            return false;
        }
        return true;
    }

    static void printMap(Map<String, String> map, String key){
        System.out.println("\t" + key + ": " + map.get(key));
    }

    static void printMap(Map<String, String> map){
        for (String key : map.keySet()){
            System.out.println("\t" + key + ": " + map.get(key));
        }
    }
}
