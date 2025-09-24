module lk.ijse.elitedrivingschool {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;

    requires java.naming;
    requires modelmapper;
    requires bcrypt;
    requires java.desktop;
    requires jbcrypt;

    // Controllers need reflection for FXML injection
    opens lk.ijse.elitedrivingschool.controller to javafx.fxml;

    // Hibernate needs reflection for entities
    opens lk.ijse.elitedrivingschool.entity to org.hibernate.orm.core;

    // JPA configuration
    opens lk.ijse.elitedrivingschool.config to jakarta.persistence;

    // DTOs (normal + TM classes) need reflection for TableView + FXML binding
    opens lk.ijse.elitedrivingschool.dto to javafx.fxml, javafx.base;
    opens lk.ijse.elitedrivingschool.dto.tm to javafx.base;

    // Export packages for general access
    exports lk.ijse.elitedrivingschool;
    exports lk.ijse.elitedrivingschool.controller;
    exports lk.ijse.elitedrivingschool.dto;
    exports lk.ijse.elitedrivingschool.dto.tm;
}
