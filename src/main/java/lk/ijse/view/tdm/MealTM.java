package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@NoArgsConstructor
@AllArgsConstructor
public class MealTM {
    private String MealId;
    private String MealName;
    private double Price;
    private java.sql.Date Date;
}
