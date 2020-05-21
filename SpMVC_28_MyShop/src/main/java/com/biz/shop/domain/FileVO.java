package com.biz.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 상품 정보의 이미지들을 관리할 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FileVO {

	private long id;
	private String file_p_code;//상품테이블과 join하기 위한 key
	private String file_origin_name;//실제 이미지 파일명
	private String file_upload_name;//업로드 된 변경된 파일명
	
}
