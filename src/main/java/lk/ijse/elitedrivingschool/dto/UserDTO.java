package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UserDTO {

    private String userId;
    private String userName;
    private String password;
    private String role;
}
