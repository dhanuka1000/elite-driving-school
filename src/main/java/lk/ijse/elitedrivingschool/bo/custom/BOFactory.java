package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.bo.custom.impl.*;
import lk.ijse.elitedrivingschool.dao.custom.impl.CourseDAOImpl;
import lk.ijse.elitedrivingschool.dao.custom.impl.InstructorDAOImpl;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{

        COURSE,
        INSTRUCTOR,
        LESSON,
        PAYMENT,
        STUDENT,
        USER
    }

    public <Hello extends SuperBO> Hello getBO(BOTypes boType) {
        return switch (boType) {
            case COURSE -> (Hello) new CourseBOImpl();
            case INSTRUCTOR -> (Hello) new InstructorBOImpl();
            case LESSON -> (Hello) new LessonBOImpl();
            case PAYMENT -> (Hello) new PaymentBOImpl();
            case STUDENT -> (Hello) new StudentBOImpl();
            case USER -> (Hello) new UserBOImpl();
        };
    }
}
