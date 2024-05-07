package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);

        //then
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getName()).isEqualTo(member.getName());

        /*
        * 같은 트랜잭션 안에서 meber를 저장하고 조회하면 영속성 컨텍스트가 똑같다
        * 같은 영속성 컨텍스트 안에서 id 값이 같으면 같은 엔티티로 식별한다.
        * 영속성 컨텍스트에서 식별자가 같으면 같은 엔티티로 인식한다고 보면 된다.
        * */
//        assertThat(findMember).isEqualTo(member);
    }
}