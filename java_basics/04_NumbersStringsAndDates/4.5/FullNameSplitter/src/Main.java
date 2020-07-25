import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ФИО: ");
        String fullName = scanner.nextLine().trim();

        var splittedFullName = fullName.split("[^a-zA-ZА-Яа-я]+");
        //здесь бы лучше использовал массив ["Фамилия", "Имя", "Отчество"] для сопоставления по индексу
        //чтобы избавиться от условий
        if (splittedFullName.length > 0 && !splittedFullName[0].equals("")){
            System.out.println("Фамилия: " + splittedFullName[0]);
            if (splittedFullName.length > 1){
                System.out.println("Имя: " + splittedFullName[1]);
            }
            if (splittedFullName.length > 2){
                System.out.println("Отчество: " + splittedFullName[2]);
            }
        }
        else {
            System.out.println("Ошибка ввода!");
        }
    }
}
