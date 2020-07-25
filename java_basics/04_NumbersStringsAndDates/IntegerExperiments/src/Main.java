import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;

        System.out.print("Введите число: ");
        int input = new Scanner(System.in).nextInt();
        System.out.println("Сумма цифр: " + sumDigits(input));
    }

    public static Integer sumDigits(Integer number)
    {
        int result = 0;
        String numStr = number.toString();
        for (int i = 0; i < numStr.length(); i++){
            result += Character.getNumericValue(numStr.charAt(i));
        }
        return result;
    }
}
