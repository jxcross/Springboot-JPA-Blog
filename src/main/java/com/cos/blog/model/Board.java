package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {

	@Id  // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	 // auto_increment
	private int id;

	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob		// 대용량 데이터
	private String content;		// 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이  됨
	
	//@ColumnDefault("0")
	private int count; 	// 조회수
	

	//private int userId;
	@ManyToOne  (fetch = FetchType.EAGER)	// Many: Board, One: User (한 명의 유저가 여러 개의 보드 가능)
	@JoinColumn(name="userId")
	private User user;		// DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
	
	// 연관 관계의 주인은 내가 아니다. (난 FK가 아니다)
	// DB에 컬럼을 만들지 말라.
	// "board" 는 Reply 클래스에 있는 변수명임! (테이블에 생성되는 FK가 아님에 유의!
	// cascade 속성으로 게시글 삭제시 댓글도 모두 지우도록 설정
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)  // OneToMany의 기본전략은 LAZY
	@JsonIgnoreProperties({"board"})  // 무한참조 금지
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}
