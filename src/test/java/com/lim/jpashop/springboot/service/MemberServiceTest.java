package com.lim.jpashop.springboot.service;

import com.lim.jpashop.springboot.domain.Member;
import com.lim.jpashop.springboot.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //junit 실행 시에 Spring과 엮어서 사용할 것이라면 이렇게 지정해주면 된다
@SpringBootTest //Spring컨테이너 안에서 이 테스트를 돌릴 수 있도록 도와주는 것으로 이게 없으면 @Autowired 같은 어노테이션을 못 쓴다
@Transactional //기본적으로 @Transactional은 커밋을 하지만 테스트 케이스에서는 커밋이 아닌 롤백을 한다. 그렇기 때문에 em.persist()를 해봤자 커밋이 안되니 영속성 컨텍스테에만 엔티티가 저장될 뿐이
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false) -> 이렇게 하면 테스트 케이스에서도 강제로 롤백이 아니라 커밋을 시킬 수 있음
//    이 방법 말고도 em.flush()를 통해 강제로 영속성 컨텍스트의 쿼리를 DB로 보내는 방법도 있다
    public void 회원가입() throws Exception{
        //given
        Member member=new Member();
        member.setName("lim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId)); //JPA에서는 같은 트랜잭션 내에서 같은 PK값(@Id값)을 가지는 엔티티는 여러개가 아닌 하나만 존재한다

    }

    @Test(expected = IllegalStateException.class) //이걸 이용하면 아래 try ... catch ... 구문을 지워도 됨
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1=new Member();
        member1.setName("lim");

        Member member2=new Member();
        member2.setName("lim");

        //when
        memberService.join(member1);
        memberService.join(member2);
//        try{
//            memberService.join(member2); //예외가 발생해야 한다!!!
//        }catch (IllegalStateException e){
//            return;
//        }

        //then
        fail("예외가 발생해야 한다."); //이게 실행되면 오류가 발생하도록 함

    }

}