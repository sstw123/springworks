package com.app.money.domain;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name = "wantedDtl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// 쓰지 않음
public class ResultWantedDetailVO {
	
	private String servId;//	서비스ID
	private String servNm;//	서비스명
	private String jurMnofNm;//	소관부처명
	private String servDgst;//	서비스 요약
	private String tgtrDtlCn;//	대상자 상세내용
	private String slctCritCn;//	선정기준 내용
	private String alwServCn;//	급여서비스 내용
	private String servSeCode;//	생애주기 코드
	
	private ResultWantedDetailSubVO applmetList;//	
	private ResultWantedDetailSubVO inqplCtadrList;//	
	private ResultWantedDetailSubVO inqplHmpgReldList;//	
	private ResultWantedDetailSubVO basfrmList;
	private ResultWantedDetailSubVO baslawList;

}