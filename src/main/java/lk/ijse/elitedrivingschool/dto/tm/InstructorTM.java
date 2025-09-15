package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class InstructorTM {

    private String instructorId;
    private String fullName;
    private String email;
    private String phone;
    private String specialization;
}
