package lk.ijse.elitedrivingschool.bo.util;

import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.LessonDAO;
import lk.ijse.elitedrivingschool.dao.custom.StudentDAO;
import lk.ijse.elitedrivingschool.dto.*;
import lk.ijse.elitedrivingschool.entity.*;

import java.sql.SQLException;
import java.util.Optional;

public class EntityDTOConverter {

    private final LessonDAO lessonDAO = DAOFactory.getInstance().getDAO(DAOTypes.LESSON);
    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);

    public StudentDTO getStudentDTO(Student student) {
        return new StudentDTO(
                student.getStudentId(),
                student.getFullName(),
                student.getEmail(),
                student.getPhone(),
                student.getDob(),
                student.getAddress(),
                student.getLesson() != null ? student.getLesson().getLessonId() : null
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

    public Student getStudent(StudentDTO dto) throws SQLException, ClassNotFoundException {
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDob(dto.getDob());
        student.setAddress(dto.getAddress());

        if (dto.getLesson() != null) {
            Optional<Lesson> lessonOptional = lessonDAO.findById(dto.getLesson());
            if (lessonOptional.isPresent()) {
                student.setLesson(lessonOptional.get());
            } else {
                throw new SQLException("Lesson not found with ID: " + dto.getLesson());
            }
        }
        return student;
    }

    public UserDTO getUserDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User getUser(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

    public InstructorDTO getInstructorDTO(Instructor instructor) {
        return new InstructorDTO(
                instructor.getInstructorId(),
                instructor.getFullName(),
                instructor.getEmail(),
                instructor.getPhone(),
                instructor.getSpecialization()
        );
    }

    public Instructor getInstructor(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(dto.getInstructorId());
        instructor.setFullName(dto.getFullName());
        instructor.setEmail(dto.getEmail());
        instructor.setPhone(dto.getPhone());
        instructor.setSpecialization(dto.getSpecialization());
        return instructor;
    }

    public CourseDTO getCourseDTO(Course course) {
        return new CourseDTO(
                course.getCourseId(),
                course.getName(),
                course.getDuration(),
                course.getFee(),
                course.getDescription()
        );
    }

    public Course getCourse(CourseDTO dto) {
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setName(dto.getName());
        course.setDuration(dto.getDuration());
        course.setFee(dto.getFee());
        course.setDescription(dto.getDescription());
        return course;
    }

    public PaymentDTO getPaymentDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getStudent() != null ? payment.getStudent().getStudentId() : null
        );
    }

    public Payment getPayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {

        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());

        if (dto.getStudentId() != null) {
            Optional<Student> studentOptional = studentDAO.findById(dto.getStudentId());
            if (studentOptional.isPresent()) {
                payment.setStudent(studentOptional.get());
            } else {
                throw new SQLException("Lesson not found with ID: " + dto.getStudentId());
            }
        }
        return payment;
    }
}
