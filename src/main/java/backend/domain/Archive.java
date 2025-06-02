package backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "archive",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"})
)
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 아이디
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    //회고 날짜(2025-06-02)
    @Column(nullable = false)
    private LocalDate date;

    //타이머 시작
    @Column(name = "startTime", nullable = false)
    private LocalTime startTime;

    //타이머 종료
    @Column(name = "endTime", nullable = false)
    private LocalTime endTime;

    //활동종류
    //운동, 등등
    @Column(nullable = false, length = 20)
    private String category;

    //회고 내용
    @Lob
    @Column(name = "content", nullable = true)
    private String content;

    //— 생성 시각
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

}
