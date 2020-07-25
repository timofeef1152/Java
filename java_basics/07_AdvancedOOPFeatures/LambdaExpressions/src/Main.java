import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.sun.source.tree.BreakTree;
import org.w3c.dom.ls.LSOutput;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {
        ArrayList<Employee> staff = loadStaffFromFile();

        System.out.println("\nсортировка сотрудников по зарабной плате и алфавиту одновременно");
        homeWork7_2(staff);

        System.out.println("\nрасчет максимальной зарплаты");
        homeWork7_8_1(staff, 2017);

        System.out.println("\nпечать ближайших самолетов");
        homeWork7_8_2();
    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for (String line : lines) {
                String[] fragments = line.split("\t");
                if (fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                        fragments[0],
                        Integer.parseInt(fragments[1]),
                        (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }

    static void homeWork7_2(List<Employee> employees) {
        ArrayList<Employee> staff = new ArrayList<>(employees);
        Collections.sort(staff, Comparator.comparingInt(Employee::getSalary).thenComparing(Employee::getName));
        staff.forEach(System.out::println);
    }

    static void homeWork7_8_1(ArrayList<Employee> employees, int hireYear) {
        ArrayList<Employee> staff = new ArrayList<>(employees);
        Calendar cal = Calendar.getInstance();
        staff.stream().filter(e -> {
            cal.setTime(e.getWorkStart());
            return cal.get(Calendar.YEAR) >= hireYear;
        })
                .forEach(System.out::println);
    }

    static void homeWork7_8_2(){
        Calendar calCurTime = Calendar.getInstance();
        Calendar calMaxTime = Calendar.getInstance();
        calMaxTime.add(Calendar.HOUR, 2);
        Airport airport = Airport.getInstance();

        ArrayList<Flight> flights = new ArrayList<>();
        airport.getTerminals().forEach((t->flights.addAll(t.getFlights())));

        ArrayList<Flight> result = flights.stream()
                .filter(f->f.getDate().compareTo(calCurTime.getTime()) > 0 && f.getDate().compareTo(calMaxTime.getTime()) <= 0)
                .sorted(Comparator.comparing(Flight::getDate))//(o1, o2) -> o1.getDate().compareTo(o2.getDate())
                .collect(Collectors.toCollection(ArrayList::new));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        result.stream().forEach(f -> System.out.println(sdf.format(f.getDate()) + " " + f.getAircraft()));
    }
















    static void test(ArrayList<Employee> staff) {
        //бесконечный поток
        Stream.iterate(1, n -> n + 1).forEach(System.out::println);
        Stream.generate(() -> "aaa").forEach(System.out::println);
        //chars filter example
        "sadasgqagajhffghd".chars().filter(x -> x > 15).forEach(c -> System.out.println(Character.toString(c)));

        //C# = Java
        //select = map
        //where = filter
        //zip = reduce
        //? = ifpresent
        staff.stream()
                .map(e -> e.getSalary())
                .filter(s -> s >= 100000)
                .reduce((s1, s2) -> s1 + s2)
                .ifPresent(System.out::println);
    }
}