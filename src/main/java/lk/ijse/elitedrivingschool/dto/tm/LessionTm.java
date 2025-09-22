package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LessionTm {

    private String lessionId;
    private String name;
    private LocalDate date;
    private String time;
    private String location;
}
