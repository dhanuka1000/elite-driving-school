package lk.ijse.elitedrivingschool.bo.util;

import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.entity.Student;

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
    
    public Student getStudent(StudentDTO dto) {
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDob(dto.getDob());
        student.setAddress(dto.getAddress());
        student.setLesson(dto.getLessionId());
        return student;
    }
}