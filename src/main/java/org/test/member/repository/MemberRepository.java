package org.test.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    // 이름으로 사용자 조회
    Optional<Member> findByMemberId(String memberId);
}
