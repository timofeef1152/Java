public class Loader {
    public static void main(String[] args) {
        //1
        //=========================================================================
        String text = "Каждый охотник желает знать, где сидит фазан";

        String[] colors = text.split(",?\\s+");

        System.out.println("\nbefore:");
        for (String str : colors){
            System.out.println(str);
        }

        for (int i = 0, j = colors.length - 1; i < colors.length / 2; i++, j--){
            String temp = colors[i];
            colors[i] = colors[j];
            colors[j] = temp;
        }

        System.out.println("\nafter:");
        for (String str : colors){
            System.out.println(str);
        }
        //=========================================================================
    }
}
