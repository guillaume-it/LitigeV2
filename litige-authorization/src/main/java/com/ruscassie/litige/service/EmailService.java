package com.ruscassie.litige.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.entity.User;

@Service
public class EmailService {
// https://o7planning.org/fr/11145/tutoriel-spring-email
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail() {

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("guillaume.ruscassie@outlook.fr");

		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");

		javaMailSender.send(msg);

	}

	public void sendEmailValidAccount(final User user) {

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());

		msg.setSubject("Validate account");
		msg.setText(user.getTokenActiveAccount() + "  " + user.getEmail());

		javaMailSender.send(msg);

	}
}