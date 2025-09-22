package lk.ijse.elitedrivingschool.bo.util;

import lk.ijse.elitedrivingschool.dto.LessionDTO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.entity.Lesson;
import lk.ijse.elitedrivingschool.entity.Student;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EntityDTOConverter {

    public StudentDTO getStudentDTO(Student student) {
        return new StudentDTO(
                student.getStudentId(),
                student.getFullName(),
                student.getEmail(),
                student.getPhone(),
                student.getDob(),
                student.getAddress(),
                student.getLesson()
        );
    }

    public LessionDTO getLessonDTO(Lesson lesson) {
        return new LessionDTO(
                lesson.getLessonId(),
                lesson.getName(),
                lesson.getDate(),
                lesson.getTime(),
                lesson.getLocation()
        );
    }

    public Lesson getLesson(LessionDTO dto) {
        Lesson lesson = new Lesson();

        lesson.setLessonId(dto.getLessionId());
        lesson.setName(dto.getName());
        lesson.setDate(dto.getDate());
        lesson.setTime(dto.getTime());
        lesson.setLocation(dto.getLocation());
        return lesson;
    }

    public Student getStudent(StudentDTO dto) {
        Student student = new Student();
        Lesson lesson = new Lesson();
        student.setStudentId(dto.getStudentId());
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDob(dto.getDob());
        student.setAddress(dto.getAddress());
        student.setLesson(dto.getLesson());
        return student;
    }
}