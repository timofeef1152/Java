import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkedPurchaseListMapper {
    private int studentId;
    private int courseId;
    private int price;
    private Date subDate;
}