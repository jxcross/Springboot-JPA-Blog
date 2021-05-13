package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@RestController
public class DummyControllerTest {
	
	@Autowired	// 의존성 주입(DI)
	private UserRepository userRepository;

	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입이 완료되었습니다";
	}
//	public String join(String username, String password, String email) {	  // key=value (약속된 규칙)
//		System.out.println("username: " + username);
//		System.out.println("password:" + password);
//		System.out.println("email: " + email);		
//		return "회원가입이 완료되었습니다";
//	}
	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/2
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4 를 실행할 경우, DB에서 못 찾으면 user가 null 이 될것임
		// 그러면, return null 이므로, 프로그램에서 문제가 생김 
		// Optional 타입으로 User 객체를 감싸서 가져올테니 null 인지 판단해서 return 처리하라
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다.");
//		});
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다: " + id);
			}			
		});
		
		// 요청: 웹브라우저
		// user 객체 => 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리) 필요
		// 스프링부트 => MessageConverter 가 응답시에 자동으로 작동됨
		// 만약, 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출하여
		// user 오브젝트를 json으로 변환해서 브라우저에 던져 줌		
		return user;
	}
	
	// http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건씩 데이터를 리턴받아 볼 경우
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		//if (pagingUser.isLast()) { }
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // JSON 데이터 요청 => Java Object로 변환해서 받아줌(MessageConverter의 Jackson 라이브러리)
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		// 더티 체킹 (변경을 감지하여 update)
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		return user;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실해하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id: " + id;
	}
	
}
