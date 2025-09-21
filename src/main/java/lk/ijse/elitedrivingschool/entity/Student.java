package lk.ijse.elitedrivingschool.entity;

import jakarta.persistence.*;
import lk.ijse.elitedrivingschool.dto.LessionDTO;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "address", length = 200)
    private String address;

    @ManyToOne
    @JoinColumn(name = "les_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Enrolment> enrolments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Payment> payments = new ArrayList<>();

}