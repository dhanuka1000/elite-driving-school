package lk.ijse.elitedrivingschool.dto.tm;

import lk.ijse.elitedrivingschool.entity.Lesson;
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
    private LocalDate dob;
    private String phone;
    private String email;
    private String address;
    private Lesson lesson;
}
