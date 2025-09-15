package lk.ijse.elitedrivingschool.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class InstructorDTO {

    private String instructorId;
    private String fullName;
    private String email;
    private String phone;
    private String specialization;
}
