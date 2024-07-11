package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MealDTO {
    private String MealId;
    private String MealName;
    private double Price;
    private java.sql.Date Date;
}
