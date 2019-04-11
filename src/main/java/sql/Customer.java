package sql;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer {
    private int id;
    private String name;
    private int age;
}
