package backend.service;

import backend.domain.Emotion;
import backend.domain.EmotionRecord;
import backend.domain.Member;
import backend.dto.EmotionRecordResponseDto;
import backend.repository.EmotionRecordRepository;
import backend.repository.MemberRepository;
import backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRecordRepository emotionRecordRepository;
    private final MemberRepository memberRepository;

    //오늘 감정 기록
    @Transactional
    public void recordTodayEmotion(Emotion emotion){
        //로그인한 member ID
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        //날짜
        LocalDate today = LocalDate.now();

        //하루 한번만 기록
        emotionRecordRepository.findByMemberAndDay(member, today)
                .ifPresent(record -> {
                    throw new RuntimeException("이미 오늘 감정을 기록했습니다.");
                });

        //저장
        EmotionRecord newRecord = new EmotionRecord(member, today, emotion);
        emotionRecordRepository.save(newRecord);
    }

    @Transactional(readOnly = true)
    public List<EmotionRecordResponseDto> getLast7DaysEmotions() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6); // 오늘 포함해서 총 7일

        List<EmotionRecord> records = emotionRecordRepository
                .findAllByMemberAndDayBetween(member, sevenDaysAgo, today);

        // 날짜 오름차순 정렬 후 DTO로 매핑
        /*
            recordTodayEmotion(Emotion) → 오늘 날짜에 EmotionRecord가 있으면 예외, 없으면 저장
            getLast7DaysEmotions() → 오늘로부터 6일 전부터 오늘까지의 기록을 모두 찾아서 EmotionRecordResponseDto로 변환
         */
        return records.stream()
                .sorted((a, b) -> a.getDay().compareTo(b.getDay()))
                .map(EmotionRecordResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 특정 날짜(day)에 기록된 감정을 조회합니다.
     * @param day 조회할 날짜 (예: 2025-06-03)
     * @return 해당 날짜의 EmotionRecordResponseDto (감정, 날짜), 기록이 없으면 Optional.empty()
     */
    @Transactional(readOnly = true)
    public Optional<EmotionRecordResponseDto> getEmotionByDate(LocalDate day) {
        // 현재 로그인한 회원 ID
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        // repository에서 해당 날짜의 레코드를 찾아 DTO로 변환
        return emotionRecordRepository.findByMemberAndDay(member, day)
                .map(EmotionRecordResponseDto::of);
    }
}
