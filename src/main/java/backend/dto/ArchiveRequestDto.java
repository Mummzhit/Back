package backend.dto;

import backend.domain.ArchiveCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ArchiveRequestDto {

    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ArchiveCategory category;

    private String content;
}
