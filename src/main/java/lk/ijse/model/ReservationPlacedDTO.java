package lk.ijse.model;

import lk.ijse.entity.Reservation;
import lk.ijse.entity.ReservationDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ReservationPlacedDTO {
    private Reservation reservation;
    private List<ReservationDetails> odList;
}
