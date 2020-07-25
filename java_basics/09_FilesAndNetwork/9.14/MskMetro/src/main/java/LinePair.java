import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LinePair {
    @Getter
    private Line line1;
    @Getter
    private Line line2;

    @Override
    public String toString(){
        return getLine1().toString() + " | " + getLine2().toString();
    }
}
