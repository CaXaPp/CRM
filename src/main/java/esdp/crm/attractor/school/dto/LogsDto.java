package esdp.crm.attractor.school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsDto {
    private LocalDateTime date;
    private List<ChangesDto> changes;
    private UserDto author;
    private Long ApplicationId;
}
