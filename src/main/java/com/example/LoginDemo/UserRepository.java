package com.example.LoginDemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //이 파일이 레포지토리임을 명시, 또한 컴포넌트 스캔의 대상에 들어가게됨
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id); //findBy{컬럼명} -> 해당 값을 가지는 user 데이터를 찾아준다. id같은 경우 유니크한 값이므로, 하나의 유저
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
}
