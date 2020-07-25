public class Loader {
    public static void main(String[] args) {
        //3
        //=========================================================================
        //Длина массива должна быть нечетной
        String example = "x     x";
        int stringsLength = example.length();
        String[][] strings = new String[stringsLength][stringsLength];

        for (int i = 0; i < stringsLength; i++){
            for (int j = 0; j < stringsLength; j++){
                if (i == j || i == stringsLength - j - 1){
                    strings[i][j] = "x";
                } else {
                    strings[i][j] = " ";
                }
                System.out.print(strings[i][j]);
            }
            System.out.println();
        }
        //=========================================================================
    }
}
