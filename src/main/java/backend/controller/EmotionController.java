package backend.controller;

import backend.domain.Emotion;
import backend.dto.EmotionRecordResponseDto;
import backend.service.EmotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    /**
     * 1) 오늘 감정 기록하기
     *    POST /api/emotions/today
     *    Body: { "emotion": "JOY" }
     */
    @PostMapping("/today")
    public ResponseEntity<?> recordToday(@RequestBody Map<String, String> request){
        // 프론트에서 { "emotion": "JOY" } 형태로 보냄
        String emotionStr = request.get("emotion");
        try {
            Emotion emotion = Emotion.valueOf(emotionStr);
            emotionService.recordTodayEmotion(emotion);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // enum 변환 실패 (JOY, SADNESS 등이 아닌 값을 보냈을 때)
            return ResponseEntity.badRequest().body(Map.of("error", "잘못된 감정 값입니다."));
        } catch (RuntimeException e) {
            // 이미 기록했거나 회원이 없을 때
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 2) 최근 7일 감정 조회하기
     *    GET /api/emotions/weekly
     *    → List<EmotionRecordResponseDto> 반환
     */
    @GetMapping("/weekly-emotion-report")
    public ResponseEntity<List<EmotionRecordResponseDto>> getWeeklyEmotions() {
        List<EmotionRecordResponseDto> dtos = emotionService.getLast7DaysEmotions();
        return ResponseEntity.ok(dtos);
    }

    //PathVariable로 입력
    //GET: localhost:8080/api/emotions/2025-06-05
    //위와 같은 형태로 입력되어야 함
    @GetMapping("/{date}")
    public ResponseEntity<?> getEmotionByDate(@PathVariable("date") String dateStr) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(dateStr); // "yyyy-MM-dd"로 파싱
        } catch (DateTimeParseException ex) {
            // 잘못된 날짜 형식으로 들어온 경우
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "날짜 형식이 잘못되었습니다. 'yyyy-MM-dd' 형태로 요청해주세요."));
        }

        Optional<EmotionRecordResponseDto> optionalDto = emotionService.getEmotionByDate(parsedDate);
        if (optionalDto.isEmpty()) {
            // 해당 날짜에 기록이 없으면 204 No Content를 반환하거나 404 Not Found 중 선택
            return ResponseEntity.noContent().build();
        }

        // 기록이 있으면 DTO를 200 OK로 응답
        return ResponseEntity.ok(optionalDto.get());
    }
}
