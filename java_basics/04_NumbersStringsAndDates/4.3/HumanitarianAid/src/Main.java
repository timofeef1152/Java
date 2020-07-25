import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сколько ящиков помещается в контейнер ?");
        int maxBoxesInContainerCount = scanner.nextInt();
        System.out.println("Сколько контейнеров помещается в грузовик ?");
        int maxContainersInTruckCount = scanner.nextInt();
        System.out.println("Сколько ящиков гуманитарной помощи необходимо расфасовать ?");
        int boxesCount = scanner.nextInt();

        System.out.println("\nФасуем...");

        int trucksCount = 0;
        int containersCount = 0;
        for (int i = 1; i <= boxesCount; i++){
            if (i == 1 || i % (maxBoxesInContainerCount * maxContainersInTruckCount) == 0){
                System.out.println("\nГрузовик " + ++trucksCount);
            }
            if (i == 1 || i % maxBoxesInContainerCount == 0){
                System.out.println("\tКонтейнер " + ++containersCount);
            }
            System.out.println("\t\tЯщик " + i);
        }
    }
}
