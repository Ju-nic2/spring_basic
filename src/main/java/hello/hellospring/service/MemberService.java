package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    //Test, Service 코드가 같은 repository 객체 사용하도록 바꿈
    // -> MemorymemberRopository data가 static에서 일반 변수로 변경되도 문제 없어짐.
    public MemberService(MemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public Long join(Member member){

        validateDuplicateMember(member);  // 중복회원 방지 // -> 이런 로직 있으면 함수로 빼자


        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {// Optional 내부 메소드
                    throw new IllegalStateException("이미 존재");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
