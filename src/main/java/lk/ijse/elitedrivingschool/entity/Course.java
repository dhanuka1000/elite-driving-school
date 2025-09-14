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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "fee", nullable = false)
    private Double fee;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Enrolment> enrolments = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Lesson> lessons = new ArrayList<>();
}