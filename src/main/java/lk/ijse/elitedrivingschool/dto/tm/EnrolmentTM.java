package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EnrolmentTM {

    private String id;
    private List<String> courseIds;
    private List<String> studentId;
    private String enrolmentDate;
    private String upfrontPaid;

    public String getCourseId() {
        if (courseIds == null || courseIds.isEmpty()) return "";
        return String.join(", ", courseIds);
    }
}
