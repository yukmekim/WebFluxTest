package org.test.member.service;

import org.springframework.stereotype.Service;
import org.test.member.entity.Member;
import org.test.member.repository.MemberRepository;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> getUserById(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }
}
