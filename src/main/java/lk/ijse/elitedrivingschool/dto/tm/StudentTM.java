package lk.ijse.elitedrivingschool.dto.tm;

import lk.ijse.elitedrivingschool.entity.Lesson;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class StudentTM {

    private String studentId;
    private String fullName;
    private String dob;
    private String phone;
    private String email;
    private String address;
    private List<String> courseIds;

    public String getCourseId() {
        if (courseIds == null || courseIds.isEmpty()) return "";
        return String.join(", ", courseIds);
    }
}
