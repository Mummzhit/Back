package backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loginId", unique = true, nullable = false)
    private String loginId;

    @Column(unique = true, nullable = false)
    private String nickname;

    //중복 관련해서 원하면 추가하자
    @Column(nullable = false)
    private String password;

    //SpringSecurity 사용할거면 권한 이런 것도 만들 수 있는데, 지금은 하지말자

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }

    //Authority
    public enum Authority{
        ROLE_USER, ROLE_ADMIN
    }
}
