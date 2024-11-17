package org.test.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.test.member.entity.Member;
import org.test.member.repository.MemberRepository;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberServiceTest {

//    private final MemberService memberService;
//
//    private final MemberRepository memberRepository;
//
//    public MemberServiceTest(MemberService memberService, MemberRepository memberRepository) {
//        this.memberService = memberService;
//        this.memberRepository = memberRepository;
//    }

    @MockBean
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    public void setMemberService() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    void getUserById() {
        Member member = new Member("sixman", "TENANT_000000000001", "6666","육장훈");
        Mockito.when(memberRepository.findByMemberId("sixman")).thenReturn(Optional.of(member));

        // When
        Optional<Member> result = memberService.getUserById("sixman");

//        Member member = new Member("sixman", "TENANT_000000000001", "6666","육장훈");
//        memberRepository.save(member);
//
//        Optional<Member> result = memberService.getUserById("sixman");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getMemberNm()).isEqualTo("육장훈");
    }
}