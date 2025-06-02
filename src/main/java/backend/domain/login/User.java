package backend.domain.login;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login_id;

    @Column(unique = true)
    private String nickname;

    //중복 관련해서 원하면 추가하자
    private String password;

    //SpringSecurity 사용할거면 권한 이런 것도 만들 수 있는데, 지금은 하지말자

    @CreationTimestamp
    private LocalDateTime createdAt;
}
