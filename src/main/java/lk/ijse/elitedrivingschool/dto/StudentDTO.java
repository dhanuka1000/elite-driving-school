package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class StudentDTO {

    private String studentId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;
    private String lessionId;
}
