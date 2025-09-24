package lk.ijse.elitedrivingschool.bo.util;

import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.CourseDAO;
import lk.ijse.elitedrivingschool.dao.custom.LessonDAO;
import lk.ijse.elitedrivingschool.dao.custom.StudentDAO;
import lk.ijse.elitedrivingschool.dto.*;
import lk.ijse.elitedrivingschool.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EntityDTOConverter {

    private final LessonDAO lessonDAO = DAOFactory.getInstance().getDAO(DAOTypes.LESSON);
    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);
    private final CourseDAO courseDAO = DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    public StudentDTO getStudentDTO(Student student) {


        List<String> courseIds = null;
        if (student.getEnrolments() != null && !student.getEnrolments().isEmpty()) {
            courseIds = student.getEnrolments().stream()
                    .map(e -> e.getCourse().getCourseId())
                    .toList();
        }

        return new StudentDTO(
                student.getStudentId(),
                student.getFullName(),
                student.getEmail(),
                student.getPhone(),
                student.getDob(),
                student.getAddress(),
                courseIds
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

        if (dto.getCourseIds() != null && !dto.getCourseIds().isEmpty()) {
            student.setEnrolments(new java.util.ArrayList<>());

            for (String courseId : dto.getCourseIds()) {
                Optional<Course> courseOptional = courseDAO.findById(courseId.trim());
                if (courseOptional.isPresent()) {
                    Course course = courseOptional.get();

                    Enrolment enrolment = new Enrolment();
                    enrolment.setId(student.getStudentId() + "-" + courseId);
                    enrolment.setStudent(student);
                    enrolment.setCourse(course);

                    student.getEnrolments().add(enrolment);
                } else {
                    throw new SQLException("Course not found with ID: " + courseId);
                }
            }
        }

        return student;
    }

    public UserDTO getUserDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User getUser(UserDTO dto) {
        if (dto == null) return null;
        return new User(
                dto.getUserId(),
                dto.getUserName(),
                dto.getPassword(),
                dto.getRole()
        );
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

    public EnrolmentDTO getEnrolmentDTO(Enrolment enrolment) {

        ArrayList<String> courseIds = new ArrayList<>();
        if (enrolment.getCourse() != null) {
            courseIds.add(enrolment.getCourse().getCourseId());
        }

        return new EnrolmentDTO(
                enrolment.getId(),
                enrolment.getStudent() != null ? Collections.singletonList(enrolment.getStudent().getStudentId()) : null,
                courseIds,
                enrolment.getEnrolmentDate(),
                enrolment.getUpfrontPaid()
        );
    }

    public Enrolment getEnrolment(EnrolmentDTO dto) throws SQLException {

        Enrolment enrolment = new Enrolment();

        enrolment.setId(dto.getId());
        enrolment.setEnrolmentDate(dto.getEnrolmentDate());
        enrolment.setUpfrontPaid(dto.getUpfrontPaid());

        if (dto.getStudentId() != null && !dto.getStudentId().isEmpty()) {
            Optional<Student> studentOptional = studentDAO.findById(dto.getStudentId().get(0));
            studentOptional.ifPresent(enrolment::setStudent);
        }

        if (dto.getCourseIds() != null && !dto.getCourseIds().isEmpty()) {
            String courseId = dto.getCourseIds().get(0);
            Optional<Course> courseOptional = courseDAO.findById(courseId);
            if (courseOptional.isPresent()) {
                enrolment.setCourse(courseOptional.get());
            } else {
                throw new SQLException("Course not found with ID: " + courseId);
            }
        }

        return enrolment;
    }
}
