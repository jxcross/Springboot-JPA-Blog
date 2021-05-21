package com.cos.blog.model;

import lombok.Data;

// https://www.jsonschema2pojo.org 사이트를 통해 json 타입을 Java 오브젝트로 변경
// 단, 변수명의 중간 대문자를 '_' 로 수정해야 
	
@Data
public class KakaoProfile {
	public Integer id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;

	@Data
	public class Properties {
		public String nickname;
		public String profile_image;
		public String thumbnail_image;
	}

	@Data
	public class KakaoAccount {
		public Boolean profile_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;

		@Data
		public class Profile {
			public String nickname;
			public String thumbnail_image_url;
			public String profile_image_url;
			public Boolean is_default_image;  // 새로 추가됨 (2021.05.20)
		}
	}
}