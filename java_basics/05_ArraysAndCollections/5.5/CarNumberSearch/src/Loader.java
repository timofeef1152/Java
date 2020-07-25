import java.util.*;

public class Loader {
    public static void main(String[] args) {
        char[] lettersArray = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
        List<Character> letters = new ArrayList<>();
        for (int i = 0; i < lettersArray.length; i++) {
            letters.add(lettersArray[i]);
        }
        ArrayList<String> numbers = new ArrayList<>();

        //Генерация номеров
        //==============================================================================================================
        //Регионы
        for (Integer r = 1; r <= 199; r++) {
            //Цифры
            for (Integer n = 1; n <= 9; n++) {
                //Буквы
                for (Integer x = 0; x < letters.size(); x++) {
                    for (Integer y = 0; y < letters.size(); y++) {
                        for (Integer z = 0; z < letters.size(); z++) {
                            {
                                String num =
                                        letters.get(x) +
                                        n.toString() + n.toString() + n.toString() +
                                        letters.get(y) + letters.get(z) +
                                        (r < 10 ? "0" + r.toString() : r.toString());
                                numbers.add(num);
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(numbers);
        //==============================================================================================================

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер авто для поиска в базе: ");
        String searchNumber = scanner.nextLine();
        //Поиск
        //==============================================================================================================
        bruteForceSearch(numbers,searchNumber);
        binarySearch(numbers,searchNumber);
        hashSetSearch(numbers,searchNumber);
        treeSetSearch(numbers,searchNumber);
        //==============================================================================================================
    }

    static void bruteForceSearch(List<String> numbers, String searchValue){
        System.out.println("Поиск перебором");
        boolean found = false;
        searchValue = searchValue.toUpperCase();
        long start = System.nanoTime();
        for (String num : numbers){
            if (num.equalsIgnoreCase(searchValue)){
                System.out.println("Номер " + num + " найден");
                System.out.println("Затраченное время: " + (System.nanoTime() - start) + " нс");
                found = true;
                break;
            }
        }
        if (!found){
            System.out.println("Номер " + searchValue + "не найден");
        }
    }

    static void binarySearch(List<String> numbers, String searchValue){
        System.out.println("Бинарный поиск");
        searchValue = searchValue.toUpperCase();
        long start = System.nanoTime();
        if (Collections.binarySearch(numbers,searchValue) >= 0){
            System.out.println("Номер " + searchValue.toUpperCase() + " найден");
            System.out.println("Затраченное время: " + (System.nanoTime() - start) + " нс");
        }
        else {
            System.out.println("Номер " + searchValue + " не найден");
        }
    }

    static void hashSetSearch(List<String> numbers, String searchValue){
        System.out.println("Поиск в HashSet");
        HashSet<String> hashSetNumbers = new HashSet<>(numbers);
        searchValue = searchValue.toUpperCase();
        long start = System.nanoTime();
        if (hashSetNumbers.contains(searchValue)){
            System.out.println("Номер " + searchValue + " найден");
            System.out.println("Затраченное время: " + (System.nanoTime() - start) + " нс");
        }
        else {
            System.out.println("Номер " + searchValue + " не найден");
        }
    }

    static void treeSetSearch(List<String> numbers, String searchValue){
        System.out.println("Поиск в TreeSet");
        TreeSet<String> treeSetNumbers = new TreeSet<>(numbers);
        searchValue = searchValue.toUpperCase();
        long start = System.nanoTime();
        if (treeSetNumbers.contains(searchValue)){
            System.out.println("Номер " + searchValue + " найден");
            System.out.println("Затраченное время: " + (System.nanoTime() - start) + " нс");
        }
        else {
            System.out.println("Номер " + searchValue + " не найден");
        }
    }
}
