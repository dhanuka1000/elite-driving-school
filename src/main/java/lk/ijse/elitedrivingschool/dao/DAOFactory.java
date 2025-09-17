package lk.ijse.elitedrivingschool.dao;

import lk.ijse.elitedrivingschool.dao.custom.SuperDAO;
import lk.ijse.elitedrivingschool.dao.custom.impl.*;


public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoType) {
        return switch (daoType) {
            case COURSE -> (T) new CourseDAOImpl();
            case ENROLMENT -> (T) new EnrolmentDAOImpl();
            case INSTRUCTOR -> (T) new InstructorDAOImpl();
            case LESSON -> (T) new LessonDAOImpl();
            case PAYMENT -> (T) new PaymentDAOImpl();
            case STUDENT -> (T) new StudentDAOImpl();
            case USER -> (T) new UserDAOImpl();
        };
    }
}
