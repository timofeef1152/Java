import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Students")
@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    @Column(name = "registration_date")
    private Date registrationDate;

//    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
//    private List<Subscription> subscriptions;
}
