package lk.ijse.elitedrivingschool.config;

import lk.ijse.elitedrivingschool.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        try (var input = FactoryConfiguration.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find hibernate.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load hibernate.properties", e);
        }

        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class)
                .addAnnotatedClass(Enrolment.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Lesson.class)
                .addAnnotatedClass(Learning.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Instructor.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public FactoryConfiguration getInstance() {
        return (factoryConfiguration == null)
                ? factoryConfiguration = new FactoryConfiguration()
                : factoryConfiguration;
    }

    public Session getSession() {

        return sessionFactory.openSession();
    }
}
