package com.biz.sec.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.utils.PbeEncryptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSendService {
	
	private final JavaMailSender javaMailSender;
	private final String from_email = "sianblone@gmail.com";
	
	public MailSendService(@Qualifier("gMailHandler") JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMail() {
		
		String to_email = "sianblone@gmail.com";
		String subject = "제목";
		String content = "안녕하세요";
		this.sendMail(to_email, subject, content);
	}
	
	public boolean sendMail(String to_email, String subject, String content) {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mHelper = new MimeMessageHelper(message, "UTF-8");
		boolean ret = false;
		
		try {
			mHelper.setFrom(from_email);
			mHelper.setTo(to_email);
			mHelper.setSubject(subject);
			// setText의 2번째 매개변수 true : HTML 태그 적용
			mHelper.setText(content, true);
			
			javaMailSender.send(message);
			ret = true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 회원가입된 사용자에게 인증 email 전송
	 * username을 암호화시키고
	 * email 인증을 수행할 수 있는 링크를 email 본문에 작성하여 전송
	 * @param userVO
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public boolean join_send(UserDetailsVO userVO) throws UnsupportedEncodingException {
		
		String username = userVO.getUsername();
		String email = userVO.getEmail();
		
		String encUsername = PbeEncryptor.encrypt(username);
		String encEmail = PbeEncryptor.encrypt(email);

		StringBuilder email_message = new StringBuilder();
		email_message.append("<p>회원가입을 환영합니다!");
		email_message.append("이메일 인증 링크를 클릭해주세요!</p>");
		email_message.append("<a href='%s'>링크</a>");

		// JASYPT를 사용하여 username과 email을 암호화하면 슬래시(/)가 포함되어있어 URL에 사용하기 힘들다
		// 따라서 URL을 통해 정상적으로 보낼 수 있도록 암호화된 문자열을 URLEncoder.encode() 등을 통해 인코딩하여 보낸다
		StringBuilder email_link = new StringBuilder();
		email_link.append("http://localhost:8080/sec");
		email_link.append("/join/emailvalid");
		email_link.append("?username=" + URLEncoder.encode(encUsername, "UTF-8"));
		email_link.append("&email=" + URLEncoder.encode(encEmail, "UTF-8"));
		
		String send_message = String.format(email_message.toString(), email_link.toString());
		
		String to_email = email;
		String subject = "회원가입 인증메일";
		String content = send_message;
		
		boolean ret = this.sendMail(to_email, subject, content);
		
		return false;
	}

	/**
	 * @since 2020-04-21
	 * 이메일 인증을 위한 token 정보를 email로 전송하기
	 * @param email_token
	 */
	public void email_auth(UserDetailsVO userVO, String email_token) {
		// TODO Auto-generated method stub
		StringBuilder email_content = new StringBuilder();
		email_content.append("<style>");
		email_content.append(".email_token {");
		email_content.append("border: 1px solid blue;");
		email_content.append("background-color: green;");
		email_content.append("color: white;");
		email_content.append("font-weight: bold;");
		email_content.append("}");
		email_content.append(".welcome {");
		email_content.append("font-size: large;");
		email_content.append("}");
		email_content.append("</style>");
		email_content.append("<p class='welcome'>회원가입을 완료하려면 인증코드를 입력해주세요</p>");
		email_content.append("<div class='email_token'>");
		email_content.append(email_token);
		email_content.append("</div>");
		
		String subject = "회원가입 인증메일";
		
		this.sendMail(userVO.getEmail(), subject, email_content.toString());
	}

}
