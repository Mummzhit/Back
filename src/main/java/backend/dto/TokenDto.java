package backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Access Token, Refresh Token 및 만료 시간 등을 함께 반환할 때 사용하는 DTO
 */
@Getter
@AllArgsConstructor
@Builder
public class TokenDto {

    private String grantType;          // 보통 "Bearer"
    private String accessToken;        // 발급된 액세스 토큰
    private Long accessTokenExpiresIn; // 액세스 토큰 만료 시간(타임스탬프 or 남은 밀리초)
    private String refreshToken;       // 발급된 리프레시 토큰
}
