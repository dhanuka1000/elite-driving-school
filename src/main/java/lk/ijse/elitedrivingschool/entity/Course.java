package lk.ijse.elitedrivingschool.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_id", nullable = false, length = 20)
    private String courseId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "fee", nullable = false)
    private String fee;

    @Column(name = "description")
    private String description;
}