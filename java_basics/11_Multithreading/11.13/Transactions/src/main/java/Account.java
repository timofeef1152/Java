import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Account implements Comparable<Account>, Cloneable {
    @Getter
    private String number;
    private volatile long balance;
    private boolean isBlocked;

    @Override
    public int compareTo(Account o) {
        return getNumber().compareTo(o.getNumber());
    }
}
