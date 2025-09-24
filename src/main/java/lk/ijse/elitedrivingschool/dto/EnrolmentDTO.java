package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EnrolmentDTO {

    private String id;
    private List<String> studentId;
    private List<String> courseIds;
    private String enrolmentDate;
    private String upfrontPaid;

}
