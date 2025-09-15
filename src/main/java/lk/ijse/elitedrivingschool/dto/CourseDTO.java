package lk.ijse.elitedrivingschool.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class CourseDTO {

    private String courseId;
    private String name;
    private String duration;
    private Double fee;
    private String description;
}
