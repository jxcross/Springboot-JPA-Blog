package com.cos.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index";		// viewResolver 작동!!
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}

	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
//	@GetMapping({"", "/"})
//	public String index() {
//		//   /WEB-INF/views/index.jsp
//		return "index";
//	}
	
//	@GetMapping({"", "/"})
//	public String index(@AuthenticationPrincipal PrincipalDetail principal) {  // 컨트롤러에서 세션을 어떻게 찾는지?
//		// /WEB-INF/views/index.jsp
//		System.out.println("로그인 사용자 아이디 : " + principal.getUsername());
//		return "index";
//	}
}
