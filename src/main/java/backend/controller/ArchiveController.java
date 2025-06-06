package backend.controller;

import backend.dto.ArchiveRequestDto;
import backend.dto.ArchiveResponseDto;
import backend.service.ArchiveService;
import backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/archives")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    /**
     * 1) 오늘의 활동 기록 추가
     *    POST /api/archives/today
     *    Body: {
     *      "startTime": "09:30",
     *      "endTime":   "10:45",
     *      "category":  "Read book",
     *      "content":   "book name..."
     *    }
     */
    @PostMapping("/today")
    public ResponseEntity<?> recordToday(
            @Valid @RequestBody ArchiveRequestDto req
    ) {
        try {
            archiveService.recordTodayArchive(req);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 2) 특정 날짜의 활동 기록 조회
     *    GET /api/archives/{date}
     *    date 형식: yyyy-MM-dd
     */
    @GetMapping("/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") String dateStr) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr); // ISO yyyy-MM-dd
        } catch (DateTimeParseException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error",
                            "날짜 형식이 잘못되었습니다. 'yyyy-MM-dd' 형태로 요청해주세요."));
        }

        List<ArchiveResponseDto> list = archiveService.getArchivesByDate(date);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}
