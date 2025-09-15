package lk.ijse.elitedrivingschool.dto.tm;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LessionTm {

    private String lessionId;
    private LocalDateTime date;
    private String duration;
    private String location;
    private String status;
    private String instructorId;
}
