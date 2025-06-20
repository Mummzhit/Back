// src/main/java/backend/exception/GlobalExceptionHandler.java
package backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 이메일 중복
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailExists(EmailExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    // 닉네임 중복
    @ExceptionHandler(NicknameExistsException.class)
    public ResponseEntity<Map<String,String>> handleNicknameExists(NicknameExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    // JPA @Column(unique=true) 제약 위반
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String cause = ex.getMostSpecificCause().getMessage();
        String message = cause.contains("login_id")
                ? "이미 존재하는 이메일입니다."
                : cause.contains("nickname")
                ? "이미 존재하는 닉네임입니다."
                : "데이터 무결성 오류가 발생했습니다.";
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", message));
    }

    // 그 외 예외 처리 (선택)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleAll(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "서버 오류가 발생했습니다."));
    }
}
