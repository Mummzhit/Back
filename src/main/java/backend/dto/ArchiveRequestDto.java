package backend.dto;

import backend.domain.ArchiveCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@NoArgsConstructor
public class ArchiveRequestDto {

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotNull
    private ArchiveCategory category;

    private String content;

    private static final DateTimeFormatter AMPM_FORMATTER =
            DateTimeFormatter.ofPattern("hh:mm a");

    public LocalTime parseStartTime() {
        try {
            // 대소문자 섞여 들어와도 파싱되게 upper–lower 처리
            return LocalTime.parse(startTime.trim().toUpperCase(), AMPM_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "startTime 형식이 잘못되었습니다. 'hh:mm AM/PM' 형태로 요청해주세요."
            );
        }
    }

    public LocalTime parseEndTime() {
        try {
            return LocalTime.parse(endTime.trim().toUpperCase(), AMPM_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "endTime 형식이 잘못되었습니다. 'hh:mm AM/PM' 형태로 요청해주세요."
            );
        }
    }
}
