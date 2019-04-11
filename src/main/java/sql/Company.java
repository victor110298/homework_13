package sql;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Company {
    private int id;
    private String name;
    private String address;
}
