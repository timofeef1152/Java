
public class Loader
{
    public static void main(String[] args)
    {
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";

        System.out.println(text);

        int sum = 0;

        var tempStr = text.substring(text.indexOf(' ')).trim();
        tempStr = tempStr.substring(tempStr.indexOf(' ')).trim();
        tempStr = tempStr.substring(0, tempStr.indexOf(' '));

        sum += Integer.parseInt(tempStr);//прибавили заработок Васи

        tempStr = text.substring(text.indexOf('-') + 1).trim();
        tempStr = tempStr.substring(0, tempStr.indexOf(' '));

        sum += Integer.parseInt(tempStr);//прибавили заработок Пети

        tempStr = text.substring(text.lastIndexOf('-') + 1).trim();
        tempStr = tempStr.substring(0, tempStr.indexOf(' '));

        sum += Integer.parseInt(tempStr);//прибавили заработок Маши

        System.out.println("Сумма заработка: " + sum);
    }
}