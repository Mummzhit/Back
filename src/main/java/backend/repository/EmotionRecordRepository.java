package backend.repository;

import backend.domain.EmotionRecord;
import backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmotionRecordRepository extends JpaRepository<EmotionRecord, Long> {

    /**
     * 해당 회원이 특정 날짜에 이미 감정을 기록했는지 확인
     */
    Optional<EmotionRecord> findByMemberAndDay(Member member, LocalDate day);

    /**
     * 해당 회원이 dateFrom ~ dateTo 기간에 기록한 레코드 전부 조회
     */
    List<EmotionRecord> findAllByMemberAndDayBetween(
            Member member,
            LocalDate dateFrom,
            LocalDate dateTo
    );
}
