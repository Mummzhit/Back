package backend.dto;

import backend.domain.Archive;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ArchiveResponseDto {

    private LocalDate date;        // 기록 날짜
    private LocalTime startTime;   // 시작 시간
    private LocalTime endTime;     // 종료 시간
    private String category;       // 활동 6가지
    private String content;        // 세부 내용

    public ArchiveResponseDto(Archive archive) {
        this.date       = archive.getDate();
        this.startTime  = archive.getStartTime();
        this.endTime    = archive.getEndTime();
        this.category   = String.valueOf(archive.getCategory());
        this.content    = archive.getContent();
    }

    public static ArchiveResponseDto of(Archive archive) {
        return new ArchiveResponseDto(archive);
    }
}
