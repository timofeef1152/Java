import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Station {
    @Getter
    private Line line;
    @Getter
    private String name;
    @Getter
    private List<Connection> connections;

    @Override
    public String toString(){
        return line.toString() + ": " + name;
    }
}
