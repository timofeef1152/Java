import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final String DATE = "Дата операции";
    private static final String DEPOSIT_COLUMN = "Расход";
    private static final String WITHDRAW_COLUMN = "Приход";
    private static final String DESCRIPTION_COLUMN = "Описание операции";

    private static double depositSum = 0;
    private static double withdrawSum = 0;
    private static TreeMap<Date,Double> depositMapByDate;
    private static TreeMap<String,Double> depositMapByRecipient;

    public static void main(String[] args) {
        try {
            depositMapByDate = new TreeMap<>();
            depositMapByRecipient = new TreeMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            List<HashMap<String,String>> content = getReportContent("data/movementList.csv");

            content.forEach(row ->{
                double currentWithdraw = Double.parseDouble(row.get(WITHDRAW_COLUMN));
                withdrawSum += currentWithdraw;
                double currentDeposit = Double.parseDouble(row.get(DEPOSIT_COLUMN));
                depositSum += currentDeposit;
                try {
                    //======Разбивка расходов по дате
                    Date date = sdf.parse(row.get(DATE));
                    if (depositMapByDate.containsKey(date)){
                        depositMapByDate.put(date,depositMapByDate.get(date) + currentDeposit);
                    }
                    else{
                        depositMapByDate.put(date,Double.parseDouble(row.get(DEPOSIT_COLUMN)));
                    }
                    //======Разбивка расходов по получателю
                    String moneyRecipient = row.get(DESCRIPTION_COLUMN).split("\\s+")[1];
                    if (depositMapByRecipient.containsKey(moneyRecipient)){
                        depositMapByRecipient.put(moneyRecipient,depositMapByDate.get(date) + currentDeposit);
                    }
                    else{
                        depositMapByRecipient.put(moneyRecipient,Double.parseDouble(row.get(DEPOSIT_COLUMN)));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<HashMap<String,String>> getReportContent(String filePath) throws IOException {
        List<HashMap<String,String>> content = new ArrayList<>();
        String[] headers;
        List<String> fileContent;
        fileContent = Files.readAllLines(Paths.get(filePath), Charset.forName("utf-8"));
        headers = fileContent.get(0).split(",");
        fileContent.stream().skip(1).forEach(line -> {
            var splittedLine = line.split(",");
            if (splittedLine.length == headers.length) {
                HashMap<String,String> hashSet = new HashMap<>();
                for (int i = 0; i < splittedLine.length; i++){
                    hashSet.put(headers[i], splittedLine[i]);
                }
                content.add(hashSet);
            }
        });
        return content;
    }

    private static void print(){
        System.out.println("\nОбщая сумма расходов:\t" + depositSum);
        System.out.println("Общая сумма доходов:\t" + withdrawSum + "\n");
        System.out.println("Подробнее о расходах:");
        System.out.println("\tПолучатель:\tсумма (руб)");
        depositMapByRecipient.forEach((k,v)->{
            System.out.println("\t" + k + ":\t" + v);
        });
        System.out.println("\n\tДата:\tсумма (руб)");
        depositMapByDate.forEach((k,v)->{
            System.out.println("\t" + k + ":\t" + v);
        });
    }
}
