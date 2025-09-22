package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LessionDTO {

    private String lessionId;
    private String name;
    private LocalDate date;
    private String time;
    private String location;
}
