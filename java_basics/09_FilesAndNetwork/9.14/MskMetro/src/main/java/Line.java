import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Line {
    @Getter
    private String number;
    @Getter
    private String name;
    @Getter
    private String color;
    @Getter
    private List<Station> stationList;

    @Override
    public String toString(){
        return "[" + number + "] " + name;
    }
}
