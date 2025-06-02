package backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 리프레시 토큰을 이용해 새로운 액세스 토큰을 요청할 때 사용하는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {

    @NotBlank(message = "Access Token 은 필수 입력 값입니다.")
    private String accessToken;

    @NotBlank(message = "Refresh Token 은 필수 입력 값입니다.")
    private String refreshToken;
}
