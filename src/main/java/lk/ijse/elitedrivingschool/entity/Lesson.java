package lk.ijse.elitedrivingschool.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lession_id")
    private String sessionId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "duration")
    private String duration;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "user_id")
    private Instructor instructor;

}