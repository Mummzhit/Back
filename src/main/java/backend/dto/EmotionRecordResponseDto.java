package backend.dto;

import backend.domain.Emotion;
import backend.domain.EmotionRecord;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmotionRecordResponseDto {
    private LocalDate day;     // 기록 날짜
    private Emotion emotion;   // 감정(8개 중 하나)

    public EmotionRecordResponseDto(LocalDate day, Emotion emotion) {
        this.day = day;
        this.emotion = emotion;
    }

    // Entity → DTO 변환 메서드
    public static EmotionRecordResponseDto of(EmotionRecord record) {
        return new EmotionRecordResponseDto(record.getDay(), record.getEmotion());
    }
}
