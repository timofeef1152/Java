import java.util.HashMap;
import java.util.regex.Pattern;

public class CustomerStorage
{
    private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private HashMap<String, Customer> storage;
    private Pattern pattern;

    public CustomerStorage()
    {
        storage = new HashMap<>();
        pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    public void addCustomer(String data) throws ArrayIndexOutOfBoundsException
    {
        try{
            String[] components = data.split("\\s+");
            if (!isValidEmailFormat(components[2])){
                throw new WrongEmailFormatException("Input error: Wrong email format!");
            }
            if (!isValidPhoneFormat(components[3])){
                throw new WrongPhoneFormatException("Input error! Expected phone format:\n+7********** (12 symbols)");
            }
            String name = components[0] + " " + components[1];
            storage.put(name, new Customer(name, components[3], components[2]));
        } catch (WrongPhoneFormatException e) {
            System.out.println(e.getMessage());
        } catch (WrongEmailFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }

    private boolean isValidPhoneFormat(String phone){
        return phone.startsWith("+7") && phone.length() == 12;
    }

    private boolean isValidEmailFormat(String email){
        return pattern.matcher(email).find();
    }
}