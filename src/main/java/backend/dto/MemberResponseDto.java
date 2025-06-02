package backend.dto;

import backend.domain.Authority;
import backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원 가입 후 생성된 Member 정보를 클라이언트에 반환하거나,
 * 회원 조회 API에서 Member 엔티티를 DTO로 변환해줄 때 사용
 */
@Getter
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private Authority authority;
    private String createdAt; // 예: "2025-06-03T14:22:10"

    // Member 엔티티를 받아서 DTO로 변환해 주는 정적 팩토리 메서드
    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authority(member.getAuthority())
                .createdAt(member.getCreatedAt().toString())
                .build();
    }
}
