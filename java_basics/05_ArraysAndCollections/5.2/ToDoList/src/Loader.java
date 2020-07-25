import java.util.ArrayList;
import java.util.Scanner;

public class Loader {
    static final String OUT_OF_BOUNDS_MESSAGE =
            ">>>Элемент с указанным индексом не существует";
    static final String TEMPLATE_MESSAGE =
            ">>>Следуйте шаблону <COMMAND> <INDEX>? <CONTENT>?";
    static final String HELPER_MESSAGE =
            ">>>Для отображения списка доступных команд введите <HELP>";
    static final String HELP_MESSAGE =
            ">>>ADD <CONTENT> Добавит запись в конец списка дел\n" +
            ">>>LIST Отобразит весь список дел\n" +
            ">>>ADD <INDEX> <CONTENT> Добавит запись в указанную позицию или в конец списка дел, если превышена длина списка дел\n" +
            ">>>DELETE <INDEX> Удаляет запись из списка дел в указанной позиции или сообщает, что такой элемент не существет, если превышена длина списка дел\n" +
            ">>>EDIT <INDEX> <CONTENT> Заменяет запись в списке дел в указанной позиции или сообщает, что такой элемент не существет, если превышена длина списка дел\n" +
            ">>>HELP Отобразит список доступных команд";

    public static void main(String[] args) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ADD");
        commandList.add("EDIT");
        commandList.add("LIST");
        commandList.add("DELETE");

        ArrayList<String> todoList = new ArrayList<>();

        System.out.println("Todo List v 1.0");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equalsIgnoreCase("stop")){
            input = scanner.nextLine().trim().replaceAll("[ ]+", " ");
            if (input.equalsIgnoreCase("help")){
                System.out.println(HELP_MESSAGE);
                continue;
            }

            var splitInput = input.split(" ");
            String command = splitInput[0].toUpperCase();
            if (!commandList.contains(command)){
                System.out.println(TEMPLATE_MESSAGE);
                System.out.println(HELPER_MESSAGE);
                continue;
            }

            boolean handled = false;
            switch (command){
                case "ADD":
                    if (splitInput.length <= 1){
                        break;
                    }
                    if (splitInput[1].matches("[^\\D]")){
                        int index = Integer.parseInt(splitInput[1]);
                        input = input.substring(input.indexOf(splitInput[1]) + splitInput[1].length() + 1);
                        addToList(todoList, input, index);
                    }
                    else {
                        input = input.substring(input.indexOf(' ') + 1);
                        todoList.add(input);
                    }
                    handled = true;
                    break;
                case "EDIT":
                    if (splitInput.length > 1 && splitInput[1].matches("[^\\D]")){
                        int index = Integer.parseInt(splitInput[1]);
                        input = input.substring(input.indexOf(splitInput[1]) + splitInput[1].length() + 1);
                        editNodeInList(todoList, input, index);
                        handled = true;
                    }
                    break;
                case "DELETE":
                    if (splitInput.length > 1 && splitInput[1].matches("[^\\D]")){
                        int index = Integer.parseInt(splitInput[1]);
                        removeFromList(todoList, index);
                        handled = true;
                    }
                    break;
                case "LIST":
                    if (splitInput.length > 1){
                        break;
                    }
                    for (int i = 0; i < todoList.size(); i++){
                        System.out.println("\t" + i + " - "  + todoList.get(i));
                    }
                    if (todoList.size() == 0){
                        System.out.println("Список дел пуст");
                    }
                    handled = true;
                    break;
            }
            if (!handled){
                System.out.println(TEMPLATE_MESSAGE);
            }
        }
    }

    static void removeFromList(ArrayList<String> stringList, int index){
        if (stringList.size() > index) {
            stringList.remove(index);
        } else {
            System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }

    static void addToList(ArrayList<String> stringList, String text, int index){
        if (stringList.size() > index) {
            stringList.add(index, text);
        } else {
            stringList.add(text);
        }
    }

    static void editNodeInList(ArrayList<String> stringList, String text, int index){
        if (stringList.size() > index) {
            stringList.set(index, text);
        } else {
            System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }
}
