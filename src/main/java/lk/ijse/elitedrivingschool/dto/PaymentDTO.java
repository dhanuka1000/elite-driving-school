package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PaymentDTO {

    private String paymentId;
    private String amount;
    private String paymentDate;
    private String paymentMethod;
    private String status;
    private String studentId;

}
