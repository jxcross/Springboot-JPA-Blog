package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 됨
// @Repository 생략 가능 
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// SELECT * FROM user WHERE username=1?;
	Optional<User> findByUsername(String username);

	// JPA Naming 쿼리
	// SELECT * FROM user WHERE username=? AND password=?
	// security 사용으로 대체됨!
//	User findByUsernameAndPassword(String username, String password);
	
//	@Query(value = "SELECT * FROM user WHERE username=? AND password=?", nativeQuery = true)
//	User login(String username, String password);
}
