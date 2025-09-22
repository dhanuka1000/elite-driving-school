package lk.ijse.elitedrivingschool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @Column(name = "lesson_id", nullable = false, length = 20)
    private String lessonId;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false, length = 20)
    private String time;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;
}