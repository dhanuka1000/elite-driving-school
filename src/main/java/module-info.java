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

    opens lk.ijse.elitedrivingschool.controller to javafx.fxml;

    opens lk.ijse.elitedrivingschool.entity to org.hibernate.orm.core;
    opens lk.ijse.elitedrivingschool.config to jakarta.persistence;

    exports lk.ijse.elitedrivingschool;
    exports lk.ijse.elitedrivingschool.controller;
    exports lk.ijse.elitedrivingschool.dto;
    opens lk.ijse.elitedrivingschool.dto to javafx.fxml;
}