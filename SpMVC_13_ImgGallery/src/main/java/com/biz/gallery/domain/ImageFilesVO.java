package com.biz.gallery.domain;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("fileVO")// Mapper를 사용하지 않을 경우 alias는 쓸모가 없다
public class ImageFilesVO {
	
	public long img_file_seq;
	public long img_file_p_code;
	public String img_file_origin_name;
	public String img_file_upload_name;

}
