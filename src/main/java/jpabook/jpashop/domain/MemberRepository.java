package jpabook.jpashop.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // Spring boot에서 EntityManager을 알아서 생성자 주입해준다.

    public Long save(Member member) {
        em.persist(member);
        // 사이드 이펙트를 일으키는 커멘드 유형이기에 리턴값을 거의 만들지 않는다.
        // 그러나 아이디 정도 반환하면 다시 조회가 가능하기에 return 해본다.
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
