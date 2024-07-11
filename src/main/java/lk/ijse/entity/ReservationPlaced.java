package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ReservationPlaced {
    private  Reservation reservation;
    private List<ReservationDetails> odList;
}
