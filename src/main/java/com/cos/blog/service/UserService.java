package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해줌
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();	// 1234 원문
		String encPassowrd = encoder.encode(rawPassword);	// 해쉬
		user.setPassword(encPassowrd);
		user.setRole(RoleType.USER	);
		userRepository.save(user);
	}
	
// security 사용으로 대체됨 
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
	@Transactional
	public void 회원수정(User user) {
		// 수정 시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update 문을 날려준다
		User persistence = userRepository.findById(user.getId())
										.orElseThrow(()->{
											return new IllegalArgumentException("회원 찾기 실패");
										});
		// Validation 체크 => oauth 필드에 값이 없으면 수정 가능
		// 웹 브라우저로 접근하는 경우 => updateForm.jsp 에서 담당하고,
		// 그 외에 postman 등으로 접근하는 경우, 여기서 담당
		if (persistence.getOauth() == null || persistence.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistence.setPassword(encPassword);
			persistence.setEmail(user.getEmail());
		}
		
		// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 자동 발생
		// 영속화된 persistence 객체의 변화가 감지되면 더티체킹이 되어 update 문을 날려줌
	}
	
	@Transactional(readOnly=true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
}
