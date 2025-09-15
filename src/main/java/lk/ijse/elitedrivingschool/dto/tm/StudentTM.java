package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class StudentTM {

    private String studentId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;
    private String lessionId;
}
