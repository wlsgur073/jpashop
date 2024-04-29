package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/*
 * 조회하는 곳에서는 성능을 최적화 시켜준다.
 * 영속성 컨텍스트 더디 체킹을 안하고 DB에 과부화를 주지 않는다.
 * */
@Transactional(readOnly = true) // 데이터 변경하는 service에서는 트랜잭션이 꼭 필요하다.
@RequiredArgsConstructor // final이 명시된 필드만 생성자를 생성해준다.
public class MemberService {

//    @Autowired // Field Injection 변경이 불가능하기에 추천하지 않는다.
    private final MemberRepository memberRepository;


    /*
    Setter Injection의 치명적인 단점은 해당 repository가 runtime 중간에 바꿀 수 있다.
    보통 애프리케이션 로딩 시점에, 스프링이 올라오는 타이밍에 이미 어노테이션들은 설정이 끝난다. 그러므로 해당 방식은 추천하지 않는다.
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */


    /*
    * 해당 Service가 생성될 때 injection을 해준다.
    * 셍성자 인젝션을 쓰면 중간에 set 할 수 없으며, test case를 작성할 때, 파라미터에 직접 주입을 해줘야 되기에
    * 해당 부분을 놓치지 않고 명확하게 무엇이 필요한지 알 수 있다.
    *
    * 해당 생성자 인젝션은 lombok에서 제공하는 RequiredArgsConstructor라는 어노테이션을 이용하여 간단하게 코딩할 수 있다.
    @Autowired // Constructor Injection
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional // 해당 service에서는 읽기(조회) 기능이 비중이 많기에 쓰기 기능에만 따로 readOnly false 설정을 해준다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        // EXCEPTION
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findOne(Long meberId) {
        return memberRepository.findOne(meberId);
    }
}
