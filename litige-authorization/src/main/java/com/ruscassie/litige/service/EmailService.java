package com.ruscassie.litige.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.entity.User;

@Service
public class EmailService {
// https://o7planning.org/fr/11145/tutoriel-spring-email
	@Autowired
	private JavaMailSender javaMailSender;

//	public void sendEmailValidAccount(final User user) {
//
//		final SimpleMailMessage msg = new SimpleMailMessage();
//		msg.setTo(user.getEmail());
//
//		msg.setSubject("Activation de votre compte Arsel");
//		msg.setText(user.getTokenActiveAccount() + "  " + user.getEmail());
//
//		javaMailSender.send(msg);
//
//	}

	public void sendEmailValidAccount(final User user) throws MessagingException, IOException {

		final MimeMessage msg = javaMailSender.createMimeMessage();

		// true = multipart message
		final MimeMessageHelper helper = new MimeMessageHelper(msg, true);

		// helper.setTo(user.getEmail());
		helper.setTo("guillaume.ruscassie@outlook.fr");
		helper.setSubject("Activation de votre compte Arsel");

		// default = text/plain
		// helper.setText("Check attachment for image!");

		// true = text/html
		helper.setText("<a href=\"http://localhost:4200/signin/link-validation-email/" + user.getEmail() + "/"
				+ user.getTokenActiveAccount() + ">Link</a>", true);

		// hard coded a file path
		// FileSystemResource file = new FileSystemResource(new
		// File("path/android.png"));

		// helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

		javaMailSender.send(msg);

	}
}