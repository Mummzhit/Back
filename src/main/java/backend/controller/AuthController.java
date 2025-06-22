package backend.controller;

import backend.domain.Member;
import backend.dto.*;
import backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, Authentication authentication) {
        // authentication가 null 이 아니면 이미 토큰 있는상태
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "이미 로그인된 상태입니다."));
        }
        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    //로그아웃 구현
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberResponseDto> profile(){
        return ResponseEntity.ok(authService.getMyProfile());
    }
}