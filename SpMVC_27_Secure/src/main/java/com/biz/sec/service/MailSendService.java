package com.biz.sec.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSendService {
	
	private final JavaMailSender javaMailSender;
	
	public MailSendService(@Qualifier("naverMailHandler") JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	public void sendMail() {
		
		String from_email = "sliec20@naver.com";
		String to_email = "sianblone@gmail.com";
		String subject = "제목";
		String content = "안녕하세요";
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mHelper;
		mHelper = new MimeMessageHelper(message, "UTF-8");
		
		try {
			mHelper.setFrom(from_email);
			mHelper.setTo(to_email);
			mHelper.setSubject(subject);
			// setText의 2번째 매개변수 true : HTML 태그 적용
			mHelper.setText(content, true);
			
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
