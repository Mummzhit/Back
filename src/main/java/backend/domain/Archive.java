package backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "archive")
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    //6개 활동
    /*
    KNOWLEDGE,   // 지식·공부
    EXERCISE,    // 운동·레저
    ORGANIZE,    // 정리·관리
    HOBBY,       // 취미·창작
    MEDITATION,  // 명상·휴식
    SOCIAL       // 소통·관계
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ArchiveCategory category;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Archive(Member member,
                   LocalDate date,
                   LocalTime startTime,
                   LocalTime endTime,
                   ArchiveCategory category,
                   String content) {
        this.member    = member;
        this.date      = date;
        this.startTime = startTime;
        this.endTime   = endTime;
        this.category  = category;
        this.content   = content;
    }
}
