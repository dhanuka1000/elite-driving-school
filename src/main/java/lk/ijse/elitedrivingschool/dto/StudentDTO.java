package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.util.List;

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
    private String dob;
    private String address;
    private List<String> courseIds;

}
