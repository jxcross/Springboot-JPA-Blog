package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder  // 빌더 패
@AllArgsConstructor
@NoArgsConstructor
@Data
// ORM -> JAVA(다른 언어) Object -> 테이블로 매핑해 주는 기술 
@Entity // User 클래스가 MySQL에 테이블을 생성
//@DynamicInsert  // insert 시에 null 인 필드를 제외시켜준다.
public class User {
	
	@Id // Primary Key
	@GeneratedValue(strategy=GenerationType.IDENTITY	)  // 프로젝트에서 연결된 DB의 넘버링 전략을 따름
	private int id;		// 시퀀스, auto_increment
	
	@Column(nullable=false, length=30)
	private String username;  	// 아이디
	
	@Column(nullable=false, length=100)
	private String password;		// 해쉬 (비밀번호 암호화)
	
	@Column (nullable=false, length=50)
	private String email;	// myEmail, my_email
	
	//@ColumnDefault("user")
	//private String role;	// Enum을 쓰는 게 좋다 (admin, user, manager, ...)
	// DB는 RoleType 이 없음
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp  // 시간 자동 입력
	private Timestamp createDate;
	
}
