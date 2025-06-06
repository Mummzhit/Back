package backend.service;

import backend.domain.Archive;
import backend.domain.Member;
import backend.dto.ArchiveResponseDto;
import backend.dto.ArchiveRequestDto;
import backend.repository.ArchiveRepository;
import backend.repository.MemberRepository;
import backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final MemberRepository memberRepository;

    /**
     * 오늘의 활동 기록 추가
     */
    @Transactional
    public void recordTodayArchive(ArchiveRequestDto req) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        LocalDate today = LocalDate.now();

        Archive arch = Archive.builder()
                .member(member)
                .date(today)
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .category(req.getCategory())
                .content(req.getContent())
                .build();

        archiveRepository.save(arch);
    }

    /**
     * 특정 날짜의 활동 기록 조회
     */
    @Transactional(readOnly = true)
    public List<ArchiveResponseDto> getArchivesByDate(LocalDate date) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        return archiveRepository
                .findAllByMemberAndDate(member, date)
                .stream()
                .map(ArchiveResponseDto::of)
                .collect(Collectors.toList());
    }
}
