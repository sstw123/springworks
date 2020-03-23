package com.mycompany.bucket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BucketDTO {
	
	private int b_id;//	INT,
	private int b_order;//	INT,
	private String b_content;//	VARCHAR(1000),
	private boolean b_success;//	BOOLEAN

}
