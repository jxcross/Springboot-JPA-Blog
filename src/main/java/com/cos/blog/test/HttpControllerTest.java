package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {
	
	private static final String TAG="HttpControllerTest: ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m = new Member(1,  "ssar", "1234", "abc@abc.com");
		Member m = Member.builder().username("ssar").password("1234").email("ssar@abc.com").build();
		
		System.out.println(TAG+"getter:"+m.getId());
		m.setId(5000);
		System.out.println(TAG+"setter:"+ m.getId());
		return "lombok test 완료";
	}

//	@GetMapping("/http/get")
//	public String getTest(@RequestParam int id, @RequestParam String username) {
//		return "get 요청: " + id + "," + username;
//	}
	
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청: " + m.getId() +","+ m.getUsername()+","+m.getEmail()+","+m.getEmail();
	}
	
	
//	@PostMapping("/http/post")
//	public String postTest(Member m) {
//		return "post 요청: " + m.getId()+","+m.getUsername()+","+m.getEmail()+","+m.getPassword();
//	}
	
//	@PostMapping("/http/post")
//	public String postTest(@RequestBody String text) {
//		return "post 요청: " + text;
//	}
	
	// Spring converter 작동
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청: " + m.getId() +","+ m.getUsername()+","+m.getEmail()+","+m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청: " + m.getId() +","+ m.getUsername()+","+m.getEmail()+","+m.getEmail();
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
