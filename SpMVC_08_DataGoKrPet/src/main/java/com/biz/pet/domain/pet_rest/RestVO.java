package com.biz.pet.domain.pet_rest;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * java 1.7 이상에서만 작동된다
 * 그 이하에서는 jaxb 라이브러리만 추가해주면 된다
 */
@XmlRootElement(name="rfcOpenApi")
public class RestVO {
	
	public RestBody body;

}
