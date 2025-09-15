package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UserTM {

    private String userId;
    private String userName;
    private String password;
    private String role;
    private LocalDate registrationDate;
}
