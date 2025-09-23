package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PaymentTM {

    private String paymentId;
    private String amount;
    private String paymentDate;
    private String paymentMethod;
    private String status;
    private String studentId;
}
