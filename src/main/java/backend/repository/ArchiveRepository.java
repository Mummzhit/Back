package backend.repository;

import backend.domain.Archive;
import backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    /**
     * 특정 회원이 지정된 날짜에 남긴 모든 기록 조회
     */
    List<Archive> findAllByMemberAndDate(Member member, LocalDate date);

    /**
     * 하루 기록 건수 카운트
     */
    long countByMemberAndDate(Member member, LocalDate date);
}