import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Connection {
    @Getter
    private String lineNumber;
    @Getter
    private String description;
}
