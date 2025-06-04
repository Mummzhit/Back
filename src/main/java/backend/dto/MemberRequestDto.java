package backend.dto;

import backend.domain.Authority;
import backend.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 회원가입(Sign-Up) 또는 로그인 요청 시 사용하는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 50, message = "비밀번호는 8자 이상 50자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(max = 20, message = "닉네임은 최대 20자까지 가능합니다.")
    private String nickname;

    /**
     * 1) signup() 시 서비스 계층에서 호출할 메서드
     *    비밀번호 인코딩과 기본 권한(ROLE_USER)을 함께 넘겨서 Member 엔티티를 생성하도록 도와줍니다.
     */
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .nickname(this.nickname)
                .authority(Authority.ROLE_USER) // 회원가입 시 기본 권한
                .build();
    }

    /**
     * 2) login() 시 서비스 계층에서 호출할 메서드
     *    UsernamePasswordAuthenticationToken 을 생성하여 AuthenticationManager에 넘기도록 돕습니다.
     */
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
