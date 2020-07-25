public class Main {
    public static void main(String[] args) {
        for (char letter = 'A'; letter <= 'z'; letter++){
            if (Character.isAlphabetic(letter)){
                System.out.println("Символ №" + (int)letter + ": " + letter);
            }
        }
    }
}
