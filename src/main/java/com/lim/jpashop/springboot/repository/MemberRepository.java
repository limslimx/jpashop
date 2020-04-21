package com.lim.jpashop.springboot.repository;

import com.lim.jpashop.springboot.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager em;

//    spring boot(정확히는 spring boot jpa)가 @PersistenceContext를 @Autowired로 대신할 수 있게 해주고 그러면 생성자를 통한 injection이 가능해짐
//    @PersistenceContext
//    private EntityManager em;
//
//    public MemberRepository(EntityManager em){
//        this.em=em;
//    }

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
