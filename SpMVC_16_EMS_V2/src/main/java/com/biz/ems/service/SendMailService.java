package com.biz.ems.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biz.ems.domain.EmailVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SendMailService {
	
	private final JavaMailSender mailSender;
	
	public void sendMail(EmailVO emailVO) {
		
		String from_email = emailVO.getFrom_email();
		String to_email = emailVO.getTo_email();
		
		String from_name = emailVO.getFrom_name();
		String to_name = emailVO.getTo_name();
		
		// mail을 보내기 위한 smtp 객체
		// mail 메시지를 담을 객체
		MimeMessage message = mailSender.createMimeMessage();
		
		// mail 보내는데 도와줄 보조 객체
		MimeMessageHelper mHelper = null;
		
		try {
			// 1번째 매개변수 : message => 보낼 제목과 본문을 담을 객체
			// 2번째 매개변수 : false => 텍스트만 보냄, true => 파일 첨부 가능
			// 3번째 매개변수 : 문자열 인코딩 지정
			mHelper = new MimeMessageHelper(message, false, "UTF-8");
			mHelper.setFrom(from_email, from_name);
			mHelper.setTo(to_email);
			
			mHelper.setSubject(emailVO.getSubject());
			mHelper.setText(emailVO.getContent(), true);//2번째 매개변수를 true로 설정하면 HTML 태그를 반영하여 보낼 수 있다
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
