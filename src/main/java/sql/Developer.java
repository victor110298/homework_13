package sql;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Developer {
    private int id;
    private String name;
    private int age;
    private String gender;
    private double salary;
}
