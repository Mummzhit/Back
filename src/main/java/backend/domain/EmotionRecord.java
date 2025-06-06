package backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "emotion_record",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "day"})
)
public class EmotionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate day;

    //8개 감정
    /*
    ANGER,        // 분노
    ANTICIPATION, // 기대
    JOY,          // 기쁨
    SURPRISE,     // 놀라움
    TRUST,        // 신뢰
    SADNESS,      // 슬픔
    DISGUST,      // 역겨움
    FEAR          // 두려움
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Emotion emotion;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public EmotionRecord(Member member, LocalDate day, Emotion emotion) {
        this.member = member;
        this.day = day;
        this.emotion = emotion;
    }


}
