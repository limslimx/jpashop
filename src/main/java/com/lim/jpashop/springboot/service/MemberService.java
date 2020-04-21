package com.lim.jpashop.springboot.service;

import com.lim.jpashop.springboot.domain.Member;
import com.lim.jpashop.springboot.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
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
