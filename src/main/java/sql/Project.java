package sql;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    private double cost;
    private Date date;
}
