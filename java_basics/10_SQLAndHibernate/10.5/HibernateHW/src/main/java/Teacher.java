import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "Teachers")
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int salary;
    private int age;
}
