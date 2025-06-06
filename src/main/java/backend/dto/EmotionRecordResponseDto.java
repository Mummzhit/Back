package backend.dto;

import backend.domain.Emotion;
import backend.domain.EmotionRecord;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
public class EmotionRecordResponseDto {
    private LocalDate day;     // 기록 날짜
    private Emotion emotion;   // 감정(8개 중 하나)
    private String nickname; //유저 닉네임
    private DayOfWeek dayOfWeek; //요일 정보

    public EmotionRecordResponseDto(LocalDate day, Emotion emotion, String nickname) {
        this.day = day;
        this.emotion = emotion;
        this.nickname = nickname;
        this.dayOfWeek = day.getDayOfWeek();
    }

    // Entity → DTO 변환 메서드
    public static EmotionRecordResponseDto of(EmotionRecord record) {
        return new EmotionRecordResponseDto(
                record.getDay(),
                record.getEmotion(),
                record.getMember().getNickname() //멤버로부터 nickname 출력
                );
    }
}
