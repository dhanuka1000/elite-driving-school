package lk.ijse.elitedrivingschool.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LessionDTO {

    private String lessionId;
    private LocalDateTime date;
    private String duration;
    private String location;
    private String status;
    private String instructorId;
}
