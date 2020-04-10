package com.biz.score.model;

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
public class ScoreVO {
	
	private long id;
	
	// DB에서 가져올 때, 저장할 때 매핑할 칼럼명
	private String st_num;
	private String st_name;
	private String st_grade;
	private String st_class;
	
	// DB에 저장할 때 매핑할 칼럼명
	private String s_num;
	private String s_subject;
	private int s_score;
	
	// DB에서 가져올 때 매핑할 변수명
	private int s_kor;
	private int s_eng;
	private int s_math;
	private int s_sum;
	private int s_avg;
	private int s_rank;
}
