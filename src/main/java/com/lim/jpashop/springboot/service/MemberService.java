package com.lim.jpashop.springboot.service;

import com.lim.jpashop.springboot.domain.Member;
import com.lim.jpashop.springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true) //트랜잭션 처리기능 향상을 위해 조회만 가능하도록 한다. 아래서 만약에 조회 이외의 기능을 사용한다면 따로 @Transactional을 붙여주면 된다
@Service
public class MemberService {

    private final MemberRepository memberRepository;

//    최신버전의 스프링에서는 생성자가 하나만 있는 경우에는 @Autowired 없이도 자동으로 injection을 해준다
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     * @param member Member Entity
     * @return member`s Id(PK) 반환
     */
    @Transactional //위에서 @Transactional에 readonly를 부여했기 때문에 조회 기능 이외의 기능을 쓰는 이 메서드에 @Transactional을 지정해줘야 한다
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     *  전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * Id(PK)를 통한 엔티티 조회
     * @param memberId Member`s Id
     * @return Member엔티티를 반환
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}